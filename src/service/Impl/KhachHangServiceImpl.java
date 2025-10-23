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

import model.KhachHang;
import service.KhachHangService;

public class KhachHangServiceImpl implements KhachHangService {
    private static final String FILE_PATH = "src/resources/KhachHang.txt";
    private List<KhachHang> danhSachKhachHang;
    private int nextMaKh;

    public KhachHangServiceImpl() {
        danhSachKhachHang = new ArrayList<>();
        nextMaKh = 1;
        loadData();
    }

    // Tạo mã khách hàng tự động
    private String generateMaKh() {
        return String.format("KH%03d", nextMaKh++);
    }

    @Override
    public boolean themKhachHang(KhachHang khachHang) {
        try {
            khachHang.setMaKH(generateMaKh());
            danhSachKhachHang.add(khachHang);
            saveData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<KhachHang> layTatCaKhachHang() {
        return new ArrayList<>(danhSachKhachHang);
    }

    @Override
    public KhachHang timKhachHangTheoMa(String maKh) {
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getMaKH().equals(maKh)) {
                return kh;
            }
        }
        return null;
    }

    @Override
    public List<KhachHang> timKhachHangTheoHoTen(String hoTen) {
        List<KhachHang> ketQua = new ArrayList<>();
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getHoTen().toLowerCase().contains(hoTen.toLowerCase())) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }

    @Override
    public List<KhachHang> timKhachHangTheoCMND(String cmnd) {
        List<KhachHang> ketQua = new ArrayList<>();
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getCMND().contains(cmnd)) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }

    @Override
    public List<KhachHang> timKhachHangTheoSDT(String sdt) {
        List<KhachHang> ketQua = new ArrayList<>();
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getSDT().contains(sdt)) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }

    @Override
    public List<KhachHang> timKiemKhachHang(String tuKhoa) {
        List<KhachHang> ketQua = new ArrayList<>();
        for (KhachHang kh : danhSachKhachHang) {
            if (kh.getMaKH().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                    kh.getHoTen().toLowerCase().contains(tuKhoa.toLowerCase()) ||
                    kh.getCMND().contains(tuKhoa) ||
                    kh.getSDT().contains(tuKhoa)) {
                ketQua.add(kh);
            }
        }
        return ketQua;
    }

    @Override
    public boolean capNhatKhachHang(KhachHang khachHang) {
        try {
            for (int i = 0; i < danhSachKhachHang.size(); i++) {
                if (danhSachKhachHang.get(i).getMaKH().equals(khachHang.getMaKH())) {
                    danhSachKhachHang.set(i, khachHang);
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
    public boolean xoaKhachHang(String maKH) {
        try {
            for (int i = 0; i < danhSachKhachHang.size(); i++) {
                if (danhSachKhachHang.get(i).getMaKH().equals(maKH)) {
                    danhSachKhachHang.remove(i);
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

    /* Lưu dữ liệu vào file */
    private void saveData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (KhachHang kh : danhSachKhachHang) {
                writer.println(kh.getMaKH() + "|" +
                        kh.getHoTen() + "|" +
                        kh.getNgaySinh() + "|" +
                        kh.getGioiTinh() + "|" +
                        kh.getDiaChi() + "|" +
                        kh.getCMND() + "|" +
                        kh.getSDT());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Đọc dữ liệu từ file */
    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    KhachHang kh = new KhachHang(
                            parts[0], // ma_kh
                            parts[1], // hovaTen
                            Date.valueOf(parts[2]), // ngaySinh
                            parts[3], // gioiTinh
                            parts[4], // diaChi
                            parts[5], // cMND
                            parts[6] // sDT
                    );
                    danhSachKhachHang.add(kh);

                    // Cập nhật nextMaKh
                    String maKH = parts[0];
                    if (maKH.startsWith("KH")) {
                        try {
                            int so = Integer.parseInt(maKH.substring(2));
                            if (so >= nextMaKh) {
                                nextMaKh = so + 1;
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
