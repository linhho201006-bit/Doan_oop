package service.Impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.TaiKhoan;
import service.TaiKhoanService;

public class TaiKhoanServiceImpl implements TaiKhoanService {

    private static final String FILE_PATH = "TaiKhoan.txt";
    private List<TaiKhoan> danhSachTaiKhoan;

    public TaiKhoanServiceImpl() {
        danhSachTaiKhoan = docFile();
        if (danhSachTaiKhoan == null) {
            danhSachTaiKhoan = new ArrayList<>();
        }
    }

    // ========== HÀM ĐỌC / GHI FILE ==========
    @SuppressWarnings("unchecked")
    private List<TaiKhoan> docFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<TaiKhoan>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void ghiFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(danhSachTaiKhoan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ========== CÁC CHỨC NĂNG ==========

    @Override
    public boolean taoTaiKhoan(TaiKhoan taiKhoan) {
        if (taiKhoan == null || !kiemTraRangBuocTenDangNhap(taiKhoan.getTenDangNhap())) {
            return false;
        }
        danhSachTaiKhoan.add(taiKhoan);
        ghiFile();
        return true;
    }

    @Override
    public List<TaiKhoan> layTatCaTaiKhoan() {
        return new ArrayList<>(danhSachTaiKhoan);
    }

    @Override
    public TaiKhoan timTaiKhoanTheoTenDangNhap(String tenDangNhap) {
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                return tk;
            }
        }
        return null;
    }

    @Override
    public TaiKhoan timTaiKhoanTheoMaNV(String maNV) {
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getMaNV().equalsIgnoreCase(maNV)) {
                return tk;
            }
        }
        return null;
    }

    @Override
    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getTenDangNhap().equalsIgnoreCase(tenDangNhap)
                    && tk.getMatKhau().equals(matKhau)) {
                return tk;
            }
        }
        return null;
    }

    @Override
    public boolean doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi) {
        TaiKhoan tk = timTaiKhoanTheoTenDangNhap(tenDangNhap);
        if (tk != null && tk.getMatKhau().equals(matKhauCu)) {
            tk.setMatKhau(matKhauMoi);
            ghiFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean kiemTraRangBuocMaNV(String maNV) {
        // Ở đây chỉ kiểm tra trùng lặp, thực tế nên kiểm tra trong NhanVienService
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getMaNV().equalsIgnoreCase(maNV)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean kiemTraRangBuocTenDangNhap(String tenDangNhap) {
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                return false;
            }
        }
        return true;
    }

    
    @Override
    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan) {
        for (int i = 0; i < danhSachTaiKhoan.size(); i++) {
            if (danhSachTaiKhoan.get(i).getTenDangNhap().equalsIgnoreCase(taiKhoan.getTenDangNhap())) {
                danhSachTaiKhoan.set(i, taiKhoan);
                ghiFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoaTaiKhoan(String tenDangNhap) {
        TaiKhoan tk = timTaiKhoanTheoTenDangNhap(tenDangNhap);
        if (tk != null) {
            danhSachTaiKhoan.remove(tk);
            ghiFile();
            return true;
        }
        return false;
    }


    @Override
    public int thongKeSoLuongTaiKhoanTheoVaiTro(String vaiTro) {
        int dem = 0;
        for (TaiKhoan tk : danhSachTaiKhoan) {
            if (tk.getVaiTro().equalsIgnoreCase(vaiTro)) {
                dem++;
            }
        }
        return dem;
    }
}
