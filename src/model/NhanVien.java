package model;

import java.sql.Date;

public class NhanVien {
    private String maNV;
    private String hoTen;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String vaiTro; // vai trò (admin,nhanvien,quanly,...)
    private String Email;
    private String CMND;
    private String SDT;
    private Double luong;

    public NhanVien() {
    };

    public NhanVien(String maNV, String hoTen, Date ngaySinh, String gioiTinh, String diaChi, String vaiTro,
            String CMND, String SDT, String Email, Double luong) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
        this.CMND = CMND;
        this.SDT = SDT;
        this.Email = Email;
        this.luong = luong;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Double getLuong() {
        return luong;
    }

    public void setLuong(Double luong) {
        this.luong = luong;
    }

    @Override
    public String toString() {
        return "NhanVien [maNV:" + maNV + ", hoTen:" + hoTen + ",ngaySinh:" + ngaySinh + ",gioiTinh:" + gioiTinh
                + ", diaChi:" + diaChi + ", vaiTro:" + vaiTro +
                ",Email:" + Email + "CMND:" + CMND + ", SDT:" + SDT + ", luong:" + luong + "]";
    }
}
