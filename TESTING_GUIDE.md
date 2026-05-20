# 🚀 QUICK START GUIDE - TESTING & RUNNING

## 📋 PREREQUISITES

- Java 17+ installed
- Maven 3.6+ (or use mvnw)
- SQL Server/Database configured
- Port 8080 available

---

## ⚙️ BUILD & RUN

### Method 1: Using Maven Wrapper (Recommended)

**Windows:**
```powershell
cd d:\LTWeb1\Dangconghien\dangconghien

# Clean and build
.\mvnw.cmd clean package

# Run the application
java -jar target/dangconghien-0.0.1-SNAPSHOT.jar
```

**Linux/Mac:**
```bash
cd /path/to/dangconghien

# Clean and build
./mvnw clean package

# Run the application
java -jar target/dangconghien-0.0.1-SNAPSHOT.jar
```

### Method 2: Using IDE

**IntelliJ IDEA / VS Code:**
1. Open project in IDE
2. Right-click `DangconghienApplication.java`
3. Select "Run"
4. Or press `Ctrl+Shift+F10`

### Method 3: Maven (if installed globally)
```bash
mvn clean package
java -jar target/dangconghien-0.0.1-SNAPSHOT.jar
```

---

## 🌐 ACCESS APPLICATION

Once running:
```
URL: http://localhost:8080/students
```

### Home Page Features:
- 📊 List all students
- 🔍 Search by name or email
- ➕ Add new student
- ✏️ Edit student
- 🗑️ Delete student
- 📱 Responsive on all devices

---

## ✅ TEST SCENARIOS

### 1️⃣ ADDING NEW STUDENT ✅

**Happy Path:**
1. Click "➕ Thêm Sinh viên"
2. Fill form:
   - Name: "Trần Văn A"
   - Age: 20
   - Gender: Nam
   - Email: "tran.van.a@email.com"
3. Click "Thêm Sinh viên"
4. ✅ See: "Thêm sinh viên thành công!"
5. ✅ Redirected to list
6. ✅ New student visible in table

**Error Case 1: Invalid Email:**
1. Email: "not-an-email"
2. Submit
3. ✅ Error: "Email không hợp lệ"
4. ✅ Form keeps data
5. ✅ Can fix and resubmit

**Error Case 2: Duplicate Email:**
1. Email: "existing@email.com" (already in DB)
2. Submit
3. ✅ Error: "Email đã được dùng"

**Error Case 3: Invalid Age:**
1. Age: 15 (too young)
2. Submit
3. ✅ Error: "Tuổi phải từ 16 đến 120 tuổi"

**Error Case 4: Invalid Name:**
1. Name: "A" (too short)
2. Submit
3. ✅ Error: "Tên sinh viên phải từ 2-100 ký tự"

---

### 2️⃣ EDITING STUDENT ✏️

**Happy Path:**
1. Find student in list
2. Click "Sửa"
3. Form shows current data
4. Change some field (e.g., age: 21)
5. Click "Cập nhật"
6. ✅ See: "Cập nhật sinh viên thành công!"
7. ✅ Data updated in list

**Important - Email Behavior:**
1. Edit student with email: "student@email.com"
2. Change email to: "student2@email.com"
3. ✅ Email check excludes current student
4. ✅ Can change own email
5. ✅ But cannot change to existing email

---

### 3️⃣ SEARCHING 🔍

**Search by Name:**
1. Type "Trần" in search box
2. Click "Tìm kiếm"
3. ✅ Shows all students with "Trần" in name

**Search by Email:**
1. Type "gmail.com" in search box
2. Click "Tìm kiếm"
3. ✅ Shows all students with "gmail.com" in email

**Clear Search:**
1. Click "Xóa lọc"
2. ✅ Shows all students again

**Empty Search:**
1. Type something that doesn't exist
2. Click "Tìm kiếm"
3. ✅ Shows: "Không có sinh viên"

---

### 4️⃣ DELETING STUDENT 🗑️

**Happy Path:**
1. Click "Xóa"
2. Confirmation: "Bạn có chắc muốn xóa sinh viên này?"
3. Click OK
4. ✅ See: "Xóa sinh viên [Name] thành công!"
5. ✅ Student removed from list

**Cancel Delete:**
1. Click "Xóa"
2. Confirmation appears
3. Click Cancel
4. ✅ Nothing happens

---

### 5️⃣ EMPTY STATE

**When No Students:**
1. Manually delete all students
2. ✅ See: "Không có sinh viên"
3. ✅ See: "Hãy thêm sinh viên đầu tiên của bạn"
4. ✅ See: "Thêm Sinh viên" button

---

### 6️⃣ UI/UX TESTING

