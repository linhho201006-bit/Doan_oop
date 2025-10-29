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
        chiTietHoaDonService = null;
        loadData();
    }

    public void setChiTietHoaDonService(ChiTietHoaDonService chiTietHoaDonService) {
        this.chiTietHoaDonService = chiTietHoaDonService;
    }

    private String generateMaHoaDon() {
        return String.format("HD%03d", nextMaHoaDon++);
    }

    @Override
    public boolean taoHoaDon(HoaDon hoaDon) {
        try {
            if (!kiemTraRangBuocKhachHang(hoaDon.getMaKH())) {
                System.out.println("Mã khách hàng không tồn tại: " + hoaDon.getMaKH());
                return false;
            }
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
    public Double tinhTongDoanhThuHoaDon() {
        return danhSachHoaDon.stream()
                .filter(hd -> "Đã thanh toán".equals(hd.getTrangThai()))
                .mapToDouble(hd -> hd.getTongTien() != null ? hd.getTongTien() : 0.0)
                .sum();
    }

    @Override
    public List<HoaDon> thongKeHoaDonTheoTrangThai() {
        return new ArrayList<>(danhSachHoaDon);
    }

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

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    HoaDon hd = new HoaDon(
                            parts[0],
                            parts[1],
                            parts[2].equals("null") ? null : Date.valueOf(parts[3]),
                            parts[3].equals("null") ? null : Double.parseDouble(parts[2]),
                            parts[4].equals("null") ? null : parts[4],
                            parts[5]);
                    danhSachHoaDon.add(hd);
                    String maHoaDon = parts[0];
                    if (maHoaDon.startsWith("HD")) {
                        try {
                            int so = Integer.parseInt(maHoaDon.substring(2));
                            if (so >= nextMaHoaDon) {
                                nextMaHoaDon = so + 1;
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                new File(FILE_PATH).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
