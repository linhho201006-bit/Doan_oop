package model;

import model.abstraction.MayTinh;

public class PC extends MayTinh {
    private String loaiCPU;
    private int ram;

    public PC() {
    }

    public PC(String maMay, String tenMay, String hangSX, double gia, int ram, String loaiCPU) {
        super(maMay, tenMay, hangSX, gia);
        this.loaiCPU = loaiCPU;
        this.ram = ram;
    }

    // Getters and Setters
    public String getMaPC() {
        return getMaMay();
    }

    public void setMaPC(String maPC) {
        setMaMay(maPC);
    }

    public String getLoaiCPU() {
        return loaiCPU;
    }

    public int getRam() {
        return ram;
    }

    public void setLoaiCPU(String loaiCPU) {
        this.loaiCPU = loaiCPU;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    @Override
    public String toString() {
        return "PC [Loai CPU: " + loaiCPU + ", RAM: " + ram + "]";
    }
}