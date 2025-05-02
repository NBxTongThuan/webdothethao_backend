package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.ProductGender;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private LocalDateTime createdDate;

    @Column(name = "quantity_sold")
    private int quantitySold;

    @Column(name = "price")
    private long price;

    @Column(name = "is_in_stock")
    private boolean isInStock;

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
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Reviews> reviewsList;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private ProductGender productGender;

    @Column(name = "money_off")
    private long moneyOff;

}
