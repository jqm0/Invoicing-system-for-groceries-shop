package project_classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopSetting {
    private String shopName;
    private String invoiceHeader;
    String DB_URL = "jdbc:sqlserver://localhost:1433;" + "databaseName=HotelDBMS;" + "encrypt=true;"+ "trustServerCertificate=true";
	String USERNAME = "sa";
	String PASSWORD = "root";
	 Database db = new Database(DB_URL, USERNAME, PASSWORD); // create instance of Database class

    List<Map<String, Object>> results = new ArrayList<>();
    public ShopSetting() {
        this.shopName = "";
        this.invoiceHeader = "";
        new HashMap<>();
    }

    public void loadData() throws SQLException {
    	
        Database.connectToDb();
       ResultSet rs = db.executeQuery("SELECT * FROM Table_1");
       while (rs.next()) {
           Map<String, Object> row = new HashMap<>();
           row.put("column1", rs.getObject("name"));
           row.put("column2", rs.getObject("roll"));
           // add more columns as needed
           results.add(row);
      
       }
       for(Map<String, Object> a : results) {
    	   System.out.println(a);
       }
    }
    
    
    public void setShopName(String name) throws SQLException {
        Database.connectToDb();
        this.shopName = name;
        String[] a= {"a","b"};
        String[] b= {"VARCHAR(15)","VARCHAR(15)"};
        Database.createTable("names",a, b);
        // save data to database or data structure as needed
    }
    
    public void setInvoiceHeader(String tel, String fax, String email, String website) {
        this.invoiceHeader = "Tel: " + tel + ", Fax: " + fax + ", Email: " + email + ", Website: " + website;
        // save data to database or data structure as needed
    }
    
    public void goBack() {
        // go back to main menu
    }
    
    // other methods as needed
    
}
