package com.tongthuan.webdothethao_backend.entity;

import java.sql.Date;

import jakarta.persistence.*;

import com.tongthuan.webdothethao_backend.constantvalue.Gender;

import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_detail_id", length = 50)
    private String userDetailId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "address", columnDefinition = "LONGTEXT")
    private String address;
}
