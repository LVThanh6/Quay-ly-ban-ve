package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	private static Connection con =null;
	private static DBConnection instance = new DBConnection();
	/**
	 * @return the instance
	 */
	public static DBConnection getInstance() {
		return instance;
	}
	
	public void connect() {
        // Chú ý dấu ":" sau jdbc và thêm trustServerCertificate
        String url = "jdbc:sqlserver://MSI\\SQLEXPRESS:1433;"
    			+ "databaseName=QuanLyRapPhim;"
    			+ "encrypt=true;"
    			+ "trustServerCertificate=true;";
        String user = "sa";
        String password = "sapassword";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void disconnect() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}


	/**
	 * @return the con
	 */
	public static Connection getCon() {
		return con;
	}


}
