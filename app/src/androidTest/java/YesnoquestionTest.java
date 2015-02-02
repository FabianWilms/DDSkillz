
import org.junit.Test;

import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Yesnoquestion;

import static org.junit.Assert.assertEquals;

/**
 * Created by o4 on 10.12.14.
 */
public class YesnoquestionTest {
    @Test
    public void testYesnoQuestion() throws Exception {
        Yesnoquestion testYesnoquestion = new Yesnoquestion();
        testYesnoquestion.setCorrectValue(true);
        testYesnoquestion.setText("Frage");
        assertEquals("Richtig", true, testYesnoquestion.getCorrectValue());
        assertEquals("Text", "Frage", testYesnoquestion.getText());
    }
}
