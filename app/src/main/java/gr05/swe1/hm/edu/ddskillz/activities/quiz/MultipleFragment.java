package gr05.swe1.hm.edu.ddskillz.activities.quiz;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import gr05.swe1.hm.edu.ddskillz.BuildConfig;
import gr05.swe1.hm.edu.ddskillz.R;

public class MultipleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, Quiz, View.OnTouchListener {
    private static final String OPTION_1 = "option1";
    private static final String OPTION_2 = "option2";
    private static final String OPTION_3 = "option3";
    private static final String OPTION_4 = "option4";

    private String option1;
    private String option2;
    private String option3;
    private String option4;

    private QuizManager mListener;
    private CheckBox[] buttons;
    private boolean freezed = false;

    public MultipleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RadioFragment.
     */
    public static MultipleFragment newInstance(String option1, String option2, String option3, String option4) {
        MultipleFragment fragment = new MultipleFragment();
        Bundle args = new Bundle();
        args.putString(OPTION_1, option1);
        args.putString(OPTION_2, option2);
        args.putString(OPTION_3, option3);
        args.putString(OPTION_4, option4);
        fragment.setArguments(args);
        return fragment;
    }

    private void selectButton(CheckBox button, int x, int y) {
        mListener.onFirstValid();

        button.setSelected(!button.isSelected());
        button.setChecked(button.isSelected());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float size = Math.max(button.getWidth(), button.getHeight()) * 1.41f;
            ViewAnimationUtils.createCircularReveal(button,
                    x,
                    y,
                    0,
                    size
            ).start();

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            option1 = getArguments().getString(OPTION_1);
            option2 = getArguments().getString(OPTION_2);
            option3 = getArguments().getString(OPTION_3);
            option4 = getArguments().getString(OPTION_4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frame = inflater.inflate(R.layout.fragment_multiple, container, false);

        CheckBox r1 = (CheckBox) frame.findViewById(R.id.checkBox);
        CheckBox r2 = (CheckBox) frame.findViewById(R.id.checkBox2);
        CheckBox r3 = (CheckBox) frame.findViewById(R.id.checkBox3);
        CheckBox r4 = (CheckBox) frame.findViewById(R.id.checkBox4);

        buttons = new CheckBox[]{r1, r2, r3, r4};

        r1.setText(option1);
        r2.setText(option2);
        r3.setText(option3);
        r4.setText(option4);

        r1.setOnTouchListener(this);
        r2.setOnTouchListener(this);
        r3.setOnTouchListener(this);
        r4.setOnTouchListener(this);


        return frame;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QuizManager) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mListener != null) {
            mListener.onFirstValid();
        }
    }


    @Override
    public String getAnswer() {
        List<String> answers = new ArrayList<String>();
        for (CheckBox next : buttons){
            if(next.isSelected())
                answers.add((String)next.getText());
        }
        return answers.toString();
    }

    @Override
    public void freeze() {
        freezed = true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !freezed) {
            selectButton((CheckBox) v, (int) event.getX(), (int) event.getY());
        }
        return true;
    }
}
