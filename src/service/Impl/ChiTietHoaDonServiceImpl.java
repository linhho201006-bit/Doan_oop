package service.Impl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import model.ChiTietHoaDon;
import service.ChiTietHoaDonService;
import service.HoaDonService;
import service.KhachHangService;

public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {
    private static final String FILE_PATH = "src/resources/ChiTietHoaDon.txt";

    private List<ChiTietHoaDon> danhSachChiTietHoaDon;
    private int nextMaChiTietHoaDon;
    private HoaDonService hoaDonService;
    private KhachHangService khachHangService;

    public ChiTietHoaDonServiceImpl() {
        danhSachChiTietHoaDon = new ArrayList<>();
        nextMaChiTietHoaDon = 1;
        hoaDonService = null; // sẽ được set từ bên ngoài
        khachHangService = new KhachHangServiceImpl();
        loadData();
    }

    // Setter cho HoaDonService
    public void setHoaDonService(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    // Sinh mã chi tiết hóa đơn tự động
    private String generateMaChiTietHoaDon() {
        return String.format("CTHD%03d", nextMaChiTietHoaDon++);
    }

    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        try {
            // Kiểm tra mã hóa đơn tồn tại
            if (!kiemTraRangBuocHoaDon(chiTietHoaDon.getMaHD())) {
                System.out.println(" Mã hóa đơn không tồn tại: " + chiTietHoaDon.getMaHD());
                return false;
            }

            // Kiểm tra mã khách hàng tồn tại
            if (!kiemTraRangBuocKhachHang(chiTietHoaDon.getMaKH())) {
                System.out.println(" Mã khách hàng không tồn tại: " + chiTietHoaDon.getMaKH());
                return false;
            }

            // Tự động sinh mã chi tiết hóa đơn
            chiTietHoaDon.setMaCTHD(generateMaChiTietHoaDon());
            chiTietHoaDon.tinhThanhTien();

            danhSachChiTietHoaDon.add(chiTietHoaDon);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ChiTietHoaDon> layTatCaChiTietHoaDon() {
        return new ArrayList<>(danhSachChiTietHoaDon);
    }

    @Override
    public ChiTietHoaDon timChiTietHoaDonTheoMa(String maCTHD) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaCTHD().equalsIgnoreCase(maCTHD))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ChiTietHoaDon> timChiTietHoaDonTheoMaKhachHang(String maKH) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaKH().equalsIgnoreCase(maKH))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietHoaDon> timKiemChiTietHoaDon(String tuKhoa) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaCTHD().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaHD().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaKH().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaMay().toLowerCase().contains(tuKhoa.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean capNhatChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        try {
            for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
                if (danhSachChiTietHoaDon.get(i).getMaCTHD().equals(chiTietHoaDon.getMaCTHD())) {

                    // Kiểm tra lại mã hóa đơn
                    if (!kiemTraRangBuocHoaDon(chiTietHoaDon.getMaHD())) {
                        System.out.println(" Mã hóa đơn không tồn tại: " + chiTietHoaDon.getMaHD());
                        return false;
                    }

                    // Kiểm tra mã khách hàng
                    if (!kiemTraRangBuocKhachHang(chiTietHoaDon.getMaKH())) {
                        System.out.println(" Mã khách hàng không tồn tại: " + chiTietHoaDon.getMaKH());
                        return false;
                    }

                    chiTietHoaDon.tinhThanhTien();
                    danhSachChiTietHoaDon.set(i, chiTietHoaDon);
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
    public boolean xoaChiTietHoaDon(String maCTHD) {
        boolean removed = danhSachChiTietHoaDon.removeIf(cthd -> cthd.getMaCTHD().equalsIgnoreCase(maCTHD));
        if (removed)
            saveData();
        return removed;
    }

    @Override
    public boolean xoaTatCaChiTietHoaDonTheoMaHoaDon(String maHD) {
        try {
            // Lọc ra các chi tiết không thuộc mã hóa đơn cần xóa
            List<ChiTietHoaDon> danhSachConLai = danhSachChiTietHoaDon.stream()
                    .filter(ct -> !ct.getMaHD().equals(maHD))
                    .collect(Collectors.toList());

            // Kiểm tra xem có xóa được không
            if (danhSachConLai.size() == danhSachChiTietHoaDon.size()) {
                System.out.println("Không có chi tiết hóa đơn nào thuộc mã: " + maHD);
                return false;
            }

            // Gán lại danh sách và lưu file
            danhSachChiTietHoaDon = danhSachConLai;
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean kiemTraRangBuocHoaDon(String maHD) {
        return hoaDonService != null && hoaDonService.timHoaDonTheoMa(maHD) != null;
    }

    @Override
    public boolean kiemTraRangBuocKhachHang(String maKH) {
        return khachHangService != null && khachHangService.timKhachHangTheoMa(maKH) != null;
    }

    @Override
    public Double tinhTongTienTheoMaHoaDon(String maHD) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaHD().equalsIgnoreCase(maHD))
                .mapToDouble(ChiTietHoaDon::getThanhTien)
                .sum();
    }

    @Override
    public Double tinhTongTienTheoKhachHang(String maKH) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaKH().equalsIgnoreCase(maKH))
                .mapToDouble(ChiTietHoaDon::getThanhTien)
                .sum();
    }

    @Override
    public List<ChiTietHoaDon> thongKeChiTietHoaDonTheoKhachHang() {
        return new ArrayList<>(danhSachChiTietHoaDon);
    }

    // Lưu dữ liệu ra file
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
                writer.println(cthd.getMaCTHD() + "|" +
                        cthd.getMaHD() + "|" +
                        cthd.getMaKH() + "|" +
                        cthd.getMaMay() + "|" +
                        cthd.getDonGia() + "|" +
                        cthd.getSoLuong() + "|" +
                        cthd.getThanhTien());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Đọc dữ liệu từ file
    private void loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    ChiTietHoaDon cthd = new ChiTietHoaDon(
                            parts[0], // maCTHD
                            parts[1], // maHD
                            parts[2], // maKH
                            parts[3], // maSP
                            Double.parseDouble(parts[4]), // donGia
                            Integer.parseInt(parts[5]), // soLuong
                            Double.parseDouble(parts[6]) // thanhTien
                    );
                    danhSachChiTietHoaDon.add(cthd);

                    // cập nhật mã kế tiếp
                    try {
                        int so = Integer.parseInt(parts[0].substring(4));
                        if (so >= nextMaChiTietHoaDon) {
                            nextMaChiTietHoaDon = so + 1;
                        }
                    } catch (NumberFormatException ignore) {
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
