package com.example.beskbd;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.example.beskbd.rest.RestBraintreeApi;
import com.example.beskbd.services.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RestBraintreeApiTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private RestBraintreeApi restBraintreeApi;

    private Logger mockLogger = mock(Logger.class);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // If you need to use a logger, set it up here if necessary
        // e.g., when(LoggerFactory.getLogger(any())).thenReturn(mockLogger);
    }

    @Test
    public void test_process_payment_success() {
        String paymentNonce = "valid-nonce";
        Double amount = 100.0;
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);

        Transaction mockTransaction = mock(Transaction.class);
        when(mockTransaction.getId()).thenReturn("test-transaction-id");

        Result<Transaction> mockResult = mock(Result.class);
        when(mockResult.isSuccess()).thenReturn(true);
        when(mockResult.getTarget()).thenReturn(mockTransaction);

        when(paymentService.processPayment(paymentNonce, amountDecimal)).thenReturn(mockResult);

        ResponseEntity<String> response = restBraintreeApi.processPayment(paymentNonce, amount);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment successful, ID: test-transaction-id", response.getBody());
        verify(paymentService).processPayment(paymentNonce, amountDecimal);
    }

    @Test
    public void test_process_payment_invalid_nonce() {
        String invalidNonce = "invalid-nonce";
        Double amount = 100.0;
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);

        Result<Transaction> mockResult = mock(Result.class);
        when(mockResult.isSuccess()).thenReturn(false);
        when(mockResult.getMessage()).thenReturn("Invalid payment method nonce");

        when(paymentService.processPayment(invalidNonce, amountDecimal)).thenReturn(mockResult);

        ResponseEntity<String> response = restBraintreeApi.processPayment(invalidNonce, amount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error processing payment: Invalid payment method nonce", response.getBody());
        verify(paymentService).processPayment(invalidNonce, amountDecimal);
    }

    @Test
    public void test_cancel_endpoint_returns_200_ok() {
        ResponseEntity<String> response = restBraintreeApi.cancel();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment canceled! You can continue shopping.", response.getBody());
    }

    @Test
    public void test_success_endpoint_with_valid_transaction_id() {
        String transactionId = "valid-transaction-id";
        String response = restBraintreeApi.success(transactionId);

        assertEquals("Payment successful, ID: " + transactionId, response);
    }

    @Test
    public void test_success_endpoint_with_null_or_empty_transaction_id() {
        String responseNull = restBraintreeApi.success(null);
        assertEquals("Error processing payment: Transaction ID is missing", responseNull);

        String responseEmpty = restBraintreeApi.success("");
        assertEquals("Error processing payment: Transaction ID is missing", responseEmpty);
    }

    @Test
    public void test_process_payment_service_exception() {
        String paymentNonce = "invalid-nonce";
        Double amount = 100.0;
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);

        when(paymentService.processPayment(paymentNonce, amountDecimal)).thenThrow(new RuntimeException("Service exception"));

        ResponseEntity<String> response = restBraintreeApi.processPayment(paymentNonce, amount);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error processing payment", response.getBody());
        verify(paymentService).processPayment(paymentNonce, amountDecimal);
    }

    @Test
    public void test_process_payment_with_zero_or_negative_amount() {
        String paymentNonce = "valid-nonce";
        Double zeroAmount = 0.0;
        Double negativeAmount = -10.0;

        ResponseEntity<String> zeroAmountResponse = restBraintreeApi.processPayment(paymentNonce, zeroAmount);
        ResponseEntity<String> negativeAmountResponse = restBraintreeApi.processPayment(paymentNonce, negativeAmount);

        assertEquals(HttpStatus.BAD_REQUEST, zeroAmountResponse.getStatusCode());
        assertEquals("Error processing payment: Amount must be greater than zero", zeroAmountResponse.getBody());

        assertEquals(HttpStatus.BAD_REQUEST, negativeAmountResponse.getStatusCode());
        assertEquals("Error processing payment: Amount must be greater than zero", negativeAmountResponse.getBody());
    }
    @Test
    public void test_process_payment_failure() {
        String paymentNonce = "invalid-nonce";
        Double amount = 100.0;
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);

        Result<Transaction> mockResult = mock(Result.class);
        when(mockResult.isSuccess()).thenReturn(false);
        when(mockResult.getMessage()).thenReturn("Invalid payment method nonce.");

        when(paymentService.processPayment(paymentNonce, amountDecimal)).thenReturn(mockResult);

        ResponseEntity<String> response = restBraintreeApi.processPayment(paymentNonce, amount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error processing payment: Invalid payment method nonce.", response.getBody());
        verify(paymentService).processPayment(paymentNonce, amountDecimal);
    }
}