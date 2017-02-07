package com.amazonaws.lambda.athena;

/**
 * Created by srramas on 2/7/17.
 */
import java.sql.*;
import java.util.Properties;
import java.util.Random;

import com.amazonaws.athena.jdbc.AthenaDriver;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;


public class AthenaJDBCDemo {

    static final String athenaUrl = "jdbc:awsathena://athena.us-east-1.amazonaws.com:443";
    static String tableName = "employee";


    public static void athenaTodynamo() {

        Connection conn = null;
        Statement statement = null;

        try {
            Class.forName("com.amazonaws.athena.jdbc.AthenaDriver");
            Properties info = new Properties();
            info.put("s3_staging_dir", "s3://testeastreg/athena");
           // info.put("log_path", "/Users/myUser/.athena/athenajdbc.log");
            info.put("aws_credentials_provider_class","com.amazonaws.auth.DefaultAWSCredentialsProviderChain");
            System.out.println("Connecting to Athena...");
            conn = DriverManager.getConnection(athenaUrl, info);
            System.out.println("Listing tables...");
            String sql = "select * from abc.employee_orc";
            statement = conn.createStatement();
            statement.setFetchSize(100);
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("After Athena query execution");

           TableWriteItems writeItems = new TableWriteItems(tableName);

            while (rs.next()) {
                //Retrieve table column.
                String name = rs.getString("name");
                double id = rs.getDouble("id");
                String age = rs.getString("age");
                //Display values.
                System.out.println("Name: " + name);
                System.out.println("id: " + id);
                System.out.println("age: " + age);
                Random random=new Random();
                int r=random.nextInt(20);
                age=age+r;
                Item item=new Item()
                        .withPrimaryKey("age", age)
                        .withDouble("id", id)
                        .withString("name",name);
                writeItems.addItemToPut(item);
            }

            System.out.println("After bathwrite constructed");
            DynamoDBConnector.processItems(writeItems);

            rs.close();
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