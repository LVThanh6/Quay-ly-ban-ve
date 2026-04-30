package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DBConnection;
import model.Ve;
import model.TrangThaiGhe;
import model.SuatChieu;
import model.Ghe;

public class Ve_DAO {
	public ArrayList<Ve> getAllVe() {
		ArrayList<Ve> dsVe = new ArrayList<Ve>();
		String sql = "SELECT * FROM Ve";
		Connection con = DBConnection.getInstance().getCon();
		
		try (Statement statement = con.createStatement();
			 ResultSet rs = statement.executeQuery(sql)) {
			while (rs.next()) {
				String maSanPham = rs.getString("MaSanPham");
				String maSuatChieu = rs.getString("MaSuatChieu");
				String maGhe = rs.getString("MaGhe");
				String trangThai = rs.getString("TrangThai");
				double phiDichVu = rs.getDouble("PhiDichVu");
				double giaVeThucTe = rs.getDouble("GiaVeThucTe");
				
				Ve ve = new Ve();
				ve.setMaSanPham(maSanPham);
				ve.setTrangThai(trangThai);
				ve.setPhiDichVu(phiDichVu);
				ve.setGiaVeThucTe(giaVeThucTe);
				
				SuatChieu sc = new SuatChieu();
				sc.setMaSuatChieu(maSuatChieu);
				
				Ghe ghe = new Ghe();
				ghe.setMaGhe(maGhe);
				
				TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc, trangThai);
				ve.setTrangThaiGhe(ttg);
				
				dsVe.add(ve);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsVe;
	}
	
	public boolean addVe(Ve ve) {
		String sql = "INSERT INTO Ve (MaSanPham, MaSuatChieu, MaGhe, TrangThai, PhiDichVu, GiaVeThucTe) VALUES (?, ?, ?, ?, ?, ?)";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ve.getMaSanPham());
			stmt.setString(2, ve.getTrangThaiGhe() != null && ve.getTrangThaiGhe().getSuatChieu() != null ? ve.getTrangThaiGhe().getSuatChieu().getMaSuatChieu() : "");
			stmt.setString(3, ve.getTrangThaiGhe() != null && ve.getTrangThaiGhe().getGhe() != null ? ve.getTrangThaiGhe().getGhe().getMaGhe() : "");
			stmt.setString(4, ve.getTrangThai());
			stmt.setDouble(5, ve.getPhiDichVu());
			stmt.setDouble(6, ve.getGiaVeThucTe());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateVe(Ve ve) {
		String sql = "UPDATE Ve SET MaSuatChieu = ?, MaGhe = ?, TrangThai = ?, PhiDichVu = ?, GiaVeThucTe = ? WHERE MaSanPham = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, ve.getTrangThaiGhe() != null && ve.getTrangThaiGhe().getSuatChieu() != null ? ve.getTrangThaiGhe().getSuatChieu().getMaSuatChieu() : "");
			stmt.setString(2, ve.getTrangThaiGhe() != null && ve.getTrangThaiGhe().getGhe() != null ? ve.getTrangThaiGhe().getGhe().getMaGhe() : "");
			stmt.setString(3, ve.getTrangThai());
			stmt.setDouble(4, ve.getPhiDichVu());
			stmt.setDouble(5, ve.getGiaVeThucTe());
			stmt.setString(6, ve.getMaSanPham());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteVe(String maSanPham) {
		String sql = "DELETE FROM Ve WHERE MaSanPham = ?";
		Connection con = DBConnection.getInstance().getCon();
		try(PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maSanPham);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
