package project_classes;

import java.sql.*;

public class Database {
	private static Connection conn = null;
	private String url;
	private String username;
	private String password;

	public Database(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public static Connection connectToDb() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
					+ "trustServerCertificate=true";
			String user = "sa";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(conn);
		return conn;
	}

	public static void createTable(String tableName, String[] columnNames, String[] columnTypes) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (";
		for (int i = 0; i < columnNames.length; i++) {
			sql += columnNames[i] + " " + columnTypes[i];
			if (i < columnNames.length - 1) {
				sql += ",";
			}
		}
		sql += ")";
		Statement statement = conn.createStatement();
		statement.executeUpdate(sql);
	}

	public void disconnect() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeQuery(sql);
	}

	public int executeUpdate(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeUpdate(sql);
	}
}
