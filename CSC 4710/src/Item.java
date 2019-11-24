
import java.util.Date;
import java.util.List;

public class Item {
	protected int iditem;
	protected String title;
	protected String description;
	protected Date post_date;
	protected Double price;
	protected String user_id;
	protected List<String> categoryList;
	
	public String getCategoryList() {
		StringBuilder str = new StringBuilder();
		categoryList.forEach((temp) -> {
			str.append(temp);
			str.append(", ");
			});
				
		return str.substring(0, str.length() - 2).toString();
	}

	public void setCategoryList(List<String> categoryList) {
		this.categoryList = categoryList;
	}

	@Override
	public String toString() {

		return "Item [ title=" + title + ", description=" + description + ", post_date=" + post_date + ", price=" + price
				+ ", category=" + this.getCategoryList() + "]";
	}

	public Item(int iditem, String title, String description, Date post_date, Double price, String user_id,
			List<String> categoryList) {
		super();
		this.iditem = iditem;
		this.title = title;
		this.description = description;
		this.post_date = post_date;
		this.price = price;
		this.user_id = user_id;
		this.categoryList = categoryList;
	}
	
	public Item(String title, String description, Date post_date, Double price, List<String> categoryList) {
		super();
		this.categoryList = categoryList;
		this.title = title;
		this.description = description;
		this.post_date = post_date;
		this.price = price;
	}

	public int getIditem() {
		return iditem;
	}
	public void setIditem(int iditem) {
		this.iditem = iditem;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
