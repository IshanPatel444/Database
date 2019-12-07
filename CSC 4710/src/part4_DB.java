import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class part4_DB {
	private static Connection connect = null;
	
	public static List<String> part4() {
		List<String> list=new ArrayList<String>();
		try {
			String qury = "SELECT user_id, COUNT(`user_id`) AS value_occurrence FROM projectdb.item GROUP BY user_id ORDER BY value_occurrence DESC;";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet result = statement.executeQuery(qury);
        	int x = 0;
        	
        	while(result.next()) {
        		if (x <= result.getInt("value_occurrence")) {
        			x = result.getInt("value_occurrence");
            		list.add(result.getString("user_id"));

        		}
        	}
        	statement.close();
        	System.out.println(list);
		}catch (Exception e) {
			System.out.println(e);
		}
		return list;
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
