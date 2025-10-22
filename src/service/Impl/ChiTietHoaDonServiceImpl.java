package service.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
        hoaDonService = null; // Sẽ được set sau khi HoaDonServiceImpl được tạo
        khachHangService = new service.Impl.KhachHangServiceImpl();
        loadData();
    }

    // Setter cho HoaDonService
    public void setHoaDonService(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    // Tạo mã chi tiết hóa đơn tự động
    private String generateMaChiTietHoaDon() {
        return String.format("CTHD%03d", nextMaChiTietHoaDon++);
    }

    @Override
    public boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        try {
            // Kiểm tra ràng buộc: mã hóa đơn phải tồn tại
            if (!kiemTraRangBuocHoaDon(chiTietHoaDon.getMaHoaDon())) {
                System.out.println("Mã hóa đơn không tồn tại: " + chiTietHoaDon.getMaHoaDon());
                return false;
            }

            // Kiểm tra ràng buộc: mã khách hàng phải tồn tại
            if (!kiemTraRangBuocKhachHang(chiTietHoaDon.getMaKhachHang())) {
                System.out.println("Mã khách hàng không tồn tại: " + chiTietHoaDon.getMaKhachHang());
                return false;
            }

            // Tự động tạo mã chi tiết hóa đơn
            chiTietHoaDon.setMaChiTietHoaDon(generateMaChiTietHoaDon());

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
    public ChiTietHoaDon timChiTietHoaDonTheoMa(String maChiTietHoaDon) {
        for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
            if (cthd.getMaChiTietHoaDon().equals(maChiTietHoaDon)) {
                return cthd;
            }
        }
        return null;
    }

    @Override
    public List<ChiTietHoaDon> timChiTietHoaDonTheoMaHoaDon(String maHoaDon) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaHoaDon().equals(maHoaDon))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietHoaDon> timChiTietHoaDonTheoMaKhachHang(String maKhachHang) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaKhachHang().equals(maKhachHang))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietHoaDon> timKiemChiTietHoaDon(String tuKhoa) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaChiTietHoaDon().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaVe().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaKhachHang().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                        cthd.getMaHoaDon().toLowerCase().contains(tuKhoa.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean capNhatChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        try {
            for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
                if (danhSachChiTietHoaDon.get(i).getMaChiTietHoaDon().equals(chiTietHoaDon.getMaChiTietHoaDon())) {
                    // Kiểm tra ràng buộc nếu mã hóa đơn thay đổi
                    if (!danhSachChiTietHoaDon.get(i).getMaHoaDon().equals(chiTietHoaDon.getMaHoaDon())) {
                        if (!kiemTraRangBuocHoaDon(chiTietHoaDon.getMaHoaDon())) {
                            System.out.println("Mã hóa đơn không tồn tại: " + chiTietHoaDon.getMaHoaDon());
                            return false;
                        }
                    }

                    // Kiểm tra ràng buộc nếu mã khách hàng thay đổi
                    if (!danhSachChiTietHoaDon.get(i).getMaKhachHang().equals(chiTietHoaDon.getMaKhachHang())) {
                        if (!kiemTraRangBuocKhachHang(chiTietHoaDon.getMaKhachHang())) {
                            System.out.println("Mã khách hàng không tồn tại: " + chiTietHoaDon.getMaKhachHang());
                            return false;
                        }
                    }

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
    public boolean xoaChiTietHoaDon(String maChiTietHoaDon) {
        try {
            for (int i = 0; i < danhSachChiTietHoaDon.size(); i++) {
                if (danhSachChiTietHoaDon.get(i).getMaChiTietHoaDon().equals(maChiTietHoaDon)) {
                    danhSachChiTietHoaDon.remove(i);
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
    public boolean xoaTatCaChiTietHoaDonTheoMaHoaDon(String maHoaDon) {
        try {
            danhSachChiTietHoaDon.removeIf(cthd -> cthd.getMaHoaDon().equals(maHoaDon));
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean kiemTraRangBuocHoaDon(String maHoaDon) {
        if (hoaDonService == null) {
            return false;
        }
        return hoaDonService.timHoaDonTheoMa(maHoaDon) != null;
    }

    @Override
    public boolean kiemTraRangBuocKhachHang(String maKhachHang) {
        return khachHangService.timKhachHangTheoMa(maKhachHang) != null;
    }

    @Override
    public Double tinhTongTienTheoMaHoaDon(String maHoaDon) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaHoaDon().equals(maHoaDon))
                .mapToDouble(cthd -> cthd.getThanhTien() != null ? cthd.getThanhTien() : 0.0)
                .sum();
    }

    @Override
    public Double tinhTongTienTheoKhachHang(String maKhachHang) {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaKhachHang().equals(maKhachHang))
                .mapToDouble(cthd -> cthd.getThanhTien() != null ? cthd.getThanhTien() : 0.0)
                .sum();
    }

    @Override
    public int demSoChiTietHoaDonTheoMaHoaDon(String maHoaDon) {
        return (int) danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getMaHoaDon().equals(maHoaDon))
                .count();
    }

    @Override
    public ChiTietHoaDon layChiTietHoaDonCoThanhTienCaoNhat() {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getThanhTien() != null)
                .max((cthd1, cthd2) -> Double.compare(cthd1.getThanhTien(), cthd2.getThanhTien()))
                .orElse(null);
    }

    @Override
    public ChiTietHoaDon layChiTietHoaDonCoThanhTienThapNhat() {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getThanhTien() != null)
                .min((cthd1, cthd2) -> Double.compare(cthd1.getThanhTien(), cthd2.getThanhTien()))
                .orElse(null);
    }

    @Override
    public Double tinhThanhTienTrungBinh() {
        return danhSachChiTietHoaDon.stream()
                .filter(cthd -> cthd.getThanhTien() != null)
                .mapToDouble(ChiTietHoaDon::getThanhTien)
                .average()
                .orElse(0.0);
    }

    @Override
    public List<ChiTietHoaDon> thongKeChiTietHoaDonTheoKhachHang() {
        return new ArrayList<>(danhSachChiTietHoaDon);
    }

    // Lưu dữ liệu vào file
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (ChiTietHoaDon cthd : danhSachChiTietHoaDon) {
                writer.println(cthd.getMaChiTietHoaDon() + "|" +
                        cthd.getMaKhachHang() + "|" +
                        cthd.getMaHoaDon() + "|" +
                        cthd.getSoLuongKhach() + "|" +
                        cthd.getThanhTien());
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
                    ChiTietHoaDon cthd = new ChiTietHoaDon(
                            parts[0], // maChiTietHoaDon
                            parts[1], // maVe
                            parts[2], // maKhachHang
                            parts[3], // maHoaDon
                            Integer.parseInt(parts[4]), // soLuongKhach
                            Double.parseDouble(parts[5]) // thanhTien
                    );
                    danhSachChiTietHoaDon.add(cthd);

                    // Cập nhật nextMaChiTietHoaDon
                    String maChiTietHoaDon = parts[0];
                    if (maChiTietHoaDon.startsWith("CTHD")) {
                        try {
                            int so = Integer.parseInt(maChiTietHoaDon.substring(4));
                            if (so >= nextMaChiTietHoaDon) {
                                nextMaChiTietHoaDon = so + 1;
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
