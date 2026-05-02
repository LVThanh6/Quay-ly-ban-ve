package view;

public class TestMainGUI {
    public static void main(String[] args) {
        try {
            model.NhanVien nv = new model.NhanVien();
            nv.setVaiTro(model.ChucVu.QUAN_LY);
            view.MainGUI main = new view.MainGUI(nv);
            System.out.println("MainGUI created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
