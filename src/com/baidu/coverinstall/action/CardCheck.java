package com.baidu.coverinstall.action;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.baidu.coverinstall.util.CMD;
import com.baidu.coverinstall.util.ColumnCheckSQLite;

public class CardCheck extends Check{
    private static Logger logger = Logger.getLogger(CardCheck.class);
    
	public  final String TAG = "CardCheck";
	
	@Override
	public CheckResult doCheck(String toFile, String fromFile) {
		logger.info("start check CardCheck!");
		
		//super.isAlarm = needAlarm;
		CheckResult checkResult = new CheckResult();
		checkResult.setSubject(TAG);
		
		try {
			// pull db
			File file = new File("DB");
			if  (!file .exists() && !file .isDirectory()){
				file .mkdir();   
			}
//			// changeMode
			logger.info(CMD.CMD_CHMOD);
			CMD.doCmd(CMD.CMD_CHMOD);
			Thread.sleep(1000);
			logger.info(CMD.CMD_PULLDB);
			CMD.doCmd(CMD.CMD_PULLDB);
			Thread.sleep(2000);
			
			ColumnCheckSQLite  columnCheckSQLite = new ColumnCheckSQLite("DB\\SearchBox.db");
			if (columnCheckSQLite.doCheckLegoCardTable(toFile , fromFile)) {
				checkResult.setResult(true);
				checkResult.setContent("Pass");
			} else {
			    checkResult.setResult(false);
			    List<String> errorCard = columnCheckSQLite.getErrorCard();
			    String content = "缺失卡票：";
			    for(int i=0; i<errorCard.size(); i++) {
			        if(i==0) {
			            content += errorCard.get(i);
			            continue;
			        }
			        content += ",";
			        content += errorCard.get(i);
			    }
 			    checkResult.setContent(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkResult.setResult(false);
			checkResult.setContent(e.getMessage());
		}
		logger.info("end check CardCheck!");
		return checkResult;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return TAG;
	}
	

}
