package com.baidu.coverinstall.main;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.action.DBCheck;
import com.baidu.coverinstall.action.InstallCheck;
import com.baidu.coverinstall.action.MonkeyCheck;
import com.baidu.coverinstall.action.SwipeCheck;
import com.baidu.coverinstall.action.CardCheck;
import com.baidu.coverinstall.action.Check;
import com.baidu.coverinstall.action.CheckResult;
import com.baidu.coverinstall.util.CMD;
import com.baidu.coverinstall.util.Mail;
import com.baidu.coverinstall.util.MailConfPO;
import com.baidu.coverinstall.util.XMLPO;
import com.baidu.coverinstall.util.XMLPaser;

public class RunTest {
    private static Logger logger = Logger.getLogger(RunTest.class);
    
	public static String ADB ="adb"; 
	//private ArrayList<String> packages = new ArrayList<String>();  
	private ArrayList<Check> listeners = new ArrayList<Check>();
	private ArrayList<CheckResult> checkResultSet = new ArrayList< CheckResult>();
	
	private MailConfPO mailConfPO ;
	//
	private ArrayList<XMLPO> hs =null;
	
	public ArrayList<Check> getListeners() {
		return listeners;
	}
	public void setCheckers(ArrayList<Check> listeners) {
		this.listeners = listeners;
	}
	public void addChecker(Check check){
		this.listeners.add(check);
	}
	
	public RunTest(){
		readConf();
	}
	private String getToFile(String[] toFiles, int i){
		int toFileLength = toFiles.length;
		if (i >  toFileLength -1){
			i = toFileLength -1;
		}
		return toFiles[i];
	}

