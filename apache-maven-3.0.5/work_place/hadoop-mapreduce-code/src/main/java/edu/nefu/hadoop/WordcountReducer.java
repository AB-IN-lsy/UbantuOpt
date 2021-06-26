package edu.nefu.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class WordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    /**
     * 父类四个函数setup cleanup run reduce
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //super.reduce(key, values, context);
        // (root,1) (root,1)
        int sum = 0;
        for(IntWritable value : values){
            sum += value.get();
        }
        IntWritable v = new IntWritable(sum);
        context.write(key, v);
    }
}
