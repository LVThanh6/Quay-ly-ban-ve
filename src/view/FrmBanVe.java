package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;
import java.io.File;

import controller.NhanVienController;
import model.Phim;

public class FrmBanVe extends JFrame {

    private NhanVienController controller;
    private JTable tblPhim;
    private DefaultTableModel modelPhim;
    private JButton btnDatVe;
    private JPanel pnlSeats;
    private JLabel lblTotal;
    private JLabel lblPoster; // Hiển thị ảnh poster phim
    
    // Đường dẫn thư mục ảnh phim
    private static final String IMG_DIR = "res/images/";
    // Danh sách file ảnh (theo thứ tự)
    private static final String[] POSTER_FILES = {
        "phim (1).jpg", "phim (2).jpg", "phim (3).jpg", "phim (4).jpg", "phim (5).jpg",
        "phim (6).jpg", "phim (7).jpg", "phim (8).jpg", "phim (9).jpg", "phim (10).jpg"
    };
    
    private String selectedPhim = "";
    private String selectedGhe = "";

    public FrmBanVe(NhanVienController controller) {
        this.controller = controller;
        setTitle("Chi Tiết Đơn Hàng - Đặt Vé");
        
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadData();
    }

    private void initUI() {
        // Cột TRÁI: Danh sách phim
        JPanel pnlLeft = new JPanel(new BorderLayout());
        pnlLeft.setOpaque(false);
        pnlLeft.setPreferredSize(new Dimension(300, 0));
        pnlLeft.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));
        
        JLabel lblPhimTitle = new JLabel("Phim đang chiếu");
        lblPhimTitle.setForeground(new Color(30, 30, 30));
        lblPhimTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlLeft.add(lblPhimTitle, BorderLayout.NORTH);

        // Poster ảnh phim
        lblPoster = new JLabel();
        lblPoster.setPreferredSize(new Dimension(280, 170));
        lblPoster.setHorizontalAlignment(JLabel.CENTER);
        lblPoster.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblPoster.setText("Chọn phim để xem poster");
        lblPoster.setForeground(new Color(160, 160, 160));
        lblPoster.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblPoster.setHorizontalTextPosition(JLabel.CENTER);
        lblPoster.setVerticalTextPosition(JLabel.CENTER);
        pnlLeft.add(lblPoster, BorderLayout.NORTH);

        // Panel tiêu đề + poster ghép vào NORTH
        JPanel pnlLeftTop = new JPanel(new BorderLayout(0, 8));
        pnlLeftTop.setOpaque(false);
        pnlLeftTop.add(lblPhimTitle, BorderLayout.NORTH);
        pnlLeftTop.add(lblPoster, BorderLayout.CENTER);
        pnlLeft.add(pnlLeftTop, BorderLayout.NORTH);

        String[] cols = {"Mã", "Tên Phim"};
        modelPhim = new DefaultTableModel(cols, 0);
        tblPhim = new JTable(modelPhim);
        tblPhim.setRowHeight(30);
        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(tblPhim);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        pnlLeft.add(scroll, BorderLayout.CENTER);
        add(pnlLeft, BorderLayout.WEST);

        // Cột GIỮA: Sơ đồ ghế
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel lblScreen = new JLabel("MÀN HÌNH CHÍNH");
        lblScreen.setHorizontalAlignment(JLabel.CENTER);
        lblScreen.setForeground(new Color(229, 9, 20));
        lblScreen.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblScreen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(229, 9, 20)));
        pnlCenter.add(lblScreen, BorderLayout.NORTH);

        pnlSeats = new JPanel(new GridLayout(3, 10, 5, 5));
        pnlSeats.setOpaque(false);
        pnlSeats.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        
        // Vẽ ghế A, B (Thường), C (VIP)
        String[] rows = {"A", "B", "C"};
        for (String r : rows) {
            for (int i = 1; i <= 10; i++) {
                JToggleButton btnSeat = new JToggleButton(r + i);
                btnSeat.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                btnSeat.setBackground(new Color(220, 220, 220));
                btnSeat.setForeground(new Color(30, 30, 30));
                btnSeat.setFocusPainted(false);
                
                if (r.equals("C")) {
                    btnSeat.setToolTipText("Ghế VIP");
                    btnSeat.setBackground(new Color(255, 235, 150)); // Vàng nhạt VIP
                    btnSeat.setForeground(new Color(120, 80, 0));
                }
                
                boolean isVIP = r.equals("C");
                btnSeat.addActionListener(e -> {
                    if (btnSeat.isSelected()) {
                        btnSeat.setBackground(new Color(229, 9, 20)); // Đỏ khi chọn
                        btnSeat.setForeground(Color.WHITE);
                        selectedGhe += btnSeat.getText() + " ";
                    } else {
                        btnSeat.setBackground(isVIP ? new Color(255, 235, 150) : new Color(220, 220, 220));
                        btnSeat.setForeground(isVIP ? new Color(120, 80, 0) : new Color(30, 30, 30));
                        selectedGhe = selectedGhe.replace(btnSeat.getText() + " ", "");
                    }
                    updateTotal();
                });
                pnlSeats.add(btnSeat);
            }
        }
        
        JPanel pnlSeatWrapper = new JPanel(new BorderLayout());
        pnlSeatWrapper.setOpaque(false);
        pnlSeatWrapper.add(pnlSeats, BorderLayout.NORTH);
        pnlCenter.add(pnlSeatWrapper, BorderLayout.CENTER);
        
        add(pnlCenter, BorderLayout.CENTER);

        // Cột PHẢI: Tóm tắt đơn hàng
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setOpaque(false);
        pnlRight.setPreferredSize(new Dimension(300, 0));
        pnlRight.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));
        
        JLabel lblOrderTitle = new JLabel("Chi tiết đơn hàng");
        lblOrderTitle.setForeground(new Color(30, 30, 30));
        lblOrderTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        pnlRight.add(lblOrderTitle, BorderLayout.NORTH);
        
        lblTotal = new JLabel("Tổng tiền: $0.00");
        lblTotal.setForeground(new Color(30, 30, 30));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        btnDatVe = new JButton("XÁC NHẬN ĐẶT VÉ");
        btnDatVe.setBackground(Color.WHITE);
        btnDatVe.setForeground(new Color(229, 9, 20));
        btnDatVe.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDatVe.setPreferredSize(new Dimension(0, 50));
        btnDatVe.setFocusPainted(false);
        btnDatVe.setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2));
        
        JPanel pnlOrderBottom = new JPanel(new GridLayout(2, 1, 10, 10));
        pnlOrderBottom.setOpaque(false);
        pnlOrderBottom.add(lblTotal);
        pnlOrderBottom.add(btnDatVe);
        
        pnlRight.add(pnlOrderBottom, BorderLayout.SOUTH);
        add(pnlRight, BorderLayout.EAST);

        // Sự kiện
        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblPhim.getSelectedRow();
            if (row >= 0) {
                selectedPhim = modelPhim.getValueAt(row, 1).toString();
                updateTotal();
                // Hiển thị poster ảnh tương ứng với index
                int posterIdx = row % POSTER_FILES.length;
                File imgFile = new File(IMG_DIR + POSTER_FILES[posterIdx]);
                if (imgFile.exists()) {
                    ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
                    Image scaled = icon.getImage().getScaledInstance(280, 170, Image.SCALE_SMOOTH);
                    lblPoster.setIcon(new ImageIcon(scaled));
                    lblPoster.setText("");
                } else {
                    lblPoster.setIcon(null);
                    lblPoster.setText("Không có ảnh");
                }
            }
        });

        btnDatVe.addActionListener(e -> {
            if (selectedPhim.isEmpty() || selectedGhe.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phim và ít nhất 1 ghế!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Đặt vé thành công!\nPhim: " + selectedPhim + "\nGhế: " + selectedGhe);
            // Reset
            selectedGhe = "";
            tblPhim.clearSelection();
            for (java.awt.Component c : pnlSeats.getComponents()) {
                if (c instanceof JToggleButton) {
                    JToggleButton tb = (JToggleButton) c;
                    tb.setSelected(false);
                    // Ghế VIP: text màu vàng đậm, ghế thường: xám nhạt
                    boolean vip = tb.getText().startsWith("C");
                    tb.setBackground(vip ? new Color(255, 235, 150) : new Color(220, 220, 220));
                    tb.setForeground(vip ? new Color(120, 80, 0) : new Color(30, 30, 30));
                }
            }
            updateTotal();
        });
    }

    private void updateTotal() {
        int count = selectedGhe.split(" ").length;
        if (selectedGhe.trim().isEmpty()) count = 0;
        double total = count * 15.0; // Giả sử $15/vé
        lblTotal.setText(String.format("Tổng tiền: $%.2f", total));
    }

    private void loadData() {
        List<Phim> dsPhim = controller.xemLichChieu();
        modelPhim.setRowCount(0);
        for (Phim p : dsPhim) {
            modelPhim.addRow(new Object[]{p.getMaPhim(), p.getTenPhim()});
        }
    }
}
