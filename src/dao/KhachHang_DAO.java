package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.KhachHang;

public class KhachHang_DAO {

	public ArrayList<KhachHang> getAllKhachHang() {
	    ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
	    String sql = "SELECT * FROM KhachHang";
	    Connection con = DBConnection.getInstance().getCon();
	    
	    // Kiểm tra nếu con bị null thì thoát sớm để tránh NullPointerException
	    if (con == null) {
	        System.err.println("Lỗi: Kết nối Database trống (null). Vui lòng kiểm tra lại cấu hình kết nối!");
	        return dsKhachHang; 
	    }

	    try (Statement statement = con.createStatement();
	         ResultSet rs = statement.executeQuery(sql)) {
	        
	        while (rs.next()) {
	            String SDT = rs.getString("SDT"); 
	            String hoTen = rs.getString("hoTen");
	            Date ngaySinh = rs.getDate("ngaySinh");
	            
	            KhachHang kh = new KhachHang(SDT, hoTen, ngaySinh);
	            dsKhachHang.add(kh);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsKhachHang;
	}

	public boolean addKhachHang(KhachHang kh) {
		String sql = "INSERT INTO KhachHang (SDT, hoTen, ngaySinh) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, kh.getsDT());
			stmt.setString(2, kh.getHoTen());
			if (kh.getNgaySinh() != null) {
				stmt.setDate(3, new java.sql.Date(kh.getNgaySinh().getTime()));
			} else {
				stmt.setNull(3, java.sql.Types.DATE);
			}
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateKhachHang(KhachHang kh) {
		String sql = "UPDATE KhachHang SET hoTen = ?, ngaySinh = ? WHERE SDT = ?";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, kh.getHoTen());
			if (kh.getNgaySinh() != null) {
				stmt.setDate(2, new java.sql.Date(kh.getNgaySinh().getTime()));
			} else {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			stmt.setString(3, kh.getsDT());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public KhachHang getKhachHangBySdt(String sdt) {
		String sql = "SELECT * FROM KhachHang WHERE SDT = ?";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return null;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, sdt);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new KhachHang(rs.getString("SDT"), rs.getString("hoTen"), rs.getDate("ngaySinh"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteKhachHang(String sdt) {
		String sql = "DELETE FROM KhachHang WHERE SDT = ?";
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			
			stmt.setString(1, sdt);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}


