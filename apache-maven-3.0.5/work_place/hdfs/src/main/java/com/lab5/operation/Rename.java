package com.lab5.operation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

public class Rename
{
    public static void main( String[] args ) throws IOException, Exception, URISyntaxException
    {

        CommandLineParser parser = new BasicParser( );
        Options options = new Options( );
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("d", "dir", true, "Dir to make");
        options.addOption("s", "src", true, "source file");
        CommandLine commandLine = parser.parse( options, args );

        if( commandLine.hasOption('h') ) {
            System.out.println( "Help Message");
            System.exit(0);
        }

        String src = "", dest = "";

        if( commandLine.hasOption('s') ) {
            src = commandLine.getOptionValue('s');
        }
        else{
            System.out.println( "need input param: -s" );
            System.exit(1);
        }

        if( commandLine.hasOption('d') ) {
            dest = commandLine.getOptionValue('d');
        } else {
            System.out.println( "need input param: -d" );
            System.exit(1);
        }

        Configuration conf = new Configuration(); //读取配置文件
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

        //1 获取hdfs客户端对象
        FileSystem fs = FileSystem.get(new URI("hdfs://DESKTOP-TSQQRSN:9000"), conf, "root");
        // 三个变量 namenode工作地址 配置文件 用户

        //2 rename
        fs.rename(new Path(src), new Path(dest));

        //3 关闭资源
        fs.close();
        System.out.printf("HDFS rename from %s to %s\n", src, dest);
    }
}
