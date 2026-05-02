package view;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Màn hình hiển thị và in Hóa Đơn / Vé.
 */
public class FrmInHoaDon extends JDialog {

    private JTextArea txtInvoice;
    private JButton btnPrint;
    private JButton btnClose;

    public FrmInHoaDon(JFrame parent, String invoiceContent) {
        super(parent, "Chi Tiết Hóa Đơn", true);
        setSize(400, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        txtInvoice = new JTextArea();
        txtInvoice.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtInvoice.setEditable(false);
        txtInvoice.setText(invoiceContent);
        
        // Căn lề padding cho đẹp
        txtInvoice.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(txtInvoice);
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlButtons.setBackground(Color.WHITE);

        btnPrint = new JButton("🖨️ In Hóa Đơn");
        btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPrint.setBackground(new Color(70, 130, 180));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFocusPainted(false);

        btnClose = new JButton("Đóng");
        btnClose.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClose.setFocusPainted(false);

        btnPrint.addActionListener(e -> {
            try {
                boolean printed = txtInvoice.print();
                if (printed) {
                    JOptionPane.showMessageDialog(this, "In hóa đơn thành công!");
                    dispose();
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi máy in: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClose.addActionListener(e -> dispose());

        pnlButtons.add(btnPrint);
        pnlButtons.add(btnClose);

        add(pnlButtons, BorderLayout.SOUTH);
    }
    
    /**
     * Hàm tiện ích tạo nội dung hóa đơn mẫu
     */
    public static String generateInvoiceContent(String bookingInfo, String cartInfo, double totalTicket, double totalCombo, String taxDetail, double grandTotal) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        sb.append("====================================\n");
        sb.append("           GRAND CINEMA\n");
        sb.append("         HÓA ĐƠN BÁN LẺ\n");
        sb.append("====================================\n");
        sb.append("Ngày in: ").append(sdf.format(new Date())).append("\n");
        sb.append("Nhân viên: Hệ thống\n");
        sb.append("------------------------------------\n");
        
        if (bookingInfo != null && !bookingInfo.isEmpty()) {
            sb.append("[THÔNG TIN VÉ]\n");
            sb.append(bookingInfo).append("\n");
            sb.append("------------------------------------\n");
        }
        
        if (cartInfo != null && !cartInfo.isEmpty()) {
            sb.append("[BẮP NƯỚC THÊM]\n");
            sb.append(cartInfo).append("\n");
            sb.append("------------------------------------\n");
        }
        
        sb.append(String.format("Tiền vé:         %,10.0f VND\n", totalTicket));
        sb.append(String.format("Tiền bắp nước:   %,10.0f VND\n", totalCombo));
        if (taxDetail != null && !taxDetail.isEmpty()) {
            sb.append(taxDetail); 
        }
        sb.append("====================================\n");
        sb.append(String.format("TỔNG THANH TOÁN: %,10.0f VND\n", grandTotal));
        sb.append("====================================\n");
        sb.append("     Xin cảm ơn và hẹn gặp lại!\n");
        sb.append("        Wifi: GRAND_CINEMA\n");
        sb.append("        Pass: grand1234\n");
        
        return sb.toString();
    }
}
