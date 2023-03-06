package project_classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
	private Connection conn;
	private ArrayList<Item> itemList;

	public Shop() throws SQLException {
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
		if (!isTableExists("items")) {
			createItemsTable();
		}

		loadItemsFromDatabase();
	}

	public void manageShopItems() throws SQLException {
		Scanner input = new Scanner(System.in);
		Shop shop = new Shop();
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
				System.out.print("Enter item ID to edit: ");
				int editItemId = input.nextInt();
				input.nextLine(); // consume the newline character
				Item itemToEdit = shop.searchItemById(editItemId);
				if (itemToEdit != null) {
					System.out.print("Enter new name: ");
					String newName = input.nextLine();
					System.out.print("Enter new quantity: ");
					int newQuantity = input.nextInt();
					System.out.print("Enter new unit price: ");
					int newUnitPrice = input.nextInt();
					System.out.print("Enter new quantity amount price: ");
					int newQtyAmountPrice = input.nextInt();
					itemToEdit.setName(newName);
					itemToEdit.setQuantity(newQuantity);
					itemToEdit.setUnitPrice(newUnitPrice);
					itemToEdit.setQtyAmountPrice(newQtyAmountPrice);
					shop.editItem(itemToEdit);
					System.out.println("Item edited successfully.");
				} else {
					System.out.println("Item not found.");
				}
				break;

			case 3:
				System.out.print("Enter item ID to remove: ");
				int removeItemId = input.nextInt();
				input.nextLine(); // consume the newline character
				Item itemToRemove = shop.searchItemById(removeItemId);
				if (itemToRemove != null) {
					try {
						shop.removeItem(removeItemId);
						System.out.println("Item removed successfully.");
					} catch (SQLException e) {
						System.out.println("Error removing item from database.");
						e.printStackTrace();
					}
				} else {
					System.out.println("Item not found.");
				}
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

	public Item searchItemById(int id) {
		for (Item item : itemList) {
			if (item.getId() == id) {
				System.out.println("Found item with ID " + id);
				return item;
			}
		}
		return null;
	}

	private void loadItemsFromDatabase() throws SQLException {
		String sql = "SELECT * FROM items";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int quantity = rs.getInt("quantity");
			int unitPrice = rs.getInt("unitPrice");
			int qtyAmountPrice = rs.getInt("qtyAmountPrice");

			Item newItem = new Item(name, id, quantity, unitPrice, qtyAmountPrice);
			itemList.add(newItem);
		}

		stmt.close();
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

	public void removeItem(int itemId) throws SQLException {
		if (!isTableExists("items")) {
			createItemsTable();
		}
		Item itemToRemove = searchItemById(itemId);
		if (itemToRemove != null) {
			String sql = "DELETE FROM items WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, itemId);
			stmt.executeUpdate();
			stmt.close();
			itemList.remove(itemToRemove);
			System.out.println("Item removed successfully.");
		} else {
			System.out.println("Item not found.");
		}
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

	public void editItem(Item itemToEdit) throws SQLException {
		String sql = "UPDATE items SET name=?, quantity=?, unitPrice=?, qtyAmountPrice=? WHERE id=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, itemToEdit.getName());
		stmt.setInt(2, itemToEdit.getQuantity());
		stmt.setInt(3, itemToEdit.getUnitPrice());
		stmt.setInt(4, itemToEdit.getQtyAmountPrice());
		stmt.setInt(5, itemToEdit.getId());
		stmt.executeUpdate();
		stmt.close();
	}

}
