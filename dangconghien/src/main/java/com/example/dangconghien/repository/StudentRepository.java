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

    // 1. GET ALL - SẮP XẾP ID TĂNG DẦN
    List<Student> findAllByOrderByIdAsc();

    // 2. TÌM KIẾM THEO TÊN (Ignore Case)
    List<Student> findByNameContainingIgnoreCase(String name);

    // 3. KIỂM TRA EMAIL ĐÃ TỒN TẠI
    boolean existsByEmail(String email);

    // 4. JPQL tìm kiếm nâng cao
    @Query("SELECT s FROM Student s " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByName(@Param("keyword") String keyword);

    // 5. Tìm kiếm + phân trang
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword%")
    Page<Student> searchWithPaging(@Param("keyword") String keyword,
                                   Pageable pageable);
}
