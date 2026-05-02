package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.ComboBapNuoc_DAO;
import model.ComboBapNuoc;
import view.FrmComboBapNuoc;

public class ComboBapNuoc_Controller implements ActionListener {

	private FrmComboBapNuoc view;
	private ComboBapNuoc_DAO dao;

	public ComboBapNuoc_Controller(FrmComboBapNuoc view) {
		this.view = view;
		this.dao = new ComboBapNuoc_DAO();
		this.view.updateTable(this.dao.getAllCombo());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(view.getBtnThem())) {
			themCombo();
		} else if (o.equals(view.getBtnSua())) {
			suaCombo();
		} else if (o.equals(view.getBtnXoa())) {
			xoaCombo();
		} else if (o.equals(view.getBtnXoaTrang())) {
			xoaTrang();
		}
	}

	private void themCombo() {
		String maCombo = view.getTxtMaCombo().getText().trim();
		String tenCombo = view.getTxtTenCombo().getText().trim();
		String giaBanStr = view.getTxtGiaBanCoBan().getText().trim();

		if (maCombo.isEmpty() || tenCombo.isEmpty() || giaBanStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double giaBan = Double.parseDouble(giaBanStr);
			ComboBapNuoc cb = new ComboBapNuoc(maCombo, tenCombo, giaBan);
			
			if (dao.addCombo(cb)) {
				view.updateTable(dao.getAllCombo());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Thêm combo bắp nước thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Thêm combo bắp nước thất bại (Mã combo có thể đã tồn tại)!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá bán phải là số!");
		}
	}

	private void suaCombo() {
		String maCombo = view.getTxtMaCombo().getText().trim();
		String tenCombo = view.getTxtTenCombo().getText().trim();
		String giaBanStr = view.getTxtGiaBanCoBan().getText().trim();

		if (maCombo.isEmpty() || tenCombo.isEmpty() || giaBanStr.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!");
			return;
		}

		try {
			double giaBan = Double.parseDouble(giaBanStr);
			ComboBapNuoc cb = new ComboBapNuoc(maCombo, tenCombo, giaBan);
			
			if (dao.updateCombo(cb)) {
				view.updateTable(dao.getAllCombo());
				JOptionPane.showMessageDialog(view, "Cập nhật combo bắp nước thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Cập nhật combo bắp nước thất bại!");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view, "Giá bán phải là số!");
		}
	}

	private void xoaCombo() {
		int row = view.getTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(view, "Vui lòng chọn combo cần xóa trên bảng!");
			return;
		}

		String maCombo = view.getTable().getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa combo này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			if (dao.deleteCombo(maCombo)) {
				view.updateTable(dao.getAllCombo());
				xoaTrang();
				JOptionPane.showMessageDialog(view, "Xóa combo bắp nước thành công!");
			} else {
				JOptionPane.showMessageDialog(view, "Xóa combo bắp nước thất bại!");
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaCombo().setText("");
		view.getTxtTenCombo().setText("");
		view.getTxtGiaBanCoBan().setText("");
		
		view.getTxtMaCombo().requestFocus();
	}
}
