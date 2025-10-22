package service;

import java.util.List;

import model.ChiTietHoaDon;

public interface ChiTietHoaDonService {

    // Thêm chi tiết hóa đơn mới
    boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon);

    // Lấy tất cả chi tiết hóa đơn
    List<ChiTietHoaDon> layTatCaChiTietHoaDon();

    // Lấy chi tiết hóa đơn theo mã
    ChiTietHoaDon timChiTietHoaDonTheoMa(String maChiTietHoaDon);

    // Lấy chi tiết hóa đơn theo mã hóa đơn
    List<ChiTietHoaDon> timChiTietHoaDonTheoMaHoaDon(String maHoaDon);

    // Lấy chi tiết hóa đơn theo mã khách hàng
    List<ChiTietHoaDon> timChiTietHoaDonTheoMaKhachHang(String maKhachHang);

    // Tìm kiếm tổng hợp
    List<ChiTietHoaDon> timKiemChiTietHoaDon(String tuKhoa);

    // Cập nhật chi tiết hóa đơn
    boolean capNhatChiTietHoaDon(ChiTietHoaDon chiTietHoaDon);

    // Xóa chi tiết hóa đơn
    boolean xoaChiTietHoaDon(String maChiTietHoaDon);

    // Xóa tất cả chi tiết hóa đơn của một hóa đơn
    boolean xoaTatCaChiTietHoaDonTheoMaHoaDon(String maHoaDon);

    // Kiểm tra ràng buộc: mã hóa đơn phải tồn tại
    boolean kiemTraRangBuocHoaDon(String maHoaDon);

    // Kiểm tra ràng buộc: mã khách hàng phải tồn tại
    boolean kiemTraRangBuocKhachHang(String maKhachHang);

    // Tính tổng tiền của một hóa đơn
    Double tinhTongTienTheoMaHoaDon(String maHoaDon);

    // Tính tổng tiền theo khách hàng
    Double tinhTongTienTheoKhachHang(String maKhachHang);

    // Đếm số lượng chi tiết hóa đơn của một hóa đơn
    int demSoChiTietHoaDonTheoMaHoaDon(String maHoaDon);

    // Lấy chi tiết hóa đơn có thành tiền cao nhất
    ChiTietHoaDon layChiTietHoaDonCoThanhTienCaoNhat();

    // Lấy chi tiết hóa đơn có thành tiền thấp nhất
    ChiTietHoaDon layChiTietHoaDonCoThanhTienThapNhat();

    // Tính thành tiền trung bình
    Double tinhThanhTienTrungBinh();

    // Thống kê chi tiết hóa đơn theo khách hàng
    List<ChiTietHoaDon> thongKeChiTietHoaDonTheoKhachHang();
}
