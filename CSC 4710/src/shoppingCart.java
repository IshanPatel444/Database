import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class shoppingCart {
	private static Connection connect = null;	
	private static PreparedStatement preparedStatement = null; 

	public static Boolean addItemCart(String user_id, int item_id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
        			+ "user=john&password=pass1234");

        	preparedStatement = connect.prepareStatement("INSERT INTO shopping_cart"
        			+ " (id_user, id_item) values(?,?)");
			preparedStatement.setString(1,user_id);
			preparedStatement.setInt(2, item_id);
			preparedStatement.executeUpdate();
			return true;
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public static ArrayList<Integer> CartItemID() throws SQLException {
		ArrayList<Integer> intItem = new ArrayList<>();
		try {
	        String sql = "SELECT id_item FROM projectdb.shopping_cart where id_user = \"" + LogInUserData.getUserID() + "\";";
	        Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
		              + "user=john&password=pass1234");
			
			Statement statement =  (Statement) connect.createStatement();
	        ResultSet resultSet = statement.executeQuery(sql);
	        
	        while(resultSet.next()) {
	        	intItem.add(resultSet.getInt("id_item"));
	        }
	        statement.close();
		} catch(Exception e) {
				System.out.println(e);
			}
		return intItem;
	}
	
	public static ArrayList<Item> CartItem(ArrayList<Integer> itemList) throws SQLException {
		ArrayList<Item> listItem = new ArrayList<Item>();
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	              + "user=john&password=pass1234");
		for(int i = 0; i< itemList.size(); i++) {
            int item_ID = itemList.get(i);

            String qury = "SELECT iditem, description, price, post_date,user_id, title FROM projectdb.item where iditem = " + item_ID + ";";
    		Statement statement1 = (Statement) connect.createStatement();
            ResultSet result = statement1.executeQuery(qury);

            String user_id = "";
            int iditem = 0;
            String title = "";
            String description = "";
            Double price = 0.0;
            Date post_date = null;
            List<String> categoryOfItem = new ArrayList<String>();
                        
            while (result.next()) {
            	user_id = result.getString("user_id");
            	iditem = result.getInt("iditem");
                title = result.getString("title");
                description = result.getString("description");
                price = result.getDouble("price");
                post_date = (java.util.Date) result.getDate("post_date");
                
                String stmt = "select category_description from projectdb.category_item where item_ID =" + item_ID +";";
        		
                Statement statement2 = (Statement) connect.createStatement();
                ResultSet rs = statement2.executeQuery(stmt);
                
                while (rs.next()) {
                	categoryOfItem.add(rs.getString("category_description"));
                }
                statement2.close();
            }
            Item item = new Item(iditem, title,description, post_date, price,user_id, categoryOfItem);
            listItem.add(item);
            statement1.close();
		}
		} catch(Exception e) {
			System.out.println(e);
		}
        return listItem;	
	}
}


