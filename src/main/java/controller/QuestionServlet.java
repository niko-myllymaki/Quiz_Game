package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.AnswerDAO;
import repository.FalseAnswerDAO;
import repository.QuestionDAO;
import model.Answer;
import model.Question;
import model.FalseAnswer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
	  try {
	    QuestionDAO questionDao = new QuestionDAO(ds);
	    AnswerDAO answerDao = new AnswerDAO(ds);
	    FalseAnswerDAO falseAnswerDao = new FalseAnswerDAO(ds);
	    List<Question> questions = questionDao.getQuestions();
	    List<Answer> realAnswers = answerDao.getAnswers();
	    List<FalseAnswer> falseAnswers = falseAnswerDao.getFalseAnswers();
	    List<String> correctAnswerList = new ArrayList<>();
	    List<String> combinedAnswerList = new ArrayList<>();

	    //Randomizing questions
		Random rand = new Random();
		int randInt = rand.nextInt(questions.size());
	    Question questionToAsk = questions.get(randInt);	    	    
	    request.setAttribute("questionForClient", questionToAsk);
	    
	    //Add the correct answers to a list
	    String answer = null;
	    for(Answer ans: realAnswers) {
	      answer = ans.getAnswer();	
	      correctAnswerList.add(answer);	
	    }
	    
	    //Provide possible answers for questions.jsp for the user
	    int answersToGet = questionToAsk.getQuestionid();
	    
	    for(Answer a: realAnswers) {	
	      if(a.getQuestionId() == answersToGet) {
	        combinedAnswerList.add(a.getAnswer());	  
	      }	
	    }
	    	
	    for(FalseAnswer fa: falseAnswers) {
	      if(fa.getQuestionId() == answersToGet) {
	        combinedAnswerList.add(fa.getFalseAnswer());
	      }
		}
	    
	    //Convert arraylist to array
	    Object[] answersArr = combinedAnswerList.toArray();
	    
	    //Shuffle the possible answers
	    int sizeOfArr = answersArr.length;
	    for (int i = sizeOfArr-1; i > 0; i--) {
	      // Pick a random index from 0 to i	
	      int j = rand.nextInt(i);
	     
	      // Swap answersArr[i] with the element at random index
	      String temp = (String) answersArr[i];
	      answersArr[i] = answersArr[j];
	      answersArr[j] = temp;	
	    }
	    	    
		request.setAttribute("possibleAnswers", answersArr);
		  
	    //Check if the list contains user's choice and send a response to questions.jsp
	    String choice = request.getParameter("choice");
	    if(choice != null && choice.trim().length() > 0) {
	      if(correctAnswerList.contains(choice)) {
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
