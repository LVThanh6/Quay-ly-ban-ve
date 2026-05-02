package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.ComboBapNuoc;

public class ComboBapNuoc_DAO {
	public ArrayList<ComboBapNuoc> getAllCombo() {
		ArrayList<ComboBapNuoc> dsCombo = new ArrayList<ComboBapNuoc>();
		String sql = "SELECT * FROM ComboBapNuoc";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsCombo;
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maCombo = rs.getString("MaSanPham");
				String tenCombo = rs.getString("TenCombo");
				double giaBan = rs.getDouble("GiaComboCoBan");
				
				ComboBapNuoc cb = new ComboBapNuoc(maCombo, tenCombo, giaBan);
				dsCombo.add(cb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsCombo;
	}
	
	public boolean addCombo(ComboBapNuoc cb) {
		String sql = "INSERT INTO ComboBapNuoc (MaSanPham, TenCombo, GiaComboCoBan) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, cb.getMaSanPham());
			stmt.setString(2, cb.getTenSanPham());
			stmt.setDouble(3, cb.getGiaBanCoBan());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateCombo(ComboBapNuoc cb) {
		String sql = "UPDATE ComboBapNuoc SET TenCombo = ?, GiaComboCoBan = ? WHERE MaSanPham = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, cb.getTenSanPham());
			stmt.setDouble(2, cb.getGiaBanCoBan());
			stmt.setString(3, cb.getMaSanPham());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteCombo(String maCombo) {
		String sql = "DELETE FROM ComboBapNuoc WHERE MaSanPham = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maCombo);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
