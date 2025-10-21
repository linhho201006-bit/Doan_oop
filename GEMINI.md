# HỆ THỐNG QUẢN LÝ MÁY TÍNH - LUỒNG CODE CHUẨN
 
## 1. KIẾN TRÚC TỔNG QUAN

- **MVC Pattern**: Model-View-Controller
- **Service Layer**: Interface + Implementation
- **File-based Storage**: Lưu trữ dữ liệu trong file .txt
- **Circular Dependency**: Sử dụng setter methods để tránh lỗi StackOverflowError

## 2. CẤU TRÚC THƯ MỤC

```
src/
├── model/                    # Các entity/POJO
│   ├── abstraction/         # Abstract classes
│   └── *.java              # Concrete models
├── service/                 # Service interfaces
├── service/Impl/           # Service implementations
├── controller/             # Controllers (UI layer)
├── resources/              # Data files (.txt)
└── App.java               # Main entry point
```

## 3. LUỒNG CODE CHUẨN CHO MODEL

### 3.1 Cấu trúc Model cơ bản:

```java
public class ModelName {
    // Fields với naming convention: MaField, TenField, etc.
    private String MaField;
    private String TenField;
    private Double GiaField;
    private Date NgayField;
    private String TrangThai; // "Chuẩn bị", "Đang bán", "Đã đầy", "Hoàn thành", "Hủy"

    // Constructor mặc định
    public ModelName() {}

    // Constructor đầy đủ
    public ModelName(String maField, String tenField, Double giaField, Date ngayField, String trangThai) {
        MaField = maField;
        TenField = tenField;
        GiaField = giaField;
        NgayField = ngayField;
        TrangThai = trangThai;
    }

    // Getter và Setter cho tất cả fields
    public String getMaField() { return MaField; }
    public void setMaField(String maField) { MaField = maField; }
    // ... các getter/setter khác

    // Business methods
    public boolean coTheThucHien() {
        return "Trạng thái hợp lệ".equals(TrangThai);
    }

    public void capNhatTrangThai() {
        // Logic cập nhật trạng thái
    }

    @Override
    public String toString() {
        return "ModelName [MaField=" + MaField + ", TenField=" + TenField +
               ", GiaField=" + GiaField + ", NgayField=" + NgayField +
               ", TrangThai=" + TrangThai + "]";
    }
}
```

## 4. LUỒNG CODE CHUẨN CHO SERVICE

### 4.1 Service Interface:

```java
public interface ServiceName {
    // CRUD operations
    boolean them(Entity entity);
    List<Entity> layTatCa();
    Entity timTheoMa(String ma);
    boolean capNhat(Entity entity);
    boolean xoa(String ma);

    // Business operations
    boolean kiemTraRangBuoc(String ma);
    Double tinhTongTien();
    List<Entity> timKiem(String tuKhoa);

    // Status-based queries
    List<Entity> layTheoTrangThai(String trangThai);
    List<Entity> layChuaThanhToan();
    List<Entity> layDaThanhToan();
}
```

### 4.2 Service Implementation:

```java
public class ServiceNameImpl implements ServiceName {
    private static final String FILE_PATH = "src/resources/EntityName.txt";
    private List<Entity> danhSach;
    private int nextMa;
    private OtherService otherService; // Dependency injection

    public ServiceNameImpl() {
        danhSach = new ArrayList<>();
        nextMa = 1;
        otherService = null; // Tránh circular dependency
        loadData();
    }

    // Setter cho dependency injection
    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }

    // Generate mã tự động
    private String generateMa() {
        return String.format("MA%03d", nextMa++);
    }

    // CRUD implementations
    @Override
    public boolean them(Entity entity) {
        try {
            // Kiểm tra ràng buộc
            if (!kiemTraRangBuoc(entity.getMaField())) {
                System.out.println("Ràng buộc không thỏa mãn: " + entity.getMaField());
                return false;
            }

            // Tự động tạo mã
            entity.setMaField(generateMa());

            danhSach.add(entity);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // File operations
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Entity entity : danhSach) {
                writer.println(entity.getMaField() + "|" +
                              entity.getTenField() + "|" +
                              entity.getGiaField() + "|" +
                              entity.getNgayField() + "|" +
                              entity.getTrangThai());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    Entity entity = new Entity(
                        parts[0], // maField
                        parts[1], // tenField
                        parts[2].equals("null") ? null : Double.parseDouble(parts[2]), // giaField
                        parts[3].equals("null") ? null : Date.valueOf(parts[3]), // ngayField
                        parts[4] // trangThai
                    );
                    danhSach.add(entity);

                    // Cập nhật nextMa
                    String ma = parts[0];
                    if (ma.startsWith("MA")) {
                        try {
                            int so = Integer.parseInt(ma.substring(2));
                            if (so >= nextMa) {
                                nextMa = so + 1;
                            }
                        } catch (NumberFormatException e) {
                            // Bỏ qua
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                new File(FILE_PATH).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
```

