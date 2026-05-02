package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ConnectDB.DBConnection;

public class SanPham_DAO {
    public boolean addSanPham(String maSP, String loaiSP) {
        String sql = "INSERT INTO SanPham (MaSanPham, LoaiSanPham) VALUES (?, ?)";
        Connection con = DBConnection.getInstance().getCon();
        if (con == null) return false;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maSP);
            stmt.setString(2, loaiSP);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
