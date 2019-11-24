import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FavSellerData {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	
	public static ArrayList<User> FavSellerList(String UserID) {
		ArrayList<User> listFavUser = new ArrayList<User>();
		try {
			String qury = "SELECT user_id FROM projectdb.favorite_seller where user_favorite =" + UserID + ";";
			
			System.out.println("This is in the registerData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();

        	ResultSet result = statement.executeQuery(qury);
        	
			String user_id = "";

        	while(result.next()) {
        		user_id = result.getString("user_id");
        		Statement statement1 =  (Statement) connect.createStatement();
        		String stmt = "SELECT FNAME, LNAME, Email, gender, age FROM projectdb.users where UserID = " + user_id + ";";
        		
        		ResultSet set = statement1.executeQuery(stmt);
 
        		String FNAME = "";
        		String LNAME = "";
        		String Email = "";
        		String gender = "";
        		int age = 0;
        		
        		while(set.next()) {
        			FNAME = set.getString("FNAME");
        			LNAME = set.getString("LNAME");
        			Email = set.getString("Email");
        			gender = set.getString("gender");
        			age = set.getInt("age");
        			
        		}
        		
        		statement1.close();
        		User user = new User(user_id,FNAME, LNAME, Email, gender, age);
        		listFavUser.add(user);
        	}
        	statement.close();
			System.out.println("Get list of Fav User");
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return listFavUser;
	}

	public static ArrayList<User> SellerList() {
		ArrayList<User> listUser = new ArrayList<User>();
		try {
			String qury = "SELECT UserID, FNAME, LNAME, Email, gender, age FROM projectdb.users;";
			
			System.out.println("This is in the registerData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();

        	ResultSet result = statement.executeQuery(qury);
        	
    		String UserID = "";
    		String FNAME = "";
    		String LNAME = "";
    		String Email = "";
    		String gender = "";
    		int age = 0;

        	while(result.next()) {        		
        			FNAME = result.getString("FNAME");
        			LNAME = result.getString("LNAME");
        			Email = result.getString("Email");
        			gender = result.getString("gender");
        			age = result.getInt("age");
        			UserID = result.getString("UserID");
            		User user = new User(UserID,FNAME, LNAME, Email, gender, age);
            		listUser.add(user);
        		}
			System.out.println("Get list of User");
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return listUser;
	}

	public static boolean updateFavList(String[] userID, String user) {
		boolean result = false;
		userID = userID[0].split(",");
		try {
			System.out.println("This is in the FavSellerData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
			
			preparedStatement = connect.prepareStatement("DELETE FROM `projectdb`.`favorite_seller` WHERE user_favorite = ?");
			preparedStatement.setString(1,LogInUserData.getUserID());
			preparedStatement.executeUpdate();
			
			if(userID != null && userID.length != 0) {
				for (int i = 0; i < userID.length; i++) {
					preparedStatement = connect.prepareStatement("INSERT INTO `projectdb`.`favorite_seller`(`user_favorite`,`user_id`) values (?,?)");	
					preparedStatement.setString(1,LogInUserData.getUserID());
					preparedStatement.setString(2,userID[i]);
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
