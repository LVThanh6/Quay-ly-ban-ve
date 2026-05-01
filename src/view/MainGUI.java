package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.*;
import model.ChucVu;
import model.NhanVien;

public class MainGUI extends JFrame {

    private NhanVien currentUser;
    private NhanVienController baseController;

    private JPanel pnlSidebar;
    private JPanel pnlContent;
    private CardLayout cardLayout;

    // Các cụm menu
    private JPanel pnlMenuSale;
    private JPanel pnlMenuAdmin;

    // Map chứa các nút menu để thao tác chuyển trang
    private Map<String, JButton> menuButtons = new HashMap<>();

    protected MainGUI(NhanVien user) {
        this.currentUser = user;
        if (currentUser.getVaiTro() == ChucVu.QUAN_LY) {
            this.baseController = new QuanLyController();
        } else {
            this.baseController = new NhanVienController();
        }
        initUI();
    }

    private void initUI() {
        setTitle("CINEMANAGER PRO - " + currentUser.getHoTen());
        setSize(1280, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        pnlContent = new JPanel(cardLayout);

        // 1. Tạo Sidebar (Trái)
        createSidebar();

        // 2. Tích hợp các Giao diện (Views)
        setupViews();

        add(pnlContent, BorderLayout.CENTER);

        // Áp dụng Phân Quyền (RBAC)
        applyRBAC();
    }

    private void createSidebar() {
        pnlSidebar = new JPanel(new BorderLayout());
        pnlSidebar.setPreferredSize(new Dimension(250, 0));
        pnlSidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));

        // Logo
        JPanel pnlTop = new JPanel(new GridLayout(2, 1));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblLogo = new JLabel("GRAND CINEMA");
        lblLogo.setForeground(new Color(229, 9, 20));
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnlTop.add(lblLogo);

