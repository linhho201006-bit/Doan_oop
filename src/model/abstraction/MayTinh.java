package model.abstraction;

public abstract class MayTinh {
    protected String maMay;
    protected String tenMay;
    protected double gia;
    protected String hangSX;
    protected String maNCC;

    public MayTinh() {
    }

    public MayTinh(String maMay, String tenMay, String hangSX, double gia, String maNCC) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.hangSX = hangSX;
        this.gia = gia;
        this.maNCC = maNCC;
    }

    // Getter and Setter
    public String getMaMay() {
        return maMay;
    }

    public String getTenMay() {
        return tenMay;
    }

    public double getGia() {
        return gia;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    @Override
    public String toString() {
        return "MayTinh [Ma may: " + maMay + ", Ten may: " + tenMay +
                ", Hang san xuat: " + hangSX + ", Gia: " + gia + ",  Ma NCC:" + maNCC + "]";
    }

}
