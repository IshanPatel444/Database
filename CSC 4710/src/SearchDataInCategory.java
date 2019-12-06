
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SearchDataInCategory {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;

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

        ArrayList<Integer> intItem = FavItem();
        
        if(intItem != null) {
        	for (int i = 0; i < listItem.size(); i++)
        	{
        		for (int j = 0; j < intItem.size(); j++) {
        			if(listItem.get(i).getIditem() == intItem.get(j) & (!listItem.get(i).isFav()))
        				listItem.get(i).setFav(true);
        		}
        	}
        }
        
        return listItem;
    }

	public static ArrayList<Integer> FavItem() throws SQLException {
		ArrayList<Integer> intItem = new ArrayList<>();
		try {
	        String sql = "SELECT item_PK FROM projectdb.favorite_item where item_fav_by_user = "+ LogInUserData.getUserID() +";";
	        Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
		              + "user=john&password=pass1234");
			
			Statement statement =  (Statement) connect.createStatement();
			
	        ResultSet resultSet = statement.executeQuery(sql);
	        
	        while(resultSet.next()) {
	        	intItem.add(resultSet.getInt("item_PK"));
	        }
	        statement.close();
		} catch(Exception e) {
				System.out.println(e);
			}
		return intItem;

	}

	public static boolean updateFavItem(String[] itemID, String userID) {
		boolean result = false;
		itemID = itemID[0].split(",");
		try {
			System.out.println("This is in the updateFavItem");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
			preparedStatement = connect.prepareStatement("DELETE FROM `projectdb`.`favorite_item` WHERE item_fav_by_user = ?");
			preparedStatement.setString(1,LogInUserData.getUserID());
			preparedStatement.executeUpdate();
			if(itemID != null && itemID.length != 0) {
				for (int i = 0; i < itemID.length; i++) {
					preparedStatement = connect.prepareStatement("INSERT INTO `projectdb`.`favorite_item`(`item_fav_by_user`,\r\n" + 
							"`item_PK`) values (?,?)");	
					preparedStatement.setString(1,LogInUserData.getUserID());
					preparedStatement.setInt(2, Integer.parseInt(itemID[i]));
					preparedStatement.executeUpdate();
				}
			}
			System.out.println("Fav User has been Updated");
			result = true;
		}catch (Exception e) {
			System.out.println(e);
		}
		return result;

	}

}
