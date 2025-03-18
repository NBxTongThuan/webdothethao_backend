package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "types")
public class Types {

    @Id
    @Column(name = "type_id")
    private int typeId;

    @Column(name = "type_name")
    private String typename;

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @OneToMany(mappedBy = "type",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Products> productsList;

}
