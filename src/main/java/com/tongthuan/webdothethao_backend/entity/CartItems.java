package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_item_id", length = 50)
    private String cartItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private long price;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_attribute_id", nullable = false)
    private ProductAttributes productAttribute;
}
