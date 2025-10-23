package service;

import java.util.List;
import model.BaoHanh;

public interface BaoHanhService {
    // Tạo bảo hành mới
    boolean taoBaoHanh(BaoHanh baoHanh);

    // Lấy tất cả bảo hành
    List<BaoHanh> layTatCaBaoHanh();

    // Tìm bảo hành theo mã
    BaoHanh timBaoHanhTheoMaBH(String maBH);

    // Tìm bảo hành theo mã sản phẩm
    BaoHanh timBaoHanhTheoMaMay(String maMay);

    // Tìm bảo hành theo thông tin khách hàng
    List<BaoHanh> timKiemBaoHanhTheoKhachHang(String tenKH);

    // Tìm kiếm theo mã hóa đơn
    List<BaoHanh> timKiemBaoHanhTheoMaHoaDon(String maHD);

    // Tìm theo trạng thái bảo hành
    List<BaoHanh> timKiemBaoHanhTheoTinhTrang(String tinhTrang);

    // Cập nhật bảo hành
    boolean capNhatBaoHanh(BaoHanh baoHanh);

    // Xóa bảo hành
    boolean xoaBaoHanh(String maBH);

    // Cập nhật trạng thái bảo hành
    boolean capNhatTrangThaiBaoHanh(String maBH, String tinhTrang);

    // Kiểm tra ràng buộc : mã sản phẩm phải tồn tại
    boolean kiemTraRangBuocMaSanPham(String maMay);

    // Kiểm tra ràng buộc : mã hóa đơn phải tồn tại
    boolean kiemTraRangBuocMaHoaDon(String maHD);

    // Tính tổng số bảo hành
    int tinhTongSoBaoHanh();

    // Thống kê số lượng bảo hành theo trạng thái
    int thongKeSoLuongBaoHanhTheoTrangThai(String tinhTrang);
}
