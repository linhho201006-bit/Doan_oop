package controller;

import java.util.List;
import java.util.Scanner;
import model.ChiTietPhieuNhapHang;
import service.ChiTietPhieuNhapHangService;
import service.Impl.ChiTietPhieuNhapHangServiceImpl;

public class ChiTietPhieuNhapHangController {

    private ChiTietPhieuNhapHangService chiTietService;
    private Scanner scanner;

    public ChiTietPhieuNhapHangController() {
        chiTietService = new ChiTietPhieuNhapHangServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ CHI TIẾT PHIẾU NHẬP HÀNG ===");
            System.out.println("1. Thêm chi tiết phiếu nhập hàng mới");
            System.out.println("2. Hiển thị tất cả chi tiết phiếu nhập hàng");
            System.out.println("3. Tìm kiếm chi tiết phiếu nhập hàng");
            System.out.println("4. Cập nhật chi tiết phiếu nhập hàng");
            System.out.println("5. Xóa chi tiết phiếu nhập hàng");
            System.out.println("6. Thống kê chi tiết phiếu nhập hàng");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int chon = scanner.nextInt();
            scanner.nextLine();

            switch (chon) {
                case 1 -> themChiTietPhieuNhapHang();
                case 2 -> hienThiTatCaChiTietPhieuNhapHang();
                case 3 -> timKiemChiTietPhieuNhapHang();
                case 4 -> capNhatChiTietPhieuNhapHang();
                case 5 -> xoaChiTietPhieuNhapHang();
                case 6 -> thongKeChiTietPhieuNhapHang();
                case 0 -> {
                    System.out.println(" Thoát quản lý chi tiết phiếu nhập hàng!");
                    return;
                }
                default -> System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. THÊM =================
    private void themChiTietPhieuNhapHang() {
        System.out.println("\n=== THÊM CHI TIẾT PHIẾU NHẬP HÀNG ===");

        System.out.print("Nhập mã phiếu nhập hàng: ");
        String maPhieu = scanner.nextLine();

        System.out.print("Nhập mã máy: ");
        String maMay = scanner.nextLine();

        System.out.print("Nhập số lượng: ");
        int soLuong = scanner.nextInt();

        System.out.print("Nhập đơn giá: ");
        double donGia = scanner.nextDouble();
        scanner.nextLine();

        double thanhTien = soLuong * donGia;

        ChiTietPhieuNhapHang chiTiet = new ChiTietPhieuNhapHang("", maPhieu, maMay, soLuong, donGia, thanhTien);

        if (chiTietService.taoChiTietPhieuNhapHang(chiTiet)) {
            System.out.println(" Thêm chi tiết phiếu nhập hàng thành công!");
        } else {
            System.out.println(" Thêm thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ =================
    private void hienThiTatCaChiTietPhieuNhapHang() {
        System.out.println("\n=== DANH SÁCH CHI TIẾT PHIẾU NHẬP HÀNG ===");

        List<ChiTietPhieuNhapHang> ds = chiTietService.layTatCaChiTietPhieuNhapHang();

        if (ds.isEmpty()) {
            System.out.println("Không có dữ liệu!");
            return;
        }

        System.out.printf("%-10s %-15s %-15s %-10s %-15s %-15s%n",
                "Mã CTPNH", "Mã Phiếu Nhập", "Mã Máy", "SL", "Đơn Giá", "Thành Tiền");

        for (ChiTietPhieuNhapHang ct : ds) {
            System.out.printf("%-10s %-15s %-15s %-10d %-15.2f %-15.2f%n",
                    ct.getMaCTPNH(), ct.getMaPhieuNhap(), ct.getMaMay(),
                    ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien());
        }
    }

    // ================= 3. TÌM KIẾM =================
    private void timKiemChiTietPhieuNhapHang() {
        System.out.println("\n=== TÌM KIẾM CHI TIẾT PHIẾU NHẬP HÀNG ===");
        System.out.println("1. Theo mã chi tiết phiếu nhập hàng");
        System.out.println("2. Theo mã phiếu nhập hàng");
        System.out.println("3. Theo mã máy");
        System.out.println("4. Tìm kiếm tổng hợp");
        System.out.print("Chọn: ");

        int chon = scanner.nextInt();
        scanner.nextLine();

        switch (chon) {
            case 1 -> {
                System.out.print("Nhập mã CTPNH: ");
                String ma = scanner.nextLine();
                ChiTietPhieuNhapHang ct = chiTietService.timChiTietPhieuNhapHangTheoMa(ma);
                if (ct != null)
                    hienThiThongTinChiTiet(ct);
                else
                    System.out.println(" Không tìm thấy!");
            }
            case 2 -> {
                System.out.print("Nhập mã phiếu nhập hàng: ");
                String maPNH = scanner.nextLine();
                List<ChiTietPhieuNhapHang> ds = chiTietService.timKiemChiTietPhieuNhapHangTheoMaPhieuNhapHang(maPNH);
                if (ds.isEmpty())
                    System.out.println("❌ Không tìm thấy!");
                else
                    ds.forEach(this::hienThiThongTinChiTiet);
            }
            case 3 -> {
                System.out.print("Nhập mã máy: ");
                String maMay = scanner.nextLine();
                List<ChiTietPhieuNhapHang> ds = chiTietService.timKiemChiTietPhieuNhapHangTheoMaSanPham(maMay);
                if (ds.isEmpty())
                    System.out.println("❌ Không tìm thấy!");
                else
                    ds.forEach(this::hienThiThongTinChiTiet);
            }
            case 4 -> {
                System.out.print("Nhập từ khóa: ");
                String tuKhoa = scanner.nextLine();
                List<ChiTietPhieuNhapHang> ds = chiTietService.timKiemChiTietPhieuNhapHang(tuKhoa);
                if (ds.isEmpty())
                    System.out.println("❌ Không tìm thấy!");
                else
                    ds.forEach(this::hienThiThongTinChiTiet);
            }
            default -> System.out.println(" Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT =================
    private void capNhatChiTietPhieuNhapHang() {
        System.out.println("\n=== CẬP NHẬT CHI TIẾT PHIẾU NHẬP HÀNG ===");
        System.out.print("Nhập mã chi tiết phiếu nhập hàng: ");
        String ma = scanner.nextLine();

        ChiTietPhieuNhapHang ct = chiTietService.timChiTietPhieuNhapHangTheoMa(ma);
        if (ct == null) {
            System.out.println(" Không tìm thấy!");
            return;
        }

        System.out.println("Để trống nếu không muốn thay đổi:");
        System.out.print("Số lượng (" + ct.getSoLuong() + "): ");
        String soLuongStr = scanner.nextLine();
        if (!soLuongStr.isEmpty())
            ct.setSoLuong(Integer.parseInt(soLuongStr));

        System.out.print("Đơn giá (" + ct.getDonGia() + "): ");
        String donGiaStr = scanner.nextLine();
        if (!donGiaStr.isEmpty())
            ct.setDonGia(Double.parseDouble(donGiaStr));

        ct.setThanhTien(ct.getSoLuong() * ct.getDonGia());

        if (chiTietService.capNhatChiTietPhieuNhapHang(ct))
            System.out.println(" Cập nhật thành công!");
        else
            System.out.println(" Cập nhật thất bại!");
    }

    // ================= 5. XÓA =================
    private void xoaChiTietPhieuNhapHang() {
        System.out.println("\n=== XÓA CHI TIẾT PHIẾU NHẬP HÀNG ===");
        System.out.print("Nhập mã chi tiết phiếu nhập hàng: ");
        String ma = scanner.nextLine();

        if (chiTietService.xoaChiTietPhieuNhapHang(ma))
            System.out.println(" Xóa thành công!");
        else
            System.out.println(" Xóa thất bại!");
    }

    // ================= 6. THỐNG KÊ =================
    private void thongKeChiTietPhieuNhapHang() {
        System.out.println("\n=== THỐNG KÊ CHI TIẾT PHIẾU NHẬP HÀNG ===");
        System.out.println("1. Theo sản phẩm");
        System.out.println("2. Theo nhà cung cấp");
        System.out.print("Chọn: ");
        int chon = scanner.nextInt();
        scanner.nextLine();

        switch (chon) {
            case 1 -> {
                List<ChiTietPhieuNhapHang> ds = chiTietService.thongKeChiTietPhieuNhapHangTheoSanPham();
                if (ds.isEmpty())
                    System.out.println("Không có dữ liệu!");
                else
                    ds.forEach(this::hienThiThongTinChiTiet);
            }
            case 2 -> {
                List<ChiTietPhieuNhapHang> ds = chiTietService.thongKeChiTietPhieuNhapHangTheoNhaCungCap();
                if (ds.isEmpty())
                    System.out.println("Không có dữ liệu!");
                else
                    ds.forEach(this::hienThiThongTinChiTiet);
            }
            default -> System.out.println(" Lựa chọn không hợp lệ!");
        }
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinChiTiet(ChiTietPhieuNhapHang ct) {
        System.out.printf("%-10s %-15s %-15s %-10d %-15.2f %-15.2f%n",
                ct.getMaCTPNH(), ct.getMaPhieuNhap(), ct.getMaMay(),
                ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien());
    }
}
