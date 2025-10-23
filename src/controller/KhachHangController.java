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

    // Hiển thị menu chính
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
            scanner.nextLine(); // Đọc ký tự newline còn lại

            switch (choice) {
                case 1:
                    themKhachHang();
                    break;
                case 2:
                    hienThiTatCaKhachHang();
                    break;
                case 3:
                    timKiemKhachHang();
                    break;
                case 4:
                    capNhatKhachHang();
                    break;
                case 5:
                    xoaKhachHang();
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // Thêm khách hàng mới
    private void themKhachHang() {
        System.out.println("\n=== THÊM KHÁCH HÀNG MỚI ===");

        System.out.print("Nhập họ và tên: ");
        String hoTen = scanner.nextLine();

        System.out.print("Nhập ngày sinh (yyyy-mm-dd): ");
        String ngaySinhStr = scanner.nextLine();
        Date ngaySinh = Date.valueOf(ngaySinhStr);

        System.out.print("Nhập giới tính: ");
        String gioiTinh = scanner.nextLine();

        System.out.print("Nhập địa chỉ: ");
        String diaChi = scanner.nextLine();

        System.out.print("Nhập CMND: ");
        String CMND = scanner.nextLine();

        System.out.print("Nhập số điện thoại: ");
        String SDT = scanner.nextLine();

        KhachHang khachHang = new KhachHang("", hoTen, ngaySinh, gioiTinh, diaChi, CMND, SDT);

        if (khachHangService.themKhachHang(khachHang)) {
            System.out.println("Thêm khách hàng thành công! Mã khách hàng: " + khachHang.getMaKH());
        } else {
            System.out.println("Lỗi khi thêm khách hàng!");
        }
    }

    // Hiển thị tất cả khách hàng
    private void hienThiTatCaKhachHang() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ KHÁCH HÀNG ===");
        List<KhachHang> danhSach = khachHangService.layTatCaKhachHang();

        if (danhSach.isEmpty()) {
            System.out.println("Không có khách hàng nào!");
        } else {
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s%n",
                    "Mã KH", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "CMND", "SĐT");
            System.out.println("=".repeat(100));

            for (KhachHang kh : danhSach) {
                System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s%n",
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getNgaySinh(),
                        kh.getGioiTinh(),
                        kh.getDiaChi(),
                        kh.getCMND(),
                        kh.getSDT());
            }
        }
    }

    // Tìm kiếm khách hàng
    private void timKiemKhachHang() {
        System.out.println("\n=== TÌM KIẾM KHÁCH HÀNG ===");
        System.out.println("1. Tìm theo mã khách hàng");
        System.out.println("2. Tìm theo họ tên");
        System.out.println("3. Tìm theo CMND");
        System.out.println("4. Tìm theo số điện thoại");
        System.out.println("5. Tìm kiếm tổng hợp");
        System.out.print("Chọn loại tìm kiếm: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        List<KhachHang> ketQua = null;

        switch (choice) {
            case 1:
                System.out.print("Nhập mã khách hàng: ");
                String maKH = scanner.nextLine();
                KhachHang kh = khachHangService.timKhachHangTheoMa(maKH);
                if (kh != null) {
                    ketQua = List.of(kh);
                } else {
                    ketQua = List.of();
                }
                break;
            case 2:
                System.out.print("Nhập họ tên: ");
                String hoTen = scanner.nextLine();
                ketQua = khachHangService.timKhachHangTheoHoTen(hoTen);
                break;
            case 3:
                System.out.print("Nhập CMND: ");
                String cmnd = scanner.nextLine();
                ketQua = khachHangService.timKhachHangTheoCMND(cmnd);
                break;
            case 4:
                System.out.print("Nhập số điện thoại: ");
                String sdt = scanner.nextLine();
                ketQua = khachHangService.timKhachHangTheoSDT(sdt);
                break;
            case 5:
                System.out.print("Nhập từ khóa tìm kiếm: ");
                String tuKhoa = scanner.nextLine();
                ketQua = khachHangService.timKiemKhachHang(tuKhoa);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        hienThiKetQuaTimKiem(ketQua);
    }

    // Hiển thị kết quả tìm kiếm
    private void hienThiKetQuaTimKiem(List<KhachHang> ketQua) {
        if (ketQua == null || ketQua.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng nào!");
        } else {
            System.out.println("\nKết quả tìm kiếm:");
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s%n",
                    "Mã KH", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "CMND", "SĐT");
            System.out.println("=".repeat(100));

            for (KhachHang kh : ketQua) {
                System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s%n",
                        kh.getMaKH(),
                        kh.getHoTen(),
                        kh.getNgaySinh(),
                        kh.getGioiTinh(),
                        kh.getDiaChi(),
                        kh.getCMND(),
                        kh.getSDT());
            }
        }
    }

    // Cập nhật thông tin khách hàng
    private void capNhatKhachHang() {
        System.out.println("\n=== CẬP NHẬT THÔNG TIN KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng cần cập nhật: ");
        String maKh = scanner.nextLine();

        KhachHang khachHang = khachHangService.timKhachHangTheoMa(maKh);
        if (khachHang == null) {
            System.out.println("Không tìm thấy khách hàng với mã: " + maKh);
            return;
        }

        System.out.println("Thông tin hiện tại:");
        System.out.println(khachHang);

        System.out.println("\nNhập thông tin mới (để trống nếu không thay đổi):");

        System.out.print("Họ và tên [" + khachHang.getHoTen() + "]: ");
        String hoTen = scanner.nextLine();
        if (!hoTen.trim().isEmpty()) {
            khachHang.setHoTen(hoTen);
        }

        System.out.print("Ngày sinh [" + khachHang.getNgaySinh() + "] (yyyy-mm-dd): ");
        String ngaySinhStr = scanner.nextLine();
        if (!ngaySinhStr.trim().isEmpty()) {
            khachHang.setNgaySinh(Date.valueOf(ngaySinhStr));
        }

        System.out.print("Giới tính [" + khachHang.getGioiTinh() + "]: ");
        String gioiTinh = scanner.nextLine();
        if (!gioiTinh.trim().isEmpty()) {
            khachHang.setGioiTinh(gioiTinh);
        }

        System.out.print("Địa chỉ [" + khachHang.getDiaChi() + "]: ");
        String diaChi = scanner.nextLine();
        if (!diaChi.trim().isEmpty()) {
            khachHang.setDiaChi(diaChi);
        }

        System.out.print("CMND [" + khachHang.getCMND() + "]: ");
        String cmnd = scanner.nextLine();
        if (!cmnd.trim().isEmpty()) {
            khachHang.setCMND(cmnd);
        }

        System.out.print("Số điện thoại [" + khachHang.getSDT() + "]: ");
        String sdt = scanner.nextLine();
        if (!sdt.trim().isEmpty()) {
            khachHang.setSDT(sdt);
        }

        if (khachHangService.capNhatKhachHang(khachHang)) {
            System.out.println("Cập nhật thông tin khách hàng thành công!");
        } else {
            System.out.println("Lỗi khi cập nhật thông tin khách hàng!");
        }
    }

    // Xóa khách hàng
    private void xoaKhachHang() {
        System.out.println("\n=== XÓA KHÁCH HÀNG ===");
        System.out.print("Nhập mã khách hàng cần xóa: ");
        String maKh = scanner.nextLine();

        KhachHang khachHang = khachHangService.timKhachHangTheoMa(maKh);
        if (khachHang == null) {
            System.out.println("Không tìm thấy khách hàng với mã: " + maKh);
            return;
        }

        System.out.println("Thông tin khách hàng sẽ bị xóa:");
        System.out.println(khachHang);
        System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.toLowerCase().equals("y") || confirm.toLowerCase().equals("yes")) {
            if (khachHangService.xoaKhachHang(maKh)) {
                System.out.println("Xóa khách hàng thành công!");
            } else {
                System.out.println("Lỗi khi xóa khách hàng!");
            }
        } else {
            System.out.println("Hủy bỏ việc xóa khách hàng.");
        }
    }
}
