package model;

import java.util.Date;

public class KhachHang {
    private String sDT;
    private String hoTen;
    private Date ngaySinh;

    public KhachHang() {
    }

    public KhachHang(String sDT, String hoTen, Date ngaySinh) {
        this.sDT = sDT;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void dangKyThanhVien() {
        /* Logic đăng ký */ }

    public void capNhatThongTin() {
        /* Logic cập nhật */ }
}
