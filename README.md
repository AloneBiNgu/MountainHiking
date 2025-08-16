# Mountain Hiking Registration (Java • Maven • MVC • Console)


---

## ✨ Chức năng

* Thêm / Cập nhật / Hiển thị / Xóa đăng ký
* Tìm kiếm theo **tên sinh viên** (không phân biệt hoa/thường)
* Lọc theo **campus** (SE/HE/DE/QE/CE)
* Thống kê theo **đỉnh núi** (số lượng & tổng học phí)
* Đọc danh mục **núi** từ CSV (classpath resource)
* **Lưu/đọc** đăng ký ra/từ file cục bộ
* Tính học phí theo **Telco enum** (Viettel/VNPT giảm 35%)
* **Xóa file dữ liệu** (registrations.dat) trực tiếp từ menu

---

## 🧱 Công nghệ & yêu cầu

* **Java 17+**, **Maven 3.9+**
* Ứng dụng **console** (không GUI)

---

## 📁 Cấu trúc dự án (Maven)

```
src/
└─ main/
   ├─ java/
   │  └─ com/mycompany/mountainhiking/
   │     ├─ enums/
   │     │  └─ Telco.java
   │     ├─ models/
   │     │  ├─ Student.java
   │     │  ├─ Mountain.java
   │     │  └─ Registration.java
   │     ├─ repo/
   │     │  ├─ RegistrationRepository.java
   │     │  ├─ FileRegistrationRepository.java
   │     │  ├─ MountainRepository.java
   │     │  └─ ClasspathMountainRepository.java
   │     ├─ services/
   │     │  ├─ Validator.java
   │     │  ├─ TelcoPolicy.java
   │     │  └─ TuitionCalculator.java
   │     ├─ views/
   │     │  └─ ConsoleView.java
   │     ├─ controllers/
   │     │  ├─ RegistrationController.java
   │     │  └─ MenuController.java
   │     └─ MountainHiking.java           # main()
   └─ resources/
      └─ data/
         └─ MountainList.csv
data/
└─ registrations.dat
```

## ⚙️ Build & chạy

**Maven Exec**

```bash
mvn -q exec:java -Dexec.mainClass=com.mycompany.mountainhiking.MountainHiking
```

**Đóng gói Jar**

```bash
mvn -q package
java -jar target/<artifactId>-<version>.jar
```

---

## 📄 Dữ liệu

**CSV DATA**: `src/main/resources/data/MountainList.csv`
Định dạng (header không bắt buộc; nếu có `code,name,location` sẽ được bỏ qua):

```
M001,Fansipan,Lao Cai
M002,Bach Moc Luong Tu,Lai Chau
M003,Ta Xua,Son La
M004,LangBiang,Lam Dong
M005,Chua Chan,Dong Nai
```

**File lưu đăng ký**: `./data/registrations.dat`

* Tạo tự động khi lưu.
* Lưu dạng **Map** để bền khi bạn refactor class/package.

---

## 🧭 Menu & cách dùng (CLI)

Khi chạy app bạn sẽ thấy:

```
1) New
2) Update
3) Display
4) Delete
5) Search
6) Filter
7) Statistics
8) Save
9) Delete Data File
10) Exit
```

Gợi ý quy trình:

1. `1) New` → thêm vài đăng ký mẫu
2. `3) Display` → kiểm tra danh sách
3. `8) Save` → lưu ra file
4. Khởi động lại app → dữ liệu được **load** lại

**Tùy chọn 9) Delete Data File**

* Xóa file `./data/registrations.dat` **và** dọn dữ liệu trên RAM.
* Dùng khi: file cũ không tương thích sau refactor, muốn reset dữ liệu test, v.v.
* Không thể hoàn tác; sau đó danh sách sẽ rỗng → hãy thêm mới rồi **Save** lại.

---

## ✅ Kiểm tra hợp lệ

* **Student ID**: `^(SE|HE|DE|QE|CE)\d{6}$`
* **Tên**: 2..20 ký tự
* **SĐT**: `^0\d{9}$`
* **Email**: `local@domain.tld` cơ bản
* **Mã núi**: phải tồn tại trong danh mục CSV đã load

---

## 💸 Học phí & Telco enum

* **Học phí gốc**: `6,000,000`
* **Enum Telco** quản lý prefix + tỷ lệ giảm:

  * Viettel & VNPT/VinaPhone → **0.35** (giảm 35%)
  * MobiFone/Gmobile/Vietnamobile → **0.00** (không giảm)
* `TuitionCalculator.calc(phone)` → `BASE * (1 - discountRate)`

Ví dụ:

* `0961234567` → 3,900,000
* `0912345678` → 3,900,000
* `0901234567` → 6,000,000

---

## 🗂️ Repository & xóa file dữ liệu

* `RegistrationRepository` (interface) + `FileRegistrationRepository` (triển khai)
* `save()` ghi **Map**; `load()` đọc Map và có log số bản ghi
* **Hàm mới**: `deleteStorageFile()`

  * Trả về `true` nếu xóa được (hoặc không tồn tại).
  * Dọn luôn dữ liệu trên RAM (`map.clear()`).

---

## 🔎 Search & Filter

* **Search** theo tên (substring, không phân biệt hoa/thường).
* **Filter** theo campus (`SE/HE/DE/QE/CE`).
* Cả hai đều duyệt **in-memory O(n)**.

---

## 🧪 Checklist test

* [ ] Add 3 đăng ký (Viettel, VNPT, khác) → học phí đúng
* [ ] Display đúng cột/format
* [ ] Update đổi phone → học phí cập nhật
* [ ] Delete có xác nhận
* [ ] Search/Filter đúng tiêu chí
* [ ] Statistics tính count & total fee theo mã núi
* [ ] Save → Exit → chạy lại → dữ liệu được load
* [ ] **Delete Data File** → danh sách rỗng → Add & Save lại OK

---

## 🛠️ Khắc phục sự cố

* **“No data.”**: chưa có bản ghi, load thất bại, hoặc kết quả Search/Filter rỗng.
* **File không tương thích sau refactor**: chọn `9) Delete Data File`, sau đó thêm lại dữ liệu.
* **Sai thư mục làm việc**: in tuyệt đối đường dẫn `registrations.dat` để chắc chắn Save/Load cùng nơi.
* **CSV trống/thiếu**: app sẽ fallback danh sách mặc định; kiểm tra `src/main/resources/data/MountainList.csv`.

---
