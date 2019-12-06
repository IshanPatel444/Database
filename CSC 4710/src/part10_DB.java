import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class part10_DB {
	private static Connection connect = null;
	
	public static List<String> part10() {
		List<String> list=new ArrayList<String>();
		List<String> list1=new ArrayList<String>();
		try {
			String qury = "SELECT distinct(iditem) FROM projectdb.item where user_id in (SELECT distinct(user_id) FROM projectdb.review_item where review_rating = \"Excellent\");";

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet result = statement.executeQuery(qury);

        	while(result.next()) {
        		qury = "SELECT user_id FROM projectdb.review_item where item_id = " + result.getString("iditem") + " and review_rating = \"Excellent\";";
        		System.out.println(qury);
        	}
        	statement.close();
        	
		}catch (Exception e) {
			System.out.println(e);
		}
		
		for (int i = 0; i < list1.size(); i++) {
			list.remove(list1.get(i));
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
