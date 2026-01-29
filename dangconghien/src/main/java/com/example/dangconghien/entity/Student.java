package com.example.dangconghien.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    // Constructor rỗng (BẮT BUỘC)
    public Student() {
    }

    // Constructor đầy đủ
    public Student(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
