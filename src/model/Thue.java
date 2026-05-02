package model;

public class Thue {
    private String maLoaiThue;
    private String tenLoaiThue;
    private double mucThuePhanTram; // Dạng số nguyên (ví dụ 10 cho 10%)

    public Thue() {
    }

    public Thue(String maLoaiThue, String tenLoaiThue, double mucThuePhanTram) {
        this.maLoaiThue = maLoaiThue;
        this.tenLoaiThue = tenLoaiThue;
        this.mucThuePhanTram = mucThuePhanTram;
    }

    public String getMaLoaiThue() { return maLoaiThue; }
    public void setMaLoaiThue(String maLoaiThue) { this.maLoaiThue = maLoaiThue; }

    public String getTenLoaiThue() { return tenLoaiThue; }
    public void setTenLoaiThue(String tenLoaiThue) { this.tenLoaiThue = tenLoaiThue; }

    public double getMucThuePhanTram() { return mucThuePhanTram; }
    public void setMucThuePhanTram(double mucThuePhanTram) { this.mucThuePhanTram = Math.max(0, mucThuePhanTram); }
    
    // Tiện ích để lấy hệ số (ví dụ 0.1)
    public double getHeSoThue() {
        return mucThuePhanTram / 100.0;
    }
}
