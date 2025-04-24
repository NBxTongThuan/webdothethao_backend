package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Size;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categories_id")
    private int categoriesId;

    @Column(name = "categories_name")
    private String categoriesName;

    @Column(name = "image_data", columnDefinition = "LONGTEXT")
    @Lob
    private String imageData;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    public Size size;

    @OneToMany(mappedBy = "categories",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Types> typesList;





}
