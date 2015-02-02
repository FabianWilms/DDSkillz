package gr05.swe1.hm.edu.ddskillz.activities.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gr05.swe1.hm.edu.ddskillz.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class MasteredFragment extends Fragment implements  Quiz{

    private QuizManager mListener;

    public static MasteredFragment newInstance() {
        MasteredFragment fragment = new MasteredFragment();
        return fragment;
    }

    public MasteredFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_mastered, container, false);
        mListener.setQuestion("Mastered this skill You have!");
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (QuizManager) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FirstValidListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String getAnswer() {
        return "foo foo foapog poiwegöawegsergrsdhawrgojaerglknawrw4toawöaegbpouhaergükjqerguq34zjaergljaerglhaergöohaeghargeoiharghargekjnaergöknarjaergölhawraer" +
                "iwrgöojaregojareglaerlkaerkölharwgöoarwoarelköjargelökaergölkjargeölkhrglhaerglaerglökararhölkargeölkarergeaer";
    }

    @Override
    public void freeze() {

    }
}
