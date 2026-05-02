package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.NhanVien_DAO;
import model.NhanVien;

public class LoginGUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    // Constructor được bảo vệ (protected) theo yêu cầu, chỉ được gọi từ package
    // hoặc subclass
    protected LoginGUI() {
        setTitle("Đăng nhập hệ thống");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setBounds(20, 30, 100, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(120, 30, 150, 25);
        add(txtUsername);

        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setBounds(20, 70, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(120, 70, 150, 25);
        add(txtPassword);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setBounds(100, 110, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xulyDangNhap();
            }
        });
    }

    private void xulyDangNhap() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        NhanVien_DAO dao = new NhanVien_DAO();
        boolean success = false;
        NhanVien userLogin = null;

        for (NhanVien nv : dao.getAllNhanVien()) {
            // Giả sử mã nhân viên là username
            if (nv.getMaNhanVien().equals(username) && nv.getMatKhau().equals(password)) {
                success = true;
                userLogin = nv;
                break;
            }
        }

        // Demo fallback để dễ test nếu DB trống hoặc lỗi kết nối
        if (!success && username.equals("admin") && password.equals("admin")) {
            userLogin = new NhanVien("admin", "Quản Lý Hệ Thống", "admin", "0123", 1000, model.ChucVu.QUAN_LY);
            success = true;
        } else if (!success && username.equals("nhanvien") && password.equals("nhanvien")) {
            userLogin = new NhanVien("nhanvien", "Nhân Viên Bán Vé", "nhanvien", "0123", 1000, model.ChucVu.NHAN_VIEN);
            success = true;
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            this.dispose(); // Đóng form đăng nhập
            MainGUI mainGUI = new MainGUI(userLogin);
            mainGUI.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }
    }

    // Cung cấp một phương thức tĩnh public để Start gọi mà không vi phạm quy tắc
    // protected constructor
    public static void start() {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
