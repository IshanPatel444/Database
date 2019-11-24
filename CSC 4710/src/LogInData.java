import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class LogInData {
	private static Connection connect = null;
	private static PreparedStatement preparedStatement = null;
	
	public static boolean LogInDB(String user_id, String password) {
		boolean found = false;
		String userID;
		String fName;
		String lName;
		String email;
		String gender;
		int age;
		
		try {
			Date date = new Date();
			date = new java.sql.Date(date.getTime()); 
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
		              + "user=john&password=pass1234");
			
			String query = "select UserID, FNAME, LNAME, Email, gender, age from Users where UserID = '"+ user_id +"' and PASS = '"+ password +"'";
			
			preparedStatement = connect.prepareStatement(query);
			ResultSet result = preparedStatement.executeQuery(query);
						
			int count = 0;
			while(result.next()) {
				userID = result.getString("UserID");
				fName = result.getString("FNAME");
				lName = result.getString("LNAME");
				email = result.getString("Email");
				gender =result.getString("gender");
				age = result.getInt("age");
				LogInUserData.setLoginUserData(userID, fName, lName, email, gender, age);

				count++;
			}
			if(count == 1) {
							System.out.println("Login successful!");				
				found = true;
			}
			else {
				System.out.println("Wrong input");
				found = false;
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return found;
	}
}