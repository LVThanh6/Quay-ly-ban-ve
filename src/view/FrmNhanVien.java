package view;

import java.util.List;
import javax.swing.JTextField;
import controller.NhanVien_Controller;
import model.NhanVien;

public class FrmNhanVien extends FrmBaseManager {

    private JTextField txtmanhanvien;
    private JTextField txthoten;
    private JTextField txtmatkhau;
    private JTextField txtsdt;
    private JTextField txtluongcoban;
    private JTextField txtvaitro;
    
    private NhanVien_Controller controller;

    public FrmNhanVien() {
        super("Quản lý Nhân Viên", new String[]{"Mã NV", "Họ Tên", "Mật khẩu", "SĐT", "Lương CB", "Vai trò"});
        
        // Khởi tạo các ô nhập liệu
        txtmanhanvien = createTextField("MaNhanVien");
        txthoten = createTextField("HoTen");
        txtmatkhau = createTextField("MatKhau");
        txtsdt = createTextField("SDT");
        txtluongcoban = createTextField("LuongCoBan");
        txtvaitro = createTextField("VaiTro");
    }
    
    public void setController(NhanVien_Controller controller) {
        this.controller = controller;
        btnThem.addActionListener(controller);
        btnSua.addActionListener(controller);
        btnXoa.addActionListener(controller);
        btnXoaTrang.addActionListener(controller);
    }

    public JTextField getTxtMaNhanVien() { return txtmanhanvien; }
    public JTextField getTxtHoTen() { return txthoten; }
    public JTextField getTxtMatKhau() { return txtmatkhau; }
    public JTextField getTxtSDT() { return txtsdt; }
    public JTextField getTxtLuongCoBan() { return txtluongcoban; }
    public JTextField getTxtVaiTro() { return txtvaitro; }

    public void updateTable(List<NhanVien> list) {
        tableModel.setRowCount(0);
        for (NhanVien item : list) {
            Object[] row = new Object[] {
                item.getMaNhanVien(),
                item.getHoTen(),
                item.getMatKhau(),
                item.getSdt(),
                item.getLuongCoBan(),
                item.getVaiTro() != null ? item.getVaiTro().name() : ""
            };
            tableModel.addRow(row);
        }
    }
}
