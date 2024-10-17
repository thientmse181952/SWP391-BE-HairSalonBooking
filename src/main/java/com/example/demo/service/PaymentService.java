package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Payment;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.BookingRequest;
import com.example.demo.model.PaymentRequest;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookingRepository bookingRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + paymentId));
    }

    public Payment createPayment(PaymentRequest paymentRequest) {
        Payment payment = modelMapper.map(paymentRequest, Payment.class);

        Set<Booking> bookings = new HashSet<>();
        for(Long idService : paymentRequest.getBooking_id()){
            Booking booking = bookingRepository.findById(idService).orElseThrow(() -> new NotFoundException("Service not found"));
            bookings.add(booking);
        }

        payment.setBookings(bookings);


        try{
            Payment newPayment = paymentRepository.save(payment);
            return newPayment;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate stylist found");
        }
    }
    public Payment updatePayment(long paymentId, Payment updatedPayment) {
        Payment newPayment = getPaymentById(paymentId);
        newPayment.setPayment_type(updatedPayment.getPayment_type());
        newPayment.setAmount(updatedPayment.getAmount());
        newPayment.setPayment_date(updatedPayment.getPayment_date());
        newPayment.setCustomer(updatedPayment.getCustomer());
        newPayment.setBookings(updatedPayment.getBookings());


//        Booking existingBooking = getBookingById(bookingId);
//        existingBooking.setStylist(updatedBooking.getStylist());
//        existingBooking.setCustomer(updatedBooking.getCustomer());
////        existingBooking.setSchedule(updatedBooking.getSchedule());
//        existingBooking.setAppointmentDate(updatedBooking.getAppointmentDate());
//        existingBooking.setStartTime(updatedBooking.getStartTime());
//        existingBooking.setEndTime(updatedBooking.getEndTime());
//        existingBooking.setStatus(updatedBooking.getStatus());
        return paymentRepository.save(newPayment);
    }

//    public void deletePayment(Long bookingId) {
//        Booking existingBooking = getBookingById(bookingId);
//        paymentRepository.delete(existingBooking);
//    }
}