# 📐 KIẾN TRÚC HỆ THỐNG - TRƯỚC & SAU

## TRƯỚC (Cũ)

```
┌──────────────────────────────────────────────────────────┐
│                    BROWSER (UI)                          │
│                                                          │
│  - Bootstrap basic template                             │
│  - Simple form inputs                                   │
│  - No validation hints                                  │
│  - No success/error messages                            │
│  - Not responsive                                       │
└────────────────────┬─────────────────────────────────────┘
                     │ HTTP Request
                     ▼
┌──────────────────────────────────────────────────────────┐
│              SPRING BOOT CONTROLLER                      │
│                                                          │
│  - Basic request handling                               │
│  - Email check only for new records                     │
│  - No detailed validation                               │
│  - Simple redirect                                      │
│  - No error messages to display                         │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│              SERVICE LAYER                               │
│                                                          │
│  - Direct save() call                                   │
│  - No validation                                        │
│  - No data sanitization                                 │
│  - No error handling                                    │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│            DATA ACCESS (Repository)                      │
│                                                          │
│  - Basic CRUD operations                                │
│  - Only search by name                                  │
│  - Simple email exists check                            │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│               SQL SERVER DATABASE                        │
└──────────────────────────────────────────────────────────┘
```

**Vấn Đề:**
- ❌ Không validate input
- ❌ Không sanitize data
- ❌ Không có user feedback
- ❌ Lỗi email update không check exclude ID
- ❌ UI không modern

---

## SAU (Hiện Đại)

```
┌──────────────────────────────────────────────────────────┐
│                  BROWSER (Modern UI)                     │
│                                                          │
│  ✨ Gradient backgrounds                                │
│  ✨ Animated components                                 │
│  ✨ Inline validation hints                             │
│  ✨ Real-time error display                             │
│  ✨ Success/Error toast alerts                          │
│  ✨ Fully responsive design                             │
│  ✨ Touch-friendly mobile UI                            │
└────────────────────┬─────────────────────────────────────┘
                     │ HTTP Request (validated client-side)
                     ▼
┌──────────────────────────────────────────────────────────┐
│         SPRING BOOT CONTROLLER (Enhanced)                │
│                                                          │
│  ✨ Request validation:                                 │
│    - Null checks                                        │
│    - ID validation (> 0)                                │
│    - Entity existence checks                            │
│                                                          │
│  ✨ Error handling:                                     │
│    - Try-catch blocks                                   │
│    - Specific error messages                            │
│    - Model attributes for view                          │
│                                                          │
│  ✨ RedirectAttributes:                                 │
│    - Flash messages for success/error                   │
│    - User feedback after action                         │
│                                                          │
│  ✨ Better logic:                                       │
│    - Validation before save                             │
│    - Error propagation                                  │
│    - Check record existence before delete               │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│         SERVICE LAYER (Business Logic)                   │
│                                                          │
│  ✨ Data Validation:                                    │
│    └─ validateStudent(): All field checks               │
│    └─ isValidEmail(): Regex pattern                     │
│    └─ isValidAge(): 16-120 range                        │
│    └─ isValidName(): 2-100 chars                        │
│                                                          │
│  ✨ Data Sanitization:                                  │
│    └─ sanitizeStudent(): trim() all strings             │
│    └─ toLowerCase() email                               │
│    └─ Remove extra spaces                               │
│                                                          │
│  ✨ Business Rules:                                     │
│    └─ Email duplicate check                             │
│    └─ Support exclude ID (for updates)                  │
│    └─ Transaction management (@Transactional)           │
│                                                          │
│  ✨ Error Handling:                                     │
│    └─ Return error messages (not null)                  │
│    └─ Try-catch wrapper                                 │
│    └─ Detailed error reporting                          │
│                                                          │
│  ✨ New Methods:                                        │
│    └─ saveStudent(): Enhanced with validation           │
│    └─ updateStudent(): Partial update                   │
│    └─ countTotalStudents(): Statistics                  │
│    └─ searchStudents(): Name OR email search            │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│       DATA ACCESS LAYER (Repository - Enhanced)          │
│                                                          │
│  ✨ New Query Methods:                                  │
│    └─ findByNameContainingIgnoreCaseOrEmailContains()   │
│      (Search by name OR email)                          │
│                                                          │
│    └─ existsByEmailAndIdNot(email, id)                  │
│      (Check email excluding current record)             │
│                                                          │
│  ✨ Existing Methods:                                   │
│    └─ findAllByOrderByIdAsc()                           │
│    └─ existsByEmail()                                   │
│    └─ Standard CRUD from JpaRepository                  │
└────────────────┬──────────────────────────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────────────────────────┐
│               SQL SERVER DATABASE                        │
│                                                          │
│  Parameterized queries protect against SQL Injection     │
└──────────────────────────────────────────────────────────┘
```

**Cải Tiến:**
- ✅ Multi-layer validation
- ✅ Data sanitization
- ✅ Rich user feedback
- ✅ Smart email checking (exclude ID)
- ✅ Modern, responsive UI
- ✅ Better security
- ✅ Error handling at each layer

