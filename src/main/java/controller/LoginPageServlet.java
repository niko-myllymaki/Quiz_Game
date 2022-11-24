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

@WebServlet ("/LoginPageServlet/")
public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/QuizGameDB")
	private DataSource ds;
  
    public LoginPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try {
		UserDAO userdao = new UserDAO(ds);
		String username = request.getParameter("username");	
		String password = request.getParameter("password");	
		List<String> usernames = new ArrayList<>();
		List<String> passwords = new ArrayList<>();
        HttpSession session = request.getSession();
        
		List<User> users = userdao.getUsers();
		for(User u: users) {
		  usernames.add(u.getUsername());
		  passwords.add(u.getPassword());
		}
		
		if(username == "" || password == "") {
	      request.setAttribute("error", "Please fill all the required fields.");	  
	    } else if(usernames.contains(username) && passwords.contains(password) ) {
	      //Now we can use this in QuestionServlet or any other servlet		
	      int userId = userdao.getUserId(username, password);
	      session.setAttribute("userName", username);	
	      session.setAttribute("userId", userId); 
	      response.sendRedirect("QuestionServlet");  
	      return;
	    } else {
	      User auser = new User(0, username, password, 0);
	      int userId = userdao.insertUser(auser);
	      session.setAttribute("userName", username);
	      session.setAttribute("userId", userId);
	      response.sendRedirect("QuestionServlet");  
	      return;
		}
		
	  } catch (SQLException e) {
          request.setAttribute("error", "Problems. Try again later.");
		  e.printStackTrace();
	  }	
	
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
	  rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
	}

}
