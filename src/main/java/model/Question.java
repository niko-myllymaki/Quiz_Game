package model;

import java.io.Serializable;

public class Question implements Serializable {

  private static final long serialVersionUID = 1L;

  private int questionid;
  private String question;
  
  public Question() {
	  
  }
  
  public Question(int questionid, String question) {
    this.questionid = questionid;
    this.question = question;
  }

  public int getQuestionid() {
	return questionid;
  }

  public void setQuestionid(int questionid) {
	this.questionid = questionid;
  }

  public String getQuestion() {
	return question;
  }

  public void setQuestion(String question) {
	this.question = question;
  }
  
 

}
