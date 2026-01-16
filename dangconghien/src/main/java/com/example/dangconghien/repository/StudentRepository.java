package com.example.dangconghien.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dangconghien.entity.Student;
public interface StudentRepository 
        extends JpaRepository<Student, Integer> {
}
