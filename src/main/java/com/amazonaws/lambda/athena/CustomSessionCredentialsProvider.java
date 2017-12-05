package com.amazonaws.lambda.athena;

import com.amazonaws.auth.*;

/**
 * Created by srramas on 3/2/17.
 */
public class CustomSessionCredentialsProvider implements AWSCredentialsProvider {

     BasicSessionCredentials basicSessionCredentials=null;

    public CustomSessionCredentialsProvider(String accessId, String secretKey, String token)
    {
         basicSessionCredentials=new BasicSessionCredentials(accessId,secretKey,token);
    }



    public AWSCredentials getCredentials() {
        return basicSessionCredentials;
    }

    public void refresh() {
    }

}
