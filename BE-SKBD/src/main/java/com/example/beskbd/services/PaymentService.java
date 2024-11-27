package com.example.beskbd.services;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final BraintreeGateway braintreeGateway;

    @Autowired
    public PaymentService(BraintreeGateway braintreeGateway) {
        this.braintreeGateway = braintreeGateway;
    }

    public Result<Transaction> processPayment(String nonceFromTheClient, BigDecimal amount) {
        // Create a transaction request
        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .paymentMethodNonce(nonceFromTheClient)
                .options()
                .submitForSettlement(true)
                .done();

        // Process the payment
        Result<Transaction> result = braintreeGateway.transaction().sale(request);

        // Handle the result
        if (result.isSuccess()) {
            return result; // Payment was successful
        } else {
            // Log error details if needed
            System.err.println("Payment failed: " + result.getMessage());
            return result; // Return the result with error details
        }
    }
}