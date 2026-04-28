package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.Ghe_DAO;
import model.Ghe;
import model.PhongChieu;
import view.FrmGhe;

public class Ghe_Controller implements ActionListener {

	private FrmGhe view;
	private Ghe_DAO dao;

	public Ghe_Controller(FrmGhe view) {
		this.view = view;
		this.dao = new Ghe_DAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themGhe();
		} else if (o.equals(view.getBtnSua())) {
			suaGhe();
		} else if (o.equals(view.getBtnXoa())) {
			xoaGhe();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themGhe() {
		String maGhe = view.getTxtMaGhe().getText().trim();
		String loaiGhe = view.getTxtLoaiGhe().getText().trim();
		String maPhongChieu = view.getTxtMaPhongChieu().getText().trim();

		if (maGhe.isEmpty() || loaiGhe.isEmpty() || maPhongChieu.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		PhongChieu pc = new PhongChieu(maPhongChieu, 0, "");
		Ghe ghe = new Ghe(maGhe, loaiGhe, pc);
		
		if (dao.addGhe(ghe)) {
			view.updateTable(dao.getAllGhe());
			xoaTrang();
			JOptionPane.showMessageDialog(view, "Thêm ghế thành công!");
		} else {
			JOptionPane.showMessageDialog(view, "Thêm ghế thất bại (Mã ghế có thể đã tồn tại)!");
		}
	}

	private void suaGhe() {
		String maGhe = view.getTxtMaGhe().getText().trim();
		String loaiGhe = view.getTxtLoaiGhe().getText().trim();
		String maPhongChieu = view.getTxtMaPhongChieu().getText().trim();

		if (maGhe.isEmpty() || loaiGhe.isEmpty() || maPhongChieu.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		PhongChieu pc = new PhongChieu(maPhongChieu, 0, "");
		Ghe ghe = new Ghe(maGhe, loaiGhe, pc);
		
		if (dao.updateGhe(ghe)) {
			view.updateTable(dao.getAllGhe());
			JOptionPane.showMessageDialog(view, "Cập nhật ghế thành công!");
		} else {
			JOptionPane.showMessageDialog(view, "Cập nhật ghế thất bại!");
		}
	}

	private void xoaGhe() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn ghế cần xóa trên bảng!");
			return;
		}

		String maGhe = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa ghế này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteGhe(maGhe)) {
				view.updateTable(dao.getAllGhe());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa ghế thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa ghế thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaGhe().setText("");
		view.getTxtLoaiGhe().setText("");
		view.getTxtMaPhongChieu().setText("");
		
		view.getTxtMaGhe().requestFocus();
	}
}
