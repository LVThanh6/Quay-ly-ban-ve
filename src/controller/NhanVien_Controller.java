package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dao.NhanVien_DAO;
import model.NhanVien;
import view.FrmNhanVien;

public class NhanVien_Controller implements ActionListener {

	private FrmNhanVien view;
	private NhanVien_DAO dao;

	public NhanVien_Controller(FrmNhanVien view) {
		this.view = view;
		this.dao = new NhanVien_DAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themNhanVien();
		} else if (o.equals(view.getBtnSua())) {
			suaNhanVien();
		} else if (o.equals(view.getBtnXoa())) {
			xoaNhanVien();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themNhanVien() {
		String maNV = view.getTxtMaNhanVien().getText().trim();
		String hoTen = view.getTxtHoTen().getText().trim();
		String sdt = view.getTxtSDT().getText().trim();
		String matKhau = view.getTxtMatKhau().getText().trim();
		String luongCoBanStr = view.getTxtLuongCoBan().getText().trim();
		String vaiTro = view.getTxtVaiTro().getText().trim();

		if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || matKhau.isEmpty() || luongCoBanStr.isEmpty() || vaiTro.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double luongCoBan = Double.parseDouble(luongCoBanStr);
			model.ChucVu chucVuEnum = model.ChucVu.NHAN_VIEN;
			try {
				chucVuEnum = model.ChucVu.valueOf(vaiTro.toUpperCase());
			} catch(Exception ex) {}
			NhanVien nv = new NhanVien(maNV, hoTen, matKhau, sdt, luongCoBan, chucVuEnum);
			
			if (dao.addNhanVien(nv)) {
				view.updateTable(dao.getAllNhanVien());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm nhân viên thất bại (Mã nhân viên có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Lương cơ bản phải là số!");
		}
	}

	private void suaNhanVien() {
		String maNV = view.getTxtMaNhanVien().getText().trim();
		String hoTen = view.getTxtHoTen().getText().trim();
		String sdt = view.getTxtSDT().getText().trim();
		String matKhau = view.getTxtMatKhau().getText().trim();
		String luongCoBanStr = view.getTxtLuongCoBan().getText().trim();
		String vaiTro = view.getTxtVaiTro().getText().trim();

		if (maNV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || matKhau.isEmpty() || luongCoBanStr.isEmpty() || vaiTro.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double luongCoBan = Double.parseDouble(luongCoBanStr);
			model.ChucVu chucVuEnum = model.ChucVu.NHAN_VIEN;
			try {
				chucVuEnum = model.ChucVu.valueOf(vaiTro.toUpperCase());
			} catch(Exception ex) {}
			NhanVien nv = new NhanVien(maNV, hoTen, matKhau, sdt, luongCoBan, chucVuEnum);
			
			if (dao.updateNhanVien(nv)) {
				view.updateTable(dao.getAllNhanVien());
				JOptionPane.showMessageDialog(view, "Cập nhật nhân viên thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật nhân viên thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Lương cơ bản phải là số!");
		}
	}

	private void xoaNhanVien() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên cần xóa trên bảng!");
			return;
		}

		String maNV = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteNhanVien(maNV)) {
				view.updateTable(dao.getAllNhanVien());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa nhân viên thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa nhân viên thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaNhanVien().setText("");
		view.getTxtHoTen().setText("");
		view.getTxtMatKhau().setText("");
		view.getTxtSDT().setText("");
		view.getTxtLuongCoBan().setText("");
		view.getTxtVaiTro().setText("");
		
		view.getTxtMaNhanVien().requestFocus();
	}
}
