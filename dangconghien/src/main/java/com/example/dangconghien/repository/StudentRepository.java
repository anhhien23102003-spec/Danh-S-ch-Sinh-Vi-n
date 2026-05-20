package com.example.dangconghien.repository;

import com.example.dangconghien.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 1. Lấy tất cả sinh viên sắp xếp theo ID tăng dần
    List<Student> findAllByOrderByIdAsc();

    // 2. Tìm kiếm theo tên (không phân biệt hoa thường)
    List<Student> findByNameContainingIgnoreCase(String name);

    // 3. Tìm kiếm theo tên hoặc email (không phân biệt hoa thường)
    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String name, String email);

    // 4. Kiểm tra email đã tồn tại
    boolean existsByEmail(String email);

    // 5. Kiểm tra email tồn tại nhưng không tính ID hiện tại
    boolean existsByEmailAndIdNot(String email, Integer id);

    // 6. Tìm kiếm nâng cao với JPQL
    @Query("SELECT s FROM Student s " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByName(@Param("keyword") String keyword);

    // 7. Tìm kiếm có phân trang
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword%")
    Page<Student> searchWithPaging(@Param("keyword") String keyword,
                                   Pageable pageable);
}
