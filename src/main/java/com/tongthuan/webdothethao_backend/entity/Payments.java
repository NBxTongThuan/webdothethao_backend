package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;
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
    @Enumerated(EnumType.STRING)  // Lưu dưới dạng chuỗi
    private PaymentMethod paymentMethod;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)  // Lưu dưới dạng chuỗi
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Orders order;

}
