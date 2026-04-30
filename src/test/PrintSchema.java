package test;

import ConnectDB.DBConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class PrintSchema {
    public static void main(String[] args) {
        try {
            DBConnection.getInstance().connect();
            Connection con = DBConnection.getInstance().getCon();
            if (con == null) {
                System.out.println("Connection failed!");
                return;
            }
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("TABLE: " + tableName);
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    System.out.println("  - " + columnName + " (" + columnType + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
