package test;

import dao.*;
import model.*;
import ConnectDB.DBConnection;
import java.util.ArrayList;

public class TestDAO {
    public static void main(String[] args) {
        try {
            System.out.println("Connecting to DB...");
            DBConnection.getInstance().connect();
            System.out.println("Connected!");

            try {
                System.out.println("Testing PhongChieu...");
                PhongChieu_DAO pcDAO = new PhongChieu_DAO();
                ArrayList<PhongChieu> dsPC = pcDAO.getAllPhongChieu();
                System.out.println("PhongChieu count: " + dsPC.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing Ghe...");
                Ghe_DAO gheDAO = new Ghe_DAO();
                ArrayList<Ghe> dsGhe = gheDAO.getAllGhe();
                System.out.println("Ghe count: " + dsGhe.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing KhuyenMai...");
                KhuyenMai_DAO kmDAO = new KhuyenMai_DAO();
                ArrayList<KhuyenMai> dsKM = kmDAO.getAllKhuyenMai();
                System.out.println("KhuyenMai count: " + dsKM.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing ComboBapNuoc...");
                ComboBapNuoc_DAO comboDAO = new ComboBapNuoc_DAO();
                ArrayList<ComboBapNuoc> dsCombo = comboDAO.getAllCombo();
                System.out.println("ComboBapNuoc count: " + dsCombo.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing Thue...");
                Thue_DAO thueDAO = new Thue_DAO();
                ArrayList<Thue> dsThue = thueDAO.getAllThue();
                System.out.println("Thue count: " + dsThue.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing TrangThaiGhe...");
                TrangThaiGhe_DAO ttgDAO = new TrangThaiGhe_DAO();
                ArrayList<TrangThaiGhe> dsTTG = ttgDAO.getAllTrangThai();
                System.out.println("TrangThaiGhe count: " + dsTTG.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Testing SuatChieu...");
                SuatChieu_DAO scDAO = new SuatChieu_DAO();
                ArrayList<SuatChieu> dsSC = scDAO.getAllSuatChieu();
                System.out.println("SuatChieu count: " + dsSC.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
