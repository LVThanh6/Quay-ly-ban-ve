package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuPanel extends JPanel {
    private JFrame parentFrame;

    // Thiết lập giao diện Nimbus cho môn học nhìn đẹp hơn
    static {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        // Dùng BorderLayout để add JMenuBar thì nó sẽ phủ kín chiều ngang
        setLayout(new BorderLayout());

        // Khởi tạo Thanh Menu chuẩn theo môn Lập trình Hướng sự kiện
        JMenuBar menuBar = new JMenuBar();

        // ==================== 1. MENU HỆ THỐNG ====================
        JMenu mnuHeThong = new JMenu("Hệ Thống");
        mnuHeThong.setMnemonic(KeyEvent.VK_H); // Phím tắt Alt + H

        JMenuItem mniDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem mniThoat = new JMenuItem("Thoát");

        // Sự kiện: Bấm thoát thì tắt app
        mniThoat.addActionListener(e -> System.exit(0));

        mnuHeThong.add(mniDangXuat);
        mnuHeThong.addSeparator(); // Kẻ đường gạch ngang chia cách
        mnuHeThong.add(mniThoat);

        // ==================== 2. MENU QUẢN LÝ PHIM VÀ RẠP ====================
        JMenu mnuQuayPhim = new JMenu("Quản Lý Phim & Rạp");
        mnuQuayPhim.setMnemonic(KeyEvent.VK_Q);

        JMenuItem mniPhim = new JMenuItem("Quản lý Phim...");
        JMenuItem mniPhongChieu = new JMenuItem("Quản lý Phòng Chiếu...");
        JMenuItem mniSuatChieu = new JMenuItem("Quản lý Suất Chiếu...");
        JMenuItem mniGhe = new JMenuItem("Quản lý Ghế...");
        JMenuItem mniTrangThaiGhe = new JMenuItem("Cập nhật Trạng Thái Ghế...");

        mniPhim.addActionListener(e -> moForm(new FrmPhim()));
        mniPhongChieu.addActionListener(e -> moForm(new FrmPhongChieu()));
        mniSuatChieu.addActionListener(e -> moForm(new FrmSuatChieu()));
        mniGhe.addActionListener(e -> moForm(new FrmGhe()));
        mniTrangThaiGhe.addActionListener(e -> moForm(new FrmTrangThaiGhe()));

        mnuQuayPhim.add(mniPhim);
        mnuQuayPhim.add(mniPhongChieu);
        mnuQuayPhim.add(mniSuatChieu);
        mnuQuayPhim.add(mniGhe);
        mnuQuayPhim.add(mniTrangThaiGhe);

        // ==================== 3. MENU NGHIỆP VỤ BÁN HÀNG ====================
        JMenu mnuBanHang = new JMenu("Nghiệp Vụ Bán Hàng");
        mnuBanHang.setMnemonic(KeyEvent.VK_N);

        JMenuItem mniVe = new JMenuItem("Bán Vé Mới...");
        JMenuItem mniPhieuDatVe = new JMenuItem("Phiếu Đặt Vé...");
        JMenuItem mniCTPD = new JMenuItem("Chi Tiết Phiếu Đặt...");
        JMenuItem mniHoaDon = new JMenuItem("Tra cứu Hóa Đơn...");
        JMenuItem mniCTHD = new JMenuItem("Chi Tiết Hóa Đơn...");

        mniVe.addActionListener(e -> moForm(new FrmVe()));
        mniPhieuDatVe.addActionListener(e -> moForm(new FrmPhieuDatVe()));
        mniCTPD.addActionListener(e -> moForm(new FrmChiTietPhieuDat()));
        mniHoaDon.addActionListener(e -> moForm(new FrmHoaDon()));
        mniCTHD.addActionListener(e -> moForm(new FrmChiTietHoaDon()));

        mnuBanHang.add(mniVe);
        mnuBanHang.add(mniPhieuDatVe);
        mnuBanHang.add(mniCTPD);
        mnuBanHang.addSeparator();
        mnuBanHang.add(mniHoaDon);
        mnuBanHang.add(mniCTHD);

        // ==================== 4. MENU BẢNG DANH MỤC THỰC THỂ ====================
        JMenu mnuDanhMuc = new JMenu("Danh Mục Mở Rộng");
        mnuDanhMuc.setMnemonic(KeyEvent.VK_D);

        JMenuItem mniSanPham = new JMenuItem("Danh mục Cơ bản (Abstract)...");
        JMenuItem mniCombo = new JMenuItem("Khởi tạo Combo/Bắp Nước...");
        JMenuItem mniKhuyenMai = new JMenuItem("Chương trình Khuyến Mãi...");
        JMenuItem mniKhachHang = new JMenuItem("Hồ sơ Khách Hàng...");
        JMenuItem mniNhanVien = new JMenuItem("Nhân sự (Nhân Viên)...");

        mniSanPham.addActionListener(e -> moForm(new FrmSanPham()));
        mniCombo.addActionListener(e -> moForm(new FrmComboBapNuoc()));
        mniKhuyenMai.addActionListener(e -> moForm(new FrmKhuyenMai()));
        mniKhachHang.addActionListener(e -> moForm(new FrmKhachHang()));
        mniNhanVien.addActionListener(e -> moForm(new FrmNhanVien()));

        mnuDanhMuc.add(mniSanPham);
        mnuDanhMuc.add(mniCombo);
        mnuDanhMuc.add(mniKhuyenMai);
        mnuDanhMuc.addSeparator();
        mnuDanhMuc.add(mniKhachHang);
        mnuDanhMuc.add(mniNhanVien);

        // ==================== 5. MENU CÀI ĐẶT / CẤU HÌNH ====================
        JMenu mnuCaiDat = new JMenu("Cài Đặt");

        JMenuItem mniThue = new JMenuItem("Mức Thuế...");
        JMenuItem mniInHoaDon = new JMenuItem("Cấu hình in Hóa đơn...");

        mniThue.addActionListener(e -> moForm(new FrmThue()));
        mniInHoaDon.addActionListener(e -> moForm(new FrmInHoaDon()));

        mnuCaiDat.add(mniThue);
        mnuCaiDat.add(mniInHoaDon);

        // Add tất cả Menu chính vào Cấu trúc dạng MenuBar
        menuBar.add(mnuHeThong);
        menuBar.add(mnuQuayPhim);
        menuBar.add(mnuBanHang);
        menuBar.add(mnuDanhMuc);
        menuBar.add(mnuCaiDat);

        // Add JMenuBar vào Panel hiện tại (để tương thích kịch bản bạn đang dùng)
        add(menuBar, BorderLayout.CENTER);
    }

    // Hàm sự kiện mở Form mới & đóng Form cũ
    private void moForm(JFrame newForm) {
        if (parentFrame != null) {
            parentFrame.dispose();
        }
        newForm.setVisible(true);
    }
}
