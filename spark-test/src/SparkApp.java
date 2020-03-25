import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.IOException;

/**
 * Created by csr on 3/23/20.
 */
public class SparkApp {
    public static void main(String[] args) throws IOException {
        System.setProperty("HADOOP_CONF_DIR", "/home/csr/projects/vagrant-hadoop-hive-spark/resources/hadoop");
        SparkConf sparkConf = new SparkConf()
                .setAppName("Example Spark App")
                .setMaster("yarn-client").set("spark.local.ip", "node1");  // Delete this line when submitting to a cluster
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<String> stringJavaRDD = sparkContext.textFile("/tmp/nationalparks.csv");
        System.out.println("Number of lines in file = " + stringJavaRDD.count());
    }
}
