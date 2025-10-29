package controller;

import java.util.List;
import java.util.Scanner;

import model.PC;
import model.LapTop;
import model.abstraction.MayTinh;
import service.MayTinhService;
import service.Impl.MayTinhServiceImpl;

public class MayTinhController {

    private MayTinhService mayTinhService;
    private Scanner scanner;

    public MayTinhController() {
        mayTinhService = new MayTinhServiceImpl();
        scanner = new Scanner(System.in);
    }

    // Hiển thị menu chính
    public void hienThiMenu() {
        while (true) {
            System.out.println("\n=== QUẢN LÝ MÁY TÍNH ===");
            System.out.println("1. Thêm PC");
            System.out.println("2. Thêm Laptop");
            System.out.println("3. Hiển thị tất cả máy tính");
            System.out.println("4. Tìm kiếm máy tính");
            System.out.println("5. Cập nhật máy tính");
            System.out.println("6. Xóa máy tính");
            System.out.println("7. Thống kê máy tính");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> themPC();
                case 2 -> themLapTop();
                case 3 -> hienThiTatCaMayTinh();
                case 4 -> timKiemMayTinh();
                case 5 -> capNhatMayTinh();
                case 6 -> xoaMayTinh();
                case 7 -> thongKeMayTinh();
                case 0 -> {
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // === THÊM PC ===
    private void themPC() {
        System.out.println("\n=== THÊM PC ===");
        System.out.print("Nhập tên máy: ");
        String tenMay = scanner.nextLine();
        System.out.print("Nhập hãng sản xuất: ");
        String hangSX = scanner.nextLine();
        System.out.print("Nhập giá: ");
        double gia = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Nhập nhà cung cấp: ");
        String maNCC = scanner.nextLine();
        System.out.print("Nhập dung lượng RAM (GB): ");
        int ram = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nhập loại CPU: ");
        String loaiCPU = scanner.nextLine();

        PC pc = new PC("", tenMay, hangSX, gia, maNCC, ram, loaiCPU);
        if (mayTinhService.themPC(pc)) {
            System.out.println("Thêm PC thành công! Mã: " + pc.getMaMay());
        } else {
            System.out.println("Thêm PC thất bại!");
        }
    }

    // === THÊM LAPTOP ===
    private void themLapTop() {
        System.out.println("\n=== THÊM LAPTOP ===");
        System.out.print("Nhập tên máy: ");
        String tenMay = scanner.nextLine();
        System.out.print("Nhập hãng sản xuất: ");
        String hangSX = scanner.nextLine();
        System.out.print("Nhập giá: ");
        double gia = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Nhập nhà cung cấp: ");
        String maNCC = scanner.nextLine();
        System.out.print("Nhập trọng lượng: ");
        double trongLuong = scanner.nextDouble();
        System.out.print("Nhập kích thước màn hình (inch): ");
        double kichThuocManHinh = scanner.nextDouble();
        scanner.nextLine();

        LapTop laptop = new LapTop("", tenMay, hangSX, gia, maNCC, trongLuong, kichThuocManHinh);
        if (mayTinhService.themLapTop(laptop)) {
            System.out.println("Thêm Laptop thành công! Mã: " + laptop.getMaMay());
        } else {
            System.out.println("Thêm Laptop thất bại!");
        }
    }

    // === HIỂN THỊ TẤT CẢ ===
    private void hienThiTatCaMayTinh() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ MÁY TÍNH ===");

        List<PC> dsPC = mayTinhService.layTatCaPC();
        List<LapTop> dsLap = mayTinhService.layTatCaLapTop();

        if (dsPC.isEmpty() && dsLap.isEmpty()) {
            System.out.println("Không có máy tính nào!");
            return;
        }

        if (!dsPC.isEmpty()) {
            System.out.println("\n--- DANH SÁCH PC ---");
            System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s%n",
                    "Mã", "Tên máy", "Hãng SX", "Giá", "RAM", "CPU");
            for (PC pc : dsPC) {
                System.out.printf("%-10s %-20s %-15s %-10.0f %-10d %-10s%n",
                        pc.getMaMay(), pc.getTenMay(), pc.getHangSX(),
                        pc.getGia(), pc.getRam(), pc.getLoaiCPU());
            }
        }

        if (!dsLap.isEmpty()) {
            System.out.println("\n--- DANH SÁCH LAPTOP ---");
            System.out.printf("%-10s %-20s %-15s %-10s %-15s %-15s%n",
                    "Mã", "Tên máy", "Hãng SX", "Giá", "Trọng lượng", "Màn hình");
            for (LapTop lt : dsLap) {
                System.out.printf("%-10s %-20s %-15s %-10.0f %-15.1f %-15s.1f",
                        lt.getMaMay(), lt.getTenMay(), lt.getHangSX(),
                        lt.getGia(), lt.getTrongLuong(), lt.getKichThuocManHinh());
            }
        }
    }

    // === TÌM KIẾM ===
    private void timKiemMayTinh() {
        System.out.println("\n=== TÌM KIẾM MÁY TÍNH ===");
        System.out.println("1. Theo tên");
        System.out.println("2. Theo khoảng giá");
        System.out.println("3. Theo hãng sản xuất");
        System.out.println("4. PC theo RAM");
        System.out.println("5. PC theo loại CPU");
        System.out.println("6. Laptop theo trọng lượng");
        System.out.println("7. Laptop theo kích thước màn hình");
        System.out.println("8. Tìm kiếm tổng hợp");
        System.out.print("Chọn: ");
        int chon = scanner.nextInt();
        scanner.nextLine();

        List<MayTinh> kq = null;

        switch (chon) {
            case 1 -> {
                System.out.print("Nhập tên máy: ");
                String ten = scanner.nextLine();
                kq = mayTinhService.timKiemMayTinhTheoTen(ten);
            }
            case 3 -> {
                System.out.print("Nhập hãng sản xuất: ");
                String hang = scanner.nextLine();
                kq = mayTinhService.timKiemMayTinhTheoHangSX(hang);
            }
            case 4 -> {
                System.out.print("Nhập RAM (GB): ");
                int ram = scanner.nextInt();
                scanner.nextLine();
                hienThiPC(mayTinhService.timKiemPCTheoRam(ram));
                return;
            }
            case 6 -> {
                System.out.print("Nhập trọng lượng: ");
                Double trongLuong = scanner.nextDouble();
                hienThiLapTop(mayTinhService.timKiemLapTopTheoTrongLuong(trongLuong));
                return;
            }
            case 8 -> {
                System.out.print("Nhập từ khóa: ");
                String tuKhoa = scanner.nextLine();
                kq = mayTinhService.timKiemTongHop(tuKhoa);
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }

        hienThiKetQuaTimKiem(kq);
    }

    private void hienThiKetQuaTimKiem(List<MayTinh> ds) {
        if (ds == null || ds.isEmpty()) {
            System.out.println("Không tìm thấy máy nào!");
            return;
        }

        System.out.printf("%-10s %-20s %-15s %-10s%n", "Mã", "Tên máy", "Hãng SX", "Giá");
        for (MayTinh m : ds) {
            String ma = (m instanceof PC) ? ((PC) m).getMaMay() : ((LapTop) m).getMaMay();
            System.out.printf("%-10s %-20s %-15s %-10.0f%n", ma, m.getTenMay(), m.getHangSX(), m.getGia());
        }
    }

    private void hienThiPC(List<PC> ds) {
        if (ds.isEmpty()) {
            System.out.println("Không có PC nào!");
            return;
        }
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-10s%n",
                "Mã", "Tên", "Hãng SX", "Giá", "RAM", "CPU");
        for (PC pc : ds) {
            System.out.printf("%-10s %-20s %-15s %-10.0f %-10d %-10s%n",
                    pc.getMaMay(), pc.getTenMay(), pc.getHangSX(),
                    pc.getGia(), pc.getRam(), pc.getLoaiCPU());
        }
    }

    private void hienThiLapTop(List<LapTop> ds) {
        if (ds.isEmpty()) {
            System.out.println("Không có Laptop nào!");
            return;
        }
        System.out.printf("%-10s %-20s %-15s %-10s %-15s %-15s",
                "Mã", "Tên", "Hãng SX", "Giá", "Trọng lượng", "Màn hình");
        for (LapTop lt : ds) {
            System.out.printf("%-10s %-20s %-15s %-10.0f %-15.1f %-15s.1f",
                    lt.getMaMay(), lt.getTenMay(), lt.getHangSX(),
                    lt.getGia(), lt.getTrongLuong(), lt.getKichThuocManHinh());
        }
    }

    // === CẬP NHẬT ===
    private void capNhatMayTinh() {
        System.out.println("\n=== CẬP NHẬT MÁY TÍNH ===");
        System.out.println("1. PC");
        System.out.println("2. Laptop");
        System.out.print("Chọn loại: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        if (c == 1)
            capNhatPC();
        else if (c == 2)
            capNhatLapTop();
        else
            System.out.println("Lựa chọn không hợp lệ!");
    }

    private void capNhatPC() {
        System.out.print("Nhập mã PC cần cập nhật: ");
        String ma = scanner.nextLine();
        PC pc = mayTinhService.timKiemPC(ma);
        if (pc == null) {
            System.out.println("Không tìm thấy PC!");
            return;
        }

        System.out.print("Tên [" + pc.getTenMay() + "]: ");
        String ten = scanner.nextLine();
        if (!ten.isEmpty())
            pc.setTenMay(ten);

        System.out.print("Hãng SX [" + pc.getHangSX() + "]: ");
        String hang = scanner.nextLine();
        if (!hang.isEmpty())
            pc.setHangSX(hang);

        System.out.print("Giá [" + pc.getGia() + "]: ");
        String giaStr = scanner.nextLine();
        if (!giaStr.isEmpty())
            pc.setGia(Double.parseDouble(giaStr));

        System.out.print("RAM [" + pc.getRam() + "]: ");
        String ramStr = scanner.nextLine();
        if (!ramStr.isEmpty())
            pc.setRam(Integer.parseInt(ramStr));

        System.out.print("CPU [" + pc.getLoaiCPU() + "]: ");
        String cpu = scanner.nextLine();
        if (!cpu.isEmpty())
            pc.setLoaiCPU(cpu);

        if (mayTinhService.capNhatPC(pc))
            System.out.println("Cập nhật PC thành công!");
        else
            System.out.println("Cập nhật thất bại!");
    }

    private void capNhatLapTop() {
        System.out.print("Nhập mã Laptop cần cập nhật: ");
        String ma = scanner.nextLine();
        LapTop lt = mayTinhService.timKiemLapTopTheoMa(ma);
        if (lt == null) {
            System.out.println("Không tìm thấy Laptop!");
            return;
        }

        System.out.print("Tên [" + lt.getTenMay() + "]: ");
        String ten = scanner.nextLine();
        if (!ten.isEmpty())
            lt.setTenMay(ten);

        System.out.print("Hãng SX [" + lt.getHangSX() + "]: ");
        String hang = scanner.nextLine();
        if (!hang.isEmpty())
            lt.setHangSX(hang);

        System.out.print("Giá [" + lt.getGia() + "]: ");
        String giaStr = scanner.nextLine();
        if (!giaStr.isEmpty())
            lt.setGia(Double.parseDouble(giaStr));

        System.out.print("Trọng lượng [" + lt.getTrongLuong() + "]: ");
        String trongLuong = scanner.nextLine();
        if (!trongLuong.isEmpty())
            lt.setTrongLuong(Double.parseDouble(trongLuong));

        System.out.print("Màn hình [" + lt.getKichThuocManHinh() + "]: ");
        String manStr = scanner.nextLine();
        if (!manStr.isEmpty())
            lt.setKichThuocManHinh(Double.parseDouble(manStr));

        if (mayTinhService.capNhatLapTop(lt))
            System.out.println("Cập nhật Laptop thành công!");
        else
            System.out.println("Cập nhật thất bại!");
    }

    // === XÓA ===
    private void xoaMayTinh() {
        System.out.println("\n=== XÓA MÁY TÍNH ===");
        System.out.println("1. PC");
        System.out.println("2. Laptop");
        System.out.print("Chọn loại: ");
        int c = scanner.nextInt();
        scanner.nextLine();

        if (c == 1) {
            System.out.print("Nhập mã PC: ");
            String ma = scanner.nextLine();
            if (mayTinhService.xoaPC(ma))
                System.out.println("Đã xóa PC!");
            else
                System.out.println("Không tìm thấy hoặc lỗi!");
        } else if (c == 2) {
            System.out.print("Nhập mã Laptop: ");
            String ma = scanner.nextLine();
            if (mayTinhService.xoaLapTop(ma))
                System.out.println("Đã xóa Laptop!");
            else
                System.out.println("Không tìm thấy hoặc lỗi!");
        } else {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    // === THỐNG KÊ ===
    private void thongKeMayTinh() {
        List<PC> dsPC = mayTinhService.layTatCaPC();
        List<LapTop> dsLap = mayTinhService.layTatCaLapTop();
        System.out.println("\n=== THỐNG KÊ MÁY TÍNH ===");
        System.out.println("Tổng số PC: " + dsPC.size());
        System.out.println("Tổng số Laptop: " + dsLap.size());
        System.out.println("Tổng cộng: " + (dsPC.size() + dsLap.size()));

        if (!dsPC.isEmpty()) {
            double tbPC = dsPC.stream().mapToDouble(MayTinh::getGia).average().orElse(0);
            System.out.println("Giá trung bình PC: " + String.format("%.0f VNĐ", tbPC));
        }
        if (!dsLap.isEmpty()) {
            double tbLap = dsLap.stream().mapToDouble(MayTinh::getGia).average().orElse(0);
            System.out.println("Giá trung bình Laptop: " + String.format("%.0f VNĐ", tbLap));
        }
    }
}
