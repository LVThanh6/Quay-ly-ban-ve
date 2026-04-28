package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmHoaDon extends JFrame {
    private JTextField txtMaHoaDon, txtThoiGianTao, txtTongTien, txtMaNhanVien, txtSDTKhachHang, txtMaKhuyenMai, txtMaThue;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmHoaDon() {
        setTitle("Quản Lý Hóa Đơn");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Hóa Đơn"));
        
        pnlInput.add(new JLabel("Mã Hóa Đơn:"));
        txtMaHoaDon = new JTextField();
        pnlInput.add(txtMaHoaDon);

        pnlInput.add(new JLabel("Thời Gian Tạo (yyyy-MM-dd HH:mm):"));
        txtThoiGianTao = new JTextField();
        pnlInput.add(txtThoiGianTao);

        pnlInput.add(new JLabel("Tổng Tiền:"));
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false); // Thường để auto-tính
        pnlInput.add(txtTongTien);

        pnlInput.add(new JLabel("Mã Nhân Viên:"));
        txtMaNhanVien = new JTextField();
        pnlInput.add(txtMaNhanVien);
        
        pnlInput.add(new JLabel("SĐT Khách Hàng:"));
        txtSDTKhachHang = new JTextField();
        pnlInput.add(txtSDTKhachHang);

        pnlInput.add(new JLabel("Mã Khuyến Mãi:"));
        txtMaKhuyenMai = new JTextField();
        pnlInput.add(txtMaKhuyenMai);
        
        pnlInput.add(new JLabel("Mã Thuế:"));
        txtMaThue = new JTextField();
        pnlInput.add(txtMaThue);
        
        pnlInput.add(new JLabel("")); // Dummy label

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
        String[] columns = {"Mã Hóa Đơn", "Thời Gian", "Tổng Tiền", "Mã NV", "SĐT Khách", "Mã KM", "Mã Thuế"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Hóa Đơn"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmHoaDon().setVisible(true);
    }
}
