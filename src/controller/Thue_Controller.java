package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.Thue_DAO;
import model.Thue;
import view.FrmThue;

public class Thue_Controller implements ActionListener {

	private FrmThue view;
	private Thue_DAO dao;

	public Thue_Controller(FrmThue view) {
		this.view = view;
		this.dao = new Thue_DAO();
		this.view.updateTable(this.dao.getAllThue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themThue();
		} else if (o.equals(view.getBtnSua())) {
			suaThue();
		} else if (o.equals(view.getBtnXoa())) {
			xoaThue();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themThue() {
		String maThue = view.getTxtMaThue().getText().trim();
		String tenThue = view.getTxtTenThue().getText().trim();
		String mucThueStr = view.getTxtMucThue().getText().trim();

		if (maThue.isEmpty() || tenThue.isEmpty() || mucThueStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double mucThue = Double.parseDouble(mucThueStr);
			Thue t = new Thue(maThue, tenThue, mucThue);
			
			if (dao.addThue(t)) {
				view.updateTable(dao.getAllThue());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm thuế thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm thuế thất bại (Mã Thuế có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Mức thuế phải là số!");
		}
	}

	private void suaThue() {
		String maThue = view.getTxtMaThue().getText().trim();
		String tenThue = view.getTxtTenThue().getText().trim();
		String mucThueStr = view.getTxtMucThue().getText().trim();

		if (maThue.isEmpty() || tenThue.isEmpty() || mucThueStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double mucThue = Double.parseDouble(mucThueStr);
			Thue t = new Thue(maThue, tenThue, mucThue);
			
			if (dao.updateThue(t)) {
				view.updateTable(dao.getAllThue());
				JOptionPane.showMessageDialog(view, "Cập nhật thuế thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật thuế thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Mức thuế phải là số!");
		}
	}

	private void xoaThue() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa trên bảng!");
			return;
		}

		String maThue = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteThue(maThue)) {
				view.updateTable(dao.getAllThue());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa thuế thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa thuế thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaThue().setText("");
		view.getTxtTenThue().setText("");
		view.getTxtMucThue().setText("");
		
		view.getTxtMaThue().requestFocus();
	}
}
