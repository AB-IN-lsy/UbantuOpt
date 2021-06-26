package edu.nefu.hadoop;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordcountMapped extends Mapper<LongWritable, Text, Text, IntWritable> {
    /**
     * 前两个是输入的KV，后两个是输出的KV
     * 父类四个方法，setup，run，cleanup，还有要重写的map
     */

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // super.map(key, value, context);
        Text k = new Text();
        IntWritable v = new IntWritable(1);
        String line = value.toString();
        String[] words = line.split(" ");
        for(String word : words){
            k.set(word);
            context.write(k, v);
        }

    }
}
