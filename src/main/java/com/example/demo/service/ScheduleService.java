package com.example.demo.service;

import com.example.demo.entity.Schedule;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;


    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule updateSchedule(Schedule schedule, Long scheduleId) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        existingSchedule.setStylist(schedule.getStylist());
        existingSchedule.setService(schedule.getService());
        existingSchedule.setAppointmentDate(schedule.getAppointmentDate());
        existingSchedule.setStartTime(schedule.getStartTime());
        existingSchedule.setEndTime(schedule.getEndTime());
        existingSchedule.setStatus(schedule.getStatus());

        return scheduleRepository.save(existingSchedule);
    }

    public void deleteSchedule(Long scheduleId) {
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new NotFoundException("Schedule not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
