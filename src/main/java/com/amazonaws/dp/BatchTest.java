package com.amazonaws.dp;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by srramas on 9/12/17.
 */
public class BatchTest {

    public static void main(String[] args) throws SQLException, IOException {
        Connection dbConnection = getDBConnection();
        PreparedStatement preparedStatement = null;


        dbConnection.setAutoCommit(false);//commit trasaction manually

     /*   FileReader fileReader = new FileReader("/home/local/ANT/srramas/test/test.csv");
        CopyManager copyManager = new CopyManager((BaseConnection) dbConnection);

        copyManager.copyIn("COPY emp1 FROM STDIN WITH DELIMITER ','", fileReader );
        dbConnection.commit();
*/
       /* String insertTableSQL = "insert into tbposition (sec_cusip_id,part_id,app_shr_na_qty,app_shr_l_p_date,app_shr_na_p_q_ind,app_shr_na_p_f_cnt) values(?,?,?,?,?,?)";
        preparedStatement = dbConnection.prepareStatement(insertTableSQL);

        preparedStatement.setString(1, "101");
        preparedStatement.setLong(2, 1);
        preparedStatement.setString(3, "2");
        preparedStatement.setString(4, "3");

        preparedStatement.setString(5, "system");

        preparedStatement.setString(6, "6");

        preparedStatement.addBatch();

        preparedStatement.setString(1, "102");
        preparedStatement.setString(2, "3");
        preparedStatement.setString(3, "5");
        preparedStatement.setString(4, "6");

        preparedStatement.setString(5, "system");

        preparedStatement.setString(6, "10");

        preparedStatement.addBatch();
        preparedStatement.executeBatch();
        System.out.println("before commiting");

        dbConnection.commit();*/
    }



    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {
            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }
        try {

            dbConnection = DriverManager.getConnection(
                    "jdbc:postgresql://postdb.cluster-c9ujozbff8fu.us-east-1.rds.amazonaws.com:5432/postdb", "postuser","postpassword");
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        return dbConnection;

    }
}
