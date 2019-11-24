import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class AddItemCategoryData {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	
	public static void AddItemCategoryDB(String title, String[] categories) {
		Date date = new Date();
		date = new java.sql.Date(date.getTime()); 

		// TODO Auto-generated method stub
				try {
					System.out.println("This is in the AddItemCategoryData.java");
					Class.forName("com.mysql.cj.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
			                + "user=john&password=pass1234");

					String query = "SELECT iditem from item where title = "
							+ "\""
							+ title
							+ "\" and user_id = "
							+ LogInUserData.getUserID();
					preparedStatement = connect.prepareStatement(query);
					ResultSet result = preparedStatement.executeQuery(query);
					
					while(result.next()) {
						int itemID = result.getInt("iditem");
						if (categories != null && categories.length != 0) {
							for (int i = 0; i < categories.length; i++) {
								preparedStatement = connect.prepareStatement("insert into projectdb.category_item (category_description, item_ID) values (?,?)");
								preparedStatement.setString(1,categories[i]);
								preparedStatement.setInt(2,itemID);
								preparedStatement.executeUpdate();
							}
						}
					}

					
					System.out.println("data has been pushed in category_item");
					
				}catch (Exception e) {
					System.out.println(e);
				}
			}
}
