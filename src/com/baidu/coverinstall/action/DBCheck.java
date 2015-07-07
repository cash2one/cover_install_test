package com.baidu.coverinstall.action;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.util.CMD;
import com.baidu.coverinstall.util.ColumnCheckSQLite;

public class DBCheck extends Check {
    private static Logger logger = Logger.getLogger(DBCheck.class);
    
	public  final String TAG = "DBCheck";
	@Override
	public CheckResult doCheck(String toFile , String fromFile) {
		logger.info("start check DBCheck!");
		//super.isAlarm = needAlarm;
		CheckResult checkResult = new CheckResult();
		checkResult.setSubject(TAG);
		
		try {
			// pull db
			File file = new File("DB");
			if  (!file.exists()  && !file.isDirectory()){
				file.mkdir();   
			}
			// changeMode
			CMD.doCmd(CMD.CMD_CHMOD);
			Thread.sleep(1000);
			CMD.doCmd(CMD.CMD_PULLDB);
			Thread.sleep(2000);
			
			ColumnCheckSQLite  columnCheckSQLite = new ColumnCheckSQLite("DB\\SearchBox.db");
			columnCheckSQLite.doCheckTable(toFile , fromFile);
			HashMap<String, Integer> result = columnCheckSQLite.getErrorTable();
			if (result.isEmpty()){
				checkResult.setResult(true);
				checkResult.setContent("pass");
			}else{
				checkResult.setResult(false);
				Iterator iter =  result.entrySet().iterator();
				String content = "" ; 
				while (iter.hasNext()) {
					Entry entry = (Entry)iter.next();
					String key = (String) entry.getKey();
					int val = (Integer) entry.getValue();
					if (!content.equals("")){
						content = content + ";" ;
					}
					content = content + key +"=" + val ;
				}
				checkResult.setContent(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkResult.setResult(false);
			checkResult.setContent(e.getMessage());
		}
		logger.info("end check DBCheck!");
		return checkResult;
	}
	@Override
	public String getTag() {
		return TAG;
	}
}
