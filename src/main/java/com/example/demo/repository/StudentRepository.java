package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //customize
    //đặt tên function theo định dạng DataJPA
    //findStudentById(long id)

    Student findStudentById(long id);
}
