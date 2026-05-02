package model;

import java.time.LocalDateTime;

public class VeFullInfo {
    private String maVe;
    private String maGhe;
    private String tenPhim;
    private String maPhong;
    private LocalDateTime thoiGianChieu;
    private String sdtKhach;
    private LocalDateTime ngayDat;
    private String trangThai;
    private String maPhieuDat;
    private double giaVe;

    public VeFullInfo() {}

    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }

    public String getMaGhe() { return maGhe; }
    public void setMaGhe(String maGhe) { this.maGhe = maGhe; }

    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }

    public LocalDateTime getThoiGianChieu() { return thoiGianChieu; }
    public void setThoiGianChieu(LocalDateTime thoiGianChieu) { this.thoiGianChieu = thoiGianChieu; }

    public String getSdtKhach() { return sdtKhach; }
    public void setSdtKhach(String sdtKhach) { this.sdtKhach = sdtKhach; }

    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getMaPhieuDat() { return maPhieuDat; }
    public void setMaPhieuDat(String maPhieuDat) { this.maPhieuDat = maPhieuDat; }

    public double getGiaVe() { return giaVe; }
    public void setGiaVe(double giaVe) { this.giaVe = giaVe; }
}
