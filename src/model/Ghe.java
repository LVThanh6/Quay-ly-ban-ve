package model;

public class Ghe {
    private String maGhe;
    private String loaiGhe; // Thuong, VIP
    private String hangGhe;
    private int cotGhe;
    private String trangThai; // Trống, Đã đặt, Bảo trì
    private PhongChieu phongChieu;

    public Ghe() {
    }

    public Ghe(String maGhe, String loaiGhe, String hangGhe, int cotGhe, String trangThai, PhongChieu phongChieu) {
        this.maGhe = maGhe;
        this.loaiGhe = loaiGhe;
        this.hangGhe = hangGhe;
        this.cotGhe = cotGhe;
        this.trangThai = trangThai;
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

    public String getHangGhe() { return hangGhe; }
    public void setHangGhe(String hangGhe) { this.hangGhe = hangGhe; }

    public int getCotGhe() { return cotGhe; }
    public void setCotGhe(int cotGhe) { this.cotGhe = cotGhe; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return maGhe; // Trả về mã ghế như: G1, G2
    }
}
