package snippets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.sql.CallableStatement;

public class TestMysql {

	private Connection connect = null;
	private Statement statement = null;
	private CallableStatement callableStatement = null;
	private ResultSet resultSet = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMysql x = new TestMysql();
		try {
			x.readDataBase();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public void readDataBase() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://localhost/beenbumped?" + "user=root&password=bitter");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from test2");
			writeResultSet(resultSet);

			
			Calendar cal = Calendar.getInstance();
			cal.set(2012, 10, 10);
			java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
			
			// PreparedStatements can use variables and are more efficient
			/*
			preparedStatement = connect.prepareStatement("insert into test2(title, date) values (?, ?)");
			preparedStatement.setString(1, "this is the title");
			preparedStatement.setDate(2, date);
			preparedStatement.executeUpdate();
			*/
			
			callableStatement = connect.prepareCall("call sq_insertTest2(?, ?)");
			callableStatement.setString(1, "this is the title in callableStatement");
			cal.set(2013, 01, 05);
			date = new java.sql.Date(cal.getTimeInMillis());
			callableStatement.setDate(2, date);
			callableStatement.execute();
			
			resultSet = statement.executeQuery("select * from test2");
			writeResultSet(resultSet);
			
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String title = resultSet.getString("title");
			Date date = resultSet.getDate("date");
			System.out.println("Title: " + title);
			System.out.println("Date: " + date);
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		}
		catch (Exception e) {

		}
	}

}
