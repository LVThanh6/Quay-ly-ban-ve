package model;

public class ChiTietHoaDon {
    private String maThanhToan;
    private String phuongThucThanhToan;
    private HoaDon hoaDon;
    private SanPham sanPham; // Co the la Ve hoac ComboBapNuoc

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String maThanhToan, String phuongThucThanhToan, HoaDon hoaDon, SanPham sanPham) {
        this.maThanhToan = maThanhToan;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public boolean xyLyThanhToan() {
        // Logic xu ly, ket noi API
        return true;
    }

    public void xacNhanThanhToan() {
        // update trang thai
    }
}
