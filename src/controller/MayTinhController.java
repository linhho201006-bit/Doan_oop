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
            System.out.println("\n=== Quản lý Máy Tính ===");
            System.out.println("1. Thêm PC");
            System.out.println("2. Thêm LapTop");
            System.out.println("3. Hiển thị tất cả máy tính");
            System.out.println("4. Tìm kiếm máy tính");
            System.out.println("5. Cập  nhật máy tính");
            System.out.println("6. Xóa máy tính");
            System.out.println("7. Thống kê máy tính");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();
            scanner.nextLine();// Đọc kí tự newline connf lại

            switch (choice) {
                case 1:
                    themPC();
                    break;
                case 2:
                    themLapTop();
                    break;
                case 3:
                    hienThiTatCaMayTinh();
                    break;
                case 4:
                    timKiemMayTinh();
                    break;
                case 5:
                    capNhatMayTinh();
                    break;
                case 6:
                    xoaMayTinh();
                    break;
                case 7:
                    thongKeMaytinh();
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dubjg chương trình!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ vui lòng chọn lại!");
            }
        }

    }

    // Thêm PC
    private void themPC() {
        System.out.println("\n=== Thêm PC ===");

        System.out.println("Nhập mã PC: ");
        String maPC = scanner.nextLine();
        System.out.println("Nhập tên PC: ");
        String tenPC = scanner.nextLine();
        System.out.println("Nhập giá PC: ");
        Double giaPC = scanner.nextDouble();
        System.out.println("Nhập hãng sản xuất: ");
        String hangSX = scanner.nextLine();
        System.out.println("Nhập loại CPU: ");
        String loaiCPU = scanner.nextLine();
        System.out.println("Nhập dung lượng RAM: ");
        String ram = scanner.nextLine();
        PC pc = new PC(maPC:"", tenPC, giaPC, hangSX, loaiCPU, ram);

        if(mayTinhService.themPC(pc)) {
            System.out.println("Thêm PC thành công!");
        } else {
            System.out.println("Lỗi khi thêm PC!");
        }
    }

    // Thêm LapTop
    private void themLapTop() {
        System.out.println("\n=== Thêm LapTop ===");
        
        System.out.println("Nhập mã LapTop: ");
        String maLapTop = scanner.nextLine();
        System.out.println("Nhập tên LapTop: ");
        String tenLapTop = scanner.nextLine();
        System.out.println("Nhập giá LapTop: ");
        Double giaLapTop = scanner.nextDouble();
        System.out.println("Nhập hãng sản xuất: ");
        String hangSX = scanner.nextLine();
        System.out.println("Nhập trọng lượng: ");
        Double trongLuong = scanner.nextDouble();
        System.out.println("Nhập kích thước màn hình: ");
        Double kichThuocManHinh = scanner.nextDouble();

        LapTop lapTop = new LapTop(maLapTop:"", tenLapTop, giaLapTop, hangSX, trongLuong, kichThuocManHinh);
        
        if(MayTinhService.themLapTop(lapTop)) {
            System.out.println("Thêm LapTop thành công!");
        } else {
            System.out.println("Lỗi khi thêm LapTop!");
        }
    }

    // Hiển thị tất cả máy tính
    private void hienThiTatCaMayTinh() {
        System.out.println("\n=== DANH SÁCH TẤT CẢ MÁY TÍNH ===");
        List<PC> PC = mayTinhService.layTatCaPC();
        List<LapTop> lapTop = mayTinnhService.layTatCaLapTop();

        if (PC.isEmpty() && lapTop.isEmpty()) {
            System.out.printlnn("Chưa có máy tính nào trong danh sách.");
            return;
        }

        // Hiển thị danh sách PC
        if (!PC.isEmpty()) {
            System.out.printlnn("---- Danh sách PC ----");
            hienThiPC(PC);
        }

        // Hiển thị danh sách LapTop
        if (!lapTop.isEmpty) {
            System.out.println("---- Danh sách LapTop ----");
            hienThiLapTop(lapTop);
        }

        //Hiển thị danh sách PC
        private void hienThiPC(Lit<PC> danhsach) {
            System.out.printf("%-10s %-20s %-10s %-15s %-15s %-10s\n",
            "Mã PC", "Tên PC", "Giá", "Hãng SX", "Loại CPU", "RAM");
            System.out.println("=".repeat(140));

            for (PC pc :danhsach) {
                System.out.printlnn("%-10s %-20s %-10.2f %-15s %-15s %-10s\n",
                pc.getmaPC();
                pc.getTenPC();
                pc.getGia();
                pc.getHangSX();
                pc.getLoaiCPU();
                pc.getRam(););
            }
        }

        //Hiển thị danh sách LapTop
        private void hienThiLapTop(List<lapTop> danhsach) {
            System.out.printf("%-10s %-20s %-10s %-15s %-15s %-10s\n",
            "Mã LapTop", "Tên LapTop", "Giá", "Hãng SX", "Trọng Lượng", "Kích Thước Màn Hình");
            System.out.println("=".repeat(140));

            for (LapTop lapTop : danhsach) {
                System.out.printlnn("%-10s %-20s %-10.2f %-15s %-15.2f %-10.2f\n",
                lapTop.getmaLapTop();
                lapTop.getTenLapTop();
                lapTop.getGia();
                lapTop.getHangSX();
                lapTop.getTrongLuong();
                lapTop.getKichThuocManHinh(););
            }
        }

        // Tìm kiếm máy tính
        private void timKiemMayTinnh() {
        System.out.println("\n=== TÌM KIẾM MÁY TÍNH ===");
        System.out.print("1.");
        }
    }
}
