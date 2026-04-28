package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.TrangThaiGhe_DAO;
import model.Ghe;
import model.SuatChieu;
import model.TrangThaiGhe;
import view.FrmTrangThaiGhe;

public class TrangThaiGhe_Controller implements ActionListener {

	private FrmTrangThaiGhe view;
	private TrangThaiGhe_DAO dao;

	public TrangThaiGhe_Controller(FrmTrangThaiGhe view) {
		this.view = view;
		this.dao = new TrangThaiGhe_DAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themTrangThai();
		} else if (o.equals(view.getBtnSua())) {
			suaTrangThai();
		} else if (o.equals(view.getBtnXoa())) {
			xoaTrangThai();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themTrangThai() {
		String maGhe = view.getTxtMaGhe().getText().trim();
		String maSC = view.getTxtMaSuatChieu().getText().trim();
		String trangThai = view.getTxtTrangThai().getText().trim();

		if (maGhe.isEmpty() || maSC.isEmpty() || trangThai.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		Ghe ghe = new Ghe(); ghe.setMaGhe(maGhe);
		SuatChieu sc = new SuatChieu(); sc.setMaSuatChieu(maSC);
		TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc, trangThai);
		
		if (dao.addTrangThai(ttg)) {
			view.updateTable(dao.getAllTrangThai());
			xoaTrang();
			JOptionPane.showMessageDialog(view, "Thêm trạng thái thành công!");
		} else {
			JOptionPane.showMessageDialog(view, "Thêm trạng thái thất bại!");
		}
	}

	private void suaTrangThai() {
		String maGhe = view.getTxtMaGhe().getText().trim();
		String maSC = view.getTxtMaSuatChieu().getText().trim();
		String trangThai = view.getTxtTrangThai().getText().trim();

		if (maGhe.isEmpty() || maSC.isEmpty() || trangThai.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		Ghe ghe = new Ghe(); ghe.setMaGhe(maGhe);
		SuatChieu sc = new SuatChieu(); sc.setMaSuatChieu(maSC);
		TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc, trangThai);
		
		if (dao.updateTrangThai(ttg)) {
			view.updateTable(dao.getAllTrangThai());
			JOptionPane.showMessageDialog(view, "Cập nhật trạng thái thành công!");
		} else {
			JOptionPane.showMessageDialog(view, "Cập nhật trạng thái thất bại!");
		}
	}

	private void xoaTrangThai() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa trên bảng!");
			return;
		}

		String maGhe = view.getTable().getValueAt(row, 0).toString();
		String maSC = view.getTable().getValueAt(row, 1).toString();
		
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteTrangThai(maGhe, maSC)) {
				view.updateTable(dao.getAllTrangThai());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa trạng thái thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa trạng thái thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaGhe().setText("");
		view.getTxtMaSuatChieu().setText("");
		view.getTxtTrangThai().setText("");
		
		view.getTxtMaGhe().requestFocus();
	}
}
