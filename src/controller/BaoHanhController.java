package controller;

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

    // ========================
    // MENU CHÍNH
    // ========================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ PHIẾU BẢO HÀNH ===");
            System.out.println("1. Thêm phiếu bảo hành");
            System.out.println("2. Hiển thị tất cả phiếu bảo hành");
            System.out.println("3. Tìm kiếm phiếu bảo hành");
            System.out.println("4. Cập nhật phiếu bảo hành");
            System.out.println("5. Xóa phiếu bảo hành");
            System.out.println("6. Cập nhật trạng thái bảo hành");
            System.out.println("7. Thống kê bảo hành");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> themBaoHanh();
                case 2 -> hienThiTatCaBaoHanh();
                case 3 -> timKiemBaoHanh();
                case 4 -> capNhatBaoHanh();
                case 5 -> xoaBaoHanh();
                case 6 -> capNhatTrangThai();
                case 7 -> thongKeBaoHanh();
                case 0 -> {
                    System.out.println("Đã thoát khỏi quản lý bảo hành!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ========================
    // 1. THÊM PHIẾU BẢO HÀNH
    // ========================
    private void themBaoHanh() {
        System.out.println("\n=== THÊM PHIẾU BẢO HÀNH MỚI ===");

        System.out.print("Nhập mã bảo hành: ");
        String maBH = scanner.nextLine();

        System.out.print("Nhập mã hóa đơn: ");
        String maHD = scanner.nextLine();
        if (!baoHanhService.kiemTraRangBuocMaHoaDon(maHD)) {
            System.out.println(" Mã hóa đơn không tồn tại!");
            return;
        }

        System.out.print("Nhập mã máy: ");
        String maMay = scanner.nextLine();
        if (!baoHanhService.kiemTraRangBuocMaSanPham(maMay)) {
            System.out.println(" Mã máy không tồn tại!");
            return;
        }

        System.out.print("Nhập ngày bắt đầu (dd/MM/yyyy): ");
        String ngayBatDau = scanner.nextLine();

        System.out.print("Nhập ngày kết thúc (dd/MM/yyyy): ");
        String ngayKetThuc = scanner.nextLine();

        System.out.print("Nhập tình trạng (Còn hiệu lực / Hết hạn / Đang xử lý): ");
        String tinhTrang = scanner.nextLine();

        BaoHanh bh = new BaoHanh(maBH, maHD, maMay, ngayBatDau, ngayKetThuc, tinhTrang);

        if (baoHanhService.taoBaoHanh(bh)) {
            System.out.println(" Thêm phiếu bảo hành thành công!");
        } else {
            System.out.println(" Lỗi khi thêm phiếu bảo hành!");
        }
    }

    // ========================
    // 2. HIỂN THỊ TẤT CẢ
    // ========================
    private void hienThiTatCaBaoHanh() {
        System.out.println("\n=== DANH SÁCH PHIẾU BẢO HÀNH ===");
        List<BaoHanh> ds = baoHanhService.layTatCaBaoHanh();

        if (ds.isEmpty()) {
            System.out.println("Không có phiếu bảo hành nào!");
            return;
        }

        System.out.printf("%-10s %-10s %-10s %-15s %-15s %-15s%n",
                "Mã BH", "Mã HD", "Mã Máy", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Tình Trạng");
        System.out.println("=".repeat(70));

        for (BaoHanh bh : ds) {
            System.out.printf("%-10s %-10s %-10s %-15s %-15s %-15s%n",
                    bh.getMaBH(), bh.getMaHD(), bh.getMaMay(),
                    bh.getNgayBatDau(), bh.getNgayKetThuc(), bh.getTinhTrang());
        }
    }

    // ========================
    // 3. TÌM KIẾM
    // ========================
    private void timKiemBaoHanh() {
        System.out.println("\n=== TÌM KIẾM PHIẾU BẢO HÀNH ===");
        System.out.println("1. Theo mã bảo hành");
        System.out.println("2. Theo mã sản phẩm");
        System.out.println("3. Theo mã hóa đơn");
        System.out.println("4. Theo tên khách hàng");
        System.out.println("5. Theo trạng thái");
        System.out.print("Chọn loại tìm kiếm: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập mã bảo hành: ");
                String maBH = scanner.nextLine();
                BaoHanh bh = baoHanhService.timBaoHanhTheoMa(maBH);
                if (bh != null)
                    hienThiThongTinBaoHanh(bh);
                else
                    System.out.println("Không tìm thấy!");
            }
            case 2 -> {
                System.out.print("Nhập mã sản phẩm: ");
                String maSP = scanner.nextLine();
                BaoHanh bh = baoHanhService.timBaoHanhTheoMaSanPham(maSP);
                if (bh != null)
                    hienThiThongTinBaoHanh(bh);
                else
                    System.out.println("Không tìm thấy!");
            }
            case 3 -> {
                System.out.print("Nhập mã hóa đơn: ");
                String maHD = scanner.nextLine();
                List<BaoHanh> ds = baoHanhService.timKiemBaoHanhTheoMaHoaDon(maHD);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy phiếu nào!");
                else
                    ds.forEach(this::hienThiThongTinBaoHanh);
            }
            case 4 -> {
                System.out.print("Nhập tên khách hàng: ");
                String tenKH = scanner.nextLine();
                List<BaoHanh> ds = baoHanhService.timKiemBaoHanhTheoKhachHang(tenKH);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy phiếu nào!");
                else
                    ds.forEach(this::hienThiThongTinBaoHanh);
            }
            case 5 -> {
                System.out.print("Nhập trạng thái (Còn hiệu lực / Hết hạn / Đang xử lý): ");
                String tt = scanner.nextLine();
                List<BaoHanh> ds = baoHanhService.timKiemBaoHanhTheoTrangThai(tt);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy phiếu nào!");
                else
                    ds.forEach(this::hienThiThongTinBaoHanh);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ========================
    // 4. CẬP NHẬT
    // ========================
    private void capNhatBaoHanh() {
        System.out.print("\nNhập mã bảo hành cần cập nhật: ");
        String maBH = scanner.nextLine();

        BaoHanh bh = baoHanhService.timBaoHanhTheoMa(maBH);
        if (bh == null) {
            System.out.println("Không tìm thấy phiếu bảo hành này!");
            return;
        }

        hienThiThongTinBaoHanh(bh);

        System.out.println("\nNhập thông tin mới (để trống nếu giữ nguyên):");

        System.out.print("Ngày bắt đầu [" + bh.getNgayBatDau() + "]: ");
        String nbd = scanner.nextLine();
        if (!nbd.trim().isEmpty())
            bh.setNgayBatDau(nbd);

        System.out.print("Ngày kết thúc [" + bh.getNgayKetThuc() + "]: ");
        String nkt = scanner.nextLine();
        if (!nkt.trim().isEmpty())
            bh.setNgayKetThuc(nkt);

        System.out.print("Tình trạng [" + bh.getTinhTrang() + "]: ");
        String tt = scanner.nextLine();
        if (!tt.trim().isEmpty())
            bh.setTinhTrang(tt);

        if (baoHanhService.capNhatBaoHanh(bh))
            System.out.println(" Cập nhật thành công!");
        else
            System.out.println(" Cập nhật thất bại!");
    }

    // ========================
    // 5. XÓA
    // ========================
    private void xoaBaoHanh() {
        System.out.print("\nNhập mã bảo hành cần xóa: ");
        String maBH = scanner.nextLine();

        BaoHanh bh = baoHanhService.timBaoHanhTheoMa(maBH);
        if (bh == null) {
            System.out.println("Không tìm thấy phiếu bảo hành!");
            return;
        }

        hienThiThongTinBaoHanh(bh);
        System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (baoHanhService.xoaBaoHanh(maBH))
                System.out.println(" Xóa thành công!");
            else
                System.out.println(" Lỗi khi xóa!");
        } else {
            System.out.println("Hủy thao tác xóa.");
        }
    }

    // ========================
    // 6. CẬP NHẬT TRẠNG THÁI
    // ========================
    private void capNhatTrangThai() {
        System.out.print("\nNhập mã bảo hành cần cập nhật trạng thái: ");
        String maBH = scanner.nextLine();

        System.out.print("Nhập trạng thái mới (Còn hiệu lực / Hết hạn / Đang xử lý): ");
        String trangThai = scanner.nextLine();

        if (baoHanhService.capNhatTrangThaiBaoHanh(maBH, trangThai))
            System.out.println(" Cập nhật trạng thái thành công!");
        else
            System.out.println(" Cập nhật thất bại!");
    }

    // ========================
    // 7. THỐNG KÊ
    // ========================
    private void thongKeBaoHanh() {
        System.out.println("\n=== THỐNG KÊ BẢO HÀNH ===");
        int tong = baoHanhService.tinhTongSoBaoHanh();
        System.out.println("Tổng số phiếu bảo hành: " + tong);

        System.out.print("Nhập trạng thái cần thống kê: ");
        String tt = scanner.nextLine();
        int soLuong = baoHanhService.thongKeSoLuongBaoHanhTheoTrangThai(tt);
        System.out.println("Số lượng phiếu bảo hành có trạng thái '" + tt + "': " + soLuong);
    }

    // ========================
    // HIỂN THỊ CHI TIẾT 1 PHIẾU
    // ========================
    private void hienThiThongTinBaoHanh(BaoHanh bh) {
        System.out.println("--------------------------------------");
        System.out.println("Mã bảo hành: " + bh.getMaBH());
        System.out.println("Mã hóa đơn: " + bh.getMaHD());
        System.out.println("Mã máy: " + bh.getMaMay());
        System.out.println("Ngày bắt đầu: " + bh.getNgayBatDau());
        System.out.println("Ngày kết thúc: " + bh.getNgayKetThuc());
        System.out.println("Tình trạng: " + bh.getTinhTrang());
        System.out.println("--------------------------------------");
    }
}
