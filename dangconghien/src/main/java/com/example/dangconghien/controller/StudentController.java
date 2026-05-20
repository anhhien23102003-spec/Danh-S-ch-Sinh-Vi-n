package com.example.dangconghien.controller;

import com.example.dangconghien.entity.Student;
import com.example.dangconghien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Danh sách sinh viên với tìm kiếm
     * URL: /students
     */
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

        return "students";
    }

    /**
     * Form thêm sinh viên mới
     * URL: GET /students/add
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    /**
     * Form chỉnh sửa sinh viên
     * URL: GET /students/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Integer id,
            Model model) {

        // Kiểm tra ID hợp lệ
        if (id == null || id <= 0) {
            return "redirect:/students";
        }

        Student student = studentService.getStudentById(id);

        // Nếu sinh viên không tồn tại
        if (student == null) {
            return "redirect:/students";
        }

        model.addAttribute("student", student);
        return "student-form";
    }

    /**
     * Lưu sinh viên (thêm mới hoặc cập nhật)
     * URL: POST /students/save
     */
    @PostMapping("/save")
    public String saveStudent(
            @ModelAttribute("student") Student student,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra dữ liệu rỗng
        if (student == null) {
            model.addAttribute("error", "Dữ liệu sinh viên không hợp lệ");
            return "student-form";
        }

        // Xác thực dữ liệu
        String validationError = studentService.validateStudent(student);
        if (validationError != null) {
            model.addAttribute("error", validationError);
            model.addAttribute("student", student);
            return "student-form";
        }

        // Lưu sinh viên
        String saveError = studentService.saveStudent(student);
        
        if (saveError != null) {
            model.addAttribute("error", saveError);
            model.addAttribute("student", student);
            return "student-form";
        }

        // Thành công - redirect với thông báo
        if (student.getId() == null) {
            redirectAttributes.addFlashAttribute("success", "Thêm sinh viên thành công!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Cập nhật sinh viên thành công!");
        }

        return "redirect:/students";
    }

    /**
     * Xóa sinh viên
     * URL: GET /students/delete/{id}
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra ID hợp lệ
        if (id == null || id <= 0) {
            redirectAttributes.addFlashAttribute("error", "ID sinh viên không hợp lệ");
            return "redirect:/students";
        }

        // Kiểm tra sinh viên có tồn tại không
        Student student = studentService.getStudentById(id);
        if (student == null) {
            redirectAttributes.addFlashAttribute("error", "Sinh viên không tồn tại");
            return "redirect:/students";
        }

        // Xóa sinh viên
        boolean deleteSuccess = studentService.deleteStudent(id);
        
        if (deleteSuccess) {
            redirectAttributes.addFlashAttribute("success", 
                "Xóa sinh viên " + student.getName() + " thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi khi xóa sinh viên");
        }

        return "redirect:/students";
    }

    /**
     * Redirect từ students.html đến /students
     */
    @GetMapping("/students.html")
    public String redirectHtml() {
        return "redirect:/students";
    }
}
