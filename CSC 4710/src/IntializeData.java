import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;

/*
 * Servlet implementation class DatabaseServerlet
 */

public class IntializeData {
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null; 
	private static Date date = new Date();

	public static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static LocalDate createRandomDate(int startYear, int endYear) {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(startYear, endYear);
        return LocalDate.of(year, month, day);
    }
	
	public static void CreateTriggerForItem(String userID) {
		try {
			date = new java.sql.Date(date.getTime()); 
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
			+ "user=john&password=pass1234");
			statement = connect.createStatement();
			
			statement.executeUpdate("DROP TRIGGER IF EXISTS `projectdb`.`item_BEFORE_INSERT`");
			
			String trigger = "CREATE\r\n" + 
				"TRIGGER `projectdb`.`item_BEFORE_INSERT`\r\n" + 
				"BEFORE INSERT ON `projectdb`.`item`\r\n" + 
				"FOR EACH ROW\r\n" + 
				"BEGIN\r\n" + 
				"IF \r\n" + 
				"(select count(iditem) from projectdb.item where post_date = \""
				 + (java.sql.Date) date
				 + "\" and user_id="
				 + userID
				 + ") > 4\r\n" + 
				"          THEN\r\n" + 
				"               SIGNAL SQLSTATE '45000'\r\n" + 
				"                    SET MESSAGE_TEXT = 'Cannot add any more item';\r\n" + 
				"          END IF;\r\n" + 
				"END";
			
			statement.executeUpdate(trigger);
			
			System.out.println("Trigger Created Successfully for Item");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}

	public static void CreateTriggerForReview(String userID) {
		try {
			date = new java.sql.Date(date.getTime()); 
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
			+ "user=john&password=pass1234");
			statement = connect.createStatement();
			
			statement.executeUpdate("DROP TRIGGER IF EXISTS `projectdb`.`review_item_BEFORE_INSERT`");
			
			
			String trigger = "CREATE TRIGGER `review_item_BEFORE_INSERT` BEFORE INSERT ON `review_item` FOR EACH ROW BEGIN\r\n" + 
					"IF (select count(id_review_item) from projectdb.review_item where post_date = \""
					 + (java.sql.Date) date
					 + "\" and user_id="
					 + userID
					 + ") > 4\r\n" + 
					"          THEN\r\n" + 
					"               SIGNAL SQLSTATE '45000'\r\n" + 
					"                    SET MESSAGE_TEXT = 'Cannot add any more Review';\r\n" + 
					"          END IF;\r\n" + 
					"END";
			
			statement.executeUpdate(trigger);
			
			System.out.println("Trigger Created Successfully for Review");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}
	
	public static void CreateFunctionIsReviewValid() {
		try {
			date = new java.sql.Date(date.getTime()); 

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
					+ "user=john&password=pass1234");
			statement = connect.createStatement();

			statement.executeUpdate("DROP function IF EXISTS `is_review_valid`");


			String function = "CREATE FUNCTION `is_review_valid`(item_id int) RETURNS varchar(30)\r\n" + 
					"    READS SQL DATA\r\n" + 
					"    DETERMINISTIC\r\n" + 
					"BEGIN\r\n" + 
					"	DECLARE userID VARCHAR(20);\r\n" + 
					"	SELECT user_id into userID FROM projectdb.item where iditem = item_id;\r\n" + 
					"\r\n" + 
					"RETURN userID;\r\n" + 
					"END";

			statement.executeUpdate(function);

			System.out.println("Function Created Successfully for Review");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}

	public static void CreateProcedureAddReview() {
		try {
			date = new java.sql.Date(date.getTime()); 

			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
					+ "user=john&password=pass1234");
			statement = connect.createStatement();

			statement.executeUpdate("DROP procedure IF EXISTS `add_review_SP`");


			String function = "CREATE PROCEDURE `add_review_SP`(in post_date date, \r\n" + 
					"								in user_id varchar(30),\r\n" + 
					"                                in item_id int,\r\n" + 
					"                                in review_description varchar(255),\r\n" + 
					"                                in review_rating varchar (10),\r\n" + 
					"                                out result int)\r\n" + 
					"BEGIN\r\n" + 
					"	if (projectdb.is_review_valid(item_id) <> user_id) then\r\n" + 
					"		INSERT INTO `projectdb`.`review_item`\r\n" + 
					"				( `post_date`, `user_id`, `item_id`, `review_description`, `review_rating`)\r\n" + 
					"			VALUES\r\n" + 
					"				(post_date, user_id, item_id, review_description, review_rating);\r\n" + 
					"		set result = 1;\r\n" + 
					"	else \r\n" + 
					"		set result = 0;\r\n" + 
					"	end if;\r\n" + 
					"END";

			statement.executeUpdate(function);

			System.out.println("Procedure Created Successfully for Item Review");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}
	
