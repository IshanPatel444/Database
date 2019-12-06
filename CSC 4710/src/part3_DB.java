import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class part3_DB {
	private static Connection connect = null;
	
	public static List<Item> part3(String Search) {
		ArrayList<Item> listItem = new ArrayList<Item>();
		try {
			String qury = "SELECT distinct(item_id) FROM projectdb.review_item where item_owner_id = "+ Search + " and (review_rating = \"Excellent\" or review_rating = \"good\");";
			System.out.print(qury);
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet resultSet = statement.executeQuery(qury);
        	
        	while(resultSet.next()) {
                int item_ID = resultSet.getInt("item_ID");

                qury = "SELECT iditem, description, price, post_date,user_id, title FROM projectdb.item where iditem = " + item_ID + ";";
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
        	statement.close();
		}catch (Exception e) {
			System.out.println(e);
		}
		return listItem;
	}

	public static List<User> userList(List<String> list){
		List<User> userlist=new ArrayList<User>();
		try {
			if(list != null)
			{
				for (int i = 0; i< list.size(); i++) {

					System.out.println(list.get(i));
					String qury = "SELECT UserID, FNAME, LNAME FROM projectdb.users where UserID = " + list.get(i) + ";";

					Class.forName("com.mysql.cj.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
							+ "user=john&password=pass1234");

					Statement statement =  (Statement) connect.createStatement();
					ResultSet result = statement.executeQuery(qury);

					while(result.next()) {
						System.out.print("in Try");
						
						userlist.add(new User(list.get(i), result.getString("FNAME"), result.getString("LNAME")));
					}
					statement.close();
					System.out.println(list);
				}
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		return userlist;
		
	}

}
