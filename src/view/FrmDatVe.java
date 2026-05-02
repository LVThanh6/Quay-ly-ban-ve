package view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import controller.NhanVienController;
import model.KhachHang;
import model.PhieuDatVe;
import model.Phim;

/**
 * Màn hình Đặt Vé Rạp Chiếu Phim (Chỉ đặt chỗ, chưa thanh toán).
 */
public class FrmDatVe extends JFrame {

    // ── Constants ──────────────────────────────────────────────
    private static final int ROWS = 5;
    private static final int COLS = 10;
    private static final double PRICE_NORMAL = 75_000; // VND
    private static final double PRICE_VIP = 120_000;

    // ── Fields ─────────────────────────────────────────────────
    private final NhanVienController controller;
    private final model.NhanVien user;
    
    // DAOs for SQL persistence
    private final dao.PhieuDatVe_DAO pdvDAO = new dao.PhieuDatVe_DAO();
    private final dao.ChiTietPhieuDat_DAO ctpdvDAO = new dao.ChiTietPhieuDat_DAO();
    private final dao.Ve_DAO veDAO = new dao.Ve_DAO();
    private final dao.KhachHang_DAO khDAO = new dao.KhachHang_DAO();
    private final dao.TrangThaiGhe_DAO ttgDAO = new dao.TrangThaiGhe_DAO();
    private final dao.SanPham_DAO spDAO = new dao.SanPham_DAO();

    private DefaultTableModel modelPhim;
    private JTable tblPhim;
    private JPanel pnlSeats;
    private JLabel lblSelectedInfo;
    private JLabel lblTotalPrice;

    // Customer info
    private JTextField txtSdt;
    private JTextField txtHoTen;

    // State
    private String selectedPhim = "";
    private String selectedPhimId = "";
    private model.SuatChieu selectedSuatChieuObj = null;
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Map<String, JToggleButton> seatButtons = new LinkedHashMap<>();
    private List<model.TrangThaiGhe> listGheCuaSuat = new ArrayList<>();
    
    private JPanel pnlTimeSlots;
    private JPanel pnlRooms;
    private List<model.SuatChieu> dsSuatChieuPhimHienTai = new ArrayList<>();
    private String selectedTime = "";

    // Row labels (A-E); last row = VIP
    private static final String[] ROW_LABELS = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

