package controller;

import java.util.List;
import dao.Phim_DAO;
import dao.Ve_DAO;
import model.Phim;
import model.Ve;

public class NhanVienController {
    
    // Khai báo các DAO cần thiết
    protected Phim_DAO phimDAO;
    protected Ve_DAO veDAO;
    
    public NhanVienController() {
        this.phimDAO = new Phim_DAO();
        this.veDAO = new Ve_DAO();
    }
    
    /**
     * Xử lý logic bán vé
     * @param ve Đối tượng vé cần bán
     * @return boolean Thành công hay thất bại
     */
    public boolean banVe(Ve ve) {
        // Thực hiện logic liên quan đến bán vé
        return veDAO.addVe(ve);
    }
    
    /**
     * Xem lịch chiếu phim
     * @return List danh sách các phim (hoặc lịch chiếu)
     */
    public List<Phim> xemLichChieu() {
        return phimDAO.getAllPhim();
    }
    
    // Có thể bổ sung thêm các nghiệp vụ khác của nhân viên
}
