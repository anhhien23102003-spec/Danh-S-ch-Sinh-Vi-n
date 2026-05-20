package com.example.dangconghien.service;

import com.example.dangconghien.entity.Student;
import com.example.dangconghien.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // EMAIL REGEX PATTERN
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 120;
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;

    /**
     * Lấy danh sách tất cả sinh viên theo ID tăng dần
     */
    public List<Student> getAllStudents() {
        return repository.findAllByOrderByIdAsc();
    }

    /**
     * Lấy sinh viên theo ID
     */
    public Student getStudentById(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        Optional<Student> optional = repository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Tìm kiếm sinh viên theo tên hoặc email
     */
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }
        String trimmedKeyword = keyword.trim();
        // Tìm theo tên hoặc email
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            trimmedKeyword, trimmedKeyword);
    }

    /**
     * Kiểm tra email hợp lệ
     */
    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.toLowerCase()).matches();
    }

    /**
     * Kiểm tra tuổi hợp lệ
     */
    private boolean isValidAge(Integer age) {
        return age != null && age >= MIN_AGE && age <= MAX_AGE;
    }

    /**
     * Kiểm tra tên hợp lệ
     */
    private boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String trimmed = name.trim();
        return trimmed.length() >= MIN_NAME_LENGTH && 
               trimmed.length() <= MAX_NAME_LENGTH;
    }

    /**
     * Làm sạch dữ liệu sinh viên (trim, lowercase email)
     */
    private void sanitizeStudent(Student student) {
        if (student != null) {
            if (student.getName() != null) {
                student.setName(student.getName().trim());
            }
            if (student.getEmail() != null) {
                student.setEmail(student.getEmail().trim().toLowerCase());
            }
            if (student.getGender() != null) {
                student.setGender(student.getGender().trim());
            }
        }
    }

    /**
     * Xác thực dữ liệu sinh viên
     * @return null nếu hợp lệ, nếu không trả về thông báo lỗi
     */
    public String validateStudent(Student student) {
        if (student == null) {
            return "Dữ liệu sinh viên không hợp lệ";
        }

        if (!isValidName(student.getName())) {
            return "Tên sinh viên phải từ 2-100 ký tự";
        }

        if (!isValidAge(student.getAge())) {
            return "Tuổi phải từ " + MIN_AGE + " đến " + MAX_AGE + " tuổi";
        }

        if (student.getGender() == null || 
            (!student.getGender().equals("Nam") && !student.getGender().equals("Nữ"))) {
            return "Vui lòng chọn giới tính";
        }

        if (!isValidEmail(student.getEmail())) {
            return "Email không hợp lệ";
        }

        return null; // Hợp lệ
    }

    /**
     * Kiểm tra email tồn tại (không tính email của chính sinh viên đó khi update)
     */
    public boolean isEmailExists(String email, Integer excludeId) {
        if (!isValidEmail(email)) {
            return false;
        }
        String normalizedEmail = email.trim().toLowerCase();
        
        if (excludeId != null) {
            // Khi update, kiểm tra xem email có tồn tại trong các sinh viên khác không
            return repository.existsByEmailAndIdNot(normalizedEmail, excludeId);
        } else {
            // Khi thêm mới
            return repository.existsByEmail(normalizedEmail);
        }
    }

    /**
     * Lưu sinh viên (thêm mới hoặc cập nhật)
     * @return null nếu lỗi, nếu không trả về thông báo lỗi
     */
    @Transactional
    public String saveStudent(Student student) {
        // Làm sạch dữ liệu
        sanitizeStudent(student);

        // Xác thực
        String validationError = validateStudent(student);
        if (validationError != null) {
            return validationError;
        }

        // Kiểm tra email trùng (không tính email của chính sinh viên đó khi update)
        if (isEmailExists(student.getEmail(), student.getId())) {
            return "Email này đã được sử dụng";
        }

        try {
            repository.save(student);
            return null; // Thành công
        } catch (Exception e) {
            return "Lỗi khi lưu dữ liệu: " + e.getMessage();
        }
    }

    /**
     * Xóa sinh viên
     */
    @Transactional
    public boolean deleteStudent(Integer id) {
        if (id == null || id <= 0) {
            return false;
        }

        if (!repository.existsById(id)) {
            return false;
        }

        try {
            repository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Đếm tổng số sinh viên
     */
    public long countTotalStudents() {
        return repository.count();
    }

    /**
     * Cập nhật sinh viên một phần
     */
    @Transactional
    public String updateStudent(Integer id, Student newStudent) {
        Student oldStudent = getStudentById(id);
        if (oldStudent == null) {
            return "Sinh viên không tồn tại";
        }

        sanitizeStudent(newStudent);
        oldStudent.setName(newStudent.getName());
        oldStudent.setAge(newStudent.getAge());
        oldStudent.setGender(newStudent.getGender());
        oldStudent.setEmail(newStudent.getEmail());

        return saveStudent(oldStudent);
    }
}
