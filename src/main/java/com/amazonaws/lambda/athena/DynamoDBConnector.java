package com.amazonaws.lambda.athena;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by srramas on 2/7/17.
 */
public class DynamoDBConnector {

    static DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new DefaultAWSCredentialsProviderChain()));



    public  static DynamoDB getClient(){
        return  dynamoDB;
    }


    public static Table getTable(String tableName){

        return dynamoDB.getTable(tableName);
    }


  public static void processItems(TableWriteItems  writeItems){
      try{
          BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(writeItems);

          do {

              // Check for unprocessed keys which could happen if you exceed provisioned throughput

              Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

              if (outcome.getUnprocessedItems().size() == 0) {
                  System.out.println("No unprocessed items found");
              } else {
                  System.out.println("Retrieving the unprocessed items");
                  outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
              }

          } while (outcome.getUnprocessedItems().size() > 0);

      }  catch (Exception e) {
          System.err.println("Failed to retrieve items: ");
          e.printStackTrace(System.err);
      }

  }

}
