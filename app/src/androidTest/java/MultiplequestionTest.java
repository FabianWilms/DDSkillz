import org.junit.Test;

import java.util.LinkedList;

import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Multiplequestion;

import static org.junit.Assert.assertEquals;

/**
 * Created by o4 on 11.12.14.
 */
public class MultiplequestionTest {
    @Test
    public void testMultipleQuestion() throws Exception{
        Multiplequestion testMultiplequestion=new Multiplequestion();
        testMultiplequestion.setText("Frage");
        LinkedList<String> testListRight=new LinkedList<String>();
        testListRight.add("Richtig");
        testListRight.add("Si");
        testMultiplequestion.setCorrectAnswers(testListRight);
        LinkedList<String> testListFalse=new LinkedList<String>();
        testListFalse.add("Sehr falsch");
    testListFalse.add("Ganz falsch");
        testMultiplequestion.setFalseAnswers(testListFalse);
        assertEquals("Text", "Frage", testMultiplequestion.getText());
        assertEquals("Korrekt 1", "Richtig", testMultiplequestion.getCorrectAnswers().get(0));
        assertEquals("Korrekt 2", "Si", testMultiplequestion.getCorrectAnswers().get(1));
        assertEquals("Falsch 1", "Sehr falsch", testMultiplequestion.getFalseAnswers().get(0));
        assertEquals("Falsc 2", "Ganz falsch", testMultiplequestion.getFalseAnswers().get(1));
    }
}
