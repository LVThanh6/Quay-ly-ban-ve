package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmPhim extends JFrame {
    private JTextField txtMaPhim, txtTenPhim, txtNgaySanXuat, txtDonViSanXuat, txtGioiHan, txtThoiLuong, txtLoaiPhim;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmPhim() {
        setTitle("Quản Lý Phim");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Phim"));
        
        pnlInput.add(new JLabel("Mã Phim:"));
        txtMaPhim = new JTextField();
        pnlInput.add(txtMaPhim);

        pnlInput.add(new JLabel("Tên Phim:"));
        txtTenPhim = new JTextField();
        pnlInput.add(txtTenPhim);

        pnlInput.add(new JLabel("Ngày Sản Xuất (yyyy-MM-dd):"));
        txtNgaySanXuat = new JTextField();
        pnlInput.add(txtNgaySanXuat);

        pnlInput.add(new JLabel("Đơn Vị Sản Xuất:"));
        txtDonViSanXuat = new JTextField();
        pnlInput.add(txtDonViSanXuat);
        
        pnlInput.add(new JLabel("Giới Hạn Tuổi:"));
        txtGioiHan = new JTextField();
        pnlInput.add(txtGioiHan);

        pnlInput.add(new JLabel("Thời Lượng (Phút):"));
        txtThoiLuong = new JTextField();
        pnlInput.add(txtThoiLuong);

        pnlInput.add(new JLabel("Loại Phim:"));
        txtLoaiPhim = new JTextField();
        pnlInput.add(txtLoaiPhim);
        
        pnlInput.add(new JLabel("")); // Dummy label for spacing
        pnlInput.add(new JLabel("")); 

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
        String[] columns = {"Mã Phim", "Tên Phim", "Ngày SX", "Đơn Vị SX", "Giới Hạn", "Thời Lượng", "Loại Phim"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Phim"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmPhim().setVisible(true);
    }
}
