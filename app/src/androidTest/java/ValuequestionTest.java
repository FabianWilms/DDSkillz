
import org.junit.Test;

import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Valuequestion;

import static org.junit.Assert.assertEquals;

/**
 * Created by o4 on 10.12.14.
 */
public class ValuequestionTest {
    @Test
    public void testValueQuestion() throws Exception {
        Valuequestion testValuequestion = new Valuequestion();
        testValuequestion.setText("Bla");
        testValuequestion.setCorrectValue(5);
        testValuequestion.setLowerBound(2);
        testValuequestion.setUpperBound(10000);
        assertEquals("Frage", "Bla", testValuequestion.getText());
        assertEquals("Korrekt", 5, testValuequestion.getCorrectValue().intValue());
        assertEquals("Unten", 2, testValuequestion.getLowerBound().intValue());
        assertEquals("Oben", 10000, testValuequestion.getUpperBound().intValue());
    }
}
