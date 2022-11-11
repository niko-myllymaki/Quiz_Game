package model;

import java.io.Serializable;

public class Answer implements Serializable{
	
  private static final long serialVersionUID = 1L;
  
  private int answerId;
  private String answer;
  private int questionId;
  
  public Answer() {
	  
  }
  
  public Answer(int answerId, String answer, int questionId) {
    this.answerId = answerId;
    this.answer = answer;
    this.questionId = questionId;
  }

  public int getAnswerId() {
	return answerId;
  }

  public void setAnswerId(int answerId) {
	this.answerId = answerId;
  }

  public String getAnswer() {
	return answer;
  }

  public void setAnswer(String answer) {
	this.answer = answer;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


}
