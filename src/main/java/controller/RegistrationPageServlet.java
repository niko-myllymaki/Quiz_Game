package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import repository.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet ("/Registration/")
public class RegistrationPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/QuizGameDB")
	private DataSource ds;
	
    public RegistrationPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
	  rd.forward(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  UserDAO userDao = null;  
	  String username = request.getParameter("username");	
	  String password = request.getParameter("password");	
      HttpSession session = request.getSession();
      
	  List<User> users = null;
      List<String> usernames = new ArrayList<>();
	  List<String> passwords = new ArrayList<>();
      
	  try {
        userDao = new UserDAO(ds);
	  } catch (SQLException e) {
		e.printStackTrace();
	  } 
	  
	  try {
		users = userDao.getUsers();
	  } catch (SQLException e) {
		e.printStackTrace();
	  }
	  
	  for(User u: users) {
	    usernames.add(u.getUsername());
	    passwords.add(u.getPassword());
	  }
	  
	  if(username == "" || password == "") {
	    request.setAttribute("error", "Please fill all the required fields.");	  
	  } else if (usernames.contains(username)) {
		request.setAttribute("error", "That user already exists."); 
	  } else {
		User auser = new User(0, username, password, 0);
		try {
		  int userId = userDao.insertUser(auser);
		  session.setAttribute("userName", username);
		  session.setAttribute("userId", userId);
		} catch (SQLException e) {
		  e.printStackTrace();
		}
		
	    response.sendRedirect("QuestionServlet"); 
	    return;
	  }
	  
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/registration.jsp");
	  rd.forward(request, response);
	}
}