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

    // Tìm kiếm chi tiết phiếu nhập hàng theo mã sản phẩm
    List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHangTheoMaMay(String maMay);

    // Tìm kiếm tổng hợp
    List<ChiTietPhieuNhapHang> timKiemChiTietPhieuNhapHang(String tuKhoa);

    // Cập nhật chi tiết phiếu nhập hàng
    boolean capNhatChiTietPhieuNhapHang(ChiTietPhieuNhapHang chiTiet);

    // Xóa chi tiết phiếu nhập hàng
    boolean xoaChiTietPhieuNhapHang(String maCTPNH);

    // Xóa chi tiết phiếu nhập hàng theo mã phiếu nhập hàng
    boolean xoaChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhap);

    // Kiểm tra ràng buộc : mã sản phẩm phải tồn tại
    boolean kiemTraRangBuocMaSanPham(String maMay);

    // Kiểm tra ràng buộc : mã phiếu nhập hàng phải tồn tại
    boolean kiemTraRangBuocMaPhieuNhapHang(String maPhieuNhap);

    // Tính tổng tiền của phiếu nhập hàng theo mã phiếu nhập hàng
    Double tinhTongTienChiTietPhieuNhapHangTheoMaPhieuNhapHang(String maPhieuNhap);

    // Thống kê chi tiết phiếu nhập hàng theo sản phẩm
    List<ChiTietPhieuNhapHang> thongKeChiTietPhieuNhapHangTheoSanPham();
}
