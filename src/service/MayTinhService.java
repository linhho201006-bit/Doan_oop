package service;

import java.util.List;

import model.abstraction.MayTinh;
import model.PC;
import model.LapTop;

public interface MayTinhService {

    // Thêm PC
    boolean themPC(PC maytinh);

    // thêm Laptop
    boolean themLapTop(LapTop maytinh);

    // Lấy tất cả pc
    List<PC> layTatCaPC();

    // Lấy tất cả laptop
    List<LapTop> layTatCaLapTop();

    // Lấy Tất cả máy tính
    List<MayTinh> layTatCaMayTinh();

    // Tìm kiếm pc theo mã
    PC timKiemPC(String maPC);

    // Tìm kiếm laptop theo mã
    LapTop timKiemLapTopTheoMa(String maLapTop);

    // Tìm Kiếm máy tính theo tên
    List<MayTinh> timKiemMayTinhTheoTen(String tenMay);

    // Tìm kiếm máy tính theo hãng sản xuất
    List<MayTinh> timKiemMayTinhTheoHangSX(String hangSX);

    // Tìm pc theo dung lượng ram
    List<PC> timKiemPCTheoRam(int ram);

    // Tìm kiếm laptop theo trọng lượng
    List<LapTop> timKiemLapTopTheoTrongLuong(double trongLuong);

    // Tìm kiếm tổng hợp máy tính
    List<MayTinh> timKiemTongHop(String tuKhoa);

    // Cập nhập pc
    boolean capNhatPC(PC pc);

    // Cập nhập laptop
    boolean capNhatLapTop(LapTop lapTop);

    // Xóa pc
    boolean xoaPC(String maPC);

    // Xóa laptop
    boolean xoaLapTop(String maLapTop);

    // Lấy máy tính theo loại(pc/laptop
    List<MayTinh> layMayTinhTheoLoai(String loaiMay);
}
