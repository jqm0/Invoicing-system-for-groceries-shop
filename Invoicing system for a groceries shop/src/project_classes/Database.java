package project_classes;

import java.sql.*;
import java.util.Scanner;

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
	

	public Database() {
		super();
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
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < columnNames.length; i++) {
	        sb.append(columnNames[i]).append(" ").append(columnTypes[i]).append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);

	    String sql = "CREATE TABLE " + tableName + " (" + sb.toString() + ")";
	    executeUpdate(sql);
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

	public static int executeUpdate(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeUpdate(sql);
	}

	public static void insert(String tableName, String value) throws SQLException {
	    String sql = "INSERT INTO " + tableName + " VALUES (?)";
	    PreparedStatement statement = conn.prepareStatement(sql);
	    statement.setString(1, value);
	    statement.executeUpdate();
	}
	public static boolean isTableExists(String tableName) throws SQLException {
	    boolean tableExists = false;
	    DatabaseMetaData meta = conn.getMetaData();
	    ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
	    if (resultSet.next()) {
	        tableExists = true;
	    }
	    return tableExists;
	}


	public static Connection connectToDb(String user, String pass, String url) {
		try {
		
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(conn);
		return conn;
		
	}


}
