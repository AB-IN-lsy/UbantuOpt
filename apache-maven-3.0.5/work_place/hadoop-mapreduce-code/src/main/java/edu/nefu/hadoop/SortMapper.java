package edu.nefu.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SortMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
    // 后两个是输出的KV, k = sort_item, v = number
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //super.map(key, value, context);
        int number = Integer.parseInt(value.toString());
        IntWritable v = new IntWritable(1);
        IntWritable k = new IntWritable(number);
        context.write(k, v);
    }
}
