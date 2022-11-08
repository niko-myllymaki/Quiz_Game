package repository;

import java.util.ArrayList;
import java.util.List;

import model.Answer;

public class AnswerService {
	
  private List<Answer> answersToQ1;
  private List<Answer> answersToQ2;
  private List<Answer> answersToQ3;
  private List<Answer> answersToQ4;



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
    
    answersToQ3 = new ArrayList<>();
    answersToQ3.add(new Answer(9, "Blue"));
    answersToQ3.add(new Answer(10, "Red"));
    answersToQ3.add(new Answer(11, "Pink"));
    answersToQ3.add(new Answer(12, "Yellow"));
    
    answersToQ4 = new ArrayList<>();
    answersToQ4.add(new Answer(13, "Vaasa"));
    answersToQ4.add(new Answer(14, "No idea"));
    answersToQ4.add(new Answer(15, "Gerby"));
    answersToQ4.add(new Answer(16, "Palosaari"));
    
    
  }

  public List<Answer> getAnswers(int listNum) {
    if(listNum == 1) {
      return answersToQ1; 
    } else if(listNum == 2) {
        return answersToQ2;	
    } else if(listNum == 3) {
        return answersToQ3;	
    } else {
        return answersToQ4;	
    }	  
    
  }
	
}
