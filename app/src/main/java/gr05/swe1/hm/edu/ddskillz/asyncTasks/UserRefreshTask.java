package gr05.swe1.hm.edu.ddskillz.asyncTasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.IOException;

import gr05.swe1.hm.edu.ddskillz.Exceptions.TokenTimeoutException;
import gr05.swe1.hm.edu.ddskillz.Exceptions.UserException;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * Fetches new User-Data from Backend.
 */
public class UserRefreshTask extends AsyncTask<Void, Void, Boolean> {
    private SharedPreferences prefs;
    private String error;

    public UserRefreshTask(SharedPreferences prefs){
        this.prefs = prefs;
    }

    @Override
    protected Boolean doInBackground(Void... params){
        try {
            BackendLink.updateMatrixDTO(prefs);
            BackendLink.updateEmployeeProfileMap(prefs);
            BackendLink.updateUserDTO(prefs);
            return true;
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
