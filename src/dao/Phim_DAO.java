package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Phim;

public class Phim_DAO {
	
	public ArrayList<Phim> getAllPhim(){
		ArrayList<Phim> dsPhim = new ArrayList<Phim>();
		String sql = "SELECT * FROM Phim";
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
		
			while (rs.next()) {
				String maPhim = rs.getString("MaPhim");
				String tenPhim = rs.getString("TenPhim");
				Date ngaySanXuat = rs.getDate("NgaySanXuat");
				String donViSanXuat = rs.getString("DonViSanXuat");
				int gioiHanTuoi = rs.getInt("GioiHanDoTuoi");
				int thoiLuongPhim = rs.getInt("ThoiLuongPhim");
				String loaiPhim = rs.getString("LoaiPhim");
				
				Phim phim = new Phim(maPhim, tenPhim, ngaySanXuat, donViSanXuat, gioiHanTuoi, thoiLuongPhim, loaiPhim);
				dsPhim.add(phim);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dsPhim;
	}
	
	public boolean addPhim(Phim phim) {
		String sql = "INSERT INTO Phim (MaPhim, TenPhim, NgaySanXuat, DonViSanXuat, GioiHanDoTuoi, ThoiLuongPhim, LoaiPhim) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, phim.getMaPhim());
			stmt.setString(2, phim.getTenPhim());
			stmt.setDate(3, phim.getNgaySanXuat());
			stmt.setString(4, phim.getDonViSanXuat());
			stmt.setInt(5, phim.getGioiHan());
			stmt.setInt(6, phim.getThoiLuongPhim());
			stmt.setString(7, phim.getLoaiPhim());
			
			int n = stmt.executeUpdate();
			return n > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updatePhim(Phim phim) {
		String sql = "UPDATE Phim SET TenPhim = ?, NgaySanXuat = ?, DonViSanXuat = ?, GioiHanDoTuoi = ?, ThoiLuongPhim = ?, LoaiPhim = ? WHERE MaPhim = ?";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, phim.getTenPhim());
			stmt.setDate(2, phim.getNgaySanXuat());
			stmt.setString(3, phim.getDonViSanXuat());
			stmt.setInt(4, phim.getGioiHan());
			stmt.setInt(5, phim.getThoiLuongPhim());
			stmt.setString(6, phim.getLoaiPhim());
			stmt.setString(7, phim.getMaPhim());
			
			int n = stmt.executeUpdate();
			return n > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deletePhim(String maPhim) {
		String sql = "DELETE FROM Phim WHERE MaPhim = ?";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, maPhim);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
