CREATE DATABASE school;
GO

USE school;
GO

IF OBJECT_ID('students', 'U') IS NOT NULL
    DROP TABLE students;
GO

CREATE TABLE students (
    id INT IDENTITY(1,1) PRIMARY KEY,

    student_code VARCHAR(20) NOT NULL UNIQUE,

    name NVARCHAR(100) NOT NULL,

    age INT NOT NULL
        CHECK (age >= 0 AND age <= 120),

    gender NVARCHAR(10) NOT NULL
        CHECK (gender IN (N'Nam', N'Nữ')),

    email NVARCHAR(100) NOT NULL UNIQUE
);
GO

CREATE INDEX idx_students_name
ON students(name);
GO

INSERT INTO students
(student_code, name, age, gender, email)
VALUES
('SV2026001', N'Nguyễn Văn An', 20, N'Nam', 'an.nguyen@student.edu.vn'),

('SV2026002', N'Trần Thị Bình', 21, N'Nữ', 'binh.tran@student.edu.vn'),

('SV2026003', N'Lê Hoàng Cường', 22, N'Nam', 'cuong.le@student.edu.vn'),

('SV2026004', N'Phạm Thị Dung', 20, N'Nữ', 'dung.pham@student.edu.vn'),

('SV2026005', N'Võ Minh Đức', 23, N'Nam', 'duc.vo@student.edu.vn');
GO

SELECT * FROM students;
GO