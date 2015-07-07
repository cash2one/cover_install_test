package com.baidu.coverinstall.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baidu.coverinstall.main.RunTest;

/*
 * @
 */
public class CMD {
	public static String CMD_ACTIVE = RunTest.ADB + " shell am start -n com.baidu.searchbox/.MainActivity";
	public static String CMD_SWIPE = RunTest.ADB + " shell input swipe 200 600 200 200";
	
	//screenshot cmd
	public   static String CMD_WAIT_DEVICE = RunTest.ADB + " wait-for-device";
	public   static String CMD_SCREENCAP = RunTest.ADB + " shell screencap -p /data/local/tmp/tmp.png\r\n";
	public   static String CMD_RMPIC =  RunTest.ADB + " shell rm /data/local/tmp/tmp.png";
	
	
	public   static String CMD_UNINSTALL =  RunTest.ADB + " uninstall com.baidu.searchbox" ;
	
	
	public   static String CMD_CHECKINSTALL =RunTest.ADB + " shell pm list package | grep com.baidu.searchbox";
	
	public   static String CMD_PULLDB = RunTest.ADB + " pull /sdcard/SearchBox.db DB\\SearchBox.db ";
 
	public   static String CMD_CHMOD = RunTest.ADB + " shell su -c cp /data/data/com.baidu.searchbox/databases/SearchBox.db /sdcard/";
	public   static String  monkeyCmd = " monkey --pct-touch 45 --pct-motion 20 --pct-majornav 10 --pct-appswitch 15 --pct-anyevent 10 --ignore-crashes --ignore-timeouts --ignore-security-exceptions -p com.baidu.searchbox --throttle 1000 -s 180 -v 60" ;
	
	public   static String  CMD_MONKEY = RunTest.ADB + " shell "  + monkeyCmd;
	
	public   static String  CMD_LOGCAT_CAT = RunTest.ADB + " logcat > /data/local/tmp/monkey.txt";
	public   static String  CMD_LOGCAT_CLEAR = RunTest.ADB + " logcat -c";
	public   static String  CMD_LOGCAT_DELETEFILE = RunTest.ADB + " shell rm /data/local/tmp/monkey.txt ";
	public   static String  CMD_LOGCAT_PULLFILE = RunTest.ADB + " pull /data/local/tmp/monkey.txt monkey.txt";

	public static void doCmdWithResult(String cmd)throws Exception {
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            System.out.println(loadStream(ps.getInputStream()));
            System.out.println(loadStream(ps.getErrorStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public static String doCmdAndGetResult(String cmd) throws Exception{
	   BufferedInputStream is = (BufferedInputStream)Runtime.getRuntime().exec(cmd).getInputStream() ;
       byte[] b = new byte[1024];
       String result = "";
       int c = is.read(b);
       while(c != -1){
    	   String tmpStr = new String(b,"UTF-8");
    	   result = result + tmpStr;
		   c = is.read(b);			
		}
       return result;
	}
	public static Process doCmdAndGetProcess(String executeCMD) throws Exception{
		Process process = Runtime.getRuntime().exec(executeCMD);
		return process;
	}
	
	public static void doCmd( String executeCMD) throws Exception{
		Runtime.getRuntime().exec(executeCMD);
	}
	public static String getCmdInstallFromAPK( String fileName) {
		return RunTest.ADB + " install -r old_release\\" + fileName;
	}
	// install -r
	public static String getCmdInstallToAPK( String fileName) {
		return RunTest.ADB + " install -r " + fileName;
	}
	
	public static String getCmdUninstall( String pakageName) {
		return RunTest.ADB + " uninstall " + pakageName;
	}
	public static String getCmdActive( String pakageName) {
		return RunTest.ADB + " shell am start -n "+pakageName+"/.MainActivity";
	}
	
	public static String getCmdPullScreenShot( String packageStr) {
		Date now = new Date(); 
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String nowStr = df.format(now);
		return RunTest.ADB + " pull /data/local/tmp/tmp.png " +  "pic\\" + nowStr+ "_"+ packageStr + ".png\r\t";
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
