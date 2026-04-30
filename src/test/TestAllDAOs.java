package test;

import ConnectDB.DBConnection;
import dao.*;
import java.util.List;

public class TestAllDAOs {
    public static void main(String[] args) {
        try {
            DBConnection.getInstance().connect();
            
            try {
                System.out.println("Testing NhanVien...");
                new NhanVien_DAO().getAllNhanVien();
                System.out.println("NhanVien OK");
            } catch (Exception e) { System.out.println("NhanVien ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing KhachHang...");
                new KhachHang_DAO().getAllKhachHang();
                System.out.println("KhachHang OK");
            } catch (Exception e) { System.out.println("KhachHang ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing Phim...");
                new Phim_DAO().getAllPhim();
                System.out.println("Phim OK");
            } catch (Exception e) { System.out.println("Phim ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing PhongChieu...");
                new PhongChieu_DAO().getAllPhongChieu();
                System.out.println("PhongChieu OK");
            } catch (Exception e) { System.out.println("PhongChieu ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing SuatChieu...");
                new SuatChieu_DAO().getAllSuatChieu();
                System.out.println("SuatChieu OK");
            } catch (Exception e) { System.out.println("SuatChieu ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing Ghe...");
                new Ghe_DAO().getAllGhe();
                System.out.println("Ghe OK");
            } catch (Exception e) { System.out.println("Ghe ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing TrangThaiGhe...");
                new TrangThaiGhe_DAO().getAllTrangThai();
                System.out.println("TrangThaiGhe OK");
            } catch (Exception e) { System.out.println("TrangThaiGhe ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing ComboBapNuoc...");
                new ComboBapNuoc_DAO().getAllCombo();
                System.out.println("ComboBapNuoc OK");
            } catch (Exception e) { System.out.println("ComboBapNuoc ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing KhuyenMai...");
                new KhuyenMai_DAO().getAllKhuyenMai();
                System.out.println("KhuyenMai OK");
            } catch (Exception e) { System.out.println("KhuyenMai ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing Thue...");
                new Thue_DAO().getAllThue();
                System.out.println("Thue OK");
            } catch (Exception e) { System.out.println("Thue ERROR: " + e.getMessage()); }

            try {
                System.out.println("Testing Ve...");
                new Ve_DAO().getAllVe();
                System.out.println("Ve OK");
            } catch (Exception e) { System.out.println("Ve ERROR: " + e.getMessage()); }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
