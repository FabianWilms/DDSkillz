package gr05.swe1.hm.edu.ddskillz.activities;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.tech.NfcBarcode;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserLoginTask;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserLogoutTask;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserRefreshTask;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserRegisterTask;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;


public class MainActivity extends Activity {

    private SharedPreferences prefs;
    private TileAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

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
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
        }
        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), MODE_PRIVATE);

        final Context context = this;
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshView);
        final RecyclerView tiles = (RecyclerView) findViewById(R.id.gridViewNew);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        tiles.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        tiles.setLayoutManager(gridLayoutManager);
        tiles.setAdapter(adapter = new TileAdapter(context));
        tiles.setItemAnimator(new DefaultItemAnimator());

        fab.attachToRecyclerView(tiles);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptAdd(v);
            }
        });

        if(getSharedPreferences("THEME", MODE_PRIVATE).getString("THEME", "LIGHT").equals("LIGHT")){
            refreshLayout.setColorSchemeResources(R.color.lighttheme_primary);
        }else {
            refreshLayout.setColorSchemeResources(R.color.darktheme_primary);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UserRefreshTask().execute((Void) null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_profile:
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case R.id.action_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.act_main_popup_logout_message)).
                        setPositiveButton(getString(R.string.act_main_popup_logout_btnPos), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getString(R.string.act_main_popup_logout_btnNeg), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.action_changeTheme:
                final String[] themes = {"Material Light","Material Dark"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Switch Theme").setSingleChoiceItems(themes, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item)
                        {
                            case 0:
                                getSharedPreferences("THEME", MODE_PRIVATE).edit().putString("THEME", "LIGHT").commit();
                                setTheme(R.style.LightTheme);
                                recreate();
                                break;
                            case 1:
                                getSharedPreferences("THEME", MODE_PRIVATE).edit().putString("THEME", "DARK").commit();
                                setTheme(R.style.DarkTheme);
                                recreate();
                                break;

                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.action_changeSort:
                final String[] sortBy = {"Skill-Value ascending", "Skil-Value descending", "Skillname ascending", "Skillname descending"};
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Switch Theme").setSingleChoiceItems(sortBy, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item)
                        {
                            case 0:
                                //Sort by Value asc
                                break;
                            case 1:
                                //Sort by Value desc
                                break;
                            case 2:
                                //Sort by name asc
                                break;
                            case 3:
                                //Sort by name desc
                                break;
                        }
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When returning to the activity, update the TileAdapter.
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.refresh();
    }

    /**
     * Logout the current User when he pressed on the logout menu-button.
     */
    public void logout() {
        ProgressDialog pd = new ProgressDialog(this);
        try {
            pd = ProgressDialog.show(this, "Please Wait", "Log out in progress.", false);
            UserLogoutTask mLogOutTask = new UserLogoutTask(this);
            mLogOutTask.execute((Void) null);
            if (mLogOutTask.get()) {
                BackendLink.clearAllKeys(this);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                } else {
                    startActivity(intent);
                }
                pd.dismiss();
                this.finish();
            } else {
                pd.dismiss();
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage(mLogOutTask.getError()).show();
            }
        } catch (Exception e) {
            pd.dismiss();
            e.printStackTrace();
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage(e.getMessage()).show();
        }
    }

    /**
     * Try to add a new Skill a User wants to add.
     *
     * @param view
     */
    public void attemptAdd(View view) {
        //Make new Dialog
        final Context context = view.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        final View popupView = inflater.inflate(R.layout.popup_add, null);
        builder.setView(popupView);
        builder.setTitle("Add a Skill");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean correctInput = true;
                EditText mSkillName = (EditText) popupView.findViewById(R.id.add_skill_name);
                mSkillName.setError(null);
                String skillName = mSkillName.getText().toString();
                if (TextUtils.isEmpty(skillName)) {
                    mSkillName.setError("A Skillname is required");
                    correctInput = false;
                }
                if (correctInput) {
                    String result = adapter.addSkill(skillName);
                    if (result.equals("")) {
                        Toast.makeText(context, "Skill " + skillName + " was added!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        mSkillName.setError(result);
                    }
                }
            }
        });
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
     * Fetches new User-Data from Backend.
     */
    public class UserRefreshTask extends AsyncTask<Void, Void, Boolean> {
        private String error;


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                BackendLink.updateMatrixDTO(prefs);
                BackendLink.updateEmployeeProfileMap(prefs);
                BackendLink.updateUserDTO(prefs);
                return true;
            } catch (Exception e) {
                error = e.getMessage();
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try {
                if (aBoolean) {
                    adapter.refresh();
                } else {
                    createNewSimpleAlert("Error", error);
                }
            } catch (Exception e) {
                e.printStackTrace();
                createNewSimpleAlert("Error", e.getMessage());
            }
            refreshLayout.setRefreshing(false);
        }
    }
}