    public FrmDatVe(NhanVienController controller, model.NhanVien user) {
        this.controller = controller;
        this.user = user;
        setTitle("Đặt Vé Rạp Chiếu Phim");
        setSize(1200, 680);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadPhimData();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 245, 248));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(), buildRightPanel());
        split.setResizeWeight(0.5);
        split.setDividerSize(6);
        split.setContinuousLayout(true);
        split.setBorder(null);
        root.add(split, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel hdr = new JPanel(new BorderLayout(20, 0));
        hdr.setBackground(new Color(20, 20, 30));
        hdr.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("🎬  CGV CINEMA  –  Đặt Vé");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(229, 9, 20));
        hdr.add(title, BorderLayout.WEST);

        String roleStr = (user.getVaiTro() == model.ChucVu.QUAN_LY ? "Quản Lý" : "Nhân Viên");
        JLabel userInfo = new JLabel("👤 " + user.getHoTen() + " (" + roleStr + ")");
        userInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userInfo.setForeground(Color.WHITE);
        hdr.add(userInfo, BorderLayout.EAST);

        return hdr;
    }

    private JPanel buildLeftPanel() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(16, 16, 16, 8));

        JLabel lbl = new JLabel("🎥  Phim đang chiếu");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(30, 30, 30));
        pnl.add(lbl, BorderLayout.NORTH);

        String[] cols = { "Mã Phim", "Tên Phim", "Thể loại", "Thời lượng" };
        modelPhim = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblPhim = new JTable(modelPhim);
        tblPhim.setRowHeight(32);
        tblPhim.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblPhim.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPhim.getTableHeader().setBackground(new Color(229, 9, 20));
        tblPhim.getTableHeader().setForeground(Color.BLACK);
        tblPhim.setForeground(Color.BLACK);
        tblPhim.setSelectionBackground(new Color(229, 9, 20, 60));
        tblPhim.setSelectionForeground(Color.BLACK);
        tblPhim.setGridColor(new Color(220, 220, 225));
        tblPhim.setShowGrid(true);

        JScrollPane scrollTable = new JScrollPane(tblPhim);
        scrollTable.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Panel chứa danh sách phim
        JPanel pnlPhim = new JPanel(new BorderLayout(0, 5));
        pnlPhim.setOpaque(false);
        
        JPanel pnlPhimHeader = new JPanel(new BorderLayout());
        pnlPhimHeader.setOpaque(false);
        JLabel lblPhim = new JLabel("🎥  Phim đang chiếu");
        lblPhim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlPhimHeader.add(lblPhim, BorderLayout.WEST);
        
        JButton btnRefreshPhim = new JButton("🔄 Làm mới");
        btnRefreshPhim.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnRefreshPhim.setFocusPainted(false);
        btnRefreshPhim.addActionListener(e -> loadPhimData());
        pnlPhimHeader.add(btnRefreshPhim, BorderLayout.EAST);
        
        pnlPhim.add(pnlPhimHeader, BorderLayout.NORTH);
        pnlPhim.add(scrollTable, BorderLayout.CENTER);

        // Panel Suất Chiếu & Phòng Chiếu (Thiết kế lại)
        JPanel pnlSC = new JPanel(new BorderLayout(0, 10));
        pnlSC.setOpaque(false);
        
        // Phần 1: Chọn Khung Giờ
        JPanel pnlTimeGroup = new JPanel(new BorderLayout(0, 5));
        pnlTimeGroup.setOpaque(false);
        JLabel lblTime = new JLabel("🕒  1. Chọn Khung Giờ");
        lblTime.setFont(new Font("Segoe UI", Font.BOLD, 15));
        pnlTimeGroup.add(lblTime, BorderLayout.NORTH);
        
        pnlTimeSlots = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlTimeSlots.setOpaque(false);
        JScrollPane scrollTime = new JScrollPane(pnlTimeSlots);
        scrollTime.setPreferredSize(new Dimension(0, 70));
        scrollTime.setBorder(null);
        scrollTime.setOpaque(false);
        scrollTime.getViewport().setOpaque(false);
        pnlTimeGroup.add(scrollTime, BorderLayout.CENTER);
        
        // Phần 2: Chọn Phòng Chiếu
        JPanel pnlRoomGroup = new JPanel(new BorderLayout(0, 5));
        pnlRoomGroup.setOpaque(false);
        JLabel lblRoom = new JLabel("🏢  2. Chọn Phòng Chiếu");
        lblRoom.setFont(new Font("Segoe UI", Font.BOLD, 15));
        pnlRoomGroup.add(lblRoom, BorderLayout.NORTH);
        
        pnlRooms = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        pnlRooms.setOpaque(false);
        JScrollPane scrollRoom = new JScrollPane(pnlRooms);
        scrollRoom.setPreferredSize(new Dimension(0, 90));
        scrollRoom.setBorder(null);
        scrollRoom.setOpaque(false);
        scrollRoom.getViewport().setOpaque(false);
        pnlRoomGroup.add(scrollRoom, BorderLayout.CENTER);
        
        JPanel pnlSelectionGroup = new JPanel(new GridLayout(2, 1, 0, 10));
        pnlSelectionGroup.setOpaque(false);
        pnlSelectionGroup.add(pnlTimeGroup);
        pnlSelectionGroup.add(pnlRoomGroup);
        
        pnlSC.add(pnlSelectionGroup, BorderLayout.CENTER);

        JSplitPane splitLeft = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlPhim, pnlSC);
        splitLeft.setResizeWeight(0.4);
        splitLeft.setDividerSize(6);
        splitLeft.setBorder(null);
        splitLeft.setOpaque(false);

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(splitLeft, BorderLayout.CENTER);
        pnl.add(center, BorderLayout.CENTER);

        // Khách hàng info
        JPanel pnlKhachHang = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlKhachHang.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng đặt vé"));
        pnlKhachHang.setOpaque(false);

        pnlKhachHang.add(new JLabel("Số điện thoại:"));
        txtSdt = new JTextField();
        pnlKhachHang.add(txtSdt);

        pnlKhachHang.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        pnlKhachHang.add(txtHoTen);

        pnl.add(pnlKhachHang, BorderLayout.SOUTH);

        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblPhim.getSelectedRow();
            if (row < 0) return;
            
            // Gọi reset trước để xóa sạch các lựa chọn cũ
            String newPhimId = modelPhim.getValueAt(row, 0).toString();
            String newPhimName = modelPhim.getValueAt(row, 1).toString();
            
            resetState();
            
            selectedPhimId = newPhimId;
            selectedPhim = newPhimName;
            
            loadSuatChieuData();
            renderTimeSlots();
        });

        return pnl;
    }

    private JPanel buildRightPanel() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(16, 8, 16, 16));

        JLabel lbl = new JLabel("🪑  Sơ đồ ghế ngồi");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(30, 30, 30));
        pnl.add(lbl, BorderLayout.NORTH);

        JLabel screen = new JLabel("▬▬▬▬▬  MÀN HÌNH  ▬▬▬▬▬", JLabel.CENTER);
        screen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        screen.setForeground(new Color(229, 9, 20));
        screen.setBorder(new MatteBorder(0, 0, 3, 0, new Color(229, 9, 20)));
        screen.setPreferredSize(new Dimension(0, 34));

        pnlSeats = new JPanel(new GridLayout(ROWS, COLS, 6, 6));
        pnlSeats.setOpaque(false);
        pnlSeats.setBorder(new EmptyBorder(10, 0, 10, 0));
        buildDynamicSeatGrid();

        JPanel legend = buildLegend();

        JPanel seatArea = new JPanel(new BorderLayout(0, 6));
        seatArea.setOpaque(false);
        seatArea.add(screen, BorderLayout.NORTH);
        seatArea.add(pnlSeats, BorderLayout.CENTER);
        seatArea.add(legend, BorderLayout.SOUTH);
        pnl.add(seatArea, BorderLayout.CENTER);

        pnl.add(buildBottomPanel(), BorderLayout.SOUTH);

        return pnl;
    }

    private void buildDynamicSeatGrid() {
        pnlSeats.removeAll();
        seatButtons.clear();

        if (listGheCuaSuat == null || listGheCuaSuat.isEmpty()) {
            pnlSeats.revalidate();
            pnlSeats.repaint();
            return;
        }

        int totalSeats = listGheCuaSuat.size();
        int cols = 10;
        int rows = (int) Math.ceil((double) totalSeats / cols);
        pnlSeats.setLayout(new GridLayout(rows, cols, 6, 6));

        for (model.TrangThaiGhe ttg : listGheCuaSuat) {
            String seatId = ttg.getGhe().getMaGhe();
            boolean isVip = seatId.contains("VIP") || seatId.startsWith("E") || seatId.startsWith("F");
            boolean daBan = "Đã bán".equalsIgnoreCase(ttg.getTrangThai());

            double seatPrice = (selectedSuatChieuObj != null) ? selectedSuatChieuObj.getGiaVeCoBan() : 0;
            if (isVip) seatPrice *= 1.2;

            String btnText = "<html><center>" + seatId + "<br><small>" + String.format("%,.0f", seatPrice) + "</small></center></html>";
            JToggleButton btn = new JToggleButton(btnText);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btn.setFocusPainted(false);
            btn.setMargin(new Insets(2, 2, 2, 2));
            btn.setPreferredSize(new Dimension(46, 36));
            btn.setCursor(daBan ? Cursor.getDefaultCursor() : Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            if (daBan) {
                btn.setEnabled(false);
                btn.setBackground(new Color(140, 140, 150));
                btn.setForeground(Color.WHITE);
            } else {
                setSeatDefault(btn, isVip);
            }

            btn.addItemListener(ev -> {
                if (!btn.isEnabled()) return;
                if (btn.isSelected()) {
                    btn.setBackground(new Color(229, 9, 20));
                    btn.setForeground(Color.BLACK);
                    selectedSeats.add(seatId);
                } else {
                    setSeatDefault(btn, isVip);
                    selectedSeats.remove(seatId);
                }
                refreshSummary();
            });

            seatButtons.put(seatId, btn);
            pnlSeats.add(btn);
        }

        pnlSeats.revalidate();
        pnlSeats.repaint();
    }

    private void loadTrangThaiGhe() {
        if (selectedSuatChieuObj == null) {
            listGheCuaSuat.clear();
            return;
        }
        listGheCuaSuat = controller.getTrangThaiGheBySuatChieu(selectedSuatChieuObj.getMaSuatChieu());
    }

    private void refreshSummary() {
        if (selectedSeats.isEmpty()) {
            lblSelectedInfo.setText("Chưa chọn ghế nào");
        } else {
            lblSelectedInfo.setText("Ghế: " + String.join(", ", selectedSeats));
        }
        lblTotalPrice.setText(String.format("Tạm tính: %,.0f VND", calcTotal()));
    }

    private void loadSuatChieuData() {
        dsSuatChieuPhimHienTai.clear();
        if (selectedPhimId.isEmpty()) return;
        List<model.SuatChieu> ds = controller.getSuatChieuByPhim(selectedPhimId);
        if (ds != null) {
            dsSuatChieuPhimHienTai.addAll(ds);
        }
    }

    private void renderTimeSlots() {
        pnlTimeSlots.removeAll();
        Set<String> times = new TreeSet<>();
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
        
        for (model.SuatChieu sc : dsSuatChieuPhimHienTai) {
            if (sc.getThoiGianKhoiChieu() != null) {
                times.add(sc.getThoiGianKhoiChieu().format(dtf));
            }
        }
        
        if (times.isEmpty()) {
            pnlTimeSlots.add(new JLabel("<html><i>Không có suất chiếu nào cho phim này</i></html>"));
        }
        
        for (String t : times) {
            JButton btn = new JButton(t);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setPreferredSize(new Dimension(85, 40));
            btn.setBackground(new Color(245, 245, 250));
            btn.setForeground(new Color(50, 50, 50));
            btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            btn.addActionListener(e -> {
                for (Component c : pnlTimeSlots.getComponents()) {
                    if (c instanceof JButton) {
                        c.setBackground(new Color(245, 245, 250));
                        c.setForeground(new Color(50, 50, 50));
                    }
                }
                btn.setBackground(new Color(229, 9, 20));
                btn.setForeground(Color.WHITE);
                
                selectedTime = t;
                renderRooms(t);
            });
            pnlTimeSlots.add(btn);
        }
        pnlTimeSlots.revalidate();
        pnlTimeSlots.repaint();
    }

    private void renderRooms(String time) {
        pnlRooms.removeAll();
        selectedSuatChieuObj = null;
        pnlSeats.removeAll();
        pnlSeats.revalidate();
        pnlSeats.repaint();
        
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("HH:mm");
        for (model.SuatChieu sc : dsSuatChieuPhimHienTai) {
            String scTime = sc.getThoiGianKhoiChieu() != null ? sc.getThoiGianKhoiChieu().format(dtf) : "";
            if (scTime.equals(time)) {
                // Tính số ghế trống/tổng số ghế
                List<model.TrangThaiGhe> ttgs = controller.getTrangThaiGheBySuatChieu(sc.getMaSuatChieu());
                int total = ttgs.size();
                int emptyCount = 0;
                for (model.TrangThaiGhe t : ttgs) {
                    if ("Trống".equalsIgnoreCase(t.getTrangThai())) emptyCount++;
                }

                String roomName = sc.getPhongChieu() != null ? sc.getPhongChieu().getMaPhongChieu() : "Phòng ??";
                JButton btn = new JButton("<html><center><b>" + roomName + "</b><br><small>Trống: " + emptyCount + "/" + total + "</small></center></html>");
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                btn.setPreferredSize(new Dimension(140, 60));
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(30, 30, 30));
                btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 225), 1));
                btn.setFocusPainted(false);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                btn.addActionListener(e -> {
                    for (Component c : pnlRooms.getComponents()) {
                        if (c instanceof JButton) {
                            c.setBackground(Color.WHITE);
                        }
                    }
                    btn.setBackground(new Color(255, 193, 7));
                    
                    selectedSuatChieuObj = sc;
                    loadTrangThaiGhe();
                    buildDynamicSeatGrid();
                    refreshSummary();
                });
                pnlRooms.add(btn);
            }
        }
        pnlRooms.revalidate();
        pnlRooms.repaint();
    }

    private void setSeatDefault(JToggleButton btn, boolean vip) {
        btn.setOpaque(true);
        if (vip) {
            btn.setBackground(new Color(255, 210, 60));
            btn.setForeground(Color.BLACK);
        } else {
            btn.setBackground(new Color(210, 215, 225));
            btn.setForeground(Color.BLACK);
        }
    }

    private JPanel buildLegend() {
        JPanel leg = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 4));
        leg.setOpaque(false);
        leg.add(legendItem(new Color(210, 215, 225), "Ghế thường"));
        leg.add(legendItem(new Color(255, 210, 60), "Ghế VIP"));
        leg.add(legendItem(new Color(229, 9, 20), "Đã chọn"));
        leg.add(legendItem(new Color(140, 140, 150), "Đã đặt"));
        return leg;
    }

    private JPanel legendItem(Color color, String text) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        JLabel box = new JLabel("   ");
        box.setOpaque(true);
        box.setBackground(color);
        box.setBorder(BorderFactory.createLineBorder(color.darker(), 1));
        JLabel txt = new JLabel(text);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        p.add(box);
        p.add(txt);
        return p;
    }

    private JPanel buildBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout(0, 8));
        bottom.setOpaque(false);
        bottom.setBorder(new MatteBorder(1, 0, 0, 0, new Color(200, 200, 205)));

        lblSelectedInfo = new JLabel("Chưa chọn ghế nào");
        lblSelectedInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSelectedInfo.setForeground(new Color(80, 80, 90));

        lblTotalPrice = new JLabel("Tổng tiền tạm tính: 0 VND");
        lblTotalPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalPrice.setForeground(new Color(20, 20, 20));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(8, 0, 6, 0));
        infoPanel.add(lblSelectedInfo);
        infoPanel.add(lblTotalPrice);

        JButton btnConfirm = new JButton("TẠO PHIẾU ĐẶT VÉ");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirm.setBackground(Color.WHITE);
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setContentAreaFilled(false);
        btnConfirm.setOpaque(true);
        btnConfirm.setPreferredSize(new Dimension(160, 52));
        btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnConfirm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConfirm.setBackground(new Color(229, 9, 20));
                btnConfirm.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnConfirm.setBackground(Color.WHITE);
                btnConfirm.setForeground(Color.BLACK);
            }
        });
        btnConfirm.addActionListener(e -> showOrderDialog());

        JButton btnDirectSale = new JButton("BÁN TRỰC TIẾP");
        btnDirectSale.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDirectSale.setBackground(new Color(40, 167, 69)); // Màu xanh lá cây
        btnDirectSale.setForeground(Color.WHITE);
        btnDirectSale.setBorder(BorderFactory.createLineBorder(new Color(40, 167, 69), 2));
        btnDirectSale.setFocusPainted(false);
        btnDirectSale.setContentAreaFilled(false);
        btnDirectSale.setOpaque(true);
        btnDirectSale.setPreferredSize(new Dimension(160, 52));
        btnDirectSale.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnDirectSale.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDirectSale.setBackground(new Color(33, 136, 56));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnDirectSale.setBackground(new Color(40, 167, 69));
            }
        });
        btnDirectSale.addActionListener(e -> showDirectSaleDialog());

        JPanel pnlActionButtons = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlActionButtons.setOpaque(false);
        pnlActionButtons.add(btnConfirm);
        pnlActionButtons.add(btnDirectSale);

        bottom.add(infoPanel, BorderLayout.CENTER);
        bottom.add(pnlActionButtons, BorderLayout.SOUTH);
        return bottom;
    }

    private void showOrderDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(pnlSeats);
        
        if (selectedPhim.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn một bộ phim!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn ít nhất 1 ghế!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Kiểm tra giới hạn độ tuổi
        if (!checkAge(parentWindow)) return;
        
        if (txtSdt.getText().trim().isEmpty() || txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng nhập đủ SĐT và Họ tên khách hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedSuatChieuObj == null) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn một suất chiếu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double basePrice = selectedSuatChieuObj.getGiaVeCoBan();
        double total = calcTotal();

        JDialog dlg = new JDialog(parentWindow, "Xác nhận đặt vé", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(460, 450);
        dlg.setLocationRelativeTo(parentWindow);
        dlg.setResizable(false);

        JPanel content = new JPanel(new BorderLayout(0, 0));
        content.setBackground(Color.WHITE);

        JLabel dlgTitle = new JLabel("🎟  Chi tiết phiếu đặt", JLabel.CENTER);
        dlgTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        dlgTitle.setForeground(Color.WHITE);
        dlgTitle.setOpaque(true);
        dlgTitle.setBackground(new Color(229, 9, 20));
        dlgTitle.setBorder(new EmptyBorder(14, 0, 14, 0));
        content.add(dlgTitle, BorderLayout.NORTH);

        JPanel info = new JPanel(new GridBagLayout());
        info.setBackground(Color.WHITE);
        info.setBorder(new EmptyBorder(20, 30, 10, 30));

        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.WEST;
        lc.insets = new Insets(6, 0, 6, 14);
        GridBagConstraints vc = new GridBagConstraints();
        vc.anchor = GridBagConstraints.WEST;
        vc.weightx = 1;
        vc.fill = GridBagConstraints.HORIZONTAL;
        vc.insets = new Insets(6, 0, 6, 0);
        vc.gridwidth = GridBagConstraints.REMAINDER;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        String[][] rows2 = {
                { "👤  Khách hàng:", txtHoTen.getText().trim() + " (" + txtSdt.getText().trim() + ")" },
                { "🎬  Tên phim:", selectedPhim },
                { "🪑  Ghế đã chọn:", "<html><div style='width: 220px;'>" + String.join(", ", selectedSeats) + "</div></html>" },
                { "🎫  Số lượng vé:", String.valueOf(selectedSeats.size()) },
                { "⏰  Thời gian:", new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) },
                { "🤵  Nhân viên:", user.getHoTen() },
                { "💰  Tạm tính:", String.format("%,.0f VND", total) }
        };

        for (String[] row : rows2) {
            JLabel key = new JLabel(row[0]);
            key.setFont(labelFont);
            key.setForeground(new Color(80, 80, 90));

            JLabel val = new JLabel(row[1]);
            val.setFont(row[0].startsWith("💰") ? new Font("Segoe UI", Font.BOLD, 16) : valueFont);
            val.setForeground(row[0].startsWith("💰") ? new Color(229, 9, 20) : new Color(20, 20, 20));

            lc.gridx = 0;
            lc.gridy = GridBagConstraints.RELATIVE;
            info.add(key, lc);
            vc.gridx = 1;
            info.add(val, vc);
        }

        content.add(info, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(0, 30, 24, 30));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(240, 240, 245));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
        btnCancel.addActionListener(e -> dlg.dispose());

        JButton btnPay = new JButton("Xác nhận đặt");
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPay.setBackground(new Color(229, 9, 20));
        btnPay.setForeground(Color.BLACK);
        btnPay.setFocusPainted(false);
        btnPay.setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 1));
        btnPay.addActionListener(e -> {
            dlg.dispose();
            
            // 1. Xử lý Khách hàng
            String sdt = txtSdt.getText().trim();
            KhachHang kh = khDAO.getKhachHangBySdt(sdt);
            if (kh == null) {
                kh = new KhachHang(sdt, txtHoTen.getText().trim(), null);
                khDAO.addKhachHang(kh);
            }
            
            // 2. Tạo PhieuDatVe SQL
            String pdvId = "PDV" + (System.currentTimeMillis() % 1000000);
            PhieuDatVe pdv = new PhieuDatVe(pdvId, selectedSeats.size(), new Date(), new Date(System.currentTimeMillis() + 86400000L), "Chờ thanh toán", kh, user);
            pdvDAO.addPhieuDatVe(pdv);
            
            // 3. Tạo Vé & Chi Tiết Phiếu Đặt cho từng ghế
            int ctCounter = 1;
            for (String seatId : selectedSeats) {
                // Tạo Vé mới cho mỗi ghế
                String veId = "VE" + (System.currentTimeMillis() % 1000000) + ctCounter;
                model.TrangThaiGhe currentTTG = null;
                for (model.TrangThaiGhe ttg : listGheCuaSuat) {
                    if (ttg.getGhe().getMaGhe().equals(seatId)) {
                        currentTTG = ttg;
                        break;
                    }
                }
                
                if (currentTTG != null) {
                    // Thêm vào bảng SanPham trước do ràng buộc khóa ngoại
                    spDAO.addSanPham(veId, "VE");
                    
                    model.Ve ve = new model.Ve();
                    ve.setMaSanPham(veId);
                    ve.setTrangThaiGhe(currentTTG);
                    ve.setTrangThai("Chờ thanh toán");
                    ve.setGiaVeThucTe(currentTTG.getGhe().getMaGhe().startsWith("VIP") ? PRICE_VIP : PRICE_NORMAL);
                    veDAO.addVe(ve);
                    
                    // Tạo Chi Tiết Phiếu Đặt
                    model.ChiTietPhieuDat ct = new model.ChiTietPhieuDat();
                    ct.setMaChiTietPhieuDat("CTPDV" + pdvId + "_" + ctCounter);
                    ct.setPhieuDatVe(pdv);
                    ct.setVe(ve);
                    ct.setGiaTamTinh(ve.getGiaVeThucTe());
                    ctpdvDAO.addChiTietPhieuDat(ct);
                    
                    // Cập nhật trạng thái ghế trong SQL
                    currentTTG.setTrangThai("Đã đặt");
                    ttgDAO.updateTrangThai(currentTTG);
                }
                ctCounter++;
            }
            
            JOptionPane.showMessageDialog(parentWindow,
                    "✅  Đặt vé thành công và đã lưu vào SQL!\nMã phiếu đặt: " + pdvId + "\nVui lòng hướng dẫn khách hàng thanh toán khi đến rạp.",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            resetState();
        });

        btnPanel.add(btnCancel);
        btnPanel.add(btnPay);
        content.add(btnPanel, BorderLayout.SOUTH);

        dlg.setContentPane(content);
        dlg.setVisible(true);
    }

    private void showDirectSaleDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(pnlSeats);
        
        if (selectedPhim.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn một bộ phim!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn ít nhất 1 ghế!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedSuatChieuObj == null) {
            JOptionPane.showMessageDialog(parentWindow, "Vui lòng chọn một suất chiếu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra giới hạn độ tuổi
        if (!checkAge(parentWindow)) return;

        int confirm = JOptionPane.showConfirmDialog(parentWindow, 
            "Tiếp tục chuyển sang Quầy Thanh Toán (POS) để bán trực tiếp?\nBạn có thể thêm Combo Bắp Nước ở bước tiếp theo.",
            "Xác nhận Bán Vé", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Xử lý Khách hàng vãng lai
            String sdt = "0000000000"; // Default for walk-in
            KhachHang kh = khDAO.getKhachHangBySdt(sdt);
            if (kh == null) {
                kh = new KhachHang(sdt, "Khách Vãng Lai", null);
                khDAO.addKhachHang(kh);
            }

            // 2. Tạo PhieuDatVe SQL (Bán trực tiếp cũng tạo phiếu để POS xử lý)
            String pdvId = "VL_" + (System.currentTimeMillis() % 1000000);
            PhieuDatVe pdv = new PhieuDatVe(pdvId, selectedSeats.size(), new Date(), new Date(), "Chờ thanh toán", kh, user);
            pdv.setTrangThai("Bán Trực Tiếp\nPhim: " + selectedPhim + "\nGhế: " + String.join(", ", selectedSeats));
            pdvDAO.addPhieuDatVe(pdv);

            // 3. Tạo Vé & Chi Tiết SQL
            int ctCounter = 1;
            for (String seatId : selectedSeats) {
                String veId = "VE_VL_" + (System.currentTimeMillis() % 1000000) + ctCounter;
                model.TrangThaiGhe currentTTG = null;
                for (model.TrangThaiGhe ttg : listGheCuaSuat) {
                    if (ttg.getGhe().getMaGhe().equals(seatId)) {
                        currentTTG = ttg;
                        break;
                    }
                }
                
                if (currentTTG != null) {
                    // Thêm vào bảng SanPham trước do ràng buộc khóa ngoại
                    spDAO.addSanPham(veId, "VE");
                    
                    model.Ve ve = new model.Ve();
                    ve.setMaSanPham(veId);
                    ve.setTrangThaiGhe(currentTTG);
                    ve.setTrangThai("Bán trực tiếp");
                    ve.setGiaVeThucTe(currentTTG.getGhe().getMaGhe().startsWith("VIP") ? PRICE_VIP : PRICE_NORMAL);
                    veDAO.addVe(ve);
                    
                    model.ChiTietPhieuDat ct = new model.ChiTietPhieuDat();
                    ct.setMaChiTietPhieuDat("CT_VL_" + pdvId + "_" + ctCounter);
                    ct.setPhieuDatVe(pdv);
                    ct.setVe(ve);
                    ct.setGiaTamTinh(ve.getGiaVeThucTe());
                    ctpdvDAO.addChiTietPhieuDat(ct);
                    
                    currentTTG.setTrangThai("Đã bán");
                    ttgDAO.updateTrangThai(currentTTG);
                }
                ctCounter++;
            }

            SharedData.directSaleSearchKey = pdvId;
            resetState();

            // Chuyển sang tab Bán Vé
            if (parentWindow instanceof MainGUI) {
                ((MainGUI) parentWindow).switchToBanVe();
            }
        }
    }

    private double calcTotal() {
        if (selectedSuatChieuObj == null) return 0;
        double basePrice = selectedSuatChieuObj.getGiaVeCoBan();
        double total = 0;
        for (String seatId : selectedSeats) {
            boolean vip = seatId.startsWith("E") || seatId.startsWith("F");
            total += vip ? (basePrice * 1.2) : basePrice; // Giả sử VIP đắt hơn 20%
        }
        return total;
    }

    private void resetState() {
        selectedSeats.clear();
        selectedPhim = "";
        selectedPhimId = "";
        selectedSuatChieuObj = null;
        selectedTime = "";
        txtSdt.setText("");
        txtHoTen.setText("");
        tblPhim.clearSelection();
        
        if (pnlTimeSlots != null) {
            pnlTimeSlots.removeAll();
            pnlTimeSlots.revalidate();
            pnlTimeSlots.repaint();
        }
        if (pnlRooms != null) {
            pnlRooms.removeAll();
            pnlRooms.revalidate();
            pnlRooms.repaint();
        }
        if (pnlSeats != null) {
            pnlSeats.removeAll();
            pnlSeats.revalidate();
            pnlSeats.repaint();
        }
        refreshSummary();
    }

    private boolean checkAge(Window parentWindow) {
        if (selectedSuatChieuObj == null) return true;
        int limit = selectedSuatChieuObj.getPhim().getGioiHan();
        if (limit <= 0) return true;

        String sdt = txtSdt.getText().trim();
        int finalAge = -1;

        if (!sdt.isEmpty()) {
            model.KhachHang kh = khDAO.getKhachHangBySdt(sdt);
            if (kh != null && kh.getNgaySinh() != null) {
                java.util.Calendar birth = java.util.Calendar.getInstance();
                birth.setTime(kh.getNgaySinh());
                java.util.Calendar today = java.util.Calendar.getInstance();
                finalAge = today.get(java.util.Calendar.YEAR) - birth.get(java.util.Calendar.YEAR);
                if (today.get(java.util.Calendar.DAY_OF_YEAR) < birth.get(java.util.Calendar.DAY_OF_YEAR)) {
                    finalAge--;
                }
                
                // Kiểm tra im lặng, chỉ báo lỗi nếu không đủ tuổi
            }
        }

        if (finalAge == -1) {
            String ageStr = JOptionPane.showInputDialog(parentWindow, 
                "Phim yêu cầu độ tuổi " + limit + "+\nKhông tìm thấy thông tin khách hàng, vui lòng nhập tuổi:", 
                "Xác minh độ tuổi thủ công", JOptionPane.QUESTION_MESSAGE);
            if (ageStr == null) return false;
            try {
                finalAge = Integer.parseInt(ageStr.trim());
            } catch (Exception e) {
                return false;
            }
        }

        if (finalAge < limit) {
            JOptionPane.showMessageDialog(parentWindow, 
                "Khách hàng không đủ tuổi xem phim này (Yêu cầu " + limit + "+)!", 
                "Từ chối", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void loadPhimData() {
        try {
            List<Phim> ds = controller.xemLichChieu();
            modelPhim.setRowCount(0);
            if (ds != null && !ds.isEmpty()) {
                for (Phim p : ds) {
                    modelPhim.addRow(new Object[] {
                            p.getMaPhim(),
                            p.getTenPhim(),
                            p.getLoaiPhim() != null ? p.getLoaiPhim() : "—",
                            p.getThoiLuongPhim() > 0 ? p.getThoiLuongPhim() + " phút" : "—"
                    });
                }
            } else {
                addMockPhim();
            }
        } catch (Exception e) {
            addMockPhim();
        }
    }
    
    private void addMockPhim() {
        modelPhim.setRowCount(0);
        modelPhim.addRow(new Object[]{"PHIM01", "Lật Mặt 7: Một Điều Ước", "Tình cảm, Gia đình", "138 phút"});
        modelPhim.addRow(new Object[]{"PHIM02", "Dune: Hành Tinh Cát - Phần 2", "Hành động, Viễn tưởng", "166 phút"});
    }
}
