package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import ConnectDB.DBConnection;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.Thue;

public class HoaDon_DAO {
    
    public boolean addHoaDon(HoaDon hd) {
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return false;
        
        String sqlHD = "INSERT INTO HoaDon (MaHoaDon, MaNhanVien, SDTKhachHang, MaPhieuDat, MaKhuyenMai, ThoiGianTao, TongTienGoc, TongTienThue, TongThanhToan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlCT = "INSERT INTO ChiTietHoaDon (MaChiTietHoaDon, MaHoaDon, MaSanPham, SoLuongMuc, ThanhTien) VALUES (?, ?, ?, ?, ?)";
        String sqlThue = "INSERT INTO ChiTietThueHoaDon (MaHoaDon, MaLoaiThue, SoTienThue) VALUES (?, ?, ?)";
        
        try {
            con.setAutoCommit(false);
            
            // 1. Chèn Hóa Đơn
            try (PreparedStatement stmtHD = con.prepareStatement(sqlHD)) {
                stmtHD.setString(1, hd.getMaHoaDon());
                stmtHD.setString(2, hd.getNhanVien().getMaNhanVien());
                stmtHD.setString(3, hd.getKhachHang() != null ? hd.getKhachHang().getsDT() : null);
                stmtHD.setString(4, hd.getPhieuDatVe() != null ? hd.getPhieuDatVe().getMaPhieuDat() : null);
                stmtHD.setString(5, hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKhuyenMai() : null);
                stmtHD.setTimestamp(6, new Timestamp(hd.getThoiGianTao().getTime()));
                stmtHD.setDouble(7, hd.getTongTienGoc());
                stmtHD.setDouble(8, hd.getTongTienThue());
                stmtHD.setDouble(9, hd.getTongThanhToan());
                stmtHD.executeUpdate();
            }
            
            // 2. Chèn Chi Tiết Hóa Đơn
            try (PreparedStatement stmtCT = con.prepareStatement(sqlCT)) {
                for (ChiTietHoaDon ct : hd.getChiTietHoaDons()) {
                    stmtCT.setString(1, ct.getMaChiTietHoaDon());
                    stmtCT.setString(2, hd.getMaHoaDon());
                    stmtCT.setString(3, ct.getSanPham().getMaSanPham());
                    stmtCT.setInt(4, ct.getSoLuongMuc());
                    stmtCT.setDouble(5, ct.getThanhTien());
                    stmtCT.addBatch();
                }
                stmtCT.executeBatch();
            }
            
            // 3. Tự động lấy thuế từ SQL và chèn vào ChiTietThueHoaDon
            Thue_DAO thueDAO = new Thue_DAO();
            List<Thue> dsThue = thueDAO.getAllThue();
            hd.setDanhSachThue(dsThue); // Cập nhật lại model
            
            try (PreparedStatement stmtThue = con.prepareStatement(sqlThue)) {
                for (Thue t : dsThue) {
                    double tienThue = hd.getTongTienGoc() * t.getHeSoThue();
                    stmtThue.setString(1, hd.getMaHoaDon());
                    stmtThue.setString(2, t.getMaLoaiThue());
                    stmtThue.setDouble(3, tienThue);
                    stmtThue.addBatch();
                }
                stmtThue.executeBatch();
            }
            
            con.commit();
            return true;
        } catch (SQLException e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { con.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
