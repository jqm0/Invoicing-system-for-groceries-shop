package project_classes;

import java.sql.Connection;
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

	public void createNewInvoice() throws SQLException {
		Report rep = new Report();
		boolean choice = true;
		Scanner sc = new Scanner(System.in);
		Shop shopX = new Shop();
		System.out.print("Enter Shop name : ");
		
		String shopName = sc.next();
		Invoice invoiceX = new Invoice();
		System.out.print("Enter customer Full Name : ");
		customerFullName = sc.next();
		System.out.print("Enter phone Number : ");
		invoiceX.phoneNumber = sc.nextInt();

		while (choice) {
			Item itemX = new Item();
			System.out.print("Enter Item Name : ");
			itemX.name = sc.next();
			System.out.print("Enter Item id : ");
			itemX.id = sc.nextInt();
			idInvoice = itemX.id;
			System.out.print("Enter unitPrice : ");
			itemX.unitPrice = sc.nextInt();
			System.out.print("Enter quantity : ");
			itemX.quantity = sc.nextInt();
			itemX.qtyAmountPrice = itemX.unitPrice * itemX.quantity;
			shopX.itemList.add(itemX);
			numberOfItems++;
			System.out.print("Do You Want Add Anouther Item write 1 if yes .. : ");
			if (sc.nextInt() != 1) {
				choice = false;

			}
		}
		System.out.println("---------------- Invoice Details ----------------");
		Integer total = 0;

		for (Item i : shopX.itemList) {
			System.out.println("=====================");
			System.out.println("Item name : " + i.name);
			System.out.println("Item ID : " + i.id);
			System.out.println("Item quantity : " + i.quantity);
			System.out.println("Item unitPrice : " + i.unitPrice);
			System.out.println("Total Price for this Item " + i.qtyAmountPrice);
			total = total + i.qtyAmountPrice;
		}
		totalAmount = total;

		System.out.println("======================");
		System.out.println("Total Price : " + total);
		System.out.println("---------------------");
		System.out.print(" >> Enter paid Amount :");
		paidAmount = sc.nextInt();
		balance = paidAmount - totalAmount;
		System.out.println("======================");
		System.out.println("Balance : " + balance);
		sc.close();
		//rep.invoicesList.add(invoiceX);
		// _________________________________________

		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
					+ "trustServerCertificate=true";
			String user = "sa";
			String password = "root";
			// Create connection to database
			Connection connection = DriverManager.getConnection(url, user, password);

			// Check if invoice table exists, if not create it
			Statement statement = connection.createStatement();
			String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'invoice';";
			ResultSet checkTableResult = statement.executeQuery(checkTableQuery);
			checkTableResult.next();
			int tableCount = checkTableResult.getInt(1);
			if (tableCount == 0) {
				String createTableQuery = "CREATE TABLE invoice (" + "id INT NOT NULL IDENTITY(1,1),"
						+ "customer_fullname VARCHAR(255) NOT NULL," + "phone_number VARCHAR(50) NOT NULL,"
						+ "invoice_date DATE NOT NULL," + "number_of_items INT NOT NULL," + "total_amount INT NOT NULL,"
						+ "paid_amount INT NOT NULL," + "balance INT NOT NULL," + "PRIMARY KEY(id)" + ");";
				statement.execute(createTableQuery);
			}

			// Insert invoice data into database
			String insertQuery = "INSERT INTO invoice (customer_fullname, phone_number, invoice_date, number_of_items, total_amount, paid_amount, balance) VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, customerFullName);
			preparedStatement.setInt(2, invoiceX.phoneNumber);
			Date date = new Date();
			preparedStatement.setDouble(3, date.getTime());
			preparedStatement.setInt(4, numberOfItems);
			preparedStatement.setInt(5, totalAmount);
			preparedStatement.setInt(6, paidAmount);
			preparedStatement.setInt(7, balance);
			preparedStatement.executeUpdate();

			// Close connection to database
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
