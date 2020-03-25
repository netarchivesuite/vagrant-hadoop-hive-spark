package dk.kb.netarchive;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ArchiveFilenameCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
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
