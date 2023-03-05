package project_classes;

import java.util.HashMap;
import java.util.Map;

public class MenuItem {
    public static Map<Integer, String> mainMenuItems() {
        Map<Integer, String> menuItems = new HashMap<>();
        menuItems.put(1, "Shop Settings");
        menuItems.put(2, "Manage Shop Items");
        menuItems.put(3, "Create New Invoice");
        menuItems.put(4, "Report: Statistics");
        menuItems.put(5, "Report: All Invoices");
        menuItems.put(6, "Search (1) Invoice");
        menuItems.put(7, "Program Statistics");
        menuItems.put(8, "Exit");
        return menuItems;
    }
}

