package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import model.User;

public class UserDAO {
	
  private DataSource ds;
	  
  public UserDAO(DataSource ds) throws SQLException {
    this.ds = ds;	   
  }
  
  public List<User> getUsers() throws SQLException {
    List<User> users = new ArrayList<>();
    String sql = "SELECT userid, username, password, points FROM user";
    try (Connection conn = ds.getConnection()) {
      try (PreparedStatement pstm = conn.prepareStatement(sql); 
    		  	  ResultSet rs = pstm.executeQuery()) {
        while(rs.next()) {
          User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
          users.add(user);
        }  
      }
    }
    return users;	  
  }
  
  public int insertUser(User auser) throws SQLException {
    String sql = "INSERT INTO user(username, password) VALUE(?,?)";	  
	int userid = 0;
	try (Connection conn = ds.getConnection()) {
	  try (PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    pstm.setString(1, auser.getUsername());	
	    pstm.setString(2, auser.getPassword());	  
	    pstm.executeUpdate();
	    try (ResultSet rs = pstm.getGeneratedKeys()) {
	      if (rs.next()) {
	        userid = rs.getInt(1);	
	      }	
	    }  
	  }	
	}
	return userid;  
  }
  
  public void updatePoints(int userid) throws SQLException {
    String sql = "UPDATE user SET points=? WHERE userid=?";
    try (Connection conn = ds.getConnection()) {
      try (PreparedStatement pstm = conn.prepareStatement(sql)) {
    	pstm.setInt(1, 1);  
    	pstm.setInt(2, userid);  
        pstm.executeUpdate();
      }	
    } 
  }
  
  public int getUserId(String username, String password) throws SQLException {
    String sql = "SELECT userid FROM user WHERE username=? AND password=?";
    int userId = 0;	
    try (Connection conn = ds.getConnection()) {
      try (PreparedStatement pstm = conn.prepareStatement(sql)) {
        pstm.setString(1, username);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
          userId = rs.getInt("userid");	
        }
      }	
    }
    return userId; 
  }
  
  
 
}
