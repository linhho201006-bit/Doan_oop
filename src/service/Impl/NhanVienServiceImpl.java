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

import model.NhanVien;
import service.NhanVienService;

public class NhanVienServiceImpl implements NhanVienService {
    private static final String FILE_PATH = "src/resources/NhanVien.txt";
    private List<NhanVien> danhSachnhanvien;
    private int nextMa;

    public NhanVienServiceImpl() {
        danhSachnhanvien = new ArrayList<>();
        nextMa = 1;
        loadData();
    }

    private String generateMa() {
        return String.format("NV%03d", nextMa++);
    }

    @Override
    public boolean themNhanVien(NhanVien nhanVien) {
        try {
            nhanVien.setMaNV(generateMa());
            danhSachnhanvien.add(nhanVien);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<NhanVien> layTatCaNhanVien() {
        return new ArrayList<>(danhSachnhanvien);
    }

    @Override
    public NhanVien timNhanVienTheoMa(String maNV) {
        for (NhanVien nv : danhSachnhanvien) {
            if (nv.getMaNV().equals(maNV)) {
                return nv;
            }
        }
        return null;
    }

    @Override
    public List<NhanVien> timNhanVienTheoHoTen(String tenNV) {
        List<NhanVien> ketQua = new ArrayList<>();
        for (NhanVien nv : danhSachnhanvien) {
            if (nv.getTenNV().toLowerCase().contains(tenNV.toLowerCase())) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    @Override
    public List<NhanVien> timKiemNhanVien(String tuKhoa) {
        List<NhanVien> ketQua = new ArrayList<>();
        String tuKhoaLower = tuKhoa.toLowerCase();
        for (NhanVien nv : danhSachnhanvien) {
            if (nv.getTenNV().toLowerCase().contains(tuKhoaLower) ||
                    nv.getDiaChi().toLowerCase().contains(tuKhoaLower) ||
                    nv.getSoDienThoai().contains(tuKhoa) ||
                    nv.getEmail().toLowerCase().contains(tuKhoaLower)) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    @Override
    public boolean capNhatNhanVien(NhanVien nhanVien) {
        for (int i = 0; i < danhSachnhanvien.size(); i++) {
            if (danhSachnhanvien.get(i).getMaNV().equals(nhanVien.getMaNV())) {
                danhSachnhanvien.set(i, nhanVien);
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaNhanVien(String maNV) {
        boolean removed = danhSachnhanvien.removeIf(nv -> nv.getMaNV().equals(maNV));
        if (removed) {
            saveData();
        }
        return removed;
    }

    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (NhanVien nv : danhSachnhanvien) {
                writer.println(nv.getMaNV() + "|" +
                        nv.getTenNV() + "|" +
                        (nv.getNgaySinh() != null ? nv.getNgaySinh().toString() : "null") + "|" +
                        nv.getGioiTinh() + "|" +
                        nv.getDiaChi() + "|" +
                        nv.getVaiTro() + "|" +
                        nv.getCMND() + "|" +
                        nv.getSoDienThoai() + "|" +
                        nv.getEmail() + "|" +
                        (nv.getLuong() != null ? nv.getLuong() : "null"));
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
                if (parts.length == 9) {
                    Date ngaySinh = parts[2].equals("null") ? null : Date.valueOf(parts[2]);
                    Double luong = parts[9].equals("null") ? null : Double.parseDouble(parts[9]);
                    NhanVien nv = new NhanVien(
                            parts[0],
                            parts[1],
                            ngaySinh,
                            parts[3],
                            parts[4],
                            parts[5],
                            parts[6],
                            parts[7],
                            parts[8],
                            luong);
                    danhSachnhanvien.add(nv);

                    String ma = parts[0];
                    if (ma.startsWith("NV")) {
                        try {
                            int so = Integer.parseInt(ma.substring(2));
                            if (so >= nextMa) {
                                nextMa = so + 1;
                            }
                        } catch (NumberFormatException e) {
                            // Ignore
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
