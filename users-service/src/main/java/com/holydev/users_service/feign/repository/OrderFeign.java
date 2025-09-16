package com.holydev.users_service.feign.repository;

import com.holydev.users_service.feign.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service")
public interface OrderFeign {

    @GetMapping("/api/orders/user/{userId}")
    List<OrderDto> getOrdersByUserId( @PathVariable Long userId);

}
