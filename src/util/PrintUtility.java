package util;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import model.VeFullInfo;

public class PrintUtility {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String generateTicketContent(VeFullInfo v) {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("          VÉ XEM PHIM - GRAND CINEMA      \n");
        sb.append("==========================================\n");
        sb.append("Mã vé: ").append(v.getMaVe()).append("\n");
        sb.append("Phim: ").append(v.getTenPhim()).append("\n");
        sb.append("Suất: ").append(v.getThoiGianChieu().format(dtf)).append("\n");
        sb.append("Phòng: ").append(v.getMaPhong()).append(" | Ghế: ").append(v.getMaGhe()).append("\n");
        sb.append("Loại: ").append(v.getMaGhe().contains("VIP") ? "VIP" : "Thường").append("\n");
        sb.append("Giá: ").append(String.format("%,.0f VND", v.getGiaVe())).append("\n");
        sb.append("------------------------------------------\n");
        sb.append("        CHÚC BẠN XEM PHIM VUI VẺ!         \n");
        sb.append("==========================================\n");
        return sb.toString();
    }

    public static String generateInvoiceContent(String maPhieu, String sdt, String ngayDat, List<VeFullInfo> dsVe, boolean isPaid) {
        double total = 0;
        for (VeFullInfo v : dsVe) total += v.getGiaVe();

        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append(isPaid ? "        HÓA ĐƠN THANH TOÁN (PAID)       \n" : "        PHIẾU ĐẶT CHỖ (UNPAID)          \n");
        sb.append("              GRAND CINEMA              \n");
        sb.append("==========================================\n");
        sb.append("Mã phiếu: ").append(maPhieu).append("\n");
        sb.append("Khách hàng: ").append(sdt).append("\n");
        sb.append("Ngày đặt: ").append(ngayDat).append("\n");
        sb.append("Trạng thái: ").append(isPaid ? "ĐÃ THANH TOÁN" : "CHƯA THANH TOÁN").append("\n");
        sb.append("------------------------------------------\n");
        sb.append(String.format("%-18s | %-6s | %s\n", "Phim", "Ghế", "Giá"));
        for (VeFullInfo v : dsVe) {
            String shortName = v.getTenPhim().length() > 15 ? v.getTenPhim().substring(0, 12) + "..." : v.getTenPhim();
            sb.append(String.format("%-18s | %-6s | %,.0f\n", shortName, v.getMaGhe(), v.getGiaVe()));
        }
        sb.append("------------------------------------------\n");
        sb.append("TỔNG CỘNG: ").append(String.format("%,.0f VND", total)).append("\n");
        sb.append("==========================================\n");
        sb.append("      CẢM ƠN QUÝ KHÁCH, HẸN GẶP LẠI!      \n");
        return sb.toString();
    }

    public static void showPrintPreview(String content, String title) {
        JTextArea area = new JTextArea(content);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBackground(new Color(255, 255, 240));
        
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 500));
        
        JOptionPane.showMessageDialog(null, scroll, title, JOptionPane.PLAIN_MESSAGE);
    }
}
