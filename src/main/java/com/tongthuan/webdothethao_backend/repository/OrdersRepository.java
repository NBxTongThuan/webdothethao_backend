package com.tongthuan.webdothethao_backend.repository;

import com.tongthuan.webdothethao_backend.constantvalue.OrderStatus;
import com.tongthuan.webdothethao_backend.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {

    @Query("SELECT od FROM Orders od WHERE od.user.userName = :userName")
    Page<Orders> findByUserName(@Param("userName") String userName, Pageable pageable);

    @Query("SELECT od FROM Orders od WHERE od.user.userName = :userName AND od.status = :orderStatus")
    Page<Orders> findByUserNameAndOrderStatus(@Param("userName") String userName, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT od FROM Orders od WHERE od.orderId = :orderId")
    Optional<Orders> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT od FROM Orders od WHERE od.status = :orderStatus")
    Page<Orders> adminFindAllByOrderStatus(@Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Orders o WHERE DAY(o.createdDate) = :today AND MONTH(o.createdDate) = :month AND YEAR(o.createdDate) = :year")
    Long countOrderToDay(@Param("today") int today,@Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(o.totalPrice) FROM Orders o WHERE o.status = :status AND MONTH(o.createdDate) = :month AND YEAR(o.createdDate) = :year")
    Long getRevenueOfMonth(@Param("status") OrderStatus orderStatus, @Param("month") int month, @Param("year") int year);

    @Query("SELECT FUNCTION('DATE', o.createdDate) AS date, SUM(o.totalPrice) "+
    " FROM Orders o" +
    " WHERE o.status = :status" +
    " AND o.createdDate BETWEEN :start AND :end" +
    " GROUP BY FUNCTION('DATE', o.createdDate)" +
    " ORDER BY date ASC")
    List<Object[]> getRevenueByDayBetween(@Param("status") OrderStatus status,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);


    @Query("SELECT FUNCTION('DATE', o.createdDate) AS date, SUM(o.totalPrice  - o.totalImportPrice) "+
            " FROM Orders o" +
            " WHERE o.status = :status" +
            " AND o.createdDate BETWEEN :start AND :end" +
            " GROUP BY FUNCTION('DATE', o.createdDate)" +
            " ORDER BY date ASC")
    List<Object[]> getInterestByDayBetween(@Param("status") OrderStatus status,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :orderStatus")
    long getCountOrdersByOrderStatus(@Param("orderStatus") OrderStatus orderStatus);

    @Query("SELECT o FROM Orders o WHERE DAY(o.createdDate) = :today AND MONTH(o.createdDate) = :month AND YEAR(o.createdDate) = :year")
    Page<Orders> getOrdersToday(Pageable pageable,@Param("today") int today,@Param("month") int month,@Param("year") int year);


}
