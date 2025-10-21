package service;

import java.util.List;
import model.NhanVien;

public interface NhanVienService {

    // Thêm nhân viên
    boolean themNhanVien(NhanVien nhanVien);

    // Lấy tất cả nhân viên
    List<NhanVien> layTatCaNhanVien();

    // Tìm kiếm nhân viên theo mã
    NhanVien timKiemNhanVienTheoMa(String maNhanVien);

    // Tìm kiếm nhân viên theo tên
    List<NhanVien> timKiemNhanVienTheoTen(String tenNhanVien);

    // Cập nhật nhân viên
    boolean capNhatNhanVien(NhanVien nhanVien);

    // Xóa nhân viên
    boolean xoaNhanVien(String maNhanVien);
}
