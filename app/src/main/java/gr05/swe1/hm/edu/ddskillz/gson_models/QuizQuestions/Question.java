package gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions;

import android.app.Fragment;

/**
 * Created by Maximilian on 09.12.2014.
 */
public abstract class Question {
    /*
     * returns the a Fragment that can be used to answer this question.
     * @return Fragment
     */
    public abstract Fragment getFragment() throws RuntimeException;
    /*
     * returns a String with the question text.
     */
    public abstract String getText();
    /*
     * returns a String representing the answer to this question.
     */
    public abstract String getAnswer();
}
