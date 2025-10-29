package service;

import java.util.List;
import model.NhaCungCap;

public interface NhaCungCapService {

    // Tạo nhà cung cấp mới
    boolean taoNhaCungCap(NhaCungCap nhaCungCap);

    // Lấy tất cả nhà cung cấp
    List<NhaCungCap> layTatCaNhaCungCap();

    // Tìm nhà cung cấp theo mã
    NhaCungCap timNhaCungCapTheoMa(String maNCC);

    // Tìm kiếm nhà cung cấp theo tên
    List<NhaCungCap> timKiemNhaCungCapTheoTen(String tenNCC);

    // Cập nhật nhà cung cấp
    boolean capNhatNhaCungCap(NhaCungCap nhaCungCap);

    // Xóa nhà cung cấp
    boolean xoaNhaCungCap(String maNCC);
}
