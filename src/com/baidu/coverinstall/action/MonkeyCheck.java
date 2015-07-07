package com.baidu.coverinstall.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.main.Conf;
import com.baidu.coverinstall.util.CMD;

public class MonkeyCheck extends Check {
    private static Logger logger = Logger.getLogger(MonkeyCheck.class);
    
	public  final String TAG = "MonkeyCheck";
	@Override
	public CheckResult doCheck(String toFile , String fromFile) {
		logger.info("start check MonkeyCheck!");
		CheckResult checkResult = new CheckResult();
		checkResult.setSubject(TAG);
		try {
			CMD.doCmdWithResult(CMD.CMD_LOGCAT_CLEAR);
			CMD.doCmdWithResult(CMD.CMD_LOGCAT_DELETEFILE);
			// if monkey log exist then delete
			File file = new File("monkey.txt");
			if  (file.exists()){
				file.delete();   
			}
			Process logcatProcess = CMD.doCmdAndGetProcess(CMD.CMD_LOGCAT_CAT) ;
			Process MonkeyProcess = CMD.doCmdAndGetProcess(CMD.CMD_MONKEY);
			Thread.sleep(Conf.RUN_MONKEY_TIME);
			MonkeyProcess.destroy();
			logcatProcess.destroy();
			CMD.doCmdWithResult(CMD.CMD_LOGCAT_PULLFILE);
			Thread.sleep(5000);
			if (!isCrash()){
				checkResult.setResult(true);
				checkResult.setContent("pass");
			}else{
				checkResult.setResult(false);
				checkResult.setContent(TAG + " Failed!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkResult.setResult(false);
			checkResult.setContent(e.getMessage());
		}
		logger.info("end check MonkeyCheck!");
		return checkResult;
	}
	
	@Override
	public String getTag() {
		return TAG;
	}
	private Boolean isCrash() {
	    boolean b = false;
		BufferedReader br =null ;
		FileReader fr=null ; 
		try {
			fr  = new FileReader("monkey.txt");
			br = new BufferedReader(fr);
			String s ;
			while((s = br.readLine()) !=null){
				if( s.indexOf("FATAL EXCEPTION") != -1){
					br.close();
					fr.close();
					b = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		} finally {
		    if(br != null) {
		        try {
                    br.close();
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
		    }
		    
		}
		return b;
	}
	
}
