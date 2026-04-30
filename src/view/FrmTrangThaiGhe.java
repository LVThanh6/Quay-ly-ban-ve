package view;

import java.util.List;
import javax.swing.JTextField;
import controller.TrangThaiGhe_Controller;
import model.TrangThaiGhe;

public class FrmTrangThaiGhe extends FrmBaseManager {

    private JTextField txtmatrangthaighe;
    private JTextField txtmaghe;
    private JTextField txtmasuatchieu;
    private JTextField txttrangthai;
    private JTextField txtghichu;
    
    private TrangThaiGhe_Controller controller;

    public FrmTrangThaiGhe() {
        super("Trạng Thái Ghế", new String[]{"Mã Trạng Thái", "Mã Ghế", "Mã SC", "Trạng Thái", "Ghi Chú"});
        
        // Khởi tạo các ô nhập liệu
        txtmatrangthaighe = createTextField("MaTrangThaiGhe");
        txtmaghe = createTextField("MaGhe");
        txtmasuatchieu = createTextField("MaSuatChieu");
        txttrangthai = createTextField("TrangThai");
        txtghichu = createTextField("GhiChu");
    }
    
    public void setController(TrangThaiGhe_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaTrangThaiGhe() { return txtmatrangthaighe; }
    public JTextField getTxtMaGhe() { return txtmaghe; }
    public JTextField getTxtMaSuatChieu() { return txtmasuatchieu; }
    public JTextField getTxtTrangThai() { return txttrangthai; }
    public JTextField getTxtGhiChu() { return txtghichu; }

    public void updateTable(List<TrangThaiGhe> list) {
        tableModel.setRowCount(0);
        for (TrangThaiGhe item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[5];
            for (int i=0; i<5; i++) row[i] = item.toString(); 
            tableModel.addRow(row);
        }
    }
}
