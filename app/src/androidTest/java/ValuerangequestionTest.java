import org.junit.Test;

import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Valuerangequestion;

import static junit.framework.Assert.assertEquals;

/**
 * Created by o4 on 12.12.14.
 */
public class ValuerangequestionTest {
    @Test
    public void testValuerangequestion() throws Exception{
        Valuerangequestion testValuerangequestion =new Valuerangequestion();
        testValuerangequestion.setUpperBound(1004);
        testValuerangequestion.setLowerBound(477);
        testValuerangequestion.setText("Bla");
        testValuerangequestion.setAnswerLowerBound(2000);
        testValuerangequestion.setAnswerUpperBound(800);
        assertEquals("UpperBound",1004, testValuerangequestion.getUpperBound().intValue());
        assertEquals("LowerBound",477, testValuerangequestion.getLowerBound().intValue());
        assertEquals("Test","Bla", testValuerangequestion.getText());
        assertEquals("AnswerUpperBound",800, testValuerangequestion.getAnswerUpperBound().intValue());
        assertEquals("AnswerLowerBound",2000, testValuerangequestion.getAnswerLowerBound().intValue());
    }
}
