package project_classes;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
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
				                    // handle Load Data option
				                    break;
				                case 2:
				                    // handle Set Shop Name option
				                    break;
				                case 3:
				                    // handle Set Invoice Header option
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
					// handle Manage Shop Items menu option
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
