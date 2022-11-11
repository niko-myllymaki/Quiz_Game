package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import model.FalseAnswer;

public class FalseAnswerDAO {

  private DataSource ds;
  
  public FalseAnswerDAO(DataSource ds) throws SQLException {
    this.ds = ds;
  }
  
  public List<FalseAnswer> getFalseAnswers() throws SQLException {
    List<FalseAnswer> falseAnswers = new ArrayList<>();
    String sql = "SELECT falseAnswerid, falseAnswer, questionid FROM falseAnswer";
    try(Connection conn = ds.getConnection()) {
      try(PreparedStatement pstm = conn.prepareStatement(sql)) {
        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
          FalseAnswer m = new FalseAnswer(rs.getInt(1), rs.getString(2), rs.getInt(3));
          falseAnswers.add(m);
        }
      }	
    }
	return falseAnswers;
  }
}
