package com.amazonaws.lambda.athena;


import com.amazonaws.auth.*;


/**
 * Created by srramas on 3/21/17.
 */
public class CustomSTSAssumeRoleProvider implements AWSCredentialsProvider{


   private STSAssumeRoleSessionCredentialsProvider stsAssumeRoleSessionCredentialsProvider=null;


    public CustomSTSAssumeRoleProvider(){
        System.out.println("please pass the aws_credentials_provider_arguments as rolearn,accesskey,secretkey");

    }

    public CustomSTSAssumeRoleProvider(String rolearn , String accesskey , String secretkey){
        AWSCredentials awsCredentials= new BasicAWSCredentials(accesskey,secretkey);

        stsAssumeRoleSessionCredentialsProvider =
                new STSAssumeRoleSessionCredentialsProvider.Builder(rolearn,"Assume_Athena_Role").withLongLivedCredentials(awsCredentials).build();


    }
    public AWSCredentials getCredentials() {
        return stsAssumeRoleSessionCredentialsProvider.getCredentials();
    }

    public void refresh() {
        stsAssumeRoleSessionCredentialsProvider.refresh();
    }

}
