package gr05.swe1.hm.edu.ddskillz.activities;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr05.swe1.hm.edu.ddskillz.R;
import gr05.swe1.hm.edu.ddskillz.asyncTasks.UserAddSkillTask;
import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeProfileMap;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.Matrix;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.MatrixDTO;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.SkillLevelDTO;
import gr05.swe1.hm.edu.ddskillz.util.BackendLink;

/**
 * Created by Peter on 18.11.2014.
 */
public class TileAdapter extends RecyclerView.Adapter<TileAdapter.ViewHolder> {
    private SharedPreferences prefs;
    private Context context;
    private EmployeeProfileMap abr;
    private Map<String, Integer> pics = new HashMap<String, Integer>();
    private Map<String, Palette.Swatch> colores = new HashMap<String, Palette.Swatch>();
    private int[] lvlColors = new int[]{0xb3F44336, 0xb3FF9800, 0xb3FFC107, 0xb3FFEB3B, 0xb3CDDC39, 0xb34CAF50};

    public TileAdapter(Context context) {
        this();
        prefs = context.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
        this.context = context;
        abr = BackendLink.getEmployeeProfileMap(context);
        sortCollection();
    }

    public TileAdapter() {
        pics.put("German", R.drawable.skill_german);
        pics.put("UML", R.drawable.skill_uml);
        pics.put("Maven", R.drawable.skill_maven);
        pics.put("Wicket", R.drawable.skill_wicket);
        pics.put("Vaadin", R.drawable.skill_vaadin);
        pics.put("JDBC", R.drawable.skill_jdbc);
        pics.put("Generics", R.drawable.skill_generics);
        pics.put("Debugging", R.drawable.skill_debugging);
        pics.put("ANT", R.drawable.skill_ant);
        pics.put("Gradle", R.drawable.skill_gradle);
        pics.put("OOP", R.drawable.skill_oop);
        pics.put("HTML", R.drawable.skill_html);
        pics.put("Javascript", R.drawable.skill_javascript);
        pics.put("CSS", R.drawable.skill_css);
        pics.put("HTML 5", R.drawable.skill_html);
        pics.put("Flash/ActionScript", R.drawable.skill_actionscript);
        pics.put("Flex/FlashBuilder", R.drawable.skill_flashbuilder);
        pics.put("JUnit", R.drawable.skill_junit);
        pics.put("AspectJ", R.drawable.skill_aspectj);
        pics.put("Swing", R.drawable.skill_java);
        pics.put("Kochen", R.drawable.skill_kochen);
        pics.put("NamenTanzen", R.drawable.skill_namentanzen);
        pics.put("Regex", R.drawable.skill_regex);
        pics.put("LINUX", R.drawable.skill_linux);
        pics.put("Logic", R.drawable.skill_logik);
        pics.put("Networks", R.drawable.skill_network);
        pics.put("Commerce Trail", R.drawable.skill_commerce);
        pics.put("C++", R.drawable.skill_cplusplus);
        pics.put("English", R.drawable.skill_englisch);
        pics.put("Java IO", R.drawable.skill_java);
        pics.put("C", R.drawable.skill_c);
        pics.put("Java", R.drawable.skill_java);
        pics.put("Patterns", R.drawable.skill_pattern);
        pics.put("Si-Fi", R.drawable.skill_sifi);
        pics.put("Lang", R.drawable.skill_lang);

        pics.put("blank", R.drawable.skill_blank);
    }


