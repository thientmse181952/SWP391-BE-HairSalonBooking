package com.example.demo.service;

import com.example.demo.entity.Feedback;
import com.example.demo.entity.Payment;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + feedbackId));
    }

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(long feebackId, Feedback updatedFeedback) {
        Feedback newFeedback = getFeedbackById(feebackId);
        newFeedback.setComment(updatedFeedback.getComment());
        newFeedback.setRating_stylist(updatedFeedback.getRating_stylist());
        newFeedback.setCustomer(updatedFeedback.getCustomer());
        newFeedback.setStylist(updatedFeedback.getStylist());



//        Booking existingBooking = getBookingById(bookingId);
//        existingBooking.setStylist(updatedBooking.getStylist());
//        existingBooking.setCustomer(updatedBooking.getCustomer());
////        existingBooking.setSchedule(updatedBooking.getSchedule());
//        existingBooking.setAppointmentDate(updatedBooking.getAppointmentDate());
//        existingBooking.setStartTime(updatedBooking.getStartTime());
//        existingBooking.setEndTime(updatedBooking.getEndTime());
//        existingBooking.setStatus(updatedBooking.getStatus());
        return feedbackRepository.save(newFeedback);
    }

//    public void deletePayment(Long bookingId) {
//        Booking existingBooking = getBookingById(bookingId);
//        paymentRepository.delete(existingBooking);
//    }
}