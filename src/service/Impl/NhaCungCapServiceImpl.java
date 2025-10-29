package service.Impl;

import java.io.*;
import java.util.*;
import model.NhaCungCap;
import service.NhaCungCapService;

public class NhaCungCapServiceImpl implements NhaCungCapService {
    private static final String FILE_PATH = "src/resources/NhaCungCap.txt";
    private List<NhaCungCap> danhSachNCC = new ArrayList<>();
    private int nextMaNCC = 1;

    public NhaCungCapServiceImpl() {
        docData();
    }

    // ==========================
    // 🔹 1. Tạo nhà cung cấp
    // ==========================
    @Override
    public boolean taoNhaCungCap(NhaCungCap nhaCungCap) {
        if (nhaCungCap == null)
            return false;

        // Sinh mã tự động NCC001, NCC002, ...
        nhaCungCap.setMaNCC(taoMaTuDong());
        danhSachNCC.add(nhaCungCap);
        luuData();
        return true;
    }

    // ==========================
    // 🔹 2. Lấy tất cả nhà cung cấp
    // ==========================
    @Override
    public List<NhaCungCap> layTatCaNhaCungCap() {
        return danhSachNCC;
    }

    // ==========================
    // 🔹 3. Tìm theo mã
    // ==========================
    @Override
    public NhaCungCap timNhaCungCapTheoMa(String maNCC) {
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getMaNCC().equalsIgnoreCase(maNCC)) {
                return ncc;
            }
        }
        return null;
    }

    // ==========================
    // 🔹 6. Tìm theo tên
    // ==========================
    @Override
    public List<NhaCungCap> timKiemNhaCungCapTheoTen(String tenNCC) {
        List<NhaCungCap> ketQua = new ArrayList<>();
        for (NhaCungCap ncc : danhSachNCC) {
            if (ncc.getTenNCC().toLowerCase().contains(tenNCC.toLowerCase())) {
                ketQua.add(ncc);
            }
        }
        return ketQua;
    }

    // ==========================
    // 🔹 4. Cập nhật
    // ==========================
    @Override
    public boolean capNhatNhaCungCap(NhaCungCap nhaCungCap) {
        for (int i = 0; i < danhSachNCC.size(); i++) {
            if (danhSachNCC.get(i).getMaNCC().equalsIgnoreCase(nhaCungCap.getMaNCC())) {
                danhSachNCC.set(i, nhaCungCap);
                luuData();
                return true;
            }
        }
        return false;
    }

    // ==========================
    // 🔹 5. Xóa
    // ==========================
    @Override
    public boolean xoaNhaCungCap(String maNCC) {
        Iterator<NhaCungCap> iterator = danhSachNCC.iterator();
        while (iterator.hasNext()) {
            NhaCungCap ncc = iterator.next();
            if (ncc.getMaNCC().equalsIgnoreCase(maNCC)) {
                iterator.remove();
                luuData();
                return true;
            }
        }
        return false;
    }

    // ==========================
    // 🔹 7. Sinh mã tự động
    // ==========================
    private String taoMaTuDong() {
        String ma = String.format("NCC%03d", nextMaNCC++);
        return ma;
    }

    // ==========================
    // 🔹 8. Lưu dữ liệu ra file
    // ==========================
    private void luuData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (NhaCungCap ncc : danhSachNCC) {
                writer.write(ncc.getMaNCC() + "|" +
                        ncc.getTenNCC() + "|" +
                        ncc.getDiaChi() + "|" +
                        ncc.getSoDienThoai() + "|" +
                        ncc.getEmail());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(" Lỗi khi lưu dữ liệu nhà cung cấp: " + e.getMessage());
        }
    }

    // ==========================
    // 🔹 9. Đọc dữ liệu từ file
    // ==========================
    private void docData() {
        danhSachNCC.clear();
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    NhaCungCap ncc = new NhaCungCap(
                            parts[0], // ma
                            parts[1], // hangsx
                            parts[3], // ten
                            parts[4], // nguoi dai dien
                            parts[5], // dia chi
                            parts[6], // sdt
                            parts[7] // email
                    );
                    danhSachNCC.add(ncc);

                    // Cập nhật số mã tự động
                    try {
                        int so = Integer.parseInt(parts[0].substring(3));
                        if (so >= nextMaNCC)
                            nextMaNCC = so + 1;
                    } catch (Exception e) {
                        // bỏ qua nếu lỗi parse
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu nhà cung cấp: " + e.getMessage());
        }
    }
}
