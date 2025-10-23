package service.Impl;

import model.PhieuNhapHang;
import service.PhieuNhapHangService;
import service.NhaCungCapService;

import java.io.*;
import java.sql.Date;
import java.util.*;

public class PhieuNhapHangServiceImpl implements PhieuNhapHangService {

    private static final String FILE_PATH = "src/resources/PhieuNhapHang.txt";
    private final List<PhieuNhapHang> danhSachPhieuNhap = new ArrayList<>();
    private int nextMaPhieu = 1;

    private final NhaCungCapService nhaCungCapService;

    public PhieuNhapHangServiceImpl(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
        loadData();
    }

    private String taoMaTuDong() {
        return String.format("PNH%03d", nextMaPhieu++);
    }

    @Override
    public boolean taoPhieuNhapHang(PhieuNhapHang p) {
        if (p == null)
            return false;

        if (!kiemTraRangBuocMaNhaCungCap(p.getMaNhaCungCap())) {
            System.err.println("Mã nhà cung cấp không tồn tại!");
            return false;
        }

        p.setMaPhieuNhap(taoMaTuDong());
        danhSachPhieuNhap.add(p);
        saveData();
        return true;
    }

    @Override
    public List<PhieuNhapHang> layTatCaPhieuNhapHang() {
        return danhSachPhieuNhap;
    }

