
public class User {
	private String UserID;
	private String FNAME;
	private String LNAME;
	private String Email;
	private String gender;
	private int age;
	private boolean isChecked;	
	
	
	public User(String userID, String fNAME, String lNAME) {
		super();
		UserID = userID;
		FNAME = fNAME;
		LNAME = lNAME;
	}

	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", FNAME=" + FNAME + ", LNAME=" + LNAME + ", isChecked=" + isChecked + "]";
	}

	public User(String userID, String fNAME, String lNAME, String email, String gender, int age) {
		super();
		UserID = userID;
		FNAME = fNAME;
		LNAME = lNAME;
		Email = email;
		this.gender = gender;
		this.age = age;
	}
	
	public User() {
		UserID = "" ;
		FNAME = "" ;
		LNAME = "" ;
		Email = "" ;
		gender = "" ;
		age =0;
		isChecked = false ;
	}

	public boolean isSame(String UserID) {
		if(this.getUserID().equals(UserID)) {
			return true;
		}
		return false;
	}


	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	public String getUserID() {
		return UserID;
	}


	public void setUserID(String userID) {
		UserID = userID;
	}


	public String getFNAME() {
		return FNAME;
	}


	public void setFNAME(String fNAME) {
		FNAME = fNAME;
	}


	public String getLNAME() {
		return LNAME;
	}


	public void setLNAME(String lNAME) {
		LNAME = lNAME;
	}
	
	public String getFullName() {
		return this.getFNAME()  + " " + this.getLNAME();
	}
}


