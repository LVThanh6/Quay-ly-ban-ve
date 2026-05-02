package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Ghe;
import model.PhongChieu;

public class Ghe_DAO {
	public ArrayList<Ghe> getAllGhe() {
		ArrayList<Ghe> dsGhe = new ArrayList<Ghe>();
		String sql = "SELECT * FROM Ghe";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsGhe;
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maGhe = rs.getString("MaGhe");
				String loaiGhe = rs.getString("LoaiGhe");
				String hangGhe = rs.getString("HangGhe");
				int cotGhe = rs.getInt("CotGhe");
				String trangThai = rs.getString("TrangThai");
				String maPC = rs.getString("MaPhongChieu");
				
				// Hỗ trợ tự động trích xuất Hàng/Cột từ MaGhe nếu DB để trống (Dành cho SQL tùy chỉnh)
				if (hangGhe == null || hangGhe.isEmpty()) {
					hangGhe = maGhe.substring(0, 1);
				}
				if (cotGhe == 0 && maGhe.length() >= 3) {
					try {
						// Thử lấy 2 số sau chữ cái đầu (VD: A01 -> 01)
						cotGhe = Integer.parseInt(maGhe.substring(1, 3));
					} catch (Exception e) {}
				}
				
				Ghe ghe = new Ghe(maGhe, loaiGhe, hangGhe, cotGhe, trangThai, new PhongChieu(maPC, 0, ""));
				dsGhe.add(ghe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsGhe;
	}

	public ArrayList<Ghe> getGheByPhongChieu(String maPhong) {
		ArrayList<Ghe> dsGhe = new ArrayList<Ghe>();
		String sql = "SELECT * FROM Ghe WHERE MaPhongChieu = ?";
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return dsGhe;
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maPhong);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					String maGhe = rs.getString("MaGhe");
					String hang = rs.getString("HangGhe");
					int cot = rs.getInt("CotGhe");
					
					if (hang == null || hang.isEmpty()) hang = maGhe.substring(0, 1);
					if (cot == 0 && maGhe.length() >= 3) {
						try { cot = Integer.parseInt(maGhe.substring(1, 3)); } catch (Exception e) {}
					}

					dsGhe.add(new Ghe(
						maGhe,
						rs.getString("LoaiGhe"),
						hang,
						cot,
						rs.getString("TrangThai"),
						new PhongChieu(maPhong, 0, "")
					));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsGhe;
	}
	
	public boolean addMultipleGhe(ArrayList<Ghe> list) {
		String sql = "INSERT INTO Ghe (MaGhe, LoaiGhe, HangGhe, CotGhe, TrangThai, MaPhongChieu) VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			con.setAutoCommit(false);
			for (Ghe ghe : list) {
				stmt.setString(1, ghe.getMaGhe());
				stmt.setString(2, ghe.getLoaiGhe());
				stmt.setString(3, ghe.getHangGhe());
				stmt.setInt(4, ghe.getCotGhe());
				stmt.setString(5, ghe.getTrangThai());
				stmt.setString(6, ghe.getPhongChieu() != null ? ghe.getPhongChieu().getMaPhongChieu() : null);
				stmt.addBatch();
			}
			stmt.executeBatch();
			con.commit();
			con.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			try { con.rollback(); } catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
	}

	public boolean addGhe(Ghe ghe) {
		String sql = "INSERT INTO Ghe (MaGhe, LoaiGhe, HangGhe, CotGhe, TrangThai, MaPhongChieu) VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ghe.getMaGhe());
			stmt.setString(2, ghe.getLoaiGhe());
			stmt.setString(3, ghe.getHangGhe());
			stmt.setInt(4, ghe.getCotGhe());
			stmt.setString(5, ghe.getTrangThai());
			stmt.setString(6, ghe.getPhongChieu() != null ? ghe.getPhongChieu().getMaPhongChieu() : null);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateGhe(Ghe ghe) {
		String sql = "UPDATE Ghe SET LoaiGhe = ?, HangGhe = ?, CotGhe = ?, TrangThai = ? WHERE MaGhe = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ghe.getLoaiGhe());
			stmt.setString(2, ghe.getHangGhe());
			stmt.setInt(3, ghe.getCotGhe());
			stmt.setString(4, ghe.getTrangThai());
			stmt.setString(5, ghe.getMaGhe());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteGhe(String maGhe) {
		String sql = "DELETE FROM Ghe WHERE MaGhe = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maGhe);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
