package dao;

import model.SuatChieu;
import model.TrangThaiGhe;

import java.util.ArrayList;
import java.util.List;

public class SuatChieuDAO {
    private MockDataStore db = MockDataStore.getInstance();

    public List<SuatChieu> laySuatChieuHomNay() {
        // Lấy tất cả vì mock data đều là hnay
        return db.danhSachSuatChieu;
    }

    public List<TrangThaiGhe> layTrangThaiGheTheoSuat(String maSuatChieu) {
        List<TrangThaiGhe> ketQua = new ArrayList<>();
        for(TrangThaiGhe ttg : db.danhSachTrangThaiGhe) {
            if(ttg.getSuatChieu().getMaSuatChieu().equals(maSuatChieu)) {
                ketQua.add(ttg);
            }
        }
        return ketQua;
    }
}
