package com.lab5.operation;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

public class Append
{
    public static void main( String[] args ) throws IOException, Exception, URISyntaxException
    {

        CommandLineParser parser = new BasicParser( );
        Options options = new Options( );
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("s", "src", true, "source file");
        options.addOption("d", "dest", true, "destination file to append");
        CommandLine commandLine = parser.parse( options, args );

        if( commandLine.hasOption('h') ) {
            System.out.println( "Help Message");
	        System.exit(0);
	    }


        String src = "";
        if( commandLine.hasOption('s') ) {  
            src = commandLine.getOptionValue('s');  
        } else {
            System.out.println( "need input param: -s" );
	    System.exit(1);
        }
        String dest = "";
        if( commandLine.hasOption('d') ) {  
            dest = commandLine.getOptionValue('d');  
        } else {
            System.out.println( "need input param: -d" );
	    System.exit(1);
        }

        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.setBoolean("dfs.supportappend", true);

        FileSystem fs = null;

	    try {
            //1 获取hdfs客户端对象
            String hdfs_path = "hdfs://DESKTOP-TSQQRSN:9000" + dest;
            //FileSystem fs = FileSystem.get(new URI("hdfs://hadoop1:9000"), conf, "root");
            fs = FileSystem.get(new URI(hdfs_path), conf, "root");
	        InputStream in = new BufferedInputStream(new FileInputStream(src));
            OutputStream out = fs.append(new Path(hdfs_path));
            IOUtils.copyBytes(in, out, 4096);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3 关闭资源
        fs.close();
    }
}
