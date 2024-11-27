//package com.example.beskbd.rest;
//
//import com.example.beskbd.services.PaypalService;
//import com.paypal.api.payments.Links;
//import com.paypal.api.payments.Payment;
//import com.paypal.base.rest.PayPalRESTException;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
////da test ok
//@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RequestMapping("/paypal")
//public class PaypalController {
//    @Autowired
//    private PaypalService paypalService;
//
//    @PostMapping("/pay")
//    public String pay(@RequestParam Double amount) {
//        try {
//            String cancelUrl = "http://localhost:8083/paypal/cancel";
//            String successUrl = "http://localhost:8083/paypal/success";
//            Payment payment = paypalService.createPayment(amount, "USD", "paypal", "sale",
//                    "Payment description", cancelUrl, successUrl);
//            return payment.getLinks().stream()
//                    .filter(link -> "approval_url".equals(link.getRel()))
//                    .findFirst()
//                    .map(Links::getHref)
//                    .orElse("No approval URL found");
//        } catch (PayPalRESTException e) {
//            e.printStackTrace();
//            return "Error processing payment";
//        }
//    }
//    @GetMapping("/success")
//    public String success(String paymentId, String payerID) {
//        try {
//            if (payerID == null || payerID.isEmpty()) {
//                return "Error processing payment";
//            }
//            Payment payment = paypalService.executePayment(paymentId, payerID);
//            if (payment == null) {  // Check if payment is null
//                return "Error processing payment";
//            }
//            return "Payment success, ID: " + payment.getId();
//        } catch (Exception e) {
//            return "Error processing payment";
//        }
//    }
//
//
//    @GetMapping("/cancel")
//    public ResponseEntity<String> cancel() {
//        LoggerFactory.getLogger(PaypalController.class).info("Payment was canceled by the user.");
//        return ResponseEntity.ok("Payment canceled! You can continue shopping.");
//    }
//}
// NO NEED TO USE PAYPAL ANYMORE
