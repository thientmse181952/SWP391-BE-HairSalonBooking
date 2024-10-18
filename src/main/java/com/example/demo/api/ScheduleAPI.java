package com.example.demo.api;

import com.example.demo.entity.Schedule;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.ScheduleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleAPI {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule) {
        Schedule newSchedule = scheduleService.createSchedule(schedule);
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @Valid @RequestBody Schedule schedule) {
        try {
            Schedule updatedSchedule = scheduleService.updateSchedule(schedule, id);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return new ResponseEntity<>("Schedule deleted successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("Schedule not found", HttpStatus.NOT_FOUND);
        }
    }
}
