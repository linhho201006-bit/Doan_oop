package model;

import model.abstraction.MayTinh;

public class LapTop extends MayTinh {
    private Double trongLuong;
    private Double kichThuocManHinh;

    public LapTop() {
    }

    public LapTop(String maMay, String tenMay, String hangSX, Double gia, Double trongLuong, Double kichThuocManHinh) {
        super(maMay, tenMay, hangSX, gia);
        this.trongLuong = trongLuong;
        this.kichThuocManHinh = kichThuocManHinh;
    }

    // Getters and Setters
    public String getMaLapTop() {
        return getMaMay();
    }

    public void setMaLapTop(String maLapTop) {
        setMaMay(maLapTop);
    }

    public Double getTrongLuong() {
        return trongLuong;
    }

    public Double getKichThuocManHinh() {
        return kichThuocManHinh;
    }

    public void setTrongLuong(Double trongLuong) {
        this.trongLuong = trongLuong;
    }

    public void setKichThuocManHinh(Double kichThuocManHinh) {
        this.kichThuocManHinh = kichThuocManHinh;
    }

    @Override
    public String toString() {
        return "LapTop [Trong luong: " + trongLuong + " Kich Thuoc Man Hinh: " + kichThuocManHinh + "]";
    }
}
