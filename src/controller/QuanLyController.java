package controller;

import java.util.List;

import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import model.NhanVien;
import model.Phim;

public class QuanLyController extends NhanVienController {
    
    private NhanVien_DAO nhanVienDAO;
    private HoaDon_DAO hoaDonDAO;

    public QuanLyController() {
        super();
        this.nhanVienDAO = new NhanVien_DAO();
        this.hoaDonDAO = new HoaDon_DAO();
    }
    
    // ==========================================
    // QUẢN LÝ
    // ==========================================
    
    public boolean themPhim(Phim phim) {
        return phimDAO.addPhim(phim);
    }
    
    public boolean themNhanVien(NhanVien nv) {
        return nhanVienDAO.addNhanVien(nv);
    }
    
    public List<NhanVien> layDanhSachNhanVien() {
        return nhanVienDAO.getAllNhanVien();
    }
    
    // ==========================================
    // THỐNG KÊ (Dành cho Dashboard)
    // ==========================================
    
    /**
     * Lấy tổng doanh thu
     */
    public double getThongKeDoanhThu() {
        // Cần truy vấn thông qua HoaDon_DAO hoặc Ve_DAO
        // Demo tạm trả về 0, cần liên kết đúng DAO tương ứng
        return 0.0;
    }
    
    /**
     * Lấy tỷ lệ lấp đầy phòng chiếu
     */
    public double getTyLeLapDay() {
        // Logic tính toán tỷ lệ lấp đầy
        return 0.0; 
    }
    
    /**
     * Lấy danh sách vé vừa bán gần đây nhất
     */
    public List<model.Ve> getDanhSachVeVuaBan() {
        // Giả sử có hàm lấy top n vé mới nhất trong Ve_DAO
        return veDAO.getAllVe(); 
    }
}
