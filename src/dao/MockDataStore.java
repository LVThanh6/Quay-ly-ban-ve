package dao;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockDataStore {
    private static MockDataStore instance;
    
    public List<Phim> danhSachPhim;
    public List<PhongChieu> danhSachPhongChieu;
    public List<SuatChieu> danhSachSuatChieu;
    public List<Ghe> danhSachGhe;
    public List<TrangThaiGhe> danhSachTrangThaiGhe;

    private MockDataStore() {
        danhSachPhim = new ArrayList<>();
        danhSachPhongChieu = new ArrayList<>();
        danhSachSuatChieu = new ArrayList<>();
        danhSachGhe = new ArrayList<>();
        danhSachTrangThaiGhe = new ArrayList<>();
        initMockData();
    }

    public static MockDataStore getInstance() {
        if (instance == null) {
            instance = new MockDataStore();
        }
        return instance;
    }

    private void initMockData() {
        // 1. Phim
        Phim p1 = new Phim("P01", "Mai", LocalDate.of(2024, 2, 10), "Tran Thanh Town", 18, 131, "Tâm lý/Tình cảm");
        Phim p2 = new Phim("P02", "Dune: Part Two", LocalDate.of(2024, 3, 1), "Legendary", 13, 166, "Sci-Fi");
        danhSachPhim.add(p1);
        danhSachPhim.add(p2);

        // 2. Phòng Chiếu
        PhongChieu pc1 = new PhongChieu("PC01", 40, "2D - Standard"); // Theo code cũ BanVe_UI chạy PC01 với 40 ghế
        danhSachPhongChieu.add(pc1);

        // 3. Suất Chiếu
        SuatChieu sc1 = new SuatChieu("SC01", LocalDateTime.now().plusHours(2), 80000.0, p1, pc1);
        danhSachSuatChieu.add(sc1);

        // 4. Sinh Ghế cho PC01 và Trạng Thái Ghế cho SC01
        for (int i = 1; i <= pc1.getSoLuongGhe(); i++) {
            Ghe ghe = new Ghe("G" + i, i > 30 ? "VIP" : "Thuong", pc1);
            danhSachGhe.add(ghe);
            
            // Mặc định trống
            TrangThaiGhe ttg = new TrangThaiGhe(ghe, sc1, "Trống");
            
            // Random vài ghế đã đặt
            if(i % 7 == 0) ttg.setTrangThai("Đã bán");
            
            danhSachTrangThaiGhe.add(ttg);
        }
    }
}
