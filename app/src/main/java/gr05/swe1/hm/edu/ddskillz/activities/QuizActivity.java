package gr05.swe1.hm.edu.ddskillz.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.QuizManager;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.MasteredFragment;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.Quiz;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.RadioFragment;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.ShakeFragment;
import gr05.swe1.hm.edu.ddskillz.activities.quiz.SliderFragment;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Multiplequestion;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Question;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Quizzer;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Singlequestion;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Valuequestion;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Valuerangequestion;
import gr05.swe1.hm.edu.ddskillz.gson_models.QuizQuestions.Yesnoquestion;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;


public class QuizActivity extends Activity implements QuizManager {
    public final static String SKILL_NAME = "skillName";
    public final static String SHAKE = "shake";
    public final static String RADIO = "radio";
    public static final String SKILL_LVL = "skill_lvl";

    private FloatingActionButton fab;

    private Quiz quiz;
    private Question q;
    private String skillName;
    private int skillLevel;
    private TextView question;
    private Fragment fragment;

    private int lastRand = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String theme = getSharedPreferences("THEME", MODE_PRIVATE).getString("THEME", "LIGHT");
        switch(theme){
            case "LIGHT":
                setTheme(R.style.LightTheme);
                break;
            case "DARK":
                setTheme(R.style.DarkTheme);
                break;
        }
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
            getActionBar().setElevation(2);
        }
        setContentView(R.layout.activity_quiz);

        question = (TextView) findViewById(R.id.quiz_question);
        skillName = getIntent().getExtras().getString(SKILL_NAME, "Skill");
        skillLevel = getIntent().getExtras().getInt(SKILL_LVL, 0);
        getActionBar().setTitle(skillName);
        fab = (FloatingActionButton) findViewById(R.id.accept_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawResult(answer());
            }
        });

        fragment = getFragment(skillName, skillLevel);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        quiz = (Quiz) fragment;
        fragmentTransaction.add(R.id.quizLayout, fragment).commit();


    }


    public void drawResult(final int result) {
        String resultText = "+0";
        final int lastSkillLvl = skillLevel;

        if (result<0) {
            skillLevel = -result;
            resultText= "=" + (-result);
        }else if (result>0) {
            skillLevel+=result;
            resultText = "+"+result;
        }

        fab.setClickable(false);

        final String fText = resultText;

        ValueAnimator va = ObjectAnimator.ofInt(fab, "colorNormal", fab.getColorNormal(), result!=0 ? 0xffccff90 : 0xffF44336);
        va.setEvaluator(new ArgbEvaluator());
        va.start();
        quiz.freeze();
        final Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_question);
        scaleAnimation.reset();
        final FrameLayout frame = (FrameLayout) findViewById(R.id.question_frame);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                frame.removeAllViews();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                quiz = (Quiz) fragment;
                fragmentTransaction.setCustomAnimations(R.anim.fade_out, R.anim.fade_out);
                fragmentTransaction.remove(fragment).commit();

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                question.setTextSize(80f);
                question.setPadding(0, 0, 0, 0);
                question.setText(fText);
                question.setScaleX(1 / 0.76f);
                question.setScaleY(1 / 0.76f);
                frame.addView(question);


                new AsyncTask<Void, Void, Boolean>() {

                    @Override
                    protected Boolean doInBackground(Void... params) {
                        try {
                            SharedPreferences prefs = getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), MODE_PRIVATE);
                            BackendLink.addSkill(prefs, skillName, skillLevel);
                            BackendLink.updateEmployeeProfileMap(prefs);
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean bool) {
                        if (!bool) {
                            skillLevel = lastSkillLvl;
                            Toast.makeText(QuizActivity.this, "Sorry, your Skilllevel could'nt be updated!", Toast.LENGTH_SHORT).show();
                        }
                        int cx = fab.getWidth() / 2;
                        int cy = fab.getHeight() / 2;
                        int initialRadius = Math.max(fab.getWidth(), fab.getHeight());
                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(fab, cx, cy, initialRadius, 0);

                        int cx2 = frame.getWidth();
                        int cy2 = 0;
                        int initialRadius2 = frame.getWidth() * 2;
                        Animator anim2 =
                                ViewAnimationUtils.createCircularReveal(frame, cx2, cy2, initialRadius2, 0);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                fab.setVisibility(View.INVISIBLE);

                                fragment = getFragment(skillName, skillLevel);
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                quiz = (Quiz) fragment;
                                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_in);
                                fragmentTransaction.add(R.id.quizLayout, fragment).commit();

                                question.setScaleX(1);
                                question.setScaleY(1);
                                question.setPadding(32, 32, 32, 32);
                                question.setTextSize(22);

                                fab.setColorNormal(0xfff1f1f1);
                                fab.setClickable(true);

                                frame.clearAnimation();


                                final Animation scaleAnimationBack = AnimationUtils.loadAnimation(QuizActivity.this, R.anim.scale_question_back);
                                scaleAnimationBack.reset();
                                frame.startAnimation(scaleAnimationBack);
                            }
                        });
                        anim.start();
                        anim2.start();
                    }
                }.execute((Void) null);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        frame.clearAnimation();
        frame.startAnimation(scaleAnimation);
    }

    @Override
    public void onFirstValid() {
        if (fab.getVisibility() == View.INVISIBLE) {
            fab.setVisibility(View.VISIBLE);


            int cx = fab.getWidth() / 2;
            int cy = fab.getHeight() / 2;
            Log.d("Pos", fab.getLeft() + "," + fab.getRight() + "," + fab.getBottom() + "," + fab.getTop());
            int finalRadius = Math.max(fab.getWidth(), fab.getHeight());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(fab, cx, cy, 0, finalRadius);
                anim.start();
            }
        }
    }

    @Override
    public void setQuestion(String question) {
        this.question.setText(question);
    }

    /*
     * returns the increment (positive int) of the lvl or the new lvl for the lvl (indicated by negative value).
     *
     * @param: none
     * @return: the positive increment, or if a negative value, the new set value
     */
    private int answer() {
        if (question.getText().equals("Wie schätzen Sie sich selbst ein?")) {
            return -Integer.parseInt(quiz.getAnswer());
        }
        if (question.getText().equals("Shake It!")) {
            return -Integer.parseInt(quiz.getAnswer());
        }

        if (q.getAnswer().startsWith("[")) {
            Log.d("QuizActivity:answer", q.getAnswer() + "---" + quiz.getAnswer());
            if (q.getClass().equals(Valuerangequestion.class)) {
                int lower = Integer.parseInt(q.getAnswer().substring(1, q.getAnswer().indexOf(",")).trim());
                int upper = Integer.parseInt(q.getAnswer().substring(q.getAnswer().indexOf(",") + 1, q.getAnswer().length() - 1).trim());
                int given = Integer.parseInt(quiz.getAnswer().trim());
                if (given >= lower && given <= upper)
                    return 1;
                else
                    return 0;
            } else {
                //Multiplequestion
                boolean correct = true;
                String given = quiz.getAnswer();
                String correctAnswers = q.getAnswer();

                given = given.substring(1, given.length() - 1);
                correctAnswers = correctAnswers.substring(1, correctAnswers.length() - 1);
                int count = 1;

                // get correct answer count
                while (correctAnswers.contains(",")) {
                    //Log.d("QuizActivity:answer:correct",correctAnswers);
                    correctAnswers = correctAnswers.substring(correctAnswers.indexOf(",") + 1, correctAnswers.length());
                    count++;
                }
                correctAnswers = q.getAnswer();
                Log.d("QuizActivity:answer", count + " answers are correct");

                //check if each answer is correct
                while (given.length() > 0 && correct) {
                    String next;
                    if (given.contains(",")) {
                        next = given.substring(0, given.indexOf(","));
                        given = given.substring(given.indexOf(",") + 1, given.length());
                    } else {
                        next = given;
                        given = "";
                    }
                    //Log.d("QuizActivity:answer:given",given);


                    if (!correctAnswers.contains(next.trim())) {
                        correct = false;
                        Log.d("QuizActivity:answer", next + " is wrong");
                    }
                    count--;
                }
                Log.d("QuizActivity:answer", "count=" + count);
                return count == 0 && correct ? 1:0 ;
            }
        }
        return quiz.getAnswer().equals(q.getAnswer())? 1:0;
    }

    /*
     * Fetches QuestionData from Json-Files and returns a random Question's Fragment.
     * If skill is maxed a MasteredFragment, if no skill could be loaded a self assessment Fragment is returned.
     * @param: skillName: Name of the skill, skillLevel: Level of the Skill
     * @return: Fragment
     */
    private Fragment getFragment(String skillName, int skillLevel) {
        Fragment fragment;
        if (skillName.equals("NamenTanzen"))
            fragment = new ShakeFragment();
        else if (skillLevel >= 5)
            fragment = new MasteredFragment();
        else {
            try {
                String json = new Scanner(getAssets().open(skillName.toLowerCase().trim() + ".json")).useDelimiter("//A").next();
                Quizzer quizzer = new Gson().fromJson(json, Quizzer.class);


                List<Question> questions = new ArrayList<Question>();
                questions.addAll(quizzer.getMultiplequestions());
                questions.addAll(quizzer.getSinglequestions());
                questions.addAll(quizzer.getValuequestions());
                questions.addAll(quizzer.getYesnoquestions());
                questions.addAll(quizzer.getValuerangequestions());


                Random random = new Random();
                // int value rand defines which question is picked. (Of all available)
                int nextRand;
                if(lastRand<0 || questions.size()==1)
                    nextRand = random.nextInt(questions.size());
                else
                    nextRand = random.nextInt(questions.size()-1);
                if(lastRand==nextRand){
                    nextRand++;
                }

                q = questions.get(nextRand);
                lastRand=nextRand;

                Log.d("QuizActivity:getFragment",q.getClass().getName());

                this.setQuestion(q.getText());
                fragment = this.q.getFragment();
                if (fragment == null)
                    throw new NullPointerException();

            } catch (Exception e) {
                Log.e("QuizActivity:getFragment:failed to load fragment", e.getClass().getName());
                setQuestion("Wie schätzen Sie sich selbst ein?");
                fragment = SliderFragment.newInstance(0, 5);
            }
        }

        return fragment;
    }
}
