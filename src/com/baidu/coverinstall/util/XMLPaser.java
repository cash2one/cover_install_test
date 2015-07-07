package com.baidu.coverinstall.util;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


public class XMLPaser {
	/*
	 * 
	<installSet>
	<Install>	
		<toVersion>6.6</toVersion>
		<fromVersion>6.3,6.4</fromVersion>
		<isthrunk>true</isthrunk>
		<product>all</product>
		<packageName>com.baidu.searchbox</packageName>		
	</Install>
	<Install>	
		<toVersion>6.6</toVersion>
		<fromVersion>6.3,6.4</fromVersion>
		<isthrunk>false</isthrunk>
		<product>all</product>
		<packageName>com.baidu.searchbox_huawei</packageName>		
	</Install>
	</installSet>
	 */
		public static ArrayList<XMLPO>  parse(String xmlPath){
				//ArrayList<XMLPO> xmlPoList = new ArrayList<XMLPO>();
				XMLPO xmlPO = null;
				InputStream stream = null;
				ArrayList<XMLPO> xmlPoList =null;
				try{
				//get xml parser
					XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
					XmlPullParser xmlParser = factory.newPullParser();
					//xmlParser.setInput(new StringReader(xmlPath));
					xmlParser.setInput(new FileReader(xmlPath));
					
					int evnType = xmlParser.getEventType();
						//continue  to end document
					while(evnType != XmlPullParser.END_DOCUMENT){
						switch(evnType){
							case XmlPullParser.START_TAG:
									String tag = xmlParser.getName();
									if(tag !=null && tag.equalsIgnoreCase("installSet")){ 
										xmlPoList = new ArrayList<XMLPO>();
									}else if(tag !=null && tag.equalsIgnoreCase("Install")){ 
										xmlPO = new XMLPO();
									}else if(tag !=null && tag.equalsIgnoreCase("toVersion")){
										xmlPO.setToVersion(xmlParser.nextText().split(","));
									}else if(tag !=null &&tag.equalsIgnoreCase("fromVersion")){
										String[] fromversions = xmlParser.nextText().split(","); 
										xmlPO.setFromVersion(fromversions);
									}else if(tag !=null &&tag.equalsIgnoreCase("isthrunk")){
										xmlPO.setIsThrunk(xmlParser.nextText().equals("true") ? true : false);
									}else if(tag !=null &&tag.equalsIgnoreCase("product")){
										xmlPO.setProduct(xmlParser.nextText());
									}else if(tag !=null &&tag.equalsIgnoreCase("packageName")){
										xmlPO.setPackageName(xmlParser.nextText());
									}else if(tag !=null &&tag.equalsIgnoreCase("byStep")){
										xmlPO.setByStep(xmlParser.nextText());
									}
									break;
								case XmlPullParser.END_TAG:
									if(xmlParser.getName().equalsIgnoreCase("Install") && xmlPO != null){
										xmlPoList.add(xmlPO);
									}
									break;
								default:break;
							}
							evnType = xmlParser.next();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					return xmlPoList;
				}
		public static MailConfPO  parseMail(String xmlPath){
			//ArrayList<XMLPO> xmlPoList = new ArrayList<XMLPO>();
			MailConfPO mailPO = null;
			try{
			//get xml parser
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				XmlPullParser xmlParser = factory.newPullParser();
				//xmlParser.setInput(new StringReader(xmlPath));
				xmlParser.setInput(new FileReader(xmlPath));
				
				int evnType = xmlParser.getEventType();
					//continue  to end document
				while(evnType != XmlPullParser.END_DOCUMENT){
					switch(evnType){
						case XmlPullParser.START_TAG:
								String tag = xmlParser.getName();
								if(tag !=null && tag.equalsIgnoreCase("mail")){ 
									mailPO = new MailConfPO();
								}else if(tag !=null && tag.equalsIgnoreCase("userName")){ 
									mailPO.setUserName(xmlParser.nextText());
								}else if(tag !=null && tag.equalsIgnoreCase("passwd")){
									mailPO.setPasswd(xmlParser.nextText());
								}else if(tag !=null &&tag.equalsIgnoreCase("to")){
									mailPO.setTo(xmlParser.nextText());
								}	
								
								break;
							default:break;
						}
						evnType = xmlParser.next();
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
				return mailPO;
			}
		}