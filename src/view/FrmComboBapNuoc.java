package view;

import java.util.List;
import javax.swing.JTextField;
import controller.ComboBapNuoc_Controller;
import model.ComboBapNuoc;

public class FrmComboBapNuoc extends FrmBaseManager {

    private JTextField txtmacombo;
    private JTextField txttencombo;
    private JTextField txtgiabancoban;
    
    private ComboBapNuoc_Controller controller;

    public FrmComboBapNuoc() {
        super("Quản lý Combo Bắp Nước", new String[]{"Mã Combo", "Tên Combo", "Giá Bán"});
        
        // Khởi tạo các ô nhập liệu
        txtmacombo = createTextField("MaCombo");
        txttencombo = createTextField("TenCombo");
        txtgiabancoban = createTextField("GiaBanCoBan");
    }
    
    public void setController(ComboBapNuoc_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaCombo() { return txtmacombo; }
    public JTextField getTxtTenCombo() { return txttencombo; }
    public JTextField getTxtGiaBanCoBan() { return txtgiabancoban; }

    public void updateTable(List<ComboBapNuoc> list) {
        tableModel.setRowCount(0);
        for (ComboBapNuoc item : list) {
            Object[] row = new Object[] {
                item.getMaSanPham(),
                item.getTenSanPham(),
                item.getGiaBanCoBan()
            };
            tableModel.addRow(row);
        }
    }
}
