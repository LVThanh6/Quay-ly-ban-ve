package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.KhachHang_Controller;
import dao.KhachHang_DAO;
import model.KhachHang;

public class FrmKhachHang extends JFrame {
    private JTextField txtSDT, txtHoTen, txtNgaySinh;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private KhachHang_Controller controller;
    private KhachHang_DAO khachHangDAO;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public FrmKhachHang() {
        setTitle("Quản Lý Khách Hàng");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        khachHangDAO = new KhachHang_DAO();
        controller = new KhachHang_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Khách Hàng"));
        
        pnlInput.add(new JLabel("Số Điện Thoại:"));
        txtSDT = new JTextField();
        pnlInput.add(txtSDT);

        pnlInput.add(new JLabel("Họ và Tên:"));
        txtHoTen = new JTextField();
        pnlInput.add(txtHoTen);

        pnlInput.add(new JLabel("Ngày Sinh (dd/MM/yyyy):"));
        txtNgaySinh = new JTextField();
        pnlInput.add(txtNgaySinh);

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
        String[] columns = {"Số Điện Thoại", "Họ Tên", "Ngày Sinh"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Khách Hàng"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtSDT.setText(table.getValueAt(row, 0).toString());
                    txtHoTen.setText(table.getValueAt(row, 1).toString());
                    txtNgaySinh.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(khachHangDAO.getAllKhachHang());
    }

    public void updateTable(ArrayList<KhachHang> ds) {
        tableModel.setRowCount(0);
        for (KhachHang kh : ds) {
            tableModel.addRow(new Object[]{
                kh.getsDT(),
                kh.getHoTen(),
                df.format(kh.getNgaySinh())
            });
        }
    }

    // Getters
    public JTextField getTxtSDT() { return txtSDT; }
    public JTextField getTxtHoTen() { return txtHoTen; }
    public JTextField getTxtNgaySinh() { return txtNgaySinh; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnXoaTrang() { return btnXoaTrang; }
    public JTable getTable() { return table; }

    public static void main(String[] args) {
        // Cần kết nối DB trước khi chạy
        try {
            ConnectDB.DBConnection.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FrmKhachHang().setVisible(true);
    }
}

