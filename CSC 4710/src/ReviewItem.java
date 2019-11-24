import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.cj.jdbc.CallableStatement;

public class ReviewItem {
	private static Connection connect = null;

	public static Boolean addReview(String user_id, int item_id, String review_description, String review_rating) {
		int result = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
        			+ "user=john&password=pass1234");

    		Date date = new Date();
    		date = new java.sql.Date(date.getTime()); 
    		
    		String add_review = "{call projectdb.add_review_SP(?,?,?,?,?,?)}";
    		CallableStatement callableStatement = (CallableStatement) connect.prepareCall(add_review);
    		
    		callableStatement.setDate(1, (java.sql.Date) date );
    		callableStatement.setString(2, user_id);
    		callableStatement.setInt(3, item_id);
    		callableStatement.setString(4, review_description);
    		callableStatement.setString(5, review_rating);
    		callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
    		callableStatement.executeUpdate();
    		result = callableStatement.getInt(6);
    		
		} catch(Exception e) {
			System.out.println(e);
		}
		return (result == 1);
	}
	
	public static ArrayList<Review> ItemReview(int itemID) throws SQLException {
		ArrayList<Review> listReview = new ArrayList<Review>();
        try {
        	String qury ="SELECT id_review_item, post_date, user_id, item_id, review_description, review_rating FROM projectdb.review_item where item_id = '"+ itemID +"';";

        	Class.forName("com.mysql.cj.jdbc.Driver");
        	connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
        			+ "user=john&password=pass1234");

        	Statement statement =  (Statement) connect.createStatement();

        	ResultSet result = statement.executeQuery(qury);
        	
        	int id_review_item = 0;
        	Date post_date = null;
        	String user_id = "";
        	int item_id = 0;
        	String review_description = "";
        	String review_rating = "";

        	while(result.next()) {
        		id_review_item = result.getInt("id_review_item");
                post_date = (java.util.Date) result.getDate("post_date");
        		user_id = result.getString("user_id");
        		item_id = result.getInt("item_id");
        		review_description = result.getString("review_description");
        		review_rating = result.getString("review_rating");
        		
        		String FNAME = "";
        		String LNAME = "";
        		
        		Statement statement1 =  (Statement) connect.createStatement();
        		String stmt = "SELECT FNAME, LNAME FROM projectdb.users where UserID = '"
        					+ user_id + "';";
        		
        		ResultSet set = statement1.executeQuery(stmt);
        		while(set.next()) {
        			FNAME = set.getString("FNAME");
        			LNAME = set.getString("LNAME");
        		}
        		
        		Review review = new Review(id_review_item, post_date, user_id, item_id, review_description, review_rating, 
        				FNAME, LNAME);
        		listReview.add(review);
        	}
    		statement.close();

        } catch(Exception e) {
        	System.out.println(e);
        }

        return listReview;
    }
}
