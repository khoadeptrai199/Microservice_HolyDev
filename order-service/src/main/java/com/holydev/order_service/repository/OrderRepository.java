package com.holydev.order_service.repository;

import com.holydev.order_service.Dto.OrderDto;
import com.holydev.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT new com.holydev.order_service.Dto.OrderDto(o.id, o.userId, o.product, o.price) FROM Order o WHERE o.userId = :userId")
    List<OrderDto> getOrdersByUserId(@Param("userId") Long userId);
}

