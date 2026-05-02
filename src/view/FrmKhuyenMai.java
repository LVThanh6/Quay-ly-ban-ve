package view;

import java.util.List;
import javax.swing.JTextField;
import controller.KhuyenMai_Controller;
import model.KhuyenMai;

public class FrmKhuyenMai extends FrmBaseManager {

    private JTextField txtmakhuyenmai;
    private JTextField txttenkhuyenmai;
    private JTextField txthinhthucgiam;
    private JTextField txttongtientoithieu;
    
    private KhuyenMai_Controller controller;

    public FrmKhuyenMai() {
        super("Quản lý Khuyến Mãi", new String[]{"Mã KM", "Tên KM", "Mức Giảm (%)", "Đơn tối thiểu"});
        
        // Khởi tạo các ô nhập liệu
        txtmakhuyenmai = createTextField("MaKhuyenMai");
        txttenkhuyenmai = createTextField("TenKhuyenMai");
        txthinhthucgiam = createTextField("HinhThucGiam");
        txttongtientoithieu = createTextField("TongTienToiThieu");
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
    public JTextField getTxtTongTienToiThieu() { return txttongtientoithieu; }

    public void updateTable(List<KhuyenMai> list) {
        tableModel.setRowCount(0);
        for (KhuyenMai item : list) {
            Object[] row = new Object[] {
                item.getMaKhuyenMai(),
                item.getTenKhuyenMai(),
                item.getHinhThucGiam(),
                item.getTongTienToiThieu()
            };
            tableModel.addRow(row);
        }
    }
}
