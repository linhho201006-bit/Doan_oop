package service;

import java.util.List;
import model.ChiTietPhieuNhapHang;

public interface ChiTietPhieuNhapHangService {

    // Tạo chi tiết phiếu nhập hàng mới
    boolean taoChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet);

    // Lấy tất cả chi tiết phiếu nhập hàng
    List<ChiTietPhieuNhapHang> layTatCaChiTietPhieuNhapHang();

    // Tìm chi tiết phiếu nhập hàng theo mã
    ChiTietPhieuNhapHang timChiTietPhieuNhapHangTheoMa(String maCTPNH);

    // Tìm kiếm chi tiết phiếu nhập hàng theo mã phiếu nhập hàng
    List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang);

    // Tìm kiếm chi tiết phiếu nhập hàng theo mã sản phẩm
    List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaSanPham(String maSanPham);

    // Tìm kiếm tổng hợp
    List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHang(String tuKhoa);

    // Cập nhật chi tiết phiếu nhập hàng
    boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet);

    // Xóa chi tiết phiếu nhập hàng
    boolean xoaChiTietPhieuNhapHang(String maCTPNH);

    // Xóa chi tiết phiếu nhập hàng theo mã phiếu nhập hàng
    boolean xoaChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang);

    // Kiểm tra ràng buộc : mã sản phẩm phải tồn tại
    boolean kiemTraRangBuocMaSanPham(String maSanPham);

    // Kiểm tra ràng buộc : mã phiếu nhập hàng phải tồn tại
    boolean kiemTraRangBuocMaPhieuNhapHang(String maPhieuNhapHang);

    // Tính tổng tiền của phiếu nhập hàng theo mã phiếu nhập hàng
    Double tinhTongTienChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhapHang);

    // Tính tổng tiền theo nhà cung cấp
    Double tinhTongTienChiTietPhieuNhapHangTheoNhaCungCap(String maNhaCungCap);

    // Thống kê tổng tiền nhập hàng theo khoảng thời gian
    Double thongKeTongTienChiTietPhieuNhapHangTheoKhoangThoiGian(String tuNgay, String denNgay);

    // Thống kê chi tiết phiếu nhập hàng theo sản phẩm
    List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoSanPham();

    // Thống kê chi tiết phiếu nhập hàng theo nhà cung cấp
    List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoNhaCungCap();
}
