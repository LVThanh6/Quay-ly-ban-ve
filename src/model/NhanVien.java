package model;

public class NhanVien {
    private String maNhanVien;
    private String hoTen;
    private String matKhau;
    private String sdt;
    private double luongCoBan;
    private ChucVu vaiTro;

    // Hàm khởi tạo rỗng (Mặc định)
    public NhanVien() {
    }

    // Hàm khởi tạo đầy đủ tham số
    public NhanVien(String maNhanVien, String hoTen, String matKhau, String sdt, double luongCoBan, ChucVu vaiTro) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.luongCoBan = luongCoBan;
        this.vaiTro = vaiTro;
    }

    // ==========================================
    // CÁC HÀM GETTER VÀ SETTER
    // ==========================================

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = Math.max(0, luongCoBan);
    }

    public ChucVu getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(ChucVu vaiTro) {
        this.vaiTro = vaiTro;
    }

    // Ghi đè phương thức toString để tiện cho việc in ra console kiểm tra (Debug)
    @Override
    public String toString() {
        return "NhanVien{" +
                "maNhanVien='" + maNhanVien + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                '}';
    }
}