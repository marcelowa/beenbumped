/* src/app/Config.java */
package app;

import java.util.Properties;

public class Config {

	static private Config instance = null;
	private Properties config;
	private String appName;
	private String dbUser;
	private String dbPassword;
	private String dbHost;
	private String dbSchema;
	
	public Config() {
    	try {
    		config = new Properties();
    		config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/config.properties"));
    		
    		appName = config.getProperty("app.name");
    		dbUser = config.getProperty("db.user");
    		dbPassword = config.getProperty("db.password");
    		dbHost = config.getProperty("db.host");
    		dbSchema = config.getProperty("db.schema");
 
    	} catch (Exception ex) {
    		ex.printStackTrace();
        }
	}

	/**
	 * singleton access method
	 * @return Properties
	 */
	static public Config getInstance() {
		if (null == instance) {
			instance = new Config();
		}
		return instance;
	}

	public String getAppName() {
		return appName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public String getDbHost() {
		return dbHost;
	}

	public String getDbSchema() {
		return dbSchema;
	}
}
