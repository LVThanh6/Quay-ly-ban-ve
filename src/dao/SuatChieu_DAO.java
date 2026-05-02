package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Phim;
import model.PhongChieu;
import model.SuatChieu;

public class SuatChieu_DAO {
	public ArrayList<SuatChieu> getAllSuatChieu() {
		ArrayList<SuatChieu> dsSuatChieu = new ArrayList<SuatChieu>();
		String sql = "SELECT * FROM SuatChieu";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsSuatChieu;
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maSC = rs.getString("MaSuatChieu");
				Timestamp ts = rs.getTimestamp("ThoiGianKhoiChieu");
				LocalDateTime thoiGian = ts != null ? ts.toLocalDateTime() : null;
				double giaVe = rs.getDouble("GiaVeCoBan");
				String maPhim = rs.getString("MaPhim");
				String maPhong = rs.getString("MaPhongChieu");
				
				Phim p = new Phim(); p.setMaPhim(maPhim);
				PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
				
				SuatChieu sc = new SuatChieu(maSC, thoiGian, giaVe, p, pc);
				dsSuatChieu.add(sc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsSuatChieu;
	}
	
	public ArrayList<SuatChieu> getSuatChieuByPhim(String maPhimParam) {
		ArrayList<SuatChieu> dsSuatChieu = new ArrayList<SuatChieu>();
		String sql = "SELECT * FROM SuatChieu WHERE MaPhim = ?";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsSuatChieu;
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhimParam);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String maSC = rs.getString("MaSuatChieu");
					Timestamp ts = rs.getTimestamp("ThoiGianKhoiChieu");
					LocalDateTime thoiGian = ts != null ? ts.toLocalDateTime() : null;
					double giaVe = rs.getDouble("GiaVeCoBan");
					String maPhim = rs.getString("MaPhim");
					String maPhong = rs.getString("MaPhongChieu");
					
					Phim p = new Phim(); p.setMaPhim(maPhim);
					PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
					
					SuatChieu sc = new SuatChieu(maSC, thoiGian, giaVe, p, pc);
					dsSuatChieu.add(sc);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsSuatChieu;
	}
	
	public boolean addSuatChieu(SuatChieu sc) {
		String sql = "INSERT INTO SuatChieu (MaSuatChieu, ThoiGianKhoiChieu, GiaVeCoBan, MaPhim, MaPhongChieu) VALUES (?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, sc.getMaSuatChieu());
			stmt.setTimestamp(2, sc.getThoiGianKhoiChieu() != null ? Timestamp.valueOf(sc.getThoiGianKhoiChieu()) : null);
			stmt.setDouble(3, sc.getGiaVeCoBan());
			stmt.setString(4, sc.getPhim().getMaPhim());
			stmt.setString(5, sc.getPhongChieu().getMaPhongChieu());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateSuatChieu(SuatChieu sc) {
		String sql = "UPDATE SuatChieu SET ThoiGianKhoiChieu = ?, GiaVeCoBan = ?, MaPhim = ?, MaPhongChieu = ? WHERE MaSuatChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setTimestamp(1, sc.getThoiGianKhoiChieu() != null ? Timestamp.valueOf(sc.getThoiGianKhoiChieu()) : null);
			stmt.setDouble(2, sc.getGiaVeCoBan());
			stmt.setString(3, sc.getPhim().getMaPhim());
			stmt.setString(4, sc.getPhongChieu().getMaPhongChieu());
			stmt.setString(5, sc.getMaSuatChieu());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSuatChieu(String maSC) {
		String sql = "DELETE FROM SuatChieu WHERE MaSuatChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSC);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Kiểm tra va chạm lịch chiếu (bao gồm thời gian phim + 15p nghỉ)
	 * @param gapPhut Thời gian nghỉ giữa các suất chiếu (VD: 15)
	 */
	public boolean kiemTraVaChamLich(String maPhong, LocalDateTime startNew, int durationNew, String excludeMaSC, int gapPhut) {
		LocalDateTime endNew = startNew.plusMinutes(durationNew + gapPhut);
		
		String sql = "SELECT sc.ThoiGianKhoiChieu, p.ThoiLuongPhim " +
		             "FROM SuatChieu sc " +
		             "JOIN Phim p ON sc.MaPhim = p.MaPhim " +
		             "WHERE sc.MaPhongChieu = ?";
		
		if (excludeMaSC != null && !excludeMaSC.isEmpty()) {
			sql += " AND sc.MaSuatChieu != ?";
		}
		
		Connection con = DBConnection.getInstance().getCon();
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhong);
			if (excludeMaSC != null && !excludeMaSC.isEmpty()) {
				stmt.setString(2, excludeMaSC);
			}
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LocalDateTime startOld = rs.getTimestamp("ThoiGianKhoiChieu").toLocalDateTime();
					int durationOld = rs.getInt("ThoiLuongPhim");
					LocalDateTime endOld = startOld.plusMinutes(durationOld + gapPhut);
					
					// Kiểm tra giao nhau: (StartA < EndB) AND (EndA > StartB)
					if (startNew.isBefore(endOld) && endNew.isAfter(startOld)) {
						return true; // Có va chạm
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
