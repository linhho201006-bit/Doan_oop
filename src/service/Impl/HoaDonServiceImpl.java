package service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.HoaDon;
import service.ChiTietHoaDonService;
import service.HoaDonService;
import service.KhachHangService;

public class HoaDonServiceImpl implements HoaDonService {
    private static final String FILE_PATH = "src/resources/HoaDon.txt";

    private List<HoaDon> danhSachHoaDon;
    private int nextMaHoaDon;
    private KhachHangService khachHangService;
    private ChiTietHoaDonService chiTietHoaDonService;

    public HoaDonServiceImpl() {
        danhSachHoaDon = new ArrayList<>();
        nextMaHoaDon = 1;
        khachHangService = new service.Impl.KhachHangServiceImpl();
        chiTietHoaDonService = null; // Sẽ được set sau khi ChiTietHoaDonServiceImpl được tạo
        loadData();
    }

    // Setter cho ChiTietHoaDonService
    public void setChiTietHoaDonService(ChiTietHoaDonService chiTietHoaDonService) {
        this.chiTietHoaDonService = chiTietHoaDonService;
    }

    // Tạo mã hóa đơn tự động
    private String generateMaHoaDon() {
        return String.format("HD%03d", nextMaHoaDon++);
    }

    @Override
    public boolean taoHoaDon(HoaDon hoaDon) {
        try {
            // Kiểm tra ràng buộc: mã khách hàng phải tồn tại
            if (!kiemTraRangBuocKhachHang(hoaDon.getMaKH())) {
                System.out.println("Mã khách hàng không tồn tại: " + hoaDon.getMaKH());
                return false;
            }

            // Tự động tạo mã hóa đơn
            hoaDon.setMaHD(generateMaHoaDon());
            hoaDon.setTrangThai("Chưa thanh toán");

            danhSachHoaDon.add(hoaDon);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HoaDon> layTatCaHoaDon() {
        return new ArrayList<>(danhSachHoaDon);
    }

    @Override
    public HoaDon timHoaDonTheoMa(String maHoaDon) {
        for (HoaDon hd : danhSachHoaDon) {
            if (hd.getMaHD().equals(maHoaDon)) {
                return hd;
            }
        }
        return null;
    }

    @Override
    public List<HoaDon> timHoaDonTheoMaKhachHang(String maKH) {
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getMaKH().equals(maKH))
                .collect(Collectors.toList());
    }

    @Override
    public List<HoaDon> timHoaDonTheoTrangThai(String trangThai) {
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getTrangThai().equalsIgnoreCase(trangThai))
                .collect(Collectors.toList());
    }

