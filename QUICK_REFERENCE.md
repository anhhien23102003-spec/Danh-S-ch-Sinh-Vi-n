# ⚙️ QUICK REFERENCE - CONSTANTS & VALIDATION RULES

## 🔢 VALIDATION CONSTANTS

### Name Validation
```
Minimum Length: 2 characters
Maximum Length: 100 characters
Allowed: Any Unicode characters
Examples:
  ✓ Nguyễn Văn A
  ✓ Trần Thị B
  ✓ Phan Van C
  ✗ A (too short)
  ✗ [long string >100] (too long)
```

### Age Validation
```
Minimum Age: 16 years
Maximum Age: 120 years
Type: Integer
Examples:
  ✓ 18
  ✓ 20
  ✓ 25
  ✗ 15 (too young)
  ✗ 150 (too old)
  ✗ 16.5 (not integer - will fail)
```

### Email Validation
```
Format: RFC-compliant email regex
Pattern: ^[A-Za-z0-9+_.-]+@(.+)$

Valid Examples:
  ✓ user@example.com
  ✓ test.email@test.co.uk
  ✓ user+tag@example.com
  ✓ test_123@company.co.jp
  ✓ a@b.com

Invalid Examples:
  ✗ invalid.email (no @)
  ✗ @example.com (no username)
  ✗ user@ (no domain)
  ✗ user@.com (no domain name)
  ✗ user @example.com (space)
```

### Gender Validation
```
Allowed Values: "Nam" OR "Nữ"
Type: String
Examples:
  ✓ Nam
  ✓ Nữ
  ✗ Male (wrong language)
  ✗ Female (wrong language)
  ✗ Other (not allowed)
  ✗ null (not allowed)
```

---

## 🎯 VALIDATION FLOW

### Adding New Student:
```
Input Received
    ↓
1. Controller Validation
    ├─ student != null
    ├─ Request mapping correct
    └─ Path variable valid
    ↓
2. Service Validation
    ├─ name: 2-100 chars
    ├─ age: 16-120 range
    ├─ gender: Nam/Nữ only
    ├─ email: regex pattern
    └─ all fields non-null
    ↓
3. Sanitization
    ├─ name.trim()
    ├─ email.toLowerCase().trim()
    └─ gender.trim()
    ↓
4. Business Rule Check
    └─ email NOT in database
    ↓
5. Database Save
    ├─ Database constraints
    └─ Transaction
    ↓
Result: Success or Error Message
```

### Updating Existing Student:
```
Input Received (with ID)
    ↓
1. Controller Validation
    ├─ student != null
    ├─ id > 0
    └─ record exists in DB
    ↓
2. Service Validation (same as above)
    ├─ name: 2-100 chars
    ├─ age: 16-120 range
    ├─ gender: Nam/Nữ only
    ├─ email: regex pattern
    └─ all fields non-null
    ↓
3. Sanitization (same as above)
    ↓
4. Business Rule Check
    └─ email NOT in OTHER records
       (allow same student to keep email)
    ↓
5. Database Update
    └─ Transaction
    ↓
Result: Success or Error Message
```

---

## 📧 EMAIL VALIDATION LOGIC

### New Student:
```java
if (isEmailExists(email, null)) {
    return "Email đã được dùng";
}
```
- Check if email exists anywhere in DB
- Cannot use taken email

### Existing Student Update:
```java
if (isEmailExists(email, studentId)) {
    return "Email đã được dùng";
}
```
- Check if email exists in OTHER records
- Can keep own email
- Cannot change to taken email

---

## 🔍 SEARCH FUNCTIONALITY

### What Can Be Searched:

**1. By Name:**
```
Keyword: "Trần"
Results: Trần Văn A, Trần Thị B, ...
Note: Case-insensitive
Note: Partial match (contains)
```

**2. By Email:**
```
Keyword: "gmail.com"
Results: user1@gmail.com, user2@gmail.com, ...
Note: Case-insensitive
Note: Partial match (contains)
```

**3. Both (Combined Search):**
```
Keyword: "Nguyễn"
Results: 
  - Students with "Nguyễn" in name
  - AND
  - Students with "Nguyễn" in email (if any)
```

### Search Logic:
```java
searchStudents(String keyword)
    ↓
if (keyword null or empty) {
    return getAllStudents();
}
    ↓
normalized = keyword.trim();
    ↓
return records WHERE
    name CONTAINS normalized (ignore case)
    OR
    email CONTAINS normalized (ignore case)
```

---

## 🎨 UI/UX CONSTANTS

### Color Palette:
```
Primary Gradient: #667eea → #764ba2 (Purple-Blue)
Success Gradient: #11998e → #38ef7d (Teal-Green)
Warning Gradient: #f6d365 → #fda085 (Amber-Orange)
Danger Gradient: #f5576c → #ff9a56 (Red-Orange)

Background: #f4f6f9 (Light Gray)
Card: #ffffff (White)
Text Primary: #2c3e50 (Dark Blue-Gray)
Text Secondary: #666666 (Gray)
Text Muted: #999999 (Light Gray)

Border: #e0e0e0 (Light Gray)
Badge Nam: Purple-Blue gradient
Badge Nữ: Pink-Red gradient
```

