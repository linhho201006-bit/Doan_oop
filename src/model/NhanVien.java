package model;

import java.sql.Date;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String vaiTro; // vai trò (admin,nhanvien,quanly,...)
    private String email;
    private String cmnd;
    private String soDienThoai;
    private Double luong;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, Date ngaySinh, String gioiTinh, String diaChi, String vaiTro,
            String cmnd, String soDienThoai, String email, Double luong) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.vaiTro = vaiTro;
        this.cmnd = cmnd;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.luong = luong;
    }

    // Getter and Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCMND() {
        return cmnd;
    }

    public void setCMND(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public Double getLuong() {
        return luong;
    }

    public void setLuong(Double luong) {
        this.luong = luong;
    }

    @Override
    public String toString() {
        return "NhanVien [Ma NV:" + maNV + ", Ten NV:" + tenNV + ", Ngay Sinh:" + ngaySinh + ", Gioi tinh:" + gioiTinh
                + ", Dia Chi:" + diaChi + ", Vai tro:" + vaiTro +
                ", Email:" + email + ", CMND:" + cmnd + ", SDT:" + soDienThoai + ", Luong:" + luong + "]";
    }
}
