package com.feng.socket.utils;

import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.io.*;
import java.nio.file.Files;

public class FileUtils {

    public static void CreateFile(String UserFile) throws IOException {
//        File file = new File("D:/ReallyShare/user" + UserFile);
        File file1 = new File("D:/ReallyShare/user/" + UserFile);
        File file2 = new File("D:/ReallyShare/user/" + UserFile + "/work");
        File file3 = new File("D:/ReallyShare/user/" + UserFile + "/info");
        file1.mkdir();
        file2.mkdir();
        file3.mkdir();
        FileUtils.hhh(UserFile);
    }

    public static void CreateWorkFile(String UserFile, String WorkFile) {
        File file2 = new File("D:/ReallyShare/user/" + UserFile + "/work/" + WorkFile);
        file2.mkdir();
    }

    public static void  hhh( String UserFile) throws IOException {
        InputStream in = new FileInputStream("D:/ReallyShare/project/1.png");
        OutputStream out = new FileOutputStream("D:/ReallyShare/user/" + UserFile + "/info/1.png");

        //2.定义一个变量，记住每一个读取的字节
        int len;

        //3.获取拷贝文件前系统时间
        long beginTime = System.currentTimeMillis();

        //4.读取字节，判断是否到达文件末尾
        while((len = in.read()) != -1) {
            out.write(len);//写入文件
        }
        //5.获取文件拷贝结束时间并输出
        long endTime = System.currentTimeMillis();

        //6.关闭输入输出流
        in.close();
        out.close();
    }
}
