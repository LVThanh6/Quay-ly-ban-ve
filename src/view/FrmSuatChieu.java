package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import controller.SuatChieu_Controller;
import dao.SuatChieu_DAO;
import model.SuatChieu;

public class FrmSuatChieu extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMaSuatChieu, txtThoiGian, txtGiaVe, txtMaPhim, txtMaPhongChieu;
    private JButton btnThem, btnSua, btnXoa, btnXoaTrang;
    private JTable table;
    private DefaultTableModel tableModel;
    private SuatChieu_Controller controller;
    private SuatChieu_DAO dao;

    public FrmSuatChieu() {
        setTitle("Quản Lý Suất Chiếu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        dao = new SuatChieu_DAO();
        controller = new SuatChieu_Controller(this);

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

        // Register controller
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);

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
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtMaSuatChieu.setText(table.getValueAt(row, 0).toString());
                    txtThoiGian.setText(table.getValueAt(row, 1).toString());
                    txtGiaVe.setText(table.getValueAt(row, 2).toString());
                    txtMaPhim.setText(table.getValueAt(row, 3).toString());
                    txtMaPhongChieu.setText(table.getValueAt(row, 4).toString());
                }
            }
        });

        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Load initial data
        updateTable(dao.getAllSuatChieu());
    }

    public void updateTable(ArrayList<SuatChieu> ds) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (SuatChieu sc : ds) {
            String thoiGian = sc.getThoiGianKhoiChieu() != null ? sc.getThoiGianKhoiChieu().format(formatter) : "";
            tableModel.addRow(new Object[]{
                sc.getMaSuatChieu(),
                thoiGian,
                sc.getGiaVeCoBan(),
                sc.getPhim().getMaPhim(),
                sc.getPhongChieu().getMaPhongChieu()
            });
        }
    }

    // Getters and Setters
    public JTextField getTxtMaSuatChieu() { return txtMaSuatChieu; }
    public JTextField getTxtThoiGian() { return txtThoiGian; }
    public JTextField getTxtGiaVe() { return txtGiaVe; }
    public JTextField getTxtMaPhim() { return txtMaPhim; }
    public JTextField getTxtMaPhongChieu() { return txtMaPhongChieu; }
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnXoaTrang() { return btnXoaTrang; }
    public JTable getTable() { return table; }

    public static void main(String[] args) {
        try {
            ConnectDB.DBConnection.getInstance().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FrmSuatChieu().setVisible(true);
    }
}
