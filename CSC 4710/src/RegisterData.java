

import java.sql.DriverManager;
import java.sql.Connection;
//import java.sql.Statement;
import java.sql.PreparedStatement;

public class RegisterData {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	public static void RegisterDB(String UserID, String PASS, String first_name , String last_name, 
			String email, int age, String gender) {
		try {
			System.out.println("This is in the registerData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
			preparedStatement = connect.prepareStatement("insert into Users(UserID," + 
					"PASS," + 
					"FNAME," + 
					"LNAME," + 
					"Email," + 
					"gender," + 
					"age) values(?,?,?,?,?,?,?)");
			preparedStatement.setString(1, UserID);
			preparedStatement.setString(2, PASS);
			preparedStatement.setString(3, first_name);
			preparedStatement.setString(4, last_name);
			preparedStatement.setString(5, email);
			preparedStatement.setString(6, gender);
			preparedStatement.setInt(7, age);
			
			preparedStatement.executeUpdate();
			System.out.println("data has been pushed in registerData.java");
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
