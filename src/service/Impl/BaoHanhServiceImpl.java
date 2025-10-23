package service.Impl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import model.BaoHanh;
import service.BaoHanhService;

public class BaoHanhServiceImpl implements BaoHanhService {

    private final String FILE_PATH = "src/resources/BaoHanh.txt";
    private List<BaoHanh> danhSachBaoHanh = new ArrayList<>();
    private int nextMaBH = 1;

    public BaoHanhServiceImpl() {
        loadData();
    }

    // ✅ Hàm sinh mã tự động
    private String taoMaTuDong() {
        return String.format("BH%03d", nextMaBH++);
    }

    @Override
    public boolean taoBaoHanh(BaoHanh baoHanh) {
        if (baoHanh == null)
            return false;

        // ✅ Sinh mã tự động nếu chưa có
        if (baoHanh.getMaBH() == null || baoHanh.getMaBH().trim().isEmpty()) {
            baoHanh.setMaBH(taoMaTuDong());
        } else if (timBaoHanhTheoMaBH(baoHanh.getMaBH()) != null) {
            return false;
        }

        danhSachBaoHanh.add(baoHanh);
        saveData();
        return true;
    }

    @Override
    public List<BaoHanh> layTatCaBaoHanh() {
        return new ArrayList<>(danhSachBaoHanh);
    }

    @Override
    public BaoHanh timBaoHanhTheoMaBH(String maBH) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaBH().equalsIgnoreCase(maBH))
                .findFirst()
                .orElse(null);
    }

    @Override
    public BaoHanh timBaoHanhTheoMaMay(String maMay) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaMay().equalsIgnoreCase(maMay))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<BaoHanh> timKiemBaoHanhTheoKhachHang(String tenKH) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaKH().toLowerCase().contains(tenKH.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BaoHanh> timKiemBaoHanhTheoMaHoaDon(String maHD) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getMaHD().equalsIgnoreCase(maHD))
                .collect(Collectors.toList());
    }

    @Override
    public List<BaoHanh> timKiemBaoHanhTheoTinhTrang(String tinhTrang) {
        return danhSachBaoHanh.stream()
                .filter(b -> b.getTinhTrang().equalsIgnoreCase(tinhTrang))
                .collect(Collectors.toList());
    }

    @Override
    public boolean capNhatBaoHanh(BaoHanh baoHanh) {
        for (int i = 0; i < danhSachBaoHanh.size(); i++) {
            if (danhSachBaoHanh.get(i).getMaBH().equalsIgnoreCase(baoHanh.getMaBH())) {
                danhSachBaoHanh.set(i, baoHanh);
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaBaoHanh(String maBH) {
        boolean removed = danhSachBaoHanh.removeIf(b -> b.getMaBH().equalsIgnoreCase(maBH));
        if (removed)
            saveData();
        return removed;
    }

    @Override
    public boolean capNhatTrangThaiBaoHanh(String maBH, String tinhTrang) {
        for (BaoHanh b : danhSachBaoHanh) {
            if (b.getMaBH().equalsIgnoreCase(maBH)) {
                b.setTinhTrang(tinhTrang);
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean kiemTraRangBuocMaSanPham(String maMay) {
        return danhSachBaoHanh.stream()
                .anyMatch(b -> b.getMaMay().equalsIgnoreCase(maMay));
    }

    @Override
    public boolean kiemTraRangBuocMaHoaDon(String maHD) {
        return danhSachBaoHanh.stream()
                .anyMatch(b -> b.getMaHD().equalsIgnoreCase(maHD));
    }

    @Override
    public int tinhTongSoBaoHanh() {
        return danhSachBaoHanh.size();
    }

    @Override
    public int thongKeSoLuongBaoHanhTheoTrangThai(String tinhTrang) {
        return (int) danhSachBaoHanh.stream()
                .filter(b -> b.getTinhTrang().equalsIgnoreCase(tinhTrang))
                .count();
    }

    private void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BaoHanh b : danhSachBaoHanh) {
                bw.write(String.join("|",
                        b.getMaBH(),
                        b.getMaMay(),
                        b.getMaHD(),
                        b.getMaKH(),
                        b.getNgayBatDau(),
                        b.getNgayKetThuc(),
                        b.getTinhTrang()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file BaoHanh: " + e.getMessage());
        }
    }

    private void loadData() {
        danhSachBaoHanh.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    BaoHanh b = new BaoHanh(
                            parts[0], parts[1], parts[2],
                            parts[3], parts[4], parts[5], parts[6]);
                    danhSachBaoHanh.add(b);
                }
            }
        } catch (IOException e) {
            System.out.println("File BaoHanh chưa tồn tại hoặc lỗi khi đọc file.");
        }
    }
}
