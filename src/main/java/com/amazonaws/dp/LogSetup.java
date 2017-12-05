package com.amazonaws.dp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by srramas on 3/23/17.
 */
public class LogSetup {

    public static void main(String a[]){

   String s="s3://athena-organ-test/post/post.csv";

   String spattern="s3://([^/]*)/(.*)";


        Pattern pattern = Pattern.compile(spattern);
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        // check all occurance
        if (matcher.find() && matcher.groupCount()==2) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }else{

        }




    }
}
