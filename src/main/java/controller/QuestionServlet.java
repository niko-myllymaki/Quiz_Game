package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.QuestionDAO;
import model.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

@WebServlet ("/QuestionServlet/")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/QuizGameDB")
	private DataSource ds;
	
    public QuestionServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  ArrayList<String> data = new ArrayList<>();
	  try {
	    QuestionDAO questionDao = new QuestionDAO(ds);
	    List<Question> questions = questionDao.getQuestions();
	    request.setAttribute("questions", questions);
	  } catch(SQLException e) {
		  request.setAttribute("error", "Problems occured. Try again later.");
		  e.printStackTrace();
	  }
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/questions.jsp");
	  rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
