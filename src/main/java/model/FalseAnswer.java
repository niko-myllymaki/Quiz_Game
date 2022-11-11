package model;

import java.io.Serializable;

public class FalseAnswer implements Serializable{
	
  private static final long serialVersionUID = 1L;
  
  private int falseAnswerId;
  private String falseAnswer;
  private int questionId;
  
  public FalseAnswer() {
	  
  }
  
  public FalseAnswer(int falseAnswerId, String falseAnswer, int questionId) {
    this.falseAnswerId = falseAnswerId;
    this.falseAnswer = falseAnswer;
    this.questionId = questionId;
  }

  public int getFalseAnswerId() {
	return falseAnswerId;
  }

  public void setFalseAnswerId(int falseAnswerId) {
	this.falseAnswerId = falseAnswerId;
  }

  public String getFalseAnswer() {
	return falseAnswer;
  }

  public void setFalseAnswer(String falseAnswer) {
	this.falseAnswer = falseAnswer;
  }

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }


}
