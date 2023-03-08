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
						+ "Customer_Full_Name VARCHAR(255)," + "Phone_Number INT," + "Invoice_Date VARCHAR(255),"
						+ "Number_Of_Items INT," + "Total_Amount FLOAT," + "Paid_Amount FLOAT," + "Balance FLOAT" + ")";
				PreparedStatement createTableStatement = con.prepareStatement(createTableSql);
				createTableStatement.executeUpdate();
				System.out.println("Table created successfully");
			}
			
			for (int i = 0; i < numberOfInvoices; i++) {
				System.out.println("-------------- Invoice " + (i+1) + "---------------");
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
	public static void searchInvoiceByPhoneNumber(int phoneNumber) throws SQLException {
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
	            System.err.println("Table not found");
	            return;
	        }

	        String sql = "SELECT * FROM CUSTOMER_INVOICE WHERE Phone_Number=?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, phoneNumber);

	        resultSet = ps.executeQuery();

	        if (resultSet.next()) {
	            System.out.println(" ======================= Invoice Details =======================");
	            System.out.println("Customer Full Name: " + resultSet.getString("Customer_Full_Name"));
	            System.out.println("Phone Number: " + resultSet.getInt("Phone_Number"));
	            System.out.println("Invoice Date: " + resultSet.getString("Invoice_Date"));
	            System.out.println("Number of Items: " + resultSet.getInt("Number_Of_Items"));
	            System.out.println("Total Amount: " + resultSet.getFloat("Total_Amount"));
	            System.out.println("Paid Amount: " + resultSet.getFloat("Paid_Amount"));
	            System.out.println("Balance: " + resultSet.getFloat("Balance"));
	            System.out.println("================================================================== ");
	        } else {
	            System.err.println("Invoice not found");
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

}
