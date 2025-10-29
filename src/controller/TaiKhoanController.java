package controller;

import java.util.List;
import java.util.Scanner;
import model.TaiKhoan;
import service.TaiKhoanService;
import service.Impl.TaiKhoanServiceImpl;

public class TaiKhoanController {
    private TaiKhoanService taiKhoanService;
    private Scanner scanner;

    public TaiKhoanController() {
        taiKhoanService = new TaiKhoanServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ========================
    // MENU CHÍNH
    // ========================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ TÀI KHOẢN ===");
            System.out.println("1. Thêm tài khoản");
            System.out.println("2. Hiển thị tất cả tài khoản");
            System.out.println("3. Tìm kiếm tài khoản");
            System.out.println("4. Cập nhật tài khoản");
            System.out.println("5. Xóa tài khoản");
            System.out.println("6. Đổi mật khẩu");
            System.out.println("7. Thống kê tài khoản theo vai trò");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: themTaiKhoan();
                case 2: hienThiTatCaTaiKhoan();
                case 3: timKiemTaiKhoan();
                case 4: capNhatTaiKhoan();
                case 5: xoaTaiKhoan();
                case 6: doiMatKhau();
                case 7: thongKeTaiKhoan();
                case 0: {
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                }
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ========================
    // 1. THÊM TÀI KHOẢN
    // ========================
    private void themTaiKhoan() {
        System.out.println("\n=== THÊM TÀI KHOẢN MỚI ===");
        System.out.print("Nhập tên đăng nhập: ");
        String tenDangNhap = scanner.nextLine();

        if (taiKhoanService.kiemTraRangBuocTenDangNhap(tenDangNhap)) {
            System.out.println("Tên đăng nhập đã tồn tại!");
            return;
        }

        System.out.print("Nhập mật khẩu: ");
        String matKhau = scanner.nextLine();

        System.out.print("Nhập vai trò (admin, nhanvien, quanly,...): ");
        String vaiTro = scanner.nextLine();

        System.out.print("Nhập mã nhân viên (nếu có): ");
        String maNV = scanner.nextLine();

        if (!maNV.isEmpty() && !taiKhoanService.kiemTraRangBuocMaNV(maNV)) {
            System.out.println(" Mã nhân viên không tồn tại trong hệ thống!");
            return;
        }

        TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, vaiTro, maNV);

        if (taiKhoanService.taoTaiKhoan(taiKhoan)) {
            System.out.println(" Tạo tài khoản thành công!");
        } else {
            System.out.println(" Lỗi khi tạo tài khoản!");
        }
    }

    // ========================
    // 2. HIỂN THỊ TẤT CẢ
    // ========================
    private void hienThiTatCaTaiKhoan() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ TÀI KHOẢN ===");

        List<TaiKhoan> danhSach = taiKhoanService.layTatCaTaiKhoan();

        if (danhSach.isEmpty()) {
            System.out.println("Không có tài khoản nào!");
            return;
        }

