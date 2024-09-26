package com.example.demo.api;


import com.example.demo.entity.Student;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentAPI {


    //Thêm sinh viên mới
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Student student) {
        //Thêm student mới
        return null;
    }

    //Get danh sách
    @GetMapping
    public ResponseEntity get() {
        return null;
    }


}
