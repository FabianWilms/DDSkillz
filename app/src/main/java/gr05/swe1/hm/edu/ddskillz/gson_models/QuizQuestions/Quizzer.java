package gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Quizzer {

    @Expose
    private List<Multiplequestion> multiplequestions = new ArrayList<Multiplequestion>();
    @Expose
    private List<Singlequestion> singlequestions = new ArrayList<Singlequestion>();
    @Expose
    private List<Yesnoquestion> yesnoquestions = new ArrayList<Yesnoquestion>();
    @Expose
    private List<Valuequestion> valuequestions = new ArrayList<Valuequestion>();
    @Expose
    private List<Valuerangequestion> valuerangequestions = new ArrayList<Valuerangequestion>();

    /**
     *
     * @return
     * The multiplequestions
     */
    public List<Multiplequestion> getMultiplequestions() {
        return multiplequestions;
    }

    /**
     *
     * @param multiplequestions
     * The multiplequestions
     */
    public void setMultiplequestions(List<Multiplequestion> multiplequestions) {
        this.multiplequestions = multiplequestions;
    }

    /**
     *
     * @return
     * The singlequestions
     */
    public List<Singlequestion> getSinglequestions() {
        return singlequestions;
    }

    /**
     *
     * @param singlequestions
     * The singlequestions
     */
    public void setSinglequestions(List<Singlequestion> singlequestions) {
        this.singlequestions = singlequestions;
    }

    /**
     *
     * @return
     * The yesnoquestions
     */
    public List<Yesnoquestion> getYesnoquestions() {
        return yesnoquestions;
    }

    /**
     *
     * @param yesnoquestions
     * The yesnoquestions
     */
    public void setYesnoquestions(List<Yesnoquestion> yesnoquestions) {
        this.yesnoquestions = yesnoquestions;
    }

    /**
     *
     * @return
     * The valuequestions
     */
    public List<Valuequestion> getValuequestions() {
        return valuequestions;
    }

    /**
     *
     * @param valuequestions
     * The valuequestions
     */
    public void setValuequestions(List<Valuequestion> valuequestions) {
        this.valuequestions = valuequestions;
    }

    /**
     *
     * @return
     * The valuerangequestions
     */
    public List<Valuerangequestion> getValuerangequestions() {
        return valuerangequestions;
    }

    /**
     *
     * @param valuerangequestions
     * The valuerangequestions
     */
    public void setValuerangequestions(List<Valuerangequestion> valuerangequestions) {
        this.valuerangequestions = valuerangequestions;
    }

}