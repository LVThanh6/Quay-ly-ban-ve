package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.ChucVu;
import model.NhanVien;

public class NhanVien_DAO {
	public ArrayList<NhanVien> getAllNhanVien() {
		ArrayList<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		String sql = "SELECT * FROM NhanVien";
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maNhanVien = rs.getString("MaNhanVien");
				String hoTen = rs.getString("HoTen");
				String sdt = rs.getString("SDT");
				String matKhau = rs.getString("MatKhau");
				Double luongCoBan = rs.getDouble("LuongCoBan");
				String vaiTro = rs.getString("VaiTro");
				ChucVu vaiTroEnum = null;
				try {
					vaiTroEnum = model.ChucVu.valueOf(vaiTro);
				} catch (Exception e) {
					// Fallback or handle differently if needed. Assuming DB matches Enum
					if(vaiTro != null && vaiTro.toLowerCase().contains("quản lý")) vaiTroEnum = model.ChucVu.QUAN_LY;
					else vaiTroEnum = model.ChucVu.NHAN_VIEN;
				}
				
				NhanVien nv = new NhanVien(maNhanVien, hoTen, matKhau, sdt, luongCoBan, vaiTroEnum);
				dsNhanVien.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsNhanVien;
	}
	
	public boolean addNhanVien(NhanVien nv) {
		String sql = "INSERT INTO NhanVien (MaNhanVien, HoTen, MatKhau, SDT, LuongCoBan, VaiTro) VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getHoTen());
			stmt.setString(3, nv.getMatKhau());
			stmt.setString(4, nv.getSdt());
			stmt.setDouble(5, nv.getLuongCoBan());
			stmt.setString(6, nv.getVaiTro() != null ? nv.getVaiTro().name() : model.ChucVu.NHAN_VIEN.name());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateNhanVien(NhanVien nv) {
		String sql = "UPDATE NhanVien SET HoTen = ?, MatKhau = ?, SDT = ?, LuongCoBan = ?, VaiTro = ? WHERE MaNhanVien = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nv.getHoTen());
			stmt.setString(2, nv.getMatKhau());
			stmt.setString(3, nv.getSdt());
			stmt.setDouble(4, nv.getLuongCoBan());
			stmt.setString(5, nv.getVaiTro() != null ? nv.getVaiTro().name() : model.ChucVu.NHAN_VIEN.name());
			stmt.setString(6, nv.getMaNhanVien());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteNhanVien(String maNhanVien) {
		String sql = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maNhanVien);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
