
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchDataInCategory {
	private static Connection connect = null;

	public static ArrayList<Item> SearchCategory(String category) throws SQLException {
		ArrayList<Item> listItem = new ArrayList<Item>();
        try {
        String sql = 
        		"select item_ID from projectdb.category_item where category_description  Like '"+category+ "%';";
        Class.forName("com.mysql.cj.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	              + "user=john&password=pass1234");
		
		Statement statement =  (Statement) connect.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int item_ID = resultSet.getInt("item_ID");

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
        resultSet.close();
        statement.close();
		} catch(Exception e) {
			System.out.println(e);
		}

        return listItem;
    }
}
