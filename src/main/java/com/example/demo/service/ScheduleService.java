package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.entity.Schedule;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.ScheduleRequest;
import com.example.demo.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    ModelMapper modelMapper;

    public Schedule createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule1 = modelMapper.map(scheduleRequest, Schedule.class);
        Schedule schedule = scheduleRepository.save(schedule1);
        schedule.setStylist(schedule1.getStylist());
        schedule.setStatus(schedule1.getStatus());
        schedule.setReason(schedule1.getReason());
        schedule.setStartTime(schedule1.getStartTime());
        schedule.setEndTime(schedule1.getEndTime());


        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleId(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Booking not found with ID: " + scheduleId));
    }

    public Schedule updateSchedule(Schedule schedule, Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        existingSchedule.setStylist(schedule.getStylist());
        existingSchedule.setReason(schedule.getReason());
        existingSchedule.setStatus(schedule.getStatus());
        existingSchedule.setStartTime(schedule.getStartTime());
        existingSchedule.setEndTime(schedule.getEndTime());



        return scheduleRepository.save(existingSchedule);
    }

    public Schedule updateScheduleStatus(Long scheduleId, String newStatus) {
        Schedule existingBooking = getScheduleId(scheduleId);
        existingBooking.setStatus(newStatus);
        return scheduleRepository.save(existingBooking);
    }

    public Schedule deleteSchedule(long scheduleId) {
        Schedule booking = scheduleRepository.findFeedbackById(scheduleId);
        if (booking == null) {
            throw new NotFoundException("Schedule not found");
        }
        booking.setDeleted(true);
        return scheduleRepository.save(booking);
    }
}
