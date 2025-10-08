package com.holydev.notificationservice.eventListeners;

import com.holydev.notificationservice.events.OrderPlacedEvent;
import com.holydev.notificationservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = "order-topic", groupId = "notification-group", containerFactory = "orderPlacedEventListenerFactory")
    public void handleOrderEvent(OrderPlacedEvent event) {
        System.out.println("📨 Nhận được event từ Kafka: " + event);
        // Thực hiện gửi email ở đây
        emailService.sendOrderEmail(event);
    }
}
