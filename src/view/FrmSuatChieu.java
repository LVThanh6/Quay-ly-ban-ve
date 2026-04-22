package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmSuatChieu extends JFrame {
    private JTextField txtMaSuatChieu, txtThoiGian, txtGiaVe, txtMaPhim, txtMaPhongChieu;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmSuatChieu() {
        setTitle("Quản Lý Suất Chiếu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Panel Nhập Liệu ---
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 15));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin Suất Chiếu"));
        
        pnlInput.add(new JLabel("Mã Suất Chiếu:"));
        txtMaSuatChieu = new JTextField();
        pnlInput.add(txtMaSuatChieu);

        pnlInput.add(new JLabel("Thời Gian Khởi Chiếu:"));
        txtThoiGian = new JTextField();
        pnlInput.add(txtThoiGian);

        pnlInput.add(new JLabel("Giá Vé Cơ Bản:"));
        txtGiaVe = new JTextField();
        pnlInput.add(txtGiaVe);
        
        pnlInput.add(new JLabel("Mã Phim:"));
        txtMaPhim = new JTextField();
        pnlInput.add(txtMaPhim);

        pnlInput.add(new JLabel("Mã Phòng Chiếu:"));
        txtMaPhongChieu = new JTextField();
        pnlInput.add(txtMaPhongChieu);
        
        pnlInput.add(new JLabel("")); // Dummy label
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
        String[] columns = {"Mã SC", "Thời Gian", "Giá Vé", "Mã Phim", "Mã Phòng"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Suất Chiếu"));
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmSuatChieu().setVisible(true);
    }
}
