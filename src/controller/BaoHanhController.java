package controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import model.BaoHanh;
import service.BaoHanhService;
import service.Impl.BaoHanhServiceImpl;

public class BaoHanhController {

    private BaoHanhService baoHanhService;
    private Scanner scanner;

    public BaoHanhController() {
        baoHanhService = new BaoHanhServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ BẢO HÀNH ===");
            System.out.println("1. Thêm bảo hành mới");
            System.out.println("2. Hiển thị danh sách bảo hành");
            System.out.println("3. Tìm kiếm bảo hành");
            System.out.println("4. Cập nhật bảo hành");
            System.out.println("5. Xóa bảo hành");
            System.out.println("6. Thống kê số lượng bảo hành theo trạng thái");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ dòng trôi

            switch (choice) {
                case 1 -> themBaoHanh();
                case 2 -> hienThiTatCa();
                case 3 -> timKiemBaoHanh();
                case 4 -> capNhatBaoHanh();
                case 5 -> xoaBaoHanh();
                case 6 -> thongKeTheoTrangThai();
                case 0 -> {
                    System.out.println("Đã thoát quản lý bảo hành!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. THÊM BẢO HÀNH =================
    private void themBaoHanh() {
        System.out.println("\n=== THÊM BẢO HÀNH MỚI ===");
        System.out.print("Nhập mã máy: ");
        String maMay = scanner.nextLine();

        if (!baoHanhService.kiemTraRangBuocMaSanPham(maMay)) {
            System.out.println("Mã máy không tồn tại!");
            return;
        }

        System.out.print("Nhập mã hóa đơn: ");
        String maHD = scanner.nextLine();

        if (!baoHanhService.kiemTraRangBuocMaHoaDon(maHD)) {
            System.out.println("Mã hóa đơn không tồn tại!");
            return;
        }

        System.out.print("Nhập mã khách hàng: ");
        String maKH = scanner.nextLine();

        System.out.print("Nhập ngày bắt đầu (yyyy-MM-dd): ");
        Date ngayBD = Date.valueOf(scanner.nextLine());

        System.out.print("Nhập ngày kết thúc (yyyy-MM-dd): ");
        Date ngayKT = Date.valueOf(scanner.nextLine());

        System.out.print("Nhập tình trạng (Còn hiệu lực / Hết hạn / Đang xử lý): ");
        String tinhTrang = scanner.nextLine();

        // Mã bảo hành để trống, service có thể tự sinh
        BaoHanh bh = new BaoHanh("", maHD, maMay, maKH, ngayBD, ngayKT, tinhTrang);

        if (baoHanhService.taoBaoHanh(bh)) {
            System.out.println("Thêm bảo hành thành công!");
        } else {
            System.out.println("Thêm bảo hành thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ TẤT CẢ =================
    private void hienThiTatCa() {
        System.out.println("\n=== DANH SÁCH BẢO HÀNH ===");
        List<BaoHanh> ds = baoHanhService.layTatCaBaoHanh();

        if (ds.isEmpty()) {
            System.out.println("Không có bảo hành nào!");
            return;
        }

        System.out.printf("%-10s %-10s %-10s %-10s %-15s %-15s %-15s%n",
                "Mã BH", "Mã HD", "Mã Máy", "Mã KH", "Ngày BĐ", "Ngày KT", "Tình Trạng");

        for (BaoHanh bh : ds) {
            System.out.printf("%-10s %-10s %-10s %-10s %-15s %-15s %-15s%n",
                    bh.getMaBH(), bh.getMaHD(), bh.getMaMay(), bh.getMaKH(),
                    bh.getNgayBatDau(), bh.getNgayKetThuc(), bh.getTinhTrang());
        }
    }

    // ================= 3. TÌM KIẾM =================
    private void timKiemBaoHanh() {
        System.out.println("\n=== TÌM KIẾM BẢO HÀNH ===");
        System.out.println("1. Theo mã bảo hành");
        System.out.println("2. Theo mã máy");
        System.out.print("Chọn: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        switch (c) {
            case 1 -> {
                System.out.print("Nhập mã bảo hành: ");
                String ma = scanner.nextLine();
                BaoHanh bh = baoHanhService.timBaoHanhTheoMaBH(ma);
                if (bh != null)
                    hienThiThongTinBaoHanh(bh);
                else
                    System.out.println("Không tìm thấy!");
            }
            case 2 -> {
                System.out.print("Nhập mã máy: ");
                String maMay = scanner.nextLine();
                BaoHanh bh = baoHanhService.timBaoHanhTheoMaMay(maMay);
                if (bh != null)
                    hienThiThongTinBaoHanh(bh);
                else
                    System.out.println("Không tìm thấy!");
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT =================
    private void capNhatBaoHanh() {
        System.out.println("\n=== CẬP NHẬT BẢO HÀNH ===");
        System.out.print("Nhập mã bảo hành cần cập nhật: ");
        String ma = scanner.nextLine();

        BaoHanh bh = baoHanhService.timBaoHanhTheoMaBH(ma);
        if (bh == null) {
            System.out.println("Không tìm thấy bảo hành!");
            return;
        }

        System.out.print("Ngày bắt đầu (" + bh.getNgayBatDau() + "): ");
        String bd = scanner.nextLine();
        if (!bd.isEmpty())
            bh.setNgayBatDau(Date.valueOf(bd));

        System.out.print("Ngày kết thúc (" + bh.getNgayKetThuc() + "): ");
        String kt = scanner.nextLine();
        if (!kt.isEmpty())
            bh.setNgayKetThuc(Date.valueOf(kt));

        System.out.print("Tình trạng (" + bh.getTinhTrang() + "): ");
        String tt = scanner.nextLine();
        if (!tt.isEmpty())
            bh.setTinhTrang(tt);

        if (baoHanhService.capNhatBaoHanh(bh))
            System.out.println("Cập nhật thành công!");
        else
            System.out.println("Cập nhật thất bại!");
    }

    // ================= 5. XÓA =================
    private void xoaBaoHanh() {
        System.out.println("\n=== XÓA BẢO HÀNH ===");
        System.out.print("Nhập mã bảo hành cần xóa: ");
        String ma = scanner.nextLine();

        if (baoHanhService.xoaBaoHanh(ma))
            System.out.println("Xóa thành công!");
        else
            System.out.println("Không tìm thấy mã bảo hành!");
    }

    // ================= 6. THỐNG KÊ =================
    private void thongKeTheoTrangThai() {
        System.out.println("\n=== THỐNG KÊ BẢO HÀNH THEO TRẠNG THÁI ===");
        System.out.print("Nhập tình trạng: ");
        String tt = scanner.nextLine();
        int soLuong = baoHanhService.thongKeSoLuongBaoHanhTheoTrangThai(tt);
        System.out.println("Số lượng bảo hành có trạng thái '" + tt + "': " + soLuong);
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinBaoHanh(BaoHanh bh) {
        System.out.printf("%-10s %-10s %-10s %-10s %-15s %-15s %-15s%n",
                bh.getMaBH(), bh.getMaHD(), bh.getMaMay(), bh.getMaKH(),
                bh.getNgayBatDau(), bh.getNgayKetThuc(), bh.getTinhTrang());
    }
}
