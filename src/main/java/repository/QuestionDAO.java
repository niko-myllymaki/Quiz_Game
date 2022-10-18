package repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuestionDAO {
	
  private DataSource ds;
  
  public QuestionDAO(DataSource ds) throws SQLException {
    this.ds = ds;	   
  }
  
  public List<Question> getQuestions() throws SQLException {
    List<Question> questions = new ArrayList<>();
    String sql = "SELECT questionid, question FROM question";
    try(Connection conn = ds.getConnection()) {
      try(PreparedStatement pstm = conn.prepareStatement(sql)) {
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
           Question m = new Question(rs.getInt(1), rs.getString(2));
           questions.add(m);
        }
      }  	
    }
    return questions;	  
  }

}
