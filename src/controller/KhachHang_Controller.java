package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import dao.KhachHang_DAO;
import model.KhachHang;
import view.FrmKhachHang;

public class KhachHang_Controller implements ActionListener {
	private FrmKhachHang view;
	private KhachHang_DAO dao;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public KhachHang_Controller(FrmKhachHang view) {
		this.view = view;
		this.dao = new KhachHang_DAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themKhachHang();
		} else if (o.equals(view.getBtnSua())) {
			suaKhachHang();
		} else if (o.equals(view.getBtnXoa())) {
			xoaKhachHang();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themKhachHang() {
		String sdt = view.getTxtSDT().getText();
		String hoTen = view.getTxtHoTen().getText();
		String ngaySinhStr = view.getTxtNgaySinh().getText();

		if (sdt.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			Date ngaySinh = df.parse(ngaySinhStr);
			KhachHang kh = new KhachHang(sdt, hoTen, ngaySinh);
			if (dao.addKhachHang(kh)) {
				view.updateTable(dao.getAllKhachHang());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm khách hàng thất bại (có thể trùng số điện thoại)!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Ngày sinh không hợp lệ (dd/MM/yyyy)!");
		}
	}

	private void suaKhachHang() {
		String sdt = view.getTxtSDT().getText();
		String hoTen = view.getTxtHoTen().getText();
		String ngaySinhStr = view.getTxtNgaySinh().getText();

		if (sdt.isEmpty() || hoTen.isEmpty() || ngaySinhStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			Date ngaySinh = df.parse(ngaySinhStr);
			KhachHang kh = new KhachHang(sdt, hoTen, ngaySinh);
			if (dao.updateKhachHang(kh)) {
				view.updateTable(dao.getAllKhachHang());
				JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật khách hàng thất bại!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Ngày sinh không hợp lệ (dd/MM/yyyy)!");
		}
	}

	private void xoaKhachHang() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng cần xóa!");
			return;
		}

		String sdt = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteKhachHang(sdt)) {
				view.updateTable(dao.getAllKhachHang());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa khách hàng thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtSDT().setText("");
		view.getTxtHoTen().setText("");
		view.getTxtNgaySinh().setText("");
		view.getTxtSDT().requestFocus();
	}
}
