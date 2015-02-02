package gr05.swe1.hm.edu.ddskillz.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * Created by Fabian on 30.11.2014.
 */
public class UserLogoutTask extends AsyncTask<Void, Void, Boolean>{
    private Context context;
    private String error;

    public UserLogoutTask(Context context){this.context = context;}

    public String getError(){
        return error;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            error = "You aren't signed in anymore. Please clear your app data to Login again.";
            return BackendLink.logout(context);
        } catch (Exception e) {
            e.printStackTrace();
            this.error = e.getMessage();
            return false;
        }
    }
}
