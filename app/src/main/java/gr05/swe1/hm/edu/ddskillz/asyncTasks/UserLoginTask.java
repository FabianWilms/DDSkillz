package gr05.swe1.hm.edu.ddskillz.asyncTasks;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.activities.LoginActivity;
import gr05.swe1.hm.edu.ddskillz.activities.MainActivity;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * A task running in the Background which tries to verifiy the user to the backend.
 */
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
    private LoginActivity callingAct;
    private final String username;
    private final String pasword;
    private String error = "";

    /**
     * Custom Constructor
     *
     * @param callingAct The calling Login-Activity. Needed for changing the GUI.
     * @param username The Username of the User
     * @param password The Password of the User
     */
    public UserLoginTask(LoginActivity callingAct, String username, String password) {
        this.callingAct = callingAct;
        this.username = username;
        this.pasword = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            BackendLink b = new BackendLink(username, pasword, callingAct.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), Context.MODE_PRIVATE));
            return b.wasLoginSuccessfull();
        } catch (Exception e) {
            Log.e("<LoginActivity-UserLoginTask-doInBackground> Error: ", e.getMessage() + "-" + e.getClass());
            this.error = e.getMessage();
            return false;
        }
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        callingAct.showProgress(false);
        if (success) {
            Intent intent = new Intent(callingAct.getApplicationContext(), MainActivity.class);
            if(Build.VERSION.SDK_INT >= 21) {
                callingAct.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(callingAct).toBundle());
            } else {
                callingAct.startActivity(intent);
            }
        } else {
            if(error.equals("")) {
                callingAct.setErrorOnUsernameInput(callingAct.getString(R.string.act_login_error_login));
            }else {
                callingAct.createNewSimpleAlert("Error", error);
            }
        }
    }

    @Override
    protected void onCancelled() {
        callingAct.showProgress(false);
        callingAct.createNewSimpleAlert("Canceled", "The login was cancelled. Please try again!");
    }
}