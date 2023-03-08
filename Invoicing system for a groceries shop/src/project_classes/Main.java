package project_classes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, IOException, InterruptedException {
		Integer countCase1 = 0;
		Integer countCase2 = 0;
		Integer countCase3 = 0;
		Integer countCase4 = 0;
		Integer countCase5 = 0;
		Integer countCase6 = 0;
		Integer countCase7 = 0;
		Integer countCase8 = 0;

		Menu menu = new Menu();
		Map<Integer, String> menuItems = MenuItem.mainMenuItems();
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				menu.show(menuItems);

				try {
					int choice = scanner.nextInt();
					switch (choice) {
					case 1:
						countCase1++;

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
						countCase2 ++;
						System.out.println("You selected : Manage Shop Items\n");
						Shop shop = new Shop();
						shop.manageShopItems();

						break;
					case 3:
						countCase3 ++;
						Invoice newInvoice = new Invoice();
						Invoice.createNewInvoice();
						break;
					case 4:
						countCase4 ++;
						// handle Report: Statistics menu option
						break;
					case 5:
						countCase5 ++;
						// handle Report: All Invoices menu option
						break;
					case 6:
						countCase6 ++;
						// handle Search (1) Invoice menu option
						break;
					case 7:
						countCase7 ++;
						System.out.println("===== Program Statistics for current Run =====");
						System.out.println("The option 1 has been entered " + countCase1 + " Times " + "\r\n"
								+ "The option 2 has been entered " + countCase2 + " Times " + "\r\n"
								+ "The option 3 has been entered " + countCase3 + " Times " + "\r\n"
								+ "The option 4 has been entered " + countCase4 + " Times " + "\r\n"
								+ "The option 5 has been entered " + countCase5 + " Times " + "\r\n"
								+ "The option 6 has been entered " + countCase6 + " Times " + "\r\n"
								+ "The option 7 has been entered " + countCase7 + " Times " + "\r\n");
						break;
					case 8:
						countCase8 ++;
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
}
