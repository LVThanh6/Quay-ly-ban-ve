package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmChiTietHoaDon extends JFrame {
    private JTextField txtMaThanhToan, txtPhuongThucTT, txtMaHoaDon, txtMaSanPham;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmChiTietHoaDon() {
        setTitle("Quản Lý Chi Tiết Hóa Đơn");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Chi Tiết Hóa Đơn"));
        
        pnlInput.add(new JLabel("Mã Thanh Toán:"));
        txtMaThanhToan = new JTextField();
        pnlInput.add(txtMaThanhToan);

        pnlInput.add(new JLabel("Phương Thức Thanh Toán:"));
        txtPhuongThucTT = new JTextField();
        pnlInput.add(txtPhuongThucTT);

        pnlInput.add(new JLabel("Mã Hóa Đơn:"));
        txtMaHoaDon = new JTextField();
        pnlInput.add(txtMaHoaDon);
        
        pnlInput.add(new JLabel("Mã Sản Phẩm (Vé/Combo):"));
        txtMaSanPham = new JTextField();
        pnlInput.add(txtMaSanPham);

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
        String[] columns = {"Mã Thanh Toán", "Phương Thức", "Mã Hóa Đơn", "Mã Sản Phẩm"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Chi Tiết Hóa Đơn"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmChiTietHoaDon().setVisible(true);
    }
}
