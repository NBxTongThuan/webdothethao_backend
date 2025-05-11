package com.tongthuan.webdothethao_backend.dto.response.AdminResponse;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderResponse {

    private String orderId;
    private OrderStatus status;
    private LocalDateTime createdDate;
    private String toName;
    private String toPhone;
    private String toEmail;
    private String toProvince;
    private String toDistrict;
    private String toWard;
    private String toAddress;
    private String orderNote;
    private String orderNoteCanceled;
    private long totalPrice;
    private long totalImportPrice;
    private long shipFee;
    private long totalMoneyOff;
    private long finalPrice;
    private LocalDateTime dateReceive;
    private LocalDateTime dateExpected;
    private LocalDateTime dateCancel;

    public AdminOrderResponse(Orders order) {
        this.orderId = order.getOrderId();
        this.status = order.getStatus();
        this.createdDate = order.getCreatedDate();
        this.toName = order.getToName();
        this.toPhone = order.getToPhone();
        this.toEmail = order.getToEmail();
        this.toProvince = order.getToProvince();
        this.toDistrict = order.getToDistrict();
        this.totalImportPrice = order.getTotalImportPrice();
        this.toWard = order.getToWard();
        this.toAddress = order.getToAddress();
        this.orderNote = order.getOrderNote();
        this.orderNoteCanceled = order.getOrderNoteCanceled();
        this.totalPrice = order.getTotalPrice();
        this.shipFee = order.getShipFee();
        this.totalMoneyOff = order.getTotalMoneyOff();
        this.finalPrice = order.getFinalPrice();
        this.dateReceive = order.getDateReceive();
        this.dateExpected = order.getDateExpected();
        this.dateCancel = order.getDateCanceled();
    }
}