    @Override
    public PhieuNhapHang timPhieuNhapHangTheoMa(String maPhieuNhap) {
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaPhieuNhap().equalsIgnoreCase(maPhieuNhap))
                return p;
        }
        return null;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayNhap(String ngayNhap) {
        List<PhieuNhapHang> ketQua = new ArrayList<>();
        try {
            Date dateNhap = Date.valueOf(ngayNhap);
            for (PhieuNhapHang phieu : danhSachPhieuNhap) {
                if (phieu.getNgayNhap() != null && phieu.getNgayNhap().equals(dateNhap)) {
                    ketQua.add(phieu);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Định dạng ngày nhập không hợp lệ. Dạng hợp lệ: yyyy-MM-dd");
        }
        return ketQua;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayThanhToan(String ngayThanhToan) {
        List<PhieuNhapHang> ketQua = new ArrayList<>();
        try {
            Date dateThanhToan = Date.valueOf(ngayThanhToan);
            for (PhieuNhapHang phieu : danhSachPhieuNhap) {
                if (phieu.getNgayThanhToan() != null && phieu.getNgayThanhToan().equals(dateThanhToan)) {
                    ketQua.add(phieu);
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Định dạng ngày thanh toán không hợp lệ. Dạng hợp lệ: yyyy-MM-dd");
        }
        return ketQua;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoTrangThai(String trangThai) {
        List<PhieuNhapHang> ketQua = new ArrayList<>();
        for (PhieuNhapHang phieu : danhSachPhieuNhap) {
            if (phieu.getTrangThai() != null && phieu.getTrangThai().equalsIgnoreCase(trangThai)) {
                ketQua.add(phieu);
            }
        }
        return ketQua;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoPhuongThucThanhToan(String phuongThucThanhToan) {
        List<PhieuNhapHang> ketQua = new ArrayList<>();
        for (PhieuNhapHang phieu : danhSachPhieuNhap) {
            if (phieu.getPhuongThucThanhToan() != null &&
                    phieu.getPhuongThucThanhToan().equalsIgnoreCase(phuongThucThanhToan)) {
                ketQua.add(phieu);
            }
        }
        return ketQua;
    }

    // ✅ implement đúng theo interface
    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNhaCungCap(String maNCC) {
        List<PhieuNhapHang> ketQua = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaNhaCungCap().equalsIgnoreCase(maNCC)) {
                ketQua.add(p);
            }
        }
        return ketQua;
    }

    @Override
    public List<PhieuNhapHang> timPhieuNhapHangTheoNhaCungCap(String maNhaCungCap) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaNhaCungCap().equalsIgnoreCase(maNhaCungCap))
                kq.add(p);
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timPhieuNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        try {
            Date start = Date.valueOf(tuNgay);
            Date end = Date.valueOf(denNgay);
            for (PhieuNhapHang p : danhSachPhieuNhap) {
                Date ngayNhap = p.getNgayNhap(); // ✅ không dùng Date.valueOf() nữa
                if (ngayNhap != null && !ngayNhap.before(start) && !ngayNhap.after(end))
                    kq.add(p);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Lỗi định dạng ngày tháng (phải là yyyy-MM-dd)");
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHang(String tuKhoa) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        tuKhoa = tuKhoa.toLowerCase();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaPhieuNhap().toLowerCase().contains(tuKhoa)
                    || p.getMaNhaCungCap().toLowerCase().contains(tuKhoa)
                    || (p.getTrangThai() != null && p.getTrangThai().toLowerCase().contains(tuKhoa))
                    || (p.getPhuongThucThanhToan() != null
                            && p.getPhuongThucThanhToan().toLowerCase().contains(tuKhoa))) {
                kq.add(p);
            }
        }
        return kq;
    }

    @Override
    public boolean capNhatPhieuNhapHang(PhieuNhapHang p) {
        for (int i = 0; i < danhSachPhieuNhap.size(); i++) {
            if (danhSachPhieuNhap.get(i).getMaPhieuNhap().equalsIgnoreCase(p.getMaPhieuNhap())) {
                danhSachPhieuNhap.set(i, p);
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaPhieuNhapHang(String maPhieuNhap) {
        Iterator<PhieuNhapHang> it = danhSachPhieuNhap.iterator();
        while (it.hasNext()) {
            PhieuNhapHang p = it.next();
            if (p.getMaPhieuNhap().equalsIgnoreCase(maPhieuNhap)) {
                it.remove();
                saveData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean huyPhieuNhapHang(String maPhieuNhap) {
        PhieuNhapHang p = timPhieuNhapHangTheoMa(maPhieuNhap);
        if (p != null) {
            p.setTrangThai("Đã hủy");
            saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean thanhToanPhieuNhapHang(String maPhieuNhap, String phuongThucThanhToan) {
        PhieuNhapHang p = timPhieuNhapHangTheoMa(maPhieuNhap);
        if (p != null) {
            p.setTrangThai("Đã thanh toán");
            p.setPhuongThucThanhToan(phuongThucThanhToan);
            p.setNgayThanhToan(Date.valueOf(java.time.LocalDate.now())); // ✅ truyền Date, không phải String
            saveData();
            return true;
        }
        return false;
    }

    @Override
    public boolean kiemTraRangBuocMaNhaCungCap(String maNhaCungCap) {
        return nhaCungCapService.timNhaCungCapTheoMa(maNhaCungCap) != null;
    }

    @Override
    public Double tinhTongTienPhieuNhapHang() {
        double tong = 0;
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            tong += p.getTongTien();
        }
        return tong;
    }

    @Override
    public Double thongKeTongTienNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay) {
        List<PhieuNhapHang> ds = timPhieuNhapHangTheoKhoangThoiGian(tuNgay, denNgay);
        double tong = 0;
        for (PhieuNhapHang p : ds) {
            tong += p.getTongTien();
        }
        return tong;
    }

    @Override
    public Double thongKeSoLuongPhieuNhapHangTheoTrangThai(String trangThai) {
        return (double) timKiemPhieuNhapHangTheoTrangThai(trangThai).size();
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PhieuNhapHang p : danhSachPhieuNhap) {
                writer.write(p.getMaPhieuNhap() + "|" +
                        p.getMaNhaCungCap() + "|" +
                        p.getNgayNhap() + "|" +
                        (p.getNgayThanhToan() == null ? "null" : p.getNgayThanhToan()) + "|" +
                        (p.getPhuongThucThanhToan() == null ? "null" : p.getPhuongThucThanhToan()) + "|" +
                        (p.getTrangThai() == null ? "null" : p.getTrangThai()) + "|" +
                        p.getTongTien());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    private void loadData() {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                if (parts.length == 7) {
                    PhieuNhapHang p = new PhieuNhapHang(
                            parts[0], // maPhieuNhap
                            parts[1], // maNhaCungCap
                            Date.valueOf(parts[2]), // ngày nhập
                            parts[3].equals("null") ? null : Date.valueOf(parts[3]), // ✅ ngày thanh toán
                            parts[4].equals("null") ? null : parts[4], // phương thức thanh toán
                            parts[5].equals("null") ? null : parts[5], // trạng thái
                            Double.parseDouble(parts[6]) // tổng tiền
                    );
                    danhSachPhieuNhap.add(p);

                    try {
                        int so = Integer.parseInt(parts[0].substring(3));
                        if (so >= nextMaPhieu)
                            nextMaPhieu = so + 1;
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
    }
}
