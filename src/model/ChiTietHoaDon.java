package model;

public class ChiTietHoaDon {
    private String maCTHD;
    private String maHD;
    private String maSP;
    private double donGia;
    private int soLuong;
    private double thanhTien;
    
    public ChiTietHoaDon(){}

    public ChiTietHoaDon(String maCTHD,String maHD,String maSP,double donGia,int soLuong,double thanhTien){
         this.maCTHD = maCTHD;
         this.maHD =maHD;
         this.maSP =maSP;
         this.donGia =donGia;
         this.soLuong =soLuong;
         this.thanhTien =thanhTien;
         this.thanhTien =donGia*soLuong;

    }

    //Getter and Setter
    public String getMaCTHD(){
        return maCTHD;
    }

    public String getMaHD(){
        return maHD;
    }

    public String getMaSP(){
        return maSP;
    }

    public double getDonGia(){
        return donGia;
    }

    public int getSoLuong(){
        return soLuong;
    }

    public double getThanhTien(){
        return thanhTien;
    }

    public void setMaHD(String maHD){
        this.maHD =maHD;
    }

    public void setMaSP(String maSP){
        this.maSP =maSP;
    }

    public void setDonGia(double donGia){
        this.donGia =donGia;
    }

    public void setSoLuong(int soLuong){
        this.soLuong =soLuong;
    }

    public void setThanhTien(double thanhTien){
        this.thanhTien =thanhTien;
    }

    //phương thức tính thành tiền
    public void tinhThanhTien(){
        if(donGia != 0 && soLuong != 0){
            thanhTien = donGia * soLuong;
        }
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon[Ma CTHD: " + maCTHD + " Ma HD: " + maHD +
        " Ma SP: " + maSP + " Don Gia: " + donGia +
         " So Luong: " + soLuong + " Thanh Tien: " + thanhTien + "]";
    }
}



