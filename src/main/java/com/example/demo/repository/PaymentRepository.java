package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findFeedbackByIsDeletedFalse();

    Payment findFeedbackById (long id);

}
