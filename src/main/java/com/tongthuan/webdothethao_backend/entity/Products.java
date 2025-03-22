package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", length = 50)
    private String productId;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Images> listImage;

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;


    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<ProductAttributes> listProductAttributes;
    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "type_id", nullable = false)
    private Types type;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<CartItems> cartItemsList;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Reviews> reviewsList;

    @OneToMany(mappedBy = "product",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<OrderDetail> orderDetailList;

}
