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

So not everything it needs is contained in the jar-with-dependencies. I think the reason is that when you run a job with the "hadoop" command alll
these get added onto the classpath but they are missing when you start the application with the "java" command. This can almost certainly be
resolved by explicitly adding them as dependencies, so we just need to experiment to find which ones are needed and which ones aren't - e.g. can be marked
as "provided".