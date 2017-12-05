package com.amazonaws.cw.lambda;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaAthenaOperator implements RequestHandler<InputStream, Object> {

    private static final String ATHENA_JDBC_URL_KEY = "athena_jdbc_url";
    private static final String ATHENA_STAGING_S3_DIR_KEY = "athena_s3_staging_dir";
    private static final String TIMEOUT_KEY = "athena_timeout";


    // Creating the below for future use...and future is here now..
    private static final String ATHENA_QUERY = "query";

    @Override
    public Object handleRequest(InputStream input, Context context) {
        LambdaLogger logger = context.getLogger();
        String athenaUrl = System.getenv(ATHENA_JDBC_URL_KEY);
        String stagingDir = System.getenv(ATHENA_STAGING_S3_DIR_KEY);
        int timeout = Integer.parseInt(System.getenv(TIMEOUT_KEY));
        String query = System.getenv(ATHENA_QUERY);

        try {
            ArrayList<String> rows = AthenaQueryExecutor.executeQuery(athenaUrl, stagingDir, timeout, query);
            logger.log("databases present in athena: " + rows);
            return rows;
        } catch (ClassNotFoundException | SQLException e) {
            logger.log("Exception while fetching results from athena\n");
            logger.log(e.getLocalizedMessage() + "\n");
            for(StackTraceElement element : e.getStackTrace())
                logger.log(element.toString() + "\n");
            throw new RuntimeException(e);
        }
    }

}