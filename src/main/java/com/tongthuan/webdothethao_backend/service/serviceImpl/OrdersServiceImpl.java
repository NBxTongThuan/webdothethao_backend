package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.CancelOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.dto.response.AdminResponse.RevenueByDateResponse;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.*;
import com.tongthuan.webdothethao_backend.service.JWTService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private JWTService jwtService;

    //USER
    @Transactional
    @Override
    public Orders addCodOrder(OrderRequest orderRequest, HttpServletRequest request) {

        String token = jwtService.getTokenFromCookie(request);

        if (jwtService.isTokenExpired(token))
            return null;

        if (token.equalsIgnoreCase(""))
            return null;

        Users user = usersService.findByUserName(jwtService.extractUsername(token));
        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null)
            return null;
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setCreatedDate(LocalDateTime.now());
        orders.setDateExpected(LocalDateTime.now().plusDays(3));
        orders.setOrderNote(orderRequest.getOrderNote());
        orders.setStatus(OrderStatus.PENDING);
        orders.setTotalPrice(getTotalPrice(cart.getCartId()));
        orders.setTotalMoneyOff(getTotalMoneyOff(cart.getCartId()));
        orders.setFinalPrice(getFinalPrice(cart.getCartId()));
        orders.setShipFee(30000L);
        orders.setToProvince(orderRequest.getToProvince());
        orders.setToDistrict(orderRequest.getToDistrict());
        orders.setToWard(orderRequest.getToWard());
        orders.setToAddress(orderRequest.getToAddress());
        orders.setToEmail(orderRequest.getToEmail());
        orders.setToName(orderRequest.getToName());
        orders.setToPhone(orderRequest.getToPhone());

        //orderDetails
        //orderDetails
        List<OrderItems> orderItems = createOrderItemsList(cart.getCartId());
        orderItems.forEach(item -> {

            ProductAttributes productAttribute = item.getProductAttribute();
            if (productAttribute == null) {
                return;
            }
            productAttribute.setQuantity(productAttribute.getQuantity() - item.getQuantity());
            productAttribute.setQuantitySold(productAttribute.getQuantitySold() + item.getQuantity());
            Products product = productAttribute.getProduct();
            product.setQuantitySold(product.getQuantitySold() + item.getQuantity());
            item.setOrder(orders);
            productsRepository.saveAndFlush(product);
            productAttributesRepository.saveAndFlush(productAttribute);

        });

        orders.setListOrderItems(orderItems);
        //Payment
        Payments payments = new Payments();
        payments.setOrder(orders);
        payments.setUser(user);
        payments.setAmount(getFinalPrice(cart.getCartId()));
        payments.setStatus(PaymentStatus.PENDING);
        payments.setCreatedDate(LocalDateTime.now());
        payments.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);

        //Notification
        Notifications notification = new Notifications();
        notification.setContent("Người dùng " + user.getUserName() + " vừa tạo một đơn hàng mới!");
        notification.setTitle("ĐƠN HÀNG MỚI");
        notification.setRead(false);
        notification.setOrder(orders);
        notification.setUser(user);
        notification.setCreatedDate(LocalDateTime.now());


        cartItemsRepository.deleteByCartId(cart.getCartId());
        ordersRepository.saveAndFlush(orders);
        paymentsRepository.saveAndFlush(payments);
        notificationRepository.saveAndFlush(notification);
        return ordersRepository.saveAndFlush(orders);
    }

    @Override
    public Page<Orders> findByUserName(String userName, Pageable pageable) {
        return ordersRepository.findByUserName(userName, pageable);
    }

    @Override
    public Page<Orders> findByUserNameAndOrderStatus(String userName, OrderStatus orderStatus, Pageable pageable) {
        return ordersRepository.findByUserNameAndOrderStatus(userName, orderStatus, pageable);
    }

    @Override
    public Optional<Orders> findByOrderId(String orderId) {
        return ordersRepository.findByOrderId(orderId);
    }

    @Transactional
    @Override
    public boolean cancelingOrder(CancelOrderRequest cancelOrderRequest) {

        Orders order = ordersRepository.findByOrderId(cancelOrderRequest.getOrderId()).orElse(null);

        Payments payment = paymentsRepository.findByOrderId(cancelOrderRequest.getOrderId()).orElse(null);
        if (payment == null) {
            return false;
        }

        if (order == null) {
            return false;
        }
        List<OrderItems> orderItemsList = orderItemRepository.findByOrderId(cancelOrderRequest.getOrderId());

        orderItemsList.forEach(
                item -> {
                    ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(item.getProductAttribute().getProductAttributeId());
                    if (productAttribute == null)
                        return;
                    productAttribute.setQuantity(productAttribute.getQuantity() + item.getQuantity());
                    productAttributesRepository.saveAndFlush(productAttribute);
                });
        payment.setStatus(PaymentStatus.CANCELLED);
        order.setStatus(OrderStatus.CANCELLED);
        order.setOrderNoteCanceled(cancelOrderRequest.getOrderCancelNote());
        order.setDateCanceled(LocalDateTime.now());
        paymentsRepository.saveAndFlush(payment);
        ordersRepository.saveAndFlush(order);
        return true;
    }

    //VNPay
    @Override
    public Orders createVNPayOrder(OrderRequest orderRequest, Users user, Cart cart, String vnpTxnRef) {

        Orders orders = new Orders();
        orders.setUser(user);
        orders.setCreatedDate(LocalDateTime.now());
        orders.setDateExpected(LocalDateTime.now().plusDays(3));

        orders.setOrderNote(orderRequest.getOrderNote());
        orders.setStatus(OrderStatus.PENDING);
        orders.setTotalPrice(getTotalPrice(cart.getCartId()));
        orders.setTotalMoneyOff(getTotalMoneyOff(cart.getCartId()));
        orders.setFinalPrice(getFinalPrice(cart.getCartId()));
        orders.setShipFee(30000L);
        orders.setToProvince(orderRequest.getToProvince());
        orders.setToDistrict(orderRequest.getToDistrict());
        orders.setToWard(orderRequest.getToWard());
        orders.setToAddress(orderRequest.getToAddress());
        orders.setToEmail(orderRequest.getToEmail());
        orders.setToName(orderRequest.getToName());
        orders.setToPhone(orderRequest.getToPhone());

        //orderDetails
        List<OrderItems> orderItems = createOrderItemsList(cart.getCartId());
        orderItems.forEach(item -> {

            ProductAttributes productAttribute = item.getProductAttribute();
            if (productAttribute == null) {
                return;
            }
            productAttribute.setQuantity(productAttribute.getQuantity() - item.getQuantity());
            productAttribute.setQuantitySold(productAttribute.getQuantitySold() + item.getQuantity());
            Products product = productAttribute.getProduct();
            product.setQuantitySold(product.getQuantitySold() + item.getQuantity());
            item.setOrder(orders);
            productsRepository.saveAndFlush(product);
            productAttributesRepository.saveAndFlush(productAttribute);

        });
        orders.setListOrderItems(orderItems);
        System.out.println("return true 1");
        //Payment
        Payments payments = new Payments();
        payments.setOrder(orders);
        payments.setStatus(PaymentStatus.PENDING);
        payments.setCreatedDate(LocalDateTime.now());
        payments.setAmount(getFinalPrice(cart.getCartId()));
        payments.setUser(user);
        payments.setVnpTxnRef(vnpTxnRef);
        payments.setPaymentMethod(PaymentMethod.VN_PAY);


        //Notification
        Notifications notification = new Notifications();
        notification.setContent("Người dùng " + user.getUserName() + " vừa tạo một đơn hàng mới!");
        notification.setTitle("ĐƠN HÀNG MỚI");
        notification.setRead(false);
        notification.setOrder(orders);
        notification.setUser(user);
        notification.setCreatedDate(LocalDateTime.now());

        cartItemsRepository.deleteByCartId(cart.getCartId());
        ordersRepository.saveAndFlush(orders);
        paymentsRepository.saveAndFlush(payments);
        notificationRepository.saveAndFlush(notification);

        return orders;
    }

    public void handleCancelVNPayOrder(Payments payment) {
        payment.setStatus(PaymentStatus.CANCELLED);
        Orders order = payment.getOrder();
        order.setStatus(OrderStatus.CANCELLED);
        order.setOrderNoteCanceled("Đơn hàng bị hủy do thanh toán không thành công!");
        order.setDateCanceled(LocalDateTime.now());
        List<OrderItems> orderItemsList = order.getListOrderItems();
        for (OrderItems orderItems : orderItemsList) {
            ProductAttributes productAttribute = orderItems.getProductAttribute();
            productAttribute.setQuantity(productAttribute.getQuantity() + orderItems.getQuantity());
            productAttribute.setQuantitySold(productAttribute.getQuantitySold() - orderItems.getQuantity());
            Products product = productAttribute.getProduct();
            product.setQuantitySold(product.getQuantitySold() - orderItems.getQuantity());
            productsRepository.saveAndFlush(product);
            productAttributesRepository.saveAndFlush(productAttribute);
        }
        ordersRepository.saveAndFlush(order);
        paymentsRepository.save(payment);
    }

