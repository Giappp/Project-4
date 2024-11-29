package com.example.beskbd.rest;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.example.beskbd.services.PaymentService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "*", maxAge = 360000)
@RequestMapping("/braintree")
public class RestBraintreeApi {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestParam String paymentMethodNonce, @RequestParam Double amount) {
        try {
            if (amount == null || amount <= 0) {
                return ResponseEntity.badRequest().body("Error processing payment: Amount must be greater than zero");
            }

            BigDecimal amountDecimal = BigDecimal.valueOf(amount);
            Result<Transaction> result = paymentService.processPayment(paymentMethodNonce, amountDecimal);

            if (result.isSuccess()) {
                Transaction transaction = result.getTarget();
                return ResponseEntity.ok("Payment successful, ID: " + transaction.getId());
            } else {
                return ResponseEntity.badRequest().body("Error processing payment: " + result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing payment");
        }
    }

    @GetMapping("/success")
    public String success(@RequestParam String transactionId) {
        try {
            if (transactionId == null || transactionId.isEmpty()) {
                return "Error processing payment: Transaction ID is missing";
            }
            return "Payment successful, ID: " + transactionId;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing payment: " + e.getMessage();
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        LoggerFactory.getLogger(RestBraintreeApi.class).info("Payment was canceled by the user.");
        return ResponseEntity.ok("Payment canceled! You can continue shopping.");
    }
}