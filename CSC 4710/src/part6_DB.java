import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class part6_DB {
	private static Connection connect = null;

	public static List<String> part6() {
		List<String> list=new ArrayList<String>();
		List<String> list1=new ArrayList<String>();
		try {
			String qury = "SELECT item_owner_id FROM projectdb.review_item where review_rating = \"Excellent\" GROUP BY user_id HAVING COUNT(user_id) >= 3 ;";
			String qury1 = "select distinct(item_owner_id) FROM projectdb.review_item;\r\n";

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
					+ "user=john&password=pass1234");

			Statement statement =  (Statement) connect.createStatement();
			ResultSet result = statement.executeQuery(qury);

			Statement statement1 =  (Statement) connect.createStatement();
			ResultSet result1 = statement1.executeQuery(qury1);

			while(result.next()) {
				list.add(result.getString("item_owner_id"));
			}

			while(result1.next()) {
				list1.add(result1.getString("item_owner_id"));
			}

			statement.close();
			statement1.close();

			System.out.println(list);
		}catch (Exception e) {
			System.out.println(e);
		}		

		List<String> union = new ArrayList<String>(list1);
		union.addAll(list);
		List<String> intersection = new ArrayList<String>(list1);
		intersection.retainAll(list);
		union.removeAll(intersection);

		return union;
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
