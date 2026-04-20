package model;

public class InHoaDon {
    private String maMayIn;
    private String khoGiay;
    private String mauIn;

    public InHoaDon() {}

    public InHoaDon(String maMayIn, String khoGiay, String mauIn) {
        this.maMayIn = maMayIn;
        this.khoGiay = khoGiay;
        this.mauIn = mauIn;
    }

    public String getMaMayIn() { return maMayIn; }
    public void setMaMayIn(String maMayIn) { this.maMayIn = maMayIn; }

    public String getKhoGiay() { return khoGiay; }
    public void setKhoGiay(String khoGiay) { this.khoGiay = khoGiay; }

    public String getMauIn() { return mauIn; }
    public void setMauIn(String mauIn) { this.mauIn = mauIn; }

    public boolean inHoaDonHD(HoaDon hd) {
        // Logic gửi API máy in (mô phỏng in thành công)
        System.out.println("Đang in Hóa Đơn: " + hd.getMaHoaDon() + " ra " + maMayIn + " khổ " + khoGiay);
        return true;
    }

    public String dinhDangDuLieu(HoaDon hd) {
        return "=== HÓA ĐƠN " + hd.getMaHoaDon() + " ===\n" +
               "Tổng tiền: " + hd.tinhTongTien() + " VNĐ\n" +
               "Cảm ơn quý khách!";
    }
}
