package com.example.demo.service;


import com.example.demo.entity.Student;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    //Create
    public Student create(Student student) {
        Student newStudent = studentRepository.save(student);
        return newStudent;
    }



    //Read
    public List<Student> getAllStudent() {
        List<Student> students = studentRepository.findAll();
        return students;
    }



    //Update
    public Student update(long id, Student student) {
        //Tìm student cần được update
        Student oldStudent = studentRepository.findStudentById();
        if(oldStudent == null) throw new EntityNotFoundException("Student not found!"); {

        }

        //Cập nhật thông tin


        //lưu xuống db
    }



    //Delete


}
