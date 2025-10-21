package model;

import java.sql.Date;

public class HoaDon {
    private String maHD;
    private String maKH;
    private Date ngayThanhToan;
    private Double tongTien;
    private String phuongThucThanhToan;
    private String trangThai;//"Chưa thanh toán","Đã thanh toán","Đã hủy"

    public HoaDon(){}

    public HoaDon(String maHD,String maKH,Date ngayThanhToan,Double tongTien,
        String phuongThucThanhToan,String trangThai){
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
    }
    //Getter and Setter
    public String getMaHD(){
        return maHD;
    }

    public String getMaKH(){
        return maKH;
    }

    public Date getNgayThanhToan(){
        return ngayThanhToan;
    }

    public Double getTongTien(){
        return tongTien;
    }

    public String getPhuongThucThanhToan(){
        return phuongThucThanhToan;
    }

    public String getTrangThai(){
        return trangThai;
    }

    public void setMaHD(String maHD){
        this.maHD = maHD;
    }

    public void setMaKH(String maKH){
        this.maKH = maKH;
    }

    public void setNgayThanhToan(Date ngayThanhToan){
        this.ngayThanhToan = ngayThanhToan;
    }

    public void setTongTien(Double tongTien){
        this.tongTien = tongTien;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan){
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public void setTrangThai(String trangThai){
        this.trangThai = trangThai;
    }

    //Phương thức thanh toán hóa đơn
    public void thanhToanHoaDon(){
        if("Chưa thanh toán".equals(trangThai)){
            trangThai = "Đã thanh toán";
            ngayThanhToan = new Date(System.currentTimeMillis());
        }
    }

    //Phương thức hủy hóa đơn
    public void huyHoaDon(){
        if(!"Đã thanh toán".equals(trangThai)){
            trangThai = "Đã hủy";
        }
    }

    //Kiểm tra có thể thanh toán không
    public boolean coTheThanhToan(){
        return "Chưa thanh toán".equals(trangThai);
    }

    //Kiểm tra có thể hủy không
    public boolean coTheHuy(){
        return !"Đã thanh toán".equals(trangThai);
    }

    @Override
    public String toString(){
        return "HoaDon [maHD:" + maHD + ", maKH:" + maKH +
         ", ngayThanhToan:" + ngayThanhToan + ", tongTien:" + tongTien +
         ", phuongThucThanhToan:" + phuongThucThanhToan + ", trangThai:" + trangThai + "]";
    }
}


