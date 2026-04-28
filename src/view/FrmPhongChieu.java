package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.PhongChieu_Controller;
import dao.PhongChieu_DAO;
import model.PhongChieu;

public class FrmPhongChieu extends JFrame {
    private JTextField txtMaPhongChieu, txtSoLuongGhe, txtDinhDangPhong;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private PhongChieu_Controller controller;
    private PhongChieu_DAO dao;

    public FrmPhongChieu() {
        setTitle("Quản Lý Phòng Chiếu");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new PhongChieu_DAO();
        controller = new PhongChieu_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Phòng Chiếu"));
        
        pnlInput.add(new JLabel("Mã Phòng Chiếu:"));
        txtMaPhongChieu = new JTextField();
        pnlInput.add(txtMaPhongChieu);

        pnlInput.add(new JLabel("Số Lượng Ghế:"));
        txtSoLuongGhe = new JTextField();
        pnlInput.add(txtSoLuongGhe);

        pnlInput.add(new JLabel("Định Dạng Phòng:"));
        txtDinhDangPhong = new JTextField();
        pnlInput.add(txtDinhDangPhong);

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
        String[] columns = {"Mã Phòng Chiếu", "Số Lượng Ghế", "Định Dạng Phòng"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Phòng Chiếu"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaPhongChieu.setText(table.getValueAt(row, 0).toString());
                    txtSoLuongGhe.setText(table.getValueAt(row, 1).toString());
                    txtDinhDangPhong.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllPhongChieu());
    }

    public void updateTable(ArrayList<PhongChieu> ds) {
        tableModel.setRowCount(0);
        for (PhongChieu pc : ds) {
            tableModel.addRow(new Object[]{
                pc.getMaPhongChieu(),
                pc.getSoLuongGhe(),
                pc.getDinhDangPhong()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaPhongChieu() { return txtMaPhongChieu; }
    public JTextField getTxtSoLuongGhe() { return txtSoLuongGhe; }
    public JTextField getTxtDinhDangPhong() { return txtDinhDangPhong; }
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
        new FrmPhongChieu().setVisible(true);
    }
}
