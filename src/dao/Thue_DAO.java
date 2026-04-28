package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Thue;

public class Thue_DAO {
	public ArrayList<Thue> getAllThue() {
		ArrayList<Thue> dsThue = new ArrayList<Thue>();
		String sql = "SELECT * FROM Thue";
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maThue = rs.getString("MaThue");
				String tenThue = rs.getString("TenThue");
				double mucThue = rs.getDouble("MucThue");
				
				Thue t = new Thue(maThue, tenThue, mucThue);
				dsThue.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsThue;
	}
	
	public boolean addThue(Thue t) {
		String sql = "INSERT INTO Thue (MaThue, TenThue, MucThue) VALUES (?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, t.getMaThue());
			stmt.setString(2, t.getTenThue());
			stmt.setDouble(3, t.getMucThue());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateThue(Thue t) {
		String sql = "UPDATE Thue SET TenThue = ?, MucThue = ? WHERE MaThue = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, t.getTenThue());
			stmt.setDouble(2, t.getMucThue());
			stmt.setString(3, t.getMaThue());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteThue(String maThue) {
		String sql = "DELETE FROM Thue WHERE MaThue = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maThue);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
