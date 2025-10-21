package model;

public class ChiTietPhieuNhapHang {
    private String maCTPNH;
    private String maPhieuNhap;
    private String maMay;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietPhieuNhapHang(){}

    public ChiTietPhieuNhapHang(String maCTPNH, String maPhieuNhap, String maMay, int soLuong, double donGia, double thanhTien) {
        this.maCTPNH = maCTPNH;
        this.maPhieuNhap = maPhieuNhap;
        this.maMay = maMay;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = donGia * soLuong;
    }

    // Getter and Setter
    public String getMaCTPNH() {
        return maCTPNH;
    }
    
    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }   

    public String getMaMay() {
        return maMay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setCTPHNH(String ctPNH) {
        this.maCTPNH = ctPNH;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    //phương thức tính thành tiền
    public void tinhThanhTien(){
        if(donGia != 0 && soLuong != 0){
            thanhTien = donGia * soLuong;
        }
    }
    @Override
    public String toString() {
        return "ChiTietPhieuNhapHang[maCTPNH:" + maCTPNH +", maPhieuNhap:'" + maPhieuNhap + 
                ", maMay:'" + maMay + ", soLuong:" + soLuong +
                ", donGia:" + donGia +", thanhTien:" + thanhTien + "]'";
    }
   
}
