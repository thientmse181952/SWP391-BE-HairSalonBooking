package com.example.demo.api;

import com.example.demo.entity.Payment;
import com.example.demo.model.PaymentRequest;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@SecurityRequirement(name = "api")
public class PaymentAPI {

    @Autowired
    private PaymentService paymentService;

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

//    @DeleteMapping("/{bookingId}")
//    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {
//        bookingService.deleteBooking(bookingId);
//        return ResponseEntity.ok("Booking deleted successfully.");
//    }
}