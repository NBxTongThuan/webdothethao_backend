package com.tongthuan.webdothethao_backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.tongthuan.webdothethao_backend.constantvalue.PaymentMethod;
import com.tongthuan.webdothethao_backend.constantvalue.PaymentStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", length = 50)
    private String paymentId;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING) // Lưu dưới dạng chuỗi
    private PaymentMethod paymentMethod;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Lưu dưới dạng chuỗi
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "amount")
    private long amount;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "vnp_txn_ref")
    private String vnpTxnRef;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private Users user;
}
