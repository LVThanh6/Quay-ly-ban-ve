package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class HoaDon {
    private String maHoaDon;
    private Date thoiGianTao;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private KhuyenMai khuyenMai; // có thể null nếu k áp dụng
    private Thue thue;
    private List<ChiTietHoaDon> chiTietHoaDons;

    public HoaDon() {
        this.chiTietHoaDons = new ArrayList<>();
    }

    public HoaDon(String maHoaDon, Date thoiGianTao, NhanVien nhanVien, KhachHang khachHang, KhuyenMai khuyenMai, Thue thue) {
        this.maHoaDon = maHoaDon;
        this.thoiGianTao = thoiGianTao;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.khuyenMai = khuyenMai;
        this.thue = thue;
        this.chiTietHoaDons = new ArrayList<>();
    }

    public String getMaHoaDon() { return maHoaDon; }
    public void setMaHoaDon(String maHoaDon) { this.maHoaDon = maHoaDon; }

    public Date getThoiGianTao() { return thoiGianTao; }
    public void setThoiGianTao(Date thoiGianTao) { this.thoiGianTao = thoiGianTao; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public KhuyenMai getKhuyenMai() { return khuyenMai; }
    public void setKhuyenMai(KhuyenMai khuyenMai) { this.khuyenMai = khuyenMai; }

    public Thue getThue() { return thue; }
    public void setThue(Thue thue) { this.thue = thue; }

    public List<ChiTietHoaDon> getChiTietHoaDons() { return chiTietHoaDons; }
    public void setChiTietHoaDons(List<ChiTietHoaDon> chiTietHoaDons) { this.chiTietHoaDons = chiTietHoaDons; }

    public void addChiTiet(ChiTietHoaDon c) {
        this.chiTietHoaDons.add(c);
        tinhTongTien();
    }

    public void huyHoaDon() {
        // logic hủy
    }

    public double tinhTongTien() {
        double tong = 0;
        for(ChiTietHoaDon ct : chiTietHoaDons) {
            tong += ct.getSanPham().tinhGiaThucTe();
        }
        
        // Phụ phí Thuế
        if(thue != null) {
            tong = tong + (tong * thue.getMucThue());
        }
        
        // Giảm trừ Khuyến mãi (Giả sử giảm chiết khấu %)
        if(khuyenMai != null) {
            tong = tong - (tong * khuyenMai.getHinhThucGiam());
        }
        
        return tong;
    }
}
