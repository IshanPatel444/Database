import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class part5_DB {
	private static Connection connect = null;
	
	public static List<String> part5(String x, String y) {
		List<String> list=new ArrayList<String>();
		List<String> listX=new ArrayList<String>();
		List<String> listY=new ArrayList<String>();
		try {
			String quryX = "SELECT user_id FROM projectdb.favorite_seller where user_favorite = \"" + x + "\";";
			String quryY = "SELECT user_id FROM projectdb.favorite_seller where user_favorite = \"" + y + "\";";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statementX =  (Statement) connect.createStatement();
        	ResultSet resultX = statementX.executeQuery(quryX);
        	
        	Statement statementY =  (Statement) connect.createStatement();
        	ResultSet resultY = statementY.executeQuery(quryY);
        	
        	while(resultY.next())
        		listY.add(resultY.getString("user_id"));

        	while(resultX.next())
        		listX.add(resultX.getString("user_id"));

		}catch (Exception e) {
			System.out.println(e);
		}
		
		listX.retainAll(listY); 
		
		return listX;
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
