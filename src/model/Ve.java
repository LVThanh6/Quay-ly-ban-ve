package model;

public class Ve extends SanPham {
    private String trangThai; // "Hợp lệ", "Đã sử dụng", "Đã hủy"
    private double phiDichVu;
    private double giaVeThucTe;
    private TrangThaiGhe trangThaiGhe; // Chứa thông tin SuatChieu và Ghe

    public Ve() {
    }

    public Ve(String maVe, String trangThai, double phiDichVu, double giaVeThucTe, TrangThaiGhe trangThaiGhe) {
        super(maVe, "Vé xem phim: " + trangThaiGhe.getSuatChieu().getPhim().getTenPhim(), giaVeThucTe);
        this.trangThai = trangThai;
        this.phiDichVu = phiDichVu;
        this.giaVeThucTe = giaVeThucTe;
        this.trangThaiGhe = trangThaiGhe;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getPhiDichVu() {
        return phiDichVu;
    }

    public void setPhiDichVu(double phiDichVu) {
        this.phiDichVu = phiDichVu;
    }

    public double getGiaVeThucTe() {
        return giaVeThucTe;
    }

    public void setGiaVeThucTe(double giaVeThucTe) {
        this.giaVeThucTe = giaVeThucTe;
    }

    public TrangThaiGhe getTrangThaiGhe() {
        return trangThaiGhe;
    }

    public void setTrangThaiGhe(TrangThaiGhe trangThaiGhe) {
        this.trangThaiGhe = trangThaiGhe;
    }

    public boolean kiemTraTrangThaiGhe() {
        return "Trống".equals(this.trangThaiGhe.getTrangThai());
    }

    public void dieuChinhGhe(Ghe gheMoi) {
        this.trangThaiGhe.setGhe(gheMoi);
    }

    @Override
    public double tinhGiaThucTe() {
        return giaVeThucTe + phiDichVu;
    }
}
