package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class FrmGhe extends JFrame {
    private JTextField txtMaGhe, txtLoaiGhe, txtMaPhongChieu;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;

    public FrmGhe() {
        setTitle("Quản Lý Ghế");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

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
        
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new FrmGhe().setVisible(true);
    }
}
