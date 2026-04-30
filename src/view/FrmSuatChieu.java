package view;

import java.util.List;
import javax.swing.JTextField;
import controller.SuatChieu_Controller;
import model.SuatChieu;

public class FrmSuatChieu extends FrmBaseManager {

    private JTextField txtmasuatchieu;
    private JTextField txtthoigian;
    private JTextField txtgiave;
    private JTextField txtmaphim;
    private JTextField txtmaphongchieu;
    
    private SuatChieu_Controller controller;

    public FrmSuatChieu() {
        super("Quản lý Suất Chiếu", new String[]{"Mã SC", "Thời Gian", "Giá Vé", "Mã Phim", "Mã Phòng"});
        
        // Khởi tạo các ô nhập liệu
        txtmasuatchieu = createTextField("MaSuatChieu");
        txtthoigian = createTextField("ThoiGian");
        txtgiave = createTextField("GiaVe");
        txtmaphim = createTextField("MaPhim");
        txtmaphongchieu = createTextField("MaPhongChieu");
    }
    
    public void setController(SuatChieu_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaSuatChieu() { return txtmasuatchieu; }
    public JTextField getTxtThoiGian() { return txtthoigian; }
    public JTextField getTxtGiaVe() { return txtgiave; }
    public JTextField getTxtMaPhim() { return txtmaphim; }
    public JTextField getTxtMaPhongChieu() { return txtmaphongchieu; }

    public void updateTable(List<SuatChieu> list) {
        tableModel.setRowCount(0);
        for (SuatChieu item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[5];
            for (int i=0; i<5; i++) row[i] = item.toString(); 
            tableModel.addRow(row);
        }
    }
}
