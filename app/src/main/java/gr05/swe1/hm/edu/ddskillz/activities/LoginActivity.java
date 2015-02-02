package gr05.swe1.hm.edu.ddskillz.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserLoginTask;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserRegisterTask;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

import static gr05.swe1.hm.edu.ddskillz.R.string.act_login_error_unRequired;

public class LoginActivity extends Activity {
    // UI references.
    private View mProgressView;
    public EditText mUsernameView;
    public EditText mPasswordView;
    private Button mRegisterButton;
    private Button mSignInButton;
    //SharedPreferences
    public SharedPreferences prefs;

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
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide t = new Slide();
            t.setSlideEdge(Gravity.RIGHT);
            this.getWindow().setEnterTransition(t);
            this.getWindow().setExitTransition(t);
        }
        setContentView(R.layout.activity_login);

        prefs = this.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), MODE_PRIVATE);

        //Hat der Nutzer sich bereits eingeloggt, wird die Login-View umgangen.
        if (!prefs.getString(BackendLink.KEYS.TOKEN.s(), "nichts").equals("nichts")) {
            Log.d("LoginActivity / OnCreate():", "User already logged in. Continuing to MainActivity");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mUsernameView.setText(prefs.getString(BackendLink.KEYS.USER_NAME.s(), ""));
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                //On pressing enter on the keyboard login. IME_NULL is enter-button
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    ConnectivityManager cm = (ConnectivityManager) textView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (isConnected) {
                        attemptLogin();
                    } else {
                        createNewSimpleAlert(getString(R.string.act_login_error_noConnectionTitle), getString(R.string.act_login_error_noConnectionMessage));
                    }
                    return true;
                }
                return false;
            }
        });
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    attemptLogin();
                } else {
                    createNewSimpleAlert(getString(R.string.act_login_error_noConnectionTitle), getString(R.string.act_login_error_noConnectionMessage));
                }
            }
        });
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager cm = (ConnectivityManager) view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (isConnected) {
                    attemptRegistration(view);
                } else {
                    createNewSimpleAlert(getString(R.string.act_login_error_noConnectionTitle), getString(R.string.act_login_error_noConnectionMessage));
                }
            }
        });
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempt to register as a new User. If the checkbox is checked the user gets logged in directly after a successfull registration
     * @param view
     */
    public void attemptRegistration(View view) {
        Context context = view.getContext();
        //Make new Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        final View popupView = inflater.inflate(R.layout.popup_register, null);
        builder.setView(popupView);
        builder.setTitle(R.string.popup_registration_textview_register);
        builder.setPositiveButton(getString(R.string.popup_registration_button_register), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.setNegativeButton(getString(R.string.popup_registration_button_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean coorectInput = true;
                EditText mRegisterUser = (EditText) popupView.findViewById(R.id.register_username_input);
                EditText mRegisterPass = (EditText) popupView.findViewById(R.id.register_password_input);
                EditText mRegisterMail = (EditText) popupView.findViewById(R.id.register_mail_input);
                CheckBox directLogin = (CheckBox) popupView.findViewById(R.id.checkBoxLogin);
                mRegisterUser.setError(null);
                mRegisterPass.setError(null);
                mRegisterMail.setError(null);
                String user = mRegisterUser.getText().toString();
                String pass = mRegisterPass.getText().toString();
                String mail = mRegisterMail.getText().toString();
                if (TextUtils.isEmpty(user)) {
                    mRegisterUser.setError(getString(R.string.act_login_error_unRequired));
                    coorectInput = false;
                } else if (user.length() <= 3) {
                    mRegisterUser.setError(getString(R.string.act_login_error_unLengthRequired));
                    coorectInput = false;
                }
                if (TextUtils.isEmpty(pass)) {
                    mRegisterPass.setError(getString(R.string.act_login_error_pwRequired));
                    coorectInput = false;
                }
                if (TextUtils.isEmpty(mail)) {
                    mRegisterMail.setError(getString(R.string.act_login_error_emRequired));
                    coorectInput = false;
                }
                if (coorectInput) {
                    UserRegisterTask mRegTask = new UserRegisterTask(LoginActivity.this, user, pass, mail);
                    mRegTask.execute((Void) null);
                    try {
                        coorectInput = mRegTask.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (coorectInput) {
                        Toast t = Toast.makeText(v.getContext(), getString(R.string.act_login_toast_succsesfull), Toast.LENGTH_LONG);
                        t.show();
                        if (directLogin.isChecked()) {
                            showProgress(true);
                            UserLoginTask mAuthTask = new UserLoginTask(LoginActivity.this, user, pass);
                            mAuthTask.execute((Void) null);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                        }
                    }
                }
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(act_login_error_unRequired));
            focusView = mUsernameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.act_login_error_pwRequired));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            UserLoginTask mAuthTask = new UserLoginTask(this, username, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Creates a simple Alert Dialog without Buttons to inform the User of Errors or Events that happened in the background.
     *
     * @param title   Title of the Alert Dialog
     * @param message Message of the Alert Dialog
     */
    public void createNewSimpleAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message).show();
    }

    /**
     * Adds an Error-Message to the Username-TextView and focuses on it.
     *
     * @param errorMessage Error Message to be displayed.
     */
    public void setErrorOnUsernameInput(String errorMessage) {
        mUsernameView.setError(errorMessage);
        mUsernameView.requestFocus();
    }

    /**
     * Shows the progress UI and hides the login form.
     *
     * @param show Boolean-Value to determine whether the Loading-Bar should be visible or not.
     */
    public void showProgress(final boolean show) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            mUsernameView.setEnabled(!show);
            mPasswordView.setEnabled(!show);
            mSignInButton.setEnabled(!show);
            mRegisterButton.setEnabled(!show);
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }
}