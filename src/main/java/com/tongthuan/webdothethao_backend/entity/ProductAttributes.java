package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_attributes")
public class ProductAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_attribute_id", length = 50)
    private String productAttributeId;

    @Column(name = "color")
    private String color;

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

}
