package model.abstraction;

public abstract class MayTinh {
    protected String maMay;
    protected String tenMay;
    protected double gia;
    protected String hangSX;

    public MayTinh() {
    }

    public MayTinh(String maMay, String tenMay, double gia, String hangSX) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.gia = gia;
        this.hangSX = hangSX;
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

    @Override
    public String toString() {
        return "MayTinh [Ma may: " + maMay + " Ten may: " + tenMay + " Gia: " + gia + " Hang san xuat: " + hangSX + "]";
    }

}
