package model;

public class NhaCungCap {
    private String maNCC;
    private String hang;
    private String tenNCC;
    private String nguoiDaiDien;
    private String diaChi;
    private String soDienThoai;
    private String email;

    public NhaCungCap(String maNCC, String hang, String tenNCC, String nguoiDaiDien, String diaChi, String soDienThoai, String email) {
        this.maNCC = maNCC;
        this.hang = hang;
        this.tenNCC = tenNCC;
        this.nguoiDaiDien = nguoiDaiDien;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.email = email;
    }

    // Getter and Setter
    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getNguoiDaiDien() {
        return nguoiDaiDien;
    }

    public void setNguoiDaiDien(String nguoiDaiDien) {
        this.nguoiDaiDien = nguoiDaiDien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "NhaCungCap [maNCC:" + maNCC + ", hang:" + hang + ", tenNCC:" + tenNCC +
                ", nguoiDaiDien:" + nguoiDaiDien + ", diaChi:" + diaChi +
                ", soDienThoai:" + soDienThoai + ", email:" + email + "]";
    }
}
