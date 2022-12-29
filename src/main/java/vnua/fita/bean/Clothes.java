package vnua.fita.bean;

public class Clothes {
	private Integer id;
	private String name;
	private Integer price;
	private String brand;
	private Integer quantity;
	private String origin;
	private String size;
	private String preview;
	
	public Clothes() {
		
	}
	
	public Clothes(Integer id, String name, Integer price, String brand, Integer quantity, String origin, String size, String preview) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.quantity = quantity;
		this.origin = origin;
		this.size = size;
		this.preview = preview;
	}
	
	public Clothes(String name, Integer price, String brand, Integer quantity, String origin, String size, String preview) {
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.quantity = quantity;
		this.origin = origin;
		this.size = size;
		this.preview = preview;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	@Override
	public String toString() {
		return "Clothes [id=" + id + ", name=" + name + ", price=" + price + ", brand=" + brand + ", quantity="
				+ quantity + ", origin=" + origin + ", size=" + size + ", preview=" + preview + "]";
	}
}
