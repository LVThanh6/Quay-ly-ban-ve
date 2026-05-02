package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import dao.SuatChieu_DAO;
import model.Phim;
import model.PhongChieu;
import model.SuatChieu;
import view.FrmSuatChieu;

public class SuatChieu_Controller implements ActionListener {

	private FrmSuatChieu view;
	private SuatChieu_DAO dao;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public SuatChieu_Controller(FrmSuatChieu view) {
		this.view = view;
		this.dao = new SuatChieu_DAO();
		dao.Phim_DAO phimDAO = new dao.Phim_DAO();
		dao.PhongChieu_DAO phongDAO = new dao.PhongChieu_DAO();
		this.view.setDataLists(phimDAO.getAllPhim(), phongDAO.getAllPhongChieu());
		this.view.updateTable(this.dao.getAllSuatChieu());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themSuatChieu();
		} else if (o.equals(view.getBtnSua())) {
			suaSuatChieu();
		} else if (o.equals(view.getBtnXoa())) {
			xoaSuatChieu();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themSuatChieu() {
		String maSC = view.getTxtMaSuatChieu().getText().trim();
		String ngayStr = view.getTxtNgay().getText().trim();
		String gioStr = view.getTxtGioBatDau().getText().trim();
		String giaVeStr = view.getTxtGiaVe().getText().trim();
		String maPhim = view.getSelectedMaPhim();
		String maPhong = view.getSelectedMaPhong();

		if (maSC.isEmpty() || ngayStr.isEmpty() || gioStr.isEmpty() || giaVeStr.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			LocalDateTime startNew = LocalDateTime.parse(ngayStr + " " + gioStr + ":00", formatter);
			double giaVe = Double.parseDouble(giaVeStr);
			
			// Lấy thời lượng phim
			int duration = 0;
			dao.Phim_DAO phimDAO = new dao.Phim_DAO();
			for(Phim p : phimDAO.getAllPhim()) {
				if(p.getMaPhim().equals(maPhim)) {
					duration = p.getThoiLuongPhim();
					break;
				}
			}

			// Kiểm tra va chạm lịch (Gap 15p)
			if (dao.kiemTraVaChamLich(maPhong, startNew, duration, null, 15)) {
				JOptionPane.showMessageDialog(view, "Xung đột lịch chiếu! Phòng " + maPhong + " đang bận hoặc cần thời gian nghỉ 15p.");
				return;
			}

			Phim p = new Phim(); p.setMaPhim(maPhim);
			PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
			
			SuatChieu sc = new SuatChieu(maSC, startNew, giaVe, p, pc);
			
			if (dao.addSuatChieu(sc)) {
				view.updateTable(dao.getAllSuatChieu());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá vé phải là số!");
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(view, "Ngày (yyyy-MM-dd) hoặc Giờ (HH:mm) không hợp lệ!");
		}
	}

	private void suaSuatChieu() {
		String maSC = view.getTxtMaSuatChieu().getText().trim();
		String ngayStr = view.getTxtNgay().getText().trim();
		String gioStr = view.getTxtGioBatDau().getText().trim();
		String giaVeStr = view.getTxtGiaVe().getText().trim();
		String maPhim = view.getSelectedMaPhim();
		String maPhong = view.getSelectedMaPhong();

		if (maSC.isEmpty() || ngayStr.isEmpty() || gioStr.isEmpty() || giaVeStr.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			LocalDateTime startNew = LocalDateTime.parse(ngayStr + " " + gioStr + ":00", formatter);
			double giaVe = Double.parseDouble(giaVeStr);
			
			// Lấy thời lượng phim
			int duration = 0;
			dao.Phim_DAO phimDAO = new dao.Phim_DAO();
			for(Phim p : phimDAO.getAllPhim()) {
				if(p.getMaPhim().equals(maPhim)) {
					duration = p.getThoiLuongPhim();
					break;
				}
			}

			// Kiểm tra va chạm lịch (Gap 15p, loại trừ chính nó)
			if (dao.kiemTraVaChamLich(maPhong, startNew, duration, maSC, 15)) {
				JOptionPane.showMessageDialog(view, "Xung đột lịch chiếu! Phòng " + maPhong + " đang bận hoặc cần thời gian nghỉ 15p.");
				return;
			}

			Phim p = new Phim(); p.setMaPhim(maPhim);
			PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
			
			SuatChieu sc = new SuatChieu(maSC, startNew, giaVe, p, pc);
			
			if (dao.updateSuatChieu(sc)) {
				view.updateTable(dao.getAllSuatChieu());
				JOptionPane.showMessageDialog(view, "Cập nhật suất chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật suất chiếu thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá vé phải là số!");
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(view, "Ngày (yyyy-MM-dd) hoặc Giờ (HH:mm) không hợp lệ!");
		}
	}

	private void xoaSuatChieu() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa trên bảng!");
			return;
		}

		String maSC = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteSuatChieu(maSC)) {
				view.updateTable(dao.getAllSuatChieu());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa suất chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa suất chiếu thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaSuatChieu().setText("");
		view.getTxtNgay().setText("");
		view.getTxtGioBatDau().setText("");
		view.getTxtGiaVe().setText("");
		view.clearPhimSelection();
		view.clearPhongSelection();
		
		view.getTxtMaSuatChieu().requestFocus();
	}
}
