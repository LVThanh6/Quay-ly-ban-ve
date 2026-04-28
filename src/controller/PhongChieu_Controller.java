package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.PhongChieu_DAO;
import model.PhongChieu;
import view.FrmPhongChieu;

public class PhongChieu_Controller implements ActionListener {

	private FrmPhongChieu view;
	private PhongChieu_DAO dao;

	public PhongChieu_Controller(FrmPhongChieu view) {
		this.view = view;
		this.dao = new PhongChieu_DAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themPhongChieu();
		} else if (o.equals(view.getBtnSua())) {
			suaPhongChieu();
		} else if (o.equals(view.getBtnXoa())) {
			xoaPhongChieu();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themPhongChieu() {
		String maPhong = view.getTxtMaPhongChieu().getText().trim();
		String soLuongGheStr = view.getTxtSoLuongGhe().getText().trim();
		String dinhDang = view.getTxtDinhDangPhong().getText().trim();

		if (maPhong.isEmpty() || soLuongGheStr.isEmpty() || dinhDang.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			int soLuongGhe = Integer.parseInt(soLuongGheStr);
			PhongChieu pc = new PhongChieu(maPhong, soLuongGhe, dinhDang);
			
			if (dao.addPhongChieu(pc)) {
				view.updateTable(dao.getAllPhongChieu());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm phòng chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm phòng chiếu thất bại (Mã phòng chiếu có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Số lượng ghế phải là số!");
		}
	}

	private void suaPhongChieu() {
		String maPhong = view.getTxtMaPhongChieu().getText().trim();
		String soLuongGheStr = view.getTxtSoLuongGhe().getText().trim();
		String dinhDang = view.getTxtDinhDangPhong().getText().trim();

		if (maPhong.isEmpty() || soLuongGheStr.isEmpty() || dinhDang.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			int soLuongGhe = Integer.parseInt(soLuongGheStr);
			PhongChieu pc = new PhongChieu(maPhong, soLuongGhe, dinhDang);
			
			if (dao.updatePhongChieu(pc)) {
				view.updateTable(dao.getAllPhongChieu());
				JOptionPane.showMessageDialog(view, "Cập nhật phòng chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật phòng chiếu thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Số lượng ghế phải là số!");
		}
	}

	private void xoaPhongChieu() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng chiếu cần xóa trên bảng!");
			return;
		}

		String maPhong = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa phòng chiếu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deletePhongChieu(maPhong)) {
				view.updateTable(dao.getAllPhongChieu());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa phòng chiếu thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa phòng chiếu thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaPhongChieu().setText("");
		view.getTxtSoLuongGhe().setText("");
		view.getTxtDinhDangPhong().setText("");
		
		view.getTxtMaPhongChieu().requestFocus();
	}
}
