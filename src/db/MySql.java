package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.Config;

public class MySql {

	static private MySql instance = null;

	private Connection connection;

	// protected so creation is only handled through getInstance
	protected MySql() {
		try {
			Config c = Config.getInstance();
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//Class.forName("com.mysql.jdbc.Driver");
			try {
				connection = DriverManager.getConnection("jdbc:mysql://"+c.getDbHost()+"/"+c.getDbSchema()+"?user="+c.getDbUser()+"&password="+c.getDbPassword());
			} catch (SQLException e) {
				System.out.print("\ncan't get connection\n");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.print("\ncan't get driver\n");
			e.printStackTrace();
		}

	}

	public Connection getConnection() {
		return connection;
	}
	
	static public MySql getInstance() {
		if (null == instance) {
			instance = new MySql();
		}
		return instance;
	}
}