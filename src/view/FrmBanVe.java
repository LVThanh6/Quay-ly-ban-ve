package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import controller.NhanVienController;
import dao.ComboBapNuoc_DAO;
import model.ComboBapNuoc;
import model.PhieuDatVe;
import util.PrintUtility;
import model.VeFullInfo;

/**
 * Màn hình Bán Vé / Thanh Toán (POS).
 * Xử lý thanh toán vé đã đặt và bán trực tiếp Combo.
 */
public class FrmBanVe extends JFrame {

    private final NhanVienController controller;
    private final model.NhanVien user;
    private final ComboBapNuoc_DAO comboDAO;
    
    // DAOs for SQL persistence
    private final dao.PhieuDatVe_DAO pdvDAO = new dao.PhieuDatVe_DAO();
    private final dao.ChiTietPhieuDat_DAO ctpdvDAO = new dao.ChiTietPhieuDat_DAO();
    private final dao.HoaDon_DAO hdDAO = new dao.HoaDon_DAO();
    private final dao.Thue_DAO thueDAO = new dao.Thue_DAO();
    private final dao.KhuyenMai_DAO kmDAO = new dao.KhuyenMai_DAO();

    private JTextField txtSearch, txtPromo;
    private JTextArea txtBookingInfo;
    
    private JTable tblCombo;
    private DefaultTableModel modelCombo;

    private JTable tblCart;
    private DefaultTableModel modelCart;

    private JLabel lblTotalTicket;
    private JLabel lblTotalCombo;
    private JLabel lblTaxDetail; // Added for multiple taxes
    private JLabel lblGrandTotal;
    private JLabel lblPromoDetail;

    // State
    private PhieuDatVe currentPhieu = null;
    private double currentTicketPrice = 0;
    private double currentComboPrice = 0;
    private List<model.Thue> activeTaxes = new ArrayList<>();
    private model.KhuyenMai currentKM = null;
    
    // Lưu các combo đã chọn: Model = [Mã, Tên, Giá, SL, Thành tiền]

    public FrmBanVe(NhanVienController controller, model.NhanVien user) {
        this.controller = controller;
        this.user = user;
        this.comboDAO = new ComboBapNuoc_DAO();

        setTitle("Bán Vé & Bán Bắp Nước");
        setSize(1200, 680);
        setMinimumSize(new Dimension(900, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadComboData();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 245, 248));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);

        // Main working area: 4 Zones
        JPanel pnlMain = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlMain.setOpaque(false);
        pnlMain.setBorder(new EmptyBorder(10, 16, 10, 16));

        // Zone 1: Tra cứu & Thông tin vé
        pnlMain.add(buildZone1());
        // Zone 2: Danh mục Combo
        pnlMain.add(buildZone2());
        // Zone 3: Giỏ hàng
        pnlMain.add(buildZone3());
        // Zone 4: Tổng kết & Thuế
        pnlMain.add(buildZone4());

        root.add(pnlMain, BorderLayout.CENTER);
        
        // Load active taxes from DB
        loadTaxData();

