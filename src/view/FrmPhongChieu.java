package view;

import java.util.List;
import javax.swing.JTextField;
import controller.PhongChieu_Controller;
import model.PhongChieu;

public class FrmPhongChieu extends FrmBaseManager {

    private JTextField txtmaphongchieu;
    private JTextField txttenphong;
    private JTextField txtsoluongghe;
    private JTextField txttrangthai;
    private JTextField txtdinhdangphong;
    
    private PhongChieu_Controller controller;

    public FrmPhongChieu() {
        super("Quản lý Phòng Chiếu", new String[]{"Mã Phòng", "Tên Phòng", "Số Lượng Ghế", "Trạng Thái", "Định Dạng"});
        
        // Khởi tạo các ô nhập liệu
        txtmaphongchieu = createTextField("MaPhongChieu");
        txttenphong = createTextField("TenPhong");
        txtsoluongghe = createTextField("SoLuongGhe");
        txttrangthai = createTextField("TrangThai");
        txtdinhdangphong = createTextField("DinhDangPhong");
    }
    
    public void setController(PhongChieu_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaPhongChieu() { return txtmaphongchieu; }
    public JTextField getTxtTenPhong() { return txttenphong; }
    public JTextField getTxtSoLuongGhe() { return txtsoluongghe; }
    public JTextField getTxtTrangThai() { return txttrangthai; }
    public JTextField getTxtDinhDangPhong() { return txtdinhdangphong; }

    public void updateTable(List<PhongChieu> list) {
        tableModel.setRowCount(0);
        for (PhongChieu item : list) {
            // FIXME: Ánh xạ đúng thuộc tính model vào mảng Object.
            Object[] row = new Object[5];
            for (int i=0; i<5; i++) row[i] = item.toString(); 
            tableModel.addRow(row);
        }
    }
}
