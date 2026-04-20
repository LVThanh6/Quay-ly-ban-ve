package dao;

import model.Phim;
import java.util.List;

public class PhimDAO {
    private MockDataStore db = MockDataStore.getInstance();

    public List<Phim> layTatCaPhim() {
        return db.danhSachPhim;
    }

    public Phim timPhimTheoMa(String maPhim) {
        for(Phim p : db.danhSachPhim) {
            if(p.getMaPhim().equals(maPhim)) return p;
        }
        return null;
    }
}
