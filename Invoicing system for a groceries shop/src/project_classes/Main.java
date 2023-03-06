package project_classes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException {
		Menu menu = new Menu();
		Map<Integer, String> menuItems = MenuItem.mainMenuItems();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			menu.show(menuItems);

			try {
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					// Shop Settings menu option
					Map<Integer, String> shopSettingsMenuItems = new HashMap<>();
					ShopSetting sh = new ShopSetting();
					shopSettingsMenuItems.put(1, "Load Data (Items and invoices)");
					shopSettingsMenuItems.put(2, "Set Shop Name");
					shopSettingsMenuItems.put(3, "Set Invoice Header (Tel / Fax / Email / Website)");
					shopSettingsMenuItems.put(4, "Go Back");
					while (true) {
						menu.show(shopSettingsMenuItems);
						try {
							int shopSettingsChoice = scanner.nextInt();
							switch (shopSettingsChoice) {
							case 1:
								System.out.println("Enter table name to be loaded : ");
								String tableN = scanner.next();
								sh.loadData(tableN);

								break;
							case 2:
								Database.connectToDb();
								System.out.println("Please enter shop name: ");
								sh.setShopName(scanner.next());
								System.out.println("1 - store it in new Table ");
								System.out.println("2 - store it in existing Table ");
								int option = scanner.nextInt();
								if (option == 1) {
									System.out.println("Enter table name: ");
									String tableName = scanner.next();
									System.out.println("Enter column names (separated by commas): ");
									String columnNamesStr = scanner.next();
									String[] columnNames = columnNamesStr.split(",");
									System.out.println("Enter column types (separated by commas): ");
									String columnTypesStr = scanner.next();
									String[] columnTypes = columnTypesStr.split(",");
									Database.createTable(tableName, columnNames, columnTypes);
									Database.insert(tableName, sh.getShopName());
								} else if (option == 2) {
									System.out.println("Enter table name: ");
									String tableName = scanner.next();
									Database.insert(tableName, sh.getShopName());
								} else {
									System.out.println("Invalid option selected.");
								}
								break;

							case 3:
								System.out.print("Enter tel: ");
								String tel = scanner.next();
								System.out.print("Enter fax: ");
								String fax = scanner.next();
								System.out.print("Enter email: ");
								String email = scanner.next();
								System.out.print("Enter website: ");
								String website = scanner.next();
								sh.setInvoiceHeader(tel, fax, email, website);

								break;
							case 4:
								// go back to the main menu
								break;
							default:
								System.out.println("Invalid choice. Please try again.");
								break;
							}
							if (shopSettingsChoice == 4) {
								break;
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid input. Please enter a number.");
							scanner.next();
						}
					}
					break;

				case 2:
					// Manage Shop Items
					System.out.println("You selected : Manage Shop Items\n");

					// create Shop object
					Shop shop = new Shop();
					shop.manageShopItems();

					// save shop items to database

					break;
				case 3:
					
					// handle Create New Invoice menu option
					break;
				case 4:
					// handle Report: Statistics menu option
					break;
				case 5:
					// handle Report: All Invoices menu option
					break;
				case 6:
					// handle Search (1) Invoice menu option
					break;
				case 7:
					// handle Program Statistics menu option
					break;
				case 8:
					System.out.println("Are you sure you want to exit? (Y/N)");
					String confirm = scanner.next();
					if (confirm.equalsIgnoreCase("Y")) {
						return;
					}
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
		}
	}
}
