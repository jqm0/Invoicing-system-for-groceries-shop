package project_classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Invoice {
	String customerFullName;
	Integer phoneNumber;
	Date date;
	Integer numberOfItems = 0;
	Integer totalAmount;
	Integer paidAmount;
	Integer balance;
	Integer idInvoice;
	Integer txtFileCount = 1;

	public static void createNewInvoice() throws IOException, InterruptedException {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Scanner sa = new Scanner(System.in);
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(" - How many invoice You want to store ?");
		int numberOfInvoices = sa.nextInt();

		Connection con = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			// Check if the table exists
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, "CUSTOMER_INVOICE", null);
			if (!resultSet.next()) {
				// If the table does not exist, create it
				String createTableSql = "CREATE TABLE CUSTOMER_INVOICE (" + "Invoice_ID INT IDENTITY(1,1) PRIMARY KEY,"
						+ "Invoice_Number INT," + "Customer_Full_Name VARCHAR(255)," + "Phone_Number INT,"
						+ "Invoice_Date VARCHAR(255)," + "Number_Of_Items INT," + "Total_Amount FLOAT,"
						+ "Paid_Amount FLOAT," + "Balance FLOAT" + ")";

				PreparedStatement createTableStatement = con.prepareStatement(createTableSql);
				createTableStatement.executeUpdate();
				System.out.println("Table created successfully");
			}

			for (int i = 0; i < numberOfInvoices; i++) {
				System.out.println("-------------- Invoice " + (i + 1) + "---------------");
				System.out.println("Please Enter Customer's Full Name:");
				String customerName = bf.readLine();
				System.out.println("Please Enter Customer's Phone Number:");
				int phoneNumber = sa.nextInt();
				System.out.println("Please Enter Invoice Date:");
				String invoiceDate = sa.next();
				System.out.println("Please Enter Number of Items:");
				int numberOfItems = sa.nextInt();
				System.out.println("Please Enter Total Amount:");
				float totalAmount = sa.nextFloat();
				System.out.println("Please Enter Paid Amount:");
				float paidAmount = sa.nextFloat();
				float balance = paidAmount - totalAmount;

				String sql = "INSERT INTO CUSTOMER_INVOICE(Customer_Full_Name,Phone_Number,Invoice_Date,Number_Of_Items,Total_Amount,Paid_Amount,Balance) VALUES(?,?,?,?,?,?,?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, customerName);
				ps.setInt(2, phoneNumber);
				ps.setString(3, invoiceDate);
				ps.setInt(4, numberOfItems);
				ps.setFloat(5, totalAmount);
				ps.setFloat(6, paidAmount);
				ps.setFloat(7, balance);
				ps.executeUpdate();

				System.out.println(" Data successfully inserted");
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println(e);
				}
			}
		}
	}

	public static void searchInvoiceById() throws IOException, InterruptedException {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Scanner sa = new Scanner(System.in);

		Connection con = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			// Prompt the user to enter the invoice ID
			System.out.println("Please Enter the Invoice ID:");
			int invoiceID = sa.nextInt();

			// Prepare the SQL statement to search for the invoice
			String sql = "SELECT * FROM CUSTOMER_INVOICE WHERE Invoice_ID=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, invoiceID);

			// Execute the SQL statement and retrieve the result set
			ResultSet rs = ps.executeQuery();

			// Check if the result set is empty
			if (!rs.next()) {
				System.out.println("Invoice Not Found");
			} else {
				// Print the header
				System.out.println("Invoice Details:");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(
						"Invoice ID\tCustomer Name\tPhone Number\tInvoice Date\tNumber of Items\tTotal Amount\tPaid Amount\tBalance");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");

				// Print the details of the invoice
				do {
					int Invoice_ID = rs.getInt("Invoice_ID");
					String customerName = rs.getString("Customer_Full_Name");
					int phoneNumber = rs.getInt("Phone_Number");
					String invoiceDate = rs.getString("Invoice_Date");
					int numberOfItems = rs.getInt("Number_Of_Items");
					float totalAmount = rs.getFloat("Total_Amount");
					float paidAmount = rs.getFloat("Paid_Amount");
					float balance = rs.getFloat("Balance");

					System.out.println(Invoice_ID + "\t\t" + customerName + "\t\t" + phoneNumber + "\t\t" + invoiceDate
							+ "\t\t" + numberOfItems + "\t\t" + totalAmount + "\t\t" + paidAmount + "\t\t" + balance);
					System.out.println(
							"--------------------------------------------------------------------------------------------------------------------------------");

				} while (rs.next());
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println(e);
				}
			}
		}
	}

	public static void displayAllInvoices() throws IOException, InterruptedException {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		Connection con = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			con = DriverManager.getConnection(url, user, pass);

			// Check if the table exists
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet resultSet = metadata.getTables(null, null, "CUSTOMER_INVOICE", null);
			if (!resultSet.next()) {
				// If the table does not exist, print an error message and return
				System.out.println("Table not found");
				return;
			}

			String sql = "SELECT * FROM CUSTOMER_INVOICE";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			System.out.println(
					"Invoice Number\tCustomer Name\tPhone Number\tInvoice Date\tNumber of Items\tTotal Amount\tPaid Amount\tBalance");
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------------------");

			while (rs.next()) {
				int invoiceId = rs.getInt("Invoice_ID");
				String customerName = rs.getString("Customer_Full_Name");
				int phoneNumber = rs.getInt("Phone_Number");
				String invoiceDate = rs.getString("Invoice_Date");
				int numberOfItems = rs.getInt("Number_Of_Items");
				float totalAmount = rs.getFloat("Total_Amount");
				float paidAmount = rs.getFloat("Paid_Amount");
				float balance = rs.getFloat("Balance");
				System.out.println(invoiceId + "\t\t" + customerName + "\t\t" + phoneNumber + "\t\t" + invoiceDate
						+ "\t\t" + numberOfItems + "\t\t" + totalAmount + "\t\t" + paidAmount + "\t\t" + balance);
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					System.err.println(e);
				}
			}
		}
	}

	public static void statisticsReport() throws IOException, InterruptedException {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Connection con = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();
			String sql = "select SUM (Number_Of_Items) AS NumberOfItems, COUNT (Customer_Full_Name) AS NO_OF_INVOICE, SUM (Total_Amount) AS  Total_Sale from CUSTOMER_INVOICE  ";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				System.out.println("Number Of Items\tNO OF INVOICE \tTotal Sale");
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");

				System.out.println(rs.getString("NumberOfItems") + "\t\t" + rs.getString("NO_OF_INVOICE") + "\t\t"
						+ rs.getString("Total_Sale"));
				System.out.println(
						"--------------------------------------------------------------------------------------------------------------------------------");

			}
		}

		catch (Exception ex) {
			System.err.println(ex);
		}
	}

}
