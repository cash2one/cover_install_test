package com.baidu.coverinstall.action;

import java.io.File;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.util.CMD;

public class SwipeCheck extends Check {
    private static Logger logger = Logger.getLogger(SwipeCheck.class);
    
	public  final String TAG = "SwipeCheck";
	@Override
	public CheckResult doCheck(String toFile , String fromFile) {
		logger.info("start check SwipeCheck!");
		CheckResult checkResult = new CheckResult();
		checkResult.setSubject(TAG);
		try {
			for (int i = 0 ;  i < 3 ;i++ ){
				Thread.sleep(5000);
				CMD.doCmd(CMD.CMD_SWIPE);
				Thread.sleep(5000);
				
				screenShot(toFile.replaceAll(".", "_"));
			}

			String result = CMD.doCmdAndGetResult(CMD.CMD_CHECKINSTALL);
			int c = result.indexOf("com.baidu.searchbox");
			if (c >= 0){
				checkResult.setResult(true);
				checkResult.setContent("pass");
			}else{
				checkResult.setResult(false);
				checkResult.setContent(TAG + " failed!");
			}
		}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					checkResult.setResult(false);
					checkResult.setContent(e.getMessage());
		}
		logger.info("end check SwipeCheck!");
		return checkResult;
	}
	private void screenShot(String packageVersion) throws Exception{
		CMD.doCmd(CMD.CMD_WAIT_DEVICE);
		CMD.doCmd(CMD.CMD_SCREENCAP);
		Thread.sleep(5000);
		File file = new File("pic");
		if  (!file .exists()  && !file .isDirectory()){
			file .mkdir();   
		}
		CMD.doCmd(CMD.getCmdPullScreenShot(packageVersion));
		Thread.sleep(5000);
		CMD.doCmd(CMD.CMD_RMPIC);
	}
	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}
	

}
