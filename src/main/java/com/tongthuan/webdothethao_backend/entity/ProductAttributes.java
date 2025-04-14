package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product_attributes")
public class ProductAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_attribute_id", length = 50)
    private String productAttributeId;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)  // Lưu dưới dạng chuỗi
    private Color color;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    @OneToMany(mappedBy = "productAttribute",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<CartItems> cartItemsList;

    @OneToMany(mappedBy = "productAttribute",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<OrderItems> orderItemsList;

    @OneToMany(mappedBy = "productAttribute",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE})
    private List<Reviews> reviewsList;

}
