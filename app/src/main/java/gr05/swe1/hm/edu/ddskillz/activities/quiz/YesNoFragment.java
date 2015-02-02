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
import android.widget.RadioGroup;

import gr05.swe1.hm.edu.ddskillz.BuildConfig;
import gr05.swe1.hm.edu.ddskillz.R;
public class YesNoFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,Quiz,  View.OnTouchListener {
    private QuizManager mListener;
    private Button activeButton;
    private boolean freezed = false;

    public YesNoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RadioFragment.
     */
    public static YesNoFragment newInstance() {
        return new YesNoFragment();
    }

    private void selectButton(Button button, int x, int y) {
        mListener.onFirstValid();

        if (activeButton != null) {
            activeButton.setSelected(false);
            activeButton = null;
        }

        activeButton = button;
        button.setSelected(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimationUtils.createCircularReveal(activeButton,
                    x,
                    y,
                    0,
                    Math.max(activeButton.getWidth(), activeButton.getHeight()) * 1.41f
            ).start();
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frame =  inflater.inflate(R.layout.fragment_yes_no, container, false);

        Button r1 = (Button) frame.findViewById(R.id.no);
        Button r2 = (Button) frame.findViewById(R.id.yes);


        r1.setOnTouchListener(this);
        r2.setOnTouchListener(this);


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
        return activeButton.getText().toString();
    }

    @Override
    public void freeze() {
        freezed = true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !freezed) {
            selectButton((Button) v, (int) event.getX(), (int) event.getY());
        }
        return false;
    }
}
