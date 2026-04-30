package ConnectDB;

public class TestConn {
    public static void main(String[] args) {
        DBConnection.getInstance().connect();
        if (DBConnection.getInstance().getCon() != null) {
            System.out.println("Ket noi thanh cong!");
        } else {
            System.out.println("Ket noi that bai!");
        }
    }
}