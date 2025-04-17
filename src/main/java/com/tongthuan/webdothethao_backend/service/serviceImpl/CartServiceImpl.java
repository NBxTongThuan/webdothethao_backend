package com.tongthuan.webdothethao_backend.service.serviceImpl;

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

import java.util.Collections;
import java.util.List;

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
            return ResponseEntity.badRequest().body("Người dùng không tồn tại!");
        }

        // Kiểm tra cart có tồn tại không
        Cart cart = cartRepository.findCartByUserId(user.getUserId());
        if (cart == null) {
            return ResponseEntity.badRequest().body("Giỏ hàng không tồn tại!");
        }

        // Kiểm tra sản phẩm có tồn tại không
        ProductAttributes productAttributes = productAttributesRepository.findByProductAttributeId(cartItemRequest.getProductAttributeId());
        if (productAttributes == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại!");
        }

        CartItems existingCartItem = cartItemsRepository.findByCartAndProductAttribute(cart, productAttributes);

        if (existingCartItem != null) {
            // Nếu sản phẩm đã có, cập nhật số lượng
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            cartItemsRepository.save(existingCartItem);
            return ResponseEntity.ok().body("Cập nhật số lượng sản phẩm trong giỏ hàng thành công");
        } else {
            // Nếu chưa có, tạo mới
            CartItems cartItems = new CartItems();
            cartItems.setCart(cart);
            cartItems.setPrice(cartItemRequest.getPrice());
            cartItems.setProductAttribute(productAttributes);
            cartItems.setQuantity(cartItemRequest.getQuantity());
            cartItemsRepository.save(cartItems);
            return ResponseEntity.ok().body("Thêm sản phẩm vào giỏ hàng thành công");
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
}
