package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.DBConnection;
import model.ChiTietPhieuDat;
import model.Ve;
import model.TrangThaiGhe;
import model.SuatChieu;
import model.Phim;

public class ChiTietPhieuDat_DAO {

    public boolean addChiTietPhieuDat(ChiTietPhieuDat ct) {
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return false;

        String sql = "INSERT INTO ChiTietPhieuDat (MaChiTietPhieuDat, MaPhieuDat, MaVe, GiaTamTinh) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, ct.getMaChiTietPhieuDat());
            stmt.setString(2, ct.getPhieuDatVe().getMaPhieuDat());
            stmt.setString(3, ct.getVe().getMaSanPham());
            stmt.setDouble(4, ct.getGiaTamTinh());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChiTietPhieuDat> getChiTietByPhieu(String maPhieu) {
        List<ChiTietPhieuDat> list = new ArrayList<>();
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return list;

        String sql = "SELECT ct.*, v.GiaVeThucTe, p.TenPhim, p.GioiHanDoTuoi as GioiHan " +
                     "FROM ChiTietPhieuDat ct " +
                     "JOIN Ve v ON ct.MaVe = v.MaSanPham " +
                     "JOIN SuatChieu sc ON v.MaSuatChieu = sc.MaSuatChieu " +
                     "JOIN Phim p ON sc.MaPhim = p.MaPhim " +
                     "WHERE ct.MaPhieuDat = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maPhieu);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ChiTietPhieuDat ct = new ChiTietPhieuDat();
                ct.setMaChiTietPhieuDat(rs.getString("MaChiTietPhieuDat"));
                ct.setGiaTamTinh(rs.getDouble("GiaTamTinh"));

                Ve v = new Ve();
                v.setMaSanPham(rs.getString("MaVe"));
                v.setGiaVeThucTe(rs.getDouble("GiaVeThucTe"));

                // Phục vụ kiểm tra tuổi
                Phim p = new Phim();
                p.setTenPhim(rs.getString("TenPhim"));
                p.setGioiHan(rs.getInt("GioiHan"));

                SuatChieu sc = new SuatChieu();
                sc.setPhim(p);

                TrangThaiGhe ttg = new TrangThaiGhe();
                ttg.setSuatChieu(sc);
                v.setTrangThaiGhe(ttg);

                ct.setVe(v);
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
