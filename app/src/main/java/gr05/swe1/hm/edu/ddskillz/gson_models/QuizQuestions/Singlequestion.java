
package gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions;

import android.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.gson.annotations.Expose;

import gr05.swe1.hm.edu.ddskillz.activities.quiz.RadioFragment;

public class Singlequestion extends Question{

    @Expose
    private String text;
    @Expose
    private String correctAnswer;
    @Expose
    private List<String> falseAnswers = new ArrayList<String>();

    private transient Fragment fragment;

    @Override
    public Fragment getFragment() throws RuntimeException{
        if(fragment==null) {
            List<String> answers = new ArrayList<String>();
            answers.add(getCorrectAnswer());

            List<String> falseAnswers = new ArrayList<String>();
            falseAnswers.addAll(getFalseAnswers());

            Log.d("SingleQuestion:getFragment","adding answers");

            Random random = new Random();

            while(answers.size()<4) {
                int next = random.nextInt(falseAnswers.size());
                answers.add(falseAnswers.remove(next));
            }

            Collections.shuffle(answers);
            fragment = RadioFragment.newInstance(answers.get(0), answers.get(1), answers.get(2), answers.get(3));
        }

        return fragment;
    }

    @Override
    public String getAnswer() {
        return correctAnswer;
    }

    /**
     * @return The text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return The correctAnswer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @param correctAnswer The correctAnswer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * @return The falseAnswers
     */
    public List<String> getFalseAnswers() {
        return falseAnswers;
    }

    /**
     * @param falseAnswers The falseAnswers
     */
    public void setFalseAnswers(List<String> falseAnswers) {
        this.falseAnswers = falseAnswers;
    }

}
