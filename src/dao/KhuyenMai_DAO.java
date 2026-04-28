package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.KhuyenMai;

public class KhuyenMai_DAO {
	public ArrayList<KhuyenMai> getAllKhuyenMai() {
		ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<KhuyenMai>();
		String sql = "SELECT * FROM KhuyenMai";
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maKM = rs.getString("MaKhuyenMai");
				String tenKM = rs.getString("TenChuongTrinh");
				double mucGiam = rs.getDouble("HinhThucGiam");
				
				KhuyenMai km = new KhuyenMai(maKM, tenKM, mucGiam);
				dsKhuyenMai.add(km);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsKhuyenMai;
	}
	
	public boolean addKhuyenMai(KhuyenMai km) {
		String sql = "INSERT INTO KhuyenMai (MaKhuyenMai, TenChuongTrinh, HinhThucGiam) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, km.getMaKhuyenMai());
			stmt.setString(2, km.getTenKhuyenMai());
			stmt.setDouble(3, km.getHinhThucGiam());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateKhuyenMai(KhuyenMai km) {
		String sql = "UPDATE KhuyenMai SET TenChuongTrinh = ?, HinhThucGiam = ? WHERE MaKhuyenMai = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, km.getTenKhuyenMai());
			stmt.setDouble(2, km.getHinhThucGiam());
			stmt.setString(3, km.getMaKhuyenMai());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteKhuyenMai(String maKM) {
		String sql = "DELETE FROM KhuyenMai WHERE MaKhuyenMai = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maKM);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
