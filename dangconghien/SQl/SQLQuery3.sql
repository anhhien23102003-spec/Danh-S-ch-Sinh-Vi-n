CREATE DATABASE school;
GO

USE school;
GO

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('students', 'U') IS NOT NULL
    DROP TABLE students;
GO

-- Tạo bảng students (ĐẦY ĐỦ)
CREATE TABLE students (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 0 AND age <= 120),
    gender NVARCHAR(10) NOT NULL CHECK (gender IN (N'Nam', N'Nữ')),
    email NVARCHAR(100) NOT NULL UNIQUE
);
GO

-- Tạo index tìm kiếm theo tên
CREATE INDEX idx_students_name ON students(name);
GO

-- Thêm dữ liệu mẫu (CÓ GIỚI TÍNH)
INSERT INTO students (name, age, gender, email) VALUES
(N'Nguyễn Văn An', 20, N'Nam', 'an.nguyen@student.edu.vn'),
(N'Trần Thị Bình', 21, N'Nữ', 'binh.tran@student.edu.vn'),
(N'Lê Hoàng Cường', 22, N'Nam', 'cuong.le@student.edu.vn'),
(N'Phạm Thị Dung', 20, N'Nữ', 'dung.pham@student.edu.vn'),
(N'Võ Minh Đức', 23, N'Nam', 'duc.vo@student.edu.vn');
GO

-- Kiểm tra dữ liệu
SELECT * FROM students;

