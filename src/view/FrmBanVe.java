package view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import controller.NhanVienController;
import model.Phim;

/**
 * Màn hình Bán Vé Rạp Chiếu Phim.
 * Giao diện chia đôi 5:5 (JSplitPane):
 * - Trái : poster + danh sách phim (JTable)
 * - Phải : sơ đồ ghế (GridLayout) + nút Xác nhận đặt vé
 */
public class FrmBanVe extends JFrame {

    // ── Constants ──────────────────────────────────────────────
    private static final String IMG_DIR = "res/images/";
    private static final String[] POSTER_FILES = {
            "phim (1).jpg", "phim (2).jpg", "phim (3).jpg", "phim (4).jpg", "phim (5).jpg",
            "phim (6).jpg", "phim (7).jpg", "phim (8).jpg", "phim (9).jpg", "phim (10).jpg"
    };
    private static final int ROWS = 5;
    private static final int COLS = 10;
    private static final double PRICE_NORMAL = 75_000; // VND
    private static final double PRICE_VIP = 120_000;

    // ── Fields ─────────────────────────────────────────────────
    private final NhanVienController controller;
    private DefaultTableModel modelPhim;
    private JTable tblPhim;
    private JLabel lblPoster;
    private JPanel pnlSeats;
    private JLabel lblSelectedInfo; // right panel seat summary
    private JLabel lblTotalPrice;

    // State
    private String selectedPhim = "";
    private final Set<String> selectedSeats = new LinkedHashSet<>();
    private final Set<String> bookedSeats = new LinkedHashSet<>(); // Thêm danh sách ghế đã đặt
    private final Map<String, JToggleButton> seatButtons = new LinkedHashMap<>();

    // Row labels (A-E); last row = VIP
    private static final String[] ROW_LABELS = { "A", "B", "C", "D", "E" };

    // ─────────────────────────────────────────────────────────────
    public FrmBanVe(NhanVienController controller) {
        this.controller = controller;
        setTitle("Bán Vé Rạp Chiếu Phim");
        setSize(1200, 680);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadPhimData();
    }

