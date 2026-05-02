package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.PhongChieu;

public class PhongChieu_DAO {
	public ArrayList<PhongChieu> getAllPhongChieu() {
		ArrayList<PhongChieu> dsPhongChieu = new ArrayList<PhongChieu>();
		String sql = "SELECT * FROM PhongChieu";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsPhongChieu;
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maPhongChieu = rs.getString("MaPhongChieu");
				int soLuongGhe = rs.getInt("SoLuongGhe");
				String dinhDangPhong = rs.getString("DinhDangPhong");
				
				PhongChieu pc = new PhongChieu(maPhongChieu, soLuongGhe, dinhDangPhong);
				dsPhongChieu.add(pc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsPhongChieu;
	}
	
	public boolean addPhongChieu(PhongChieu pc) {
		String sql = "INSERT INTO PhongChieu (MaPhongChieu, SoLuongGhe, DinhDangPhong) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, pc.getMaPhongChieu());
			stmt.setInt(2, pc.getSoLuongGhe());
			stmt.setString(3, pc.getDinhDangPhong());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updatePhongChieu(PhongChieu pc) {
		String sql = "UPDATE PhongChieu SET SoLuongGhe = ?, DinhDangPhong = ? WHERE MaPhongChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, pc.getSoLuongGhe());
			stmt.setString(2, pc.getDinhDangPhong());
			stmt.setString(3, pc.getMaPhongChieu());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePhongChieu(String maPhongChieu) {
		String sql = "DELETE FROM PhongChieu WHERE MaPhongChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhongChieu);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
