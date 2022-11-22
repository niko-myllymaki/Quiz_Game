package model;

import java.io.Serializable;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;
	
  private int userid;
  private String username; 
  private String password;
  private int points;
  
  public User() {
	  
  }
  
  public User(int userid, String username, String password, int points) {
    this.userid = userid;
    this.username = username;
    this.password = password;
    this.points = points;
  }

  public int getUserid() {
	return userid;
  }

  public void setUserid(int userid) {
	this.userid = userid;
  }

  public String getUsername() {
	return username;
  }

  public void setUsername(String username) {
	this.username = username;
  }

  public String getPassword() {
	return password;
  }

  public void setPassword(String password) {
	this.password = password;
  }
  
  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }
	  
	
	  
	

}
