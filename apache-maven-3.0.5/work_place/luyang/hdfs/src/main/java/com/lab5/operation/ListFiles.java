package com.lab5.operation;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;

import javax.print.DocFlavor;

public class ListFiles
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
        String dest = "";

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

        //2 ListFiles
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path(dest), true);
        while(listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.getPath().getName());
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getLen());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for(BlockLocation blockLocation : blockLocations){
                String[] hosts = blockLocation.getHosts();
                for(String host : hosts){
                    System.out.println(host);
                }
            }
            System.out.println("------------------------------");
        }
        //3 关闭资源
        fs.close();
        System.out.printf("HDFS ListFiles: %s\n", dest);
    }
}
