package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Phim;


public class Phim_DAO {
	
	public ArrayList<Phim> getAllPhim(){
		ArrayList<Phim> dsPhim = new ArrayList<Phim>();
		String sql = "SELECT * FROM Phim";
		Connection con = DBConnection.getInstance().getCon();
		
		try (PreparedStatement preparedStatement = con.prepareStatement(sql);
				ResultSet rs = preparedStatement.executeQuery()) {
		
			while (rs.next()) {
				String maPhim = rs.getString("MaPhim");
				String tenPhim = rs.getString("TenPhim");
				Date ngaySanXuat = rs.getDate("NgaySanXuat");
				String donViSanXuat = rs.getString("DonViSanXuat");
				int gioiHanTuoi = rs.getInt("GioiHanDoTuoi");
				float thoiLuongPhim = rs.getFloat("ThoiLuongPhim");
				String loaiPhim = rs.getString("LoaiPhim");
				
				Phim phim = new Phim(maPhim, tenPhim, ngaySanXuat, donViSanXuat, gioiHanTuoi, gioiHanTuoi, loaiPhim);
				dsPhim.add(phim);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		
		return dsPhim;
	}
	
	public boolean addPhim(Phim phim) {
		String sql = "INSERT INTO Phim (MaPhim, TenPhim, NgaySanXuat, DonViSanXuat, GioiHanDoTuoi, ThoiLuongPhim, LoaiPhim) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			
			preparedStatement.setString(1, phim.getMaPhim());
			preparedStatement.setString(2, phim.getTenPhim());
			preparedStatement.setDate(3, phim.getNgaySanXuat());
			preparedStatement.setString(4, phim.getDonViSanXuat());
			preparedStatement.setInt(5, phim.getGioiHan());
			preparedStatement.setFloat(6, phim.getThoiLuongPhim());
			preparedStatement.setString(7, phim.getLoaiPhim());
			
			int n = preparedStatement.executeUpdate();
			return n > 0;
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	
//	public boolean updatePhim(Phim phim) {
//		String sql = "UPDATE Phim SET maPhim = ?, tenPhim = ?, ngaySanXuat = ?, donViSanXuat = ?, gioiHan";
//	}
	
}