**Responsive Design:**
1. Open browser DevTools (F12)
2. Toggle device toolbar
3. Test on:
   - 📱 iPhone 12 (390px)
   - 📱 iPad (768px)
   - 💻 Desktop (1920px)
4. ✅ All elements properly sized
5. ✅ No overflow/scrolling issues
6. ✅ Buttons are touch-friendly on mobile

**Color & Theme:**
1. ✅ Purple-Blue gradient header
2. ✅ White content cards
3. ✅ Color-coded gender badges (Nam=Purple, Nữ=Pink)
4. ✅ Hover effects on buttons
5. ✅ Smooth animations

**Notifications:**
1. Add student
2. ✅ Green success banner appears
3. Wait 5 seconds
4. ✅ Banner can be manually closed
5. ✅ Refresh page - banner gone

---

### 7️⃣ BROWSER COMPATIBILITY

Test on:
- ✅ Chrome (latest)
- ✅ Firefox (latest)
- ✅ Safari (latest)
- ✅ Edge (latest)

All should display correctly.

---

### 8️⃣ PERFORMANCE TESTING

**Large Dataset:**
1. Add 100+ students to database
2. ✅ List loads quickly
3. ✅ Search responds fast
4. ✅ No UI lag

**Network Latency:**
1. Open DevTools Network tab
2. Set throttling to "Slow 3G"
3. Perform actions
4. ✅ UI remains responsive
5. ✅ Loading indicators show (if added)

---

## 🐛 DEBUGGING

### Check Logs
```bash
# If running in terminal, check console output
# Look for:
# - Validation errors
# - Database errors
# - Controller logs
```

### Database Verification
```sql
-- Connect to SQL Server
SELECT * FROM students;

-- Should see:
-- id | name | age | gender | email
```

### Browser Console (F12)
```javascript
// Check for JavaScript errors
// Should see: No errors
// Only warnings if any (acceptable)
```

### Network Tab (F12)
```
// Check requests
GET /students → 200 OK
POST /students/save → 303 Redirect (or 200)
GET /students/edit/1 → 200 OK
```

---

## 🔧 COMMON ISSUES & FIXES

### Issue 1: Port 8080 already in use
```bash
# Solution 1: Use different port
java -jar target/dangconghien-0.0.1-SNAPSHOT.jar --server.port=8081

# Solution 2: Kill process on port 8080
# Windows: netstat -ano | findstr :8080
# Linux: lsof -i :8080
```

### Issue 2: Database connection error
```bash
# Check application.properties
# Verify:
# - spring.datasource.url
# - spring.datasource.username
# - spring.datasource.password
# - SQL Server is running
```

### Issue 3: Form submission redirects to 404
```bash
# Check:
# - @PostMapping("/save") annotation
# - Form action path: th:action="@{/students/save}"
# - Controller class has @Controller annotation
```

### Issue 4: Styling not loading
```bash
# Check:
# - CDN links are accessible
# - Bootstrap CSS load: <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
# - Browser cache cleared (Ctrl+Shift+Delete)
```

### Issue 5: Email validation always fails
```bash
# Check regex pattern in StudentService
private static final Pattern EMAIL_PATTERN = 
    Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

# Test examples:
# ✓ user@example.com
# ✓ test.email@test.co.uk
# ✓ test+tag@example.com
# ✗ invalid.email
```

---

## 📊 TEST CASES CHECKLIST

```
[ ] Add new student - Happy path
[ ] Add new student - Invalid email
[ ] Add new student - Duplicate email
[ ] Add new student - Invalid age (too young)
[ ] Add new student - Invalid age (too old)
[ ] Add new student - Short name
[ ] Add new student - Long name
[ ] Add new student - Missing gender

[ ] Edit student - Happy path
[ ] Edit student - Change email (new)
[ ] Edit student - Change email (duplicate)
[ ] Edit student - Invalid data

[ ] Delete student - Happy path
[ ] Delete student - Cancel confirm
[ ] Delete non-existent student

[ ] Search - By name
[ ] Search - By email
[ ] Search - No results
[ ] Search - Clear filter

[ ] UI - Mobile view
[ ] UI - Tablet view
[ ] UI - Desktop view
[ ] UI - Color/badges correct

[ ] Empty state display
[ ] Error messages display
[ ] Success messages display
[ ] Buttons work correctly
[ ] Links navigate correctly
```

---

## 📞 SUPPORT

If you encounter issues:

1. **Check logs** in terminal/console
2. **Verify database** connection
3. **Clear browser cache** (Ctrl+Shift+Delete)
4. **Restart application** (Kill and restart)
5. **Check file permissions** (read/write access)

---

**Happy Testing! 🎉**
