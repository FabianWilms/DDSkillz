package gr05.swe1.hm.edu.ddskillz.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * Created by Fabian on 05.12.2014.
 */
public class UserAddSkillTask extends AsyncTask<Void, Void, Boolean>{
    private SharedPreferences prefs;
    private final String skillName;
    private final int skillValue;
    private String error = "";

    public UserAddSkillTask(SharedPreferences prefs, String skillName, int skillValue){
        this.prefs = prefs;
        this.skillName = skillName;
        this.skillValue = skillValue;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (BackendLink.addSkill(prefs, skillName, skillValue));{
                BackendLink.updateEmployeeProfileMap(prefs);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            error = e.getMessage();
            return false;
        }
    }

    public String getError(){
        return error;
    }
}
