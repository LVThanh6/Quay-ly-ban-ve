package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class HoaDon {
    private String maHoaDon;
    private Date thoiGianTao;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private KhuyenMai khuyenMai;
    private PhieuDatVe phieuDatVe;
    private List<Thue> danhSachThue;
    private List<ChiTietHoaDon> chiTietHoaDons;
    private double tongTienGoc;
    private double tongTienThue;
    private double tongThanhToan;

    public HoaDon() {
        this.chiTietHoaDons = new ArrayList<>();
    }

    public HoaDon(String maHoaDon, Date thoiGianTao, NhanVien nhanVien, KhachHang khachHang, KhuyenMai khuyenMai, PhieuDatVe phieuDatVe) {
        this.maHoaDon = maHoaDon;
        this.thoiGianTao = thoiGianTao;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.khuyenMai = khuyenMai;
        this.phieuDatVe = phieuDatVe;
        this.danhSachThue = new ArrayList<>();
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

    public PhieuDatVe getPhieuDatVe() { return phieuDatVe; }
    public void setPhieuDatVe(PhieuDatVe phieuDatVe) { this.phieuDatVe = phieuDatVe; }

    public List<Thue> getDanhSachThue() { return danhSachThue; }
    public void setDanhSachThue(List<Thue> danhSachThue) { this.danhSachThue = danhSachThue; }

    public double getTongTienGoc() { return tongTienGoc; }
    public void setTongTienGoc(double tongTienGoc) { this.tongTienGoc = Math.max(0, tongTienGoc); }

    public double getTongTienThue() { return tongTienThue; }
    public void setTongTienThue(double tongTienThue) { this.tongTienThue = Math.max(0, tongTienThue); }

    public double getTongThanhToan() { return tongThanhToan; }
    public void setTongThanhToan(double tongThanhToan) { this.tongThanhToan = Math.max(0, tongThanhToan); }

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
        this.tongTienGoc = 0;
        for(ChiTietHoaDon ct : chiTietHoaDons) {
            this.tongTienGoc += ct.getThanhTien();
        }
        
        this.tongTienThue = 0;
        if(danhSachThue != null) {
            for(Thue t : danhSachThue) {
                this.tongTienThue += this.tongTienGoc * t.getHeSoThue();
            }
        }
        
        double discount = 0;
        if(khuyenMai != null) {
            discount = this.tongTienGoc * khuyenMai.getHinhThucGiam();
        }
        
        this.tongThanhToan = this.tongTienGoc + this.tongTienThue - discount;
        return this.tongThanhToan;
    }
}
