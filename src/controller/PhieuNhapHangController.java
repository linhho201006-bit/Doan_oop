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
        // ✅ Khởi tạo NhaCungCapService trước
        NhaCungCapService nhaCungCapService = new NhaCungCapServiceImpl();

        // ✅ Truyền vào PhieuNhapHangServiceImpl
        phieuNhapHangService = new PhieuNhapHangServiceImpl(nhaCungCapService);
        scanner = new Scanner(System.in);
    }

    // ==================== MENU CHÍNH ====================
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

    // ==================== 1. TẠO PHIẾU NHẬP HÀNG ====================
    private void taoPhieuNhapHang() {
        System.out.println("\n=== TẠO PHIẾU NHẬP HÀNG ===");

        System.out.print("Nhập mã nhà cung cấp: ");
        String maNCC = scanner.nextLine();

        if (!phieuNhapHangService.kiemTraRangBuocMaNhaCungCap(maNCC)) {
            System.out.println(" Mã nhà cung cấp không tồn tại!");
            return;
        }

        System.out.print("Nhập ngày nhập (yyyy-MM-dd): ");
        String ngayNhap = scanner.nextLine();

        System.out.print("Nhập tổng tiền: ");
        Double tongTien = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Nhập phương thức thanh toán (Tiền mặt/Chuyển khoản): ");
        String phuongThuc = scanner.nextLine();

        PhieuNhapHang phieu = new PhieuNhapHang();
        phieu.setMaNhaCungCap(maNCC);
        phieu.setNgayNhap(java.sql.Date.valueOf(ngayNhap));
        phieu.setTongTien(tongTien);
        phieu.setPhuongThucThanhToan(phuongThuc);
        phieu.setTrangThai("Chưa thanh toán");

        if (phieuNhapHangService.taoPhieuNhapHang(phieu)) {
            System.out.println(" Tạo phiếu nhập hàng thành công!");
        } else {
            System.out.println(" Tạo phiếu nhập hàng thất bại!");
        }
    }

    // ==================== 2. HIỂN THỊ TẤT CẢ ====================
    private void hienThiTatCaPhieuNhapHang() {
        System.out.println("\n=== DANH SÁCH PHIẾU NHẬP HÀNG ===");
        List<PhieuNhapHang> ds = phieuNhapHangService.layTatCaPhieuNhapHang();

        if (ds.isEmpty()) {
            System.out.println("Không có phiếu nhập hàng nào!");
            return;
        }

        System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15s %-15s%n",
                "Mã PN", "Mã NCC", "Ngày nhập", "Ngày TT", "Phương thức", "Tổng tiền", "Trạng thái");
        System.out.println("=".repeat(105));

        for (PhieuNhapHang p : ds) {
            System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15.0f %-15s%n",
                    p.getMaPhieuNhap(),
                    p.getMaNhaCungCap(),
                    p.getNgayNhap(),
                    p.getNgayThanhToan() == null ? "Chưa TT" : p.getNgayThanhToan(),
                    p.getPhuongThucThanhToan(),
                    p.getTongTien(),
                    p.getTrangThai());
        }
    }

    // ==================== 3. TÌM KIẾM ====================
    private void timKiemPhieuNhapHang() {
        System.out.println("\n=== TÌM KIẾM PHIẾU NHẬP HÀNG ===");
        System.out.println("1. Theo mã phiếu");
        System.out.println("2. Theo mã nhà cung cấp");
        System.out.println("3. Theo ngày nhập");
        System.out.println("4. Theo ngày thanh toán");
        System.out.println("5. Theo trạng thái");
        System.out.println("6. Theo phương thức thanh toán");
        System.out.println("7. Theo khoảng thời gian");
        System.out.println("8. Tìm kiếm tổng hợp");
        System.out.print("Chọn loại tìm kiếm: ");
        int chon = scanner.nextInt();
        scanner.nextLine();

        List<PhieuNhapHang> ketQua = null;

        switch (chon) {
            case 1 -> {
                System.out.print("Nhập mã phiếu: ");
                String maPN = scanner.nextLine();
                PhieuNhapHang p = phieuNhapHangService.timPhieuNhapHangTheoMa(maPN);
                ketQua = p != null ? List.of(p) : List.of();
            }
            case 2 -> {
                System.out.print("Nhập mã nhà cung cấp: ");
                String maNCC = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHangTheoNhaCungCap(maNCC);
            }
            case 3 -> {
                System.out.print("Nhập ngày nhập (yyyy-MM-dd): ");
                String ngay = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHangTheoNgayNhap(ngay);
            }
            case 4 -> {
                System.out.print("Nhập ngày thanh toán (yyyy-MM-dd): ");
                String ngayTT = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHangTheoNgayThanhToan(ngayTT);
            }
            case 5 -> {
                System.out.print("Nhập trạng thái: ");
                String tt = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHangTheoTrangThai(tt);
            }
            case 6 -> {
                System.out.print("Nhập phương thức thanh toán: ");
                String pt = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHangTheoPhuongThucThanhToan(pt);
            }
            case 7 -> {
                System.out.print("Từ ngày (yyyy-MM-dd): ");
                String tu = scanner.nextLine();
                System.out.print("Đến ngày (yyyy-MM-dd): ");
                String den = scanner.nextLine();
                ketQua = phieuNhapHangService.timPhieuNhapHangTheoKhoangThoiGian(tu, den);
            }
            case 8 -> {
                System.out.print("Nhập từ khóa tổng hợp: ");
                String tuKhoa = scanner.nextLine();
                ketQua = phieuNhapHangService.timKiemPhieuNhapHang(tuKhoa);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }

        if (ketQua != null && !ketQua.isEmpty()) {
            hienThiKetQuaTimKiem(ketQua);
        } else {
            System.out.println(" Không tìm thấy kết quả phù hợp!");
        }
    }

    private void hienThiKetQuaTimKiem(List<PhieuNhapHang> ketQua) {
        System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15s %-15s%n",
                "Mã PN", "Mã NCC", "Ngày nhập", "Ngày TT", "Phương thức", "Tổng tiền", "Trạng thái");
        System.out.println("=".repeat(105));

        for (PhieuNhapHang p : ketQua) {
            System.out.printf("%-10s %-15s %-15s %-15s %-20s %-15.0f %-15s%n",
                    p.getMaPhieuNhap(),
                    p.getMaNhaCungCap(),
                    p.getNgayNhap(),
                    p.getNgayThanhToan() == null ? "Chưa TT" : p.getNgayThanhToan(),
                    p.getPhuongThucThanhToan(),
                    p.getTongTien(),
                    p.getTrangThai());
        }
    }

    // ==================== 4. CẬP NHẬT ====================
    private void capNhatPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần cập nhật: ");
        String maPN = scanner.nextLine();

        PhieuNhapHang phieu = phieuNhapHangService.timPhieuNhapHangTheoMa(maPN);
        if (phieu == null) {
            System.out.println(" Không tìm thấy phiếu nhập hàng với mã " + maPN);
            return;
        }

        System.out.println("Thông tin hiện tại: " + phieu);
        System.out.println("Nhập thông tin mới (bỏ trống nếu không thay đổi):");

        System.out.print("Phương thức thanh toán [" + phieu.getPhuongThucThanhToan() + "]: ");
        String phuongThuc = scanner.nextLine();
        if (!phuongThuc.isEmpty())
            phieu.setPhuongThucThanhToan(phuongThuc);

        System.out.print("Trạng thái [" + phieu.getTrangThai() + "]: ");
        String trangThai = scanner.nextLine();
        if (!trangThai.isEmpty())
            phieu.setTrangThai(trangThai);

        if (phieuNhapHangService.capNhatPhieuNhapHang(phieu)) {
            System.out.println(" Cập nhật thành công!");
        } else {
            System.out.println(" Cập nhật thất bại!");
        }
    }

    // ==================== 5. XÓA ====================
    private void xoaPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần xóa: ");
        String maPN = scanner.nextLine();

        System.out.print("Bạn có chắc chắn muốn xóa (y/n)? ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (phieuNhapHangService.xoaPhieuNhapHang(maPN)) {
                System.out.println(" Xóa thành công!");
            } else {
                System.out.println(" Xóa thất bại!");
            }
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

    // ==================== 6. THANH TOÁN ====================
    private void thanhToanPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần thanh toán: ");
        String maPN = scanner.nextLine();

        System.out.print("Nhập phương thức thanh toán: ");
        String phuongThuc = scanner.nextLine();

        if (phieuNhapHangService.thanhToanPhieuNhapHang(maPN, phuongThuc)) {
            System.out.println(" Thanh toán thành công!");
        } else {
            System.out.println(" Thanh toán thất bại!");
        }
    }

    // ==================== 7. HỦY PHIẾU ====================
    private void huyPhieuNhapHang() {
        System.out.print("Nhập mã phiếu cần hủy: ");
        String maPN = scanner.nextLine();

        if (phieuNhapHangService.huyPhieuNhapHang(maPN)) {
            System.out.println(" Đã hủy phiếu nhập hàng!");
        } else {
            System.out.println(" Hủy thất bại!");
        }
    }

    // ==================== 8. THỐNG KÊ ====================
    private void thongKePhieuNhapHang() {
        System.out.println("\n=== THỐNG KÊ PHIẾU NHẬP HÀNG ===");
        System.out.print("Nhập khoảng thời gian (yyyy-MM-dd):\nTừ ngày: ");
        String tu = scanner.nextLine();
        System.out.print("Đến ngày: ");
        String den = scanner.nextLine();

        Double tongTien = phieuNhapHangService.thongKeTongTienNhapHangTheoKhoangThoiGian(tu, den);
        System.out.println("Tổng tiền nhập hàng từ " + tu + " đến " + den + ": " + tongTien + " VNĐ");

        System.out.print("Nhập trạng thái muốn thống kê (VD: Đã thanh toán): ");
        String tt = scanner.nextLine();
        Double soLuong = phieuNhapHangService.thongKeSoLuongPhieuNhapHangTheoTrangThai(tt);
        System.out.println("Số lượng phiếu nhập trạng thái \"" + tt + "\": " + soLuong);
    }
}
