package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.TrangThaiGhe_Controller;
import dao.TrangThaiGhe_DAO;
import model.TrangThaiGhe;

public class FrmTrangThaiGhe extends JFrame {
    private JTextField txtMaGhe, txtMaSuatChieu, txtTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private TrangThaiGhe_Controller controller;
    private TrangThaiGhe_DAO dao;

    public FrmTrangThaiGhe() {
        setTitle("Quản Lý Trạng Thái Ghế");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new TrangThaiGhe_DAO();
        controller = new TrangThaiGhe_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Trạng Thái Ghế"));
        
        pnlInput.add(new JLabel("Mã Ghế:"));
        txtMaGhe = new JTextField();
        pnlInput.add(txtMaGhe);

        pnlInput.add(new JLabel("Mã Suất Chiếu:"));
        txtMaSuatChieu = new JTextField();
        pnlInput.add(txtMaSuatChieu);

        pnlInput.add(new JLabel("Trạng Thái (Trống, Đang giữ, Đã bán):"));
        txtTrangThai = new JTextField();
        pnlInput.add(txtTrangThai);

        // --- Panel Nút Chức Năng ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm", IconUtils.getAddIcon());
        btnSua = new JButton("Sửa", IconUtils.getEditIcon());
        btnXoa = new JButton("Xóa", IconUtils.getDeleteIcon());
        btnXoaTrang = new JButton("Xóa Trắng", IconUtils.getClearIcon());
        
        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnXoaTrang);

        // Register controller
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);

        // --- Panel Nút Menu Điều Hướng ---
        JPanel pnlMenu = new MenuPanel(this);

        // Container phía Bắc (chứa Form và Buttons)
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlMenu, BorderLayout.NORTH);
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlButtons, BorderLayout.SOUTH);
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlNorth, BorderLayout.NORTH);

        // --- Panel Bảng Dữ Liệu ---
        String[] columns = {"Mã Ghế", "Mã Suất Chiếu", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Trạng Thái Ghế"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaGhe.setText(table.getValueAt(row, 0).toString());
                    txtMaSuatChieu.setText(table.getValueAt(row, 1).toString());
                    txtTrangThai.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllTrangThai());
    }

    public void updateTable(ArrayList<TrangThaiGhe> ds) {
        tableModel.setRowCount(0);
        for (TrangThaiGhe ttg : ds) {
            tableModel.addRow(new Object[]{
                ttg.getGhe().getMaGhe(),
                ttg.getSuatChieu().getMaSuatChieu(),
                ttg.getTrangThai()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaGhe() { return txtMaGhe; }
    public JTextField getTxtMaSuatChieu() { return txtMaSuatChieu; }
    public JTextField getTxtTrangThai() { return txtTrangThai; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnXoaTrang() { return btnXoaTrang; }
    public JTable getTable() { return table; }

    public static void main(String[] args) {
        try {
            ConnectDB.DBConnection.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FrmTrangThaiGhe().setVisible(true);
    }
}
