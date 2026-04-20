package view;

import model.NhanVien;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DangNhap_UI extends JFrame {

    private JTextField txtSDT;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JButton btnThoat;

    // Định nghĩa màu sắc chủ đạo (Cinema Dark Theme)
    private final Color COLOR_BG = new Color(18, 18, 18);          // Màu nền chính
    private final Color COLOR_CARD = new Color(30, 30, 30);        // Màu thẻ đăng nhập
    private final Color COLOR_ACCENT = new Color(229, 9, 20);      // Màu đỏ Netflix (Accent)
    private final Color COLOR_ACCENT_HOVER = new Color(180, 0, 15); // Đỏ đậm khi hover
    private final Color COLOR_TEXT_MAIN = Color.WHITE;
    private final Color COLOR_TEXT_SUB = new Color(179, 179, 179);  // Màu xám nhạt cho label
    private final Color COLOR_INPUT_BG = new Color(51, 51, 51);    // Màu nền ô nhập

    public DangNhap_UI() {
        setTitle("Hệ thống Rạp Chiếu Phim - Đăng nhập");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Background chính của Frame
        getContentPane().setBackground(COLOR_BG);
        setLayout(new GridBagLayout());

        initUI();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        
        // ==========================================
        // THẺ ĐĂNG NHẬP (LOGIN CARD)
        // ==========================================
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(60, 60, 60), 1), 
            new EmptyBorder(40, 40, 40, 40)
        ));
        card.setPreferredSize(new Dimension(380, 480));

        // 1. Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblSubTitle = new JLabel("Chào mừng bạn trở lại!");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubTitle.setForeground(COLOR_TEXT_SUB);
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblSubTitle);
        card.add(Box.createRigidArea(new Dimension(0, 40)));

        // 2. Ô nhập Số điện thoại
        JLabel lblSDT = new JLabel("Số điện thoại");
        lblSDT.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSDT.setForeground(COLOR_TEXT_SUB);
        lblSDT.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblSDT);
        card.add(Box.createRigidArea(new Dimension(0, 5)));

        txtSDT = createStyledTextField();
        card.add(txtSDT);
        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // 3. Ô nhập Mật khẩu
        JLabel lblMatKhau = new JLabel("Mật khẩu");
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMatKhau.setForeground(COLOR_TEXT_SUB);
        lblMatKhau.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblMatKhau);
        card.add(Box.createRigidArea(new Dimension(0, 5)));

        txtMatKhau = createStyledPasswordField();
        card.add(txtMatKhau);
        card.add(Box.createRigidArea(new Dimension(0, 30)));

        // 4. Nút Đăng nhập
        btnDangNhap = new JButton("ĐĂNG NHẬP");
        styleButton(btnDangNhap, COLOR_ACCENT, COLOR_ACCENT_HOVER, true);
        btnDangNhap.addActionListener(e -> xuLyDangNhap());
        card.add(btnDangNhap);
        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // 5. Nút Thoát
        btnThoat = new JButton("Thoát hệ thống");
        styleButton(btnThoat, COLOR_INPUT_BG, new Color(70, 70, 70), false);
        btnThoat.addActionListener(e -> System.exit(0));
        card.add(btnThoat);

        // Thêm card vào giữ màn hình
        add(card, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBackground(COLOR_INPUT_BG);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 80), 1),
            new EmptyBorder(0, 15, 0, 15)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setBackground(COLOR_INPUT_BG);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 80), 1),
            new EmptyBorder(0, 15, 0, 15)
        ));
        return field;
    }

    private void styleButton(JButton btn, Color bg, Color hover, boolean isPrimary) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setContentAreaFilled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Hiệu ứng hover cho nút
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hover);
                if (!isPrimary) btn.setBorder(new LineBorder(COLOR_ACCENT, 1));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
                btn.setBorder(new EmptyBorder(10, 20, 10, 20));
            }
        });
    }

    private void xuLyDangNhap() {
        String sdtNhap = txtSDT.getText().trim();
        String matKhauNhap = new String(txtMatKhau.getPassword());

        if (sdtNhap.isEmpty() || matKhauNhap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Số điện thoại và Mật khẩu!", 
                                          "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Mock Data
        NhanVien nvTest = new NhanVien("NV01", "Lê Minh Cường", "hash_123", "0911222333", 6000000, "Nhân viên bán vé");

        if (nvTest.getSdt().equals(sdtNhap) && nvTest.getMatKhau().equals(matKhauNhap)) {
            JOptionPane.showMessageDialog(this, "Xin chào " + nvTest.getHoTen() + "!\nĐăng nhập thành công!");
            this.dispose(); 
            SwingUtilities.invokeLater(() -> {
                MainUI main = new MainUI(nvTest);
                main.setVisible(true); 
            });
        } else {
            JOptionPane.showMessageDialog(this, "Số điện thoại hoặc mật khẩu không đúng!", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Cấu hình nhìn chuyên nghiệp hơn dù không khai báo LaFs ngoài
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new DangNhap_UI().setVisible(true);
        });
    }
}
