package com.example.paymentservice.service;

import com.example.paymentservice.dto.PaymentEvent;
import com.example.paymentservice.dto.PaymentStatus;
import com.example.paymentservice.entities.User;
import com.example.paymentservice.repo.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class PaymentService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    KafkaTemplate<String, Object> kafkaTemplate;
    UserRepository userRepository;
    NotificationService notificationService;

    @KafkaListener(topics = "payment",groupId = "payment-group")
    @Transactional
    public void processPaymentEvent(PaymentEvent paymentEvent){
        logger.info("Receive info: {}",paymentEvent.getPaymentId());
        try{
            User user = userRepository.findByUsername(paymentEvent.getUsername())
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            switch (paymentEvent.getStatus()) {
                case PENDING:
                    // Validate and subtract balance
                    if (user.getAmountAvailable().compareTo(paymentEvent.getAmount()) >= 0) {
                        var amountLeft = user.getAmountAvailable().subtract(paymentEvent.getAmount());
                        logger.info("Reducing {} amount from {} to {}",paymentEvent.getUsername(),
                               user.getAmountAvailable(), amountLeft);
                        user.setAmountAvailable(amountLeft);
                        userRepository.save(user);

                        // Send payment confirmation
                        notificationService.sendPaymentConfirmation(paymentEvent);
                    } else {
                        logger.info("Payment reject for user: {}",paymentEvent.getUsername());
                        // Insufficient funds
                        paymentEvent.setStatus(PaymentStatus.REJECTED);
                        notificationService.sendPaymentFailure(paymentEvent);
                    }
                    break;

                case CANCEL:
                    // Refund the amount
                    logger.info("Refund for username: {}", paymentEvent.getUsername());
                    user.setAmountAvailable(user.getAmountAvailable().add(paymentEvent.getAmount()));
                    userRepository.save(user);

                    // Send cancellation notification
                    notificationService.sendCancellationConfirmation(paymentEvent);
                    break;
            }
        } catch (Exception e) {
            // Handle errors, potentially send to error topic
            logger.error("Error handling payment: {}", e.getMessage());
            kafkaTemplate.send("payment-error-topic", paymentEvent);
        }
    }
}
