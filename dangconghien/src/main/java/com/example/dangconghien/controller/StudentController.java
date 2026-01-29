package com.example.dangconghien.controller;

import com.example.dangconghien.entity.Student;
import com.example.dangconghien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // =========================
    // 1. DANH SÁCH + TÌM KIẾM
    // URL: /students
    // =========================
    @GetMapping
    public String listStudents(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchStudents(keyword);
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);

        return "students"; // students.html
    }

    // =========================
    // 2. FORM THÊM SINH VIÊN
    // URL: /students/add
    // =========================
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form"; // student-form.html
    }

    // =========================
    // 3. LƯU SINH VIÊN
    // (DÙNG CHUNG CHO ADD + UPDATE)
    // =========================
    @PostMapping("/save")
    public String saveStudent(
            @ModelAttribute("student") Student student,
            Model model) {

        // Chỉ kiểm tra email trùng khi THÊM MỚI
        if (student.getId() == null &&
                studentService.isEmailExists(student.getEmail())) {

            model.addAttribute("error", "Email đã tồn tại!");
            return "student-form";
        }

        studentService.saveStudent(student);
        return "redirect:/students";
    }

    // =========================
    // 4. FORM SỬA SINH VIÊN
    // URL: /students/edit/{id}
    // =========================
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Integer id,
            Model model) {

        Student student = studentService.getStudentById(id);

        if (student == null) {
            return "redirect:/students";
        }

        model.addAttribute("student", student);
        return "student-form";
    }

    // =========================
    // 5. XÓA SINH VIÊN
    // URL: /students/delete/{id}
    // =========================
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // =========================
    // 6. REDIRECT students.html
    // =========================
    @GetMapping("/students.html")
    public String redirectHtml() {
        return "redirect:/students";
    }
}
