package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.NhanVienController;
import dao.Phim_DAO;
import model.Phim;

public class FrmQuanLyPhim extends JFrame {

    private NhanVienController controller;
    private Phim_DAO phimDAO;

    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JTextField txtMaPhim, txtTenPhim, txtLoaiPhim, txtDonViSX, txtGioiHanTuoi, txtThoiLuong, txtNgaySX;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong;

    public FrmQuanLyPhim(NhanVienController controller) {
        this.controller = controller;
        this.phimDAO = new Phim_DAO();
        setTitle("Quản lý Danh Mục Phim");

        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadData();
    }

    private void initUI() {
        // North: Input Panel
        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin phim"));

        pnlInput.add(new JLabel("Mã phim:"));
        txtMaPhim = new JTextField();
        pnlInput.add(txtMaPhim);

        pnlInput.add(new JLabel("Tên phim:"));
        txtTenPhim = new JTextField();
        pnlInput.add(txtTenPhim);

        pnlInput.add(new JLabel("Loại phim:"));
        txtLoaiPhim = new JTextField();
        pnlInput.add(txtLoaiPhim);

        pnlInput.add(new JLabel("Đơn vị sản xuất:"));
        txtDonViSX = new JTextField();
        pnlInput.add(txtDonViSX);

        pnlInput.add(new JLabel("Giới hạn độ tuổi:"));
        txtGioiHanTuoi = new JTextField();
        pnlInput.add(txtGioiHanTuoi);

        pnlInput.add(new JLabel("Thời lượng (phút):"));
        txtThoiLuong = new JTextField();
        pnlInput.add(txtThoiLuong);

        pnlInput.add(new JLabel("Ngày sản xuất (yyyy-mm-dd):"));
        txtNgaySX = new JTextField();
        pnlInput.add(txtNgaySX);

        add(pnlInput, BorderLayout.NORTH);

        // Center: Table
        String[] columns = { "Mã phim", "Tên phim - Loại", "Ngày sản xuất", "Thời lượng", "Đơn vị sản xuất", "Giới hạn tuổi" };
        modelPhim = new DefaultTableModel(columns, 0);
        tblPhim = new JTable(modelPhim);
        add(new JScrollPane(tblPhim), BorderLayout.CENTER);

        // South: Buttons
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Cập nhật");
        btnXoa = new JButton("Xóa");
        btnXoaRong = new JButton("Làm mới");

        pnlSouth.add(btnThem);
        pnlSouth.add(btnSua);
        pnlSouth.add(btnXoa);
        pnlSouth.add(btnXoaRong);
        add(pnlSouth, BorderLayout.SOUTH);

        // Event handling
        btnThem.addActionListener(e -> themPhim());
        btnSua.addActionListener(e -> suaPhim());
        btnXoa.addActionListener(e -> xoaPhim());
        btnXoaRong.addActionListener(e -> xoaTrang());
    }

    public void setReadOnly(boolean readOnly) {
        btnThem.setVisible(!readOnly);
        btnSua.setVisible(!readOnly);
        btnXoa.setVisible(!readOnly);
    }

    private void themPhim() {
        try {
            Phim p = collectPhim();
            if (phimDAO.addPhim(p)) {
                JOptionPane.showMessageDialog(this, "Thêm phim thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Mã phim đã tồn tại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ: " + e.getMessage());
        }
    }

    private void suaPhim() {
        try {
            Phim p = collectPhim();
            if (phimDAO.updatePhim(p)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ: " + e.getMessage());
        }
    }

    private void xoaPhim() {
        int row = tblPhim.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim cần xóa!");
            return;
        }
        String ma = tblPhim.getValueAt(row, 0).toString();
        if (phimDAO.deletePhim(ma)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!");
            loadData();
        }
    }

    private Phim collectPhim() {
        String ma = txtMaPhim.getText();
        String ten = txtTenPhim.getText();
        String loai = txtLoaiPhim.getText();
        String dv = txtDonViSX.getText();
        int tuoi = Integer.parseInt(txtGioiHanTuoi.getText());
        int thoiLuong = Integer.parseInt(txtThoiLuong.getText());
        Date ngay = Date.valueOf(txtNgaySX.getText());
        return new Phim(ma, ten, ngay, dv, tuoi, thoiLuong, loai);
    }

    private void xoaTrang() {
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtLoaiPhim.setText("");
        txtDonViSX.setText("");
        txtGioiHanTuoi.setText("");
        txtThoiLuong.setText("");
        txtNgaySX.setText("");
        txtMaPhim.setEditable(true);
        tblPhim.clearSelection();
    }

    private void loadData() {
        try {
            List<Phim> dsPhim = controller.xemLichChieu();
            modelPhim.setRowCount(0);
            if (dsPhim != null) {
                for (Phim p : dsPhim) {
                    modelPhim.addRow(new Object[] {
                            p.getMaPhim(), p.getTenPhim() + " - " + p.getLoaiPhim(), p.getNgaySanXuat(),
                            p.getThoiLuongPhim(), p.getDonViSanXuat(), p.getGioiHan()
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Cannot load Phim data: " + e.getMessage());
        }
    }
}
