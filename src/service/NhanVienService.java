package service;

import java.util.List;

import model.NhanVien;

public interface NhanVienService {

    // Thêm nhân viên
    boolean themNhanVien(NhanVien nhanVien);

    // Lấy tất cả nhân viên
    List<NhanVien> layTatCaNhanVien();

    // Tìm nhân viên theo mã
    NhanVien timNhanVienTheoMa(String maNV);

    // Tìm nhân viên theo họ tên
    List<NhanVien> timNhanVienTheoHoTen(String tenNV);

    // Tìm kiếm tổng hợp
    List<NhanVien> timKiemNhanVien(String tuKhoa);

    // Cập nhật nhân viên
    boolean capNhatNhanVien(NhanVien nhanVien);

    // Xóa nhân viên
    boolean xoaNhanVien(String maNV);


}
