package project_classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private Connection conn;
	private ArrayList<Item> itemList;

	public Shop() {
		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing System;" + "encrypt=true;"
					+ "trustServerCertificate=true";
			String user = "sa";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection has been established.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		itemList = new ArrayList<>();
	}

	public void manageShopItems() {
		Scanner input = new Scanner(System.in);
		int choice = 0;

		while (choice != 4) {
			System.out.println("\nSHOP ITEM MENU");
			System.out.println("1. Add Item");
			System.out.println("2. Edit Item");
			System.out.println("3. Remove Item");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			choice = input.nextInt();

			switch (choice) {
			case 1:
				try {
					Item newItem = new Item();
					newItem.setName(input.nextLine());
					System.out.print("Enter item name: ");
					newItem.setName(input.nextLine());
					System.out.print("Enter item id: ");
					newItem.setId(input.nextInt());
					System.out.print("Enter item quantity: ");
					newItem.setQuantity(input.nextInt());
					System.out.print("Enter item unit price: ");
					newItem.setUnitPrice(input.nextInt());
					System.out.print("Enter item quantity amount price: ");
					newItem.setQtyAmountPrice(input.nextInt());
					addItem(newItem);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				// TODO: implement edit item
				System.out.println("Edit Item selected");
				break;
			case 3:
				// TODO: implement remove item
				System.out.println("Remove Item selected");
				break;
			case 4:
				System.out.println("Exiting Shop Item Menu...");
				break;
			default:
				System.out.println("Invalid choice, please enter a number from 1 to 4.");
				break;
			}
		}
	}

	public void addItem(Item newItem) throws SQLException {
		if (!isTableExists("items")) {
			System.out.println(" ... Create items Table because items Table Not exist .. ");
			createItemsTable();
		}
	
		String sql = "INSERT INTO items (name, id, quantity, unitPrice, qtyAmountPrice) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, newItem.getName());
		stmt.setInt(2, newItem.getId());
		stmt.setInt(3, newItem.getQuantity());
		stmt.setInt(4, newItem.getUnitPrice());
		stmt.setInt(5, newItem.getQtyAmountPrice());
		stmt.executeUpdate();
		System.out.println(" ...  item detail is been added to items Table .. ");
		stmt.close();
		itemList.add(newItem);
	}

	private boolean isTableExists(String tableName) throws SQLException {
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet tables = meta.getTables(null, null, tableName, null);
		return tables.next();
	}

	private void createItemsTable() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE items (\n" + "    name TEXT,\n" + "    id INTEGER,\n" + "    quantity INTEGER,\n"
				+ "    unitPrice INTEGER,\n" + "    qtyAmountPrice INTEGER\n" + ");";
		stmt.execute(sql);
		System.out.println("Table 'items' created successfully.");
	}

	public void load() {
		// TODO Auto-generated method stub

	}
}
