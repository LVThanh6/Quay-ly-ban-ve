package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Thue;

public class Thue_DAO {
	public ArrayList<Thue> getAllThue() {
		ArrayList<Thue> dsThue = new ArrayList<Thue>();
		String sql = "SELECT * FROM DanhMucThue";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) {
			System.err.println("Lỗi: Kết nối Database trống (null). Vui lòng kiểm tra lại cấu hình kết nối!");
			return dsThue;
		}
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maLoaiThue = rs.getString("MaLoaiThue");
				String tenLoaiThue = rs.getString("TenLoaiThue");
				double mucThue = rs.getDouble("MucThuePhanTram");
				
				Thue t = new Thue(maLoaiThue, tenLoaiThue, mucThue);
				dsThue.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsThue;
	}
	
	public boolean addThue(Thue t) {
		String sql = "INSERT INTO DanhMucThue (MaLoaiThue, TenLoaiThue, MucThuePhanTram) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, t.getMaLoaiThue());
			stmt.setString(2, t.getTenLoaiThue());
			stmt.setDouble(3, t.getMucThuePhanTram());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateThue(Thue t) {
		String sql = "UPDATE DanhMucThue SET TenLoaiThue = ?, MucThuePhanTram = ? WHERE MaLoaiThue = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, t.getTenLoaiThue());
			stmt.setDouble(2, t.getMucThuePhanTram());
			stmt.setString(3, t.getMaLoaiThue());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteThue(String maLoaiThue) {
		String sql = "DELETE FROM DanhMucThue WHERE MaLoaiThue = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maLoaiThue);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
