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
    protected dao.SuatChieu_DAO suatChieuDAO;
    protected dao.TrangThaiGhe_DAO trangThaiGheDAO;
    
    public NhanVienController() {
        this.phimDAO = new Phim_DAO();
        this.veDAO = new Ve_DAO();
        this.suatChieuDAO = new dao.SuatChieu_DAO();
        this.trangThaiGheDAO = new dao.TrangThaiGhe_DAO();
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
    
    public List<model.SuatChieu> getSuatChieuByPhim(String maPhim) {
        return suatChieuDAO.getSuatChieuByPhim(maPhim);
    }
    
    public List<model.TrangThaiGhe> getTrangThaiGheBySuatChieu(String maSuatChieu) {
        return trangThaiGheDAO.getTrangThaiGheBySuatChieu(maSuatChieu);
    }
    
    public boolean updateTrangThaiGhe(model.TrangThaiGhe ttg) {
        return trangThaiGheDAO.updateTrangThai(ttg);
    }
}
