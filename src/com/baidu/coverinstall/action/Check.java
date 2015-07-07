package com.baidu.coverinstall.action;


public abstract class Check {
		
	// sub class need implenments this method and keep the value of filed
	public abstract CheckResult doCheck(String toFile , String fromFile);
	
	public abstract String getTag();
}
