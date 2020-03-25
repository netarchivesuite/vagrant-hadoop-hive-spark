This is a simple test class to explore how to run a hadoop job on a remote cluster. 

````
Usage: 
mvn clean package


[ -d hadoop-2.7.6 ] || tar -xzf $PWD/../resources/hadoop-2.7.6.tar.gz 

export HADOOP_CONF_DIR="$PWD/../resources/hadoop/"
export HADOOP_HOME="$PWD/hadoop-2.7.6"
export PATH="$PATH:$HADOOP_HOME/bin"

sudo ln -s -f  /usr/lib/jvm/java-1.8.0 /usr/local/java

hdfs dfs -mkdir /foo
hdfs dfs -copyFromLocal src/main/data/* /foo/

yarn jar \
    target/CDXCountAnalyser-1.0-SNAPSHOT-withdeps.jar \
    dk.kb.netarchive.CDXCountAnalyser \
    /foo \
    /bar9
````





To run the code on the remote machine (node1, u:vagrant, p:vagrant)
````
scp  target/dk.kb.netarchive.CDXCountAnalyser-1.0-SNAPSHOT.jar vagrant@node1:.
ssh vagrant@node1 <<EOF
\$HADOOP_PREFIX/bin/yarn jar dk.kb.netarchive.CDXCountAnalyser-1.0-SNAPSHOT.jar  dk.kb.netarchive.CDXCountAnalyser  file:///data hdfs:///out
EOF
````
or 
````
ssh vagrant@node1 java -cp './dk.kb.netarchive.CDXCountAnalyser-1.0-SNAPSHOT.jar:$(/usr/local/hadoop/bin/hadoop classpath)  CDXCountAnalyserUgi  file:///data "f2.*" hdfs:///out4'

````