package controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import model.KhachHang;
import service.KhachHangService;
import service.Impl.KhachHangServiceImpl;

public class KhachHangController {
    private KhachHangService khachHangService;
    private Scanner scanner;

    public KhachHangController() {
        khachHangService = new KhachHangServiceImpl();
        scanner = new Scanner(System.in);
    }

    // === MENU CHÍNH ===
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ KHÁCH HÀNG ===");
            System.out.println("1. Thêm khách hàng mới");
            System.out.println("2. Hiển thị tất cả khách hàng");
            System.out.println("3. Tìm kiếm khách hàng");
            System.out.println("4. Cập nhật thông tin khách hàng");
            System.out.println("5. Xóa khách hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> themKhachHang();
                case 2 -> hienThiTatCaKhachHang();
                case 3 -> timKiemKhachHang();
                case 4 -> capNhatKhachHang();
                case 5 -> xoaKhachHang();
                case 0 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // === 1. THÊM KHÁCH HÀNG ===
    private void themKhachHang() {
        System.out.println("\n=== THÊM KHÁCH HÀNG MỚI ===");

        System.out.print("Nhập mã khách hàng: ");
        String maKH = scanner.nextLine();

        System.out.print("Nhập họ và tên: ");
        String tenKH = scanner.nextLine();

        System.out.print("Nhập ngày sinh (yyyy-mm-dd): ");
        String ngaySinhStr = scanner.nextLine();
        Date ngaySinh = Date.valueOf(ngaySinhStr);

        System.out.print("Nhập giới tính: ");
        String gioiTinh = scanner.nextLine();

        System.out.print("Nhập địa chỉ: ");
        String diaChi = scanner.nextLine();

        System.out.print("Nhập CMND: ");
        String cmnd = scanner.nextLine();

        System.out.print("Nhập số điện thoại: ");
        String soDT = scanner.nextLine();

        KhachHang kh = new KhachHang(maKH, tenKH, ngaySinh, gioiTinh, diaChi, cmnd, soDT);

        if (khachHangService.themKhachHang(kh)) {
            System.out.println(" Thêm khách hàng thành công!");
        } else {
            System.out.println(" Lỗi khi thêm khách hàng!");
        }
    }

    // === 2. HIỂN THỊ TẤT CẢ KHÁCH HÀNG ===
    private void hienThiTatCaKhachHang() {
        System.out.println("\n=== DANH SÁCH KHÁCH HÀNG ===");
        List<KhachHang> danhSach = khachHangService.layTatCaKhachHang();

        if (danhSach.isEmpty()) {
            System.out.println("Không có khách hàng nào!");
            return;
        }

        System.out.printf("%-8s %-20s %-12s %-10s %-20s %-15s %-12s%n",
                "Mã KH", "Tên KH", "Ngày sinh", "Giới tính", "Địa chỉ", "CMND", "SĐT");
        System.out.println("=".repeat(100));

        for (KhachHang kh : danhSach) {
            System.out.printf("%-8s %-20s %-12s %-10s %-20s %-15s %-12s%n",
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getNgaySinh(),
                    kh.getGioiTinh(),
                    kh.getDiaChi(),
                    kh.getCMND(),
                    kh.getSoDienThoai());
        }
    }

    // === 3. TÌM KIẾM KHÁCH HÀNG ===
    private void timKiemKhachHang() {
        System.out.println("\n=== TÌM KIẾM KHÁCH HÀNG ===");
        System.out.println("1. Theo mã khách hàng");
        System.out.println("2. Theo họ tên");
        System.out.println("3. Tìm kiếm tổng hợp");
        System.out.print("Chọn loại tìm kiếm: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        List<KhachHang> ketQua = null;

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập mã khách hàng: ");
                String ma = scanner.nextLine();
                KhachHang kh = khachHangService.timKhachHangTheoMa(ma);
                ketQua = (kh != null) ? List.of(kh) : List.of();
            }
            case 2 -> {
                System.out.print("Nhập họ tên: ");
                String ten = scanner.nextLine();
                ketQua = khachHangService.timKhachHangTheoHoTen(ten);
            }
            case 3 -> {
                System.out.print("Nhập từ khóa: ");
                String tuKhoa = scanner.nextLine();
                ketQua = khachHangService.timKiemKhachHang(tuKhoa);
            }
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
                return;
            }
        }

        hienThiKetQua(ketQua);
    }

    private void hienThiKetQua(List<KhachHang> ketQua) {
        if (ketQua == null || ketQua.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào!");
            return;
        }

        System.out.println("\nKẾT QUẢ TÌM KIẾM:");
        System.out.printf("%-8s %-20s %-12s %-10s %-20s %-15s %-12s%n",
                "Mã KH", "Tên KH", "Ngày sinh", "Giới tính", "Địa chỉ", "CMND", "SĐT");
        System.out.println("=".repeat(100));

        for (KhachHang kh : ketQua) {
            System.out.printf("%-8s %-20s %-12s %-10s %-20s %-15s %-12s%n",
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getNgaySinh(),
                    kh.getGioiTinh(),
                    kh.getDiaChi(),
                    kh.getCMND(),
                    kh.getSoDienThoai());
        }
    }

    // === 4. CẬP NHẬT KHÁCH HÀNG ===
    private void capNhatKhachHang() {
        System.out.println("\n=== CẬP NHẬT KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng: ");
        String ma = scanner.nextLine();

        KhachHang kh = khachHangService.timKhachHangTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return;
        }

        System.out.println("Thông tin hiện tại: " + kh);

        System.out.print("Tên KH (" + kh.getTenKH() + "): ");
        String ten = scanner.nextLine();
        if (!ten.isBlank())
            kh.setTenKH(ten);

        System.out.print("Ngày sinh (" + kh.getNgaySinh() + "): ");
        String ns = scanner.nextLine();
        if (!ns.isBlank())
            kh.setNgaySinh(Date.valueOf(ns));

        System.out.print("Giới tính (" + kh.getGioiTinh() + "): ");
        String gt = scanner.nextLine();
        if (!gt.isBlank())
            kh.setGioiTinh(gt);

        System.out.print("Địa chỉ (" + kh.getDiaChi() + "): ");
        String dc = scanner.nextLine();
        if (!dc.isBlank())
            kh.setDiaChi(dc);

        System.out.print("CMND (" + kh.getCMND() + "): ");
        String cm = scanner.nextLine();
        if (!cm.isBlank())
            kh.setCMND(cm);

        System.out.print("SĐT (" + kh.getSoDienThoai() + "): ");
        String sdt = scanner.nextLine();
        if (!sdt.isBlank())
            kh.setSoDienThoai(sdt);

        if (khachHangService.capNhatKhachHang(kh)) {
            System.out.println(" Cập nhật thành công!");
        } else {
            System.out.println(" Cập nhật thất bại!");
        }
    }

    // === 5. XÓA KHÁCH HÀNG ===
    private void xoaKhachHang() {
        System.out.println("\n=== XÓA KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng: ");
        String ma = scanner.nextLine();

        KhachHang kh = khachHangService.timKhachHangTheoMa(ma);
        if (kh == null) {
            System.out.println("Không tìm thấy khách hàng!");
            return;
        }

        System.out.println("Khách hàng sẽ bị xóa: " + kh);
        System.out.print("Xác nhận (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (khachHangService.xoaKhachHang(ma)) {
                System.out.println(" Đã xóa khách hàng thành công!");
            } else {
                System.out.println(" Lỗi khi xóa khách hàng!");
            }
        } else {
            System.out.println("Hủy thao tác xóa.");
        }
    }
}
