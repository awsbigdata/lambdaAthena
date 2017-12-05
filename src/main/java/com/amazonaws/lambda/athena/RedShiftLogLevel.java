package com.amazonaws.lambda.athena;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by srramas on 3/20/17.
 */
public class RedShiftLogLevel {



    public static void main(String a[]){
        try{
            System.out.println(System.getProperty("java.util.logging.config.file"));
          // System.setProperty("java.util.logging.config.file","/home/local/ANT/srramas/IdeaProjects/lambdaAthena/logging.properties");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Properties info = new Properties();
            info.setProperty("user","sqluser");
            info.setProperty("password","sqlpassword");
            //info.setProperty("java.util.logging.ConsoleHandler.level","FINEST");
          //  info.setProperty("loglevel","2");
          // info.setProperty("java.util.logging.config.file","/home/local/ANT/srramas/IdeaProjects/lambdaAthena/logging.properties");


            Connection con= DriverManager.getConnection(
                    "jdbc:sqlserver://sqlserver.c9ujozbff8fu.us-east-1.rds.amazonaws.com:1433",info);
//here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            stmt.execute("use [dbtest]");


            con.close();
        }catch(Exception e){ System.out.println(e);}
    }

}


