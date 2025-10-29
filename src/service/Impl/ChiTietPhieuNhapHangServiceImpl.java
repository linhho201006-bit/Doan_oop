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

    private void luuDuLieuRaFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ChiTietPhieuNhapHang c : danhSachChiTiet) {
                bw.write(String.join("|",
                        c.getMaCTPNH(),
                        c.getMaPhieuNhap(),
                        c.getMaMay(),
                        String.valueOf(c.getSoLuong()),
                        String.valueOf(c.getDonGia()),
                        String.valueOf(c.getThanhTien())));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file ChiTietPhieuNhapHang: " + e.getMessage());
        }
    }

    private void docDuLieuTuFile() {
        danhSachChiTiet.clear();
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 6) {
                    ChiTietPhieuNhapHang ct = new ChiTietPhieuNhapHang(
                            parts[0],
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            Double.parseDouble(parts[4]),
                            Double.parseDouble(parts[5]));
                    danhSachChiTiet.add(ct);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file ChiTietPhieuNhapHang: " + e.getMessage());
        }
    }

    @Override
    public boolean taoChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet) {
        if (chiTiet == null || timChiTietPhieuNhapHangTheoMa(chiTiet.getMaCTPNH()) != null)
            return false;
        chiTiet.tinhThanhTien();
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
                chiTiet.tinhThanhTien();
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
    public boolean xoaChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhap) {
        boolean removed = danhSachChiTiet.removeIf(c -> c.getMaPhieuNhap().equalsIgnoreCase(maPhieuNhap));
        if (removed)
            luuDuLieuRaFile();
        return removed;
    }

    @Override
    public List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaMay(String maMay) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaMay().equalsIgnoreCase(maMay))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHang(String tuKhoa) {
        String keyword = tuKhoa.toLowerCase();
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaCTPNH().toLowerCase().contains(keyword)
                        || c.getMaPhieuNhap().toLowerCase().contains(keyword)
                        || c.getMaMay().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public boolean kiemTraRangBuocMaSanPham(String maMay) {
        return danhSachChiTiet.stream().anyMatch(c -> c.getMaMay().equalsIgnoreCase(maMay));
    }

    @Override
    public boolean kiemTraRangBuocMaPhieuNhapHang(String maPhieuNhap) {
        return danhSachChiTiet.stream().anyMatch(c -> c.getMaPhieuNhap().equalsIgnoreCase(maPhieuNhap));
    }

    @Override
    public Double tinhTongTienChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhap) {
        return danhSachChiTiet.stream()
                .filter(c -> c.getMaPhieuNhap().equalsIgnoreCase(maPhieuNhap))
                .mapToDouble(ChiTietPhieuNhapHang::getThanhTien)
                .sum();
    }

    @Override
    public List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoSanPham() {
        Map<String, ChiTietPhieuNhapHang> thongKe = new HashMap<>();

        for (ChiTietPhieuNhapHang ct : danhSachChiTiet) {
            ChiTietPhieuNhapHang tong = thongKe.get(ct.getMaMay());
            if (tong == null) {
                tong = new ChiTietPhieuNhapHang(
                        "TK_" + ct.getMaMay(),
                        "THONGKE",
                        ct.getMaMay(),
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien());
            } else {
                tong.setSoLuong(tong.getSoLuong() + ct.getSoLuong());
                tong.setThanhTien(tong.getThanhTien() + ct.getThanhTien());
            }
            thongKe.put(ct.getMaMay(), tong);
        }

        return new ArrayList<>(thongKe.values());
    }
}
