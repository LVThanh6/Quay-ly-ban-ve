package view;

import controller.DatVeController;
import model.NhanVien;
import model.SuatChieu;
import model.TrangThaiGhe;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import java.util.List;

public class BanVe_UI extends JFrame {

    private JPanel pnSodoGhe;
    private JPanel pnHoaDon;
    private JTextArea txtChiTietHoaDon;
    private JLabel lblTongTien;
    private JButton btnThanhToan;
    
    private DatVeController controller;
    private double tongTien = 0;
    private NhanVien nhanVien;

    public BanVe_UI(NhanVien nv) {
        this.nhanVien = nv;
        this.controller = new DatVeController();

        setTitle("Hệ thống Quản lý Rạp chiếu phim - Bán vé tại quầy (NV: " + nv.getHoTen() + ")");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ở hệ thống thật nên là DISPOSE_ON_CLOSE
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    private void initUI() {
        pnSodoGhe = new JPanel();
        pnSodoGhe.setBorder(new TitledBorder("Sơ đồ phòng chiếu"));
        
        // Lấy suất chiếu đầu tiên trong CSDL Mock cho nhanh
        List<SuatChieu> ls = controller.layLichChieuHomNay();
        if(ls.isEmpty()) return;
        
        SuatChieu scHienTai = ls.get(0);
        pnSodoGhe.setBorder(new TitledBorder("Sơ đồ phòng chiếu " + scHienTai.getPhongChieu().getMaPhongChieu() + " - Phim: " + scHienTai.getPhim().getTenPhim()));
        
        List<TrangThaiGhe> gheList = controller.laySoDoGhe(scHienTai.getMaSuatChieu());
        
        // Layout: 5 hàng, 8 cột = 40 ghế
        pnSodoGhe.setLayout(new GridLayout(5, 8, 5, 5)); 

        for (TrangThaiGhe ttg : gheList) {
            JButton btnGhe = new JButton(ttg.getGhe().getMaGhe());
            
            if("Đã bán".equals(ttg.getTrangThai())) {
                btnGhe.setBackground(Color.GRAY);
                btnGhe.setEnabled(false);
            } else {
                btnGhe.setBackground(Color.GREEN);
            }
            
            btnGhe.addActionListener(e -> {
                if (btnGhe.getBackground() == Color.GREEN) {
                    btnGhe.setBackground(Color.RED); 
                    double gia = scHienTai.getGiaVeCoBan();
                    if("VIP".equals(ttg.getGhe().getLoaiGhe())) gia += 20000; // Phụ thu ghế VIP
                    
                    capNhatHoaDon("Mã " + btnGhe.getText() + " (" + ttg.getGhe().getLoaiGhe() + ") : " + gia + " VNĐ");
                    tinhTongTien(gia);
                } else if (btnGhe.getBackground() == Color.RED) {
                    btnGhe.setBackground(Color.GREEN);
                    double gia = scHienTai.getGiaVeCoBan();
                    if("VIP".equals(ttg.getGhe().getLoaiGhe())) gia += 20000;
                    
                    capNhatHoaDon("HUỶ CHỌN: " + btnGhe.getText());
                    tinhTongTien(-gia);
                }
            });
            pnSodoGhe.add(btnGhe);
        }

        // PHẦN HOÁ ĐƠN
        pnHoaDon = new JPanel();
        pnHoaDon.setBorder(new TitledBorder("Chi tiết Hóa đơn (Khách Lẻ)"));
        pnHoaDon.setLayout(new BorderLayout(5, 5));
        pnHoaDon.setPreferredSize(new Dimension(350, 0)); 

        txtChiTietHoaDon = new JTextArea();
        txtChiTietHoaDon.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtChiTietHoaDon);
        
        JPanel pnThanhToan = new JPanel(new GridLayout(3, 1, 5, 5));
        lblTongTien = new JLabel("Tổng tiền: 0 VNĐ", SwingConstants.RIGHT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);
        
        btnThanhToan = new JButton("Xác nhận Thanh toán & In Vé");
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
        btnThanhToan.setBackground(new Color(229, 9, 20)); // Netflix Red
        btnThanhToan.setForeground(Color.WHITE);
        
        btnThanhToan.addActionListener(e -> {
            if(tongTien <= 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn ghế!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Thanh toán thành công " + tongTien + " VNĐ! Đang in vé...", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            // Logic tạo Model HoaDon, ChiTietHoaDon ở đây nếu có thời gian
        });

        JButton btnQuayVe = new JButton("Trở về Màn Hình Chính");
        btnQuayVe.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                new MainUI(this.nhanVien).setVisible(true);
                this.dispose();
            });
        });

        pnThanhToan.add(lblTongTien);
        pnThanhToan.add(btnThanhToan);
        pnThanhToan.add(btnQuayVe);

        pnHoaDon.add(scrollPane, BorderLayout.CENTER);
        pnHoaDon.add(pnThanhToan, BorderLayout.SOUTH);

        add(pnSodoGhe, BorderLayout.CENTER);
        add(pnHoaDon, BorderLayout.EAST);
    }

    private void capNhatHoaDon(String noiDung) {
        txtChiTietHoaDon.append(noiDung + "\n");
    }
    
    private void tinhTongTien(double tien) {
        tongTien += tien;
        lblTongTien.setText("Tổng tiền: " + tongTien + " VNĐ");
    }
}
