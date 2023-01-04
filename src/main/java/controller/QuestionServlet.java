package controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.AnswerDAO;
import repository.FalseAnswerDAO;
import repository.QuestionDAO;
import repository.UserDAO;
import model.Answer;
import model.Question;
import model.FalseAnswer;

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
	
	
    public QuestionServlet() {
      super();
    }
    
    //Handle login, questions
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
	  HttpSession session = request.getSession();
		
	  QuestionDAO questionDao = null;
	  UserDAO userDao = null;
	  try {
	    questionDao = new QuestionDAO(ds);
	    userDao = new UserDAO(ds);
	  } catch (Exception e) {
		  e.printStackTrace();	
	    }

	  List<Question> questions = null;
	  List<Answer> realAnswers = null;
	  List<FalseAnswer> falseAnswers = null;
	  List<String> combinedAnswerList = new ArrayList<>();
	  try {
	    questions = questionDao.getQuestions();
	  } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  
	  //Get username from LoginPageServlet and show it in questions.jsp
      String loggedUsername = (String) session.getAttribute("userName");
      request.setAttribute("loggedUsername", loggedUsername);
	   
      //Randomizing questions
      Random rand = new Random();
      int randInt = rand.nextInt(questions.size());
	  Question questionToAsk = questions.get(randInt);	    	    
	  request.setAttribute("questionForClient", questionToAsk);
			      
	  //Provide possible answers for questions.jsp for the user
	  int answersToGet = questionToAsk.getQuestionid();
	  
	  realAnswers = getRealAnswers();
	  falseAnswers = getFalseAnswers();
	
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
	  
	  //Get logged userId and logged user points and send and use them in questions.jsp
	  int loggedUserId = (int) session.getAttribute("userId");
	  int loggedUserPoints = 0;
	  try {
		loggedUserPoints = userDao.getUserPoints(loggedUserId);
	  } catch (SQLException e) {
		e.printStackTrace();
	  }
	  request.setAttribute("loggedUserPoints", loggedUserPoints);
	      
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/questions.jsp");
      rd.forward(request, response);
	}
	
	//Handle if Answer and Redirect back to GET
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  
	  HttpSession session = request.getSession();
	
	  UserDAO userDao = null;	
	  try {
	    userDao = new UserDAO(ds);	 
	  } catch (Exception e) {
		 
	    }
	  
	  List<String> correctAnswers = null;
	  correctAnswers = getCorrectAnswers();
	  
	  int loggedUserId = (int) session.getAttribute("userId");
	  int loggedUserPoints = 0;
	  try {
		loggedUserPoints = (int) userDao.getUserPoints(loggedUserId);
	  } catch (SQLException e) {
		e.printStackTrace();
	  }
	  
	  request.setAttribute("loggedUserPoints", loggedUserPoints);

	  //Check if the list contains user's choice and send a response to questions.jsp
	  //Update user's points if necessary
	  String choice = request.getParameter("choice");
  	  if(choice != null && choice.trim().length() > 0) {
	    if(correctAnswers.contains(choice)) {
	      request.setAttribute("Answer", true);  
 		  try {
			userDao.updateUserPoints(loggedUserId, loggedUserPoints);
		  } catch (SQLException e) {
			e.printStackTrace();
		    }
		} else {
	        request.setAttribute("Answer", false);  
		  }
	  }	    
	  
  	  doGet(request, response);
	  RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/questions.jsp");
	  rd.forward(request, response);
  }		
	
  public List<String> getCorrectAnswers() {
	
	List<Answer> realAnswers = getRealAnswers(); 
    List<String> correctAnswerList = new ArrayList<>();

    //Add the correct answers to a list	
	String answer = null;
	for(Answer ans: realAnswers) {
	  answer = ans.getAnswer();	
      correctAnswerList.add(answer);	
	}
    return correctAnswerList;
  }
  
  public List<Answer> getRealAnswers() {
    AnswerDAO answerDao = null;
	try {
	  answerDao = new AnswerDAO(ds);
	} catch (Exception e) {
	    	
	  }
	    
    List<Answer> realAnswers = null;
	try {
	  realAnswers = answerDao.getAnswers();
	} catch (SQLException e) {
	  e.printStackTrace();
	}	
    return realAnswers;
  }
  
  public List<FalseAnswer> getFalseAnswers() {
    FalseAnswerDAO falseAnseAnswerDAO = null;
    try {
	  falseAnseAnswerDAO = new FalseAnswerDAO(ds);
	} catch (SQLException e) {
	  e.printStackTrace();
	}
    
    List<FalseAnswer> falseAnswers = null;
    try {
	  falseAnswers = falseAnseAnswerDAO.getFalseAnswers();
	} catch (SQLException e) {
	  e.printStackTrace();
	}
    return falseAnswers;
  }

}

  

