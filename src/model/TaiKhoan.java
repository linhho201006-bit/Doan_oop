package model;

public class TaiKhoan {
    private String tenDangNhap; // Tên đăng nhập
    private String matKhau; // Mật khẩu
    private String vaiTro; // Vai trò (admin, nhanvien, quanly,...)
    private String maNV; // Liên kết đến nhân viên (nếu là nhân viên)

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String vaiTro, String maNV) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.maNV = maNV;
    }

    // Getter - Setter
    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    @Override
    public String toString() {
        return "TaiKhoan [Ten dang nhap:" + tenDangNhap + ", Vai tro:" + vaiTro +
                ", Mat khau:" + matKhau + ", Ma NV:" + maNV + "]";
    }
}
