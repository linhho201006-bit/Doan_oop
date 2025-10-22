package controller;

import java.util.List;
import java.util.Scanner;

import model.NhaCungCap;
import service.NhaCungCapService;
import service.Impl.NhaCungCapServiceImpl;

public class NhaCungCapController {

    private NhaCungCapService nhaCungCapService;
    private Scanner scanner;

    public NhaCungCapController() {
        nhaCungCapService = new NhaCungCapServiceImpl();
        scanner = new Scanner(System.in);
    }

    // ================= MENU CHÍNH =================
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ NHÀ CUNG CẤP ===");
            System.out.println("1. Thêm nhà cung cấp mới");
            System.out.println("2. Hiển thị danh sách nhà cung cấp");
            System.out.println("3. Tìm kiếm nhà cung cấp");
            System.out.println("4. Cập nhật thông tin nhà cung cấp");
            System.out.println("5. Xóa nhà cung cấp");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // bỏ trôi dòng

            switch (choice) {
                case 1 -> themNhaCungCap();
                case 2 -> hienThiTatCaNhaCungCap();
                case 3 -> timKiemNhaCungCap();
                case 4 -> capNhatNhaCungCap();
                case 5 -> xoaNhaCungCap();
                case 0 -> {
                    System.out.println("Đã thoát quản lý nhà cung cấp!");
                    return;
                }
                default -> System.out.println(" Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= 1. THÊM =================
    private void themNhaCungCap() {
        System.out.println("\n=== THÊM NHÀ CUNG CẤP MỚI ===");
        System.out.print("Tên nhà cung cấp: ");
        String tenNCC = scanner.nextLine();

        System.out.print("Hãng: ");
        String hang = scanner.nextLine();

        System.out.print("Người đại diện: ");
        String nguoiDaiDien = scanner.nextLine();

        System.out.print("Địa chỉ: ");
        String diaChi = scanner.nextLine();

        System.out.print("Số điện thoại: ");
        String sdt = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        NhaCungCap ncc = new NhaCungCap("", hang, tenNCC, nguoiDaiDien, diaChi, sdt, email);

        if (nhaCungCapService.taoNhaCungCap(ncc)) {
            System.out.println(" Thêm nhà cung cấp thành công! Mã NCC: " + ncc.getMaNCC());
        } else {
            System.out.println(" Thêm thất bại!");
        }
    }

    // ================= 2. HIỂN THỊ =================
    private void hienThiTatCaNhaCungCap() {
        System.out.println("\n=== DANH SÁCH NHÀ CUNG CẤP ===");
        List<NhaCungCap> ds = nhaCungCapService.layTatCaNhaCungCap();

        if (ds.isEmpty()) {
            System.out.println("Không có nhà cung cấp nào!");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s %-25s%n",
                "Mã NCC", "Tên NCC", "Hãng", "Người đại diện", "Địa chỉ", "SĐT", "Email");

        for (NhaCungCap ncc : ds) {
            System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s %-25s%n",
                    ncc.getMaNCC(), ncc.getTenNCC(), ncc.getHang(),
                    ncc.getNguoiDaiDien(), ncc.getDiaChi(),
                    ncc.getSoDienThoai(), ncc.getEmail());
        }
    }

    // ================= 3. TÌM KIẾM =================
    private void timKiemNhaCungCap() {
        System.out.println("\n=== TÌM KIẾM NHÀ CUNG CẤP ===");
        System.out.println("1. Theo mã NCC");
        System.out.println("2. Theo tên nhà cung cấp");
        System.out.print("Chọn: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Nhập mã NCC: ");
                String ma = scanner.nextLine();
                NhaCungCap ncc = nhaCungCapService.timNhaCungCapTheoMa(ma);
                if (ncc != null)
                    hienThiThongTinNhaCungCap(ncc);
                else
                    System.out.println(" Không tìm thấy nhà cung cấp!");
            }
            case 2 -> {
                System.out.print("Nhập tên NCC: ");
                String ten = scanner.nextLine();
                List<NhaCungCap> ds = nhaCungCapService.timKiemNhaCungCapTheoTen(ten);
                if (ds.isEmpty())
                    System.out.println(" Không tìm thấy nhà cung cấp!");
                else
                    ds.forEach(this::hienThiThongTinNhaCungCap);
            }
            default -> System.out.println(" Lựa chọn không hợp lệ!");
        }
    }

    // ================= 4. CẬP NHẬT =================
    private void capNhatNhaCungCap() {
        System.out.println("\n=== CẬP NHẬT NHÀ CUNG CẤP ===");
        System.out.print("Nhập mã NCC cần cập nhật: ");
        String ma = scanner.nextLine();

        NhaCungCap ncc = nhaCungCapService.timNhaCungCapTheoMa(ma);
        if (ncc == null) {
            System.out.println(" Không tìm thấy nhà cung cấp!");
            return;
        }

        System.out.println("Để trống nếu không muốn thay đổi:");

        System.out.print("Tên NCC (" + ncc.getTenNCC() + "): ");
        String ten = scanner.nextLine();
        if (!ten.isEmpty())
            ncc.setTenNCC(ten);

        System.out.print("Hãng (" + ncc.getHang() + "): ");
        String hang = scanner.nextLine();
        if (!hang.isEmpty())
            ncc.setHang(hang);

        System.out.print("Người đại diện (" + ncc.getNguoiDaiDien() + "): ");
        String daiDien = scanner.nextLine();
        if (!daiDien.isEmpty())
            ncc.setNguoiDaiDien(daiDien);

        System.out.print("Địa chỉ (" + ncc.getDiaChi() + "): ");
        String diaChi = scanner.nextLine();
        if (!diaChi.isEmpty())
            ncc.setDiaChi(diaChi);

        System.out.print("Số điện thoại (" + ncc.getSoDienThoai() + "): ");
        String sdt = scanner.nextLine();
        if (!sdt.isEmpty())
            ncc.setSoDienThoai(sdt);

        System.out.print("Email (" + ncc.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isEmpty())
            ncc.setEmail(email);

        if (nhaCungCapService.capNhatNhaCungCap(ncc))
            System.out.println(" Cập nhật nhà cung cấp thành công!");
        else
            System.out.println(" Cập nhật thất bại!");
    }

    // ================= 5. XÓA =================
    private void xoaNhaCungCap() {
        System.out.println("\n=== XÓA NHÀ CUNG CẤP ===");
        System.out.print("Nhập mã NCC cần xóa: ");
        String ma = scanner.nextLine();

        if (nhaCungCapService.xoaNhaCungCap(ma))
            System.out.println(" Đã xóa nhà cung cấp " + ma);
        else
            System.out.println(" Xóa thất bại hoặc mã không tồn tại!");
    }

    // ================= HÀM PHỤ =================
    private void hienThiThongTinNhaCungCap(NhaCungCap ncc) {
        System.out.printf("%-10s %-20s %-15s %-20s %-25s %-15s %-25s%n",
                ncc.getMaNCC(), ncc.getTenNCC(), ncc.getHang(),
                ncc.getNguoiDaiDien(), ncc.getDiaChi(),
                ncc.getSoDienThoai(), ncc.getEmail());
    }
}
