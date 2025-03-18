package com.tongthuan.webdothethao_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", length = 50)
    private String orderId;

    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "status")
    private String status;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payments payment;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<OrderDetail> listOrderDetail;


}
