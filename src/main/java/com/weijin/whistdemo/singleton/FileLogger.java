package com.weijin.whistdemo.singleton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class FileLogger {
    private String path = "log.txt";//目的路径

    private FileOutputStream out = new FileOutputStream(path, true);

    //    上面的true意为 out从日志文件尾部开始添加纪录！重启后新日志自动追加到末尾，原信息不变！
    private FileLogger() throws Exception          //日志类
    {
        System.out.println("Logger singleton starts.");
    }

    private static class My                     //单例模式 静态  内部   类
    {
        private static FileLogger fileLogger;

        static {
            try {
                fileLogger = new FileLogger();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static FileLogger getFileLogger()        //外部引用类，获取内部信息
    {
        return My.fileLogger;
    }

    //    文本的输入
    public void write(String msg) {
        try {
            out = new FileOutputStream(path, true);
            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            int hh = c.get(Calendar.HOUR);
            int mm = c.get(Calendar.MINUTE);
            int ss = c.get(Calendar.SECOND);

            String strTime = "";
//            strTime = strTime.format("time:%d-%02d-%02d %02d-%02d-%02d\r\n", y, m, d, hh, mm, ss);
            String strContent = msg + "\r\n";

            byte buf[] = strTime.getBytes(StandardCharsets.UTF_8);//设置编码方式
            out.write(buf);
            buf = strContent.getBytes(StandardCharsets.UTF_8);
            out.write(buf);//此write非上面方法名write，此是(FileOutoutStream) out 的子方法
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}