        JLabel lblRole = new JLabel(
                "Vai trò: " + (currentUser.getVaiTro() == ChucVu.QUAN_LY ? "Quản Lý" : "Nhân Viên"));
        lblRole.setForeground(new Color(100, 100, 100));
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));
        pnlTop.add(lblRole);
        pnlSidebar.add(pnlTop, BorderLayout.NORTH);

        // Khu vực Menu (Scrollable)
        JPanel pnlMenuContainer = new JPanel();
        pnlMenuContainer.setLayout(new BoxLayout(pnlMenuContainer, BoxLayout.Y_AXIS));
        pnlMenuContainer.setOpaque(false);

        // Nhóm 1: Bán Hàng & CRM
        pnlMenuSale = createMenuGroup("BÁN HÀNG & CRM");
        addMenuItem(pnlMenuSale, "Bán Vé", "BanVe");
        addMenuItem(pnlMenuSale, "Combo Bắp Nước", "Combo");
        addMenuItem(pnlMenuSale, "Khách Hàng", "KhachHang");
        addMenuItem(pnlMenuSale, "Thuê Dịch Vụ", "Thue");
        pnlMenuContainer.add(pnlMenuSale);

        // Nhóm 2: Quản Trị
        pnlMenuAdmin = createMenuGroup("QUẢN TRỊ HỆ THỐNG");
        addMenuItem(pnlMenuAdmin, "Danh mục Phim", "Phim");
        addMenuItem(pnlMenuAdmin, "Suất Chiếu", "SuatChieu");
        addMenuItem(pnlMenuAdmin, "Phòng Chiếu", "PhongChieu");
        addMenuItem(pnlMenuAdmin, "Ghế", "Ghe");
        addMenuItem(pnlMenuAdmin, "Trạng Thái Ghế", "TrangThaiGhe");
        addMenuItem(pnlMenuAdmin, "Khuyến Mãi", "KhuyenMai");
        addMenuItem(pnlMenuAdmin, "Nhân Sự", "NhanVien");
        pnlMenuContainer.add(pnlMenuAdmin);

        JScrollPane scrollMenu = new JScrollPane(pnlMenuContainer);
        scrollMenu.setBorder(null);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        pnlSidebar.add(scrollMenu, BorderLayout.CENTER);

        // Nút Đăng xuất
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        pnlBottom.setOpaque(false);
        JButton btnLogout = createMenuButton("Đăng Xuất");
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginGUI.start();
        });
        pnlBottom.add(btnLogout);
        pnlSidebar.add(pnlBottom, BorderLayout.SOUTH);

        add(pnlSidebar, BorderLayout.WEST);
    }

    private JPanel createMenuGroup(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(new Color(120, 120, 120));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 0));

        JPanel pnlWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlWrapper.setOpaque(false);
        pnlWrapper.add(lblTitle);

        panel.add(pnlWrapper);
        return panel;
    }

    private void addMenuItem(JPanel parent, String text, String cardName) {
        JButton btn = createMenuButton(text);
        btn.addActionListener(e -> cardLayout.show(pnlContent, cardName));

        JPanel pnlWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlWrapper.setOpaque(false);
        pnlWrapper.add(btn);

        parent.add(pnlWrapper);
        menuButtons.put(cardName, btn);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.BLACK);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(230, 40));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(230, 230, 230));
                btn.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(null);
                btn.setForeground(Color.BLACK);
            }
        });

        return btn;
    }

    private void setupViews() {
        // Nhóm Bán Hàng
        pnlContent.add(new FrmBanVe(baseController).getContentPane(), "BanVe");

        FrmComboBapNuoc frmCombo = new FrmComboBapNuoc();
        frmCombo.setController(new ComboBapNuoc_Controller(frmCombo));
        pnlContent.add(frmCombo.getContentPane(), "Combo");

        FrmKhachHang frmKH = new FrmKhachHang();
        frmKH.setController(new KhachHang_Controller(frmKH));
        pnlContent.add(frmKH.getContentPane(), "KhachHang");

        FrmThue frmThue = new FrmThue();
        frmThue.setController(new Thue_Controller(frmThue));
        pnlContent.add(frmThue.getContentPane(), "Thue");

        // Nhóm Quản Trị (Chỉ tạo nếu là quản lý để tiết kiệm bộ nhớ, hoặc tạo hết nhưng
        // cấm click)
        if (currentUser.getVaiTro() == ChucVu.QUAN_LY) {
            pnlContent.add(new FrmQuanLyPhim((QuanLyController) baseController).getContentPane(), "Phim");

            FrmSuatChieu frmSC = new FrmSuatChieu();
            frmSC.setController(new SuatChieu_Controller(frmSC));
            pnlContent.add(frmSC.getContentPane(), "SuatChieu");

            FrmPhongChieu frmPC = new FrmPhongChieu();
            frmPC.setController(new PhongChieu_Controller(frmPC));
            pnlContent.add(frmPC.getContentPane(), "PhongChieu");

            FrmGhe frmGhe = new FrmGhe();
            frmGhe.setController(new Ghe_Controller(frmGhe));
            pnlContent.add(frmGhe.getContentPane(), "Ghe");

            FrmTrangThaiGhe frmTTG = new FrmTrangThaiGhe();
            frmTTG.setController(new TrangThaiGhe_Controller(frmTTG));
            pnlContent.add(frmTTG.getContentPane(), "TrangThaiGhe");

            FrmKhuyenMai frmKM = new FrmKhuyenMai();
            frmKM.setController(new KhuyenMai_Controller(frmKM));
            pnlContent.add(frmKM.getContentPane(), "KhuyenMai");

            FrmNhanVien frmNV = new FrmNhanVien();
            frmNV.setController(new NhanVien_Controller(frmNV));
            pnlContent.add(frmNV.getContentPane(), "NhanVien");
        } else {
            // Thêm panel trống thông báo cho các tab quản trị nếu nhân viên cố gắng truy
            // cập
            JPanel pnlLock = new JPanel(new BorderLayout());
            JLabel lblLock = new JLabel("TÍNH NĂNG NÀY CHỈ DÀNH CHO QUẢN LÝ", SwingConstants.CENTER);
            lblLock.setForeground(Color.RED);
            lblLock.setFont(new Font("Segoe UI", Font.BOLD, 24));
            pnlLock.add(lblLock, BorderLayout.CENTER);

            String[] adminCards = { "Phim", "SuatChieu", "PhongChieu", "Ghe", "TrangThaiGhe", "KhuyenMai", "NhanVien" };
            for (String card : adminCards) {
                pnlContent.add(pnlLock, card);
            }
        }
    }

    private void applyRBAC() {
        if (currentUser.getVaiTro() == ChucVu.NHAN_VIEN) {
            pnlMenuAdmin.setVisible(false); // Ẩn hoàn toàn nhóm menu Quản trị
        }
    }
}
