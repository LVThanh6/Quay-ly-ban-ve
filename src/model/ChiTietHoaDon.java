package model;

public class ChiTietHoaDon {
    private String maChiTietHoaDon;
    private HoaDon hoaDon;
    private SanPham sanPham;
    private int soLuongMuc;
    private double thanhTien;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maChiTietHoaDon, HoaDon hoaDon, SanPham sanPham, int soLuongMuc, double thanhTien) {
        this.maChiTietHoaDon = maChiTietHoaDon;
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.soLuongMuc = soLuongMuc;
        this.thanhTien = thanhTien;
    }

    public String getMaChiTietHoaDon() { return maChiTietHoaDon; }
    public void setMaChiTietHoaDon(String maChiTietHoaDon) { this.maChiTietHoaDon = maChiTietHoaDon; }

    public HoaDon getHoaDon() { return hoaDon; }
    public void setHoaDon(HoaDon hoaDon) { this.hoaDon = hoaDon; }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }

    public int getSoLuongMuc() { return soLuongMuc; }
    public void setSoLuongMuc(int soLuongMuc) { this.soLuongMuc = Math.max(0, soLuongMuc); }

    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = Math.max(0, thanhTien); }
}
