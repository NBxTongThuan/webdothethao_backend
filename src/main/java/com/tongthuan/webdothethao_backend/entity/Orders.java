package com.tongthuan.webdothethao_backend.entity;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private Long totalPrice;

    @Column(name = "ship_fee")
    private Long shipFee;

    @Column(name = "final_price")
    private Long finalPrice;

    @Column(name = "total_money_off")
    private Long totalMoneyOff;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "to_name")
    private String toName;

    @Column(name = "to_phone")
    private String toPhone;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "to_province")
    private String toProvince;

    @Column(name = "to_district")
    private String toDistrict;

    @Column(name = "to_ward")
    private String toWard;

    @Column(name = "to_address", columnDefinition = "LONGTEXT")
    private String toAddress;

    @Column(name = "order_note", columnDefinition = "LONGTEXT")
    private String orderNote;

    @Column(name = "order_note_canceled", columnDefinition = "LONGTEXT")
    private String orderNoteCanceled;

    @Column(name = "date_receive")
    private LocalDateTime dateReceive;

    @Column(name = "date_expected")
    private LocalDateTime dateExpected;

    @Column(name = "date_canceled")
    private LocalDateTime dateCanceled;

//    @Column(name = "deleted")
//    private boolean deleted;

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
    private List<OrderItems> listOrderItems;


}
