package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.ServiceofStylist;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.BookingRequest;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.HairServiceRepository;
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

    @Autowired
    HairServiceRepository hairServiceRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + bookingId));
    }

    public Booking createBooking(BookingRequest bookingRequest) {
        Booking booking = modelMapper.map(bookingRequest, Booking.class);

        Set<ServiceofStylist> serviceofStylists = new HashSet<>();
        for(Long idService : bookingRequest.getService_id()){
            ServiceofStylist serviceofStylist = hairServiceRepository.findById(idService).orElseThrow(() -> new NotFoundException("Service not found"));
            serviceofStylists.add(serviceofStylist);
        }

        booking.setServiceofStylists(serviceofStylists);
        booking.setAppointmentDate(bookingRequest.getAppointmentDate());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());
        booking.setStatus(bookingRequest.getStatus());



        try{
            Booking newbooking1 = bookingRepository.save(booking);
            return newbooking1;
        }catch(Exception e){
            throw new DuplicateEntity("Duplicate stylist found");
        }
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
    public Booking updateBookingStatus(Long bookingId, String newStatus) {
        Booking existingBooking = getBookingById(bookingId);
        existingBooking.setStatus(newStatus);
        return bookingRepository.save(existingBooking);
    }
    public void deleteBooking(Long bookingId) {
        Booking existingBooking = getBookingById(bookingId);
        bookingRepository.delete(existingBooking);
    }

}