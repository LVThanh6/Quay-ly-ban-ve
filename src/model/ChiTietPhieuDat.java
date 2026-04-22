package model;

public class ChiTietPhieuDat {
    private String maChiTietPhieuDat;
    private double giaTamTinh;
    private PhieuDatVe phieuDatVe;
    private Ve ve;

    public ChiTietPhieuDat() {
    }

    public ChiTietPhieuDat(String maChiTietPhieuDat, double giaTamTinh, PhieuDatVe phieuDatVe, Ve ve) {
        this.maChiTietPhieuDat = maChiTietPhieuDat;
        this.giaTamTinh = giaTamTinh;
        this.phieuDatVe = phieuDatVe;
        this.ve = ve;
    }

    public String getMaChiTietPhieuDat() {
        return maChiTietPhieuDat;
    }

    public void setMaChiTietPhieuDat(String maChiTietPhieuDat) {
        this.maChiTietPhieuDat = maChiTietPhieuDat;
    }

    public double getGiaTamTinh() {
        return giaTamTinh;
    }

    public void setGiaTamTinh(double giaTamTinh) {
        this.giaTamTinh = giaTamTinh;
    }

    public PhieuDatVe getPhieuDatVe() {
        return phieuDatVe;
    }

    public void setPhieuDatVe(PhieuDatVe phieuDatVe) {
        this.phieuDatVe = phieuDatVe;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
    }

    public double tinhGiaTamTinh() {
        return this.ve.tinhGiaThucTe();
    }
}