//VNPay

    //ADMIN
    @Override
    public boolean adminUpdateOrderByOrderId(AdminUpdateOrderRequest adminUpdateOrderRequest) {
        Orders order = ordersRepository.findByOrderId(adminUpdateOrderRequest.getOrderId()).orElse(null);
        if (order == null) {
            System.out.println("ko thay don hang");
            return false;
        }
        Payments payment = paymentsRepository.findByOrderId(adminUpdateOrderRequest.getOrderId()).orElse(null);
        if (payment == null) {
            System.out.println("ko thay don hang");
            return false;
        }
        order.setStatus(adminUpdateOrderRequest.getOrderStatus());

        //CONFIRMED hoặc SHIPPING
        if (adminUpdateOrderRequest.getOrderStatus() == OrderStatus.CONFIRMED || adminUpdateOrderRequest.getOrderStatus() == OrderStatus.SHIPPING) {
            order.setDateReceive(LocalDateTime.now());
            if (payment.getPaymentMethod() == PaymentMethod.VN_PAY)
                payment.setStatus(PaymentStatus.COMPLETED);
            else if (payment.getPaymentMethod() == PaymentMethod.CASH_ON_DELIVERY)
                payment.setStatus(PaymentStatus.PENDING);
        }//SHIPPING
        else if (adminUpdateOrderRequest.getOrderStatus() == (OrderStatus.DELIVERED)) {
            List<OrderItems> orderItemsList = orderItemRepository.findByOrderId(order.getOrderId());
            for (OrderItems orderItem : orderItemsList) {
                ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(orderItem.getProductAttribute().getProductAttributeId());
                if (productAttribute == null)
                    continue;
                Products product = productAttribute.getProduct();
                product.setQuantitySold(product.getQuantitySold() + orderItem.getQuantity());
                productsRepository.saveAndFlush(product);
            }
            order.setDateReceive(LocalDateTime.now());
            payment.setStatus(PaymentStatus.COMPLETED);
        }//CANCELLED
        else if (adminUpdateOrderRequest.getOrderStatus() == (OrderStatus.CANCELLED)) {
            List<OrderItems> orderItemsList = orderItemRepository.findByOrderId(adminUpdateOrderRequest.getOrderId());
            orderItemsList.forEach(
                    item -> {
                        ProductAttributes productAttribute = productAttributesRepository.findByProductAttributeId(item.getProductAttribute().getProductAttributeId());
                        if (productAttribute == null)
                            return;
                        productAttribute.setQuantity(productAttribute.getQuantity() + item.getQuantity());
                        productAttributesRepository.saveAndFlush(productAttribute);
                    });
            order.setOrderNoteCanceled(adminUpdateOrderRequest.getOrderCancelNote());
            order.setDateCanceled(LocalDateTime.now());
            payment.setStatus(PaymentStatus.CANCELLED);

        }
        ordersRepository.saveAndFlush(order);
        paymentsRepository.saveAndFlush(payment);
        return true;
    }

    @Override
    public Page<Orders> adminGetAllOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable);
    }

    @Override
    public Page<Orders> adminGetAllOrdersByStatus(Pageable pageable, OrderStatus orderStatus) {
        return ordersRepository.adminFindAllByOrderStatus(orderStatus, pageable);
    }

    @Override
    public Long getTotalToDayOrder() {
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        return ordersRepository.countOrderToDay(today, month, year);
    }

    @Override
    public Long getRevenueOfMonth() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        return ordersRepository.getRevenueOfMonth(OrderStatus.DELIVERED, month, year);
    }

    @Override
    public List<RevenueByDateResponse> getRevenueByDateResponse(LocalDate start, LocalDate end) {

        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        return ordersRepository.getRevenueByDayBetween(OrderStatus.DELIVERED, startDateTime, endDateTime).stream().map(row -> new RevenueByDateResponse(
                ((Date) row[0]).toLocalDate(),
                (Long) row[1]
        )).collect(Collectors.toList());
    }

    @Override
    public Page<Orders> getNewOrders(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        int today = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();

        return ordersRepository.getOrdersToday(pageable, today, month, year);
    }


    public long getTotalPrice(String cartId) {
        long totalPrice = 0;
        List<CartItems> cartItems = cartItemsRepository.findByCartId(cartId);
        if (!cartItems.isEmpty()) {
            for (CartItems cartItem : cartItems) {
                Products product = cartItem.getProductAttribute().getProduct();
                totalPrice += product.getPrice() * cartItem.getQuantity();
            }
        }
        return totalPrice;
    }

    public long getTotalMoneyOff(String cartId) {
        long totalMoneyOff = 0L;
        List<CartItems> cartItems = cartItemsRepository.findByCartId(cartId);
        if (!cartItems.isEmpty()) {
            for (CartItems cartItem : cartItems) {
                Products product = cartItem.getProductAttribute().getProduct();
                totalMoneyOff += product.getMoneyOff() > 0 ? product.getMoneyOff() * cartItem.getQuantity() : 0L;
            }
        }
        return totalMoneyOff;
    }

    public long getFinalPrice(String cartId) {
        long finalPrice = 0L;
        List<CartItems> cartItems = cartItemsRepository.findByCartId(cartId);
        if (!cartItems.isEmpty()) {
            for (CartItems cartItem : cartItems) {
                Products product = cartItem.getProductAttribute().getProduct();
                finalPrice += product.getMoneyOff() > 0 ? ((product.getPrice() - product.getMoneyOff()) * cartItem.getQuantity()) : (product.getPrice() * cartItem.getQuantity());
            }
        }
        return finalPrice;
    }

    public List<OrderItems> createOrderItemsList(String cartId) {
        List<OrderItems> orderItemsList = new ArrayList<>();
        List<CartItems> cartItems = cartItemsRepository.findByCartId(cartId);
        if (!cartItems.isEmpty()) {
            for (CartItems cartItem : cartItems) {
                OrderItems orderItems = new OrderItems();
                orderItems.setReviewed(false);
                orderItems.setQuantity(cartItem.getQuantity());
                orderItems.setProductAttribute(cartItem.getProductAttribute());
                orderItems.setMoneyOffPerOneProduct(cartItem.getProductAttribute().getProduct().getMoneyOff());
                orderItems.setOriginalPrice(cartItem.getPrice());
                orderItems.setFinalPrice(cartItem.getPrice() - cartItem.getProductAttribute().getProduct().getMoneyOff());
                orderItemsList.add(orderItems);
            }
        }
        return orderItemsList;
    }


}
