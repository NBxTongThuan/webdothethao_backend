package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private String addressId;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_phone")
    private String toPhone;

    @Column(name = "to_province")
    private String toProvince;

    @Column(name = "to_district")
    private String toDistrict;

    @Column(name = "to_ward")
    private String toWard;

    @Column(name = "to_address", columnDefinition = "LONGTEXT")
    private String toAddress;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private Users user;
}
