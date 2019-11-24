import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 * @author www.codejava.net
 */
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private int itemID;
	
    protected void check_connect() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://:3306/projectdb?"
  			          + "user=john&password=pass1234");
        }
    }
    
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }

 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        System.out.println(action);
        try 
        {
        	switch (action) {
        	case "/InitilizeDB":
        		initializeDatabase(request, response);
        		System.out.println("We are inside Initialize Data");
        		break;
        	case "/LogInDB":
        		Login(request, response);
        		System.out.println("We are inside log in");
        		break; 
        	case "/RegisterDB":
            	registerDatabase(request, response);
            	System.out.println("We are inside Register Data");
                break;
        	case "/AddItemDB":
        		addItemDatabase(request, response);
            	System.out.println("Item Added");
        		break;
        	case "/SearchInDB":
        		searchInDatabase(request, response);
            	System.out.println("Search Completed");
        		break; 
        	case "/UserItemInDB":
        		userItemInDatabase(request, response);
            	System.out.println("Get All Item by User");
        		break; 
        	case "/ItemReview":
        		itemReview(request, response);
            	System.out.println("Get All Item by User");
        		break; 
        	case "/AddItemReview":
        		addItemReview(request, response);
            	System.out.println("Add Review on Item by User");
        		break; 
        	case "/FavUser":
        		favSellerList(request, response);
            	System.out.println("Updated Fav List");
        		break;
        	}
        } catch (SQLException ex) {
        throw new ServletException(ex);
    }
}
    
    private void favSellerList(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	String result = "error";
    	String user_id = LogInUserData.getUserID();
		String ck_favUser[] = request.getParameterValues("hiddenField");
		
		if(ck_favUser != null && ck_favUser.length!=0) {
			if(FavSellerData.updateFavList(ck_favUser, user_id))
				result = "success";
		}
		
		
		List<User> AllUserID = FavSellerData.SellerList();
    	List<User> FavUserList = FavSellerData.FavSellerList(user_id);
    	
    	for (int i = 0; i < FavUserList.size(); i++) {
    		User tempUser = FavUserList.get(i);
    		for (int j = 0; j < AllUserID.size(); j++) {
    			if(AllUserID.get(j).isSame(tempUser.getUserID()) && (!AllUserID.get(j).isChecked())) {
    				AllUserID.get(j).setChecked(true);
    			}
    		}
    	}
    	
    	for (int i = 0; i < AllUserID.size(); i++) {
    		if(AllUserID.get(i).isSame(user_id))
    			AllUserID.remove(i);
    	}
    
    	request.setAttribute("AllUserID", AllUserID);
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("favSeller.jsp");       
		dispatcher.forward(request, response);
}
    
    private void addItemReview(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	String result = "error";
    	String rating = request.getParameter("rating");
    	String description = request.getParameter("description");
    	String user_id = LogInUserData.getUserID();

    	if (ReviewItem.addReview(user_id, itemID, description, rating))
    		result = "success";
    	
    	List<Review> listReview = ReviewItem.ItemReview(itemID);
    	request.setAttribute("listReview", listReview);
    	request.setAttribute("itemID", itemID);
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserReview.jsp");       
		dispatcher.forward(request, response);
}   
    
    private void itemReview(HttpServletRequest request, HttpServletResponse response)
    		throws SQLException, IOException, ServletException {
    	itemID = Integer.parseInt(request.getParameter("itemID"));
    	List<Review> listReview = ReviewItem.ItemReview(itemID);
    	request.setAttribute("listReview", listReview);
    	request.setAttribute("itemID", itemID);
		request.setAttribute("result", "success");
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserReview.jsp");       
		dispatcher.forward(request, response);
}

	private void userItemInDatabase(HttpServletRequest request, HttpServletResponse response) 
    	throws SQLException, IOException, ServletException {
		String user_id  = "";
		user_id = request.getParameter("sellerID");

		if	(user_id == "" || user_id == null)
			user_id = LogInUserData.getUserID();
		
		User user = UserData.UserDB(user_id);
		List<Item> listItem = UserItem.SearchUserItem(user_id);
		request.setAttribute("userName",user.getFullName());
		request.setAttribute("listItem", listItem);
		request.setAttribute("result", "success");
		RequestDispatcher dispatcher = request.getRequestDispatcher("itemByUser.jsp");       
		dispatcher.forward(request, response);
    }

	private void initializeDatabase(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	IntializeData.IntializeDB();
    	System.out.println("Data has been initialize !");
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
        response.getWriter().append("served at: ").append(request.getContextPath());
    }
    
    private void Login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String UserID = request.getParameter("UserID");
        String password = request.getParameter("password");

        if(LogInData.LogInDB(UserID, password)) {
        	IntializeData.CreateTriggerForItem(UserID);
        	IntializeData.CreateTriggerForReview(UserID);
			request.setAttribute("age", LogInUserData.getTheAge());
			request.setAttribute("email", LogInUserData.getTheEmail());
			request.setAttribute("first_name", LogInUserData.getTheFName());
			request.setAttribute("last_name", LogInUserData.getTheLName());
			request.setAttribute("gender", LogInUserData.getTheGender());
			request.setAttribute("result", "success");			
        	RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
        	dispatcher.forward(request, response);
        } else {
			request.setAttribute("result", "error");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");       
            dispatcher.forward(request, response);
        }
    }
  
    protected void registerDatabase(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
		RegisterData RegisterDB = new RegisterData();
		System.out.print("you have been linked.");
		String user_id = request.getParameter("UserID");
		String password = request.getParameter("password");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		RegisterData.RegisterDB(user_id, password, first_name, last_name, email, age, gender);
		request.setAttribute("RegisterDB", RegisterDB);
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    
    protected void addItemDatabase(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException, SQLException {
//		AddItemData AddItemDB = new AddItemData();
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String price = request.getParameter("price");
		String categories[] = request.getParameterValues("ck_category");
		
		System.out.print(categories.toString());
		
		
		if(AddItemData.AddItemDB(title, description, Double.valueOf(price))) {
			AddItemCategoryData.AddItemCategoryDB(title, categories);
			request.setAttribute("result", "success");
			
		}
		else
			request.setAttribute("result", "error");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("add_item.jsp");
		dispatcher.forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    
    private void searchInDatabase(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		String search = request.getParameter("search");
		if	(!(search == "" || search == null))
		{
			System.out.println("Search Category Start with " + search);
			List<Item> listItem = SearchDataInCategory.SearchCategory(search); 
			request.setAttribute("search", search);    
			request.setAttribute("listItem", listItem);
			request.setAttribute("result", "success");
		}
		else {
			request.setAttribute("result", "error");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");       
        dispatcher.forward(request, response);
   }
}