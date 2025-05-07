package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id", length = 50)
    private String imageId;

    @Column(name = "data", columnDefinition = "LONGTEXT")
    @Lob
    private String data;

//    @Column(name = "color")
//    @Enumerated(EnumType.STRING)  // Lưu dưới dạng chuỗi
//    private Color color;

    @Column(name = "url")
    private String url;
    @Column(name = "name")
    private String name;


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
