package service;

import model.PhieuNhapHang;
import java.util.List;

public interface PhieuNhapHangService {
    // Tạo phiếu nhập hàng mới
    boolean taoPhieuNhapHang(PhieuNhapHang phieuNhapHang);

    // Lấy tất cả phiếu nhập hàng
    List<PhieuNhapHang> layTatCaPhieuNhapHang();

    // Tìm phiếu nhập hàng theo mã
    PhieuNhapHang timPhieuNhapHangTheoMa(String maPNH);

    // Tìm phiếu nhập hàng theo nhà cung cấp
    List<PhieuNhapHang> timPhieuNhapHangTheoNhaCungCap(String maNCC);

    // Tìm kiếm tổng hợp
    List<PhieuNhapHang> timKiemPhieuNhapHang(String tuKhoa);

    // Cập nhật phiếu nhập hàng
    boolean capNhatPhieuNhapHang(PhieuNhapHang phieuNhapHang);

    // Xóa phiếu nhập hàng
    boolean xoaPhieuNhapHang(String maPNH);

    // Hủy phiếu nhập hàng
    boolean huyPhieuNhapHang(String maPNH);

    // Thanh toán phiếu nhập hàng
    boolean thanhToanPhieuNhapHang(String maPNH, String phuongThucThanhToan);

    // Kiểm tra ràng buộc :mã nhà cung cấp phải tồn tại
    boolean kiemTraRangBuocMaNhaCungCap(String maNCC);

    // Tính tổng tiền của phiếu nhập hàng
    Double tinhTongTienPhieuNhapHang();

    // Tìm phiếu nhập hàng theo trạng thái
    List<PhieuNhapHang> timKiemPhieuNhapHangTheoTrangThai(String trangThai);

    // Thống kê số lượng phiếu nhập hàng theo trạng thái
    Double thongKeSoLuongPhieuNhapHangTheoTrangThai(String trangThai);

}
