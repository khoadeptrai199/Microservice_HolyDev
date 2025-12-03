package com.holydev.order_service.controller;

import com.holydev.order_service.Dto.OrderDto;
import com.holydev.order_service.feignRepository.UserClient;
import com.holydev.order_service.fiegnDto.UserDto;
import com.holydev.order_service.model.Order;
import com.holydev.order_service.repository.OrderRepository;
import com.holydev.order_service.response.OrderResponse;
import com.holydev.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    private final UserClient userClient;

   /* @PostMapping
    @CacheEvict(value = "allOrders", allEntries = true)
    public Order placeOrder(@RequestBody Order order) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUserId(userId);
        return orderService.createOrder(order);
    }*/

    @PostMapping
    @CacheEvict(value = "allOrders", allEntries = true)
    public Order placeOrder(@RequestBody Order order, JwtAuthenticationToken auth) {
        String sub = auth.getToken().getSubject(); // lấy Keycloak-sub (UUID)
        UserDto user = userClient.getUserByKeycloakId(sub); // Feign gọi user-service

        order.setUserId(user.getId()); // gán Long userId
        return orderService.createOrder(order);
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllORders();
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
