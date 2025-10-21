package model;

import java.sql.Date;

public class KhachHang {
    private String maKH;
    private String hoTen;
    private String gioiTinh;
    private String CMND;
    private String diaChi;
    private Date ngaySinh;
    private String SDT;

    public KhachHang(){}

    public KhachHang(String maKH, String hoTen, String gioiTinh, String CMND, String diaChi, Date ngaySinh, String SDT) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.CMND = CMND;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
    }

    //Getter and Setter
    public String getMaKH() {
        return maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public String getCMND() {
        return CMND;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
    
    @Override
    public String toString() {
        return "KhachHang [maKH:" + maKH + ", hoTen:" + hoTen + 
        ", gioiTinh:" + gioiTinh + ", CMND:" + CMND + ", diaChi:" + diaChi + 
        ", ngaySinh:" + ngaySinh + ", SDT:" + SDT + "]";

}
}
