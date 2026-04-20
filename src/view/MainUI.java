package view;

import dao.MockDataStore;
import controller.DatVeController;
import model.NhanVien;
import model.Phim;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainUI extends JFrame {
    private NhanVien nv;
    private MockDataStore db = MockDataStore.getInstance();
    
    public MainUI(NhanVien nv) {
        this.nv = nv;
        setTitle("HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM - CENTRAL CINEMA");
        setSize(1280, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel pnMainContent = new JPanel(new BorderLayout());
        pnMainContent.add(createHeader(), BorderLayout.NORTH);
        pnMainContent.add(createDashboard(), BorderLayout.CENTER); 
        
        add(pnMainContent, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel pnSidebar = new JPanel();
        pnSidebar.setBackground(new Color(43, 52, 62)); 
        pnSidebar.setPreferredSize(new Dimension(250, 0));
        pnSidebar.setLayout(new BoxLayout(pnSidebar, BoxLayout.Y_AXIS));
        
        pnSidebar.add(createMenuButton("Dashboard", true, null));
        pnSidebar.add(createMenuButton("Quản lý Khách hàng", false, null));
        pnSidebar.add(createMenuButton("Quản lý Phim & Suất chiếu", false, null));
        
        JButton btnBanVe = createMenuButton("Bán Vé Tại Quầy", false, e -> moManHinhBanVe());
        pnSidebar.add(btnBanVe);
        
        pnSidebar.add(createMenuButton("Báo cáo & Thống kê", false, null));
        
        return pnSidebar;
    }

    private void moManHinhBanVe() {
        // Mở cửa sổ Bán Vé (pass NhanVien qua)
        SwingUtilities.invokeLater(() -> {
            BanVe_UI banVeUI = new BanVe_UI(nv);
            banVeUI.setVisible(true);
            this.dispose(); // Tạm đóng main, hoặc có thể mở như một tab/dialog
        });
    }

    private JPanel createHeader() {
        JPanel pnHeader = new JPanel(new BorderLayout());
        pnHeader.setBackground(new Color(52, 73, 94));
        pnHeader.setPreferredSize(new Dimension(0, 60));
        
        JLabel lblTitle = new JLabel("  HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM - CENTRAL CINEMA");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblUser = new JLabel("Profile: " + nv.getHoTen() + " (" + nv.getVaiTro() + ")  ");
        lblUser.setForeground(Color.WHITE);
        
        pnHeader.add(lblTitle, BorderLayout.WEST);
        pnHeader.add(lblUser, BorderLayout.EAST);
        
        return pnHeader;
    }

    private JScrollPane createDashboard() {
        JPanel pnDashboard = new JPanel(new GridBagLayout());
        pnDashboard.setBackground(new Color(240, 242, 245)); 
        pnDashboard.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Fetch dynamic counts from mock DB
        int countPhim = db.danhSachPhim.size();
        int countSuatChieu = db.danhSachSuatChieu.size();
        long countGheBan = db.danhSachTrangThaiGhe.stream().filter(g -> "Đã bán".equals(g.getTrangThai())).count();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.33;
        pnDashboard.add(createStatCard("Phim Đang Chiếu", String.valueOf(countPhim), Color.WHITE), gbc);
        
        gbc.gridx = 1;
        pnDashboard.add(createStatCard("Suất Chiếu Hôm Nay", String.valueOf(countSuatChieu), Color.WHITE), gbc);
        
        gbc.gridx = 2;
        pnDashboard.add(createStatCard("Vé Bán Trong Ngày", String.valueOf(countGheBan), Color.WHITE), gbc);

        // Table Lịch Chiếu
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3; gbc.weighty = 1.0;
        
        String[] cols = {"Mã Suất", "Tên Phim", "Giờ Chiếu", "Phòng", "Giá Vé"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        db.danhSachSuatChieu.forEach(sc -> {
            model.addRow(new Object[]{
                sc.getMaSuatChieu(), sc.getPhim().getTenPhim(), 
                sc.getThoiGianKhoiChieu().toLocalTime().toString(), 
                sc.getPhongChieu().getMaPhongChieu(), sc.getGiaVeCoBan() + " VNĐ"
            });
        });

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JPanel pnTableBox = new JPanel(new BorderLayout());
        pnTableBox.setBorder(BorderFactory.createTitledBorder("Lịch Chiếu Hôm Nay"));
        pnTableBox.add(new JScrollPane(table));
        
        pnDashboard.add(pnTableBox, gbc);

        return new JScrollPane(pnDashboard);
    }

    private JButton createMenuButton(String text, boolean active, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(250, 45));
        btn.setForeground(Color.WHITE);
        btn.setBackground(active ? new Color(60, 75, 90) : new Color(43, 52, 62));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if(action != null) {
            btn.addActionListener(action);
        }
        
        return btn;
    }

    private JPanel createStatCard(String title, String value, Color bg) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        card.add(new JLabel(" " + title, SwingConstants.LEFT));
        JLabel lblVal = new JLabel(value + " ", SwingConstants.RIGHT);
        lblVal.setFont(new Font("Arial", Font.BOLD, 24));
        lblVal.setForeground(new Color(229, 9, 20)); // Red accent
        card.add(lblVal);
        return card;
    }
}