package model;

public abstract class SanPham {
    protected String maSanPham;
    protected String tenSanPham;
    protected double giaBanCoBan;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, double giaBanCoBan) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBanCoBan = giaBanCoBan;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaBanCoBan() {
        return giaBanCoBan;
    }

    public void setGiaBanCoBan(double giaBanCoBan) {
        this.giaBanCoBan = Math.max(0, giaBanCoBan);
    }

    // Phương thức trừu tượng nếu cần
    public abstract double tinhGiaThucTe();
}
