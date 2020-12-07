
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.File;
import java.io.IOException;
import java.security.PrivilegedAction;

/**
 * This job counts the number of cdx records pointing to every arc- or warc-file in a given collection of
 * cdx files. Here we are just using it as a test class so give it some input files with lines that end "foobar.warc"
 * etc. or just feed it some real cdx files.
 */
public class CDXCountAnalyserUgi extends Configured implements Tool {

    static public class ArchiveFilenameCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        final private static LongWritable ONE = new LongWritable(1);
        private Text tokenValue = new Text();

        /**
         * Counts based on the last token on the cdx line being the name of an arcfile or warcfile.
         * @param offset
         * @param text
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void map(LongWritable offset, Text text, Context context) throws IOException, InterruptedException {
            String tokens[] = text.toString().split("\\s+");
            String lastToken = tokens[tokens.length -1];
            if (lastToken.endsWith("arc")) {
                tokenValue.set(lastToken);
                context.write(tokenValue, ONE);
            }
        }
    }

    static public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable total = new LongWritable();

        @Override
        protected void reduce(Text token, Iterable<LongWritable> counts, Context context)
                throws IOException, InterruptedException {
            long n = 0;
            for (LongWritable count : counts)
                n += count.get();
            total.set(n);
            context.write(token, total);
        }
    }

    public int run(String[] args) throws Exception {
        System.setProperty("HADOOP_USER_NAME", "vagrant");  //Not sure if this is necessary
        Configuration conf = new Configuration();
        conf.set("yarn.resourcemanager.address", "node1:8032");
        //conf.set("yarn.resourcemanager.hostname", "node1");
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("mapreduce.jobtracker.address", "node1");   //Not sure if this is necessary
        conf.set("fs.defaultFS", "hdfs://node1");
        File file = new File("target/CDXCountAnalyser-1.0-SNAPSHOT.jar");
        conf.set("mapreduce.job.jar", file.getAbsolutePath());
        //For below see https://stackoverflow.com/questions/17265002/hadoop-no-filesystem-for-scheme-file
        //but still need to check that this is necessary.
        conf.set("fs.hdfs.impl",
            org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        conf.set("fs.file.impl",
            org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        //Deprecated method. How should we really do this?
        Job job = new Job(conf, "CDX Count Analysis");
        job.setJarByClass(this.getClass());

        int n = args.length;
        if (n > 0)
            TextInputFormat.addInputPath(job, new Path(args[0]));
        //if (n > 1)
            //SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
        //
        if (n > 1) {
            CDXCountAnalyserUgi.SimplePathFilter.filter = args[1];
            TextInputFormat.setInputPathFilter(job, SimplePathFilter.class);
        }
        if (n > 1)
           SequenceFileOutputFormat.setOutputPath(job, new Path(args[2]));

        job.setMapperClass(ArchiveFilenameCountMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        return job.waitForCompletion(true) ? 0 : -1;
    }

    public static void main(String[] args) throws Exception {
        UserGroupInformation ugi = UserGroupInformation.createRemoteUser("vagrant");
        ugi.doAs(new PrivilegedAction<Integer>() {
            @Override
            public Integer run() {
                try {
                    return new Integer(ToolRunner.run(new CDXCountAnalyserUgi(), args));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return -101;
            }
        });
    }

    public static class SimplePathFilter implements PathFilter {

        public static String filter = ".*";

        public static void init() {};

        @Override
        public boolean accept(Path path) {
            return path.getName().matches(filter);
        }
    }
}
