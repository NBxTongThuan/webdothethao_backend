package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @Column(name = "user_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "enable")
    private boolean enable;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Reviews> listReviews;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<Orders> listOrders;


}
