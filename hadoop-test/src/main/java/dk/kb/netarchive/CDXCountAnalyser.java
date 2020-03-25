package dk.kb.netarchive;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * This job counts the number of cdx records pointing to every arc- or warc-file in a given collection of
 * cdx files. Here we are just using it as a test class so give it some input files with lines that end "foobar.warc"
 * etc. or just feed it some real cdx files.
 */
public class CDXCountAnalyser extends Configured implements Tool {
    
    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new CDXCountAnalyser(), args));
    }
    
    
    
    
    
    
    
    
    public int run(String[] args) throws Exception {
    
        Configuration conf = getConf();
        
        Job job = Job.getInstance(conf, this.getClass().getCanonicalName());
        job.setJarByClass(this.getClass());
    
    
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        int n = args.length;
        if (n > 0) {
            Path inputFile = new Path(args[0]);
            TextInputFormat.addInputPath(job, inputFile);
        }
        if (n > 1) {
            TextOutputFormat.setOutputPath(job, new Path(args[1]));
        }

        job.setMapperClass(ArchiveFilenameCountMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);


        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        return job.waitForCompletion(true) ? 0 : -1;
    }

}
