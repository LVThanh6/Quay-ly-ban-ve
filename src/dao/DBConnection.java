package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Thay đổi MSI\\SQLEXPRESS nếu tên server của bạn khác
        	String url = "jdbc:sqlserver://MSI\\SQLEXPRESS:55252;"
        			+ "databaseName=QuanLyRapPhim;"
        			+ "encrypt=true;"
        			+ "trustServerCertificate=true;";
        	String user = "sa";
        	String pass = "sapassword"; // Đảm bảo khớp 100%
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
