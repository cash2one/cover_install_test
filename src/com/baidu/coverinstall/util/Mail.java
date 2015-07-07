package com.baidu.coverinstall.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.action.CheckResult;



public class Mail {
    private static Logger logger = Logger.getLogger(Mail.class);
    
	public  String userName ="bdmobile" ;
	public  String passWord = "Vw*hmNg#pN";
	public  String tos = "wuhao16@baidu.com";
	public  void sendMail(String subject,String theMessage ){
		SenderRunnable senderRunnable = new SenderRunnable(userName, passWord);
		senderRunnable.setMail(subject, theMessage, 
				tos, null);
		new Thread(senderRunnable).start();
		
	}
	public String conStructMessage(String packageVer ,ArrayList<CheckResult> checkResultSet ) {
		//Iterator<E>
		//checkResultSet
		StringBuilder standStr  = new StringBuilder("<tr bgcolor= #color><td>#item</td><td>#result</td><td>#detail</td>");
		StringBuilder result  = new StringBuilder();
		StringBuilder tmpStr =  new StringBuilder();
		int itemLenth = "#item".length();
		int resultLenth = "#result".length();
		int detailLenth = "#detail".length();
		int colorlLenth = "#color".length();
		Iterator iter = checkResultSet.iterator();
		while (iter.hasNext()) {
			CheckResult checkResult = (CheckResult)iter.next();
			if (checkResult.getContent() == null){
				checkResult.setContent("None");
			}
			tmpStr = standStr.replace(standStr.indexOf("#item"), standStr.indexOf("#item")+itemLenth, checkResult.getSubject());
			tmpStr = tmpStr.replace(standStr.indexOf("#result"), standStr.indexOf("#result")+resultLenth, checkResult.getResult().toString());
			tmpStr = tmpStr.replace(standStr.indexOf("#detail"), standStr.indexOf("#detail")+detailLenth, checkResult.getContent());
			tmpStr = tmpStr.replace(standStr.indexOf("#color"), standStr.indexOf("#color")+colorlLenth, checkResult.getResult()? "green":"red");
			standStr = new StringBuilder("<tr bgcolor= #color><td>#item</td><td>#result</td><td>#detail</td>");
			result = result.append(tmpStr);
		}
		// read template and replace render
		String outputStr = new String();
		
		BufferedReader br =null;
		//FileReader fil = null ;
		try {
			//fil = new FileReader("mail.tmp");
			br = new BufferedReader(new InputStreamReader(new FileInputStream("mail.tmp"),"GB2312"));
			String s = new String();
			StringBuilder tmp = new StringBuilder();
			while (( s =br.readLine()) != null){
				tmp = tmp.append(s);
			}
			outputStr = tmp.toString().replace("$body", result.toString());
			outputStr = outputStr.replace("$packageVersion", packageVer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				br.close();
				//fil.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		logger.info("email content : "+outputStr);
		return outputStr;
		
	}
}
