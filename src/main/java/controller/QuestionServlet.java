package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AnswerDAO;
import repository.AnswerService;
import repository.QuestionDAO;
import model.Answer;
import model.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
	  try {
	    QuestionDAO questionDao = new QuestionDAO(ds);
	    AnswerDAO answerDao = new AnswerDAO(ds);
	    List<Question> questions = questionDao.getQuestions();
	    List<Answer> realAnswers = answerDao.getAnswers();
	    List<String> answerList = new ArrayList<>();
	    
	    //For randomizing questions and getting the answer to the question
		Random rand = new Random();
		int randInt = rand.nextInt(questions.size());
	    Question questionToAsk = questions.get(randInt);
	    Answer answerToQuestion = realAnswers.get(randInt);
	    
	    request.setAttribute("questionForClient", questionToAsk);
	    
	    //Add the correct answers without id to a String list
	    Answer answer = null;
	    for(int i = 0; i < questions.size(); i++) {
	      answer = realAnswers.get(i);
	      answerList.add(answer.getAnswer());
	    }
	    
	    //Provide possible answers for questions.jsp for the user
	    int answersToGet = answerToQuestion.getAnswerId();
	    List<Answer> answers = answerService.getAnswers(answersToGet);
		request.setAttribute("possibleAnswers", answers);
		  
	    //Check if the list contains user's choice and send a response to questions.jsp
	    String choice = request.getParameter("choice");
	    if(choice != null && choice.trim().length() > 0) {
	      if(answerList.contains(choice)) {
	        request.setAttribute("Answer", true);  	 
	      }	else {
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
