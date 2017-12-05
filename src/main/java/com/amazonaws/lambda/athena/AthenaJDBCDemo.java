package com.amazonaws.lambda.athena;

/**
 * Created by srramas on 2/7/17.
 */


import com.amazonaws.athena.jdbc.AthenaQueryResultSet;
import com.amazonaws.athena.jdbc.AthenaResultSet;
import com.amazonaws.athena.jdbc.AthenaStatement;

import java.net.URL;
import java.sql.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;



public class AthenaJDBCDemo {

    static final String athenaUrl = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443/hive/hive_glue";

    public static void athenaTodynamo() {

        Connection conn = null;
        AthenaStatement statement = null;

        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", "s3://aws-athena-query-results-898623153764-us-east-1/");
            info.put("aws_credentials_provider_class","com.amazonaws.athena.jdbc.shaded.com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);
          DatabaseMetaData  dbmd = conn.getMetaData();

            ResultSet rs = dbmd.getTypeInfo();
            while (rs.next()) {
                String typeName = rs.getString("TYPE_NAME");
                short dataType = rs.getShort("DATA_TYPE");
                String createParams = rs.getString("CREATE_PARAMS");
                int nullable = rs.getInt("NULLABLE");
                boolean caseSensitive = rs.getBoolean("CASE_SENSITIVE");
                System.out.println("DBMS type " + typeName + ":");
                System.out.println("     java.sql.Types:  "  + dataType);
                System.out.print("     parameters used to create: ");
                System.out.println(createParams);
                System.out.println("     nullable?:  "  + nullable);
                System.out.print("     case sensitive?:  ");
                System.out.println(caseSensitive);
                System.out.println("");

            }



            System.out.println("Listing tables...");
            String databaseName = "sampledb";

            String sql = "msck repair table test";// in "+ databaseName;

            statement = (AthenaStatement) conn.createStatement();


            System.out.println("After Athena query execution");
            System.out.println("Listing tables...");

            ResultSet rs2 =  statement.executeQuery(sql);

           // System.out.println(athenaQueryResultSet.getQueryId());

            while (rs2.next()) {
                //Retrieve table column.
                String name = rs2.getString(1);

                System.out.println("Name: " + name);
            }
            rs2.close();

            System.out.println("After bathwrite constructed");

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception ex) {

            }
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        System.out.printf("Finished connectivity test.");
    }

    public static void main(String[] arg){
      athenaTodynamo();
        }

}