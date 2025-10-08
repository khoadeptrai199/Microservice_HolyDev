package com.holydev.order_service.services;

import com.holydev.order_service.model.Order;
import com.holydev.order_service.repository.OrderRepository;
import com.holydev.order_service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Cacheable(value = "allOrders", key = "'all'")
    public List<Order> getAllORders(){
        System.out.println("---------⏳ Querying DB...Doing Redis Get All Orders!----------");
        return orderRepository.findAll();
    }

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }
}
