package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Ghe;
import model.SuatChieu;
import model.TrangThaiGhe;

public class TrangThaiGhe_DAO {
	public ArrayList<TrangThaiGhe> getAllTrangThai() {
		ArrayList<TrangThaiGhe> dsTrangThai = new ArrayList<TrangThaiGhe>();
		String sql = "SELECT * FROM TrangThaiGhe";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsTrangThai;
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maGhe = rs.getString("MaGhe");
				String maSuatChieu = rs.getString("MaSuatChieu");
				String trangThai = rs.getString("TrangThai");
				
				Ghe ghe = new Ghe();
				ghe.setMaGhe(maGhe);
				SuatChieu sc = new SuatChieu();
				sc.setMaSuatChieu(maSuatChieu);
				
				TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc, trangThai);
				dsTrangThai.add(ttg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsTrangThai;
	}
	
	public ArrayList<TrangThaiGhe> getTrangThaiGheBySuatChieu(String maSuatChieuParam) {
		ArrayList<TrangThaiGhe> dsTrangThai = new ArrayList<TrangThaiGhe>();
		String sql = "SELECT * FROM TrangThaiGhe WHERE MaSuatChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsTrangThai;
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSuatChieuParam);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String maGhe = rs.getString("MaGhe");
					String maSuatChieu = rs.getString("MaSuatChieu");
					String trangThai = rs.getString("TrangThai");
					
					Ghe ghe = new Ghe();
					ghe.setMaGhe(maGhe);
					SuatChieu sc = new SuatChieu();
					sc.setMaSuatChieu(maSuatChieu);
					
					TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc, trangThai);
					dsTrangThai.add(ttg);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsTrangThai;
	}
	
	public boolean addTrangThai(TrangThaiGhe ttg) {
		String sql = "INSERT INTO TrangThaiGhe (MaGhe, MaSuatChieu, TrangThai) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ttg.getGhe().getMaGhe());
			stmt.setString(2, ttg.getSuatChieu().getMaSuatChieu());
			stmt.setString(3, ttg.getTrangThai());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateTrangThai(TrangThaiGhe ttg) {
		String sql = "UPDATE TrangThaiGhe SET TrangThai = ? WHERE MaGhe = ? AND MaSuatChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ttg.getTrangThai());
			stmt.setString(2, ttg.getGhe().getMaGhe());
			stmt.setString(3, ttg.getSuatChieu().getMaSuatChieu());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteTrangThai(String maGhe, String maSuatChieu) {
		String sql = "DELETE FROM TrangThaiGhe WHERE MaGhe = ? AND MaSuatChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maGhe);
			stmt.setString(2, maSuatChieu);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
