package service;

import model.PhieuNhapHang;
import java.util.List;

public interface PhieuNhapHangService {
    // Tạo phiếu nhập hàng mới
    boolean taoPhieuNhapHang(PhieuNhapHang phieuNhapHang);

    // Lấy tất cả phiếu nhập hàng
    List<PhieuNhapHang> layTatCaPhieuNhapHang();

    // Lấy phiếu nhập hàng theo mã
    PhieuNhapHang timPhieuNhapHangTheoMa(String maPhieuNhapHang);

    // Lấy danh sách phiếu nhập hàng theo nhà cung cấp
    List<PhieuNhapHang> layPhieuNhapHangTheoNhaCungCap(String maNhaCungCap);

    // Lấy danh sách phiếu nhập hàng theo khoảng thời gian
    List<PhieuNhapHang> layPhieuNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay);

    // Tìm kiếm tổng hợp
    List<PhieuNhapHang> timKiemPhieuNhapHang(String tuKhoa);

    // Tìm kiếm phiếu nhập hàng theo ngay nhập
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayNhap(String ngayNhap);

    // Tìm kiếm phiếu nhập hàng theo ngay thanh toán
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoNgayThanhToan(String ngayThanhToan);

    // Tìm kiếm phiếu nhập hàng theo trạng thái
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoTrangThai(String trangThai);

    // Tìm kiếm phiếu nhập hàng theo phương thức thanh toán
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoPhuongThucThanhToan(String phuongThucThanhToan);

    // Tìm kiếm phiếu nhập hàng theo nhà cung cấp
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoNhaCungCap(String maNhaCungCap);

    // Cập nhật phiếu nhập hàng
    boolean capNhatPhieuNhapHang(PhieuNhapHang phieuNhapHang);

    // Xóa phiếu nhập hàng
    boolean xoaPhieuNhapHang(String maPhieuNhapHang);

    // Hủy phiếu nhập hàng
    boolean huyPhieuNhapHang(String maPhieuNhapHang);

    // Thanh toán phiếu nhập hàng
    boolean thanhToanPhieuNhapHang(String maPhieuNhapHang, String phuongThucThanhToan);

    // Kiểm tra ràng buộc :mã nhà cung cấp phải tồn tại
    boolean kiemTraRangBuocMaNhaCungCap(String maNhaCungCap);

    // Tính tổng tiền của phiếu nhập hàng
    Double tinhTongTienPhieuNhapHang();

    // Thống kê tổng tiền nhập hàng theo khoảng thời gian
    Double thongKeTongTienNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay);

    // Thống kê số lượng phiếu nhập hàng theo trạng thái
    Double thongKeSoLuongPhieuNhapHangTheoTrangThai(String trangThai);

}
