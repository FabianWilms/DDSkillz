package gr05.swe1.hm.edu.ddskillz.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Date;

import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeProfileMap;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.SkillLevelDTO;
import gr05.swe1.hm.edu.ddskillz.gson_models.auth_resource.UserDTO;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

public class ProfileActivity extends Activity {

    //Textfields supposed to show user information
    private TextView name, abbreviation,mail, employmentDate, roles, skills;

    //GSON Objects with user data
    private UserDTO profile;
    private EmployeeProfileMap employeeSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
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
            setContentView(R.layout.activity_profile);
            getActionBar().setTitle("Profil");

            // initiate textviews
            name = (TextView) findViewById(R.id.userProfileName);
            abbreviation = (TextView) findViewById(R.id.userProfileAbbreviation);
            mail = (TextView) findViewById(R.id.userProfileMail);
            employmentDate = (TextView) findViewById(R.id.userProfileEmploymentDate);
            roles = (TextView) findViewById(R.id.userProfileRoles);

            // get information from backend
            profile = BackendLink.getUserDTO(this);
            employeeSkills = BackendLink.getEmployeeProfileMap(this);

            // set Textviews to show information
            name.setText(profile != null ? profile.getUsername() : "Kein Name gefunden");
            abbreviation.setText(employeeSkills != null ? employeeSkills.getEmployeeDTO().getAbreviation() : "Kein Kürzel gefunden");
            mail.setText(profile != null ? profile.getEmail() : "Keine Mail gefunden");
            employmentDate.setText(employeeSkills != null ? new Date(employeeSkills.getEmployeeDTO().getEmployementDate()).toString() : "Kein Datum gefunden");

            if (profile == null) {
                roles.setText("-");
            } else {
                String r = "";
                for (String s : profile.getRoles())
                    r += s + "\r\n";
                if (r.length() <= 0)
                    r = "-";
                else
                    r=r.substring(0,r.length()-2);
                roles.setText(r);
            }
        }catch(Exception e){
            Log.e("ProfileActivity-OnCreate",e.getStackTrace().toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
