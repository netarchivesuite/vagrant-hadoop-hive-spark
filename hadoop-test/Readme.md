This is a simple test class to explore how to run a hadoop job on a remote cluster. 

````
Usage: 
    mvn clean package 
    java -cp target/CDXCountAnalyser-1.0-SNAPSHOT-withdeps.jar CDXCountAnalyser hdfs:///var hdfs:///bar
````

To run the code on the remote machine (node1, u:vagrant, p:vagrant)
````
 scp  target/CDXCountAnalyser-1.0-SNAPSHOT.jar vagrant@node1:
 ssh vagrant@node1
 java -cp ./CDXCountAnalyser-1.0-SNAPSHOT.jar:`hadoop classpath`  CDXCountAnalyserUgi  file:///data '.*' hdfs:///out

````
or 
````
ssh vagrant@node1 java -cp './CDXCountAnalyser-1.0-SNAPSHOT.jar:$(/usr/local/hadoop/bin/hadoop classpath)  CDXCountAnalyserUgi  file:///data "f2.*" hdfs:///out4'

````