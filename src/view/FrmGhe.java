package view;

import java.util.List;
import javax.swing.JTextField;
import controller.Ghe_Controller;
import model.Ghe;

public class FrmGhe extends FrmBaseManager {

    private JTextField txtmaghe;
    private JTextField txthangghe;
    private JTextField txtcotghe;
    private JTextField txtloaighe;
    private JTextField txttrangthai;
    private JTextField txtmaphongchieu;
    
    private Ghe_Controller controller;

    public FrmGhe() {
        super("Quản lý Ghế", new String[]{"Mã Ghế", "Hàng", "Cột", "Loại", "Trạng Thái", "Mã Phòng"});
        
        // Khởi tạo các ô nhập liệu
        txtmaghe = createTextField("MaGhe");
        txthangghe = createTextField("HangGhe");
        txtcotghe = createTextField("CotGhe");
        txtloaighe = createTextField("LoaiGhe");
        txttrangthai = createTextField("TrangThai");
        txtmaphongchieu = createTextField("MaPhongChieu");
    }
    
    public void setController(Ghe_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaGhe() { return txtmaghe; }
    public JTextField getTxtHangGhe() { return txthangghe; }
    public JTextField getTxtCotGhe() { return txtcotghe; }
    public JTextField getTxtLoaiGhe() { return txtloaighe; }
    public JTextField getTxtTrangThai() { return txttrangthai; }
    public JTextField getTxtMaPhongChieu() { return txtmaphongchieu; }

    public void updateTable(List<Ghe> list) {
        tableModel.setRowCount(0);
        for (Ghe item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[6];
            for (int i=0; i<6; i++) row[i] = item.toString(); 
            tableModel.addRow(row);
        }
    }
}
