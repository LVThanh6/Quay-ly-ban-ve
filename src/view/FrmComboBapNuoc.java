package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.ComboBapNuoc_Controller;
import dao.ComboBapNuoc_DAO;
import model.ComboBapNuoc;

public class FrmComboBapNuoc extends JFrame {
    private JTextField txtMaCombo, txtTenCombo, txtGiaBanCoBan;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private ComboBapNuoc_Controller controller;
    private ComboBapNuoc_DAO dao;

    public FrmComboBapNuoc() {
        setTitle("Quản Lý Combo Bắp Nước");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new ComboBapNuoc_DAO();
        controller = new ComboBapNuoc_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Combo Bắp Nước"));
        
        pnlInput.add(new JLabel("Mã Combo:"));
        txtMaCombo = new JTextField();
        pnlInput.add(txtMaCombo);

        pnlInput.add(new JLabel("Tên Combo:"));
        txtTenCombo = new JTextField();
        pnlInput.add(txtTenCombo);

        pnlInput.add(new JLabel("Giá Combo:"));
        txtGiaBanCoBan = new JTextField();
        pnlInput.add(txtGiaBanCoBan);

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

        // Container phía Bắc
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlMenu, BorderLayout.NORTH);
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlButtons, BorderLayout.SOUTH);
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlNorth, BorderLayout.NORTH);

        // --- Panel Bảng Dữ Liệu ---
        String[] columns = {"Mã Combo", "Tên Combo", "Giá Combo"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Combo Bắp Nước"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaCombo.setText(table.getValueAt(row, 0).toString());
                    txtTenCombo.setText(table.getValueAt(row, 1).toString());
                    txtGiaBanCoBan.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllCombo());
    }

    public void updateTable(ArrayList<ComboBapNuoc> ds) {
        tableModel.setRowCount(0);
        for (ComboBapNuoc cb : ds) {
            tableModel.addRow(new Object[]{
                cb.getMaSanPham(),
                cb.getTenSanPham(),
                cb.getGiaBanCoBan()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaCombo() { return txtMaCombo; }
    public JTextField getTxtTenCombo() { return txtTenCombo; }
    public JTextField getTxtGiaBanCoBan() { return txtGiaBanCoBan; }
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
        new FrmComboBapNuoc().setVisible(true);
    }
}
