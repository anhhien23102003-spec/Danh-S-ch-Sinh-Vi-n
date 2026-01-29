package com.example.dangconghien.service;

import com.example.dangconghien.entity.Student;
import com.example.dangconghien.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // 1. LẤY DANH SÁCH SINH VIÊN (ID TĂNG DẦN)
    public List<Student> getAllStudents() {
        return repository.findAllByOrderByIdAsc();
    }

    // =========================
    // 2. LẤY SINH VIÊN THEO ID
    // =========================
    public Student getStudentById(Integer id) {
        Optional<Student> optional = repository.findById(id);
        return optional.orElse(null);
    }

    // =========================
    // 3. TÌM KIẾM SINH VIÊN THEO TÊN
    // =========================
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        return repository.findByNameContainingIgnoreCase(keyword.trim());
    }

    // =========================
    // 4. THÊM MỚI SINH VIÊN
    // =========================
    public Student addStudent(Student student) {
        return repository.save(student);
    }

    // =========================
    // 5. CẬP NHẬT SINH VIÊN
    // =========================
    public Student updateStudent(Integer id, Student newStudent) {

        Student oldStudent = getStudentById(id);

        if (oldStudent == null) {
            return null;
        }

        oldStudent.setName(newStudent.getName());
        oldStudent.setAge(newStudent.getAge());
        oldStudent.setEmail(newStudent.getEmail());

        return repository.save(oldStudent);
    }

    // =========================
    // 6. SAVE DÙNG CHUNG (ADD + UPDATE)
    // =========================
    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    // =========================
    // 7. XÓA SINH VIÊN
    // =========================
    public void deleteStudent(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    // =========================
    // 8. KIỂM TRA EMAIL TỒN TẠI
    // =========================
    public boolean isEmailExists(String email) {
        return repository.existsByEmail(email);
    }
}
