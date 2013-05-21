package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySql {

	static private MySql instance = null;

	private Connection connection;

	// protected so creation is only handled through getInstance
	protected MySql() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/beenbumped?user=root&password=bitter");
		} catch (SQLException e) {
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