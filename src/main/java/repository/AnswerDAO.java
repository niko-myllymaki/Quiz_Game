package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.Answer;

public class AnswerDAO {

  private DataSource ds;
  
  public AnswerDAO(DataSource ds) throws SQLException {
    this.ds = ds;
  }
  
  public List<Answer> getAnswers() throws SQLException {
    List<Answer> answers = new ArrayList<>();
    String sql = "SELECT answerid, answer FROM answer";
    try(Connection conn = ds.getConnection()) {
      try(PreparedStatement pstm = conn.prepareStatement(sql)) {
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
          Answer m = new Answer(rs.getInt(1), rs.getString(2));
          answers.add(m);
        }
      }	
    }
	return answers;
    
  }
	
	

}
