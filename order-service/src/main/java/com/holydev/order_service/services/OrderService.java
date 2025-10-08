package com.holydev.order_service.services;

import com.holydev.order_service.events.OrderPlacedEvent;
import com.holydev.order_service.model.Order;
import com.holydev.order_service.repository.OrderRepository;
import com.holydev.order_service.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Cacheable(value = "allOrders", key = "'all'")
    public List<Order> getAllORders(){
        System.out.println("---------⏳ Querying DB...Doing Redis Get All Orders!----------");
        return orderRepository.findAll();
    }

    public Order createOrder(Order order){
        Order saved = orderRepository.save(order);

        // Gửi event Kafka
        OrderPlacedEvent event = OrderPlacedEvent.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .total(saved.getTotal())
                .build();

        kafkaTemplate.send("order-topic", event);
        System.out.println("📤 Đã gửi Kafka event: " + event);

        return saved;
    }

}
