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

import controller.QuanLyController;
import dao.Phim_DAO;
import model.Phim;

public class FrmQuanLyPhim extends JFrame {

    private QuanLyController controller;
    private Phim_DAO phimDAO;

    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JTextField txtMaPhim, txtTenPhim, txtLoaiPhim, txtDonViSX, txtGioiHanTuoi, txtThoiLuong, txtNgaySX;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong;

    public FrmQuanLyPhim(QuanLyController controller) {
        this.controller = controller;
        this.phimDAO = new Phim_DAO();
        setTitle("Quản lý Danh Mục Phim");

        // Cấu hình cơ bản (Chỉ dùng khi test độc lập, khi nhúng vào MainGUI thì không
        // dùng đến JFrame này)
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadData();
    }

    private void initUI() {
        // Header Panel
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(true);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Danh mục Phim");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(30, 30, 30));
        pnlHeader.add(lblTitle, BorderLayout.WEST);

        // Input Panel
        JPanel pnlInputWrapper = new JPanel(new BorderLayout());
        pnlInputWrapper.setOpaque(true);
        pnlInputWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 15, 15));
        pnlInput.setOpaque(true);

        txtMaPhim = createTextField("Mã Phim", pnlInput);
        txtTenPhim = createTextField("Tên Phim", pnlInput);
        txtLoaiPhim = createTextField("Loại Phim", pnlInput);
        txtDonViSX = createTextField("Đơn Vị Sản Xuất", pnlInput);
        txtGioiHanTuoi = createTextField("Giới Hạn Tuổi", pnlInput);
        txtThoiLuong = createTextField("Thời Lượng (phút)", pnlInput);
        txtNgaySX = createTextField("Ngày SX (yyyy-mm-dd)", pnlInput);

        pnlInputWrapper.add(pnlInput, BorderLayout.CENTER);

        // Nút bấm
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pnlButtons.setOpaque(true);

        btnThem = createButton("Thêm Phim", new Color(229, 9, 20)); // Red Netflix
        btnSua = createButton("Cập Nhật", new Color(70, 130, 180));
        btnXoa = createButton("Xóa", new Color(180, 60, 60));
        btnXoaRong = createButton("Làm Mới", new Color(100, 100, 100));

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnXoaRong);

        pnlInputWrapper.add(pnlButtons, BorderLayout.SOUTH);

        // Gộp Top
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setOpaque(true);
        pnlTop.add(pnlHeader, BorderLayout.NORTH);
        pnlTop.add(pnlInputWrapper, BorderLayout.CENTER);
        add(pnlTop, BorderLayout.NORTH);

        // Table Panel
        JPanel pnlTableWrapper = new JPanel(new BorderLayout());
        pnlTableWrapper.setOpaque(true);
        pnlTableWrapper.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        String[] cols = { "Mã Phim", "Tên Phim & Thể Loại", "Ngày Khởi Chiếu", "Thời Lượng", "Đơn Vị SX",
                "Giới Hạn Tuổi" };
        modelPhim = new DefaultTableModel(cols, 0);
        tblPhim = new JTable(modelPhim);
        tblPhim.setRowHeight(40);
        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPhim.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblPhim.setShowVerticalLines(false);

        JScrollPane scroll = new JScrollPane(tblPhim);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        pnlTableWrapper.add(scroll, BorderLayout.CENTER);

        add(pnlTableWrapper, BorderLayout.CENTER);

        // Sự kiện
        setupEvents();
    }

    private JTextField createTextField(String placeholder, JPanel parent) {
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setOpaque(true);
        JLabel lbl = new JLabel(placeholder);
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(150, 35));

        pnlWrapper.add(lbl, BorderLayout.NORTH);
        pnlWrapper.add(txt, BorderLayout.CENTER);
        parent.add(pnlWrapper);
        return txt;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void setupEvents() {
        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            int row = tblPhim.getSelectedRow();
            if (row >= 0) {
                txtMaPhim.setText(modelPhim.getValueAt(row, 0).toString());
                String[] tenLoai = modelPhim.getValueAt(row, 1).toString().split(" - ");
                txtTenPhim.setText(tenLoai[0]);
                if (tenLoai.length > 1)
                    txtLoaiPhim.setText(tenLoai[1]);
                txtNgaySX.setText(modelPhim.getValueAt(row, 2).toString());
                txtThoiLuong.setText(modelPhim.getValueAt(row, 3).toString());
                txtDonViSX.setText(modelPhim.getValueAt(row, 4).toString());
                txtGioiHanTuoi.setText(modelPhim.getValueAt(row, 5).toString());
                txtMaPhim.setEditable(false);
            }
        });

        btnXoaRong.addActionListener(e -> xoaRong());

        btnThem.addActionListener(e -> {
            try {
                Phim p = new Phim(txtMaPhim.getText(), txtTenPhim.getText(), Date.valueOf(txtNgaySX.getText()),
                        txtDonViSX.getText(), Integer.parseInt(txtGioiHanTuoi.getText()),
                        Integer.parseInt(txtThoiLuong.getText()), txtLoaiPhim.getText());
                if (controller.themPhim(p)) {
                    JOptionPane.showMessageDialog(this, "Thêm phim thành công!");
                    loadData();
                    xoaRong();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm phim thất bại! Trùng mã hoặc lỗi DB.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu đầu vào: " + ex.getMessage());
            }
        });

        btnSua.addActionListener(e -> {
            try {
                Phim p = new Phim(txtMaPhim.getText(), txtTenPhim.getText(), Date.valueOf(txtNgaySX.getText()),
                        txtDonViSX.getText(), Integer.parseInt(txtGioiHanTuoi.getText()),
                        Integer.parseInt(txtThoiLuong.getText()), txtLoaiPhim.getText());
                if (phimDAO.updatePhim(p)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu đầu vào: " + ex.getMessage());
            }
        });

        btnXoa.addActionListener(e -> {
            if (phimDAO.deletePhim(txtMaPhim.getText())) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadData();
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });
    }

    private void xoaRong() {
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
        List<Phim> dsPhim = controller.xemLichChieu();
        modelPhim.setRowCount(0);
        for (Phim p : dsPhim) {
            modelPhim.addRow(new Object[] {
                    p.getMaPhim(), p.getTenPhim() + " - " + p.getLoaiPhim(), p.getNgaySanXuat(),
                    p.getThoiLuongPhim(), p.getDonViSanXuat(), p.getGioiHan()
            });
        }
    }
}
