package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.NhanVien_Controller;
import dao.NhanVien_DAO;
import model.NhanVien;

public class FrmNhanVien extends JFrame {
    private JTextField txtMaNhanVien, txtHoTen, txtMatKhau, txtSDT, txtLuongCoBan, txtVaiTro;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private NhanVien_Controller controller;
    private NhanVien_DAO dao;

    public FrmNhanVien() {
        setTitle("Quản Lý Nhân Viên");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new NhanVien_DAO();
        controller = new NhanVien_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Nhân Viên"));
        
        pnlInput.add(new JLabel("Mã Nhân Viên:"));
        txtMaNhanVien = new JTextField();
        pnlInput.add(txtMaNhanVien);

        pnlInput.add(new JLabel("Họ Tên:"));
        txtHoTen = new JTextField();
        pnlInput.add(txtHoTen);

        pnlInput.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        pnlInput.add(txtSDT);

        pnlInput.add(new JLabel("Mật Khẩu:"));
        txtMatKhau = new JTextField();
        pnlInput.add(txtMatKhau);
        
        pnlInput.add(new JLabel("Lương Cơ Bản:"));
        txtLuongCoBan = new JTextField();
        pnlInput.add(txtLuongCoBan);

        pnlInput.add(new JLabel("Vai Trò:"));
        txtVaiTro = new JTextField();
        pnlInput.add(txtVaiTro);

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

        // Container phía Bắc (chứa Form và Buttons)
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlMenu, BorderLayout.NORTH);
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlButtons, BorderLayout.SOUTH);
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlNorth, BorderLayout.NORTH);

        // --- Panel Bảng Dữ Liệu ---
        String[] columns = {"Mã NV", "Họ Tên", "Số ĐT", "Lương Cơ Bản", "Vai Trò"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Nhân Viên"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaNhanVien.setText(table.getValueAt(row, 0).toString());
                    txtHoTen.setText(table.getValueAt(row, 1).toString());
                    txtMatKhau.setText(table.getValueAt(row, 2).toString());
                    txtSDT.setText(table.getValueAt(row, 3).toString());
                    txtLuongCoBan.setText(table.getValueAt(row, 4).toString());
                    txtVaiTro.setText(table.getValueAt(row, 5).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllNhanVien());
    }

    public void updateTable(ArrayList<NhanVien> ds) {
        tableModel.setRowCount(0);
        for (NhanVien nv : ds) {
            tableModel.addRow(new Object[]{
                nv.getMaNhanVien(),
                nv.getHoTen(),
                nv.getMatKhau(),
                nv.getSdt(),
                nv.getLuongCoBan(),
                nv.getVaiTro()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaNhanVien() { return txtMaNhanVien; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JTextField getTxtMatKhau() { return txtMatKhau; }
    public JTextField getTxtSDT() { return txtSDT; }
    public JTextField getTxtLuongCoBan() { return txtLuongCoBan; }
    public JTextField getTxtVaiTro() { return txtVaiTro; }
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
        new FrmNhanVien().setVisible(true);
    }
}
