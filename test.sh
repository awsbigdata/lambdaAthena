#!/bin/bash

exec &>>/home/local/ANT/srramas/IdeaProjects/lambdaAthena/inter.log

for i in {1..50000}
do
echo "$i times"
echo "time: $(date)"
  java -cp /home/local/ANT/srramas/IdeaProjects/lambdaAthena/build/libs/lambdaAthena-all-1.0-SNAPSHOT.jar com.amazonaws.lambda.athena.AthenaJDBCDemo

sleep 10

done

echo "Completed"