    @Override
    public List<HoaDon> timHoaDonTheoPhuongThucThanhToan(String phuongThuc) {
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getPhuongThucThanhToan() != null &&
                        hd.getPhuongThucThanhToan().equalsIgnoreCase(phuongThuc))
                .collect(Collectors.toList());
    }

    @Override
    public List<HoaDon> timHoaDonTheoKhoangNgay(String ngayBatDau, String ngayKetThuc) {
        Date ngayBD = Date.valueOf(ngayBatDau);
        Date ngayKT = Date.valueOf(ngayKetThuc);
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getNgayThanhToan() != null &&
                        !hd.getNgayThanhToan().before(ngayBD) &&
                        !hd.getNgayThanhToan().after(ngayKT))
                .collect(Collectors.toList());
    }

    @Override
    public List<HoaDon> timKiemHoaDon(String tuKhoa) {
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getMaHD().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        hd.getMaKH().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        (hd.getPhuongThucThanhToan() != null &&
                                hd.getPhuongThucThanhToan().toLowerCase().contains(tuKhoa.toLowerCase()))
                        ||
                        hd.getTrangThai().toLowerCase().contains(tuKhoa.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean capNhatHoaDon(HoaDon hoaDon) {
        try {
            for (int i = 0; i < danhSachHoaDon.size(); i++) {
                if (danhSachHoaDon.get(i).getMaHD().equals(hoaDon.getMaHD())) {
                    // Kiểm tra ràng buộc nếu mã khách hàng thay đổi
                    if (!danhSachHoaDon.get(i).getMaKH().equals(hoaDon.getMaKH())) {
                        if (!kiemTraRangBuocKhachHang(hoaDon.getMaKH())) {
                            System.out.println("Mã khách hàng không tồn tại: " + hoaDon.getMaKH());
                            return false;
                        }
                    }

                    danhSachHoaDon.set(i, hoaDon);
                    saveData();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaHoaDon(String maHD) {
        try {
            for (int i = 0; i < danhSachHoaDon.size(); i++) {
                if (danhSachHoaDon.get(i).getMaHD().equals(maHD)) {
                    // Xóa tất cả chi tiết hóa đơn liên quan
                    if (chiTietHoaDonService != null) {
                        chiTietHoaDonService.xoaTatCaChiTietHoaDonTheoMaHoaDon(maHD);
                    }

                    danhSachHoaDon.remove(i);
                    saveData();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean thanhToanHoaDon(String maHD, String phuongThucThanhToan) {
        try {
            HoaDon hoaDon = timHoaDonTheoMa(maHD);
            if (hoaDon == null) {
                System.out.println("Không tìm thấy hóa đơn với mã: " + maHD);
                return false;
            }

            if (!hoaDon.coTheThanhToan()) {
                System.out.println("Hóa đơn này không thể thanh toán!");
                return false;
            }

            // Tính tổng tiền từ chi tiết hóa đơn
            Double tongTien = tinhTongTienHoaDon(maHD);
            if (tongTien == null || tongTien <= 0) {
                System.out.println("Hóa đơn không có chi tiết hoặc tổng tiền không hợp lệ!");
                return false;
            }

            hoaDon.setTongTien(tongTien);
            hoaDon.setPhuongThucThanhToan(phuongThucThanhToan);
            hoaDon.thanhToanHoaDon();

            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean huyHoaDon(String maHD) {
        try {
            HoaDon hoaDon = timHoaDonTheoMa(maHD);
            if (hoaDon == null) {
                System.out.println("Không tìm thấy hóa đơn với mã: " + maHD);
                return false;
            }

            if (!hoaDon.coTheHuy()) {
                System.out.println("Hóa đơn này không thể hủy!");
                return false;
            }

            hoaDon.huyHoaDon();
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean kiemTraRangBuocKhachHang(String maKH) {
        return khachHangService.timKhachHangTheoMa(maKH) != null;
    }

    @Override
    public Double tinhTongTienHoaDon(String maHD) {
        if (chiTietHoaDonService == null) {
            return 0.0;
        }
        return chiTietHoaDonService.tinhTongTienTheoMaHoaDon(maHD);
    }

    @Override
    public List<HoaDon> layHoaDonChuaThanhToan() {
        return timHoaDonTheoTrangThai("Chưa thanh toán");
    }

    @Override
    public List<HoaDon> layHoaDonDaThanhToan() {
        return timHoaDonTheoTrangThai("Đã thanh toán");
    }

    @Override
    public List<HoaDon> layHoaDonDaHuy() {
        return timHoaDonTheoTrangThai("Đã hủy");
    }

    @Override
    public Double tinhTongDoanhThuHoaDon() {
        return danhSachHoaDon.stream()
                .filter(hd -> "Đã thanh toán".equals(hd.getTrangThai()))
                .mapToDouble(hd -> hd.getTongTien() != null ? hd.getTongTien() : 0.0)
                .sum();
    }

    @Override
    public Double tinhTongDoanhThuTheoKhachHang(String maKH) {
        return danhSachHoaDon.stream()
                .filter(hd -> hd.getMaKH().equals(maKH))
                .filter(hd -> "Đã thanh toán".equals(hd.getTrangThai()))
                .mapToDouble(hd -> hd.getTongTien() != null ? hd.getTongTien() : 0.0)
                .sum();
    }

    @Override
    public Double tinhTongDoanhThuTheoKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        return timHoaDonTheoKhoangNgay(ngayBatDau, ngayKetThuc).stream()
                .filter(hd -> "Đã thanh toán".equals(hd.getTrangThai()))
                .mapToDouble(hd -> hd.getTongTien() != null ? hd.getTongTien() : 0.0)
                .sum();
    }

    @Override
    public List<HoaDon> thongKeHoaDonTheoTrangThai() {
        return new ArrayList<>(danhSachHoaDon);
    }

    @Override
    public List<HoaDon> layHoaDonSapHetHanThanhToan() {
        // Tạm thời trả về danh sách hóa đơn chưa thanh toán
        // Có thể cải thiện logic này bằng cách thêm trường NgayTaoHoaDon vào model
        return danhSachHoaDon.stream()
                .filter(hd -> "Chưa thanh toán".equals(hd.getTrangThai()))
                .collect(Collectors.toList());
    }

    // Lưu dữ liệu vào file
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (HoaDon hd : danhSachHoaDon) {
                writer.println(hd.getMaHD() + "|" +
                        hd.getMaKH() + "|" +
                        hd.getTongTien() + "|" +
                        hd.getNgayThanhToan() + "|" +
                        hd.getPhuongThucThanhToan() + "|" +
                        hd.getTrangThai());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc dữ liệu từ file
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    HoaDon hd = new HoaDon(
                            parts[0], // maHoaDon
                            parts[1], // maKhachHang
                            parts[2].equals("null") ? null : Date.valueOf(parts[3]), // ngayThanhToan
                            parts[3].equals("null") ? null : Double.parseDouble(parts[2]), // tienThanhToan
                            parts[4].equals("null") ? null : parts[4], // phuongThucThanhToan
                            parts[5] // trangThai
                    );
                    danhSachHoaDon.add(hd);

                    // Cập nhật nextMaHoaDon
                    String maHoaDon = parts[0];
                    if (maHoaDon.startsWith("HD")) {
                        try {
                            int so = Integer.parseInt(maHoaDon.substring(2));
                            if (so >= nextMaHoaDon) {
                                nextMaHoaDon = so + 1;
                            }
                        } catch (NumberFormatException e) {
                            // Bỏ qua nếu không parse được
                        }
                    }
                }
            }
        } catch (IOException e) {
            // File chưa tồn tại hoặc lỗi đọc file, tạo mới
            try {
                new File(FILE_PATH).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
