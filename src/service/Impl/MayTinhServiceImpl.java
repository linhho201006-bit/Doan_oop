package service.Impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.PC;
import model.LapTop;
import model.abstraction.MayTinh;
import service.MayTinhService;

public class MayTinhServiceImpl implements MayTinhService {
    private static final String FILE_PC = "src/resources/PC.txt";
    private static final String FILE_LAPTOP = "src/resources/LapTop.txt";

    private List<PC> danhSachPC;
    private List<LapTop> danhSachLapTop;
    private int nextMaPC;
    private int nextMaLapTop;

    public MayTinhServiceImpl() {
        danhSachPC = new ArrayList<>();
        danhSachLapTop = new ArrayList<>();
        nextMaPC = 1;
        nextMaLapTop = 1;
        loadData();
    }

    // ========================== HÀM TẠO MÃ TỰ ĐỘNG ==========================
    private String generateMaPC() {
        return String.format("PC%03d", nextMaPC++);
    }

    private String generateMaLapTop() {
        return String.format("LT%03d", nextMaLapTop++);
    }

    // ============================ THÊM MÁY TÍNH =============================
    @Override
    public boolean themPC(PC maytinh) {
        try {
            maytinh.setMaPC(generateMaPC());
            danhSachPC.add(maytinh);
            saveDataPC();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean themLapTop(LapTop maytinh) {
        try {
            maytinh.setMaLapTop(generateMaLapTop());
            danhSachLapTop.add(maytinh);
            saveDataLapTop();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================ LẤY DANH SÁCH =============================
    @Override
    public List<PC> layTatCaPC() {
        return new ArrayList<>(danhSachPC);
    }

    @Override
    public List<LapTop> layTatCaLapTop() {
        return new ArrayList<>(danhSachLapTop);
    }

    @Override
    public List<MayTinh> layTatCaMayTinh() {
        List<MayTinh> ketQua = new ArrayList<>();
        ketQua.addAll(danhSachPC);
        ketQua.addAll(danhSachLapTop);
        return ketQua;
    }

    // ============================ TÌM KIẾM =============================
    @Override
    public PC timKiemPC(String maPC) {
        for (PC pc : danhSachPC) {
            if (pc.getMaPC().equalsIgnoreCase(maPC)) {
                return pc;
            }
        }
        return null;
    }

    @Override
    public LapTop timKiemLapTopTheoMa(String maLapTop) {
        for (LapTop lap : danhSachLapTop) {
            if (lap.getMaLapTop().equalsIgnoreCase(maLapTop)) {
                return lap;
            }
        }
        return null;
    }

    @Override
    public List<MayTinh> timKiemMayTinhTheoTen(String tenMay) {
        List<MayTinh> ketQua = new ArrayList<>();
        for (PC pc : danhSachPC) {
            if (pc.getTenMay().toLowerCase().contains(tenMay.toLowerCase())) {
                ketQua.add(pc);
            }
        }
        for (LapTop lap : danhSachLapTop) {
            if (lap.getTenMay().toLowerCase().contains(tenMay.toLowerCase())) {
                ketQua.add(lap);
            }
        }
        return ketQua;
    }

    @Override
    public List<MayTinh> timKiemMayTinhTheoHangSX(String hangSX) {
        List<MayTinh> ketQua = new ArrayList<>();
        for (PC pc : danhSachPC) {
            if (pc.getHangSX().toLowerCase().contains(hangSX.toLowerCase())) {
                ketQua.add(pc);
            }
        }
        for (LapTop lap : danhSachLapTop) {
            if (lap.getHangSX().toLowerCase().contains(hangSX.toLowerCase())) {
                ketQua.add(lap);
            }
        }
        return ketQua;
    }

    @Override
    public List<PC> timKiemPCTheoRam(int ram) {
        List<PC> ketQua = new ArrayList<>();
        for (PC pc : danhSachPC) {
            if (pc.getRam() == ram) {
                ketQua.add(pc);
            }
        }
        return ketQua;
    }

    @Override
    public List<LapTop> timKiemLapTopTheoTrongLuong(double trongLuong) {
        List<LapTop> ketQua = new ArrayList<>();
        for (LapTop lap : danhSachLapTop) {
            if (lap.getTrongLuong() == trongLuong) {
                ketQua.add(lap);
            }
        }
        return ketQua;
    }

    @Override
    public List<MayTinh> timKiemTongHop(String tuKhoa) {
        List<MayTinh> ketQua = new ArrayList<>();
        String tk = tuKhoa.toLowerCase();

        for (PC pc : danhSachPC) {
            if (pc.getMaPC().toLowerCase().contains(tk) ||
                    pc.getTenMay().toLowerCase().contains(tk) ||
                    pc.getHangSX().toLowerCase().contains(tk) ||
                    pc.getLoaiCPU().toLowerCase().contains(tk)) {
                ketQua.add(pc);
            }
        }

        for (LapTop lap : danhSachLapTop) {
            if (lap.getMaLapTop().toLowerCase().contains(tk) ||
                    lap.getTenMay().toLowerCase().contains(tk) ||
                    lap.getHangSX().toLowerCase().contains(tk)) {
                ketQua.add(lap);
            }
        }
        return ketQua;
    }

    // ============================ CẬP NHẬT & XÓA =============================
    @Override
    public boolean capNhatPC(PC pc) {
        try {
            for (int i = 0; i < danhSachPC.size(); i++) {
                if (danhSachPC.get(i).getMaPC().equals(pc.getMaPC())) {
                    danhSachPC.set(i, pc);
                    saveDataPC();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean capNhatLapTop(LapTop lapTop) {
        try {
            for (int i = 0; i < danhSachLapTop.size(); i++) {
                if (danhSachLapTop.get(i).getMaLapTop().equals(lapTop.getMaLapTop())) {
                    danhSachLapTop.set(i, lapTop);
                    saveDataLapTop();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaPC(String maPC) {
        try {
            for (int i = 0; i < danhSachPC.size(); i++) {
                if (danhSachPC.get(i).getMaPC().equals(maPC)) {
                    danhSachPC.remove(i);
                    saveDataPC();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean xoaLapTop(String maLapTop) {
        try {
            for (int i = 0; i < danhSachLapTop.size(); i++) {
                if (danhSachLapTop.get(i).getMaLapTop().equals(maLapTop)) {
                    danhSachLapTop.remove(i);
                    saveDataLapTop();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================ LẤY THEO LOẠI =============================
    @Override
    public List<MayTinh> layMayTinhTheoLoai(String loaiMay) {
        if ("pc".equalsIgnoreCase(loaiMay)) {
            return new ArrayList<>(danhSachPC);
        } else if ("laptop".equalsIgnoreCase(loaiMay)) {
            return new ArrayList<>(danhSachLapTop);
        }
        return new ArrayList<>();
    }

    // ============================ LƯU FILE =============================
    private void saveDataPC() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PC))) {
            for (PC pc : danhSachPC) {
                writer.println(pc.getMaPC() + "|" +
                        pc.getTenMay() + "|" +
                        pc.getHangSX() + "|" +
                        pc.getGia() + "|" +
                        pc.getRam() + "|" +
                        pc.getLoaiCPU());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataLapTop() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_LAPTOP))) {
            for (LapTop lap : danhSachLapTop) {
                writer.println(lap.getMaLapTop() + "|" +
                        lap.getTenMay() + "|" +
                        lap.getHangSX() + "|" +
                        lap.getGia() + "|" +
                        lap.getTrongLuong() + "|" +
                        lap.getKichThuocManHinh());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ============================ ĐỌC FILE =============================
    private void loadData() {
        loadDataPC();
        loadDataLapTop();
    }

    private void loadDataPC() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PC))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] p = line.split("\\|");
                if (p.length == 7) {
                    PC pc = new PC(
                            p[0], // maPC
                            p[1], // tenMay
                            p[2], // hangSX
                            Double.parseDouble(p[3]), // gia
                            p[4],// ma NCC
                            Integer.parseInt(p[5]), // ram
                            p[6] // loaiCPU
                    );
                    danhSachPC.add(pc);
                    if (p[0].startsWith("PC")) {
                        try {
                            int so = Integer.parseInt(p[0].substring(2));
                            if (so >= nextMaPC)
                                nextMaPC = so + 1;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                new File(FILE_PC).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void loadDataLapTop() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LAPTOP))) {
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] p = line.split("\\|");
                if (p.length == 6) {
                    LapTop lap = new LapTop(
                            p[0], // maLapTop
                            p[1], // tenMay
                            p[2], // hangSX
                            Double.parseDouble(p[3]), // gia
                            p[4],// ma NCC
                            Double.parseDouble(p[5]), // trongluong
                            Double.parseDouble(p[6]) // kichThuocManHinh

                    );
                    danhSachLapTop.add(lap);
                    if (p[0].startsWith("LT")) {
                        try {
                            int so = Integer.parseInt(p[0].substring(2));
                            if (so >= nextMaLapTop)
                                nextMaLapTop = so + 1;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                new File(FILE_LAPTOP).createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
