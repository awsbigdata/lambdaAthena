package com.amazonaws.dp;

import java.sql.*;
import java.util.Properties;

/**
 * Created by srramas on 10/26/17.
 */
public class MysqlTest {


    static final String athenaUrl = "jdbc:mysql://mysql57.c9ujozbff8fu.us-east-1.rds.amazonaws.com:3306/mysqldb57?useSSL=false";


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties info = new Properties();
            info.put("user", "mysqluser");
            info.put("password", "mysqlpassword");
            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);


            DatabaseMetaData dbmd = conn.getMetaData();

            ResultSet rs = dbmd.getTypeInfo();
            while (rs.next()) {
                String typeName = rs.getString("TYPE_NAME");
                short dataType = rs.getShort("DATA_TYPE");
                String createParams = rs.getString("CREATE_PARAMS");
                int nullable = rs.getInt("NULLABLE");
                boolean caseSensitive = rs.getBoolean("CASE_SENSITIVE");
                System.out.println("DBMS type " + typeName + ":");
                System.out.println("     java.sql.Types:  " + dataType);
                System.out.print("     parameters used to create: ");
                System.out.println(createParams);
                System.out.println("     nullable?:  " + nullable);
                System.out.print("     case sensitive?:  ");
                System.out.println(caseSensitive);
                System.out.println("");

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
