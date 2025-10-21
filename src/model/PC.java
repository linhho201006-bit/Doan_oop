package model;

import model.abstraction.MayTinh;

public class PC extends MayTinh {
    private String loaiCPU;
    private int ram;

    public PC() {}

    public PC(String maMay, String tenMay, double gia, String hangSX, String loaiCPU, int ram) {
        super(maMay, tenMay, gia, hangSX);
        this.loaiCPU = loaiCPU;
        this.ram = ram;
    }

    //Getters and Setters
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