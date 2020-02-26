This is a simple test class to explore how to run a hadoop job on a remote cluster. 

````
Usage: 
    mvn clean package 
    java -cp target/CDXCountAnalyser-1.0-SNAPSHOT-jar-with-dependencies.jar CDXCountAnalyser hdfs:///var hdfs:///bar
````

Or at least that's what I'd expect, but actually to run it outside intellij I need something like

````
     /home/csr/jdk/jdk1.8.0_25/bin/java -classpath $CLASSPATH:/home/csr/.m2/repository/org/apache/hadoop/hadoop-client/2.7.6/hadoop-client-2.7.6.jar:/home/csr/.m2/repository/org/apache/hadoop/hadoop-mapreduce-client-app/2.7.6/hadoop-mapreduce-client-app-2.7.6.jar:/home/csr/.m2/repository/org/apache/hadoop/hadoop-yarn-api/2.7.6/hadoop-yarn-api-2.7.6.jar:/home/csr/.m2/repository/org/apache/hadoop/hadoop-mapreduce-client-jobclient/2.7.6/hadoop-mapreduce-client-jobclient-2.7.6.jar:/home/csr/.m2/repository/org/apache/hadoop/hadoop-annotations/2.7.6/hadoop-annotations-2.7.6.jar:/home/csr/.m2/repository/org/apache/hadoop/hadoop-common/2.7.6/hadoop-common-2.7.6.jar:/home/csr/jdk/jdk1.8.0_25/jre/lib/rt.jar:/home/csr/projects/vagrant-hadoop-hive-spark/hadoop-test/target/CDXCountAnalyser-1.0-SNAPSHOT-jar-with-dependencies.jar CDXCountAnalyser hdfs:///data1 hdfs:///out4
````

So not everything it needs is contained in the jar-with-dependencies.