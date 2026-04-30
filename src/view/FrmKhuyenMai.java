package view;

import java.util.List;
import javax.swing.JTextField;
import controller.KhuyenMai_Controller;
import model.KhuyenMai;

public class FrmKhuyenMai extends FrmBaseManager {

    private JTextField txtmakhuyenmai;
    private JTextField txttenkhuyenmai;
    private JTextField txthinhthucgiam;
    
    private KhuyenMai_Controller controller;

    public FrmKhuyenMai() {
        super("Quản lý Khuyến Mãi", new String[]{"Mã KM", "Tên KM", "Mức Giảm"});
        
        // Khởi tạo các ô nhập liệu
        txtmakhuyenmai = createTextField("MaKhuyenMai");
        txttenkhuyenmai = createTextField("TenKhuyenMai");
        txthinhthucgiam = createTextField("HinhThucGiam");
    }
    
    public void setController(KhuyenMai_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaKhuyenMai() { return txtmakhuyenmai; }
    public JTextField getTxtTenKhuyenMai() { return txttenkhuyenmai; }
    public JTextField getTxtHinhThucGiam() { return txthinhthucgiam; }

    public void updateTable(List<KhuyenMai> list) {
        tableModel.setRowCount(0);
        for (KhuyenMai item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[3];
            for (int i=0; i<3; i++) row[i] = item.toString(); 
            tableModel.addRow(row);
        }
    }
}
