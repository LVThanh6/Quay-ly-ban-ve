package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmSanPham extends JFrame {
    private JTextField txtMaSanPham, txtTenSanPham, txtGiaBanCoBan;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmSanPham() {
        setTitle("Quản Lý Sản Phẩm (Abstract)");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 2, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Sản Phẩm"));
        
        pnlInput.add(new JLabel("Mã Sản Phẩm:"));
        txtMaSanPham = new JTextField();
        pnlInput.add(txtMaSanPham);

        pnlInput.add(new JLabel("Tên Sản Phẩm:"));
        txtTenSanPham = new JTextField();
        pnlInput.add(txtTenSanPham);

        pnlInput.add(new JLabel("Giá Bán Cơ Bản:"));
        txtGiaBanCoBan = new JTextField();
        pnlInput.add(txtGiaBanCoBan);

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
        String[] columns = {"Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Bán Cơ Bản"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Sản Phẩm"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmSanPham().setVisible(true);
    }
}
