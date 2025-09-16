package com.holydev.order_service.controller;

import com.holydev.order_service.Dto.OrderDto;
import com.holydev.order_service.feignRepository.UserClient;
import com.holydev.order_service.fiegnDto.UserDto;
import com.holydev.order_service.model.Order;
import com.holydev.order_service.repository.OrderRepository;
import com.holydev.order_service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    private final UserClient userClient;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    OrderResponse getOrderById(@PathVariable Long id){
        Order order = orderRepository.findById(id).orElseThrow();
        //Goi user client
        UserDto userDto = userClient.getUserById(order.getUserId());

        return new OrderResponse(order.getId(), order.getProduct(), order.getPrice(), userDto);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDto> getOrdersByUserId(@PathVariable Long userId) {
        return orderRepository.getOrdersByUserId(userId);
    }
}
