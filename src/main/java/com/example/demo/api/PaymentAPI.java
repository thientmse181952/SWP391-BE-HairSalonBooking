package com.example.demo.api;

import com.example.demo.config.VNPayConfig;
import com.example.demo.entity.Payment;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.model.PaymentRequest;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@RestController
@RequestMapping("/api/payment")
@SecurityRequirement(name = "api")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private VNPayConfig vnPayConfig;

    @GetMapping("/getAllPayment")
    public ResponseEntity<List<Payment>> getAllPayment() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getPaymentId(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/createPayment")
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentRequest payment) {
        Payment newPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(newPayment);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long paymentId, @Valid @RequestBody Payment payment) {
        Payment updatePayment = paymentService.updatePayment(paymentId, payment);
        return ResponseEntity.ok(updatePayment);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Payment> deleteService(@PathVariable long id) {
        Payment payment = paymentService.deleteSchedule(id);
        return ResponseEntity.ok(payment);
    }


    @PostMapping("/payment-vnpay")
    public String createPayment(@RequestParam("amount") long amount,
                                @RequestParam("orderInfo") String orderInfo) throws UnsupportedEncodingException {
        String paymentUrl = paymentService.createPaymentUrl(amount, orderInfo);
        return paymentUrl;
    }

    @GetMapping("/payment-callback")
    public ResponseEntity<String> paymentCallback(@RequestParam Map<String, String> queryParams) {
        StringBuilder response = new StringBuilder();

        // Log all received parameters for debugging
        response.append("Received parameters:\n");
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            response.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Check if vnp_SecureHash is present
        if (!queryParams.containsKey("vnp_SecureHash")) {
            response.append("Error: Missing vnp_SecureHash\n");
            return ResponseEntity.badRequest().body(response.toString());
        }

        String vnp_SecureHash = queryParams.get("vnp_SecureHash");

        // Remove vnp_SecureHash from queryParams
        queryParams.remove("vnp_SecureHash");

        // Sort queryParams
        Map<String, String> sortedQueryParams = new TreeMap<>(queryParams);

        // Create hashData StringBuilder from sortedQueryParams
        StringBuilder hashData = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : sortedQueryParams.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    hashData.append(entry.getKey()).append("=")
                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()))
                            .append("&");
                }
            }
        } catch (UnsupportedEncodingException e) {
            response.append("Error: ").append(e.getMessage()).append("\n");
            return ResponseEntity.internalServerError().body(response.toString());
        }

        // Remove last '&' character
        if (hashData.length() > 0) {
            hashData.setLength(hashData.length() - 1);
        }

        String secureHash = paymentService.hmacSHA512(vnPayConfig.getHashSecret(), hashData.toString());

        if (secureHash.equals(vnp_SecureHash)) {
            // Valid signature
            String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
            if ("00".equals(vnp_ResponseCode)) {
                // Payment successful
                response.append("Payment successful\n");
            } else {
                // Payment failed
                response.append("Payment failed. Response Code: ").append(vnp_ResponseCode).append("\n");
            }
        } else {
            // Invalid signature
            response.append("Error: Invalid signature\n");
        }

        return ResponseEntity.ok(response.toString());
    }
}