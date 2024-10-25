package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findFeedbackByIsDeletedFalse();

    Payment findFeedbackById (long id);

    @Query("SELECT p FROM Payment p WHERE FUNCTION('MONTH', CAST(p.payment_date AS DATE)) = :month AND FUNCTION('YEAR', CAST(p.payment_date AS DATE)) = :year")
    List<Payment> findPaymentsByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT FUNCTION('MONTH', CAST(p.payment_date AS DATE)), FUNCTION('YEAR', CAST(p.payment_date AS DATE)) FROM Payment p")
    List<Object[]> findDistinctMonthsAndYears();

}

