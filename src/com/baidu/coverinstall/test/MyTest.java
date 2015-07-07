package com.baidu.coverinstall.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class MyTest {
   
    public static Logger logger = Logger.getLogger(MyTest.class);
    
    public static void main(String[] args) {
        runAutoCase(5.0f);
        String cmd = "adb install -r 6.6_thrunk_all_com.baidu.searchbox.apk";
        Process ps;
        try {
            ps = Runtime.getRuntime().exec(cmd);
            logger.info(loadStream(ps.getInputStream()));
            logger.error(loadStream(ps.getErrorStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 运行自动化脚本
     * @param version 手机百度版本
     */
    public static void runAutoCase(float version) {
        logger.info("运行自动化脚本(版本号："+version+")");
        try {
            String[] cmd = {"cmd","/C","python ./auto_case_python/test_add_card.py "+version};
            Process ps = Runtime.getRuntime().exec(cmd);
            logger.info(loadStream(ps.getInputStream()));
            logger.error(loadStream(ps.getErrorStream()));
            logger.info("结束自动化脚本(版本号："+version+")");
        }catch(Exception e) {
            logger.error(e);
        }
    }
    
    public static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while((ptr = in.read()) != -1) {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }
}
