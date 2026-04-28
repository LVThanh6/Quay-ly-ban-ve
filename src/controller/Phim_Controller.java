package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.swing.JOptionPane;

import dao.Phim_DAO;
import model.Phim;
import view.FrmPhim;

public class Phim_Controller implements ActionListener {

	private FrmPhim view;
	private Phim_DAO dao;

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
		String maPhim = view.getTxtMaPhim().getText().trim();
		String tenPhim = view.getTxtTenPhim().getText().trim();
		String ngaySanXuatStr = view.getTxtNgaySanXuat().getText().trim();
		String donViSanXuat = view.getTxtDonViSanXuat().getText().trim();
		String gioiHanStr = view.getTxtGioiHan().getText().trim();
		String thoiLuongStr = view.getTxtThoiLuong().getText().trim();
		String loaiPhim = view.getTxtLoaiPhim().getText().trim();

		if (maPhim.isEmpty() || tenPhim.isEmpty() || ngaySanXuatStr.isEmpty() || 
			donViSanXuat.isEmpty() || gioiHanStr.isEmpty() || thoiLuongStr.isEmpty() || loaiPhim.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin phim!");
			return;
		}

		try {
			Date ngaySanXuat = Date.valueOf(ngaySanXuatStr); // Định dạng phải là yyyy-MM-dd
			int gioiHan = Integer.parseInt(gioiHanStr);
			int thoiLuong = Integer.parseInt(thoiLuongStr);
			
			Phim phim = new Phim(maPhim, tenPhim, ngaySanXuat, donViSanXuat, gioiHan, thoiLuong, loaiPhim);
			
			if (dao.addPhim(phim)) {
				view.updateTable(dao.getAllPhim());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm phim thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm phim thất bại (Mã phim có thể đã tồn tại)!");
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(view, "Ngày sản xuất không hợp lệ (yyyy-MM-dd) hoặc dữ liệu số không đúng!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Lỗi định dạng dữ liệu: " + e.getMessage());
		}
	}

	private void suaPhim() {
		String maPhim = view.getTxtMaPhim().getText().trim();
		String tenPhim = view.getTxtTenPhim().getText().trim();
		String ngaySanXuatStr = view.getTxtNgaySanXuat().getText().trim();
		String donViSanXuat = view.getTxtDonViSanXuat().getText().trim();
		String gioiHanStr = view.getTxtGioiHan().getText().trim();
		String thoiLuongStr = view.getTxtThoiLuong().getText().trim();
		String loaiPhim = view.getTxtLoaiPhim().getText().trim();

		if (maPhim.isEmpty() || tenPhim.isEmpty() || ngaySanXuatStr.isEmpty() || 
			donViSanXuat.isEmpty() || gioiHanStr.isEmpty() || thoiLuongStr.isEmpty() || loaiPhim.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin phim!");
			return;
		}

		try {
			Date ngaySanXuat = Date.valueOf(ngaySanXuatStr); // Định dạng phải là yyyy-MM-dd
			int gioiHan = Integer.parseInt(gioiHanStr);
			int thoiLuong = Integer.parseInt(thoiLuongStr);
			
			Phim phim = new Phim(maPhim, tenPhim, ngaySanXuat, donViSanXuat, gioiHan, thoiLuong, loaiPhim);
			
			if (dao.updatePhim(phim)) {
				view.updateTable(dao.getAllPhim());
				JOptionPane.showMessageDialog(view, "Cập nhật phim thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật phim thất bại (Không tìm thấy mã phim)!");
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(view, "Ngày sản xuất không hợp lệ (yyyy-MM-dd) hoặc dữ liệu số không đúng!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view, "Lỗi định dạng dữ liệu: " + e.getMessage());
		}
	}

	private void xoaPhim() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn phim cần xóa trên bảng!");
			return;
		}

		String maPhim = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa phim này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deletePhim(maPhim)) {
				view.updateTable(dao.getAllPhim());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa phim thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa phim thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaPhim().setText("");
		view.getTxtTenPhim().setText("");
		view.getTxtNgaySanXuat().setText("");
		view.getTxtDonViSanXuat().setText("");
		view.getTxtGioiHan().setText("");
		view.getTxtThoiLuong().setText("");
		view.getTxtLoaiPhim().setText("");
		
		view.getTxtMaPhim().requestFocus();
	}
}
