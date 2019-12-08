import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class Tuple<A, B> {

    public final A a;
    public final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
    
    public A getA() {
    	return this.a;
    }

    @Override
	public String toString() {
    	String s = String.format("("+this.a+", "+ this.b+")");
		return s;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        if (!a.equals(tuple.a)) return false;
        return b.equals(tuple.b);
    }
}

public class part10_DB {
	private static Connection connect = null;
	
	public static void part10() {
		List<Tuple<String, Integer> > itemWithExcellent =new ArrayList<Tuple<String, Integer>>();
		try {
			String qury = "SELECT item_owner_id, count(item_owner_id) FROM projectdb.review_item WHERE review_rating = 'Excellent' group by item_id;";

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet result = statement.executeQuery(qury);

        	while(result.next()) {
        		itemWithExcellent.add(new Tuple<>(result.getString(1), result.getInt(2)));
        	}
        	statement.close();
        	
		}catch (Exception e) {
			System.out.println(e);
		}		
		
		Stream.of(itemWithExcellent.toString())
		.forEach(System.out::println);

		List<Tuple<String, Integer> > userItem =new ArrayList<Tuple<String, Integer>>();
		try {
			String qury = "SELECT user_id, count(user_id) FROM projectdb.item GROUP BY user_id;";

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
	                + "user=john&password=pass1234");
			
        	Statement statement =  (Statement) connect.createStatement();
        	ResultSet result = statement.executeQuery(qury);

        	while(result.next()) {
        		userItem.add(new Tuple<>(result.getString("user_id"), result.getInt(2)));
        	}
        	statement.close();
        	
		}catch (Exception e) {
			System.out.println(e);
		}		
		
		Stream.of(userItem.toString())
		.forEach(System.out::println);
		
		List<String> finalUserList = new ArrayList<String>();
		for (int i = 0; i<itemWithExcellent.size(); i++) {
			for(int j = 0; j< userItem.size(); j++) {
				if(userItem.get(j).equals(itemWithExcellent.get(i)))
					finalUserList.add(itemWithExcellent.get(i).getA());
			}
		}

		Stream.of(finalUserList.toString())
		.forEach(System.out::println);
		
		
		List<Tuple<String, List<String>> > ListItem =new ArrayList<Tuple<String, List<String>>>();
		for (int i = 0; i<finalUserList.size(); i++)
		{	
			List<String> userList = new ArrayList<String>();
			try {
				String qury = "SELECT user_id FROM projectdb.review_item where item_owner_id = \"" + finalUserList.get(i) +"\" and review_rating = \"Excellent\";";

				Class.forName("com.mysql.cj.jdbc.Driver");
				connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
						+ "user=john&password=pass1234");

				Statement statement =  (Statement) connect.createStatement();
				ResultSet result = statement.executeQuery(qury);

				while(result.next()) {
					userList.add(result.getString("user_id"));
				}
				statement.close();

			}catch (Exception e) {
				System.out.println(e);
			}
			ListItem.add(new Tuple<>(finalUserList.get(i),userList));
		}
		
		Stream.of(ListItem.toString())
		.forEach(System.out::println);

	}

	public List<User> userList(List<String> list){
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
