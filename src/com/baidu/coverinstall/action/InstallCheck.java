package com.baidu.coverinstall.action;

import com.baidu.coverinstall.util.CMD;

public class InstallCheck extends Check {
	/*
	 * check install state and alarm
	 * 
	 */
	
	public  final String  TAG = "InstallCheck";
	@Override
	public CheckResult doCheck(String toFile , String fromFile) {
		// TODO Auto-generated method stub
		
		CheckResult checkResult = new CheckResult();
		checkResult.setSubject(TAG);
		try {
			String result = CMD.doCmdAndGetResult(CMD.CMD_CHECKINSTALL);
			int c = result.indexOf("com.baidu.searchbox");
			if (c >= 0){
				checkResult.setResult(true);
				checkResult.setContent("pass");
			}else{
				checkResult.setResult(false);
				checkResult.setContent(TAG + " failed!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			checkResult.setResult(false);
			checkResult.setContent(e.getMessage());
		}
		return checkResult;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}
	
	
}
