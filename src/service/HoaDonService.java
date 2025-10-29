package service;

import java.util.List;
import model.HoaDon;

public interface HoaDonService {

    // Tạo hóa đơn mới
    boolean taoHoaDon(HoaDon hoaDon);

    // Lấy tất cả hóa đơn
    List<HoaDon> layTatCaHoaDon();

    // Tìm hóa đơn theo mã
    HoaDon timHoaDonTheoMa(String maHD);

    // Tìm hóa đơn theo mã khách hàng
    List<HoaDon> timHoaDonTheoMaKhachHang(String maKH);

    // Tìm kiếm tổng hợp
    List<HoaDon> timKiemHoaDon(String tuKhoa);

    // Cập nhật hóa đơn
    boolean capNhatHoaDon(HoaDon hoaDon);

    // Xóa hóa đơn
    boolean xoaHoaDon(String maHD);

    // Thanh toán hóa đơn
    boolean thanhToanHoaDon(String maHoaDon, String phuongThucThanhToan);

    // Hủy hóa đơn
    boolean huyHoaDon(String maHD);

    // Kiểm tra ràng buộc: mã khách hàng phải tồn tại
    boolean kiemTraRangBuocKhachHang(String maKH);

    // Tính tổng tiền hóa đơn từ danh sách chi tiết
    Double tinhTongTienHoaDon(String maHD);

    // Tính tổng doanh thu từ hóa đơn
    Double tinhTongDoanhThuHoaDon();

    // Thống kê hóa đơn theo trạng thái
    List<HoaDon> thongKeHoaDonTheoTrangThai();
}