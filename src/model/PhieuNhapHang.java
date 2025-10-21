package model;

import java.sql.Date;

public class PhieuNhapHang {
    private String maPhieuNhap;
    private String maNhaCungCap;
    private Date ngayNhap;
    private Date ngayThanhToan;//Ngày thanh toán bằng null nếu chưa thanh toán
    private Double tongTien;
    private String phuongThucThanhToan;//phương thức thanh toán: Tiền mặt, Chuyển khoản,...
    private String trangThai;//"Chưa thanh toán","Đã thanh toán","Đã hủy"

    public PhieuNhapHang(){}

    public PhieuNhapHang(String maPhieuNhap,String maNhaCungCap,Date ngayNhap,Date ngayThanhToan,
        Double tongTien,String phuongThucThanhToan,String trangThai){
        this.maPhieuNhap = maPhieuNhap;
        this.maNhaCungCap = maNhaCungCap;
        this.ngayNhap = ngayNhap;
        this.ngayThanhToan = ngayThanhToan;
        this.tongTien = tongTien;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
    }

    //Getter and Setter
    public String getMaPhieuNhap(){
        return maPhieuNhap;
    }

    public String getMaNhaCungCap(){
        return maNhaCungCap;
    }

    public Date getNgayNhap(){
        return ngayNhap;
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

    public void setMaPhieuNhap(String maPhieuNhap){
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setMaNhaCungCap(String maNhaCungCap){
        this.maNhaCungCap = maNhaCungCap;
    }

    public void setNgayNhap(Date ngayNhap){
        this.ngayNhap = ngayNhap;
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

    //Phương thức thanh toán phiếu nhập hàng
    public void thanhToanPhieuNhapHang(){
        if("Chưa thanh toán".equals(trangThai)){
            trangThai = "Đã thanh toán";
            ngayThanhToan = new Date(System.currentTimeMillis());
        }
    }

    //Phương thức hủy phiếu nhập hàng
    public void huyPhieuNhapHang(){
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
        return "PhieuNhapHang [maPhieuNhap:" + maPhieuNhap + ", maNhaCungCap:" + maNhaCungCap +
         ", ngayNhap:" + ngayNhap + ", ngayThanhToan:" + ngayThanhToan + ", tongTien:" + tongTien +
         ", phuongThucThanhToan:" + phuongThucThanhToan + ", trangThai:" + trangThai + "]";
    }
   
}
