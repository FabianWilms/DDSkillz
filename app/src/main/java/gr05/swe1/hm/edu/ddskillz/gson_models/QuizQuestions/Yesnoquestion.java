
package gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions;

import android.app.Fragment;
import android.util.Log;

import com.google.gson.annotations.Expose;

import gr05.swe1.hm.edu.ddskillz.activities.quiz.YesNoFragment;

public class Yesnoquestion extends Question{

    @Expose
    private String text;
    @Expose
    private Boolean correctValue;

    private transient Fragment fragment;

    @Override
    public Fragment getFragment() throws RuntimeException{
        if(fragment==null) {
           fragment = YesNoFragment.newInstance();
        }

        return fragment;
    }

    @Override
    public String getAnswer() {
        if(correctValue)
            return "Yes";
        else
            return "No";
    }

    /**
     * 
     * @return
     *     The text
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The correctValue
     */
    public Boolean getCorrectValue() {
        return correctValue;
    }

    /**
     * 
     * @param correctValue
     *     The correctValue
     */
    public void setCorrectValue(Boolean correctValue) {
        this.correctValue = correctValue;
    }

}
