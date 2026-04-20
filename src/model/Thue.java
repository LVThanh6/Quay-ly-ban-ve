package model;

public class Thue {
    private String maThue;
    private String tenThue;
    private double mucThue; // Dạng % (ví dụ 0.1 cho 10%)

    public Thue() {}

    public Thue(String maThue, String tenThue, double mucThue) {
        this.maThue = maThue;
        this.tenThue = tenThue;
        this.mucThue = mucThue;
    }

    public String getMaThue() { return maThue; }
    public void setMaThue(String maThue) { this.maThue = maThue; }

    public String getTenThue() { return tenThue; }
    public void setTenThue(String tenThue) { this.tenThue = tenThue; }

    public double getMucThue() { return mucThue; }
    public void setMucThue(double mucThue) { this.mucThue = mucThue; }
}
