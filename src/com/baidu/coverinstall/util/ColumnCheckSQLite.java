package com.baidu.coverinstall.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.sqlite.JDBC;

public class ColumnCheckSQLite {
    private static Logger logger = Logger.getLogger(ColumnCheckSQLite.class);
    
	private String filePath = null;
	
    public static final String[] TABLES = {
        "PushMsg_table",
        "T_DISCOVERY_CATEGORY",
        "T_LOCATION_POSITION",
        "account_anonysync",
        "account_loginsync",
        "android_metadata",
        "baidumsg_table",
        "barcodehistory",
        "bookmarks",
        "bookmarksdir",
        "clicklog",
        "discovery_feed",
        "discovery_module",
        "discovery_module_pfid_mapping",
        "discovery_shortcuts",
        "new_card_module",
        "new_card_module_pfid_mapping",
        "new_tip_alert",
        "newcard",
        "newcardDelete",
        "novel_chosen",
        "novel_chosen_module",
        "novel_chosen_module_pfid_mapping",
        "plugin",
        "pushTask",
        "reminddatatable",
        "searchCategory",
        "searchboxdownload",
        "searches",
        "shortcuts",
        "sourcetotals",
        "sqlite_sequence",
        "table_legocard",
        "urldata_cache",
        "videodownload",
        "videoepisodedownload",
        "videoplayfavorite",
        "videoplayhistory",
        "visitedlog",
        "xsearch_site"
    };
	
    public static final int[] TABLES_COUNT = {
        11, 6, 9, 6, 9, 1, 20, 6, 11, 3, 5, 7, 3, 3, 6, 3, 3, 3, 13, 4, 6, 3, 3, 27, 6, 18, 9, 25, 3, 21, 2, 2, 13, 5, 7, 13, 16, 12, 3, 19
    };
    
	public static final String[] TABLES_OEM = {"android_metadata","searchCategory","shortcuts","clicklog","sourcetotals","visitedlog","bookmarksdir","bookmarks","searches","pushTask","account_anonysync","account_loginsync",
        "searchboxdownload","xsearch_site","PushMsg_table","urldata_cache","barcodehistory","baidumsg_table","newcard","newcardDelete","new_tip_alert","new_card_module","new_card_module_pfid_mapping",
        "discovery_module","discovery_module_pfid_mapping","discovery_feed","reminddatatable","T_LOCATION_POSITION","T_DISCOVERY_CATEGORY","plugin","videoplayhistory","videoplayfavorite","table_legocard",
        "novel_chosen_module","novel_chosen_module_pfid_mapping","novel_chosen","discovery_shortcuts","T_PLUGIN_DEPENDENCE"} ;
	
	public static final int[] TABLES_COUNT_OEM={1, 9, 21, 5, 2, 3, 3, 11, 3, 6, 6, 9, 25, 19, 11, 5, 6, 20, 13, 4, 3, 3, 3, 3, 3, 7, 18, 9, 6, 26, 12, 16, 13, 3, 3, 6, 6, 7};
	
	private HashMap<String, Integer> errorTable = new HashMap<String, Integer>();
	private List<String> errorCard = new ArrayList<String>();
	
	public HashMap<String, Integer> getErrorTable() {
		return errorTable;
	}

	public List<String> getErrorCard() {
        return errorCard;
    }
	
	public String getFileName() {
		return filePath;
	}

	public void setFileName(String filePath) {
		this.filePath = filePath;
	}
	public ColumnCheckSQLite(String filePath){
		this.filePath = filePath;
	}
	
	public void doCheckTable(String tofile, String from)  {
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs =null ;
		try {	
            Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+ this.filePath);
            stmt = conn.createStatement();
            if ((from.indexOf("OEM") == -1)&& (tofile.indexOf("OEM") ==-1))
            	for(int i = 0 ;i < TABLES.length ;i++ ){
	        		try {
						rs = stmt.executeQuery("SELECT * FROM " + TABLES[i]);
						int columnCount = rs.getMetaData().getColumnCount();
						if (TABLES_COUNT[i] !=  rs.getMetaData().getColumnCount()){
							errorTable.put(TABLES[i], columnCount);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						errorTable.put(TABLES[i], -1);
						e.printStackTrace();
					}
	    		}
            else if ((from.indexOf("OEM") != -1)&& (tofile.indexOf("OEM") !=-1)){
            	for(int i = 0 ;i < TABLES_OEM.length ;i++ ){
	        		try {
						rs = stmt.executeQuery("SELECT * FROM " + TABLES_OEM[i]);
						int columnCount = rs.getMetaData().getColumnCount();
						if (TABLES_COUNT_OEM[i] !=  rs.getMetaData().getColumnCount()){
							errorTable.put(TABLES_OEM[i], columnCount);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						errorTable.put(TABLES[i], -1);
						e.printStackTrace();
					}
	    		}
            }else {
            	
            	throw new FromToException("FromToException!");
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				rs.close();
	            conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
		}
 
    }
	static class FromToException extends Exception{
		public FromToException(String msg){
			super(msg);
		}
	}
	public Boolean doCheckLegoCardTable (String tofile, String from)  {
		Boolean hsGuPiao = false; 
		Boolean hsBendi = false;
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs =null ;
		try {	
            Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+ this.filePath);
            stmt = conn.createStatement();
            //if ((from.indexOf("OEM") == -1)&& (tofile.indexOf("OEM") ==-1))
           
            String table;
            String title;
            if(Float.parseFloat(tofile.split("_")[0])<5.0f) {
                table = "card";
                title = "title";
            }else if(Float.parseFloat(tofile.split("_")[0])<6.0f) {
                table = "newcard";
                title = "card_title";
            }else {
                table = "table_legocard";
                title = "card_title";
            }
	        	
			rs = stmt.executeQuery("SELECT "+title+" FROM "+table);
			while(rs.next()){
				String str = rs.getString(1);
				System.out.println(str);
                if (str.indexOf("股票") != -1){
                    System.out.println(str);
                    hsGuPiao =  true;
                } 
                if (str.indexOf("天气") != -1){
                    System.out.println(str);
                    hsBendi =  true;
                } 
			}
 			//int columnCount = rs.getMetaData().getColumnCount();
					
            //else if ((from.indexOf("OEM") != -1)&& (tofile.indexOf("OEM") !=-1)){
            	
           // }else {
            	
            	//throw new FromToException("FromToException!");
           // }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
			    if(rs!=null)
                    rs.close();
			    if(stmt!=null)
			        stmt.close();
	            if(conn!=null)
	                conn.close();
			} catch (SQLException e) {
			    logger.error(e);
			}
		}
		if(!hsBendi) {
		    errorCard.add("天气");
		}
		if(!hsGuPiao) {
		    errorCard.add("股票");
		}
		return (hsBendi&&hsGuPiao);
 
    }
}
