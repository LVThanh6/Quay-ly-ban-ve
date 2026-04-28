package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmChiTietPhieuDat extends JFrame {
    private JTextField txtMaChiTiet, txtGiaTamTinh, txtMaPhieuDat, txtMaVe;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmChiTietPhieuDat() {
        setTitle("Quản Lý Chi Tiết Phiếu Đặt Vé");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Chi Tiết Phiếu Đặt"));
        
        pnlInput.add(new JLabel("Mã Chi Tiết:"));
        txtMaChiTiet = new JTextField();
        pnlInput.add(txtMaChiTiet);

        pnlInput.add(new JLabel("Giá Tạm Tính:"));
        txtGiaTamTinh = new JTextField();
        pnlInput.add(txtGiaTamTinh);

        pnlInput.add(new JLabel("Mã Phiếu Đặt:"));
        txtMaPhieuDat = new JTextField();
        pnlInput.add(txtMaPhieuDat);
        
        pnlInput.add(new JLabel("Mã Vé:"));
        txtMaVe = new JTextField();
        pnlInput.add(txtMaVe);

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
        String[] columns = {"Mã Chi Tiết", "Giá Tạm Tính", "Mã Phiếu Đặt", "Mã Vé"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Chi Tiết Phiếu Đặt"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmChiTietPhieuDat().setVisible(true);
    }
}
