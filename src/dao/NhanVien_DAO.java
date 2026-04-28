package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.KhachHang;
import model.NhanVien;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getAllNhanVien(){
		ArrayList<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		String sql = "SELECT * FORM NhanVien";
		DBConnection.getInstance().connect();
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql);
				) {
			String maNhanVien = rs.getString("MaNhanVien");
			String hoTen = rs.getString("TenNhanVien");
			String sdt = rs.getString("SDT");
			Double luongCoBan = rs.getDouble("LuongCoBan");
			String vaiTro = rs.getString("VaiTro");
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dsNhanVien;
	}
	
	public boolean addNhanVien(NhanVien nv) {
		String sql = "INSERT INTO KhachHang (SDT, hoTen, ngaySinh) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			preparedStatement.setString(1, nv.getMaNhanVien());
			preparedStatement.setString(2, nv.getHoTen());
			preparedStatement.setString(3, nv.getSdt());
			preparedStatement.setDouble(4, nv.getLuongCoBan());
			preparedStatement.setString(5, nv.getVaiTro());
			int n = preparedStatement.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	
//	public boolean updateNhanVien(NhanVien nv) {
//		String sql = "UPDATE NhanVien SET MaNhanVien = ?, HoTen = ?, SĐT  WHERE MaNhanVien = ?";
//	}
}
