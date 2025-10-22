package controller;

import java.util.List;
import java.util.Scanner;

import model.NhanVien;
import service.NhanVienService;
import service.Impl.NhanVienServiceImpl;

public class NhanVienController {
    private NhanVienService nhanVienService;
    private Scanner scanner;

    public NhanVienController() {
        nhanVienService = new NhanVienServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ NHÂN VIÊN ===");
            System.out.println("1. Thêm nhân viên mới");
            System.out.println("2. Hiển thị danh sách nhân viên");
            System.out.println("3. Tìm kiếm nhân viên");
            System.out.println("4. Cập nhật thông tin nhân viên");
            System.out.println("5. Xóa nhân viên");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ trôi dòng

            switch (choice) {
                case 1 -> themNhanVien();
                case 2 -> hienThiTatCaNhanVien();
                case 3 -> timKiemNhanVien();
                case 4 -> capNhatNhanVien();
                case 5 -> xoaNhanVien();
                case 0 -> {
                    System.out.println("Đã thoát khỏi menu quản lý nhân viên!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. THÊM NHÂN VIÊN =================
    private void themNhanVien() {
        System.out.println("\n=== THÊM NHÂN VIÊN MỚI ===");
        System.out.print("Nhập họ tên: ");
        String hoTen = scanner.nextLine();
        System.out.print("Nhập vai trò (Admin/Nhân viên/Quản lý...): ");
        String vaiTro = scanner.nextLine();
        System.out.print("Nhập lương: ");
        double luong = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        String diaChi = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String sdt = scanner.nextLine();

        NhanVien nv = new NhanVien("", hoTen, vaiTro, luong, diaChi, sdt);

        if (nhanVienService.themNhanVien(nv)) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.out.println("Thêm nhân viên thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ DANH SÁCH =================
    private void hienThiTatCaNhanVien() {
        System.out.println("\n=== DANH SÁCH NHÂN VIÊN ===");
        List<NhanVien> ds = nhanVienService.layTatCaNhanVien();

        if (ds.isEmpty()) {
            System.out.println("Không có nhân viên nào!");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-12s %-20s %-12s%n",
                "Mã NV", "Họ tên", "Vai trò", "Lương", "Địa chỉ", "SĐT");

        for (NhanVien nv : ds) {
            System.out.printf("%-10s %-20s %-15s %-12.2f %-20s %-12s%n",
                    nv.getMaNV(), nv.getHoTen(), nv.getVaiTro(),
                    nv.getLuong(), nv.getDiachi(), nv.getSdt());
        }
    }

    // ================= 3. TÌM KIẾM NHÂN VIÊN =================
    private void timKiemNhanVien() {
        System.out.println("\n=== TÌM KIẾM NHÂN VIÊN ===");
        System.out.println("1. Theo mã nhân viên");
        System.out.println("2. Theo họ tên");
        System.out.print("Chọn: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        switch (c) {
            case 1 -> {
                System.out.print("Nhập mã nhân viên: ");
                String ma = scanner.nextLine();
                NhanVien nv = nhanVienService.timKiemNhanVienTheoMa(ma);
                if (nv != null)
                    hienThiThongTinNhanVien(nv);
                else
                    System.out.println("Không tìm thấy nhân viên!");
            }
            case 2 -> {
                System.out.print("Nhập họ tên: ");
                String ten = scanner.nextLine();
                List<NhanVien> ds = nhanVienService.timKiemNhanVienTheoTen(ten);
                if (ds.isEmpty())
                    System.out.println("Không tìm thấy nhân viên nào!");
                else
                    ds.forEach(this::hienThiThongTinNhanVien);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT THÔNG TIN =================
    private void capNhatNhanVien() {
        System.out.println("\n=== CẬP NHẬT NHÂN VIÊN ===");
        System.out.print("Nhập mã nhân viên cần cập nhật: ");
        String ma = scanner.nextLine();

        NhanVien nv = nhanVienService.timKiemNhanVienTheoMa(ma);
        if (nv == null) {
            System.out.println("Không tìm thấy nhân viên!");
            return;
        }

        System.out.println("Để trống nếu không muốn thay đổi:");
        System.out.print("Họ tên (" + nv.getHoTen() + "): ");
        String ten = scanner.nextLine();
        if (!ten.isEmpty())
            nv.setHoTen(ten);

        System.out.print("Vai trò (" + nv.getVaiTro() + "): ");
        String vt = scanner.nextLine();
        if (!vt.isEmpty())
            nv.setVaiTro(vt);

        System.out.print("Lương (" + nv.getLuong() + "): ");
        String luongStr = scanner.nextLine();
        if (!luongStr.isEmpty()) {
            try {
                nv.setLuong(Double.parseDouble(luongStr));
            } catch (NumberFormatException e) {
                System.out.println("Lương không hợp lệ!");
            }
        }

        System.out.print("Địa chỉ (" + nv.getDiachi() + "): ");
        String dc = scanner.nextLine();
        if (!dc.isEmpty())
            nv.setDiachi(dc);

        System.out.print("SĐT (" + nv.getSdt() + "): ");
        String sdt = scanner.nextLine();
        if (!sdt.isEmpty())
            nv.setSdt(sdt);

        if (nhanVienService.capNhatNhanVien(nv)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    // ================= 5. XÓA NHÂN VIÊN =================
    private void xoaNhanVien() {
        System.out.println("\n=== XÓA NHÂN VIÊN ===");
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String ma = scanner.nextLine();

        if (nhanVienService.xoaNhanVien(ma)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại hoặc mã không tồn tại!");
        }
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinNhanVien(NhanVien nv) {
        System.out.printf("%-10s %-20s %-15s %-12.2f %-20s %-12s%n",
                nv.getMaNV(), nv.getHoTen(), nv.getVaiTro(),
                nv.getLuong(), nv.getDiachi(), nv.getSdt());
    }
}
