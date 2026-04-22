package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private LocalDate ngaySanXuat;
    private String donViSanXuat;
    private int gioiHan;
    private int thoiLuongPhim; // in minutes
    private String loaiPhim;

    public Phim() {}

    public Phim(String maPhim, String tenPhim, LocalDate ngaySanXuat, String donViSanXuat, int gioiHan, int thoiLuongPhim, String loaiPhim) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.ngaySanXuat = ngaySanXuat;
        this.donViSanXuat = donViSanXuat;
        this.gioiHan = gioiHan;
        this.thoiLuongPhim = thoiLuongPhim;
        this.loaiPhim = loaiPhim;
    }

    // Getters and Setters
    public String getMaPhim() { return maPhim; }
    public void setMaPhim(String maPhim) { this.maPhim = maPhim; }
    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }
    public LocalDate getNgaySanXuat() { return ngaySanXuat; }
    public void setNgaySanXuat(LocalDate ngaySanXuat) { this.ngaySanXuat = ngaySanXuat; }
    public String getDonViSanXuat() { return donViSanXuat; }
    public void setDonViSanXuat(String donViSanXuat) { this.donViSanXuat = donViSanXuat; }
    public int getGioiHan() { return gioiHan; }
    public void setGioiHan(int gioiHan) { this.gioiHan = gioiHan; }
    public int getThoiLuongPhim() { return thoiLuongPhim; }
    public void setThoiLuongPhim(int thoiLuongPhim) { this.thoiLuongPhim = thoiLuongPhim; }
    public String getLoaiPhim() { return loaiPhim; }
    public void setLoaiPhim(String loaiPhim) { this.loaiPhim = loaiPhim; }

    public void layChiTietPhim() { /* In thông tin chi tiết */ }
}
