package com.weijin.whist.singleton;

public class itsTest {
    public static void main(String[] args) {
//        获得日志单例对象
        FileLogger obj = FileLogger.getFileLogger();
        obj.write("hello");
//        obj.write("admin add course"+courseid);
        obj.write("hi");
        obj.close();
        System.out.println("end");
    }
}