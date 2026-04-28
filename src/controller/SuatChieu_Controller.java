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
		String thoiGianStr = view.getTxtThoiGian().getText().trim();
		String giaVeStr = view.getTxtGiaVe().getText().trim();
		String maPhim = view.getTxtMaPhim().getText().trim();
		String maPhong = view.getTxtMaPhongChieu().getText().trim();

		if (maSC.isEmpty() || thoiGianStr.isEmpty() || giaVeStr.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			LocalDateTime thoiGian = LocalDateTime.parse(thoiGianStr, formatter);
			double giaVe = Double.parseDouble(giaVeStr);
			
			Phim p = new Phim(); p.setMaPhim(maPhim);
			PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
			
			SuatChieu sc = new SuatChieu(maSC, thoiGian, giaVe, p, pc);
			
			if (dao.addSuatChieu(sc)) {
				view.updateTable(dao.getAllSuatChieu());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm suất chiếu thất bại (Mã SC có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá vé phải là số!");
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(view, "Thời gian phải có định dạng yyyy-MM-dd HH:mm:ss");
		}
	}

	private void suaSuatChieu() {
		String maSC = view.getTxtMaSuatChieu().getText().trim();
		String thoiGianStr = view.getTxtThoiGian().getText().trim();
		String giaVeStr = view.getTxtGiaVe().getText().trim();
		String maPhim = view.getTxtMaPhim().getText().trim();
		String maPhong = view.getTxtMaPhongChieu().getText().trim();

		if (maSC.isEmpty() || thoiGianStr.isEmpty() || giaVeStr.isEmpty() || maPhim.isEmpty() || maPhong.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			LocalDateTime thoiGian = LocalDateTime.parse(thoiGianStr, formatter);
			double giaVe = Double.parseDouble(giaVeStr);
			
			Phim p = new Phim(); p.setMaPhim(maPhim);
			PhongChieu pc = new PhongChieu(); pc.setMaPhongChieu(maPhong);
			
			SuatChieu sc = new SuatChieu(maSC, thoiGian, giaVe, p, pc);
			
			if (dao.updateSuatChieu(sc)) {
				view.updateTable(dao.getAllSuatChieu());
				JOptionPane.showMessageDialog(view, "Cập nhật suất chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật suất chiếu thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá vé phải là số!");
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(view, "Thời gian phải có định dạng yyyy-MM-dd HH:mm:ss");
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
		view.getTxtThoiGian().setText("");
		view.getTxtGiaVe().setText("");
		view.getTxtMaPhim().setText("");
		view.getTxtMaPhongChieu().setText("");
		
		view.getTxtMaSuatChieu().requestFocus();
	}
}
