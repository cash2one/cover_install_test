package com.baidu.coverinstall.util;

import java.util.ArrayList;
import java.util.Iterator;

public class XMLPO {
	/*
	<toVersion>6.6</toVersion>
	<fromVersion>6.3,6.4</fromVersion>
	<isthrunk>false</isthrunk>
	<product>all</product>
	<packageName>com.baidu.searchbox_huawei</packageName>
	*/
	
	private String[] toVersion ;
	private String[] fromVersion ;
	private Boolean isThrunk ;
	private String product ;
	private String packageName ;
	private String byStep;
	
	public XMLPO(){
	}
	public String getByStep() {
		return byStep;
	}
	public void setByStep(String byStep) {
		this.byStep = byStep;
	}
	
	public XMLPO(String[] toVersion,String[] fromVersion, Boolean isThrunk , String product ,String packageName){
		this.toVersion = toVersion;
		this.fromVersion= fromVersion;
		this.isThrunk = isThrunk;
		this.product = product;
		this.packageName = packageName;
	}
	public String[] getToVesionPackageName(){
		String[] toPackageNames = new String[this.toVersion.length] ;
		for (int i= 0 ;i < this.toVersion.length;i++){
			toPackageNames[i] = this.toVersion[i] +"_"+ 
					(this.isThrunk?"thrunk":"oem") + "_" + this.product +"_" +this.packageName + ".apk";
		}
		return toPackageNames;
	}
	public String[] getFromVesionFileName(){
		String[] fromPackageNames = new String[this.fromVersion.length] ;
		for (int i= 0 ;i < this.fromVersion.length;i++){
			fromPackageNames[i] = this.fromVersion[i] +"_"+ 
					(this.isThrunk?"thrunk":"oem") + "_" + this.product +"_" +this.packageName + ".apk";
		}
		return fromPackageNames;
	}
	
	
	public String[] getToVersion() {
		return toVersion;
	}
	public void setToVersion(String[] toVersion) {
		this.toVersion = toVersion;
	}
	public String[] getFromVersion() {
		return fromVersion;
	}
	public void setFromVersion(String[]fromVersion) {
		this.fromVersion = fromVersion;
	}
	public Boolean getIsThrunk() {
		return isThrunk;
	}
	public void setIsThrunk(Boolean isThrunk) {
		this.isThrunk = isThrunk;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
}