        System.out.printf("%-20s %-15s %-10s %-10s%n", "Tên đăng nhập", "Mật khẩu", "Vai trò", "Mã NV");
        System.out.println("=".repeat(60));
        for (TaiKhoan tk : danhSach) {
            System.out.printf("%-20s %-15s %-10s %-10s%n",
                    tk.getTenDangNhap(), tk.getMatKhau(), tk.getVaiTro(), tk.getMaNV());
        }
    }

    // ========================
    // 3. TÌM KIẾM TÀI KHOẢN
    // ========================
    private void timKiemTaiKhoan() {
        System.out.println("\n=== TÌM KIẾM TÀI KHOẢN ===");
        System.out.println("1. Theo tên đăng nhập");
        System.out.println("2. Theo mã nhân viên");
        System.out.print("Chọn loại tìm kiếm: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập tên đăng nhập: ");
                String ten = scanner.nextLine();
                TaiKhoan tk = taiKhoanService.timTaiKhoanTheoTenDangNhap(ten);
                if (tk != null)
                    hienThiThongTinTaiKhoan(tk);
                else
                    System.out.println("Không tìm thấy!");
            }
            case 2 -> {
                System.out.print("Nhập mã nhân viên: ");
                String maNV = scanner.nextLine();
                TaiKhoan tk = taiKhoanService.timTaiKhoanTheoMaNV(maNV);
                if (tk != null)
                    hienThiThongTinTaiKhoan(tk);
                else
                    System.out.println("Không tìm thấy!");
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ========================
    // 4. CẬP NHẬT TÀI KHOẢN
    // ========================
    private void capNhatTaiKhoan() {
        System.out.print("\nNhập tên đăng nhập cần cập nhật: ");
        String tenDangNhap = scanner.nextLine();

        TaiKhoan tk = taiKhoanService.timTaiKhoanTheoTenDangNhap(tenDangNhap);
        if (tk == null) {
            System.out.println("Không tìm thấy tài khoản này!");
            return;
        }

        System.out.println("Thông tin hiện tại:");
        hienThiThongTinTaiKhoan(tk);

        System.out.println("\nNhập thông tin mới (để trống nếu không đổi):");

        System.out.print("Mật khẩu [" + tk.getMatKhau() + "]: ");
        String matKhau = scanner.nextLine();
        if (!matKhau.trim().isEmpty())
            tk.setMatKhau(matKhau);

        System.out.print("Vai trò [" + tk.getVaiTro() + "]: ");
        String vaiTro = scanner.nextLine();
        if (!vaiTro.trim().isEmpty())
            tk.setVaiTro(vaiTro);

        System.out.print("Mã nhân viên [" + tk.getMaNV() + "]: ");
        String maNV = scanner.nextLine();
        if (!maNV.trim().isEmpty())
            tk.setMaNV(maNV);

        if (taiKhoanService.capNhatTaiKhoan(tk)) {
            System.out.println(" Cập nhật tài khoản thành công!");
        } else {
            System.out.println(" Lỗi khi cập nhật tài khoản!");
        }
    }

    // ========================
    // 5. XÓA TÀI KHOẢN
    // ========================
    private void xoaTaiKhoan() {
        System.out.print("\nNhập tên đăng nhập cần xóa: ");
        String tenDangNhap = scanner.nextLine();

        TaiKhoan tk = taiKhoanService.timTaiKhoanTheoTenDangNhap(tenDangNhap);
        if (tk == null) {
            System.out.println("Không tìm thấy tài khoản này!");
            return;
        }

        hienThiThongTinTaiKhoan(tk);
        System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (taiKhoanService.xoaTaiKhoan(tenDangNhap))
                System.out.println(" Xóa tài khoản thành công!");
            else
                System.out.println(" Lỗi khi xóa tài khoản!");
        } else {
            System.out.println("Hủy bỏ thao tác xóa.");
        }
    }

    // ========================
    // 6. ĐỔI MẬT KHẨU
    // ========================
    private void doiMatKhau() {
        System.out.println("\n=== ĐỔI MẬT KHẨU ===");
        System.out.print("Nhập tên đăng nhập: ");
        String tenDangNhap = scanner.nextLine();

        System.out.print("Nhập mật khẩu cũ: ");
        String matKhauCu = scanner.nextLine();

        System.out.print("Nhập mật khẩu mới: ");
        String matKhauMoi = scanner.nextLine();

        if (taiKhoanService.doiMatKhau(tenDangNhap, matKhauCu, matKhauMoi)) {
            System.out.println(" Đổi mật khẩu thành công!");
        } else {
            System.out.println(" Sai mật khẩu hoặc lỗi hệ thống!");
        }
    }

    // ========================
    // 7. THỐNG KÊ THEO VAI TRÒ
    // ========================
    private void thongKeTaiKhoan() {
        System.out.println("\n=== THỐNG KÊ TÀI KHOẢN THEO VAI TRÒ ===");
        System.out.print("Nhập vai trò cần thống kê: ");
        String vaiTro = scanner.nextLine();

        int soLuong = taiKhoanService.thongKeSoLuongTaiKhoanTheoVaiTro(vaiTro);
        System.out.println("Số lượng tài khoản có vai trò '" + vaiTro + "': " + soLuong);
    }

    // ========================
    // HÀM HIỂN THỊ THÔNG TIN CHI TIẾT
    // ========================
    private void hienThiThongTinTaiKhoan(TaiKhoan tk) {
        System.out.println("--------------------------------");
        System.out.println("Tên đăng nhập: " + tk.getTenDangNhap());
        System.out.println("Mật khẩu: " + tk.getMatKhau());
        System.out.println("Vai trò: " + tk.getVaiTro());
        System.out.println("Mã nhân viên: " + tk.getMaNV());
        System.out.println("--------------------------------");
    }
}
