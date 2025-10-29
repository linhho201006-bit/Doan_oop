import java.util.Scanner;

import controller.BaoHanhController;
import controller.ChiTietPhieuNhapHangController;
import controller.HoaDonController;
import controller.MayTinhController;
import controller.KhachHangController;
import controller.NhaCungCapController;
import controller.PhieuNhapHangController;
import controller.NhanVienController;
import controller.TaiKhoanController;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HỆ THỐNG QUẢN LÝ BÁN MÁY TÍNH ===");
            System.out.println("1. Quản lý máy tính");
            System.out.println("2. Quản lý khách hàng");
            System.out.println("3. Quản lý nhân viên");
            System.out.println("4. Quản lý tài khoản");
            System.out.println("5. Quản lý nhà cung cấp");
            System.out.println("6. Quản lý hóa đơn");
            System.out.println("7. Quản lý phiếu nhập hàng");
            System.out.println("8. Quản lý chi tiết phiếu nhập hàng");
            System.out.println("9. Quản lý bảo hành");
            System.out.println("0. Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ dòng trôi

            switch (choice) {
                case 1 -> new MayTinhController().hienThiMenu();
                case 2 -> new KhachHangController().hienThiMenu();
                case 3 -> new NhanVienController().hienThiMenu();
                case 4 -> new TaiKhoanController().hienThiMenu();
                case 5 -> new NhaCungCapController().hienThiMenu();
                case 6 -> new HoaDonController().hienThiMenu();
                case 7 -> new PhieuNhapHangController().hienThiMenu();
                case 8 -> new ChiTietPhieuNhapHangController().hienThiMenu();
                case 9 -> new BaoHanhController().hienThiMenu();
                case 0 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                    scanner.close(); // đóng scanner đúng chỗ
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
