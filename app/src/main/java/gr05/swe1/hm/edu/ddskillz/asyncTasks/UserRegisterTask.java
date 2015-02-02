package gr05.swe1.hm.edu.ddskillz.asyncTasks;

import android.os.AsyncTask;

import gr05.swe1.hm.edu.ddskillz.activities.LoginActivity;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * Created by Fabian on 26.11.2014.
 */
public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    private LoginActivity callingAct;
    private final String username;
    private final String password;
    private final String mail;
    private String error = "Something went wrong...";

    public UserRegisterTask(LoginActivity callingAct, String username, String password, String mail) {
        this.callingAct = callingAct;
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return BackendLink.BackEndLinkRegistration(username, password, mail);
        } catch (Exception e) {
            this.error = (e.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (!success)
            callingAct.createNewSimpleAlert("Error", error);
    }
}
