package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmVe extends JFrame {
    private JTextField txtMaVe, txtTrangThai, txtPhiDichVu, txtGiaVeThucTe;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmVe() {
        setTitle("Quản Lý Vé");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Vé"));
        
        pnlInput.add(new JLabel("Mã Vé:"));
        txtMaVe = new JTextField();
        pnlInput.add(txtMaVe);

        pnlInput.add(new JLabel("Trạng Thái:"));
        txtTrangThai = new JTextField();
        pnlInput.add(txtTrangThai);

        pnlInput.add(new JLabel("Phí Dịch Vụ:"));
        txtPhiDichVu = new JTextField();
        pnlInput.add(txtPhiDichVu);

        pnlInput.add(new JLabel("Giá Vé Thực Tế:"));
        txtGiaVeThucTe = new JTextField();
        pnlInput.add(txtGiaVeThucTe);

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
        String[] columns = {"Mã Vé", "Trạng Thái", "Phí Dịch Vụ", "Giá Vé Thực Tế"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Vé"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmVe().setVisible(true);
    }
}
