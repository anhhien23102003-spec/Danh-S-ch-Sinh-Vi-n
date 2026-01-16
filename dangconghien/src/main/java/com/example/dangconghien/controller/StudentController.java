package com.example.dangconghien.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.dangconghien.entity.Student;
import com.example.dangconghien.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String listStudents(Model model) {

        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);

        return "students";
    }
}