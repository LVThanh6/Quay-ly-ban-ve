package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.KhuyenMai_DAO;
import model.KhuyenMai;
import view.FrmKhuyenMai;

public class KhuyenMai_Controller implements ActionListener {

	private FrmKhuyenMai view;
	private KhuyenMai_DAO dao;

	public KhuyenMai_Controller(FrmKhuyenMai view) {
		this.view = view;
		this.dao = new KhuyenMai_DAO();
		this.view.updateTable(this.dao.getAllKhuyenMai());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themKhuyenMai();
		} else if (o.equals(view.getBtnSua())) {
			suaKhuyenMai();
		} else if (o.equals(view.getBtnXoa())) {
			xoaKhuyenMai();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themKhuyenMai() {
		String maKM = view.getTxtMaKhuyenMai().getText().trim();
		String tenKM = view.getTxtTenKhuyenMai().getText().trim();
		String hinhThucGiamStr = view.getTxtHinhThucGiam().getText().trim();
		String ttttStr = view.getTxtTongTienToiThieu().getText().trim();

		if (maKM.isEmpty() || tenKM.isEmpty() || hinhThucGiamStr.isEmpty() || ttttStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double hinhThucGiam = Double.parseDouble(hinhThucGiamStr);
			double tttt = Double.parseDouble(ttttStr);
			
			if (hinhThucGiam < 0 || tttt < 0) {
				JOptionPane.showMessageDialog(view, "Mức giảm và tổng tiền tối thiểu không được âm!");
				return;
			}
			KhuyenMai km = new KhuyenMai(maKM, tenKM, hinhThucGiam, tttt);
			
			if (dao.addKhuyenMai(km)) {
				view.updateTable(dao.getAllKhuyenMai());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm khuyến mãi thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm khuyến mãi thất bại (Mã KM có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Mức giảm phải là số!");
		}
	}

	private void suaKhuyenMai() {
		String maKM = view.getTxtMaKhuyenMai().getText().trim();
		String tenKM = view.getTxtTenKhuyenMai().getText().trim();
		String hinhThucGiamStr = view.getTxtHinhThucGiam().getText().trim();
		String ttttStr = view.getTxtTongTienToiThieu().getText().trim();

		if (maKM.isEmpty() || tenKM.isEmpty() || hinhThucGiamStr.isEmpty() || ttttStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double hinhThucGiam = Double.parseDouble(hinhThucGiamStr);
			double tttt = Double.parseDouble(ttttStr);
			
			if (hinhThucGiam < 0 || tttt < 0) {
				JOptionPane.showMessageDialog(view, "Mức giảm và tổng tiền tối thiểu không được âm!");
				return;
			}
			KhuyenMai km = new KhuyenMai(maKM, tenKM, hinhThucGiam, tttt);
			
			if (dao.updateKhuyenMai(km)) {
				view.updateTable(dao.getAllKhuyenMai());
				JOptionPane.showMessageDialog(view, "Cập nhật khuyến mãi thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật khuyến mãi thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Mức giảm phải là số!");
		}
	}

	private void xoaKhuyenMai() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn khuyến mãi cần xóa trên bảng!");
			return;
		}

		String maKM = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa khuyến mãi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteKhuyenMai(maKM)) {
				view.updateTable(dao.getAllKhuyenMai());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa khuyến mãi thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa khuyến mãi thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaKhuyenMai().setText("");
		view.getTxtTenKhuyenMai().setText("");
		view.getTxtHinhThucGiam().setText("");
		view.getTxtTongTienToiThieu().setText("");
		
		view.getTxtMaKhuyenMai().requestFocus();
	}
}
