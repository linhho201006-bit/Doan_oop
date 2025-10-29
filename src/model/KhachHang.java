package model; 

import java.sql.Date; 

public class KhachHang { 
    private String maKH; 
    private String tenKH; 
    private String gioiTinh; 
    private String cmnd; 
    private String diaChi; 
    private Date ngaySinh; 
    private String soDienThoai; 

    public KhachHang() { } 
    public KhachHang(String maKH, String tenKH, Date ngaySinh, String gioiTinh, String diaChi, String cmnd, String soDienThoai) { 
        this.maKH = maKH; 
        this.tenKH = tenKH; 
        this.ngaySinh = ngaySinh; 
        this.gioiTinh = gioiTinh; 
        this.diaChi = diaChi; 
        this.cmnd = cmnd; 
        this.soDienThoai = soDienThoai; 
    } 

        // Getter and Setter 
    public String getMaKH() {
        return maKH; }

    public String getTenKH() { 
        return tenKH; }
        
    public String getGioiTinh() { 
        return gioiTinh; } 
    
    public String getCMND() { 
        return cmnd; } 
        
    public String getDiaChi() { 
        return diaChi; } 
        
    public Date getNgaySinh() { 
        return ngaySinh; } 
        
    public String getSoDienThoai() { 
        return soDienThoai; } 
        
    public void setMaKH(String maKH) { 
        this.maKH = maKH; } 
        
    public void setTenKH(String tenKH) {
         this.tenKH = tenKH; } 
         
    public void setGioiTinh(String gioiTinh) { 
        this.gioiTinh = gioiTinh; } 
        
    public void setCMND(String cmnd) {
        this.cmnd = cmnd; } 
        
    public void setDiaChi(String diaChi) { 
        this.diaChi = diaChi; } 
        
    public void setNgaySinh(Date ngaySinh) { 
        this.ngaySinh = ngaySinh; } 
        
    public void setSoDienThoai(String soDienThoai) { 
        this.soDienThoai = soDienThoai; } 
        
    @Override 
    public String toString() { 
        return "KhachHang [Ma KH:" + maKH + ", Ten KH:" + tenKH + ", Ngay sinh:" + ngaySinh + 
        ", Gioi tinh:" + gioiTinh + ", Dia chi:" + diaChi + ", CMND:" + cmnd + ", SDT:" + soDienThoai + "]"; 
    }
}