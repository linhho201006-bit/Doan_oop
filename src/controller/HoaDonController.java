package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.HoaDon;
import service.HoaDonService;
import service.Impl.HoaDonServiceImpl;

public class HoaDonController {

    private HoaDonService hoaDonService;
    private Scanner scanner;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public HoaDonController() {
        hoaDonService = new HoaDonServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ HÓA ĐƠN ===");
            System.out.println("1. Tạo hóa đơn mới");
            System.out.println("2. Hiển thị tất cả hóa đơn");
            System.out.println("3. Tìm kiếm hóa đơn");
            System.out.println("4. Cập nhật hóa đơn");
            System.out.println("5. Xóa hóa đơn");
            System.out.println("6. Thanh toán hóa đơn");
            System.out.println("7. Hủy hóa đơn");
            System.out.println("8. Thống kê doanh thu");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ trôi dòng

            switch (choice) {
                case 1 -> taoHoaDon();
                case 2 -> hienThiTatCaHoaDon();
                case 3 -> timKiemHoaDon();
                case 4 -> capNhatHoaDon();
                case 5 -> xoaHoaDon();
                case 6 -> thanhToanHoaDon();
                case 7 -> huyHoaDon();
                case 8 -> thongKeDoanhThu();
                case 0 -> {
                    System.out.println("Đã thoát khỏi chương trình quản lý hóa đơn!");
                    return;
                }
                default -> System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. TẠO HÓA ĐƠN =================
    private void taoHoaDon() {
        System.out.println("\n=== TẠO HÓA ĐƠN MỚI ===");
        System.out.print("Nhập mã khách hàng: ");
        String maKH = scanner.nextLine();

        if (!hoaDonService.kiemTraRangBuocKhachHang(maKH)) {
            System.out.println(" Mã khách hàng không tồn tại!");
            return;
        }

        System.out.print("Nhập ngày thanh toán (yyyy-MM-dd): ");
        String ngayStr = scanner.nextLine();
        Date ngayThanhToan;
        try {
            ngayThanhToan = sdf.parse(ngayStr);
        } catch (ParseException e) {
            System.out.println(" Ngày không hợp lệ!");
            return;
        }

        System.out.print("Nhập tổng tiền: ");
        Double tongTien = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Nhập phương thức thanh toán (Tiền mặt/Chuyển khoản/...): ");
        String phuongThuc = scanner.nextLine();

        String trangThai = "Chưa thanh toán";

        java.sql.Date sqlNgayThanhToan = new java.sql.Date(ngayThanhToan.getTime());
        HoaDon hd = new HoaDon("", maKH, sqlNgayThanhToan, tongTien, phuongThuc, trangThai);

        if (hoaDonService.taoHoaDon(hd)) {
            System.out.println(" Tạo hóa đơn thành công! Mã HD: " + hd.getMaHD());
        } else {
            System.out.println(" Tạo hóa đơn thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ TẤT CẢ =================
    private void hienThiTatCaHoaDon() {
        System.out.println("\n=== DANH SÁCH HÓA ĐƠN ===");
        List<HoaDon> ds = hoaDonService.layTatCaHoaDon();

        if (ds.isEmpty()) {
            System.out.println("Không có hóa đơn nào!");
            return;
        }

        System.out.printf("%-10s %-10s %-15s %-12s %-20s %-15s%n",
                "Mã HD", "Mã KH", "Ngày TT", "Tổng tiền", "Phương thức", "Trạng thái");

        for (HoaDon hd : ds) {
            System.out.printf("%-10s %-10s %-15s %-12.0f %-20s %-15s%n",
                    hd.getMaHD(), hd.getMaKH(), sdf.format(hd.getNgayThanhToan()),
                    hd.getTongTien(), hd.getPhuongThucThanhToan(), hd.getTrangThai());
        }
    }

    // ================= 3. TÌM KIẾM =================
    private void timKiemHoaDon() {
        System.out.println("\n=== TÌM KIẾM HÓA ĐƠN ===");
        System.out.println("1. Theo mã hóa đơn");
        System.out.println("2. Theo mã khách hàng");
        System.out.print("Chọn: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        switch (c) {
            case 1 -> {
                System.out.print("Nhập mã hóa đơn: ");
                String ma = scanner.nextLine();
                HoaDon hd = hoaDonService.timHoaDonTheoMa(ma);
                if (hd != null)
                    hienThiThongTinHoaDon(hd);
                else
                    System.out.println(" Không tìm thấy hóa đơn!");
            }
            case 2 -> {
                System.out.print("Nhập mã khách hàng: ");
                String maKH = scanner.nextLine();
                List<HoaDon> ds = hoaDonService.timHoaDonTheoMaKhachHang(maKH);
                if (ds.isEmpty())
                    System.out.println(" Không tìm thấy hóa đơn!");
                else
                    ds.forEach(this::hienThiThongTinHoaDon);
            }
            default -> System.out.println(" Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT =================
    private void capNhatHoaDon() {
        System.out.println("\n=== CẬP NHẬT HÓA ĐƠN ===");
        System.out.print("Nhập mã hóa đơn cần cập nhật: ");
        String ma = scanner.nextLine();

        HoaDon hd = hoaDonService.timHoaDonTheoMa(ma);
        if (hd == null) {
            System.out.println(" Không tìm thấy hóa đơn!");
            return;
        }

        System.out.println("Để trống nếu không thay đổi:");

        System.out.print("Ngày thanh toán (" + sdf.format(hd.getNgayThanhToan()) + "): ");
        String ngayStr = scanner.nextLine();
        if (!ngayStr.isEmpty()) {
            try {
                java.util.Date utilDate = sdf.parse(ngayStr);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                hd.setNgayThanhToan(sqlDate);
            } catch (ParseException e) {
                System.out.println("Ngày không hợp lệ!");
            }

        }

        System.out.print("Tổng tiền (" + hd.getTongTien() + "): ");
        String tongStr = scanner.nextLine();
        if (!tongStr.isEmpty())
            hd.setTongTien(Double.parseDouble(tongStr));

        System.out.print("Phương thức (" + hd.getPhuongThucThanhToan() + "): ");
        String pt = scanner.nextLine();
        if (!pt.isEmpty())
            hd.setPhuongThucThanhToan(pt);

        System.out.print("Trạng thái (" + hd.getTrangThai() + "): ");
        String tt = scanner.nextLine();
        if (!tt.isEmpty())
            hd.setTrangThai(tt);

        if (hoaDonService.capNhatHoaDon(hd))
            System.out.println(" Cập nhật hóa đơn thành công!");
        else
            System.out.println(" Cập nhật thất bại!");
    }

    // ================= 5. XÓA =================
    private void xoaHoaDon() {
        System.out.println("\n=== XÓA HÓA ĐƠN ===");
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        String ma = scanner.nextLine();

        if (hoaDonService.xoaHoaDon(ma))
            System.out.println(" Xóa hóa đơn thành công!");
        else
            System.out.println(" Không tìm thấy hóa đơn!");
    }

    // ================= 6. THANH TOÁN =================
    private void thanhToanHoaDon() {
        System.out.println("\n=== THANH TOÁN HÓA ĐƠN ===");
        System.out.print("Nhập mã hóa đơn cần thanh toán: ");
        String ma = scanner.nextLine();

        System.out.print("Nhập phương thức thanh toán: ");
        String pt = scanner.nextLine();

        if (hoaDonService.thanhToanHoaDon(ma, pt))
            System.out.println(" Thanh toán thành công!");
        else
            System.out.println(" Thanh toán thất bại!");
    }

    // ================= 7. HỦY HÓA ĐƠN =================
    private void huyHoaDon() {
        System.out.println("\n=== HỦY HÓA ĐƠN ===");
        System.out.print("Nhập mã hóa đơn cần hủy: ");
        String ma = scanner.nextLine();

        if (hoaDonService.huyHoaDon(ma))
            System.out.println(" Đã hủy hóa đơn " + ma);
        else
            System.out.println(" Hủy thất bại!");
    }

    // ================= 8. THỐNG KÊ DOANH THU =================
    private void thongKeDoanhThu() {
        System.out.println("\n=== THỐNG KÊ DOANH THU ===");
        Double tong = hoaDonService.tinhTongDoanhThuHoaDon();
        System.out.println("Tổng doanh thu: " + tong);
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinHoaDon(HoaDon hd) {
        System.out.printf("%-10s %-10s %-15s %-12.0f %-20s %-15s%n",
                hd.getMaHD(), hd.getMaKH(), sdf.format(hd.getNgayThanhToan()),
                hd.getTongTien(), hd.getPhuongThucThanhToan(), hd.getTrangThai());
    }
}
