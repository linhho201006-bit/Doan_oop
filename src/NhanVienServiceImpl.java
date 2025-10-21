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
    private List<NhanVien> danhSach;
    private int nextMa;

    public NhanVienServiceImpl() {
        danhSach = new ArrayList<>();
        nextMa = 1;
        loadData();
    }

    private String generateMa() {
        return String.format("NV%03d", nextMa++);
    }

    // ========== Đúng theo interface ==========

    @Override
    public boolean themNhanVien(NhanVien nhanVien) {
        try {
            nhanVien.setMaNhanVien(generateMa());
            danhSach.add(nhanVien);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<NhanVien> layTatCaNhanVien() {
        return new ArrayList<>(danhSach);
    }

    @Override
    public NhanVien timKiemNhanVienTheoMa(String maNhanVien) {
        for (NhanVien nv : danhSach) {
            if (nv.getMaNhanVien().equals(maNhanVien)) {
                return nv;
            }
        }
        return null;
    }

    @Override
    public List<NhanVien> timKiemNhanVienTheoTen(String tenNhanVien) {
        List<NhanVien> ketQua = new ArrayList<>();
        String tenLower = tenNhanVien.toLowerCase();
        for (NhanVien nv : danhSach) {
            if (nv.getTenNhanVien().toLowerCase().contains(tenLower)) {
                ketQua.add(nv);
            }
        }
        return ketQua;
    }

    @Override
    public boolean capNhatNhanVien(NhanVien nhanVien) {
        for (int i = 0; i < danhSach.size(); i++) {
            if (danhSach.get(i).getMaNhanVien().equals(nhanVien.getMaNhanVien())) {
                danhSach.set(i, nhanVien);
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaNhanVien(String maNhanVien) {
        boolean removed = danhSach.removeIf(nv -> nv.getMaNhanVien().equals(maNhanVien));
        if (removed) {
            saveData();
        }
        return removed;
    }

    // ========== Lưu / đọc file giữ nguyên ==========

    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (NhanVien nv : danhSach) {
                writer.println(nv.getMaNhanVien() + "|" +
                        nv.getTenNhanVien() + "|" +
                        (nv.getNgaySinh() != null ? nv.getNgaySinh().toString() : "null") + "|" +
                        nv.getGioiTinh() + "|" +
                        nv.getDiaChi() + "|" +
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
                    Double luong = parts[8].equals("null") ? null : Double.parseDouble(parts[8]);
                    NhanVien nv = new NhanVien(
                            parts[0], parts[1], ngaySinh, parts[3],
                            parts[4], parts[5], parts[6], parts[7], luong);
                    danhSach.add(nv);

                    String ma = parts[0];
                    if (ma.startsWith("NV")) {
                        try {
                            int so = Integer.parseInt(ma.substring(2));
                            if (so >= nextMa) {
                                nextMa = so + 1;
                            }
                        } catch (NumberFormatException e) {
                            // Bỏ qua
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
