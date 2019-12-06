import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class part8_DB {
	private static Connection connect = null;
	
	public static List<String> part8() {
		List<String> list=new ArrayList<String>();
		try {
			String qury1 = "SELECT distinct(user_id) FROM projectdb.review_item where review_rating = \"poor\";";
					
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement1 =  (Statement) connect.createStatement();
        	ResultSet result1 = statement1.executeQuery(qury1);

        	while(result1.next()) {
        		qury1 = "SELECT item_id,user_id FROM projectdb.review_item where user_id = "+ result1.getString("user_id") +" and review_rating != \"poor\";";
            	Statement statement =  (Statement) connect.createStatement();
            	ResultSet result = statement.executeQuery(qury1);
            	if (!result.next()) {
            		list.add(result1.getString("user_id"));
            	}
            	statement.close();
        	}
        	statement1.close();

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
