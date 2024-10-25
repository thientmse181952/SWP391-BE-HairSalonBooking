package com.example.demo.api;

import com.example.demo.entity.Category;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.Payment;
import com.example.demo.service.FeedbackService;
import com.example.demo.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@SecurityRequirement(name = "api")
public class FeedbackAPI {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/getAllFeedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackId(@PathVariable Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }

    @PostMapping("/createFeedback")
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback) {
        Feedback newFeedback = feedbackService.createFeedback(feedback);
        return ResponseEntity.ok(newFeedback );
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long feedbackId, @Valid @RequestBody Feedback feedback) {
        Feedback updateFeedback = feedbackService.updateFeedback(feedbackId, feedback);
        return ResponseEntity.ok(updateFeedback);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Feedback> deleteFeedback(@PathVariable long id) {
        Feedback feedback = feedbackService.deleteFeedback(id);
        return ResponseEntity.ok(feedback);
    }
}