    @Override
    public TileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        CardView v = (CardView) LayoutInflater.from(context).inflate(R.layout.main_tile, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        abr = BackendLink.getEmployeeProfileMap(context);
        sortCollection();
        final SkillLevelDTO skill = abr.getSkills().get(position);
        final String skillName = skill.getSkillName();
        vh.title.setText(skillName);
        vh.lvl.setText(skill.getSkillValue() + "");
        vh.lvl.setBackgroundColor(lvlColors[Math.min(skill.getSkillValue(), 5)]);
        vh.star.setBackgroundColor(lvlColors[Math.min(skill.getSkillValue(), 5)]);

        if (pics.containsKey(skillName)) {
            int image = pics.get(skillName);

            vh.skillImage.setImageResource(image);

            if (colores.containsKey(skillName)) {
                vh.title.setBackgroundColor(colores.get(skillName).getRgb());
                vh.skillImage.setColorFilter(0x5f000000 + colores.get(skillName).getRgb());
                vh.title.setTextColor(colores.get(skillName).getTitleTextColor());
            } else {
                Palette.generateAsync(((BitmapDrawable) vh.skillImage.getDrawable()).getBitmap(), new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        if (swatch != null) {
                            ValueAnimator va = ObjectAnimator.ofInt(vh.title, "backgroundColor", 0xffffffff, swatch.getRgb());
                            va.setDuration(500);
                            va.setEvaluator(new ArgbEvaluator());
                            va.start();

                            vh.skillImage.setColorFilter(0x5f000000 + swatch.getRgb());
                            vh.animator = va;
                            vh.title.setTextColor(swatch.getTitleTextColor());
                            colores.put(skillName, swatch);
                        }
                    }
                });

            }
        } else {
            if (vh.animator != null)
                vh.animator.cancel();
            vh.skillImage.setColorFilter(null);
            vh.skillImage.setImageResource(pics.get("blank"));
            if(context.getSharedPreferences("THEME", Context.MODE_PRIVATE).getString("THEME", "LIGHT").equals("LIGHT")){
                vh.title.setBackgroundColor(context.getResources().getColor(R.color.lighttheme_primary_dark));
            }else{
                vh.title.setBackgroundColor(context.getResources().getColor(R.color.darktheme_primary_dark));
            }
            vh.title.setTextColor(0xfff1f1f1);
        }
        vh.skillImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra(QuizActivity.SKILL_NAME, skillName);
                intent.putExtra(QuizActivity.SKILL_LVL, skill.getSkillValue());
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(((MainActivity) context)).toBundle());
            }
        });
        vh.skillImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Do you really want to reset this Skill?").
                        setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                resetSkill(vh.title.getText().toString());
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            }

        });
        vh.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptGetBestEmployeesForSkill(skillName);
            }
        });
        vh.star.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                String contentDesc = "Show Experts for " + skillName;
                int[] pos = new int[2];
                vh.star.getLocationOnScreen(pos);

                Toast t = Toast.makeText(context, contentDesc, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP | Gravity.LEFT, pos[0] - ((contentDesc.length() / 2) * 12), pos[1] - 128);
                t.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return abr == null ? 0 : abr.getSkills().size();
    }

    /**
     * Try to get the best Employees for the given skill.
     * @param skillName Name of the Skill experts are searched for
     */
    public void attemptGetBestEmployeesForSkill(String skillName) {
        UserGetBestEmployeesTask task = new UserGetBestEmployeesTask(skillName);
        task.execute((Void) null);
    }

    /**
     * Resets the given skill to skill-level 0.
     * @param skillName Skill to reset
     */
    public void resetSkill(String skillName) {
        UserResetSkillTask task = new UserResetSkillTask(skillName);
        task.execute();
    }

    /**
     * Add a new Skill to this user by sending a request to the backend.
     * @param skillName Skill to add
     * @return Returning a String to determine if a error happened while trying to add a skill
     */
    public String addSkill(String skillName) {
        UserAddSkillTask addSkillTask = new UserAddSkillTask(prefs, skillName, 0);
        addSkillTask.execute((Void) null);
        try {
            if (addSkillTask.get()) {
                refresh();
                return "";
            } else {
                return addSkillTask.getError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Refresh the data-set to refresh the view when data was changed.
     */
    public void refresh() {
        abr = BackendLink.getEmployeeProfileMap(context);
        sortCollection();
        notifyDataSetChanged();
    }

    /**
     * Sorts the Skills by its Skill-Level
     */
    public void sortCollection() {
        if (abr != null) {
            Collections.sort(abr.getSkills(), new Comparator<SkillLevelDTO>() {
                @Override
                public int compare(SkillLevelDTO lhs, SkillLevelDTO rhs) {
                    return rhs.getSkillValue() - lhs.getSkillValue();
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView star;
        public ImageView skillImage;
        public Animator animator;
        public TextView lvl;
        public CardView layout;

        public ViewHolder(CardView itemView) {
            super(itemView);
            layout = itemView;
            title = (TextView) itemView.findViewById(R.id.tileTitle);
            star = (ImageView) itemView.findViewById(R.id.star);
            skillImage = (ImageView) itemView.findViewById(R.id.skillImage);
            lvl = (TextView) itemView.findViewById(R.id.level);

        }
    }

    /**
     * Task that fetches all needed Data from the backend and wraps the infos
     * into a TableView-Popup. If a User has a mail-adress added to his profile
     * a click on this users entry will open the email-application
     */
    public class UserGetBestEmployeesTask extends AsyncTask<Void, Void, Boolean> {

        String skillName;
        List<Pair<String, Double>> bestEmployeesList;
        Map<String, String> mailMap;
        String error;
        ProgressDialog dialog;

        public UserGetBestEmployeesTask(String skillName) {
            this.skillName = skillName;
            bestEmployeesList = new ArrayList<Pair<String, Double>>();
            mailMap = new HashMap<String, String>();
            dialog = ProgressDialog.show(context, "Fetching Users",
                    "Fetching Users from Backend", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                SharedPreferences prefs = context.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
                dialog.show();
                MatrixDTO matrix = BackendLink.getMatrixDTOWithSkill(prefs, skillName);
                for (Matrix m : matrix.getMatrix()) {
                    if (m.getSkillLevels().size() > 0) {
                        Pair<String, Double> employee = new Pair<String, Double>(m.getEmployeeDTO().getName(), (Double) ((LinkedTreeMap) m.getSkillLevels().get(0)).get("skillValue"));
                        bestEmployeesList.add(employee);
                        try {
                            mailMap.put(employee.first, BackendLink.getUserDTOFrom(prefs, employee.first).getEmail());
                        } catch (Exception e) {
                            mailMap.put(employee.first, "");
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                error = e.getMessage();
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            View parent = View.inflate(context, R.layout.popup_best_employee, null);
            TableLayout tableLayoutPopUp = (TableLayout) parent.findViewById(R.id.tablelayout_popup_best);
            tableLayoutPopUp.setStretchAllColumns(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            Collections.sort(bestEmployeesList, new Comparator<Pair<String, Double>>() {
                @Override
                public int compare(Pair<String, Double> lhs, Pair<String, Double> rhs) {
                    return (int) (rhs.second - lhs.second);
                }
            });
            int count = 1;
            for (Pair<String, Double> next : bestEmployeesList) {
                final String tempName = next.first;
                View v = View.inflate(context, R.layout.popup_best_employee_element, null);
                TextView name = (TextView) v.findViewById(R.id.textViewBestName);
                TextView lvl = (TextView) v.findViewById(R.id.textViewBestSkill);
                name.setText(tempName);
                lvl.setText(Double.toString(next.second));
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mailAddr = mailMap.get(tempName);
                        if (mailAddr.equals("")) {
                            Toast.makeText(context, tempName + " has no Mail-Adress", Toast.LENGTH_SHORT).show();
                        } else {
                            /* Create the Intent */
                            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("plain/text");
                            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{mailAddr});
                            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I need your help with " + skillName);
                            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Dear " + tempName + ",\n");
                            context.startActivity(Intent.createChooser(emailIntent, "Send a Mail to " + tempName));
                        }
                    }
                });
                //Color the best three employees.
                switch (count) {
                    case 1:
                        v.findViewById(R.id.popup_best_element).setBackgroundResource(R.drawable.popup_first_employee);
                        ((TextView) v.findViewById(R.id.textViewBestName)).setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        v.findViewById(R.id.popup_best_element).setBackgroundResource(R.drawable.popup_second_employee);
                        ((TextView) v.findViewById(R.id.textViewBestName)).setTypeface(null, Typeface.BOLD);
                        break;
                    case 3:
                        v.findViewById(R.id.popup_best_element).setBackgroundResource(R.drawable.popup_third_employee);
                        ((TextView) v.findViewById(R.id.textViewBestName)).setTypeface(null, Typeface.BOLD);
                        break;
                }
                count++;
                tableLayoutPopUp.addView(v);
            }

            dialog.dismiss();
            builder.setTitle("Best Employees for " + skillName)
                    .setView(parent)
                    .setNeutralButton("Close", null).show();
        }
    }

    /**
     * Background-Task to reset a given skill.
     */
    public class UserResetSkillTask extends AsyncTask<Void, Void, Boolean> {

        String skillName;
        String error;
        ProgressDialog dialog;

        public UserResetSkillTask(String skillName) {
            this.skillName = skillName;
            dialog = ProgressDialog.show(context, "Resetting Skill",
                    "Resetting your Skill " + skillName, true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                dialog.show();
                SharedPreferences prefs = context.getSharedPreferences(BackendLink.KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
                BackendLink.addSkill(prefs, skillName, 0);
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
        protected void onPostExecute(final Boolean success) {
            if(error != null){
                dialog.dismiss();
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Error").setMessage(error).setNeutralButton("CLose", null).show();
            } else {
                refresh();
                dialog.dismiss();
            }
        }
    }
}