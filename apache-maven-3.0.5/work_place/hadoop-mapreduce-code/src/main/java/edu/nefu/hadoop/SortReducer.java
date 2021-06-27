package edu.nefu.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SortReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
    private static int cnt = 0;

    @Override
    protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        cnt += 1;
        IntWritable k = new IntWritable(cnt);
        context.write(k, key);
    }
}
