package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmPhieuDatVe extends JFrame {
    private JTextField txtMaPhieu, txtSoLuongVe, txtNgayDat, txtThoiGianGiuCho, txtTrangThai, txtSDTKhachHang, txtMaNhanVien;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmPhieuDatVe() {
        setTitle("Quản Lý Phiếu Đặt Vé");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(4, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Phiếu Đặt Vé"));
        
        pnlInput.add(new JLabel("Mã Phiếu Đặt:"));
        txtMaPhieu = new JTextField();
        pnlInput.add(txtMaPhieu);

        pnlInput.add(new JLabel("Số Lượng Vé:"));
        txtSoLuongVe = new JTextField();
        pnlInput.add(txtSoLuongVe);

        pnlInput.add(new JLabel("Ngày Đặt:"));
        txtNgayDat = new JTextField();
        pnlInput.add(txtNgayDat);

        pnlInput.add(new JLabel("Thời Gian Giữ Chỗ:"));
        txtThoiGianGiuCho = new JTextField();
        pnlInput.add(txtThoiGianGiuCho);
        
        pnlInput.add(new JLabel("Trạng Thái:"));
        txtTrangThai = new JTextField();
        pnlInput.add(txtTrangThai);
        
        pnlInput.add(new JLabel("Số ĐT Khách Hàng:"));
        txtSDTKhachHang = new JTextField();
        pnlInput.add(txtSDTKhachHang);
        
        pnlInput.add(new JLabel("Mã Nhân Viên:"));
        txtMaNhanVien = new JTextField();
        pnlInput.add(txtMaNhanVien);

        pnlInput.add(new JLabel(""));
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

        // Container phía Bắc
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(pnlMenu, BorderLayout.NORTH);
        pnlNorth.add(pnlInput, BorderLayout.CENTER);
        pnlNorth.add(pnlButtons, BorderLayout.SOUTH);
        pnlNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnlNorth, BorderLayout.NORTH);

        // --- Panel Bảng Dữ Liệu ---
        String[] columns = {"Mã Phiếu", "Số Lượng", "Ngày Đặt", "Thời Gian Giữ", "Trạng Thái", "SĐT Khách", "Mã NV"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Phiếu Đặt Vé"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmPhieuDatVe().setVisible(true);
    }
}
