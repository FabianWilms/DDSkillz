package gr05.swe1.hm.edu.ddskillz.activities.quiz;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import gr05.swe1.hm.edu.ddskillz.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SliderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SliderFragment extends Fragment implements Quiz, SeekBar.OnSeekBarChangeListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LOWER_BOUND = "lowerBound";
    private static final String UPPER_BOUND = "upperBound";

    private int lowerBound;
    private int upperBound;

    private QuizManager mListener;
    private SeekBar slider;
    private TextView value;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SliderFragment.
     */
    public static SliderFragment newInstance(int lowerBound, int upperBound) {
        SliderFragment fragment = new SliderFragment();
        Bundle args = new Bundle();
        args.putInt(LOWER_BOUND, lowerBound);
        args.putInt(UPPER_BOUND, upperBound);
        fragment.setArguments(args);
        return fragment;
    }

    public SliderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lowerBound = getArguments().getInt(LOWER_BOUND);
            upperBound = getArguments().getInt(UPPER_BOUND);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_slider, container, false);
        slider = (SeekBar) v.findViewById(R.id.slider);
        slider.setOnSeekBarChangeListener(this);
        value = (TextView) v.findViewById(R.id.slider_value);
        slider.setMax(upperBound-lowerBound);
        value.setText(String.valueOf(lowerBound));
        return v;
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
    public String getAnswer() {
        return value.getText().toString();
    }

    @Override
    public void freeze() {
        slider.setEnabled(false);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        value.setText(String.valueOf(progress+lowerBound));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mListener.onFirstValid();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
