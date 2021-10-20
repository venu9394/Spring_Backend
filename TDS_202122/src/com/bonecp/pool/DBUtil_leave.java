package com.bonecp.pool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import java.beans.PropertyVetoException;
 
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
/*
 * @author KK JavaTutorials
 * Utility class which is responsible to get JDBC connection object using 
 * BoneCP DataSource connection pool With MYSQL Database.
 */
public class DBUtil_leave {
	private static final String DB_USERNAME="db.username";
	private static final String DB_PASSWORD="db.password";
	private static final String DB_URL ="db.url";
	private static final String DB_MINPAR ="db.min";
	private static final String DB_MAXPAR ="db.max";
	private static final String DB_PARTITIONCOUNT ="db.partitionCount";
	
	private static Properties properties = null;
	private static BoneCP connectionPool;
	private static DBUtil  datasource;
	 
	    
	    private DBUtil_leave() throws IOException, SQLException,PropertyVetoException {
	    	
	    	properties = new Properties();
	    	
	    	properties.load(new FileInputStream("C:\\ATT_SCHEDULAR\\ICONNECT\\config_att.properties"));
	    	
	        try {
	            // load the database driver (make sure this is in your classpath!)
	            Class.forName("com.mysql.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return;
	        }
	        
	        
	//static{
		
		try {
			
			
			
			System.out.println("Mail Connection ........!");
			BoneCPConfig boneCPConfig = new BoneCPConfig();
			
			boneCPConfig.setJdbcUrl(properties.getProperty(DB_URL));
			boneCPConfig.setUsername(properties.getProperty(DB_USERNAME));
			boneCPConfig.setPassword(properties.getProperty(DB_PASSWORD));
			
			boneCPConfig.setMinConnectionsPerPartition(Integer.parseInt(properties.getProperty(DB_MINPAR)));
			boneCPConfig.setMaxConnectionsPerPartition(Integer.parseInt(properties.getProperty(DB_MAXPAR)));
			boneCPConfig.setPartitionCount(Integer.parseInt(properties.getProperty(DB_PARTITIONCOUNT)));
			boneCPConfig.setCloseConnectionWatch(true);
		
			//boneCPConfig.setCloseConnectionWatchTimeout(59000);
			/*boneCPConfig.setMinConnectionsPerPartition(5);
			boneCPConfig.setMaxConnectionsPerPartition(100);
			boneCPConfig.setPartitionCount(4);*/
			boneCPConfig.setLazyInit(true);
			//boneCPConfig.setDefaultAutoCommit(true);
			
			
			connectionPool = new BoneCP(boneCPConfig);
			 
		} catch (Exception  e) {
			e.printStackTrace();
			return;
		}
	}
	
	    public static DBUtil getInstance() throws IOException, SQLException, PropertyVetoException {
	        if (datasource == null) {
	            datasource = new DBUtil();
	            return datasource;
	        } else {
	            return datasource;
	        }
	    }

	    public Connection getConnection() throws SQLException {
	        return this.connectionPool.getConnection();
	    }
}