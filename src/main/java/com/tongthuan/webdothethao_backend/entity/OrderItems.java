package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_item_id", length = 50)
    private String orderItemId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "original_price")
    private long originalPrice;

    @Column(name = "final_price")
    private long finalPrice;

    @Column(name = "is_reviewed")
    private boolean isReviewed;

    @Column(name = "import_price")
    private long importPrice;

    @Column(name = "money_off_per_one_product")
    private long moneyOffPerOneProduct;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_attribute_id", nullable = false)
    private ProductAttributes productAttribute;
}
