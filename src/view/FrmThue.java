package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.Thue_Controller;
import dao.Thue_DAO;
import model.Thue;

public class FrmThue extends JFrame {
    private JTextField txtMaThue, txtTenThue, txtMucThue;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private Thue_Controller controller;
    private Thue_DAO dao;

    public FrmThue() {
        setTitle("Quản Lý Thuế");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new Thue_DAO();
        controller = new Thue_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Thuế"));
        
        pnlInput.add(new JLabel("Mã Thuế:"));
        txtMaThue = new JTextField();
        pnlInput.add(txtMaThue);

        pnlInput.add(new JLabel("Tên Thuế:"));
        txtTenThue = new JTextField();
        pnlInput.add(txtTenThue);

        pnlInput.add(new JLabel("Mức Thuế (Ví dụ: 0.1 cho 10%):"));
        txtMucThue = new JTextField();
        pnlInput.add(txtMucThue);

        // --- Panel Nút Chức Năng ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaTrang = new JButton("Xóa Trắng");
        
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
        String[] columns = {"Mã Thuế", "Tên Thuế", "Mức Thuế"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Thuế"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaThue.setText(table.getValueAt(row, 0).toString());
                    txtTenThue.setText(table.getValueAt(row, 1).toString());
                    txtMucThue.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllThue());
    }

    public void updateTable(ArrayList<Thue> ds) {
        tableModel.setRowCount(0);
        for (Thue t : ds) {
            tableModel.addRow(new Object[]{
                t.getMaThue(),
                t.getTenThue(),
                t.getMucThue()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaThue() { return txtMaThue; }
    public JTextField getTxtTenThue() { return txtTenThue; }
    public JTextField getTxtMucThue() { return txtMucThue; }
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
        new FrmThue().setVisible(true);
    }
}
