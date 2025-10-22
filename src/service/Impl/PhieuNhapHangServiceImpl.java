package service.Impl;

import model.PhieuNhapHang;
import model.NhaCungCap;
import service.PhieuNhapHangService;
import service.NhaCungCapService;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PhieuNhapHangServiceImpl implements PhieuNhapHangService {
    public PhieuNhapHangServiceImpl() {
    }

    private static final String FILE_PATH = "src/resources/PhieuNhapHang.txt";
    private List<PhieuNhapHang> danhSachPhieuNhap = new ArrayList<>();
    private int nextMaPhieu = 1;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String taoMaTuDong() {
        return String.format("PNH%03d", nextMaPhieu++);
    }

    // Tham chiếu để kiểm tra mã nhà cung cấp hợp lệ
    private NhaCungCapService nhaCungCapService;

    public PhieuNhapHangServiceImpl(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
        docData();
    }

    // ======================================
    // 🔹 1. Tạo phiếu nhập hàng
    // ======================================
    @Override
    public boolean taoPhieuNhapHang(PhieuNhapHang phieuNhapHang) {
        if (phieuNhapHang == null)
            return false;

        // Kiểm tra ràng buộc mã nhà cung cấp
        if (!kiemTraRangBuocMaNhaCungCap(phieuNhapHang.getMaNhaCungCap())) {
            System.err.println(" Mã nhà cung cấp không tồn tại!");
            return false;
        }

        // Sinh mã tự động: PNH001
        phieuNhapHang.setMaPhieuNhapHang(taoMaTuDong());
        danhSachPhieuNhap.add(phieuNhapHang);
        luuData();
        return true;
    }

    // ======================================
    // 🔹 2. Lấy tất cả phiếu nhập hàng
    // ======================================
    @Override
    public List<PhieuNhapHang> layTatCaPhieuNhapHang() {
        return danhSachPhieuNhap;
    }

    // ======================================
    // 🔹 3. Tìm phiếu nhập theo mã
    // ======================================
    @Override
    public PhieuNhapHang timPhieuNhapHangTheoMa(String maPhieuNhapHang) {
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang)) {
                return p;
            }
        }
        return null;
    }

    // ======================================
    // 🔹 4. Lấy danh sách phiếu theo nhà cung cấp
    // ======================================
    @Override
    public List<PhieuNhapHang> layPhieuNhapHangTheoNhaCungCap(String maNhaCungCap) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaNhaCungCap().equalsIgnoreCase(maNhaCungCap)) {
                kq.add(p);
            }
        }
        return kq;
    }

    // ======================================
    // 🔹 5. Lấy danh sách theo khoảng thời gian
    // ======================================
    @Override
    public List<PhieuNhapHang> layPhieuNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        try {
            Date start = sdf.parse(tuNgay);
            Date end = sdf.parse(denNgay);
            for (PhieuNhapHang p : danhSachPhieuNhap) {
                Date ngayNhap = sdf.parse(p.getNgayNhap());
                if (!ngayNhap.before(start) && !ngayNhap.after(end)) {
                    kq.add(p);
                }
            }
        } catch (ParseException e) {
            System.err.println(" Lỗi định dạng ngày tháng!");
        }
        return kq;
    }

    // ======================================
    // 🔹 6. Tìm kiếm tổng hợp
    // ======================================
    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHang(String tuKhoa) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        tuKhoa = tuKhoa.toLowerCase();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getMaPhieuNhapHang().toLowerCase().contains(tuKhoa)
                    || p.getMaNhaCungCap().toLowerCase().contains(tuKhoa)
                    || p.getTrangThai().toLowerCase().contains(tuKhoa)
                    || p.getPhuongThucThanhToan().toLowerCase().contains(tuKhoa)) {
                kq.add(p);
            }
        }
        return kq;
    }

    // ======================================
    // 🔹 7. Tìm kiếm theo các tiêu chí
    // ======================================
    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayNhap(String ngayNhap) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getNgayNhap().equalsIgnoreCase(ngayNhap))
                kq.add(p);
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayThanhToan(String ngayThanhToan) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getNgayThanhToan().equalsIgnoreCase(ngayThanhToan))
                kq.add(p);
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoTrangThai(String trangThai) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getTrangThai().equalsIgnoreCase(trangThai))
                kq.add(p);
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoPhuongThucThanhToan(String phuongThucThanhToan) {
        List<PhieuNhapHang> kq = new ArrayList<>();
        for (PhieuNhapHang p : danhSachPhieuNhap) {
            if (p.getPhuongThucThanhToan().equalsIgnoreCase(phuongThucThanhToan))
                kq.add(p);
        }
        return kq;
    }

    @Override
    public List<PhieuNhapHang> timKiemPhieuNhapHangTheoNhaCungCap(String maNhaCungCap) {
        return layPhieuNhapHangTheoNhaCungCap(maNhaCungCap);
    }

    // ======================================
    // 🔹 8. Cập nhật / Xóa / Hủy / Thanh toán
    // ======================================
    @Override
    public boolean capNhatPhieuNhapHang(PhieuNhapHang phieuNhapHang) {
        for (int i = 0; i < danhSachPhieuNhap.size(); i++) {
            if (danhSachPhieuNhap.get(i).getMaPhieuNhapHang().equalsIgnoreCase(phieuNhapHang.getMaPhieuNhapHang())) {
                danhSachPhieuNhap.set(i, phieuNhapHang);
                luuData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaPhieuNhapHang(String maPhieuNhapHang) {
        Iterator<PhieuNhapHang> it = danhSachPhieuNhap.iterator();
        while (it.hasNext()) {
            PhieuNhapHang p = it.next();
            if (p.getMaPhieuNhapHang().equalsIgnoreCase(maPhieuNhapHang)) {
                it.remove();
                luuData();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean huyPhieuNhapHang(String maPhieuNhapHang) {
        PhieuNhapHang p = timPhieuNhapHangTheoMa(maPhieuNhapHang);
        if (p != null) {
            p.setTrangThai("Đã hủy");
            luuData();
            return true;
        }
        return false;
    }

    @Override
    public boolean thanhToanPhieuNhapHang(String maPhieuNhapHang, String phuongThucThanhToan) {
        PhieuNhapHang p = timPhieuNhapHangTheoMa(maPhieuNhapHang);
        if (p != null) {
            p.setTrangThai("Đã thanh toán");
            p.setPhuongThucThanhToan(phuongThucThanhToan);
            p.setNgayThanhToan(sdf.format(new Date()));
            luuData();
            return true;
        }
        return false;
    }

    // ======================================
    // 🔹 9. Ràng buộc mã nhà cung cấp
    // ======================================
    @Override
    public boolean kiemTraRangBuocMaNhaCungCap(String maNhaCungCap) {
        return nhaCungCapService.timNhaCungCapTheoMa(maNhaCungCap) != null;
    }

    // ======================================
    // 🔹 10. Thống kê & tổng hợp
    // ======================================
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
        List<PhieuNhapHang> danhSach = layPhieuNhapHangTheoKhoangThoiGian(tuNgay, denNgay);
        double tong = 0;
        for (PhieuNhapHang p : danhSach) {
            tong += p.getTongTien();
        }
        return tong;
    }

    @Override
    public Double thongKeSoLuongPhieuNhapHangTheoTrangThai(String trangThai) {
        return (double) timKiemPhieuNhapHangTheoTrangThai(trangThai).size();
    }

    // ======================================
    // 🔹 11. Lưu & Đọc file
    // ======================================
    private void luuData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PhieuNhapHang p : danhSachPhieuNhap) {
                bw.write(p.getMaPhieuNhapHang() + "|" +
                        p.getMaNhaCungCap() + "|" +
                        p.getNgayNhap() + "|" +
                        p.getNgayThanhToan() + "|" +
                        p.getTrangThai() + "|" +
                        p.getPhuongThucThanhToan() + "|" +
                        p.getTongTien());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println(" Lỗi khi lưu dữ liệu phiếu nhập: " + e.getMessage());
        }
    }

    private void docData() {
        danhSachPhieuNhap.clear();
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7) {
                    PhieuNhapHang p = new PhieuNhapHang(
                            parts[0], parts[1], parts[2], parts[3],
                            parts[4], parts[5], Double.parseDouble(parts[6]));
                    danhSachPhieuNhap.add(p);

                    // cập nhật mã tự động
                    try {
                        int so = Integer.parseInt(parts[0].substring(3));
                        if (so >= nextMaPhieu)
                            nextMaPhieu = so + 1;
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(" Lỗi khi đọc dữ liệu phiếu nhập: " + e.getMessage());
        }
    }
}
