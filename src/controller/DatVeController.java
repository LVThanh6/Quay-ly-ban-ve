package controller;

import dao.SuatChieuDAO;
import model.SuatChieu;
import model.TrangThaiGhe;

import java.util.List;

public class DatVeController {
    private SuatChieuDAO suatChieuDAO;

    public DatVeController() {
        this.suatChieuDAO = new SuatChieuDAO();
    }

    public List<SuatChieu> layLichChieuHomNay() {
        return suatChieuDAO.laySuatChieuHomNay();
    }

    public List<TrangThaiGhe> laySoDoGhe(String maSuatChieu) {
        return suatChieuDAO.layTrangThaiGheTheoSuat(maSuatChieu);
    }
    
    // Thêm các hàm xử lý thanh toán, hóa đơn, v.v. tại đây
}
