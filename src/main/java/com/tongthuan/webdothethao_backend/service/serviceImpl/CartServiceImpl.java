package com.tongthuan.webdothethao_backend.service.serviceImpl;

import com.tongthuan.webdothethao_backend.constantvalue.ResponseCode;
import com.tongthuan.webdothethao_backend.dto.request.CartItemRequest.AddCartItemRequest;
import com.tongthuan.webdothethao_backend.entity.Cart;
import com.tongthuan.webdothethao_backend.entity.CartItems;
import com.tongthuan.webdothethao_backend.entity.ProductAttributes;
import com.tongthuan.webdothethao_backend.entity.Users;
import com.tongthuan.webdothethao_backend.repository.CartItemsRepository;
import com.tongthuan.webdothethao_backend.repository.CartRepository;
import com.tongthuan.webdothethao_backend.repository.ProductAttributesRepository;
import com.tongthuan.webdothethao_backend.repository.UsersRepository;
import com.tongthuan.webdothethao_backend.service.serviceInterface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemsRepository cartItemsRepository;

    @Autowired
    ProductAttributesRepository productAttributesRepository;

    @Override
    public ResponseEntity<?> addItemToCart(AddCartItemRequest cartItemRequest) {
        Users user = usersRepository.findByUserName(cartItemRequest.getUserName()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "statusCode", ResponseCode.USER_NOT_FOUND,
                            "message", "Không tìm thấy người dùng!"));
        }
        // Kiểm tra cart có tồn tại không
        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("statusCode", ResponseCode.CART_NOT_FOUND,
                            "message", "Không tìm thấy giỏ hàng!")
            );
        }
        // Kiểm tra sản phẩm có tồn tại không
        ProductAttributes productAttributes = productAttributesRepository.findByProductAttributeId(cartItemRequest.getProductAttributeId());
        if (productAttributes == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("statusCode", ResponseCode.PRODUCT_NOT_FOUND,
                            "message", "Không tìm được sản phẩm!"
                    ));
        }
        CartItems existingCartItem = cartItemsRepository.findByCartAndProductAttribute(cart, productAttributes);
        if (existingCartItem != null) {
            // Nếu sản phẩm đã có, cập nhật số lượng
            if (existingCartItem.getQuantity() + cartItemRequest.getQuantity() > productAttributes.getQuantity()) {
                existingCartItem.setQuantity(productAttributes.getQuantity());
                cartItemsRepository.save(existingCartItem);
                return ResponseEntity.ok().body(Map.of(
                        "statusCode", ResponseCode.NUMBER_REACHED_MAXIMUM,
                        "message", "Số lượng trong giỏ đã đạt mức tối đa!"
                ));
            } else {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
                cartItemsRepository.save(existingCartItem);
                return ResponseEntity.ok().body(
                        Map.of("statusCode", ResponseCode.SUCCESS,
                                "message", "Tăng số lượng sản phẩm trong giỏ hàng thành công")
                );
            }
        } else {
            // Nếu chưa có, tạo mới
            CartItems cartItems = new CartItems();
            cartItems.setCart(cart);
            cartItems.setPrice(cartItemRequest.getPrice());
            cartItems.setProductAttribute(productAttributes);
            cartItems.setQuantity(cartItemRequest.getQuantity());
            cartItemsRepository.save(cartItems);
            return ResponseEntity.ok().body(
                    Map.of("statusCode", ResponseCode.SUCCESS,
                            "message", "Thêm sản phẩm vào giỏ hàng thành công"
                    )
            );
        }

    }

    @Override
    public List<CartItems> getListCartItem(String cartId) {
        return cartItemsRepository.findByCartId(cartId);
    }


    @Override
    public int deleteCartItem(String cartItemID) {
        return cartItemsRepository.deleteByCartItemID(cartItemID);
    }
//
//    @Override
//    public boolean updateCart(String cartId) {
//
//        Cart cart = cartRepository.findById(cartId).orElse(null);
//        if(cart == null)
//            return  false;
//
//        return false;
//    }
}
