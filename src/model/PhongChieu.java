package model;

public class PhongChieu {
    private String maPhongChieu;
    private int soLuongGhe;
    private String dinhDangPhong;

    public PhongChieu() {}

    public PhongChieu(String maPhongChieu, int soLuongGhe, String dinhDangPhong) {
        this.maPhongChieu = maPhongChieu;
        this.soLuongGhe = soLuongGhe;
        this.dinhDangPhong = dinhDangPhong;
    }

    public String getMaPhongChieu() { return maPhongChieu; }
    public void setMaPhongChieu(String maPhongChieu) { this.maPhongChieu = maPhongChieu; }

    public int getSoLuongGhe() { return soLuongGhe; }
    public void setSoLuongGhe(int soLuongGhe) { this.soLuongGhe = soLuongGhe; }

    public String getDinhDangPhong() { return dinhDangPhong; }
    public void setDinhDangPhong(String dinhDangPhong) { this.dinhDangPhong = dinhDangPhong; }
}
