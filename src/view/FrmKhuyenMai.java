package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.KhuyenMai_Controller;
import dao.KhuyenMai_DAO;
import model.KhuyenMai;

public class FrmKhuyenMai extends JFrame {
    private JTextField txtMaKhuyenMai, txtTenKhuyenMai, txtHinhThucGiam;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private KhuyenMai_Controller controller;
    private KhuyenMai_DAO dao;

    public FrmKhuyenMai() {
        setTitle("Quản Lý Khuyến Mãi");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new KhuyenMai_DAO();
        controller = new KhuyenMai_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Khuyến Mãi"));
        
        pnlInput.add(new JLabel("Mã Khuyến Mãi:"));
        txtMaKhuyenMai = new JTextField();
        pnlInput.add(txtMaKhuyenMai);

        pnlInput.add(new JLabel("Tên Khuyến Mãi:"));
        txtTenKhuyenMai = new JTextField();
        pnlInput.add(txtTenKhuyenMai);

        pnlInput.add(new JLabel("Hình Thức Giảm (% / VNĐ):"));
        txtHinhThucGiam = new JTextField();
        pnlInput.add(txtHinhThucGiam);

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
        String[] columns = {"Mã KM", "Tên KM", "Mức Giảm"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Khuyến Mãi"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaKhuyenMai.setText(table.getValueAt(row, 0).toString());
                    txtTenKhuyenMai.setText(table.getValueAt(row, 1).toString());
                    txtHinhThucGiam.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllKhuyenMai());
    }

    public void updateTable(ArrayList<KhuyenMai> ds) {
        tableModel.setRowCount(0);
        for (KhuyenMai km : ds) {
            tableModel.addRow(new Object[]{
                km.getMaKhuyenMai(),
                km.getTenKhuyenMai(),
                km.getHinhThucGiam()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaKhuyenMai() { return txtMaKhuyenMai; }
    public JTextField getTxtTenKhuyenMai() { return txtTenKhuyenMai; }
    public JTextField getTxtHinhThucGiam() { return txtHinhThucGiam; }
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
        new FrmKhuyenMai().setVisible(true);
    }
}
