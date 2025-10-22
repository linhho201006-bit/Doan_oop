package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.KhachHang;
import service.KhachHangService;
import service.Impl.KhachHangServiceImpl;

public class KhachHangController {

    private KhachHangService khachHangService;
    private Scanner scanner;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public KhachHangController() {
        khachHangService = new KhachHangServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ KHÁCH HÀNG ===");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Hiển thị danh sách khách hàng");
            System.out.println("3. Tìm kiếm khách hàng");
            System.out.println("4. Cập nhật khách hàng");
            System.out.println("5. Xóa khách hàng");
            System.out.println("6. Thống kê khách hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ trôi dòng

            switch (choice) {
                case 1 -> themKhachHang();
                case 2 -> hienThiTatCaKhachHang();
                case 3 -> timKiemKhachHang();
                case 4 -> capNhatKhachHang();
                case 5 -> xoaKhachHang();
                case 6 -> thongKeKhachHang();
                case 0 -> {
                    System.out.println("Đã thoát khỏi chương trình quản lý khách hàng!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. THÊM KHÁCH HÀNG =================
    private void themKhachHang() {
        System.out.println("\n=== THÊM KHÁCH HÀNG MỚI ===");

        System.out.print("Nhập họ tên: ");
        String hoTen = scanner.nextLine();
        System.out.print("Nhập giới tính (Nam/Nữ): ");
        String gioiTinh = scanner.nextLine();
        System.out.print("Nhập CMND: ");
        String cmnd = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String diaChi = scanner.nextLine();
        System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
        String ngaySinhStr = scanner.nextLine();

        java.util.Date utilDate;
        try {
            utilDate = sdf.parse(ngaySinhStr);
        } catch (ParseException e) {
            System.out.println("Ngày sinh không hợp lệ!");
            return;
        }

        // 🔥 CHUYỂN từ java.util.Date sang java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        System.out.print("Nhập số điện thoại: ");
        String sdt = scanner.nextLine();

        // ✅ Dùng sqlDate (đúng kiểu với model)
        KhachHang kh = new KhachHang("", hoTen, gioiTinh, cmnd, diaChi, sqlDate, sdt);

        if (khachHangService.themKhachHang(kh)) {
            System.out.println("Thêm khách hàng thành công! Mã KH: " + kh.getMaKH());
        } else {
            System.out.println("Thêm khách hàng thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ DANH SÁCH =================
    private void hienThiTatCaKhachHang() {
        System.out.println("\n=== DANH SÁCH KHÁCH HÀNG ===");
        List<KhachHang> ds = khachHangService.layTatCaKhachHang();

        if (ds.isEmpty()) {
            System.out.println("Không có khách hàng nào trong danh sách!");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-15s %-20s %-15s %-12s%n",
                "Mã KH", "Họ tên", "Giới tính", "CMND", "Địa chỉ", "Ngày sinh", "SĐT");

        for (KhachHang kh : ds) {
            System.out.printf("%-10s %-20s %-10s %-15s %-20s %-15s %-12s%n",
                    kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(), kh.getCMND(),
                    kh.getDiaChi(), sdf.format(kh.getNgaySinh()), kh.getSDT());
        }
    }

    // ================= 3. TÌM KIẾM =================
    private void timKiemKhachHang() {
        System.out.println("\n=== TÌM KIẾM KHÁCH HÀNG ===");
        System.out.println("1. Theo mã khách hàng");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Theo CMND");
        System.out.println("4. Theo số điện thoại");
        System.out.print("Chọn: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        switch (c) {
            case 1 -> {
                System.out.print("Nhập mã khách hàng: ");
                String ma = scanner.nextLine();
                KhachHang kh = khachHangService.timKhachHangTheoMa(ma);
                if (kh != null)
                    hienThiThongTinKhachHang(kh);
                else
                    System.out.println("Không tìm thấy khách hàng!");
            }
            case 2 -> {
                System.out.print("Nhập họ tên: ");
                String ten = scanner.nextLine();
                List<KhachHang> ds = khachHangService.timKhachHangTheoHoTen(ten);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy khách hàng!");
                else
                    ds.forEach(this::hienThiThongTinKhachHang);
            }
            case 3 -> {
                System.out.print("Nhập CMND: ");
                String cmnd = scanner.nextLine();
                List<KhachHang> ds = khachHangService.timKhachHangTheoCMND(cmnd);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy khách hàng!");
                else
                    ds.forEach(this::hienThiThongTinKhachHang);
            }
            case 4 -> {
                System.out.print("Nhập SĐT: ");
                String sdt = scanner.nextLine();
                List<KhachHang> ds = khachHangService.timKhachHangTheoSDT(sdt);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy khách hàng!");
                else
                    ds.forEach(this::hienThiThongTinKhachHang);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT =================
    private void capNhatKhachHang() {
        System.out.println("\n=== CẬP NHẬT KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng cần cập nhật: ");
        String ma = scanner.nextLine();

        KhachHang kh = khachHangService.timKhachHangTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return;
        }

        System.out.println("Để trống nếu không muốn thay đổi:");

        System.out.print("Họ tên (" + kh.getHoTen() + "): ");
        String ten = scanner.nextLine();
        if (!ten.isEmpty())
            kh.setHoTen(ten);

        System.out.print("Giới tính (" + kh.getGioiTinh() + "): ");
        String gt = scanner.nextLine();
        if (!gt.isEmpty())
            kh.setGioiTinh(gt);

        System.out.print("CMND (" + kh.getCMND() + "): ");
        String cmnd = scanner.nextLine();
        if (!cmnd.isEmpty())
            kh.setCMND(cmnd);

        System.out.print("Địa chỉ (" + kh.getDiaChi() + "): ");
        String diaChi = scanner.nextLine();
        if (!diaChi.isEmpty())
            kh.setDiaChi(diaChi);

        System.out.print("Ngày sinh (" + sdf.format(kh.getNgaySinh()) + "): ");
        String ngayStr = scanner.nextLine();
        if (!ngayStr.isEmpty()) {
            try {
                java.util.Date utilDate = sdf.parse(ngayStr);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                kh.setNgaySinh(sqlDate);
            } catch (ParseException e) {
                System.out.println("Ngày sinh không hợp lệ!");
            }
        }

        System.out.print("SĐT (" + kh.getSDT() + "): ");
        String sdt = scanner.nextLine();
        if (!sdt.isEmpty())
            kh.setSDT(sdt);

        if (khachHangService.capNhatKhachHang(kh))
            System.out.println("Cập nhật khách hàng thành công!");
        else
            System.out.println("Cập nhật thất bại!");
    }

    // ================= 5. XÓA =================
    private void xoaKhachHang() {
        System.out.println("\n=== XÓA KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng cần xóa: ");
        String ma = scanner.nextLine();

        if (khachHangService.xoaKhachHang(ma))
            System.out.println("Đã xóa khách hàng " + ma);
        else
            System.out.println("Xóa thất bại hoặc mã không tồn tại!");
    }

    // ================= 6. THỐNG KÊ =================
    private void thongKeKhachHang() {
        List<KhachHang> ds = khachHangService.layTatCaKhachHang();
        System.out.println("\n=== THỐNG KÊ KHÁCH HÀNG ===");
        System.out.println("Tổng số khách hàng: " + ds.size());

        long soNam = ds.stream().filter(kh -> kh.getGioiTinh().equalsIgnoreCase("Nam")).count();
        long soNu = ds.stream().filter(kh -> kh.getGioiTinh().equalsIgnoreCase("Nữ")).count();

        System.out.println("Số khách hàng Nam: " + soNam);
        System.out.println("Số khách hàng Nữ: " + soNu);
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinKhachHang(KhachHang kh) {
        System.out.printf("%-10s %-20s %-10s %-15s %-20s %-15s %-12s%n",
                kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(), kh.getCMND(),
                kh.getDiaChi(), sdf.format(kh.getNgaySinh()), kh.getSDT());
    }
}
