//package com.example.beskbd;
//
//
//import com.example.beskbd.rest.PaypalController;
//import com.example.beskbd.services.PaypalService;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//@ExtendWith(MockitoExtension.class)
//public class PaypalAPITest {
//
//        @Test
//        public void test_successful_payment_execution() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            Payment mockPayment = new Payment();
//            mockPayment.setId("PAY-123456789");
//
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-123")).thenReturn(mockPayment);
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-123");
//            Assertions.assertEquals("Payment success, ID: PAY-123456789", result);
//
//        }
//
//        // PaymentId is null or empty
//        @Test
//        public void test_payment_id_null_or_empty() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String resultNull = paypalController.success(null, "PAYER-123");
//            Assertions.assertEquals("Error processing payment", resultNull);
//
//            String resultEmpty = paypalController.success("", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", resultEmpty);
//        }
//
//        // Returns success message with payment ID on successful execution
//        @Test
//        public void test_success_message_with_payment_id() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            Payment mockPayment = new Payment();
//            mockPayment.setId("PAY-123456789");
//
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-123")).thenReturn(mockPayment);
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-123");
//            Assertions.assertEquals("Payment success, ID: PAY-123456789", result);
//
//        }
//
//        // Handles PayPalRESTException gracefully and returns error message
//        @Test
//        public void test_payment_execution_exception_handling() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-123"))
//                        .thenThrow(new PayPalRESTException("Error processing payment"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // PayerID is null or empty
//        @Test
//        public void test_success_with_null_or_empty_payer_id() throws PayPalRESTException {
//            // Mock the PaypalService
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            // Create a dummy Payment object to return when executePayment is called
//            Payment mockPayment = Mockito.mock(Payment.class);
//
//            // Use lenient stubbing to allow executePayment to be called with any arguments
//            Mockito.lenient()
//                    .when(paypalService.executePayment(Mockito.anyString(), Mockito.anyString()))
//                    .thenReturn(mockPayment);
//
//            // Initialize the PaypalController and inject the mocked service
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            // Test with null payer ID
//            String resultWithNullPayerID = paypalController.success("PAY-123456789", null);
//            Assertions.assertEquals("Error processing payment", resultWithNullPayerID);
//
//            // Test with empty payer ID
//            String resultWithEmptyPayerID = paypalController.success("PAY-123456789", "");
//            Assertions.assertEquals("Error processing payment", resultWithEmptyPayerID);
//        }
//
//        // PaymentId does not exist or is invalid
//        @Test
//        public void test_invalid_payment_id() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            try {
//                Mockito.when(paypalService.executePayment("INVALID-PAYMENT-ID", "PAYER-123"))
//                        .thenThrow(new PayPalRESTException("Invalid Payment ID"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("INVALID-PAYMENT-ID", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // PayPalRESTException is thrown due to network issues
//        @Test
//        public void test_payment_execution_network_issue() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-123"))
//                        .thenThrow(new PayPalRESTException("Network error"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // PayerID does not match the paymentId
//        @Test
//        public void test_payer_id_does_not_match_payment_id() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-999")).thenThrow(new PayPalRESTException("PayerID does not match"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-999");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // Logs error details when PayPalRESTException occurs
//        @Test
//        public void test_logs_error_on_paypalrestexception() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            try {
//                Mockito.when(paypalService.executePayment("PAY-123456789", "PAYER-123"))
//                        .thenThrow(new PayPalRESTException("Error processing payment"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-123456789", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // Payment execution is attempted with invalid API credentials
//        @Test
//        public void test_payment_execution_with_invalid_credentials() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//
//            try {
//                Mockito.when(paypalService.executePayment("INVALID-PAYMENT-ID", "INVALID-PAYER-ID"))
//                        .thenThrow(new PayPalRESTException("Invalid API credentials"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("INVALID-PAYMENT-ID", "INVALID-PAYER-ID");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // Payment execution is attempted with unsupported currency
//        @Test
//        public void test_payment_execution_with_unsupported_currency() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            try {
//                Mockito.when(paypalService.executePayment("PAY-UNSUPPORTED", "PAYER-123"))
//                        .thenThrow(new PayPalRESTException("Unsupported currency"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("PAY-UNSUPPORTED", "PAYER-123");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//
//        // Payment execution is attempted with incorrect payment method
//        @Test
//        public void test_payment_execution_with_incorrect_method() {
//            PaypalService paypalService = Mockito.mock(PaypalService.class);
//            try {
//                Mockito.when(paypalService.executePayment("INVALID-PAYMENT-ID", "INVALID-PAYER-ID"))
//                        .thenThrow(new PayPalRESTException("Invalid payment method"));
//            } catch (PayPalRESTException e) {
//                e.printStackTrace();
//            }
//
//            PaypalController paypalController = new PaypalController();
//            ReflectionTestUtils.setField(paypalController, "paypalService", paypalService);
//
//            String result = paypalController.success("INVALID-PAYMENT-ID", "INVALID-PAYER-ID");
//            Assertions.assertEquals("Error processing payment", result);
//        }
//    }
//// NO NEED TO USE PAYPAL ANYMORE