    // ── UI Builder ────────────────────────────────────────────
    private void initUI() {
        // Root
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 245, 248));
        setContentPane(root);

        // Header bar
        root.add(buildHeader(), BorderLayout.NORTH);

        // === JSplitPane left : right = 5 : 5 ===
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(), buildRightPanel());
        split.setResizeWeight(0.5); // 50 / 50
        split.setDividerSize(6);
        split.setContinuousLayout(true);
        split.setBorder(null);
        root.add(split, BorderLayout.CENTER);
    }

    // ── Header ───────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel hdr = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        hdr.setBackground(new Color(20, 20, 30));
        JLabel title = new JLabel("🎬  CGV CINEMA  –  Bán Vé");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(229, 9, 20));
        hdr.add(title);
        return hdr;
    }

    // ── LEFT PANEL : Phim ────────────────────────────────────
    private JPanel buildLeftPanel() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(16, 16, 16, 8));

        // Title
        JLabel lbl = new JLabel("🎥  Phim đang chiếu");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(30, 30, 30));
        pnl.add(lbl, BorderLayout.NORTH);

        // Poster
        lblPoster = new JLabel("Chọn phim để xem poster", JLabel.CENTER);
        lblPoster.setPreferredSize(new Dimension(0, 230));
        lblPoster.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblPoster.setForeground(new Color(160, 160, 160));
        lblPoster.setOpaque(true);
        lblPoster.setBackground(new Color(230, 230, 235));
        lblPoster.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblPoster.setHorizontalTextPosition(JLabel.CENTER);
        lblPoster.setVerticalTextPosition(JLabel.CENTER);

        // Table
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

        // Center area: poster + table stacked
        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.setOpaque(false);
        center.add(lblPoster, BorderLayout.NORTH);
        center.add(scrollTable, BorderLayout.CENTER);
        pnl.add(center, BorderLayout.CENTER);

        // Selection listener
        tblPhim.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;
            int row = tblPhim.getSelectedRow();
            if (row < 0)
                return;
            selectedPhim = modelPhim.getValueAt(row, 1).toString();
            loadPoster(row);
            refreshSummary();
        });

        return pnl;
    }

    // ── RIGHT PANEL : Sơ đồ ghế ─────────────────────────────
    private JPanel buildRightPanel() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(16, 8, 16, 16));

        // Title
        JLabel lbl = new JLabel("🪑  Sơ đồ ghế ngồi");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(30, 30, 30));
        pnl.add(lbl, BorderLayout.NORTH);

        // Screen label
        JLabel screen = new JLabel("▬▬▬▬▬  MÀN HÌNH  ▬▬▬▬▬", JLabel.CENTER);
        screen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        screen.setForeground(new Color(229, 9, 20));
        screen.setBorder(new MatteBorder(0, 0, 3, 0, new Color(229, 9, 20)));
        screen.setPreferredSize(new Dimension(0, 34));

        // Seat grid
        pnlSeats = new JPanel(new GridLayout(ROWS, COLS, 6, 6));
        pnlSeats.setOpaque(false);
        pnlSeats.setBorder(new EmptyBorder(10, 0, 10, 0));
        buildSeatGrid();

        // Legend
        JPanel legend = buildLegend();

        // Seat area wrapper
        JPanel seatArea = new JPanel(new BorderLayout(0, 6));
        seatArea.setOpaque(false);
        seatArea.add(screen, BorderLayout.NORTH);
        seatArea.add(pnlSeats, BorderLayout.CENTER);
        seatArea.add(legend, BorderLayout.SOUTH);
        pnl.add(seatArea, BorderLayout.CENTER);

        // Bottom: summary + confirm button
        pnl.add(buildBottomPanel(), BorderLayout.SOUTH);

        return pnl;
    }

    private void buildSeatGrid() {
        pnlSeats.removeAll();
        seatButtons.clear();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 1; c <= COLS; c++) {
                String seatId = ROW_LABELS[r] + c;
                boolean isVip = (r == ROWS - 1); // Row E = VIP

                JToggleButton btn = new JToggleButton(seatId);
                btn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                btn.setFocusPainted(false);
                btn.setMargin(new Insets(2, 2, 2, 2));
                btn.setPreferredSize(new Dimension(46, 36));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setSeatDefault(btn, isVip);
                btn.setToolTipText(isVip ? "Ghế VIP – " + (int) PRICE_VIP + " VND"
                        : "Ghế thường – " + (int) PRICE_NORMAL + " VND");

                btn.addItemListener(ev -> {
                    if (bookedSeats.contains(seatId)) return; // Bỏ qua nếu ghế đã được đặt
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
        }
        pnlSeats.revalidate();
        pnlSeats.repaint();
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

        // Selected seat summary
        lblSelectedInfo = new JLabel("Chưa chọn ghế nào");
        lblSelectedInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSelectedInfo.setForeground(new Color(80, 80, 90));

        lblTotalPrice = new JLabel("Tổng tiền: 0 VND");
        lblTotalPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalPrice.setForeground(new Color(20, 20, 20));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(8, 0, 6, 0));
        infoPanel.add(lblSelectedInfo);
        infoPanel.add(lblTotalPrice);

        // Confirm button – WHITE background, RED border (theo yêu cầu)
        JButton btnConfirm = new JButton("XÁC NHẬN ĐẶT VÉ");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConfirm.setBackground(Color.WHITE);
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 2));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setContentAreaFilled(false);
        btnConfirm.setOpaque(true);
        btnConfirm.setPreferredSize(new Dimension(0, 52));
        btnConfirm.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
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

        bottom.add(infoPanel, BorderLayout.CENTER);
        bottom.add(btnConfirm, BorderLayout.SOUTH);
        return bottom;
    }

    // ── Order Dialog ─────────────────────────────────────────
    private void showOrderDialog() {
        Window parentWindow = SwingUtilities.getWindowAncestor(pnlSeats);
        
        if (selectedPhim.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow,
                    "Vui lòng chọn một bộ phim!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(parentWindow,
                    "Vui lòng chọn ít nhất 1 ghế!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = calcTotal();

        // ── Build JDialog ──────────────────────────────────
        JDialog dlg = new JDialog(parentWindow, "Chi tiết đơn hàng", Dialog.ModalityType.APPLICATION_MODAL);
        dlg.setSize(460, 380);
        dlg.setLocationRelativeTo(parentWindow);
        dlg.setResizable(false);

        JPanel content = new JPanel(new BorderLayout(0, 0));
        content.setBackground(Color.WHITE);

        // Header
        JLabel dlgTitle = new JLabel("🎟  Chi tiết đơn hàng", JLabel.CENTER);
        dlgTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        dlgTitle.setForeground(Color.WHITE);
        dlgTitle.setOpaque(true);
        dlgTitle.setBackground(new Color(229, 9, 20));
        dlgTitle.setBorder(new EmptyBorder(14, 0, 14, 0));
        content.add(dlgTitle, BorderLayout.NORTH);

        // Info area
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
                { "🎬  Tên phim:", selectedPhim },
                { "🪑  Ghế đã chọn:", "<html><div style='width: 220px;'>" + String.join(", ", selectedSeats) + "</div></html>" },
                { "🎫  Số lượng vé:", String.valueOf(selectedSeats.size()) },
                { "💰  Tổng tiền:", String.format("%,.0f VND", total) }
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

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new EmptyBorder(0, 30, 24, 30));

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(240, 240, 245));
        btnCancel.setForeground(Color.BLACK);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210), 1));
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dlg.dispose());

        JButton btnPay = new JButton("Thanh toán");
        btnPay.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPay.setBackground(new Color(229, 9, 20));
        btnPay.setForeground(Color.BLACK);
        btnPay.setFocusPainted(false);
        btnPay.setBorder(BorderFactory.createLineBorder(new Color(229, 9, 20), 1));
        btnPay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPay.addActionListener(e -> {
            dlg.dispose();
            bookedSeats.addAll(selectedSeats); // Lưu các ghế đã đặt
            JOptionPane.showMessageDialog(parentWindow,
                    "✅  Thanh toán thành công!\nCảm ơn bạn đã đặt vé tại CGV Cinema.",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            resetState();
        });

        btnPanel.add(btnCancel);
        btnPanel.add(btnPay);
        content.add(btnPanel, BorderLayout.SOUTH);

        dlg.setContentPane(content);
        dlg.setVisible(true);
    }

    // ── Helpers ───────────────────────────────────────────────
    private double calcTotal() {
        double total = 0;
        for (String seat : selectedSeats) {
            boolean vip = seat.startsWith("E");
            total += vip ? PRICE_VIP : PRICE_NORMAL;
        }
        return total;
    }

    private void refreshSummary() {
        if (selectedSeats.isEmpty()) {
            lblSelectedInfo.setText("Chưa chọn ghế nào");
        } else {
            lblSelectedInfo.setText("Ghế: " + String.join(", ", selectedSeats));
        }
        lblTotalPrice.setText(String.format("Tổng tiền: %,.0f VND", calcTotal()));
    }

    private void loadPoster(int rowIndex) {
        int idx = rowIndex % POSTER_FILES.length;
        File f = new File(IMG_DIR + POSTER_FILES[idx]);
        if (f.exists()) {
            ImageIcon raw = new ImageIcon(f.getAbsolutePath());
            int w = lblPoster.getWidth() > 0 ? lblPoster.getWidth() : 300;
            int h = 230;
            Image scaled = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lblPoster.setIcon(new ImageIcon(scaled));
            lblPoster.setText("");
        } else {
            lblPoster.setIcon(null);
            lblPoster.setText("Không có ảnh poster");
        }
    }

    private void resetState() {
        selectedSeats.clear();
        selectedPhim = "";
        tblPhim.clearSelection();
        lblPoster.setIcon(null);
        lblPoster.setText("Chọn phim để xem poster");
        for (Map.Entry<String, JToggleButton> en : seatButtons.entrySet()) {
            String seatId = en.getKey();
            JToggleButton btn = en.getValue();
            boolean vip = seatId.startsWith("E");
            
            if (bookedSeats.contains(seatId)) {
                btn.setSelected(true);
                btn.setBackground(new Color(140, 140, 150)); // Màu ghế đã đặt
                btn.setForeground(Color.WHITE);
                btn.setEnabled(false); // Vô hiệu hóa ghế đã đặt
            } else {
                btn.setSelected(false);
                btn.setEnabled(true);
                setSeatDefault(btn, vip);
            }
        }
        refreshSummary();
    }

    private void loadPhimData() {
        List<Phim> ds = controller.xemLichChieu();
        modelPhim.setRowCount(0);
        for (Phim p : ds) {
            modelPhim.addRow(new Object[] {
                    p.getMaPhim(),
                    p.getTenPhim(),
                    p.getLoaiPhim() != null ? p.getLoaiPhim() : "—",
                    p.getThoiLuongPhim() > 0 ? p.getThoiLuongPhim() + " phút" : "—"
            });
        }
    }
}