	public void coverInstall(){
		Mail mail = new Mail();
		this.mailConfPO = XMLPaser.parseMail("updateConf.xml");
		mail.passWord = this.mailConfPO.getPasswd();
		mail.userName = this.mailConfPO.getUserName();
		mail.tos = this.mailConfPO.getTo();
		String allTableStr = new String();
		// parse rule
		Iterator<XMLPO>  it = this.hs.iterator();
		
		while (it.hasNext()){
			XMLPO xmlPO=it.next();
			String[] fromFiles = xmlPO.getFromVesionFileName();
			String[] toFiles = xmlPO.getToVesionPackageName();
			String byStep = xmlPO.getByStep() ;
			String fromFile  ;
			String toFile = null;
			
			if (byStep.equals("1")){
			    logger.info("start step_cover install test...");
				try {
					for(int j = 0; j< fromFiles.length;j++){
						fromFile =fromFiles[j];
                        if (fromFile.indexOf("4.0") != -1){
                            logger.info(CMD.getCmdUninstall(xmlPO.getPackageName()));
                            CMD.doCmdWithResult(CMD.getCmdUninstall(xmlPO.getPackageName()));
                            Thread.sleep(Conf.INSTALL_SLEEP_TIME);
                            runAutoCase(4.0f);
                            continue;
                        }else if(fromFile.indexOf("4.9") != -1) {
                            toFile="4.9_thrunk_all_com.baidu.searchbox.apk";
                            logger.info("install "+toFile);
                            CMD.doCmdWithResult(CMD.getCmdInstallFromAPK(toFile));
                            Thread.sleep(2 * Conf.INSTALL_SLEEP_TIME);
                            CMD.doCmd(CMD.CMD_ACTIVE);
                            Thread.sleep(Conf.INSTALL_SLEEP_TIME);
                            //只进行卡片测试
                            CardCheck cardCheck = new CardCheck();
                            CheckResult checkResult = new CheckResult() ;
                            checkResult  = cardCheck.doCheck(toFile , "step");
                            checkResultSet.add(checkResult);
                            allTableStr = allTableStr + mail.conStructMessage( "Step Update from 4.0_thrunk_all_com.baidu.searchbox.apk to "+toFile ,checkResultSet);
                            checkResultSet.clear();
                            continue;
                        }else if(fromFile.indexOf("5.0") != -1) {
                            logger.info(CMD.getCmdUninstall(xmlPO.getPackageName()));
                            CMD.doCmdWithResult(CMD.getCmdUninstall(xmlPO.getPackageName()));
                            Thread.sleep(Conf.INSTALL_SLEEP_TIME);
                            runAutoCase(5.0f);
                            continue;
                        }
						logger.info("install "+fromFile);
						CMD.doCmdWithResult(CMD.getCmdInstallFromAPK(fromFile));
						Thread.sleep(2 * Conf.INSTALL_SLEEP_TIME);
						CMD.doCmdWithResult(CMD.getCmdActive(xmlPO.getPackageName()));
						Thread.sleep(Conf.INSTALL_SLEEP_TIME);
					}
					toFile = toFiles[0];
					logger.info("install "+toFile);
					CMD.doCmdWithResult(CMD.getCmdInstallToAPK(toFile));
					//CMD.doCmd(CMD.getCmdInstallToAPK(toFile));
                    Thread.sleep(2 * Conf.INSTALL_SLEEP_TIME);
                    CMD.doCmdWithResult(CMD.CMD_ACTIVE);
                    Thread.sleep(Conf.INSTALL_SLEEP_TIME);
                    
					for (Check check : listeners){
						CheckResult checkResult = new CheckResult() ;
						checkResult  = check.doCheck( toFile , "step");
						checkResultSet.add(checkResult);
					}
					allTableStr = allTableStr + mail.conStructMessage( "Step Update from 5.0_thrunk_all_com.baidu.searchbox.apk to " + toFile,checkResultSet);
					checkResultSet.clear();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				logger.info("end step_cover install test...");
			}else {
			    logger.info("start cover install test...");
				for(int j = 0; j< fromFiles.length;j++){
					fromFile =fromFiles[j];
					toFile = getToFile(toFiles, j);
					
					try {
						// start from nothing end new version start ,then checkers
						//CMD.doCmd(CMD.getCmdUninstall(xmlPO.getPackageName()));
						//Thread.sleep(Conf.INSTAL_SLEEP_TIME);
						//CMD.doCmd(CMD.getCmdInstallFromAPK(fromFile));
						//Thread.sleep(2 * Conf.INSTAL_SLEEP_TIME);
						//CMD.doCmd(CMD.getCmdActive(xmlPO.getPackageName()));
						//Thread.sleep(Conf.INSTAL_SLEEP_TIME);
                        logger.info(CMD.getCmdUninstall(xmlPO.getPackageName()));
                        CMD.doCmdWithResult(CMD.getCmdUninstall(xmlPO.getPackageName()));
                        Thread.sleep(Conf.INSTALL_SLEEP_TIME);
						runAutoCase(Float.parseFloat(fromFile.split("_")[0]));
						
						logger.info(CMD.getCmdInstallToAPK(toFile));
						CMD.doCmdWithResult(CMD.getCmdInstallToAPK(toFile));
						//CMD.doCmd(CMD.getCmdInstallToAPK(toFile));
						Thread.sleep(2 * Conf.INSTALL_SLEEP_TIME);
						CMD.doCmd(CMD.CMD_ACTIVE);
						Thread.sleep(Conf.INSTALL_SLEEP_TIME);
						
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
					for (Check check : listeners){
						CheckResult checkResult = new CheckResult() ;
						checkResult  = check.doCheck( toFile , fromFile);
						checkResultSet.add(checkResult);
					}
					allTableStr = allTableStr + mail.conStructMessage( "From "+fromFile + " to " + toFile,checkResultSet);
					checkResultSet.clear();
				}
				logger.info("end cover install test...");
			}
		}
		mail.sendMail( "Cover Install Report", allTableStr);
	}
	private void readConf(){
		this.hs = XMLPaser.parse("updateConf.xml");
		
	}
	
    /**
     * 运行自动化脚本
     * @param version 手机百度版本
     */
    public void runAutoCase(float version) {
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
    
    public String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while((ptr = in.read()) != -1) {
            buffer.append((char)ptr);
        }
        return buffer.toString();
    }
	    
	public static void main(String[] args) throws Exception {
	     RunTest test = new RunTest();
		 test.addChecker(new SwipeCheck());
		 //test.addChecker(new InstallCheck());
		 test.addChecker(new DBCheck());
		 test.addChecker(new CardCheck());
		 test.addChecker(new MonkeyCheck());
		 test.coverInstall();
//		//test mail
		/*
		Mail mail = new Mail();
		ArrayList<CheckResult> checkResultSet = new ArrayList<CheckResult>();
		CheckResult a = new CheckResult() ;
		a.setContent("pass");
		a.setResult(true);
		a.setSubject("DBCheck");
		checkResultSet.add(a);
		String b = mail.conStructMessage("6.3",checkResultSet);
		mail.sendMail( "Cover Install Report", b);
		 */
//		CMD.doCmd(CMD.CMD_LOGCAT_CLEAR);
//		Process s = CMD.doCmdAndGetProcess(CMD.CMD_MONKEY);
//		Process b =CMD.doCmdAndGetProcess(CMD.CMD_LOGCAT_CAT);
//		Thread.sleep(10000);
//		s.destroy();
//		b.destroy();
//		 args
		 
		 
		// main touch
		/*
		String deviceUUID = null;
		if (args.length == 1){
			deviceUUID = args[0];
		}else if (args.length == 0 ){
			deviceUUID = null;
		}else {
			System.out.println("please input devices UUID or not! do not input extra unusefull arguments!");
			System.exit(0);
		}
		if (deviceUUID != null){
			RunTest.ADB =  "adb -s "+ deviceUUID + " ";
		}
		System.out.println("1 start check!");
		RunTest test = new RunTest();
		test.addChecker(new SwipeCheck());
		test.addChecker(new MonkeyCheck());
		test.addChecker(new DBCheck());
		test.coverInstall();
		System.out.println("-1 end check!");
		*/
//		 RunTest test = new RunTest();
//		 test.addChecker(new DBCheck());
//		 test.coverInstall();
//		new WeatherCardCheck().doCheck("6.6", "6.4");
		 
	}

}
