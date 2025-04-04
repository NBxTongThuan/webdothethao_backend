package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Gender;
import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_detail_id", length = 50)
    private String user_detail_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private Date date_of_birth;


    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    @Column(name = "avatar", columnDefinition = "LONGTEXT")
    private String avatar;

}
