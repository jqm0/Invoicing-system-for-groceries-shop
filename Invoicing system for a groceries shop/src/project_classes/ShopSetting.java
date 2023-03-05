package project_classes;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    public void loadData(String tableName) throws SQLException {
        Database.connectToDb();
        ResultSet rs = db.executeQuery("SELECT * FROM " + tableName);
		ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();
        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metadata.getColumnName(i), rs.getObject(i));
            }
            results.add(row);
        }
        for (Map<String, Object> row : results) {
            System.out.println(row);
        }
    }

    
    
    public void setShopName(String name) throws SQLException {
        Database.connectToDb();
        this.shopName = name;
        String[] a= {"a","b"};
        String[] b= {"VARCHAR(15)","VARCHAR(15)"};
        
        // save data to database or data structure as needed
    }
    
    
    public String getShopName() {
		return shopName;
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
