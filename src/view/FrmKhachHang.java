package view;

import java.util.List;
import javax.swing.JTextField;
import controller.KhachHang_Controller;
import model.KhachHang;

public class FrmKhachHang extends FrmBaseManager {

    private JTextField txtsdt;
    private JTextField txthoten;
    private JTextField txtngaysinh;
    
    private KhachHang_Controller controller;

    public FrmKhachHang() {
        super("Quản lý Khách Hàng", new String[]{"Số điện thoại", "Tên KH", "Ngày Sinh"});
        
        // Khởi tạo các ô nhập liệu
        txtsdt = createTextField("Số điện thoại");
        txthoten = createTextField("Tên Khách Hàng");
        txtngaysinh = createTextField("Ngày Sinh (dd/MM/yyyy)");
    }
    
    public void setController(KhachHang_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtSDT() { return txtsdt; }
    public JTextField getTxtHoTen() { return txthoten; }
    public JTextField getTxtNgaySinh() { return txtngaysinh; }

    public void updateTable(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[3];
            row[0] = item.getsDT();
            row[1] = item.getHoTen();
            row[2] = item.getNgaySinh() != null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(item.getNgaySinh()) : "";
            tableModel.addRow(row);
        }
    }
}
