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
import model.PhongChieu;

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

	/**
	 * Tìm kiếm vé dựa trên nhiều yếu tố (Tên phim, Mã ghế, SĐT, Ngày đặt...)
	 * Chỉ cần thỏa 1 trong các điều kiện (OR)
	 */
	public ArrayList<model.VeFullInfo> searchTickets(String keyword) {
		ArrayList<model.VeFullInfo> list = new ArrayList<>();
		String sql = "SELECT v.*, sc.ThoiGianKhoiChieu, p.TenPhim, sc.MaPhongChieu, " +
		             "pdv.SDTKhachHang, pdv.NgayDat, pdv.MaPhieuDat " +
		             "FROM Ve v " +
		             "JOIN SuatChieu sc ON v.MaSuatChieu = sc.MaSuatChieu " +
		             "JOIN Phim p ON sc.MaPhim = p.MaPhim " +
		             "JOIN ChiTietPhieuDat ctpd ON v.MaSanPham = ctpd.MaVe " +
		             "JOIN PhieuDatVe pdv ON ctpd.MaPhieuDat = pdv.MaPhieuDat " +
		             "WHERE p.TenPhim LIKE ? OR p.MaPhim LIKE ? OR pdv.SDTKhachHang LIKE ? " +
		             "OR v.MaGhe LIKE ? OR v.MaSanPham LIKE ? OR pdv.MaPhieuDat LIKE ?";
		
		Connection con = DBConnection.getInstance().getCon();
		if (con == null) return list;
		
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			String kw = "%" + keyword + "%";
			for(int i=1; i<=6; i++) stmt.setString(i, kw);
			
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					model.VeFullInfo info = new model.VeFullInfo();
					info.setMaVe(rs.getString("MaSanPham"));
					info.setMaGhe(rs.getString("MaGhe"));
					info.setTenPhim(rs.getString("TenPhim"));
					info.setMaPhong(rs.getString("MaPhongChieu"));
					info.setThoiGianChieu(rs.getTimestamp("ThoiGianKhoiChieu").toLocalDateTime());
					info.setSdtKhach(rs.getString("SDTKhachHang"));
					info.setNgayDat(rs.getTimestamp("NgayDat") != null ? rs.getTimestamp("NgayDat").toLocalDateTime() : null);
					info.setTrangThai(rs.getString("TrangThai"));
					info.setMaPhieuDat(rs.getString("MaPhieuDat"));
					info.setGiaVe(rs.getDouble("GiaVeThucTe"));
					list.add(info);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
