package com.example.paymentservice.service;

import com.example.paymentservice.dto.Notification;
import com.example.paymentservice.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String NOTIFICATIONS_TOPIC = "order-notifications";

    public void sendPaymentConfirmation(PaymentEvent payment) {
        kafkaTemplate.send(NOTIFICATIONS_TOPIC,
                createNotification(payment, "Payment Successful",
                        "Your payment for order " + payment.getPaymentId() + " has been processed."));
    }

    public void sendPaymentFailure(PaymentEvent payment) {
        kafkaTemplate.send(NOTIFICATIONS_TOPIC,
                createNotification(payment, "Payment Failed",
                        "Insufficient funds for order " + payment.getPaymentId()));
    }

    public void sendCancellationConfirmation(PaymentEvent payment) {
        kafkaTemplate.send(NOTIFICATIONS_TOPIC,
                createNotification(payment, "Order Cancelled",
                        "Your order " + payment.getPaymentId() + " has been refunded."));
    }

    private Notification createNotification(PaymentEvent paymentEvent, String subject, String message) {
        return new Notification(
                paymentEvent.getUsername(),
                subject,
                message
        );
    }
}
