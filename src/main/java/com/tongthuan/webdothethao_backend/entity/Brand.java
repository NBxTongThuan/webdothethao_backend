package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", length = 50)
    private int brandId;

    @Column(name = "brand_name")
    private String brandName;

    @OneToMany(mappedBy = "brand",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Products> productsList;

}
