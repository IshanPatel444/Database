import java.util.Date;

public class Review {
	private int id_review_item;
	private Date post_date;
	private String user_id;
	private int item_id;
	private String review_description;
	private String review_rating;
	private String LNAME;
	private String FNAME;
	
	public Review(int id_review_item, Date post_date, String user_id, int item_id, String review_description,
			String review_rating, String lNAME, String fNAME) {
		super();
		this.id_review_item = id_review_item;
		this.post_date = post_date;
		this.user_id = user_id;
		this.item_id = item_id;
		this.review_description = review_description;
		this.review_rating = review_rating;
		LNAME = lNAME;
		FNAME = fNAME;
	}

	public String getLNAME() {
		return LNAME;
	}

	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}

	public String getFNAME() {
		return FNAME;
	}

	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}	
	

	@Override
	public String toString() {
		return "Review [id_review_item=" + id_review_item + ", post_date=" + post_date + ", user_id=" + user_id
				+ ", item_id=" + item_id + ", review_description=" + review_description + ", review_rating="
				+ review_rating + "]";
	}

	public int getId_review_item() {
		return id_review_item;
	}
	public void setId_review_item(int id_review_item) {
		this.id_review_item = id_review_item;
	}
	public Date getPost_date() {
		return post_date;
	}
	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getReview_description() {
		return review_description;
	}
	public void setReview_description(String review_description) {
		this.review_description = review_description;
	}
	public String getReview_rating() {
		return review_rating;
	}
	public void setReview_rating(String review_rating) {
		this.review_rating = review_rating;
	}
}
