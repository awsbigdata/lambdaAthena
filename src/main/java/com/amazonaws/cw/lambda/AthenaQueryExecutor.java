package com.amazonaws.cw.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
/**
 * Created by srramas on 10/26/17.
 */
public class AthenaQueryExecutor {

    public static ArrayList<String> executeQuery(String athenaJDBCUrl, String stagingDir, int timeout, String query)
            throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
        } catch (ClassNotFoundException e) {
            throw e;
        }
        Properties props = new Properties();
        props.put("s3_staging_dir", stagingDir);
        props.put("aws_credentials_provider_class","com.amazonaws.athena.jdbc.shaded.com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
        ArrayList<String> dbList = new ArrayList<String>();
        DriverManager.setLoginTimeout(timeout);
        try (Connection athenaConnection = DriverManager
                .getConnection(athenaJDBCUrl, props);
             Statement statement = athenaConnection.createStatement()) {
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                dbList.add(rs.getString(1));
            }
            rs.close();
        } catch (SQLException e) {
            throw e;
        }
        return dbList;
    }
}
