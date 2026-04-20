package model;

public class KhuyenMai {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private double hinhThucGiam; // phần trăm (%) hoặc số tiền tuyệt đối tuỳ logic

    public KhuyenMai() {}

    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, double hinhThucGiam) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.hinhThucGiam = hinhThucGiam;
    }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(String maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }

    public String getTenKhuyenMai() { return tenKhuyenMai; }
    public void setTenKhuyenMai(String tenKhuyenMai) { this.tenKhuyenMai = tenKhuyenMai; }

    public double getHinhThucGiam() { return hinhThucGiam; }
    public void setHinhThucGiam(double hinhThucGiam) { this.hinhThucGiam = hinhThucGiam; }

    public boolean kiemTraDieuKienApDung(String dieukien) {
        // Logic kiểm tra
        return true;
    }
}
