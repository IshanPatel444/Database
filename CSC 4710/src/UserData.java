import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserData {
	private static Connection connect = null;
	
	public static User UserDB(String UserID) {
		User user = new User();
		
		try {
			String qury = "SELECT FNAME, LNAME, Email, gender, age FROM projectdb.users where UserID = '" + UserID + "';";
			
			System.out.println("This is in the registerData.java");
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();

        	ResultSet result = statement.executeQuery(qury);
        	
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
        		}        		
        	User temp = new User(UserID,FNAME, LNAME,Email,gender,age);
        	user = temp;
			System.out.println("Get list of User");
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return user;
	}

}