package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerRepository customerRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
    }

    public Booking createBooking(Booking bookingRequest) {
        Booking newbooking = modelMapper.map(bookingRequest, Booking.class);

        return bookingRepository.save(newbooking);
    }

    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
        Booking existingBooking = getBookingById(bookingId);
        existingBooking.setStylist(updatedBooking.getStylist());
        existingBooking.setCustomer(updatedBooking.getCustomer());
//        existingBooking.setSchedule(updatedBooking.getSchedule());
        existingBooking.setAppointmentDate(updatedBooking.getAppointmentDate());
        existingBooking.setStartTime(updatedBooking.getStartTime());
        existingBooking.setEndTime(updatedBooking.getEndTime());
        existingBooking.setStatus(updatedBooking.getStatus());
        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(Long bookingId) {
        Booking existingBooking = getBookingById(bookingId);
        bookingRepository.delete(existingBooking);
    }
}