## 5. LUỒNG CODE CHUẨN CHO CONTROLLER

### 5.1 Controller Structure:

```java
public class ControllerName {
    private ServiceName serviceName;
    private OtherService otherService;
    private Scanner scanner;

    public ControllerName() {
        serviceName = new ServiceNameImpl();
        otherService = new OtherServiceImpl();
        scanner = new Scanner(System.in);

        // Thiết lập circular dependency nếu cần
        if (serviceName instanceof ServiceNameImpl && otherService instanceof OtherServiceImpl) {
            ((ServiceNameImpl) serviceName).setOtherService(otherService);
            ((OtherServiceImpl) otherService).setServiceName(serviceName);
        }
    }

    // Menu chính
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ ENTITY ===");
            System.out.println("1. Thêm mới");
            System.out.println("2. Hiển thị tất cả");
            System.out.println("3. Tìm kiếm");
            System.out.println("4. Cập nhật");
            System.out.println("5. Xóa");
            System.out.println("6. Thống kê");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: themMoi(); break;
                case 2: hienThiTatCa(); break;
                case 3: timKiem(); break;
                case 4: capNhat(); break;
                case 5: xoa(); break;
                case 6: thongKe(); break;
                case 0: return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // CRUD methods
    private void themMoi() {
        System.out.println("\n=== THÊM MỚI ===");

        // Hiển thị danh sách liên quan
        hienThiDanhSachLienQuan();

        System.out.print("Nhập thông tin: ");
        String input = scanner.nextLine();

        // Validation
        if (input.trim().isEmpty()) {
            System.out.println("Thông tin không được để trống!");
            return;
        }

        // Tạo entity
        Entity entity = new Entity("", input, 0.0, null, "Chuẩn bị");

        if (serviceName.them(entity)) {
            System.out.println("Thêm thành công! Mã: " + entity.getMaField());
        } else {
            System.out.println("Lỗi khi thêm!");
        }
    }

    private void hienThiTatCa() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ ===");
        List<Entity> danhSach = serviceName.layTatCa();

        if (danhSach.isEmpty()) {
            System.out.println("Không có dữ liệu!");
        } else {
            hienThiDanhSach(danhSach);
        }
    }

    private void hienThiDanhSach(List<Entity> danhSach) {
        System.out.printf("%-10s %-30s %-15s %-15s %-15s%n",
                "Mã", "Tên", "Giá", "Ngày", "Trạng thái");
        System.out.println("=".repeat(85));

        for (Entity entity : danhSach) {
            System.out.printf("%-10s %-30s %-15.0f %-15s %-15s%n",
                    entity.getMaField(),
                    entity.getTenField(),
                    entity.getGiaField() != null ? entity.getGiaField() : 0.0,
                    entity.getNgayField() != null ? entity.getNgayField().toString() : "N/A",
                    entity.getTrangThai());
        }
    }

    // Helper methods
    private void hienThiDanhSachLienQuan() {
        // Hiển thị danh sách liên quan để user chọn
    }
}
```

## 6. LUỒNG CODE CHUẨN CHO APP.JAVA

```java
public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HỆ THỐNG QUẢN LÝ MÁY TÍNH ===");
            System.out.println("1. Quản lý khách hàng");
            System.out.println("2. Quản lý máy tính");
            System.out.println("3. Quản lý nhân viên");
            System.out.println("4. Quản lý nhà cung cấp");
            System.out.println("5. Quản lý phiếu nhập ");
            System.out.println("6. Quản lý hóa đơn");
            System.out.println("0. Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    KhachHangController controller1 = new KhachHangController();
                    controller1.hienThiMenu();
                    break;
                case 2:
                    TourController controller2 = new TourController();
                    controller2.hienThiMenu();
                    break;
                // ... các case khác
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
```

## 7. NAMING CONVENTIONS

### 7.1 Model Fields:

- `MaField`: Mã định danh (String)
- `TenField`: Tên (String)
- `GiaField`: Giá tiền (Double)
- `NgayField`: Ngày tháng (Date)
- `TrangThai`: Trạng thái (String)
- `SoLuong`: Số lượng (Integer)

