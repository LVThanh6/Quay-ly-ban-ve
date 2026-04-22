package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmNhanVien extends JFrame {
    private JTextField txtMaNhanVien, txtHoTen, txtMatKhau, txtSDT, txtLuongCoBan, txtVaiTro;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmNhanVien() {
        setTitle("Quản Lý Nhân Viên");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

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
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmNhanVien().setVisible(true);
    }
}
