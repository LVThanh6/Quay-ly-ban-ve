package view;

public class Starting {

    public static void main(String[] args) {
        // Sử dụng giao diện mặc định của hệ điều hành
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        // Kết nối cơ sở dữ liệu trước khi chạy ứng dụng
        ConnectDB.DBConnection.getInstance().connect();

        // Chỉ duy nhất lớp Starting chứa hàm main.
        // Điều hướng luồng đi từ Login đến giao diện chính.
        LoginGUI.start();
    }
}
