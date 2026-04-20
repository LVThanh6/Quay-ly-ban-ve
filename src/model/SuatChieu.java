package model;

import java.time.LocalDateTime;

public class SuatChieu {
    private String maSuatChieu;
    private LocalDateTime thoiGianKhoiChieu;
    private double giaVeCoBan;
    private Phim phim;
    private PhongChieu phongChieu;

    public SuatChieu() {}

    public SuatChieu(String maSuatChieu, LocalDateTime thoiGianKhoiChieu, double giaVeCoBan, Phim phim, PhongChieu phongChieu) {
        this.maSuatChieu = maSuatChieu;
        this.thoiGianKhoiChieu = thoiGianKhoiChieu;
        this.giaVeCoBan = giaVeCoBan;
        this.phim = phim;
        this.phongChieu = phongChieu;
    }

    public String getMaSuatChieu() { return maSuatChieu; }
    public void setMaSuatChieu(String maSuatChieu) { this.maSuatChieu = maSuatChieu; }

    public LocalDateTime getThoiGianKhoiChieu() { return thoiGianKhoiChieu; }
    public void setThoiGianKhoiChieu(LocalDateTime thoiGianKhoiChieu) { this.thoiGianKhoiChieu = thoiGianKhoiChieu; }

    public double getGiaVeCoBan() { return giaVeCoBan; }
    public void setGiaVeCoBan(double giaVeCoBan) { this.giaVeCoBan = giaVeCoBan; }

    public Phim getPhim() { return phim; }
    public void setPhim(Phim phim) { this.phim = phim; }

    public PhongChieu getPhongChieu() { return phongChieu; }
    public void setPhongChieu(PhongChieu phongChieu) { this.phongChieu = phongChieu; }

    public int hienThiSoGheTrong() {
        // Logic đếm số ghế trống
        return 0;
    }

    public void capNhatGhe() {
        // Cập nhật trạng thái
    }
}
