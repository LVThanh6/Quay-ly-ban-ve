package view;

import java.util.List;
import javax.swing.JTextField;
import controller.Thue_Controller;
import model.Thue;

public class FrmThue extends FrmBaseManager {

    private JTextField txtmathue;
    private JTextField txttenthue;
    private JTextField txtmucthue;
    
    private Thue_Controller controller;

    public FrmThue() {
        super("Quản lý Thuê", new String[]{"Mã Thuê", "Tên Thuê", "Mức Thuê"});
        
        // Khởi tạo các ô nhập liệu
        txtmathue = createTextField("MaThue");
        txttenthue = createTextField("TenThue");
        txtmucthue = createTextField("MucThue");
    }
    
    public void setController(Thue_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaThue() { return txtmathue; }
    public JTextField getTxtTenThue() { return txttenthue; }
    public JTextField getTxtMucThue() { return txtmucthue; }

    public void updateTable(List<Thue> list) {
        tableModel.setRowCount(0);
        for (Thue item : list) {
            Object[] row = new Object[] {
                item.getMaLoaiThue(),
                item.getTenLoaiThue(),
                item.getMucThuePhanTram()
            };
            tableModel.addRow(row);
        }
    }
}
