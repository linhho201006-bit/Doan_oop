package controller;

import model.NhanVien;
import service.NhanVienService;
import service.Impl.NhanVienServiceImpl;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class NhanVienController {
    private NhanVienService nhanVienService;
    private Scanner scanner;

    public NhanVienController() {
        nhanVienService = new NhanVienServiceImpl();
        scanner = new Scanner(System.in);
    }

    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ NHÂN VIÊN ===");
            System.out.println("1. Thêm mới nhân viên");
            System.out.println("2. Hiển thị tất cả nhân viên");
            System.out.println("3. Tìm kiếm nhân viên");
            System.out.println("4. Cập nhật nhân viên");
            System.out.println("5. Xóa nhân viên");
            System.out.println("0. Quay lại");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    themNhanVien();
                    break;
                case 2:
                    hienThiTatCaNhanVien();
                    break;
                case 3:
                    timKiemNhanVien();
                    break;
                case 4:
                    capNhatNhanVien();
                    break;
                case 5:
                    xoaNhanVien();
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void themNhanVien() {
        System.out.println("\n=== THÊM MỚI NHÂN VIÊN ===");

        System.out.print("Tên nhân viên: ");
        String hoTen = scanner.nextLine();

        System.out.print("Nhập ngày sinh (yyyy-mm-dd): ");
        String ngaySinhNV = scanner.nextLine();
        Date ngaySinh = Date.valueOf(ngaySinhNV);

        System.out.print("Giới tính: ");
        String gioiTinh = scanner.nextLine();

        System.out.print("Địa chỉ: ");
        String diaChi = scanner.nextLine();

        System.out.print("Vai trò: ");
        String vaiTro = scanner.nextLine();

        System.out.print("CMND: ");
        String CMND = scanner.nextLine();

        System.out.print("Số điện thoại: ");
        String SDT = scanner.nextLine();

        System.out.print("Email: ");
        String Email = scanner.nextLine();

        Double luong = nhapLuong();

        NhanVien nhanVien = new NhanVien("", hoTen, ngaySinh, gioiTinh, diaChi, vaiTro, CMND, SDT, Email, luong);

        if (nhanVienService.themNhanVien(nhanVien)) {
            System.out.println("Thêm thành công! Mã nhân viên: " + nhanVien.getMaNV());
        } else {
            System.out.println("Lỗi khi thêm nhân viên!");
        }
    }

    private void hienThiTatCaNhanVien() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ NHÂN VIÊN ===");
        List<NhanVien> danhSachnhanvien = nhanVienService.layTatCaNhanVien();

        if (danhSachnhanvien.isEmpty()) {
            System.out.println("Không có dữ liệu!");
        } else {
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1s%n",
                    "Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Vai trò", "CMND", "Email", "SĐT", "Lương");
            System.out.println("=".repeat(160));

            for (NhanVien nv : danhSachnhanvien) {
                System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1f%n",
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getNgaySinh(),
                        nv.getGioiTinh(),
                        nv.getDiaChi(),
                        nv.getVaiTro(),
                        nv.getCMND(),
                        nv.getEmail(),
                        nv.getSDT(),
                        nv.getLuong());
            }
        }
    }

    private void timKiemNhanVien() {
        System.out.println("\n=== TÌM KIẾM NHÂN VIÊN ===");
        System.out.println("1. Tìm theo mã nhân viên");
        System.out.println("2. Tìm theo họ tên");
        System.out.println("3. Tìm theo CMND");
        System.out.println("4. Tìm theo số điện thoại");
        System.out.println("5. Tìm kiếm tổng hợp");
        System.out.print("Chọn loại tìm kiếm: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        List<NhanVien> ketQua = null;

        switch (choice) {
            case 1:
                System.out.print("Nhập mã nhân viên: ");
                String maNV = scanner.nextLine();
                NhanVien nv = nhanVienService.timNhanVienTheoMa(maNV);
                if (nv != null) {
                    ketQua = List.of(nv);
                } else {
                    ketQua = List.of();
                }
                break;
            case 2:
                System.out.print("Nhập họ tên: ");
                String hoTen = scanner.nextLine();
                ketQua = nhanVienService.timNhanVienTheoHoTen(hoTen);
                break;
            case 3:
                System.out.print("Nhập CMND: ");
                String CMND = scanner.nextLine();
                ketQua = nhanVienService.timNhanVienTheoCMND(CMND);
                break;
            case 4:
                System.out.print("Nhập số điện thoại: ");
                String SDT = scanner.nextLine();
                ketQua = nhanVienService.timNhanVienTheoSDT(SDT);
                break;
            case 5:
                System.out.print("Nhập từ khóa tìm kiếm: ");
                String tuKhoa = scanner.nextLine();
                ketQua = nhanVienService.timKiemNhanVien(tuKhoa);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        hienThiKetQuaTimKiem(ketQua);
    }

    // Hiển thị kết quả tìm kiếm
    private void hienThiKetQuaTimKiem(List<NhanVien> ketQua) {
        if (ketQua == null || ketQua.isEmpty()) {
            System.out.println("Không tìm thấy nhân viên nào!");
        } else {
            System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1s%n",
                    "Mã NV", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Vai trò", "CMND", "Email", "SĐT", "Lương");
            System.out.println("=".repeat(160));

            for (NhanVien nv : ketQua) {
                System.out.printf("%-8s %-20s %-12s %-8s %-20s %-15s %-12s %-25s %-12s %-10.1f%n",
                        nv.getMaNV(),
                        nv.getHoTen(),
                        nv.getNgaySinh(),
                        nv.getGioiTinh(),
                        nv.getDiaChi(),
                        nv.getVaiTro(),
                        nv.getCMND(),
                        nv.getEmail(),
                        nv.getSDT(),
                        nv.getLuong());
            }
        }
    }

    private void capNhatNhanVien() {
        System.out.println("\n=== CẬP NHẬT NHÂN VIÊN ===");
        System.out.print("Nhập mã nhân viên cần cập nhật: ");
        String maNV = scanner.nextLine();

        NhanVien nhanVien = nhanVienService.timNhanVienTheoMa(maNV);

        if (nhanVien == null) {
            System.out.println("Không tìm thấy nhân viên: " + maNV);
            return;
        }
        System.out.println("Thông tin hiện tại:");
        System.out.println(nhanVien);

        System.out.println("\nNhập thông tin mới (để trống nếu không thay đổi):");

        System.out.print("Họ và tên [" + nhanVien.getHoTen() + "]: ");
        String hoTen = scanner.nextLine();
        if (!hoTen.trim().isEmpty()) {
            nhanVien.setHoTen(hoTen);
        }

        System.out.print("Ngày sinh [" + nhanVien.getNgaySinh() + "] (yyyy-mm-dd): ");
        String ngaySinhNV = scanner.nextLine();
        if (!ngaySinhNV.trim().isEmpty()) {
            nhanVien.setNgaySinh(Date.valueOf(ngaySinhNV));
        }

        System.out.print("Giới tính [" + nhanVien.getGioiTinh() + "]: ");
        String gioiTinh = scanner.nextLine();
        if (!gioiTinh.trim().isEmpty()) {
            nhanVien.setGioiTinh(gioiTinh);
        }

        System.out.print("Địa chỉ [" + nhanVien.getDiaChi() + "]: ");
        String diaChi = scanner.nextLine();
        if (!diaChi.trim().isEmpty()) {
            nhanVien.setDiaChi(diaChi);
        }

        System.out.print("Vai Trò [" + nhanVien.getVaiTro() + "]: ");
        String vaiTro = scanner.nextLine();
        if (!vaiTro.trim().isEmpty()) {
            nhanVien.setVaiTro(vaiTro);
        }

        System.out.print("CMND [" + nhanVien.getCMND() + "]: ");
        String CMND = scanner.nextLine();
        if (!CMND.trim().isEmpty()) {
            nhanVien.setCMND(CMND);
        }

        System.out.print("Số điện thoại [" + nhanVien.getSDT() + "]: ");
        String SDT = scanner.nextLine();
        if (!SDT.trim().isEmpty()) {
            nhanVien.setSDT(SDT);
        }

        System.out.print("Lương [" + nhanVien.getLuong() + "]: ");
        String luongStr = scanner.nextLine(); // đọc dạng chuỗi

        if (!luongStr.trim().isEmpty()) { // nếu không trống
            double luong = Double.parseDouble(luongStr); // chuyển sang double
            nhanVien.setLuong(luong);
        }

        if (nhanVienService.capNhatNhanVien(nhanVien)) {
            System.out.println("Cập nhật thông tin nhân viên thành công!");
        } else {
            System.out.println("Lỗi khi cập nhật thông tin nhân viên!");
        }
    }

    private void xoaNhanVien() {
        System.out.println("\n=== XÓA NHÂN VIÊN ===");
        System.out.print("Nhập mã nhân viên cần xóa: ");
        String maNV = scanner.nextLine();

        NhanVien nhanVien = nhanVienService.timNhanVienTheoMa(maNV);
        if (nhanVien == null) {
            System.out.println("Không tìm thấy nhân viên với mã: " + maNV);
            return;
        }

        System.out.println("Thông tin nhân viên sẽ bị xóa:");
        System.out.println(nhanVien);
        System.out.print("Bạn có chắc chắn muốn xóa? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.toLowerCase().equals("y") || confirm.toLowerCase().equals("yes")) {
            if (nhanVienService.xoaNhanVien(maNV)) {
                System.out.println("Xóa nhân viên thành công!");
            } else {
                System.out.println("Lỗi khi xóa nhân viên!");
            }
        } else {
            System.out.println("Hủy bỏ việc xóa nhân viên.");
        }
    }

    private Double nhapLuong(boolean... optional) {
        while (true) {
            System.out.print("Lương: ");
            String input = scanner.nextLine();
            if (optional.length > 0 && optional[0] && input.trim().isEmpty()) {
                return null;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Lương phải là một số. Vui lòng nhập lại.");
            }
        }
    }
}