package project_classes;
import java.util.Map;

public class Menu {
    public void show(Map<Integer, String> menuItems) {
        for (Map.Entry<Integer, String> item : menuItems.entrySet()) {
            System.out.println(item.getKey() + ". " + item.getValue());
        }
    }
}
