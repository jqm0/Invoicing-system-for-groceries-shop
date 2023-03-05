package project_classes;
import java.util.Scanner;

import java.util.Scanner;

public class MainMenu {
    private Menu shopSettingsMenu;
    private Menu manageItemsMenu;
    private InvoiceManager invoiceManager;
    private ShopManager shopManager;

    public MainMenu() {
        // Initialize shop settings menu
        shopSettingsMenu = new Menu("Shop Settings");
        shopSettingsMenu.addItem(new MenuItem("Load Data"));
        shopSettingsMenu.addItem(new MenuItem("Set Shop Name"));
        shopSettingsMenu.addItem(new MenuItem("Set Invoice Header"));
        shopSettingsMenu.addItem(new MenuItem("Go Back"));

        // Initialize manage items menu
        manageItemsMenu = new Menu("Manage Shop Items");
        manageItemsMenu.addItem(new MenuItem("Add Items"));
        manageItemsMenu.addItem(new MenuItem("Delete Items"));
        manageItemsMenu.addItem(new MenuItem("Change Item Price"));
        manageItemsMenu.addItem(new MenuItem("Report All Items"));
        manageItemsMenu.addItem(new MenuItem("Go Back"));

        // Initialize invoice and shop managers
        invoiceManager = new InvoiceManager();
        shopManager = new ShopManager();
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            // Show main menu
            System.out.println("\nMain Menu:");
            System.out.println("1- Shop Settings");
            System.out.println("2- Manage Shop Items");
            System.out.println("3- Create New Invoice");
            System.out.println("4- Report: Statistics");
            System.out.println("5- Report: All Invoices");
            System.out.println("6- Search (1) Invoice");
            System.out.println("7- Program Statistics");
            System.out.println("8- Exit");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        handleShopSettingsMenu(scanner);
                        break;
                    case 2:
                        handleManageItemsMenu(scanner);
                        break;
                    case 3:
                        handleNewInvoice(scanner);
                        break;
                    case 4:
                        handleStatisticsReport();
                        break;
                    case 5:
                        handleAllInvoicesReport();
                        break;
                    case 6:
                        handleInvoiceSearch(scanner);
                        break;
                    case 7:
                        handleProgramStatistics();
                        break;
                    case 8:
                        exit = handleExit(scanner);
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                scanner.nextLine();
            }
        }
    }

    private void handleShopSettingsMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            // Show shop settings menu
            shopSettingsMenu.show();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // Load Data
                        handleLoadData();
                        break;
                    case 2:
                        // Set Shop Name
                        handleSetShopName(scanner);
                        break;
                    case 3:
                        // Set Invoice Header
                        handleSetInvoiceHeader(scanner);
                        break;
                    case 4:
                        // Go Back
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                scanner.nextLine();
            }
        }
    }

    private void handleLoadData() {
        // TODO: Implement
    }

    private void handleSetShopName(Scanner scanner) {
        System.out.println("Enter new shop name:");
        String name = scanner.next();
        shopManager.setShopName