### 7.2 Method Names:

- `them*()`: Thêm mới
- `layTatCa*()`: Lấy tất cả
- `tim*TheoMa()`: Tìm theo mã
- `capNhat*()`: Cập nhật
- `xoa*()`: Xóa
- `kiemTra*()`: Kiểm tra
- `tinh*()`: Tính toán
- `hienThi*()`: Hiển thị

### 7.3 File Names:

- Model: `EntityName.java`
- Service Interface: `EntityNameService.java`
- Service Implementation: `EntityNameServiceImpl.java`
- Controller: `EntityNameController.java`
- Data File: `EntityName.txt`

## 8. CIRCULAR DEPENDENCY HANDLING

### 8.1 Vấn đề:

- Service A cần Service B
- Service B cần Service A
- Gây ra StackOverflowError

### 8.2 Giải pháp:

```java
// Trong constructor, set dependency = null
public ServiceAImpl() {
    serviceB = null; // Tránh circular dependency
}

// Thêm setter method
public void setServiceB(ServiceB serviceB) {
    this.serviceB = serviceB;
}

// Trong Controller, thiết lập dependencies
public Controller() {
    serviceA = new ServiceAImpl();
    serviceB = new ServiceBImpl();

    // Thiết lập circular dependency
    ((ServiceAImpl) serviceA).setServiceB(serviceB);
    ((ServiceBImpl) serviceB).setServiceA(serviceA);
}

// Trong methods, kiểm tra null
public void someMethod() {
    if (serviceB != null) {
        serviceB.doSomething();
    }
}
```

## 9. ERROR HANDLING PATTERNS

### 9.1 Try-Catch trong Service:

```java
public boolean someOperation() {
    try {
        // Business logic
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
```

### 9.2 Validation trong Controller:

```java
private void someOperation() {
    // Input validation
    if (input.trim().isEmpty()) {
        System.out.println("Thông tin không được để trống!");
        return;
    }

    // Business operation
    if (service.someOperation()) {
        System.out.println("Thành công!");
    } else {
        System.out.println("Lỗi khi thực hiện!");
    }
}
```

## 10. DATA PERSISTENCE PATTERNS

### 10.1 File Format:

```
MaField|TenField|GiaField|NgayField|TrangThai
MA001|Tên Entity|1000000.0|2024-01-01|Chuẩn bị
```

### 10.2 Null Handling:

```java
// Khi save
writer.println(entity.getGiaField() != null ? entity.getGiaField() : "null");

// Khi load
Double giaField = parts[2].equals("null") ? null : Double.parseDouble(parts[2]);
```

## 11. BUSINESS LOGIC PATTERNS

### 11.1 Trạng thái Entity:

- "Chuẩn bị": Mới tạo, chưa sẵn sàng
- "Đang bán": Đang hoạt động, có thể bán
- "Đã đầy": Hết chỗ, không thể bán thêm
- "Hoàn thành": Đã kết thúc thành công
- "Hủy": Đã hủy bỏ

### 11.2 Auto-update Methods:

```java
public void capNhatTrangThai() {
    if (soLuongConLai <= 0) {
        TrangThai = "Đã đầy";
    } else if (soLuongDaBan > 0) {
        TrangThai = "Đang bán";
    } else {
        TrangThai = "Chuẩn bị";
    }
}
```

## 12. TESTING PATTERNS

### 12.1 Console Input/Output:

```java
System.out.print("Nhập mã: ");
String ma = scanner.nextLine();

System.out.println("Kết quả: " + result);
System.out.printf("Giá: %.0f VNĐ%n", gia);
```

### 12.2 Confirmation Patterns:

```java
System.out.print("Bạn có chắc chắn? (y/n): ");
String confirm = scanner.nextLine();
if (confirm.toLowerCase().equals("y") || confirm.toLowerCase().equals("yes")) {
    // Thực hiện action
}
```

---

**LƯU Ý QUAN TRỌNG:**

1. Luôn kiểm tra null trước khi sử dụng dependencies
2. Sử dụng try-catch cho tất cả file operations
3. Validate input trong Controller layer
4. Sử dụng setter methods để tránh circular dependency
5. Format output đẹp với printf và String.format
6. Tự động generate mã định danh theo pattern MA001, MA002, ...
7. Luôn có toString() method cho debugging
8. Sử dụng ArrayList thay vì Vector cho performance
9. File path luôn bắt đầu với "src/resources/"
10. Scanner.nextLine() sau mỗi nextInt() để tránh lỗi input
