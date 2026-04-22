package model;

public class Ghe {
    private String maGhe;
    private String loaiGhe; // Thuong, VIP
    private PhongChieu phongChieu;

    public Ghe() {
    }

    public Ghe(String maGhe, String loaiGhe, PhongChieu phongChieu) {
        this.maGhe = maGhe;
        this.loaiGhe = loaiGhe;
        this.phongChieu = phongChieu;
    }

    public String getMaGhe() {
        return maGhe;
    }

    public void setMaGhe(String maGhe) {
        this.maGhe = maGhe;
    }

    public String getLoaiGhe() {
        return loaiGhe;
    }

    public void setLoaiGhe(String loaiGhe) {
        this.loaiGhe = loaiGhe;
    }

    public PhongChieu getPhongChieu() {
        return phongChieu;
    }

    public void setPhongChieu(PhongChieu phongChieu) {
        this.phongChieu = phongChieu;
    }

    @Override
    public String toString() {
        return maGhe; // Trả về mã ghế như: G1, G2
    }
}
