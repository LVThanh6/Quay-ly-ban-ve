package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class PhieuDatVe {
    private String maPhieuDat;
    private int soLuongVe;
    private Date ngayDat;
    private Date thoiGianGiuCho;
    private String trangThai; // "Chờ thanh toán", "Đã thanh toán", "Quá hạn", "Đã hủy"

    private KhachHang khachHang;
    private NhanVien nhanVien;
    private List<ChiTietPhieuDat> chiTietPhieuDats;

    public PhieuDatVe() {
        this.chiTietPhieuDats = new ArrayList<>();
    }

    public PhieuDatVe(String maPhieuDat, int soLuongVe, Date ngayDat, Date thoiGianGiuCho, String trangThai,
            KhachHang khachHang, NhanVien nhanVien) {
        this.maPhieuDat = maPhieuDat;
        this.soLuongVe = soLuongVe;
        this.ngayDat = ngayDat;
        this.thoiGianGiuCho = thoiGianGiuCho;
        this.trangThai = trangThai;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.chiTietPhieuDats = new ArrayList<>();
    }

    // Getters and Setter
    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    public int getSoLuongVe() {
        return soLuongVe;
    }

    public void setSoLuongVe(int soLuongVe) {
        this.soLuongVe = soLuongVe;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public Date getThoiGianGiuCho() {
        return thoiGianGiuCho;
    }

    public void setThoiGianGiuCho(Date thoiGianGiuCho) {
        this.thoiGianGiuCho = thoiGianGiuCho;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<ChiTietPhieuDat> getChiTietPhieuDats() {
        return chiTietPhieuDats;
    }

    public void setChiTietPhieuDats(List<ChiTietPhieuDat> chiTietPhieuDats) {
        this.chiTietPhieuDats = chiTietPhieuDats;
    }

    public void addChiTiet(ChiTietPhieuDat c) {
        this.chiTietPhieuDats.add(c);
        this.soLuongVe = this.chiTietPhieuDats.size();
    }

    public void taoPhieuDat() {
        /* Logic */ }

    public double tinhTongTienTamTinh() {
        double tong = 0;
        for (ChiTietPhieuDat ct : chiTietPhieuDats) {
            tong += ct.getGiaTamTinh();
        }
        return tong;
    }

    public void capNhatThongTinGhe() {
        /* Logic */ }

    public boolean kiemTraThoiHan() {
        return new Date().before(thoiGianGiuCho);
    }

    public void huyPhieuDat() {
        this.trangThai = "Đã hủy";
    }
}
