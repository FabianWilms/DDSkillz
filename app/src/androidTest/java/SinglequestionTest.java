
import org.junit.Test;

import java.util.LinkedList;

import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Singlequestion;

import static org.junit.Assert.assertEquals;

/**
 * Created by o4 on 11.12.14.
 */
public class SinglequestionTest {
    @Test
    public void testSingleQuestion() throws Exception{
        Singlequestion testSinglequestion=new Singlequestion();
        testSinglequestion.setText("Frage");
        testSinglequestion.setCorrectAnswer("Richtig");
        LinkedList<String> testList=new LinkedList<String>();
        testList.add("Falsch");
        testList.add("Auch falsch");
        testSinglequestion.setFalseAnswers(testList);
        assertEquals("Text", "Frage", testSinglequestion.getText());
        assertEquals("Korrekt", "Richtig", testSinglequestion.getCorrectAnswer());
        assertEquals("Falsch 1", "Falsch",testSinglequestion.getFalseAnswers().get(0));
        assertEquals("Falsch 2", "Auch falsch",testSinglequestion.getFalseAnswers().get(1));
    }
}