### Font Sizes:
```
Header (h1, h2): 1.8-2rem
Title (h3): 1.5rem
Normal Text (p): 0.95-1rem
Small Text: 0.85-0.9rem
Hint Text: 0.85rem
```

### Spacing:
```
Card Padding: 20-40px
Button Padding: 10-12px horizontal, 8-12px vertical
Input Padding: 12px 16px
Border Radius: 8-16px
Box Shadow: 0 4px 15px rgba(0,0,0,0.1)
```

---

## ⚡ PERFORMANCE CONSTANTS

### Timeouts:
```
Form Submission: No timeout (let server decide)
Search: Should complete <500ms
Page Load: Should complete <2s
Database Query: Should complete <100ms
```

### Limits:
```
Name Length: Max 100 characters
Email Length: Max 100 characters
Search Keyword: No limit (practical: <1000 chars)
Students Per Page: All (future: paginate at 50+)
```

---

## 🔐 SECURITY CONSTANTS

### SQL Injection Prevention:
```
Method: Spring Data JPA (Parameterized Queries)
Rule: Never concatenate user input
Pattern:
  ✓ findByNameContainingIgnoreCase(keyword)
  ✗ findByName("SELECT * FROM students")
```

### XSS Prevention:
```
Method: Thymeleaf Auto-Escaping
Example:
  User enters: <script>alert('hack')</script>
  Stored: <script>alert('hack')</script>
  Displayed: &lt;script&gt;alert('hack')&lt;/script&gt;
  Result: Shows as text, not executed
```

### Password/Sensitive Data:
```
Current: No passwords (future consideration)
Note: Never log sensitive data
Note: Never display exceptions with details
```

---

## 📱 RESPONSIVE BREAKPOINTS

### Mobile (< 768px):
```
View Type: Single Column
Button Layout: Stacked (100% width)
Table: Scrollable horizontally
Font Size: Readable (14-16px)
Touch Targets: Min 44px height
```

### Tablet (768px - 1024px):
```
View Type: 2 columns where applicable
Button Layout: Sometimes inline
Font Size: Normal
Spacing: Moderate
```

### Desktop (> 1024px):
```
View Type: Full layout
Button Layout: Inline (horizontal)
Max Width: 1200px
Font Size: Normal
Spacing: Generous
```

---

## 🚀 ERROR MESSAGES REFERENCE

### Validation Errors:
```
"Dữ liệu sinh viên không hợp lệ"
"Tên sinh viên phải từ 2-100 ký tự"
"Tuổi phải từ 16 đến 120 tuổi"
"Vui lòng chọn giới tính"
"Email không hợp lệ"
"Email đã được dùng"
```

### System Errors:
```
"Sinh viên không tồn tại"
"ID sinh viên không hợp lệ"
"Lỗi khi lưu dữ liệu: [exception message]"
"Lỗi khi xóa sinh viên"
```

### Success Messages:
```
"Thêm sinh viên thành công!"
"Cập nhật sinh viên thành công!"
"Xóa sinh viên [name] thành công!"
```

---

## 🔄 HTTP STATUS CODES

### Expected Status Codes:
```
GET /students → 200 OK
GET /students/add → 200 OK
GET /students/edit/{id} → 200 OK (or redirect if not found)
POST /students/save → 303 Redirect (to /students)
GET /students/delete/{id} → 303 Redirect (to /students)
GET /students/search → 200 OK
```

### Error Status:
```
Invalid ID → 404 Not Found (via redirect)
Database Error → 500 Internal Server Error
Bad Request → 400 Bad Request
Unauthorized → 401 Unauthorized (if auth added)
Forbidden → 403 Forbidden (if auth added)
```

---

## 📊 DATABASE CONSTRAINTS

### Student Table:
```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    
    -- Constraints:
    -- age >= 16 AND age <= 120 (app-level)
    -- gender IN ('Nam', 'Nữ') (app-level)
    -- email LIKE %@%.% (app-level + DB)
    -- email IS UNIQUE (DB-level)
)
```

---

## 🔧 CONFIGURATION PROPERTIES

### Application Properties (application.properties):
```properties
# Server
server.port=8080
server.servlet.context-path=/

# Database
spring.datasource.url=jdbc:sqlserver://[HOST]:[PORT];databaseName=[DB]
spring.datasource.username=[USER]
spring.datasource.password=[PASSWORD]

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect

# Thymeleaf
spring.thymeleaf.enabled=true
spring.thymeleaf.mode=HTML
```

---

## 🎯 TESTING CHECKLIST

| Component | Test Case | Expected Result |
|-----------|-----------|-----------------|
| Add Form | Valid input | ✓ Success message |
| Add Form | Invalid email | ✗ Error message |
| Add Form | Duplicate email | ✗ Error message |
| Add Form | Invalid age | ✗ Error message |
| Edit Form | Update valid | ✓ Success message |
| Edit Form | Change to dupe email | ✗ Error message |
| Delete | Confirm delete | ✓ Success message |
| Search | By name | ✓ Results |
| Search | By email | ✓ Results |
| Empty | No students | ✓ Empty state |

---

**This quick reference covers all validation and configuration! 📚**
