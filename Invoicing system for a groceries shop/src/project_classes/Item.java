package project_classes;

public class Item {
	public String name;
	Integer unitPrice ;
	Integer id;
	Integer quantity;
	Integer qtyAmountPrice;
	
	public Item(String name, Integer unitPrice, Integer id, Integer quantity, Integer qtyAmountPrice) {
		super();
		this.name = name;
		this.unitPrice = unitPrice;
		this.id = id;
		this.quantity = quantity;
		this.qtyAmountPrice = qtyAmountPrice;
	}
	
	public Item() {
		// TODO Auto-generated constructor stub
	}

	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}
	public Integer getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getQtyAmountPrice() {
		return qtyAmountPrice;
	}
	public void setQtyAmountPrice(Integer qtyAmountPrice) {
		this.qtyAmountPrice = qtyAmountPrice;
	}
	public String getName() {
		return name;
	}
	
}
