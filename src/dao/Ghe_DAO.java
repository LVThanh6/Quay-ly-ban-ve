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
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maGhe = rs.getString("MaGhe");
				String loaiGhe = rs.getString("LoaiGhe");
				
				Ghe ghe = new Ghe(maGhe, loaiGhe, null); // Ghế không lưu trực tiếp mã phòng chiếu trong CSDL
				dsGhe.add(ghe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsGhe;
	}
	
	public boolean addGhe(Ghe ghe) {
		String sql = "INSERT INTO Ghe (MaGhe, LoaiGhe) VALUES (?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ghe.getMaGhe());
			stmt.setString(2, ghe.getLoaiGhe());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateGhe(Ghe ghe) {
		String sql = "UPDATE Ghe SET LoaiGhe = ? WHERE MaGhe = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ghe.getLoaiGhe());
			stmt.setString(2, ghe.getMaGhe());
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
