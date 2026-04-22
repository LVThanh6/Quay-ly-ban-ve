package model;

public class ComboBapNuoc extends SanPham {

    public ComboBapNuoc() {
    }

    public ComboBapNuoc(String maCombo, String tenCombo, double giaBanCoBan) {
        super(maCombo, tenCombo, giaBanCoBan);
    }

    @Override
    public double tinhGiaThucTe() {
        return this.giaBanCoBan;
    }
}
