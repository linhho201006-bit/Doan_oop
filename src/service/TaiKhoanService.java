package service;

import java.util.List;
import model.TaiKhoan;

public interface TaiKhoanService {
    // Tạo tài khoản mới
    boolean taoTaiKhoan(TaiKhoan taiKhoan);

    // Lấy tất cả tài khoản
    List<TaiKhoan> layTatCaTaiKhoan();

    // Tìm tài khoản theo tên đăng nhập
    TaiKhoan timTaiKhoanTheoTenDangNhap(String tenDangNhap);

    // Tìm Kiếm tài khoản theo mã nhân viên
    TaiKhoan timTaiKhoanTheoMaNV(String maNV);

    // Đăng nhập
    TaiKhoan dangNhap(String tenDangNhap, String matKhau);

    // Đổi mật khẩu
    boolean doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi);

    // Kiểm tra ràng buộc : mã nhân viên phải tồn tại
    boolean kiemTraRangBuocMaNV(String maNV);

    // Kiểm tra ràng buộc : tên đăng nhập phải duy nhất
    boolean kiemTraRangBuocTenDangNhap(String tenDangNhap);

    // Cập nhật tài khoản
    boolean capNhatTaiKhoan(TaiKhoan taiKhoan);

    // Xóa tài khoản
    boolean xoaTaiKhoan(String tenDangNhap);

    // Thống kê số lượng tk theo vai trò
    int thongKeSoLuongTaiKhoanTheoVaiTro(String vaiTro);

}