        root.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (SharedData.directSaleSearchKey != null) {
                    txtSearch.setText(SharedData.directSaleSearchKey);
                    searchBooking();
                    SharedData.directSaleSearchKey = null; // Clear it after use
                }
            }
        });
    }

    private JPanel buildHeader() {
        JPanel hdr = new JPanel(new BorderLayout(20, 0));
        hdr.setBackground(new Color(20, 20, 30));
        hdr.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("🛒  CGV CINEMA  –  Thanh Toán & Điểm Bán");
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

    private JPanel buildZone1() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(BorderFactory.createTitledBorder("Vùng 1: Tra cứu & Thông tin vé"));

        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        pnlSearch.setOpaque(false);
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JButton btnSearch = new JButton("Tìm Kiếm");
        btnSearch.setBackground(new Color(229, 9, 20));
        btnSearch.setForeground(Color.BLACK); // Change to black
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> searchBooking());

        pnlSearch.add(new JLabel(" SĐT / Mã: "), BorderLayout.WEST);
        pnlSearch.add(txtSearch, BorderLayout.CENTER);
        pnlSearch.add(btnSearch, BorderLayout.EAST);

        txtBookingInfo = new JTextArea();
        txtBookingInfo.setEditable(false);
        txtBookingInfo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBookingInfo.setText("Chưa có thông tin vé...");

        pnl.add(pnlSearch, BorderLayout.NORTH);
        pnl.add(new JScrollPane(txtBookingInfo), BorderLayout.CENTER);
        return pnl;
    }

    private JPanel buildZone2() {
        JPanel pnl = new JPanel(new BorderLayout(0, 5));
        pnl.setOpaque(false);
        pnl.setBorder(BorderFactory.createTitledBorder("Vùng 2: Danh mục Combo Bắp Nước"));

        String[] cols = { "Mã", "Tên Combo", "Giá Bán" };
        modelCombo = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCombo = new JTable(modelCombo);
        tblCombo.setRowHeight(30);

        JButton btnAdd = new JButton("THÊM VÀO GIỎ HÀNG");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAdd.setBackground(new Color(255, 193, 7)); // Yellow
        btnAdd.setForeground(Color.BLACK); // Change to black
        btnAdd.addActionListener(e -> addComboToCart());

        pnl.add(new JScrollPane(tblCombo), BorderLayout.CENTER);
        pnl.add(btnAdd, BorderLayout.SOUTH);
        return pnl;
    }

    private JPanel buildZone3() {
        JPanel pnl = new JPanel(new BorderLayout(0, 5));
        pnl.setOpaque(false);
        pnl.setBorder(BorderFactory.createTitledBorder("Vùng 3: Giỏ hàng (Combo đã chọn)"));

        String[] cols = { "Mã", "Tên Combo", "Giá", "SL", "Thành tiền" };
        modelCart = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCart = new JTable(modelCart);
        tblCart.setRowHeight(28);

        JButton btnRemove = new JButton("Xóa Combo Chọn");
        btnRemove.setBackground(new Color(240, 240, 245));
        btnRemove.setForeground(Color.BLACK);
        btnRemove.addActionListener(e -> {
            int row = tblCart.getSelectedRow();
            if (row >= 0) {
                modelCart.removeRow(row);
                updateTotals();
            }
        });

        pnl.add(new JScrollPane(tblCart), BorderLayout.CENTER);
        pnl.add(btnRemove, BorderLayout.SOUTH);
        return pnl;
    }

    private JPanel buildZone4() {
        JPanel pnl = new JPanel(new BorderLayout(0, 10));
        pnl.setOpaque(false);
        pnl.setBorder(BorderFactory.createTitledBorder("Vùng 4: Chi tiết thanh toán & Thuế"));
        pnl.setBackground(Color.WHITE);

        JPanel pnlSummary = new JPanel(new GridLayout(6, 1, 0, 5));
        pnlSummary.setOpaque(false);

        // Promo Input
        JPanel pnlPromo = new JPanel(new BorderLayout(5, 0));
        pnlPromo.setOpaque(false);
        txtPromo = new JTextField();
        txtPromo.setBorder(BorderFactory.createTitledBorder("Mã KM (hoặc chọn bên phải)"));
        
        JButton btnListKM = new JButton("Chọn Mã");
        btnListKM.setToolTipText("Xem danh sách mã khuyến mãi đang có");
        btnListKM.addActionListener(e -> showKhuyenMaiList());
        
        JButton btnApplyKM = new JButton("Áp dụng");
        btnApplyKM.addActionListener(e -> applyPromotion());
        
        JPanel pnlPromoActions = new JPanel(new GridLayout(1, 2, 2, 0));
        pnlPromoActions.setOpaque(false);
        pnlPromoActions.add(btnListKM);
        pnlPromoActions.add(btnApplyKM);
        
        pnlPromo.add(txtPromo, BorderLayout.CENTER);
        pnlPromo.add(pnlPromoActions, BorderLayout.EAST);

        lblTotalTicket = new JLabel("Tiền vé: 0 VND");
        lblTotalCombo = new JLabel("Tiền Combo: 0 VND");
        lblPromoDetail = new JLabel("Giảm giá: 0 VND");
        lblTaxDetail = new JLabel("Thuế: 0 VND");
        lblGrandTotal = new JLabel("TỔNG CỘNG: 0 VND");
        
        lblTotalTicket.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTotalCombo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPromoDetail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPromoDetail.setForeground(new Color(40, 167, 69)); // Green
        lblTaxDetail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblTaxDetail.setForeground(new Color(100, 100, 100));
        lblGrandTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblGrandTotal.setForeground(new Color(229, 9, 20));

        pnlSummary.add(pnlPromo);
        pnlSummary.add(lblTotalTicket);
        pnlSummary.add(lblTotalCombo);
        pnlSummary.add(lblPromoDetail);
        pnlSummary.add(lblTaxDetail);
        pnlSummary.add(lblGrandTotal);

        JButton btnConfirm = new JButton("XÁC NHẬN THANH TOÁN");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnConfirm.setBackground(new Color(40, 167, 69)); // Green
        btnConfirm.setForeground(Color.BLACK); // Change to black
        btnConfirm.setPreferredSize(new Dimension(0, 60));
        btnConfirm.addActionListener(e -> processPayment());

        pnl.add(pnlSummary, BorderLayout.CENTER);
        pnl.add(btnConfirm, BorderLayout.SOUTH);
        return pnl;
    }

    private void addComboToCart() {
        int row = tblCombo.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Combo!");
            return;
        }
        String id = modelCombo.getValueAt(row, 0).toString();
        String name = modelCombo.getValueAt(row, 1).toString();
        double price = Double.parseDouble(modelCombo.getValueAt(row, 2).toString().replace(",", "").replace(" VND", ""));
        
        String slStr = JOptionPane.showInputDialog(this, "Số lượng:", "1");
        if (slStr != null && !slStr.trim().isEmpty()) {
            try {
                int sl = Integer.parseInt(slStr.trim());
                if (sl > 0) {
                    modelCart.addRow(new Object[] { id, name, price, sl, price * sl });
                    updateTotals();
                }
            } catch (Exception ex) {}
        }
    }

    // Removing buildBottomPanel as it's replaced by Zone 4

    private void searchBooking() {
        String query = txtSearch.getText().trim();
        if (query.isEmpty()) return;
        
        PhieuDatVe found = pdvDAO.getPhieuDatVeById(query);
        if (found == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu đặt trong SQL với thông tin: " + query);
            currentPhieu = null;
            txtBookingInfo.setText("Chưa chọn phiếu đặt nào.\n\nNhập SĐT khách hàng để tìm vé đã đặt, hoặc bỏ qua để bán Combo trực tiếp.");
            currentTicketPrice = 0;
            updateTotals();
            return;
        }
        
        // Fetch details from SQL
        found.setChiTietPhieuDats(ctpdvDAO.getChiTietByPhieu(found.getMaPhieuDat()));

        currentPhieu = found;
        
        // Kiểm tra giới hạn độ tuổi
        int limit = 0;
        // Thử lấy giới hạn tuổi từ phiếu (nếu đã mock vào ChiTiet)
        if (!found.getChiTietPhieuDats().isEmpty()) {
            limit = found.getChiTietPhieuDats().get(0).getVe().getTrangThaiGhe().getSuatChieu().getPhim().getGioiHan();
        } else if (found.getTrangThai().contains("Phim:")) {
            // Trường hợp bán trực tiếp, parse từ string mock
            // (Thực tế nên có object SuatChieu trong PhieuDatVe)
        }

        if (limit > 0) {
            String ageStr = JOptionPane.showInputDialog(this, "Phim yêu cầu độ tuổi " + limit + "+\nVui lòng nhập tuổi khách hàng:", "Xác minh độ tuổi", JOptionPane.QUESTION_MESSAGE);
            if (ageStr == null) {
                currentPhieu = null;
                txtBookingInfo.setText("Hủy kiểm tra tuổi.");
                return;
            }
            try {
                int age = Integer.parseInt(ageStr);
                if (age < limit) {
                    JOptionPane.showMessageDialog(this, "Khách hàng không đủ tuổi xem phim này (Yêu cầu " + limit + "+)!", "Từ chối", JOptionPane.ERROR_MESSAGE);
                    currentPhieu = null;
                    txtBookingInfo.setText("Từ chối thanh toán do khách hàng không đủ tuổi.");
                    updateTotals();
                    return;
                }
            } catch (Exception e) {
                currentPhieu = null;
                return;
            }
        }
        
        // Mock calculating total ticket price from number of tickets.
        // Assume normal price for all for simple mock, as we didn't save seats properly in object.
        double assumedPrice = found.getSoLuongVe() * 75000.0;
        currentTicketPrice = assumedPrice;
        
        StringBuilder sb = new StringBuilder();
        sb.append("Mã phiếu: ").append(found.getMaPhieuDat()).append("\n");
        if (found.getNgayDat() != null) {
            sb.append("Ngày tạo: ").append(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(found.getNgayDat())).append("\n");
        }
        if (found.getNhanVien() != null) {
            sb.append("Nhân viên lập: ").append(found.getNhanVien().getHoTen()).append("\n");
        }
        if (found.getKhachHang() != null) {
            sb.append("Khách hàng: ").append(found.getKhachHang().getHoTen())
              .append(" (").append(found.getKhachHang().getsDT()).append(")\n");
        }
        sb.append("Số lượng vé: ").append(found.getSoLuongVe()).append("\n");
        sb.append("Trạng thái: ").append(found.getTrangThai()).append("\n");
        sb.append("\nTạm tính tiền vé: ").append(String.format("%,.0f VND", assumedPrice));
        
        txtBookingInfo.setText(sb.toString());
        updateTotals();
    }

    private void updateTotals() {
        currentComboPrice = 0;
        for (int i = 0; i < modelCart.getRowCount(); i++) {
            currentComboPrice += Double.parseDouble(modelCart.getValueAt(i, 4).toString());
        }
        
        double totalPreTax = currentTicketPrice + currentComboPrice;
        
        // Tính Giảm giá trên giá gốc
        double totalDiscount = 0;
        if (currentKM != null) {
            totalDiscount = totalPreTax * currentKM.getHeSoGiam();
            lblPromoDetail.setText(String.format("Giảm giá (%s): -%,.0f VND", currentKM.getMaKhuyenMai(), totalDiscount));
        } else {
            lblPromoDetail.setText("Giảm giá: 0 VND");
        }

        // Tính Thuế trên giá gốc (không cộng dồn khuyến mãi)
        double totalTax = 0;
        StringBuilder taxDesc = new StringBuilder("Thuế: ");
        
        if (activeTaxes.isEmpty()) {
            taxDesc.append("0 VND");
        } else {
            for (model.Thue t : activeTaxes) {
                double amount = totalPreTax * t.getHeSoThue();
                totalTax += amount;
                taxDesc.append(t.getTenLoaiThue()).append(" (").append(String.format("%.0f%%", t.getMucThuePhanTram())).append("), ");
            }
        }
        
        lblTotalTicket.setText(String.format("Tiền vé: %,.0f VND", currentTicketPrice));
        lblTotalCombo.setText(String.format("Tiền Combo: %,.0f VND", currentComboPrice));
        lblTaxDetail.setText(taxDesc.toString());
        lblGrandTotal.setText(String.format("TỔNG CỘNG: %,.0f VND", (totalPreTax - totalDiscount + totalTax)));
    }

    private void applyPromotion() {
        String code = txtPromo.getText().trim();
        if (code.isEmpty()) {
            currentKM = null;
            updateTotals();
            return;
        }
        
        model.KhuyenMai km = kmDAO.getKhuyenMaiById(code);
        if (km != null) {
            double totalPreTax = currentTicketPrice + currentComboPrice;
            if (totalPreTax < km.getTongTienToiThieu()) {
                JOptionPane.showMessageDialog(this, "❌ Tổng tiền chưa đạt mức tối thiểu để áp dụng mã này!\nYêu cầu tối thiểu: " + String.format("%,.0f VND", km.getTongTienToiThieu()));
                currentKM = null;
            } else {
                currentKM = km;
                JOptionPane.showMessageDialog(this, "✅ Đã áp dụng mã: " + km.getTenKhuyenMai() + " (Giảm " + km.getHinhThucGiam() + "%)");
            }
        } else {
            currentKM = null;
            JOptionPane.showMessageDialog(this, "❌ Mã khuyến mãi không tồn tại!");
        }
        updateTotals();
    }

    private void showKhuyenMaiList() {
        List<model.KhuyenMai> dsKM = kmDAO.getAllKhuyenMai();
        if (dsKM.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hiện không có chương trình khuyến mãi nào!");
            return;
        }

        String[] cols = {"Mã KM", "Tên Chương Trình", "Mức Giảm (%)"};
        DefaultTableModel modelList = new DefaultTableModel(cols, 0);
        for (model.KhuyenMai km : dsKM) {
            modelList.addRow(new Object[]{km.getMaKhuyenMai(), km.getTenKhuyenMai(), km.getHinhThucGiam() + "%"});
        }

        JTable table = new JTable(modelList);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        int result = JOptionPane.showConfirmDialog(this, new JScrollPane(table), "Chọn mã khuyến mãi", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String code = table.getValueAt(row, 0).toString();
                txtPromo.setText(code);
                applyPromotion();
            }
        }
    }

    private void loadTaxData() {
        try {
            dao.Thue_DAO thueDAO = new dao.Thue_DAO();
            activeTaxes = thueDAO.getAllThue();
        } catch (Exception e) {
            // Default to empty if error
        }
    }

    private void processPayment() {
        if (currentPhieu == null && currentComboPrice == 0) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng rỗng! Vui lòng chọn phiếu hoặc thêm combo.");
            return;
        }

        // Kiểm tra xem đơn này đã thanh toán chưa
        if (currentPhieu != null && "Đã thanh toán".equalsIgnoreCase(currentPhieu.getTrangThai())) {
            JOptionPane.showMessageDialog(this, "Đơn đặt vé này đã được thanh toán rồi! Không thể thực hiện lại.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Xác nhận thanh toán số tiền: " + String.format("%,.0f VND", (currentTicketPrice + currentComboPrice)),
            "Xác nhận", JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Tạo đối tượng HoaDon
            String maHD = "HD" + (System.currentTimeMillis() % 1000000);
            model.HoaDon hd = new model.HoaDon();
            hd.setMaHoaDon(maHD);
            hd.setNhanVien(user);
            hd.setKhachHang(currentPhieu != null ? currentPhieu.getKhachHang() : null);
            hd.setPhieuDatVe(currentPhieu);
            hd.setKhuyenMai(currentKM);
            hd.setThoiGianTao(new Date());
            hd.setTongTienGoc(currentTicketPrice + currentComboPrice);
            
            // Tính toán cuối cùng cho Hóa đơn SQL
            double base = hd.getTongTienGoc();
            double discount = currentKM != null ? base * currentKM.getHeSoGiam() : 0;
            
            double totalTax = 0;
            for (model.Thue t : activeTaxes) {
                totalTax += base * t.getHeSoThue();
            }
            hd.setTongTienThue(totalTax);
            hd.setTongThanhToan(base - discount + totalTax);
            
            // 2. Thêm Chi Tiết Hóa Đơn (Combo)
            List<model.ChiTietHoaDon> dsCT = new ArrayList<>();
            for (int i = 0; i < modelCart.getRowCount(); i++) {
                model.ChiTietHoaDon ct = new model.ChiTietHoaDon();
                ct.setMaChiTietHoaDon("CTHD" + maHD + "_" + (i + 1));
                
                model.ComboBapNuoc cb = new model.ComboBapNuoc();
                cb.setMaSanPham(modelCart.getValueAt(i, 0).toString());
                
                ct.setSanPham(cb);
                ct.setSoLuongMuc(Integer.parseInt(modelCart.getValueAt(i, 3).toString()));
                ct.setThanhTien(Double.parseDouble(modelCart.getValueAt(i, 4).toString()));
                dsCT.add(ct);
            }
            hd.setChiTietHoaDons(dsCT);
            
            // 3. Lưu vào SQL
            if (hdDAO.addHoaDon(hd)) {
                if (currentPhieu != null) {
                    pdvDAO.updateTrangThai(currentPhieu.getMaPhieuDat(), "Đã thanh toán");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn vào SQL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 4. Tạo chuỗi giỏ hàng in hóa đơn
            StringBuilder cartInfo = new StringBuilder();
            for (int i = 0; i < modelCart.getRowCount(); i++) {
                String name = modelCart.getValueAt(i, 1).toString();
                String sl = modelCart.getValueAt(i, 3).toString();
                String price = String.format("%,.0f", Double.parseDouble(modelCart.getValueAt(i, 4).toString()));
                cartInfo.append(name).append(" x").append(sl).append(" : ").append(price).append(" VND\n");
            }

            double totalPreTax = currentTicketPrice + currentComboPrice;
            totalTax = 0;
            StringBuilder taxDetailPrint = new StringBuilder();
            for (model.Thue t : activeTaxes) {
                double amount = totalPreTax * t.getHeSoThue();
                totalTax += amount;
                taxDetailPrint.append(String.format("%-17s (%.0f%%): %,10.0f VND\n", t.getTenLoaiThue(), t.getMucThuePhanTram(), amount));
            }
            double finalTotal = totalPreTax - (currentKM != null ? totalPreTax * currentKM.getHeSoGiam() : 0) + totalTax;

            String invoiceContent = FrmInHoaDon.generateInvoiceContent(
                txtBookingInfo.getText().replace("Chưa chọn phiếu đặt nào.", ""),
                cartInfo.toString(),
                currentTicketPrice,
                currentComboPrice,
                taxDetailPrint.toString(),
                finalTotal
            );
            
            // Nếu có khuyến mãi, thêm vào nội dung in (Quick fix)
            if (currentKM != null) {
                invoiceContent = invoiceContent.replace("TỔNG CỘNG:", "Khuyến mãi (" + currentKM.getMaKhuyenMai() + "): -" + String.format("%,.0f", totalPreTax * currentKM.getHeSoGiam()) + " VND\nTỔNG CỘNG:");
            }

            // Tự động chuyển sang phần Quản lý vé & In ấn
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            String searchKey = (currentPhieu != null) ? currentPhieu.getMaPhieuDat() : maHD;

            // TỰ ĐỘNG IN ẤN
            List<VeFullInfo> dsVeIn = new dao.Ve_DAO().searchTickets(searchKey);
            if (!dsVeIn.isEmpty()) {
                // 1. In hóa đơn
                boolean isPaid = dsVeIn.get(0).getTrangThai().contains("Đã bán") || dsVeIn.get(0).getTrangThai().contains("đã thanh toán") || dsVeIn.get(0).getTrangThai().contains("Bán trực tiếp");
                String invoice = PrintUtility.generateInvoiceContent(searchKey, dsVeIn.get(0).getSdtKhach(), dsVeIn.get(0).getNgayDat().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), dsVeIn, isPaid);
                PrintUtility.showPrintPreview(invoice, "Tự động In Hóa Đơn");

                // 2. In lẻ từng vé
                for (VeFullInfo v : dsVeIn) {
                    PrintUtility.showPrintPreview(PrintUtility.generateTicketContent(v), "Tự động In Vé - " + v.getMaGhe());
                }
            }

            if (parentWindow instanceof MainGUI) {
                ((MainGUI) parentWindow).switchToQuanLyVe(searchKey);
            }

            resetOrder();
        }
    }

    private void resetOrder() {
        currentPhieu = null;
        currentKM = null;
        currentTicketPrice = 0;
        currentComboPrice = 0;
        txtSearch.setText("");
        txtPromo.setText("");
        txtBookingInfo.setText("Chưa chọn phiếu đặt nào.");
        modelCart.setRowCount(0);
        updateTotals();
    }

    private void loadComboData() {
        try {
            List<ComboBapNuoc> ds = comboDAO.getAllCombo();
            if (ds == null || ds.isEmpty()) {
                // Thêm dữ liệu giả nếu DB trống/lỗi để test
                addMockCombos();
            } else {
                modelCombo.setRowCount(0);
                for (ComboBapNuoc cb : ds) {
                    modelCombo.addRow(new Object[] {
                        cb.getMaSanPham(), cb.getTenSanPham(), 
                        String.format("%,.0f VND", cb.getGiaBanCoBan())
                    });
                }
            }
        } catch (Exception e) {
            addMockCombos();
        }
    }
    
    private void addMockCombos() {
        modelCombo.setRowCount(0);
        modelCombo.addRow(new Object[]{"CB01", "Combo 1 Bắp 1 Nước", "75,000 VND"});
        modelCombo.addRow(new Object[]{"CB02", "Combo 2 Bắp 2 Nước", "140,000 VND"});
        modelCombo.addRow(new Object[]{"CB03", "Bắp Lẻ (Lớn)", "50,000 VND"});
        modelCombo.addRow(new Object[]{"CB04", "Nước Ngọt (Lớn)", "30,000 VND"});
    }
}
