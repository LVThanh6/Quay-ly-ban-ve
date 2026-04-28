package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.Ghe_Controller;
import dao.Ghe_DAO;
import model.Ghe;

public class FrmGhe extends JFrame {
    private JTextField txtMaGhe, txtLoaiGhe, txtMaPhongChieu;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private Ghe_Controller controller;
    private Ghe_DAO dao;

    public FrmGhe() {
        setTitle("Quản Lý Ghế");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new Ghe_DAO();
        controller = new Ghe_Controller(this);

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Ghế"));
        
        pnlInput.add(new JLabel("Mã Ghế:"));
        txtMaGhe = new JTextField();
        pnlInput.add(txtMaGhe);

        pnlInput.add(new JLabel("Loại Ghế (Ví dụ: Thường, VIP):"));
        txtLoaiGhe = new JTextField();
        pnlInput.add(txtLoaiGhe);

        pnlInput.add(new JLabel("Mã Phòng Chiếu:"));
        txtMaPhongChieu = new JTextField();
        pnlInput.add(txtMaPhongChieu);

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
        String[] columns = {"Mã Ghế", "Loại Ghế", "Mã Phòng Chiếu"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Ghế"));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaGhe.setText(table.getValueAt(row, 0).toString());
                    txtLoaiGhe.setText(table.getValueAt(row, 1).toString());
                    txtMaPhongChieu.setText(table.getValueAt(row, 2).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllGhe());
    }

    public void updateTable(ArrayList<Ghe> ds) {
        tableModel.setRowCount(0);
        for (Ghe ghe : ds) {
            tableModel.addRow(new Object[]{
                ghe.getMaGhe(),
                ghe.getLoaiGhe(),
                ghe.getPhongChieu().getMaPhongChieu()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaGhe() { return txtMaGhe; }
    public JTextField getTxtLoaiGhe() { return txtLoaiGhe; }
    public JTextField getTxtMaPhongChieu() { return txtMaPhongChieu; }
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
        new FrmGhe().setVisible(true);
    }
}
