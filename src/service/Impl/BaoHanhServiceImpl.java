package service.Impl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import model.BaoHanh;
import service.BaoHanhService;

public class BaoHanhServiceImpl implements BaoHanhService {

    private final String FILE_PATH = "src/resources/BaoHanh.txt";
    private List<BaoHanh> danhSachBaoHanh = new ArrayList<>();

    public BaoHanhServiceImpl() {
        docDuLieuTuFile();
    }

    // =====================================================
    // 1️⃣ Đọc & ghi file dữ liệu
    // =====================================================
    private void luuDuLieuRaFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BaoHanh b : danhSachBaoHanh) {
                bw.write(String.join("|",
                        b.getMaBH(),
                        b.getMaSP(),
                        b.getMaHD(),
                        b.getTenKH(),
                        b.getNgayBatDau(),
                        b.getNgayKetThuc(),
                        b.getTrangThai()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file BaoHanh: " + e.getMessage());
        }
    }

    private void docDuLieuTuFile() {
        danhSachBaoHanh.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    BaoHanh b = new BaoHanh(
                            parts[0], // maBH
                            parts[1], // maSP
                            parts[2], // maHD
                            parts[3], // tenKH
                            parts[4], // ngayBatDau
                            parts[5], // ngayKetThuc
                            parts[6] // trangThai
                    );
                    danhSachBaoHanh.add(b);
                }
            }
        } catch (IOException e) {
            System.out.println("File BaoHanh chưa tồn tại hoặc lỗi khi đọc file.");
        }
    }

    // =====================================================
    // 2️⃣ CRUD cơ bản
    // =====================================================
    @Override
    public boolean taoBaoHanh(BaoHanh baoHanh) {
        if (baoHanh == null || timBaoHanhTheoMa(baoHanh.getMaBH()) != null)
            return false;
        danhSachBaoHanh.add(baoHanh);
        luuDuLieuRaFile();
        return true;
    }

    @Override
    public List<BaoHanh> layTatCaBaoHanh() {
        return new ArrayList<>(danhSachBaoHanh);
    }

    @Override
    public BaoHanh timBaoHanhTheoMa(String maBH) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaBH().equalsIgnoreCase(maBH))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BaoHanh timBaoHanhTheoMaSanPham(String maSP) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaSP().equalsIgnoreCase(maSP))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean capNhatBaoHanh(BaoHanh baoHanh) {
        for (int i = 0; i < danhSachBaoHanh.size(); i++) {
            if (danhSachBaoHanh.get(i).getMaBH().equalsIgnoreCase(baoHanh.getMaBH())) {
                danhSachBaoHanh.set(i, baoHanh);
                luuDuLieuRaFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaBaoHanh(String maBH) {
        boolean removed = danhSachBaoHanh.removeIf(b -> b.getMaBH().equalsIgnoreCase(maBH));
        if (removed)
            luuDuLieuRaFile();
        return removed;
    }

    // =====================================================
    // 3️⃣ Tìm kiếm
    // =====================================================
    @Override
    public List<BaoHanh> timKiemBaoHanhTheoKhachHang(String tenKH) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getTenKH().toLowerCase().contains(tenKH.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BaoHanh> timKiemBaoHanhTheoMaHoaDon(String maHD) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaHD().equalsIgnoreCase(maHD))
                .collect(Collectors.toList());
    }

    @Override
    public List<BaoHanh> timKiemBaoHanhTheoTrangThai(String trangThai) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getTrangThai().equalsIgnoreCase(trangThai))
                .collect(Collectors.toList());
    }

    // =====================================================
    // 4️⃣ Cập nhật trạng thái & ràng buộc
    // =====================================================
    @Override
    public boolean capNhatTrangThaiBaoHanh(String maBH, String trangThai) {
        for (BaoHanh b : danhSachBaoHanh) {
            if (b.getMaBH().equalsIgnoreCase(maBH)) {
                b.setTrangThai(trangThai);
                luuDuLieuRaFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean kiemTraRangBuocMaSanPham(String maSP) {
        return danhSachBaoHanh.stream()
                .anyMatch(b -> b.getMaSP().equalsIgnoreCase(maSP));
    }

    @Override
    public boolean kiemTraRangBuocMaHoaDon(String maHD) {
        return danhSachBaoHanh.stream()
                .anyMatch(b -> b.getMaHD().equalsIgnoreCase(maHD));
    }

    // =====================================================
    // 5️⃣ Tính toán & thống kê
    // =====================================================
    @Override
    public int tinhTongSoBaoHanh() {
        return danhSachBaoHanh.size();
    }

    @Override
    public int thongKeSoLuongBaoHanhTheoTrangThai(String trangThai) {
        return (int) danhSachBaoHanh.stream()
                .filter(b -> b.getTrangThai().equalsIgnoreCase(trangThai))
                .count();
    }
}
