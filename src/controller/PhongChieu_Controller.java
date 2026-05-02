package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import dao.PhongChieu_DAO;
import dao.Ghe_DAO;
import model.PhongChieu;
import model.Ghe;
import view.FrmPhongChieu;

public class PhongChieu_Controller implements ActionListener {

	private FrmPhongChieu view;
	private PhongChieu_DAO dao;
	private Ghe_DAO gheDAO;

	public PhongChieu_Controller(FrmPhongChieu view) {
		this.view = view;
		this.dao = new PhongChieu_DAO();
		this.gheDAO = new Ghe_DAO();
		this.view.updateTable(this.dao.getAllPhongChieu());
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
		} else if (o.equals(view.getBtnAutoGenerate())) {
			generateSeats();
		}
	}

	public void loadSeatMap(String maPC) {
		ArrayList<Ghe> dsGhe = gheDAO.getGheByPhongChieu(maPC);
		view.displaySeatMap(dsGhe);
	}

	private void generateSeats() {
		String maPhong = view.getTxtMaPhongChieu().getText().trim();
		String rowsStr = view.getTxtSoHang().getText().trim();
		String colsInput = view.getTxtGheMoiHang().getText().trim();

		if (maPhong.isEmpty() || rowsStr.isEmpty() || colsInput.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập Mã Phòng, Số Hàng và Ghế mỗi hàng!");
			return;
		}

		try {
			int rows = Integer.parseInt(rowsStr);
			if (rows > 26) {
				JOptionPane.showMessageDialog(view, "Số hàng tối đa là 26 (A-Z)");
				return;
			}

			// Phân tích input số ghế mỗi hàng
			int[] seatsPerRow = new int[rows];
			if (colsInput.contains(",")) {
				String[] parts = colsInput.split(",");
				for (int i = 0; i < rows; i++) {
					if (i < parts.length) {
						seatsPerRow[i] = Integer.parseInt(parts[i].trim());
					} else {
						seatsPerRow[i] = Integer.parseInt(parts[parts.length - 1].trim()); // Lấy số cuối cùng nếu thiếu
					}
				}
			} else {
				int cols = Integer.parseInt(colsInput);
				for (int i = 0; i < rows; i++) seatsPerRow[i] = cols;
			}

			ArrayList<Ghe> newSeats = new ArrayList<>();
			int totalSeats = 0;
			PhongChieu pc = new PhongChieu(maPhong, 0, view.getTxtDinhDangPhong().getText());
			
			// Lấy hậu tố của mã phòng để tạo mã ghế (VD: ROOM_05 -> 5)
			String roomSuffix = maPhong.replaceAll("[^0-9]", "");
			if (roomSuffix.isEmpty()) roomSuffix = "X";

			for (int i = 0; i < rows; i++) {
				char rowChar = (char)('A' + i);
				int colsInThisRow = seatsPerRow[i];
				for (int j = 1; j <= colsInThisRow; j++) {
					// Định dạng mã ghế: A01-P5 (theo yêu cầu SQL)
					String maGhe = String.format("%s%02d-P%s", rowChar, j, roomSuffix);
					newSeats.add(new Ghe(maGhe, "Thường", String.valueOf(rowChar), j, "Trống", pc));
					totalSeats++;
				}
			}

			if (gheDAO.addMultipleGhe(newSeats)) {
				// Cập nhật lại tổng số ghế của phòng
				pc.setSoLuongGhe(totalSeats);
				dao.updatePhongChieu(pc);
				view.updateTable(dao.getAllPhongChieu());
				loadSeatMap(maPhong);
				JOptionPane.showMessageDialog(view, "Đã tạo " + totalSeats + " ghế cho phòng " + maPhong);
			} else {
				JOptionPane.showMessageDialog(view, "Tạo ghế thất bại! (Lưu ý: Bạn nên xóa sơ đồ cũ trước khi tạo mới)");
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(view, "Số hàng và số ghế phải là số (VD: 10 hoặc 8,10,10,8)!");
		}
	}

	public void updateSingleSeat(Ghe ghe) {
		if (gheDAO.updateGhe(ghe)) {
			loadSeatMap(ghe.getPhongChieu().getMaPhongChieu());
			JOptionPane.showMessageDialog(view, "Đã cập nhật ghế " + ghe.getMaGhe());
		} else {
			JOptionPane.showMessageDialog(view, "Cập nhật ghế thất bại!");
		}
	}

	public void editSeat(Ghe ghe) {
		// Phương thức này giữ lại cho tương thích nhưng giao diện mới dùng updateSingleSeat trực tiếp
	}

	private void themPhongChieu() {
		String maPhong = view.getTxtMaPhongChieu().getText().trim();
		String dinhDang = view.getTxtDinhDangPhong().getText().trim();
		if (maPhong.isEmpty() || dinhDang.isEmpty()) {
			JOptionPane.showMessageDialog(view, "Vui lòng nhập Mã Phòng và Định Dạng!");
			return;
		}
		PhongChieu pc = new PhongChieu(maPhong, 0, dinhDang);
		if (dao.addPhongChieu(pc)) {
			view.updateTable(dao.getAllPhongChieu());
			JOptionPane.showMessageDialog(view, "Thêm phòng thành công! Hãy nhập Số hàng/Ghế mỗi hàng và nhấn 'Tạo Sơ Đồ'.");
		} else {
			JOptionPane.showMessageDialog(view, "Mã phòng đã tồn tại!");
		}
	}

	private void suaPhongChieu() {
		String maPhong = view.getTxtMaPhongChieu().getText().trim();
		String soLuongGheStr = view.getTxtSoLuongGhe().getText().trim();
		String dinhDang = view.getTxtDinhDangPhong().getText().trim();
		try {
			int soLuong = Integer.parseInt(soLuongGheStr);
			PhongChieu pc = new PhongChieu(maPhong, soLuong, dinhDang);
			if (dao.updatePhongChieu(pc)) {
				view.updateTable(dao.getAllPhongChieu());
				JOptionPane.showMessageDialog(view, "Cập nhật phòng thành công!");
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ!");
		}
	}

	private void xoaPhongChieu() {
		int row = view.getTable().getSelectedRow();
		if (row >= 0) {
			String maPC = view.getTable().getValueAt(row, 0).toString();
			if (JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa phòng này và toàn bộ ghế liên quan?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				if (dao.deletePhongChieu(maPC)) {
					view.updateTable(dao.getAllPhongChieu());
					view.displaySeatMap(new ArrayList<>());
					JOptionPane.showMessageDialog(view, "Đã xóa phòng!");
				}
			}
		}
	}

	private void xoaTrang() {
		view.getTxtMaPhongChieu().setText("");
		view.getTxtSoLuongGhe().setText("0");
		view.getTxtDinhDangPhong().setText("");
		view.getTxtSoHang().setText("");
		view.getTxtGheMoiHang().setText("");
		view.displaySeatMap(new ArrayList<>());
	}
}
