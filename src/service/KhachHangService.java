package service;

import java.util.List;
import model.KhachHang;

public interface KhachHangService {

    // Thêm khách hàng mới
    boolean themKhachHang(KhachHang khachHang);

    // Lấy tất cả khách hàng
    List<KhachHang> layTatCaKhachHang();

    // Tìm khách hàng theo mã
    KhachHang timKhachHangTheoMa(String maKH);

    // Tìm khách hàng theo họ tên
    List<KhachHang> timKhachHangTheoHoTen(String tenKH);

    // Tìm kiếm tổng hợp
    List<KhachHang> timKiemKhachHang(String tuKhoa);

    // Cập nhật khách hàng
    boolean capNhatKhachHang(KhachHang khachHang);

    // Xóa khách hàng
    boolean xoaKhachHang(String maKH);
}
