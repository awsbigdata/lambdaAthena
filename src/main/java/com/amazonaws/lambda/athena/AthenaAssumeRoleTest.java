package com.amazonaws.lambda.athena;

import com.amazonaws.athena.jdbc.AthenaStatement;

import java.sql.*;
import java.util.Properties;

/**
 * Created by srramas on 11/14/17.
 */
public class AthenaAssumeRoleTest {
    static final String athenaUrl = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443/hive/hive_glue";

    public static void athenaTodynamo() throws ClassNotFoundException, SQLException {

        Connection conn = null;
        AthenaStatement statement = null;


            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", "s3://aws-athena-query-results-898623153764-us-east-1/");
            info.put("aws_credentials_provider_class", "com.amazonaws.lambda.athena.CustomSTSCredentialsProvider");
            info.put("aws_credentials_provider_arguments","arn:aws:iam::898623153764:role/crossaccount,AKIAJHLAKS66JN7KR24A,cqUN1Su4ZwONx1miSKOdnT3WMaUNvIG83ReeIdli");
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
     }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        athenaTodynamo();
    }
}

