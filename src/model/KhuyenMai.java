package model;

public class KhuyenMai {
    private String maKhuyenMai;
    private String tenKhuyenMai;
    private double hinhThucGiam; // phần trăm (%) hoặc số tiền tuyệt đối tuỳ logic
    private double tongTienToiThieu;

    public KhuyenMai() {
    }

    public KhuyenMai(String maKhuyenMai, String tenKhuyenMai, double hinhThucGiam, double tongTienToiThieu) {
        this.maKhuyenMai = maKhuyenMai;
        this.tenKhuyenMai = tenKhuyenMai;
        this.hinhThucGiam = hinhThucGiam;
        this.tongTienToiThieu = tongTienToiThieu;
    }

    public String getMaKhuyenMai() {
        return maKhuyenMai;
    }

    public void setMaKhuyenMai(String maKhuyenMai) {
        this.maKhuyenMai = maKhuyenMai;
    }

    public String getTenKhuyenMai() {
        return tenKhuyenMai;
    }

    public void setTenKhuyenMai(String tenKhuyenMai) {
        this.tenKhuyenMai = tenKhuyenMai;
    }

    public double getHinhThucGiam() {
        return hinhThucGiam;
    }

    public void setHinhThucGiam(double hinhThucGiam) {
        this.hinhThucGiam = Math.max(0, hinhThucGiam);
    }

    public double getHeSoGiam() {
        return hinhThucGiam / 100.0;
    }

    public double getTongTienToiThieu() {
        return tongTienToiThieu;
    }

    public void setTongTienToiThieu(double tongTienToiThieu) {
        this.tongTienToiThieu = Math.max(0, tongTienToiThieu);
    }

    public boolean kiemTraDieuKienApDung(String dieukien) {
        // Logic kiểm tra
        return true;
    }
}
