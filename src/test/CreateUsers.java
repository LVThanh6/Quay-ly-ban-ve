package test;

import ConnectDB.DBConnection;
import dao.NhanVien_DAO;
import model.ChucVu;
import model.NhanVien;

public class CreateUsers {
    public static void main(String[] args) {
        // Kết nối CSDL
        DBConnection.getInstance().connect();
        
        NhanVien_DAO dao = new NhanVien_DAO();
        
        // Tạo tài khoản quản lý
        NhanVien admin = new NhanVien("admin", "Admin System", "admin", "0900000000", 20000000, ChucVu.QUAN_LY);
        // Tạo tài khoản nhân viên
        NhanVien staff = new NhanVien("staff", "Staff Ban Ve", "staff", "0900000001", 10000000, ChucVu.NHAN_VIEN);
        
        boolean okAdmin = dao.addNhanVien(admin);
        boolean okStaff = dao.addNhanVien(staff);
        
        if (okAdmin) {
            System.out.println("Tạo tài khoản quản lý thành công: admin/admin");
        } else {
            System.out.println("Tạo tài khoản quản lý thất bại (có thể đã tồn tại).");
        }
        
        if (okStaff) {
            System.out.println("Tạo tài khoản nhân viên thành công: staff/staff");
        } else {
            System.out.println("Tạo tài khoản nhân viên thất bại (có thể đã tồn tại).");
        }
        
        // Ngắt kết nối
        DBConnection.getInstance().disconnect();
    }
}
