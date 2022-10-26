package repository;

import java.util.ArrayList;
import java.util.List;
import model.Answer;

public class AnswerService {

  private List<Answer> answersToQ1;
  private List<Answer> answersToQ2;

  
  public AnswerService() {
    answersToQ1 = new ArrayList<>();
	answersToQ1.add(new Answer(1, "Berlin"));
    answersToQ1.add(new Answer(2, "Helsinki"));
	answersToQ1.add(new Answer(3, "Turku"));
	answersToQ1.add(new Answer(4, "Stockholm"));
   
    answersToQ2 = new ArrayList<>();
    answersToQ2.add(new Answer(5, "No"));
    answersToQ2.add(new Answer(6, "Yes"));
    answersToQ2.add(new Answer(7, "Maybe"));
    answersToQ2.add(new Answer(8, "We'll see"));
  }

  public List<Answer> getAnswers(int listNum) {
    if(listNum == 1) {
      return answersToQ1; 
    } else {
      return answersToQ2;	
    }	  
    
  }
  
  /*
  public Answer searchAnswer(String answer) {
    Answer result = null;
    for(Answer ans: answers) {
      if(ans.getAnswer().equalsIgnoreCase(answer))	
        result = ans;
        break;
    }
    return result;
  }
  */
	
}
