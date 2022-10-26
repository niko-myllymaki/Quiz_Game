package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AnswerService;
import repository.QuestionDAO;
import model.Answer;
import model.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

@WebServlet ("/QuestionServlet/")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/QuizGameDB")
	private DataSource ds;
	
	private AnswerService answerService;
	
    public QuestionServlet() {
        super();
        answerService = new AnswerService();	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  Random rand = new Random();
	  int randInt = rand.nextInt(2);

	  try {
	    QuestionDAO questionDao = new QuestionDAO(ds);
	    List<Question> questions = questionDao.getQuestions();
	    Question questionToAsk = questions.get(randInt);
	    request.setAttribute("questionForClient", questionToAsk);
		  
	    if(questionToAsk.getQuestion().contains("What is the capital of Finland?")) {
	  	  List<Answer> answers = answerService.getAnswers(1);
		  request.setAttribute("possibleAnswers", answers);
	    } else {
	      List<Answer> answers = answerService.getAnswers(2);
	      request.setAttribute("possibleAnswers", answers);	
	    	
	    }
	    
	    String choice = request.getParameter("choice");
	    if(choice != null && choice.trim().length() > 0) {
	      if(choice.equals("Helsinki")) {
	        request.setAttribute("Answer", true);  	  
	      }	else if (choice.equals("Yes")) {
	    	request.setAttribute("Answer", true);  
	      } else {
	    	request.setAttribute("Answer", false);  
	      }
	    }
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
