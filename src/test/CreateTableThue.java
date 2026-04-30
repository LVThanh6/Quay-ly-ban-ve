package test;

import ConnectDB.DBConnection;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTableThue {
    public static void main(String[] args) {
        try {
            DBConnection.getInstance().connect();
            Connection con = DBConnection.getInstance().getCon();
            if (con == null) {
                System.out.println("Connection failed!");
                return;
            }
            Statement stmt = con.createStatement();
            String sql = "CREATE TABLE Thue (" +
                         "MaThue varchar(50) PRIMARY KEY, " +
                         "TenThue nvarchar(255), " +
                         "MucThue float" +
                         ")";
            stmt.executeUpdate(sql);
            System.out.println("Thue table created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
