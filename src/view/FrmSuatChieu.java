package view;

import java.util.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import controller.SuatChieu_Controller;
import model.SuatChieu;

public class FrmSuatChieu extends FrmBaseManager {

    private JTextField txtmasuatchieu;
    private JTextField txtNgay;
    private JTextField txtGioBatDau;
    private JTextField txtgiave;
    private javax.swing.JComboBox<String> cbMaPhim;
    private javax.swing.JComboBox<String> cbTenPhim;
    private javax.swing.JComboBox<String> cbMaPhong;
    
    private List<model.Phim> dsPhimHienTai;
    private List<model.PhongChieu> dsPhongHienTai;
    private controller.SuatChieu_Controller controller;

    public FrmSuatChieu() {
        super("Quản lý Suất Chiếu", new String[]{"Mã SC", "Ngày", "Giờ Bắt Đầu", "Giá Vé", "Mã Phim", "Mã Phòng"});
        
        // Khởi tạo các ô nhập liệu - Chia làm 2 hàng rõ rệt (2 hàng, 4 cột)
        pnlInput.setLayout(new GridLayout(2, 4, 15, 15));
        pnlInput.setPreferredSize(new Dimension(800, 120));
        
        txtmasuatchieu = createTextField("MaSuatChieu");
        txtNgay = createTextField("Ngày (yyyy-MM-dd)");
        txtGioBatDau = createTextField("Giờ BĐ (HH:mm)");
        txtgiave = createTextField("GiaVe");
        
        cbMaPhim = new javax.swing.JComboBox<>();
        cbTenPhim = new javax.swing.JComboBox<>();
        cbMaPhim.setEditable(true);
        cbTenPhim.setEditable(true);
        
        // Link 2 combobox phim
        cbMaPhim.addActionListener(e -> syncPhimByMa());
        cbTenPhim.addActionListener(e -> syncPhimByTen());

        addControl("Mã Phim", cbMaPhim);
        addControl("Tên Phim", cbTenPhim);

        cbMaPhong = new javax.swing.JComboBox<>();
        cbMaPhong.setEditable(true);
        addControl("Mã Phòng", cbMaPhong);
        
        // Ô trống để đủ 8 ô (2x4)
        pnlInput.add(new JPanel());
        
        initTableEvent();
    }

    public void setDataLists(List<model.Phim> listPhim, List<model.PhongChieu> listPhong) {
        this.dsPhimHienTai = listPhim;
        cbMaPhim.removeAllItems();
        cbTenPhim.removeAllItems();
        for (model.Phim p : listPhim) {
            cbMaPhim.addItem(p.getMaPhim());
            cbTenPhim.addItem(p.getTenPhim());
        }

        this.dsPhongHienTai = listPhong;
        cbMaPhong.removeAllItems();
        for (model.PhongChieu pc : listPhong) {
            cbMaPhong.addItem(pc.getMaPhongChieu());
        }
    }

    public String getSelectedMaPhong() {
        return cbMaPhong.getSelectedItem() != null ? cbMaPhong.getSelectedItem().toString() : "";
    }

    private void syncPhimByMa() {
        Object sel = cbMaPhim.getSelectedItem();
        if (sel == null || dsPhimHienTai == null) return;
        for (model.Phim p : dsPhimHienTai) {
            if (p.getMaPhim().equals(sel.toString())) {
                cbTenPhim.setSelectedItem(p.getTenPhim());
                break;
            }
        }
    }

    private void syncPhimByTen() {
        Object sel = cbTenPhim.getSelectedItem();
        if (sel == null || dsPhimHienTai == null) return;
        for (model.Phim p : dsPhimHienTai) {
            if (p.getTenPhim().equals(sel.toString())) {
                cbMaPhim.setSelectedItem(p.getMaPhim());
                break;
            }
        }
    }

    public String getSelectedMaPhim() {
        return cbMaPhim.getSelectedItem() != null ? cbMaPhim.getSelectedItem().toString() : "";
    }
    
    public void setController(SuatChieu_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaSuatChieu() { return txtmasuatchieu; }
    public JTextField getTxtNgay() { return txtNgay; }
    public JTextField getTxtGioBatDau() { return txtGioBatDau; }
    public JTextField getTxtGiaVe() { return txtgiave; }
    public javax.swing.JComboBox<String> getCbMaPhim() { return cbMaPhim; }
    public javax.swing.JComboBox<String> getCbTenPhim() { return cbTenPhim; }
    public javax.swing.JComboBox<String> getCbMaPhong() { return cbMaPhong; }

    public void clearPhimSelection() {
        cbMaPhim.setSelectedItem("");
        cbTenPhim.setSelectedItem("");
    }

    public void clearPhongSelection() {
        cbMaPhong.setSelectedItem("");
    }

    private void initTableEvent() {
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtmasuatchieu.setText(tableModel.getValueAt(row, 0).toString());
                txtNgay.setText(tableModel.getValueAt(row, 1).toString());
                txtGioBatDau.setText(tableModel.getValueAt(row, 2).toString());
                txtgiave.setText(tableModel.getValueAt(row, 3).toString().replace(",", ""));
                cbMaPhim.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                cbMaPhong.setSelectedItem(tableModel.getValueAt(row, 5).toString());
            }
        });
    }

    public void updateTable(List<SuatChieu> list) {
        tableModel.setRowCount(0);
        for (SuatChieu item : list) {
            String ngay = "";
            String gio = "";
            if (item.getThoiGianKhoiChieu() != null) {
                String full = item.getThoiGianKhoiChieu().toString().replace("T", " ");
                String[] parts = full.split(" ");
                ngay = parts[0];
                if (parts.length > 1) gio = parts[1].substring(0, 5); // HH:mm
            }
            Object[] row = new Object[] {
                item.getMaSuatChieu(),
                ngay,
                gio,
                String.format("%,.0f", item.getGiaVeCoBan()),
                item.getPhim() != null ? item.getPhim().getMaPhim() : "",
                item.getPhongChieu() != null ? item.getPhongChieu().getMaPhongChieu() : ""
            };
            tableModel.addRow(row);
        }
    }
}
