package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ConnectDB.DBConnection;
import model.PhieuDatVe;
import model.KhachHang;
import model.NhanVien;

public class PhieuDatVe_DAO {

    public boolean addPhieuDatVe(PhieuDatVe pdv) {
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return false;

        String sql = "INSERT INTO PhieuDatVe (MaPhieuDat, SoLuongVe, NgayDat, ThoiGianGiuCho, TrangThai, SDTKhachHang, MaNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, pdv.getMaPhieuDat());
            stmt.setInt(2, pdv.getSoLuongVe());
            stmt.setTimestamp(3, new Timestamp(pdv.getNgayDat().getTime()));
            stmt.setTimestamp(4, new Timestamp(pdv.getThoiGianGiuCho().getTime()));
            stmt.setString(5, pdv.getTrangThai());
            stmt.setString(6, pdv.getKhachHang() != null ? pdv.getKhachHang().getsDT() : null);
            stmt.setString(7, pdv.getNhanVien() != null ? pdv.getNhanVien().getMaNhanVien() : null);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public PhieuDatVe getPhieuDatVeById(String id) {
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return null;

        String sql = "SELECT pd.*, kh.HoTen as TenKH, nv.HoTen as TenNV " +
                     "FROM PhieuDatVe pd " +
                     "LEFT JOIN KhachHang kh ON pd.SDTKhachHang = kh.SDT " +
                     "LEFT JOIN NhanVien nv ON pd.MaNhanVien = nv.MaNhanVien " +
                     "WHERE pd.MaPhieuDat = ? OR pd.SDTKhachHang = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.setString(2, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PhieuDatVe pdv = new PhieuDatVe();
                pdv.setMaPhieuDat(rs.getString("MaPhieuDat"));
                pdv.setSoLuongVe(rs.getInt("SoLuongVe"));
                pdv.setNgayDat(rs.getTimestamp("NgayDat"));
                pdv.setThoiGianGiuCho(rs.getTimestamp("ThoiGianGiuCho"));
                pdv.setTrangThai(rs.getString("TrangThai"));

                KhachHang kh = new KhachHang();
                kh.setsDT(rs.getString("SDTKhachHang"));
                kh.setHoTen(rs.getString("TenKH"));
                pdv.setKhachHang(kh);

                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setHoTen(rs.getString("TenNV"));
                pdv.setNhanVien(nv);

                return pdv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTrangThai(String maPhieu, String trangThai) {
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return false;

        String sql = "UPDATE PhieuDatVe SET TrangThai = ? WHERE MaPhieuDat = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, trangThai);
            stmt.setString(2, maPhieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
