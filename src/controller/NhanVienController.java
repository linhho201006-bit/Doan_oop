package controller;

import model.NhanVien;
import service.NhanVienService;
import service.Impl.NhanVienServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class NhanVienController {
    private NhanVienService nhanVienService;
    private Scanner scanner;

    public NhanVienController() {
        nhanVienService = new NhanVienServiceImpl();
        scanner = new Scanner(System.in);
    }

    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ NHÂN VIÊN ===");
            System.out.println("1. Thêm mới nhân viên");
            System.out.println("2. Hiển thị tất cả nhân viên");
            System.out.println("3. Tìm kiếm nhân viên");
            System.out.println("4. Cập nhật nhân viên");
            System.out.println("5. Xóa nhân viên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> themNhanVien();
                case 2 -> hienThiTatCaNhanVien();
                case 3 -> timKiemNhanVien();
                case 4 -> capNhatNhanVien();
                case 5 -> xoaNhanVien();
                case 0 -> {
                    System.out.println("Thoát khỏi quản lý nhân viên.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void themNhanVien() {
        System.out.println("\n=== THÊM NHÂN VIÊN MỚI ===");

        System.out.print("Họ và tên: ");
        String tenNV = scanner.nextLine();

        System.out.print("Ngày sinh (yyyy-mm-dd): ");
        Date ngaySinh = Date.valueOf(scanner.nextLine());

        System.out.print("Giới tính: ");
        String gioiTinh = scanner.nextLine();

        System.out.print("Địa chỉ: ");
        String diaChi = scanner.nextLine();

        System.out.print("Vai trò: ");
        String vaiTro = scanner.nextLine();

        System.out.print("CMND: ");
        String cmnd = scanner.nextLine();

        System.out.print("Số điện thoại: ");
        String soDienThoai = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Double luong = nhapLuong();

        // Khởi tạo đúng theo constructor trong NhanVien.java
        NhanVien nhanVien = new NhanVien("", tenNV, ngaySinh, gioiTinh, diaChi, vaiTro, cmnd, soDienThoai, email,
                luong);

        if (nhanVienService.themNhanVien(nhanVien)) {
            System.out.println("✅ Thêm nhân viên thành công!");
        } else {
            System.out.println("❌ Thêm nhân viên thất bại!");
        }
    }

    private void hienThiTatCaNhanVien() {
        System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
        List<NhanVien> danhSach = nhanVienService.layTatCaNhanVien();

        if (danhSach.isEmpty()) {
            System.out.println("Không có dữ liệu!");
            return;
        }

        System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10s%n",
                "Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Vai trò",
                "CMND", "Email", "SĐT", "Lương");
        System.out.println("=".repeat(150));

        for (NhanVien nv : danhSach) {
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1f%n",
                    nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getGioiTinh(),
                    nv.getDiaChi(), nv.getVaiTro(), nv.getCMND(), nv.getEmail(),
                    nv.getSoDienThoai(), nv.getLuong());
        }
    }

    private void timKiemNhanVien() {
        System.out.println("\n=== TÌM KIẾM NHÂN VIÊN ===");
        System.out.println("1. Tìm theo mã nhân viên");
        System.out.println("2. Tìm theo họ tên");
        System.out.println("3. Tìm kiếm tổng hợp");
        System.out.print("Chọn loại tìm kiếm: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        List<NhanVien> ketQua = null;

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập mã nhân viên: ");
                String ma = scanner.nextLine();
                NhanVien nv = nhanVienService.timNhanVienTheoMa(ma);
                ketQua = (nv != null) ? List.of(nv) : List.of();
            }
            case 2 -> {
                System.out.print("Nhập họ tên: ");
                String ten = scanner.nextLine();
                ketQua = nhanVienService.timNhanVienTheoHoTen(ten);
            }
            case 3 -> {
                System.out.print("Nhập từ khóa tìm kiếm: ");
                String tuKhoa = scanner.nextLine();
                ketQua = nhanVienService.timKiemNhanVien(tuKhoa);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }

        hienThiKetQuaTimKiem(ketQua);
    }

    private void hienThiKetQuaTimKiem(List<NhanVien> ketQua) {
        if (ketQua == null || ketQua.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên nào!");
            return;
        }

        System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10s%n",
                "Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Vai trò",
                "CMND", "Email", "SĐT", "Lương");
        System.out.println("=".repeat(150));

        for (NhanVien nv : ketQua) {
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1f%n",
                    nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.getGioiTinh(),
                    nv.getDiaChi(), nv.getVaiTro(), nv.getCMND(), nv.getEmail(),
                    nv.getSoDienThoai(), nv.getLuong());
        }
    }

    private void capNhatNhanVien() {
        System.out.print("\nNhập mã nhân viên cần cập nhật: ");
        String ma = scanner.nextLine();
        NhanVien nv = nhanVienService.timNhanVienTheoMa(ma);

        if (nv == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }

        System.out.println("Thông tin hiện tại:");
        System.out.println(nv);

        System.out.print("Tên mới (Enter để giữ nguyên): ");
        String tenMoi = scanner.nextLine();
        if (!tenMoi.trim().isEmpty())
            nv.setTenNV(tenMoi);

        System.out.print("Địa chỉ mới (Enter để giữ nguyên): ");
        String diaChiMoi = scanner.nextLine();
        if (!diaChiMoi.trim().isEmpty())
            nv.setDiaChi(diaChiMoi);

        System.out.print("Lương mới (Enter để giữ nguyên): ");
        String luongStr = scanner.nextLine();
        if (!luongStr.trim().isEmpty())
            nv.setLuong(Double.parseDouble(luongStr));

        if (nhanVienService.capNhatNhanVien(nv)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private void xoaNhanVien() {
        System.out.print("\nNhập mã nhân viên cần xóa: ");
        String ma = scanner.nextLine();

        if (nhanVienService.xoaNhanVien(ma)) {
            System.out.println("Xóa nhân viên thành công!");
        } else {
            System.out.println("Không tìm thấy nhân viên hoặc xóa thất bại!");
        }
    }

    private Double nhapLuong() {
        while (true) {
            System.out.print("Lương: ");
            String input = scanner.nextLine();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Lương phải là số!");
            }
        }
    }
}
