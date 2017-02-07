package com.amazonaws.lambda;

import com.amazonaws.lambda.athena.AthenaJDBCDemo;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * Created by srramas on 2/7/17.
 */
public class AthenaConnector {


    public String myHandler(int myCount, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + myCount +"\n");
        AthenaJDBCDemo.athenaTodynamo();
        return String.valueOf(myCount);
    }


}
