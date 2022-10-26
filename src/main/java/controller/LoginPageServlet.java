package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import repository.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
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
		
		List<User> users = userdao.getUsers();
		
		if(username == "" || password == "") {
	      request.setAttribute("error", "Please fill all the required fields.");	  
	    } else {
	      User auser = new User(0, username, password);
	      userdao.insertUser(auser);
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
