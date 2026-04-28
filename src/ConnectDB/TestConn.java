package ConnectDB;

public class TestConn {
    public static void main(String[] args) {
        // Vì ở chung package dao nên không cần ghi dao.DBConnection
        if (DBConnection.getInstance() != null) {
            System.out.println("Ket noi thanh cong!");
        } else {
            System.out.println("Ket noi that bai!");
        }
    }
}