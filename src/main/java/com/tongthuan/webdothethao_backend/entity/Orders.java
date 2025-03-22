package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.Color;
import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
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
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


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

    @OneToMany(mappedBy = "order",
            fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE
    })
    private List<OrderDetail> listOrderDetail;


}