	public static void AddDataToReview() {
		try {
			preparedStatement = connect.prepareStatement("INSERT INTO review (review_rating) values(?)");
			preparedStatement.setString(1,"Excellent");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("INSERT INTO review (review_rating) values(?)");
			preparedStatement.setString(1,"Good");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("INSERT INTO review (review_rating) values(?)");
			preparedStatement.setString(1,"Fair");
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("INSERT INTO review (review_rating) values(?)");
			preparedStatement.setString(1,"Poor");
			preparedStatement.executeUpdate();
			
			System.out.println("Data Inserted in Review Successfully");

		} catch (Exception e) {
			System.out.print(e);
		}	
		
	}
	
	public static void AddItem()
	{
		try {
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Sony Head-Phone");
			preparedStatement.setString(2, "Brand New Head-Phone");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 99);
			preparedStatement.setString(5, "1");
			preparedStatement.setInt(6, 1);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "LG LED 13.3 inch. TV");
			preparedStatement.setString(2, "Used TV");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
		    preparedStatement.setDouble(4, 499);
			preparedStatement.setString(5, "2");
			preparedStatement.setInt(6, 2);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, " beats Power-bank");
			preparedStatement.setString(2, "Brand New Power-Bank in good price.");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );

			preparedStatement.setDouble(4, 25);
			preparedStatement.setString(5, "2");
			preparedStatement.setInt(6, 3);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");			
			preparedStatement.setString(1, "Asus Laptop 16inch.");
			preparedStatement.setString(2, "Brand New Gamiong Laptop.");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );

			preparedStatement.setDouble(4, 999);
			preparedStatement.setString(5, "1");
			preparedStatement.setInt(6, 4);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "HP Spectre");
			preparedStatement.setString(2, "Used Spectre laptop 13.3 inch.");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );

			preparedStatement.setDouble(4, 699);
			preparedStatement.setString(5, "3");
			preparedStatement.setInt(6, 5);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Beats");
			preparedStatement.setString(2, "pink Head_Phone");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 129);
			preparedStatement.setString(5, "2");
			preparedStatement.setInt(6, 6);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "L.G");
			preparedStatement.setString(2, "TV");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 234);
			preparedStatement.setString(5, "3");
			preparedStatement.setInt(6, 7);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Sony");
			preparedStatement.setString(2, "PlayBox");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 56);
			preparedStatement.setString(5, "4");
			preparedStatement.setInt(6, 8);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "HP");
			preparedStatement.setString(2, "New Letest Spectre 15.6' inch.");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 899);
			preparedStatement.setString(5, "5");
			preparedStatement.setInt(6, 9);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "JBL");
			preparedStatement.setString(2, "Bluetooth Speeker");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 99);
			preparedStatement.setString(5, "6");
			preparedStatement.setInt(6, 10);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "J");
			preparedStatement.setString(2, "Car Speeker");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 499);
			preparedStatement.setString(5, "9");
			preparedStatement.setInt(6, 11);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Google");
			preparedStatement.setString(2, "Alexa");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );

			preparedStatement.setDouble(4, 299);
			preparedStatement.setString(5, "7");
			preparedStatement.setInt(6, 12);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Apple");
			preparedStatement.setString(2, "Letest I-Phone 7");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 699);
			preparedStatement.setString(5, "8");
			preparedStatement.setInt(6, 13);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Samsung");
			preparedStatement.setString(2, "Used Note 8");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 499);
			preparedStatement.setString(5, "9");
			preparedStatement.setInt(6, 14);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Motorola");
			preparedStatement.setString(2, "Used Moto 11");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 199);
			preparedStatement.setString(5, "10");
			preparedStatement.setInt(6, 15);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Google");
			preparedStatement.setString(2, "new Phone");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 259);
			preparedStatement.setString(5, "5");
			preparedStatement.setInt(6, 16);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Samsung");
			preparedStatement.setString(2, "New Note-9");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 799);
			preparedStatement.setString(5, "7");
			preparedStatement.setInt(6, 17);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Sony");
			preparedStatement.setString(2, "used Head-Phone");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 29);
			preparedStatement.setString(5, "6");
			preparedStatement.setInt(6, 18);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into item(title," + 
					"description," + 
					"post_date," + 
					"price," + 
					"user_id,iditem ) values(?,?,?,?,?,?)");
			preparedStatement.setString(1, "Vivo");
			preparedStatement.setString(2, "New Vivo 9 pro.");
			preparedStatement.setDate(3, java.sql.Date.valueOf(createRandomDate(2000, 2018)) );
			preparedStatement.setDouble(4, 157);
			preparedStatement.setString(5, "8");
			preparedStatement.setInt(6, 19);
			preparedStatement.executeUpdate();
			
			
			
			
			
			
			
			
			
			
			
			// Category Item
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 1);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "desktop");
			preparedStatement.setInt(2, 2);
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "consoles");
			preparedStatement.setInt(2, 3);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "phone");
			preparedStatement.setInt(2, 4);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 5);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "phone");
			preparedStatement.setInt(2, 6);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 6);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "Consoles");
			preparedStatement.setInt(2, 18);
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 15);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 13);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "phone");
			preparedStatement.setInt(2, 7);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 9);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "consoles");
			preparedStatement.setInt(2, 10);
			preparedStatement.executeUpdate();
			System.out.println("Data Inserted in Category Successfully");
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "laptop");
			preparedStatement.setInt(2, 13);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "desktop");
			preparedStatement.setInt(2, 5);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 3);
			preparedStatement.executeUpdate();
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 8);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 14);
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 16);
		    preparedStatement.executeUpdate();
		    preparedStatement = connect.prepareStatement("insert into category_item(category_description, item_ID)" + 
					"values(?,?)");
			preparedStatement.setString(1, "electronic");
			preparedStatement.setInt(2, 18);
			preparedStatement.executeUpdate();
		}
		
		catch (Exception e) {
			System.out.print(e);
		}		
	}
	
	public static void AddDataToUser() {
		try {
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"1");
			preparedStatement.setString(2,"11");
			preparedStatement.setString(3,"Ishan");
			preparedStatement.setString(4,"Patel");
			preparedStatement.setString(5,"Patelishan@gmail.com");
			preparedStatement.setInt(6, 22);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"2");
			preparedStatement.setString(2,"22");
			preparedStatement.setString(3,"Siddh");
			preparedStatement.setString(4,"vyas");
			preparedStatement.setString(5,"vyasSiddh@gmail.com");
			preparedStatement.setInt(6, 23);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"3");
			preparedStatement.setString(2,"33");
			preparedStatement.setString(3,"Akruti");
			preparedStatement.setString(4,"jani");
			preparedStatement.setString(5,"janiAkruti@gmail.com");
			preparedStatement.setInt(6, 21);
			preparedStatement.setString(7,"f");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"4");
			preparedStatement.setString(2,"44");
			preparedStatement.setString(3,"Jigar");
			preparedStatement.setString(4,"thakur");
			preparedStatement.setString(5,"thakurJigar@gmail.com");
			preparedStatement.setInt(6, 24);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"5");
			preparedStatement.setString(2,"55");
			preparedStatement.setString(3,"Kishan");
			preparedStatement.setString(4,"rabari");
			preparedStatement.setString(5,"rabariKishan@gmail.com");
			preparedStatement.setInt(6, 23);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"6");
			preparedStatement.setString(2,"66");
			preparedStatement.setString(3,"Nirav");
			preparedStatement.setString(4,"bharvad");
			preparedStatement.setString(5,"bharvadNirav@gmail.com");
			preparedStatement.setInt(6, 25);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"7");
			preparedStatement.setString(2,"77");
			preparedStatement.setString(3,"Nisharg");
			preparedStatement.setString(4,"pandit");
			preparedStatement.setString(5,"PanditNisharg@gmail.com");
			preparedStatement.setInt(6, 24);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"8");
			preparedStatement.setString(2,"88");
			preparedStatement.setString(3,"Jahnvi");
			preparedStatement.setString(4,"pandya");
			preparedStatement.setString(5,"PandyaJahnvi@gmail.com");
			preparedStatement.setInt(6, 20);
			preparedStatement.setString(7,"f");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"9");
			preparedStatement.setString(2,"99");
			preparedStatement.setString(3,"Love");
			preparedStatement.setString(4,"darji");
			preparedStatement.setString(5,"darjiLove@gmail.com");
			preparedStatement.setInt(6, 26);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
			
			preparedStatement = connect.prepareStatement("insert into users(UserID, PASS, FNAME, LNAME, Email, age, gender) values(?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1,"10");
			preparedStatement.setString(2,"101");
			preparedStatement.setString(3,"Mehul");
			preparedStatement.setString(4,"purohit");
			preparedStatement.setString(5,"PurohitMehul@gmail.com");
			preparedStatement.setInt(6, 25);
			preparedStatement.setString(7,"m");
			preparedStatement.executeUpdate();
		
			System.out.println("Data Inserted in Users Successfully");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}
	
	public static void AddDataToCategory() {
		try {
			preparedStatement = connect.prepareStatement("insert into projectdb.category (category_description) values (?)");
			preparedStatement.setString(1,"phone");
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into projectdb.category (category_description) values (?)");
			preparedStatement.setString(1,"consoles");
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into projectdb.category (category_description) values (?)");
			preparedStatement.setString(1,"electronic");
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into projectdb.category (category_description) values (?)");
			preparedStatement.setString(1,"laptop");
			preparedStatement.executeUpdate();

			preparedStatement = connect.prepareStatement("insert into projectdb.category (category_description) values (?)");
			preparedStatement.setString(1,"desktop");
			preparedStatement.executeUpdate();

			System.out.println("Data Inserted in Category Successfully");
		} catch (Exception e) {
			System.out.print(e);
		}		
	}
	
	public static void IntializeDB() {
		try {
			date = new java.sql.Date(date.getTime()); 
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://:3306/projectdb?"
			+ "user=john&password=pass1234");
			
			System.out.println("Intialize database");
			
			statement = connect.createStatement();
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
			statement.executeUpdate("DROP TABLE IF EXISTS item");
			statement.executeUpdate("DROP TABLE IF EXISTS `projectdb`.`category`");
			statement.executeUpdate("DROP TABLE IF EXISTS `projectdb`.`category_item`");
			statement.executeUpdate("DROP TABLE IF EXISTS USERS");
			statement.executeUpdate("DROP TABLE IF EXISTS REVIEW");
			statement.executeUpdate("DROP TABLE IF EXISTS favorite_item");
			statement.executeUpdate("DROP TABLE IF EXISTS REVIEW_ITEM");
			statement.executeUpdate("DROP TABLE IF EXISTS favorite_seller");
			statement.executeUpdate("DROP procedure IF EXISTS add_review_SP");
			statement.executeUpdate("DROP function IF EXISTS is_review_valid");
			statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
						
			String Users = "CREATE TABLE IF NOT EXISTS `projectdb`.`users` (\r\n" + 
					"  `UserID` VARCHAR(30) NOT NULL,\r\n" + 
					"  `PASS` VARCHAR(30) NOT NULL,\r\n" + 
					"  `FNAME` VARCHAR(20) NOT NULL,\r\n" + 
					"  `LNAME` VARCHAR(20) NOT NULL,\r\n" + 
					"  `Email` VARCHAR(30) NOT NULL,\r\n" + 
					"  `gender` VARCHAR(20) NOT NULL,\r\n" + 
					"  `age` INT(20) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`UserID`));";
			
			statement.executeUpdate(Users);
			
			String favorite_seller = "CREATE TABLE IF NOT EXISTS `projectdb`.`favorite_seller` (\r\n" + 
					"  `user_favorite` VARCHAR(30) NOT NULL,\r\n" + 
					"  `user_id` VARCHAR(30) NOT NULL,\r\n" + 
					"  UNIQUE INDEX `userFavorite_userId` (`user_favorite` ASC, `user_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `user_id_fav_seller_idx` (`user_favorite` ASC) VISIBLE,\r\n" + 
					"  INDEX `user_id_seller_idx` (`user_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `user_id_fav_seller`\r\n" + 
					"    FOREIGN KEY (`user_favorite`)\r\n" + 
					"    REFERENCES `projectdb`.`users` (`UserID`),\r\n" + 
					"  CONSTRAINT `user_id_seller`\r\n" + 
					"    FOREIGN KEY (`user_id`)\r\n" + 
					"    REFERENCES `projectdb`.`users` (`UserID`));";
			
			statement.executeUpdate(favorite_seller);
							
			String item = "CREATE TABLE IF NOT EXISTS `projectdb`.`item` (\r\n" + 
					"  `iditem` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `title` VARCHAR(45) NOT NULL,\r\n" + 
					"  `description` VARCHAR(45) NOT NULL,\r\n" + 
					"  `post_date` DATE NOT NULL,\r\n" + 
					"  `price` DOUBLE NOT NULL,\r\n" + 
					"  `user_id` VARCHAR(45) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`iditem`),\r\n" + 
					"  UNIQUE INDEX `sqlUniqueConstraint` (`title` ASC, `user_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `userID` (`user_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `userID`\r\n" + 
					"    FOREIGN KEY (`user_id`)\r\n" + 
					"    REFERENCES `projectdb`.`users` (`UserID`));";
			
			statement.executeUpdate(item);
			
			String category = "CREATE TABLE IF NOT EXISTS `projectdb`.`category` (\r\n" + 
					"  `category_description` VARCHAR(45) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`category_description`));";
			
			statement.executeUpdate(category);
			
			String category_item = "CREATE TABLE IF NOT EXISTS `projectdb`.`category_item` (\r\n" + 
					"  `category_description` VARCHAR(45) NOT NULL,\r\n" + 
					"  `item_ID` INT(11) NOT NULL,\r\n" + 
					"  INDEX `item_ID` (`item_ID` ASC) VISIBLE,\r\n" + 
					"  INDEX `category_description` (`category_description` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `category_description`\r\n" + 
					"    FOREIGN KEY (`category_description`)\r\n" + 
					"    REFERENCES `projectdb`.`category` (`category_description`),\r\n" + 
					"  CONSTRAINT `item_ID`\r\n" + 
					"    FOREIGN KEY (`item_ID`)\r\n" + 
					"    REFERENCES `projectdb`.`item` (`iditem`));";
	
			statement.executeUpdate(category_item);
			
			String review = "CREATE TABLE IF NOT EXISTS `projectdb`.`review` (\r\n" + 
					"  `review_rating` VARCHAR(30) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`review_rating`));";

			statement.executeUpdate(review);
			
			String review_item = "CREATE TABLE IF NOT EXISTS `projectdb`.`review_item` (\r\n" + 
					"  `id_review_item` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `post_date` DATE NOT NULL,\r\n" + 
					"  `user_id` VARCHAR(30) NOT NULL,\r\n" + 
					"  `item_id` INT(11) NOT NULL,\r\n" + 
					"  `review_description` VARCHAR(255) NOT NULL,\r\n" + 
					"  `review_rating` VARCHAR(30) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id_review_item`),\r\n" + 
					"  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `item_id_idx` (`item_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `review_rating_idx` (`review_rating` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `item_id_REVIEW_ITEM`\r\n" + 
					"    FOREIGN KEY (`item_id`)\r\n" + 
					"    REFERENCES `projectdb`.`item` (`iditem`),\r\n" + 
					"  CONSTRAINT `review_rating_REVIEW_ITEM`\r\n" + 
					"    FOREIGN KEY (`review_rating`)\r\n" + 
					"    REFERENCES `projectdb`.`review` (`review_rating`),\r\n" + 
					"  CONSTRAINT `user_id_REVIEW_ITEM`\r\n" + 
					"    FOREIGN KEY (`user_id`)\r\n" + 
					"    REFERENCES `projectdb`.`users` (`UserID`));";
			
			statement.executeUpdate(review_item);
			
			String Add_Item = "CREATE TABLE IF NOT EXISTS `projectdb`.`item` (\r\n" + 
					"  `iditem` INT(11) NOT NULL AUTO_INCREMENT,\r\n" +
					"  `title` VARCHAR(30) NOT NULL,\r\n" + 
					"  `description` VARCHAR(30) NOT NULL,\r\n" + 
					"  `post_date` DATE NOT NULL,\r\n" + 
					"  `user_id` VARCHAR(30) NOT NULL,\r\n"+ 
					"  `price` DOUBLE NOT NULL,\r\n" + 
					"  PRIMARY KEY (`iditem`));";
					
			statement.executeUpdate(Add_Item);
			
			String favorite_item = "CREATE TABLE IF NOT EXISTS `projectdb`.`favorite_item` (\r\n" + 
			"  `item_fav_by_user` VARCHAR(30) NOT NULL,\r\n" + 
			"  `item_PK` INT(11) NOT NULL,\r\n" + 
			"  INDEX `user_FI_FK_idx` (`item_fav_by_user` ASC) VISIBLE,\r\n" + 
			"  INDEX `item_PK_FK_idx` (`item_PK` ASC) VISIBLE,\r\n" + 
			"  CONSTRAINT `item_PK_FK`\r\n" + 
			"    FOREIGN KEY (`item_PK`)\r\n" + 
			"    REFERENCES `projectdb`.`item` (`iditem`),\r\n" + 
			"  CONSTRAINT `user_FI_FK`\r\n" + 
			"    FOREIGN KEY (`item_fav_by_user`)\r\n" + 
			"    REFERENCES `projectdb`.`users` (`UserID`))";
			
			statement.executeUpdate(favorite_item);

			AddDataToCategory();
			AddDataToUser();
			AddDataToReview();
			CreateFunctionIsReviewValid();
			CreateProcedureAddReview();
			AddItem();
			
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}