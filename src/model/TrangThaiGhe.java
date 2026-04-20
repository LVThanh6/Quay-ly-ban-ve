package model;

public class TrangThaiGhe {
    private Ghe ghe;
    private SuatChieu suatChieu;
    private String trangThai; // "Trống", "Đang giữ", "Đã bán"

    public TrangThaiGhe() {}

    public TrangThaiGhe(Ghe ghe, SuatChieu suatChieu, String trangThai) {
        this.ghe = ghe;
        this.suatChieu = suatChieu;
        this.trangThai = trangThai;
    }

    public Ghe getGhe() { return ghe; }
    public void setGhe(Ghe ghe) { this.ghe = ghe; }

    public SuatChieu getSuatChieu() { return suatChieu; }
    public void setSuatChieu(SuatChieu suatChieu) { this.suatChieu = suatChieu; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public void capNhatTrangThai(String trangThaiMoi) {
        this.trangThai = trangThaiMoi;
    }
}
