package com.example.demo.repository;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findFeedbackByIsDeletedFalse();

    Feedback findFeedbackById (long id);
}
