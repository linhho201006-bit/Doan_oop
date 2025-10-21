package service;

import java.util.List;
import model.HoaDon;

public interface HoaDonService {
    // Tạo hóa đơn mới
    boolean taoHoaDon(HoaDon hoaDon);

    // Lấy tất cả hóa đơn
    List<HoaDon> layTatCaHoaDon();

    // Lấy hóa đơn theo mã
    HoaDon timHoaDonTheoMa(String maHoaDon);

    // Lấy hóa đơn theo mã khách hàng
    List<HoaDon> timHoaDonTheoMaKhachHang(String maKhachHang);

    // Tìm hóa đơn theo trạng thái
    List<HoaDon> timHoaDonTheoTrangThai(String trangThai);

    // Tìm hóa đơn theo phương thức thanh toán
    List<HoaDon> timHoaDonTheoPhuongThucThanhToan(String phuongThuc);

    // Tìm hóa đơn theo khoảng ngày thanh toán
    List<HoaDon> timHoaDonTheoKhoangNgay(String ngayBatDau, String ngayKetThuc);

    // Tìm kiếm tổng hợp
    List<HoaDon> timKiemHoaDon(String tuKhoa);

    // Cập nhật hóa đơn
    boolean capNhatHoaDon(HoaDon hoaDon);

    // Xóa hóa đơn
    boolean xoaHoaDon(String maHoaDon);

    // Thanh toán hóa đơn
    boolean thanhToanHoaDon(String maHoaDon, String phuongThucThanhToan);

    // Hủy hóa đơn
    boolean huyHoaDon(String maHoaDon);

    // Kiểm tra ràng buộc: mã khách hàng phải tồn tại
    boolean kiemTraRangBuocKhachHang(String maKhachHang);

    // Tính tổng tiền hóa đơn từ danh sách chi tiết
    Double tinhTongTienHoaDon(String maHoaDon);

    // Lấy hóa đơn chưa thanh toán
    List<HoaDon> layHoaDonChuaThanhToan();

    // Lấy hóa đơn đã thanh toán
    List<HoaDon> layHoaDonDaThanhToan();

    // Lấy hóa đơn đã hủy
    List<HoaDon> layHoaDonDaHuy();

    // Tính tổng doanh thu từ hóa đơn
    Double tinhTongDoanhThuHoaDon();

    // Tính tổng doanh thu theo khách hàng
    Double tinhTongDoanhThuTheoKhachHang(String maKhachHang);

    // Tính tổng doanh thu theo khoảng thời gian
    Double tinhTongDoanhThuTheoKhoangThoiGian(String ngayBatDau, String ngayKetThuc);

    // Thống kê hóa đơn theo trạng thái
    List<HoaDon> thongKeHoaDonTheoTrangThai();

    // Lấy hóa đơn sắp hết hạn thanh toán (7 ngày)
    List<HoaDon> layHoaDonSapHetHanThanhToan();
}