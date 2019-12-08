import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class part11_DB {
	private static Connection connect = null;
	
	public static List<String> part10() {
		List<String> user_id = new ArrayList<String>();
		List<String> item_owner_id = new ArrayList<String>();
		try {
			String qury = "SELECT distinct item_id,user_id,item_owner_id FROM review_item  WHERE review_rating =\"Excellent\";";

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet result = statement.executeQuery(qury);

        	while(result.next()) {
        		user_id.add(result.getString("user_id"));
        		item_owner_id.add(result.getString("item_owner_id"));
        	}
        	statement.close();
        	
		}catch (Exception e) {
			System.out.println(e);
		}				
		
		List<String> finalUser = new ArrayList<String>();
		for (int i = 0; i<user_id.size(); i++)
		{
			for(int j = 0; j< item_owner_id.size(); j++)
			{
				if(item_owner_id.contains(user_id.get(i)))
				{
					if(!(finalUser.contains(user_id.get(i))))
						finalUser.add(user_id.get(i));
				}
			}
		}
		
		Stream.of(finalUser.toString())
		.forEach(System.out::println);

		if(finalUser.size()%2!=0)
			finalUser.clear();
		return finalUser;
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
