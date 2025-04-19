package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
import com.tongthuan.webdothethao_backend.dto.adminRequest.AdminUpdateOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.CancelOrderRequest;
import com.tongthuan.webdothethao_backend.dto.request.OrderRequest.OrderRequest;
import com.tongthuan.webdothethao_backend.entity.*;
import com.tongthuan.webdothethao_backend.repository.*;
import com.tongthuan.webdothethao_backend.service.serviceInterface.OrdersService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.ProductAttributeService;
import com.tongthuan.webdothethao_backend.service.serviceInterface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

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


    //USER
    @Override
    public Orders addCodOrder(OrderRequest orderRequest) {

        Orders orders = new Orders();
        Users user = usersService.findByUserName(orderRequest.getUserName());
        if (user == null) {
            return null;
        }
        orders.setUser(user);
        orders.setCreatedDate(new Date(System.currentTimeMillis()));
        orders.setDateExpected(new Date(System.currentTimeMillis() + 30000));

        orders.setOrderNote(orderRequest.getOrderNote());
        orders.setStatus(OrderStatus.PENDING);
        orders.setTotalPrice(orderRequest.getTotalPrice());
        orders.setShipFee(orderRequest.getShipFee());
        orders.setToProvince(orderRequest.getToProvince());
        orders.setToDistrict(orderRequest.getToDistrict());
        orders.setToWard(orderRequest.getToWard());
        orders.setToAddress(orderRequest.getToAddress());
        orders.setToEmail(orderRequest.getToEmail());
        orders.setToName(orderRequest.getToName());
        orders.setToPhone(orderRequest.getToPhone());

        //orderDetails
        List<OrderItems> orderItems = new ArrayList<>();
        Arrays.stream(orderRequest.getOrderItems()).forEach(item -> {
            OrderItems orderItems1 = new OrderItems();
            orderItems1.setOrder(orders);
            orderItems1.setPrice(item.getPrice());

            ProductAttributes productAttribute = productAttributeService.findByProductAttributeId(item.getProductAttributeId());
            if (productAttribute == null) {
                return;
            }
            orderItems1.setProductAttribute(
                    productAttribute
            );
            orderItems1.setQuantity(item.getQuantity());
            productAttribute.setQuantity(productAttribute.getQuantity() - item.getQuantity());
            productAttributesRepository.saveAndFlush(productAttribute);
            orderItems.add(orderItems1);
        });
        orders.setListOrderItems(orderItems);
        //Payment
        Payments payments = new Payments();
        payments.setOrder(orders);
        payments.setStatus(PaymentStatus.PENDING);
        payments.setCreatedDate(new Date(System.currentTimeMillis()));
        payments.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);

        ordersRepository.saveAndFlush(orders);
        paymentsRepository.saveAndFlush(payments);

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
        order.setDateCanceled(new Date(System.currentTimeMillis()));
        paymentsRepository.saveAndFlush(payment);
        ordersRepository.saveAndFlush(order);
        return true;
    }

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
            order.setDateReceive(new Date(System.currentTimeMillis()));
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
            order.setDateReceive(new Date(System.currentTimeMillis()));
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
            order.setDateCanceled(new Date(System.currentTimeMillis()));
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

}
