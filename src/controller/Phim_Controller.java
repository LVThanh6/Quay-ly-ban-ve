package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import dao.Phim_DAO;
import model.KhachHang;
import model.Phim;
import view.FrmKhachHang;
import view.FrmPhim;

public class Phim_Controller implements ActionListener{

	private FrmPhim view;
	private Phim_DAO dao;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	public Phim_Controller(FrmPhim view) {
		this.view = view;
		this.dao = new Phim_DAO();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themPhim();
		} else if (o.equals(view.getBtnSua())) {
			suaPhim();
		} else if (o.equals(view.getBtnXoa())) {
			xoaPhim();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}
	private void themPhim() {
		String maPhim = view.getTxtMaPhim().getText();
		String tenPhim = view.getTxtTenPhim().getText();
		String loaiPhim = view.getTxtLoaiPhim().getText();
		String donViSanXuat = view.getTxtDonViSanXuat().getText();
		String ngaySanXuat = view.getTxtNgaySanXuat().getText();
		String thoiLuong = view.getTxtThoiLuong().getText();
		String gioiHanDoTuoi = view.getTxtGioiHan().getText();
		
		if()

	

		
	}

	private void suaPhim() {
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

	private void xoaPhim() {
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
		view.getTxtDonViSanXuat().setText("");
		view.getTxtGioiHan().setText("");
		view.getTxtLoaiPhim().setText("");
		view.getTxtMaPhim().setText("");
		view.getTxtNgaySanXuat().setText("");
		view.getTxtTenPhim().setText("");
		view.getTxtThoiLuong().setText("");
		
		
		view.getTxtMaPhim().requestFocus();
	}
	
	
}
