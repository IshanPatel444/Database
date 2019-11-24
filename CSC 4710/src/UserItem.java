import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserItem {
	private static Connection connect = null;

	public static ArrayList<Item> SearchUserItem(String userID) throws SQLException {
		ArrayList<Item> listItem = new ArrayList<Item>();
        try {

        String qury = "SELECT iditem, description, price, post_date, title FROM projectdb.item where user_id = '" + userID + "';";

        Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	              + "user=john&password=pass1234");
		
		Statement statement =  (Statement) connect.createStatement();

        ResultSet result = statement.executeQuery(qury);

        int item_ID = 0;
        String title = "";
        String description = "";
        Double price = 0.0;
        Date post_date = null;
        List<String> categoryOfItem = new ArrayList<String>();
        
        while(result.next()) {
        	item_ID = result.getInt("iditem");
            title = result.getString("title");
            description = result.getString("description");
            price = result.getDouble("price");
            post_date = (java.util.Date) result.getDate("post_date");
            
            String stmt = "select category_description from projectdb.category_item where item_ID =" + item_ID +";";
            
    		Statement statement1 = (Statement) connect.createStatement();
            ResultSet rs = statement1.executeQuery(stmt);
            
            while (rs.next()) {
            	categoryOfItem.add(rs.getString("category_description"));
            }
            statement1.close();
            Item item = new Item(title,description, post_date, price, categoryOfItem);
            listItem.add(item);
        }
        statement.close();

		} catch(Exception e) {
			System.out.println(e);
		}

        return listItem;
    }
}