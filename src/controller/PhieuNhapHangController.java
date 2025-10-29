package controller;

import java.util.List;
import java.util.Scanner;
import model.PhieuNhapHang;
import service.NhaCungCapService;
import service.PhieuNhapHangService;
import service.Impl.NhaCungCapServiceImpl;
import service.Impl.PhieuNhapHangServiceImpl;

public class PhieuNhapHangController {
    private PhieuNhapHangService phieuNhapHangService;
    private Scanner scanner;

    public PhieuNhapHangController() {
        NhaCungCapService nhaCungCapService = new NhaCungCapServiceImpl();
        phieuNhapHangService = new PhieuNhapHangServiceImpl(nhaCungCapService);
        scanner = new Scanner(System.in);
    }

    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ PHIẾU NHẬP HÀNG ===");
            System.out.println("1. Tạo phiếu nhập hàng");
            System.out.println("2. Hiển thị tất cả phiếu nhập");
            System.out.println("3. Tìm kiếm phiếu nhập hàng");
            System.out.println("4. Cập nhật phiếu nhập hàng");
            System.out.println("5. Xóa phiếu nhập hàng");
            System.out.println("6. Thanh toán phiếu nhập hàng");
            System.out.println("7. Hủy phiếu nhập hàng");
            System.out.println("8. Thống kê phiếu nhập hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> taoPhieuNhapHang();
                case 2 -> hienThiTatCaPhieuNhapHang();
                case 3 -> timKiemPhieuNhapHang();
                case 4 -> capNhatPhieuNhapHang();
                case 5 -> xoaPhieuNhapHang();
                case 6 -> thanhToanPhieuNhapHang();
                case 7 -> huyPhieuNhapHang();
                case 8 -> thongKePhieuNhapHang();
                case 0 -> {
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void taoPhieuNhapHang() {
        System.out.println("\n=== TẠO PHIẾU NHẬP HÀNG ===");

        System.out.print("Nhập mã nhà cung cấp: ");
        String maNCC = scanner.nextLine();

        if (!phieuNhapHangService.kiemTraRangBuocMaNhaCungCap(maNCC)) {
            System.out.println("Mã nhà cung cấp không tồn tại!");
            return;
        }

        System.out.print("Nhập ngày nhập (yyyy-MM-dd): ");
        String ngayNhap = scanner.nextLine();

        System.out.print("Nhập tổng tiền: ");
        double tongTien = scanner.nextDouble();
        scanner.nextLine();

        PhieuNhapHang phieu = new PhieuNhapHang();
        phieu.setMaNCC(maNCC);
        phieu.setNgayNhap(java.sql.Date.valueOf(ngayNhap));
        phieu.setTongTien(tongTien);
        phieu.setTrangThai("Chưa thanh toán");

        if (phieuNhapHangService.taoPhieuNhapHang(phieu)) {
            System.out.println("Tạo phiếu nhập hàng thành công!");
        } else {
            System.out.println("Tạo phiếu nhập hàng thất bại!");
        }
    }

    private void hienThiTatCaPhieuNhapHang() {
        System.out.println("\n=== DANH SÁCH PHIẾU NHẬP HÀNG ===");
        List<PhieuNhapHang> ds = phieuNhapHangService.layTatCaPhieuNhapHang();

        if (ds.isEmpty()) {
            System.out.println("Không có phiếu nhập hàng nào!");
            return;
        }

        System.out.printf("%-10s %-10s %-15s %-15s %-20s %-10s %-15s%n",
                "Mã PNH", "Mã NCC", "Ngày nhập", "Ngày TT", "Phương thức", "Tổng tiền", "Trạng thái");
        System.out.println("=".repeat(100));

        for (PhieuNhapHang p : ds) {
            System.out.printf("%-10s %-10s %-15s %-15s %-20s %-10.0f %-15s%n",
                    p.getMaPhieuNhap(),
                    p.getMaNCC(),
                    p.getNgayNhap(),
                    p.getNgayThanhToan() == null ? "Chưa TT" : p.getNgayThanhToan(),
                    p.getPhuongThucThanhToan() == null ? "—" : p.getPhuongThucThanhToan(),
                    p.getTongTien(),
                    p.getTrangThai());
        }
    }

    private void timKiemPhieuNhapHang() {
        System.out.println("\n=== TÌM KIẾM PHIẾU NHẬP HÀNG ===");
        System.out.print("Nhập từ khóa: ");
        String tuKhoa = scanner.nextLine();

        List<PhieuNhapHang> ketQua = phieuNhapHangService.timKiemPhieuNhapHang(tuKhoa);

        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy kết quả phù hợp!");
        } else {
            hienThiKetQuaTimKiem(ketQua);
        }
    }

    private void hienThiKetQuaTimKiem(List<PhieuNhapHang> ketQua) {
        System.out.printf("%-10s %-10s %-15s %-15s %-20s %-10s %-15s%n",
                "Mã PNH", "Mã NCC", "Ngày nhập", "Ngày TT", "Phương thức", "Tổng tiền", "Trạng thái");
        System.out.println("=".repeat(100));

        for (PhieuNhapHang p : ketQua) {
            System.out.printf("%-10s %-10s %-15s %-15s %-20s %-10.0f %-15s%n",
                    p.getMaPhieuNhap(),
                    p.getMaNCC(),
                    p.getNgayNhap(),
                    p.getNgayThanhToan() == null ? "Chưa TT" : p.getNgayThanhToan(),
                    p.getPhuongThucThanhToan() == null ? "—" : p.getPhuongThucThanhToan(),
                    p.getTongTien(),
                    p.getTrangThai());
        }
    }

    private void capNhatPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần cập nhật: ");
        String maPN = scanner.nextLine();

        PhieuNhapHang phieu = phieuNhapHangService.timPhieuNhapHangTheoMa(maPN);
        if (phieu == null) {
            System.out.println("Không tìm thấy phiếu nhập hàng!");
            return;
        }

        System.out.print("Nhập trạng thái mới: ");
        String trangThai = scanner.nextLine();

        if (!trangThai.isEmpty()) {
            phieu.setTrangThai(trangThai);
        }

        if (phieuNhapHangService.capNhatPhieuNhapHang(phieu)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private void xoaPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần xóa: ");
        String maPN = scanner.nextLine();

        if (phieuNhapHangService.xoaPhieuNhapHang(maPN)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    private void thanhToanPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần thanh toán: ");
        String maPN = scanner.nextLine();

        System.out.print("Nhập phương thức thanh toán: ");
        String phuongThuc = scanner.nextLine();

        if (phieuNhapHangService.thanhToanPhieuNhapHang(maPN, phuongThuc)) {
            System.out.println("Thanh toán thành công!");
        } else {
            System.out.println("Thanh toán thất bại!");
        }
    }

    private void huyPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần hủy: ");
        String maPN = scanner.nextLine();

        if (phieuNhapHangService.huyPhieuNhapHang(maPN)) {
            System.out.println("Đã hủy phiếu nhập hàng!");
        } else {
            System.out.println("Hủy thất bại!");
        }
    }

    private void thongKePhieuNhapHang() {
        System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP HÀNG ===");
        System.out.print("Nhập trạng thái muốn thống kê (VD: Đã thanh toán): ");
        String tt = scanner.nextLine();

        Double soLuong = phieuNhapHangService.thongKeSoLuongPhieuNhapHangTheoTrangThai(tt);
        Double tongTien = phieuNhapHangService.tinhTongTienPhieuNhapHang();

        System.out.println("Số lượng phiếu có trạng thái \"" + tt + "\": " + soLuong);
        System.out.println("Tổng tiền tất cả phiếu nhập: " + tongTien + " VNĐ");
    }
}
