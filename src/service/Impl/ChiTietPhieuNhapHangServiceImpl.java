package service.Impl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import model.ChiTietPhieuNhapHang;
import service.ChiTietPhieuNhapHangService;

public class ChiTietPhieuNhapHangServiceImpl implements ChiTietPhieuNhapHangService {
    private final String FILE_PATH = "src/resources/ChiTietPhieuNhapHang.txt";
    private List<ChiTietPhieuNhapHang> danhSachChiTiet = new ArrayList<>();

    public ChiTietPhieuNhapHangServiceImpl() {
        docDuLieuTuFile();
    }

    // ==========================
    // 1️⃣ Lưu & đọc file
    // ==========================
    private void luuDuLieuRaFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ChiTietPhieuNhapHang c : danhSachChiTiet) {
                bw.write(String.join("|",
                        c.getMaCTPNH(),
                        c.getMaPhieuNhapHang(),
                        c.getMaSanPham(),
                        String.valueOf(c.getSoLuong()),
                        String.valueOf(c.getDonGia()),
                        String.valueOf(c.getThanhTien()),
                        c.getMaNhaCungCap(),
                        c.getNgayNhap()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file ChiTietPhieuNhapHang: " + e.getMessage());
        }
    }

    private void docDuLieuTuFile() {
        danhSachChiTiet.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 8) {
                    ChiTietPhieuNhapHang ct = new ChiTietPhieuNhapHang(
                            parts[0], parts[1], parts[2],
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]),
                            parts[6], parts[7]);
                    danhSachChiTiet.add(ct);
                }
            }
        } catch (IOException e) {
            System.out.println("File ChiTietPhieuNhapHang chưa tồn tại hoặc lỗi đọc file.");
        }
    }

    // ==========================
    // 2️⃣ CRUD cơ bản
    // ==========================
    @Override
    public boolean taoChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet) {
        if (chiTiet == null || timChiTietPhieuNhapHangTheoMa(chiTiet.getMaCTPNH()) != null)
            return false;
        danhSachChiTiet.add(chiTiet);
        luuDuLieuRaFile();
        return true;
    }

    @Override
    public List<ChiTietPhieuNhapHang> layTatCaChiTietPhieuNhapHang() {
        return new ArrayList<>(danhSachChiTiet);
    }

    @Override
    public ChiTietPhieuNhapHang timChiTietPhieuNhapHangTheoMa(String maCTPNH) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaCTPNH().equalsIgnoreCase(maCTPNH))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet) {
        for (int i = 0; i < danhSachChiTiet.size(); i++) {
            if (danhSachChiTiet.get(i).getMaCTPNH().equalsIgnoreCase(chiTiet.getMaCTPNH())) {
                danhSachChiTiet.set(i, chiTiet);
                luuDuLieuRaFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaChiTietPhieuNhapHang(String maCTPNH) {
        boolean removed = danhSachChiTiet.removeIf(c -> c.getMaCTPNH().equalsIgnoreCase(maCTPNH));
        if (removed)
            luuDuLieuRaFile();
        return removed;
    }

    @Override
    public boolean xoaChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang) {
        boolean removed = danhSachChiTiet.removeIf(c -> c.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang));
        if (removed)
            luuDuLieuRaFile();
        return removed;
    }

    // ==========================
    // 3️⃣ Tìm kiếm
    // ==========================
    @Override
    public List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaSanPham(String maSanPham) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaSanPham().equalsIgnoreCase(maSanPham))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHang(String tuKhoa) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaCTPNH().toLowerCase().contains(tuKhoa.toLowerCase())
                        || c.getMaSanPham().toLowerCase().contains(tuKhoa.toLowerCase())
                        || c.getMaPhieuNhapHang().toLowerCase().contains(tuKhoa.toLowerCase())
                        || c.getMaNhaCungCap().toLowerCase().contains(tuKhoa.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ==========================
    // 4️⃣ Kiểm tra ràng buộc
    // ==========================
    @Override
    public boolean kiemTraRangBuocMaSanPham(String maSanPham) {
        return danhSachChiTiet.stream().anyMatch(c -> c.getMaSanPham().equalsIgnoreCase(maSanPham));
    }

    @Override
    public boolean kiemTraRangBuocMaPhieuNhapHang(String maPhieuNhapHang) {
        return danhSachChiTiet.stream().anyMatch(c -> c.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang));
    }

    // ==========================
    // 5️⃣ Tính toán & thống kê
    // ==========================
    @Override
    public Double tinhTongTienChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang))
                .mapToDouble(ChiTietPhieuNhapHang::getThanhTien)
                .sum();
    }

    @Override
    public Double tinhTongTienChiTietPhieuNhapHangTheoNhaCungCap(String maNhaCungCap) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaNhaCungCap().equalsIgnoreCase(maNhaCungCap))
                .mapToDouble(ChiTietPhieuNhapHang::getThanhTien)
                .sum();
    }

    @Override
    public Double thongKeTongTienChiTietPhieuNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getNgayNhap().compareTo(tuNgay) >= 0 && c.getNgayNhap().compareTo(denNgay) <= 0)
                .mapToDouble(ChiTietPhieuNhapHang::getThanhTien)
                .sum();
    }

    @Override
    public List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoSanPham() {
        // Trả về danh sách nhóm theo sản phẩm
        return new ArrayList<>(danhSachChiTiet);
    }

    @Override
    public List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoNhaCungCap() {
        return new ArrayList<>(danhSachChiTiet);
    }
}
