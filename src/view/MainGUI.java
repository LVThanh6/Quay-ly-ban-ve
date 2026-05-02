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

    private FrmQuanLyVe frmQuanLyVe;

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

        // Logo & User Info
        JPanel pnlTop = new JPanel(new GridLayout(3, 1, 0, 5));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblLogo = new JLabel("GRAND CINEMA");
        lblLogo.setForeground(new Color(229, 9, 20));
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pnlTop.add(lblLogo);

        JLabel lblUser = new JLabel("👤 " + currentUser.getHoTen());
        lblUser.setForeground(new Color(30, 30, 30));
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlTop.add(lblUser);

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
        addMenuItem(pnlMenuSale, "Đặt Vé", "DatVe");
        addMenuItem(pnlMenuSale, "Thanh Toán / Điểm Bán", "BanVe");
        addMenuItem(pnlMenuSale, "Quản Lý Vé & In Ấn", "QuanLyVe");
        addMenuItem(pnlMenuSale, "Khách Hàng", "KhachHang");
        pnlMenuContainer.add(pnlMenuSale);

        // Nhóm 2: Quản Trị
        pnlMenuAdmin = createMenuGroup("QUẢN TRỊ HỆ THỐNG");
        addMenuItem(pnlMenuAdmin, "Danh mục Phim", "Phim");
        addMenuItem(pnlMenuAdmin, "Suất Chiếu", "SuatChieu");
        addMenuItem(pnlMenuAdmin, "Phòng Chiếu", "PhongChieu");
        addMenuItem(pnlMenuAdmin, "Combo Bắp Nước", "Combo");
        addMenuItem(pnlMenuAdmin, "Thuê Dịch Vụ", "Thue");
        addMenuItem(pnlMenuAdmin, "Khuyến Mãi", "KhuyenMai");
        addMenuItem(pnlMenuAdmin, "Nhân Sự", "NhanVien");
        pnlMenuContainer.add(pnlMenuAdmin);

        JScrollPane scrollMenu = new JScrollPane(pnlMenuContainer);
        scrollMenu.setBorder(null);
        scrollMenu.setOpaque(false);
        scrollMenu.getViewport().setOpaque(false);
        pnlSidebar.add(scrollMenu, BorderLayout.CENTER);

        // Nút Đăng xuất
        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        JButton btnLogout = createMenuButton("Đăng Xuất");
        btnLogout.addActionListener(e -> {
            this.dispose();
            LoginGUI.start();
        });
        pnlBottom.add(btnLogout, BorderLayout.CENTER);
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
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 0));

        JPanel pnlWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pnlWrapper.setOpaque(false);
        pnlWrapper.add(lblTitle);

        panel.add(pnlWrapper);
        return panel;
    }

    private void addMenuItem(JPanel parent, String text, String cardName) {
        JButton btn = createMenuButton(text);
        btn.addActionListener(e -> cardLayout.show(pnlContent, cardName));

        // Use a container with top/bottom padding instead of FlowLayout
        JPanel pnlItem = new JPanel(new BorderLayout());
        pnlItem.setOpaque(false);
        pnlItem.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        pnlItem.add(btn, BorderLayout.CENTER);

        parent.add(pnlItem);
        menuButtons.put(cardName, btn);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.BLACK);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 10)
        ));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(245, 245, 245));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 9, 20), 1),
                    BorderFactory.createEmptyBorder(5, 15, 5, 10)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(5, 15, 5, 10)
                ));
            }
        });

        return btn;
    }

    private void setupViews() {
        // Nhóm Bán Hàng
        pnlContent.add(new FrmDatVe(baseController, currentUser).getContentPane(), "DatVe");
        pnlContent.add(new FrmBanVe(baseController, currentUser).getContentPane(), "BanVe");
        
        frmQuanLyVe = new FrmQuanLyVe();
        pnlContent.add(frmQuanLyVe, "QuanLyVe");

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
        // Nhóm Quản Trị
        boolean isAdmin = (currentUser.getVaiTro() == ChucVu.QUAN_LY);

        FrmQuanLyPhim frmPhim = new FrmQuanLyPhim(baseController);
        if (!isAdmin) frmPhim.setReadOnly(true);
        pnlContent.add(frmPhim.getContentPane(), "Phim");

        FrmSuatChieu frmSC = new FrmSuatChieu();
        frmSC.setController(new SuatChieu_Controller(frmSC));
        if (!isAdmin) frmSC.setReadOnly(true);
        pnlContent.add(frmSC.getContentPane(), "SuatChieu");

        FrmPhongChieu frmPC = new FrmPhongChieu();
        frmPC.setController(new PhongChieu_Controller(frmPC));
        if (!isAdmin) frmPC.setReadOnly(true);
        pnlContent.add(frmPC.getContentPane(), "PhongChieu");

        FrmKhuyenMai frmKM = new FrmKhuyenMai();
        frmKM.setController(new KhuyenMai_Controller(frmKM));
        if (!isAdmin) frmKM.setReadOnly(true);
        pnlContent.add(frmKM.getContentPane(), "KhuyenMai");

        if (isAdmin) {
            FrmNhanVien frmNV = new FrmNhanVien();
            frmNV.setController(new NhanVien_Controller(frmNV));
            pnlContent.add(frmNV.getContentPane(), "NhanVien");
        } else {
            // Đối với nhân viên, tab Nhân Sự vẫn bị khóa hoàn toàn
            JPanel pnlLock = new JPanel(new BorderLayout());
            JLabel lblLock = new JLabel("BẠN KHÔNG CÓ QUYỀN TRUY CẬP MỤC NHÂN SỰ", SwingConstants.CENTER);
            lblLock.setForeground(Color.RED);
            lblLock.setFont(new Font("Segoe UI", Font.BOLD, 18));
            pnlLock.add(lblLock, BorderLayout.CENTER);
            pnlContent.add(pnlLock, "NhanVien");
        }
    }

    private void applyRBAC() {
        if (currentUser.getVaiTro() == ChucVu.NHAN_VIEN) {
            // Nhân viên vẫn thấy menu Quản trị nhưng bị giới hạn mục Nhân Sự
            if (menuButtons.containsKey("NhanVien")) {
                menuButtons.get("NhanVien").getParent().setVisible(false);
            }
        }
    }

    public void switchToBanVe() {
        cardLayout.show(pnlContent, "BanVe");
        // Kích hoạt màu nền cho nút menu nếu cần
    }

    public void switchToQuanLyVe(String keyword) {
        cardLayout.show(pnlContent, "QuanLyVe");
        if (frmQuanLyVe != null) {
            frmQuanLyVe.searchAndSelect(keyword);
        }
    }
}
