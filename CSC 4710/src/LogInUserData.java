
public class LogInUserData {
	private static String theUserID;
	private static String theFName;
	private static String theLName;
	private static String theEmail;
	private static String theGender;
	private static int theAge;
	
	public static void setLoginUserData(String userID, String FName, String LName, String Email, String Gender, int Age) {
		theUserID = userID;
		theFName = FName;
		theLName = LName;
		theEmail = Email;
		theGender = Gender;
		theAge = Age;
	}
	
	public static String getUserID() {
		return theUserID;
	}

	public static String getTheFName() {
		return theFName;
	}

	public static String getTheLName() {
		return theLName;
	}

	public static String getTheEmail() {
		return theEmail;
	}

	public static String getTheGender() {
		return theGender;
	}

	public static int getTheAge() {
		return theAge;
	}

}
