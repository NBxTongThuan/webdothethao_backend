package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "review_id", length = 50)
    private String reviewId;

    @Column(name = "rating")
    private double rating;

    @Column(name = "comment",columnDefinition = "LONGTEXT")
    private String comment;

    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "user_id")
    private Users user;

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
