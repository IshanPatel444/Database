import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

public class AddItemData {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;

	public static Boolean AddItemDB(String title, String description, Double price) {
		String UserID = LogInUserData.getUserID();
		Date date = new Date();
		date = new java.sql.Date(date.getTime()); 

		// TODO Auto-generated method stub
		try {
			System.out.println("This is in the AddItemData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
					+ "user=john&password=pass1234");

			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id ) values(?,?,?,?,?)");
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, description);
			preparedStatement.setDate(3, (java.sql.Date) date );
			preparedStatement.setDouble(4, price);
			preparedStatement.setString(5, UserID);

			preparedStatement.executeUpdate();
			System.out.println("data has been pushed in AddItemData.java");
			return true;

		}catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