---

## VALIDATION LAYERS

```
User Input (Browser)
    │
    ├─ Client-side validation (HTML5 attributes)
    │  └─ required, minlength, maxlength, type="email"
    │
    ▼
POST /students/save
    │
    ├─ Controller Validation
    │  ├─ student != null
    │  ├─ id validation if editing
    │  └─ Record existence check
    │
    ▼
Service Validation (CORE LOGIC)
    │
    ├─ validateStudent():
    │  ├─ Name: 2-100 chars
    │  ├─ Age: 16-120 range
    │  ├─ Gender: Nam/Nữ
    │  └─ Email: regex pattern
    │
    ├─ Data Sanitization:
    │  ├─ Trim spaces
    │  ├─ Lowercase email
    │  └─ Type safety
    │
    ├─ Business Rules:
    │  └─ Email duplicate check
    │     (excluding current ID if update)
    │
    ▼
Database Constraints (Final Line)
    │
    ├─ Column constraints
    ├─ Unique email constraint
    └─ Non-null constraints
    │
    ▼
SUCCESS or DETAILED ERROR MESSAGE
```

---

## ERROR HANDLING FLOW

```
┌────────────────────────────────────┐
│  Input Request (Student data)       │
└────────┬─────────────────────────────┘
         │
         ▼
    Is Student object null?
    ├─ YES → Error: "Dữ liệu không hợp lệ"
    │                                      │
    │                                      ▼
    │        ┌─────────────────────────────────────┐
    │        │ Return error to student-form.html   │
    │        │ User sees: Lỗi! ...                │
    │        │ Form keeps entered data             │
    │        └─────────────────────────────────────┘
    │
    ├─ NO
         │
         ▼
    Validate in Service
    ├─ Name validation
    ├─ Age validation  
    ├─ Email validation
    ├─ Gender validation
    │
    ├─ FAIL → Error message returned
    │        └─→ [Same error display as above]
    │
    ├─ PASS
         │
         ▼
    Email duplicate check
    ├─ (exclude current ID if updating)
    │
    ├─ DUPLICATE → Error: "Email đã được dùng"
    │             └─→ [Same error display]
    │
    ├─ UNIQUE
         │
         ▼
    Try to Save to Database
    ├─ SUCCESS:
    │   ├─ Redirect: /students
    │   └─ Flash: "Thêm sinh viên thành công!"
    │
    ├─ EXCEPTION:
    │   ├─ Catch exception
    │   ├─ Error: "Lỗi khi lưu: ..."
    │   └─ [Error display to user]
```

---

## NEW FEATURES ADDED

### 1. Comprehensive Validation
```java
validateStudent(student) → String
  ├─ null check
  ├─ name validation (2-100)
  ├─ age validation (16-120)
  ├─ email validation (regex)
  ├─ gender validation (Nam/Nữ)
  └─ return: null if valid, error message if invalid
```

### 2. Data Sanitization
```java
sanitizeStudent(student)
  ├─ Trim name, email, gender
  ├─ Lowercase email
  └─ Remove injection risks
```

### 3. Smart Email Checking
```java
isEmailExists(email, excludeId)
  ├─ If excludeId == null:
  │  └─ Check if email exists (for new records)
  ├─ If excludeId != null:
  │  └─ Check if email exists in OTHER records
  │     (allow same user to keep their email)
```

### 4. Enhanced Search
```java
searchStudents(keyword)
  ├─ OLD: Search by name only
  └─ NEW: Search by name OR email
```

### 5. Flash Messages
```java
RedirectAttributes.addFlashAttribute("success", "...")
  └─ Message appears once after redirect
  └─ Automatic dismissal
```

---

## SECURITY ENHANCEMENTS

```
┌─────────────────────────────────────────────────────┐
│              SECURITY LAYERS                        │
├─────────────────────────────────────────────────────┤
│                                                     │
│  1. Input Validation                               │
│     ├─ Type checking (Integer, String)             │
│     ├─ Length constraints                          │
│     ├─ Range validation                            │
│     └─ Format validation (email regex)             │
│                                                     │
│  2. Data Sanitization                              │
│     ├─ Trim whitespace                             │
│     ├─ Lowercase normalization                     │
│     └─ Type safety                                 │
│                                                     │
│  3. SQL Injection Prevention                       │
│     ├─ Spring Data JPA (parameterized queries)     │
│     ├─ No string concatenation                     │
│     └─ Named parameters @Param                     │
│                                                     │
│  4. XSS Prevention                                 │
│     ├─ Thymeleaf auto-escaping                     │
│     └─ No inline JavaScript                        │
│                                                     │
│  5. Null Pointer Prevention                        │
│     ├─ Null checks at every layer                  │
│     └─ Safe object access                          │
│                                                     │
│  6. Error Information Leakage                      │
│     ├─ Generic error messages to user              │
│     ├─ Detailed logging internally                 │
│     └─ No stack traces in responses                │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

**Kiến Trúc mới của bạn là solid, secure, và scalable! 🏗️**
