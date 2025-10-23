package model;

public class BaoHanh {
    private String maBH;
    private String maHD;
    private String maMay;
    private String maKH;
    private String ngayBatDau;
    private String ngayKetThuc;
    private String tinhTrang; // Tình trạng (Còn hiệu lực / Hết hạn / Đang xử lý)

    public BaoHanh() {
    }

    public BaoHanh(String maBH, String maHD, String maMay, String maKH, String ngayBatDau, String ngayKetThuc,
            String tinhTrang) {
        this.maBH = maBH;
        this.maHD = maHD;
        this.maMay = maMay;
        this.maKH = maKH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tinhTrang = tinhTrang;
    }

    // Getter - Setter
    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "BaoHanh [MaBH:" + maBH + ", MaHD:" + maHD +
                ", MaMay:" + maMay + ", NgayBatDau:" + ngayBatDau +
                ", NgayKetThuc:" + ngayKetThuc + ", TinhTrang:" + tinhTrang + "]";
    }
}
