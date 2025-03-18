package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", length = 50)
    private String paymentId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "created_date")
    private Date createdDate;
}
