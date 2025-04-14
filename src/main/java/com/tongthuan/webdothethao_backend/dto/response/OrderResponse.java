package com.tongthuan.webdothethao_backend.dto.response;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.entity.Orders;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private String orderId;
    private OrderStatus status;
    private Date createdDate;
    private String toName;
    private String toPhone;
    private String toEmail;
    private String toProvince;
    private String toDistrict;
    private String toWard;
    private String toAddress;
    private String orderNote;
    private String orderNoteCanceled;
    private double totalPrice;
    private double shipFee;
    private Date dateReceive;
    private Date dateExpected;

    public OrderResponse(Orders order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.createdDate = order.getCreatedDate();
        this.toName = order.getToName();
        this.toPhone = order.getToPhone();
        this.toEmail = order.getToEmail();
        this.toProvince = order.getToProvince();
        this.toDistrict = order.getToDistrict();
        this.toWard = order.getToWard();
        this.toAddress = order.getToAddress();
        this.orderNote = order.getOrderNote();
        this.orderNoteCanceled = order.getOrderNoteCanceled();
        this.totalPrice = order.getTotalPrice();
        this.shipFee = order.getShipFee();
        this.dateReceive = order.getDateReceive();
        this.dateExpected = order.getDateExpected();
    }
}
