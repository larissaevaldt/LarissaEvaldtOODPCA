import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

	private String db = "jdbc:mysql://apontejaj.com:3306/world";
	private String un = "cctstudent";
	private String pw = "Pass1234!";
	// this was in the constructor but we made global
	private Connection conn;
	private Statement stmt;
	private ResultSet rs = null;

	// Inner class to hold an instance of the outer class
	private static class DataSourceHelper {

		private static DataSource instance = new DataSource();

	}

	// constructor establishing the connection
	private DataSource() {

		try {

			// Get a connection to the database
			conn = DriverManager.getConnection(db, un, pw);

			// Get a statement from the connection
			stmt = conn.createStatement();

		} catch (SQLException se) {
			System.out.println("SQL Exception:");

			// Loop through the SQL Exceptions
			while (se != null) {
				System.out.println("State  : " + se.getSQLState());
				System.out.println("Message: " + se.getMessage());
				System.out.println("Error  : " + se.getErrorCode());

				se = se.getNextException();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// method to do a select statement
	public ResultSet select(String query) {

		try {
			rs = stmt.executeQuery(query);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	// method to close the connection and the statement
	public void closing() {
		// Close the result set, statement and the connection
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// method in charge of posting data or saving data into the database
	public boolean save(String query) {

		try {
			stmt.execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static DataSource getInstance() {
		return DataSourceHelper.instance;
	}

}