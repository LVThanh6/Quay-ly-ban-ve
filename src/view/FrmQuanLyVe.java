package view;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.Ve_DAO;
import model.VeFullInfo;
import util.PrintUtility;

public class FrmQuanLyVe extends JPanel {
    private JTextField txtSearch;
    private JButton btnSearch, btnInVe, btnInPhieu;
    private JTable table;
    private DefaultTableModel model;
    private Ve_DAO veDAO = new Ve_DAO();
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public FrmQuanLyVe() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 250));

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(BorderFactory.createTitledBorder("Tra cứu thông tin vé (SĐT, Mã ghế, Mã phiếu, Tên phim...)"));
        
        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch = new JButton("Tra Cứu 🔍");
        btnSearch.setBackground(new Color(33, 150, 243));
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setFocusPainted(false);
        
        pnlTop.add(new JLabel("Từ khóa tìm nhanh:"));
        pnlTop.add(txtSearch);
        pnlTop.add(btnSearch);
        
        add(pnlTop, BorderLayout.NORTH);

        String[] columns = {"Mã Vé", "Phim", "Suất Chiếu", "Phòng", "Ghế", "Khách Hàng", "Ngày Đặt", "Trạng Thái", "Mã Phiếu"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        pnlBottom.setBackground(Color.WHITE); 
        pnlBottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        btnInVe = new JButton("IN VÉ (Từng ghế) 🎫");
        btnInPhieu = new JButton("IN HÓA ĐƠN / PHIẾU ĐẶT 📄");
        
        btnInVe.setPreferredSize(new Dimension(220, 45));
        btnInPhieu.setPreferredSize(new Dimension(280, 45));
        btnInVe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnInPhieu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        btnInVe.setBackground(new Color(76, 175, 80));
        btnInVe.setForeground(Color.BLACK);
        btnInPhieu.setBackground(new Color(255, 152, 0));
        btnInPhieu.setForeground(Color.BLACK);
        
        btnInVe.setFocusPainted(false);
        btnInPhieu.setFocusPainted(false);
        
        pnlBottom.add(btnInVe);
        pnlBottom.add(btnInPhieu);
        add(pnlBottom, BorderLayout.SOUTH);

        btnSearch.addActionListener(e -> performSearch());
        txtSearch.addActionListener(e -> performSearch());
        btnInVe.addActionListener(e -> printSingleTicket());
        btnInPhieu.addActionListener(e -> printFullBooking());

        performSearch();
    }

    public void searchAndSelect(String keyword) {
        txtSearch.setText(keyword);
        performSearch();
    }

    private List<VeFullInfo> currentResults = new ArrayList<>();

    private void performSearch() {
        String kw = txtSearch.getText().trim();
        currentResults = veDAO.searchTickets(kw);
        model.setRowCount(0);
        for (VeFullInfo v : currentResults) {
            model.addRow(new Object[]{
                v.getMaVe(),
                v.getTenPhim(),
                v.getThoiGianChieu().format(dtf),
                v.getMaPhong(),
                v.getMaGhe(),
                v.getSdtKhach(),
                v.getNgayDat() != null ? v.getNgayDat().format(dtf) : "—",
                v.getTrangThai(),
                v.getMaPhieuDat()
            });
        }
    }

    private void printSingleTicket() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng trong bảng để in vé lẻ!");
            return;
        }
        VeFullInfo v = currentResults.get(row);
        PrintUtility.showPrintPreview(PrintUtility.generateTicketContent(v), "In Vé Lẻ - " + v.getMaGhe());
    }

    private void printFullBooking() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xác định đơn hàng cần in!");
            return;
        }
        String maPhieu = currentResults.get(row).getMaPhieuDat();
        List<VeFullInfo> sameBooking = new ArrayList<>();
        for (VeFullInfo v : currentResults) {
            if (v.getMaPhieuDat().equals(maPhieu)) {
                sameBooking.add(v);
            }
        }

        if (sameBooking.isEmpty()) return;

        boolean isPaid = sameBooking.get(0).getTrangThai().contains("Đã bán") || 
                         sameBooking.get(0).getTrangThai().contains("đã thanh toán") || 
                         sameBooking.get(0).getTrangThai().contains("Bán trực tiếp");

        String content = PrintUtility.generateInvoiceContent(
            maPhieu, 
            sameBooking.get(0).getSdtKhach(),
            sameBooking.get(0).getNgayDat() != null ? sameBooking.get(0).getNgayDat().format(dtf) : "—",
            sameBooking,
            isPaid
        );
        PrintUtility.showPrintPreview(content, "In Hóa đơn / Phiếu đặt");
    }
}
