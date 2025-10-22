package model;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private String vaiTro; // vai trò (admin,nhanvien,quanly,...)
    private Double luong;
    private String diaChi;
    private String SDT;

    public NhanVien() {
    };

    public NhanVien(String maNV, String hoTen, String vaiTro, Double luong, String diaChi, String SDT) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.luong = luong;
        this.diaChi = diaChi;
        this.SDT = SDT;
    }

    // Getter and Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public Double getLuong() {
        return luong;
    }

    public void setLuong(Double luong) {
        this.luong = luong;
    }

    public String getDiachi() {
        return diaChi;
    }

    public void setDiachi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSdt() {
        return SDT;
    }

    public void setSdt(String SDT) {
        this.SDT = SDT;
    }

    @Override
    public String toString() {
        return "NhanVien [maNV:" + maNV + ", hoTen:" + hoTen + ", vaiTro:" + vaiTro +
                ", luong:" + luong + ", diaChi:" + diaChi + ", SDT:" + SDT + "]";
    }
}
