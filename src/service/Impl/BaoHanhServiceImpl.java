package service.Impl;

import java.io.*;
import java.sql.Date;
import java.util.*;

import model.BaoHanh;
import service.BaoHanhService;

public class BaoHanhServiceImpl implements BaoHanhService {

    private final String FILE_PATH = "src/resources/BaoHanh.txt";
    private List<BaoHanh> danhSachBaoHanh = new ArrayList<>();
    private int nextMaBH = 1;

    public BaoHanhServiceImpl() {
        loadData();
    }

    // Sinh mã tự động
    private String taoMaTuDong() {
        return String.format("BH%03d", nextMaBH++);
    }

    @Override
    public boolean taoBaoHanh(BaoHanh baoHanh) {
        if (baoHanh == null)
            return false;

        // Sinh mã tự động nếu chưa có
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

    // Ghi dữ liệu ra file
    private void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (BaoHanh b : danhSachBaoHanh) {
                bw.write(String.join("|",
                        b.getMaBH(),
                        b.getMaHD(),
                        b.getMaMay(),
                        b.getMaKH(),
                        b.getNgayBatDau().toString(),
                        b.getNgayKetThuc().toString(),
                        b.getTinhTrang()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file BaoHanh: " + e.getMessage());
        }
    }

    // Đọc dữ liệu từ file
    private void loadData() {
        danhSachBaoHanh.clear();
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    BaoHanh b = new BaoHanh(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            Date.valueOf(parts[4]),
                            Date.valueOf(parts[5]),
                            parts[6]);
                    danhSachBaoHanh.add(b);

                    // Cập nhật nextMaBH
                    try {
                        int num = Integer.parseInt(parts[0].replace("BH", ""));
                        if (num >= nextMaBH)
                            nextMaBH = num + 1;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file BaoHanh: " + e.getMessage());
        }
    }
}
