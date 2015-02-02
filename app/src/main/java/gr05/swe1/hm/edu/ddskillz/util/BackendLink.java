package gr05.swe1.hm.edu.ddskillz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import gr05.swe1.hm.edu.ddskillz.Exceptions.TokenTimeoutException;
import gr05.swe1.hm.edu.ddskillz.Exceptions.UserException;
import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeDTO;
import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeProfileMap;
import gr05.swe1.hm.edu.ddskillz.gson_models.auth_resource.UserDTO;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.MatrixDTO;

/**
 * Created by Peter on 25.11.2014.
 */
public class BackendLink {

    private SharedPreferences prefs;
    private boolean loginSuccessfull = false;


    public BackendLink(String username, String password, SharedPreferences prefs) throws IOException {
        this.prefs = prefs;
        String url = "http://swe1.cs.hm.edu:28080/skillz/tuinterface/auth/login/";
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        HttpPost post = new HttpPost(url);
        post.setHeader("CONTENT_TYPE", "application/x-www-form-urlencoded; charset=utf-8");
        StringEntity se = new StringEntity("username=" + username + "&hashpass=" + MD5Hasher.hash(password));
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        post.setEntity(se);
        response = client.execute(post);
        //Check the response and save the received token in the app.
        if (response != null) {
            InputStream in = response.getEntity().getContent(); //Get the data in the entity
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            in.close();

            if (result.contains("\"httpStatus\":200")) {
                String token = result.split(",")[0].split(":")[1];
                String loginToken = token.substring(1, token.length() - 1);

                prefs.edit()
                        .putString(KEYS.TOKEN.s(), loginToken)
                        .putString(KEYS.USER_NAME.s(), username)
                        .apply();

                this.loginSuccessfull = true;

                try {
                    updateEmployeeProfileMap(prefs);
                    updateUserDTO(prefs);
                    updateMatrixDTO(prefs);
                } catch (UserException e) {
                    Log.e("UserException", e.getMessage());
                } catch (TokenTimeoutException e) {
                    Log.e("TokenTimeout", e.getMessage());
                } finally {
                    //loginSuccessfull = false;
                }
            } else {
                this.loginSuccessfull = false;
            }
        }
        prefs.edit().putString(KEYS.LINK.s(), new Gson().toJson(this)).commit();
    }

    public static boolean BackEndLinkRegistration(String username, String password, String mail) throws Exception {
        String baseUrl = "http://swe1.cs.hm.edu:28080/skillz/tuinterface/auth/create/";
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpPost post = new HttpPost(baseUrl);
        post.setHeader("CONTENT_TYPE", "application/x-www-form-urlencoded; charset=utf-8");
        StringEntity se = new StringEntity("username=" + username + "&email=" + mail + "&hashpass=" + MD5Hasher.hash(password));
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        post.setEntity(se);
        HttpResponse response = client.execute(post);
        if (response != null) {
            InputStream in = response.getEntity().getContent(); //Get the data in the entity
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;
            in.close();
            if (result.contains("already")) {
                throw new Exception(result);
            } else {
                return true;
            }
        }
        return false;
    }

    public static EmployeeProfileMap getEmployeeProfileMap(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
        return new Gson().fromJson(prefs.getString(KEYS.EMPLOYEE_PROFILE_MAP.s(), ""), EmployeeProfileMap.class);
    }

    public static UserDTO getUserDTO(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
        return new Gson().fromJson(prefs.getString(KEYS.USER_DTO.s(), ""), UserDTO.class);
    }

    public static MatrixDTO getMatrixDTO(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
        return new Gson().fromJson(prefs.getString(KEYS.MATRIX_DTO.s(), ""), MatrixDTO.class);
    }

    public static void clearKey(KEYS key, Context context) {
        context.getSharedPreferences(KEYS.FILE_NAME.s(), Context.MODE_PRIVATE).edit().remove(key.s()).commit();
    }

    public static void clearAllKeys(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("loginValues", Context.MODE_PRIVATE).edit();
        for (KEYS next : KEYS.values()) {
            if (next != KEYS.USER_NAME)
                edit.remove(next.s());
        }
        edit.commit();
    }

    // Use this for getting classes

    public boolean wasLoginSuccessfull() {
        return loginSuccessfull;
    }

    public static void updateEmployeeProfileMap(SharedPreferences prefs) throws IOException, UserException, TokenTimeoutException {
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/employee/" + prefs.getString(KEYS.USER_NAME.s(),"").substring(0,3).toUpperCase() + "?tokenValue=" + prefs.getString(KEYS.TOKEN.s(), ""));
        String json = new Scanner(url.openStream()).useDelimiter("\\A").next();
        EmployeeProfileMap profile = new Gson().fromJson(json, EmployeeProfileMap.class);
        if (profile.getHttpStatus() == 200)
            prefs.edit().putString(KEYS.EMPLOYEE_PROFILE_MAP.s(), json).commit();
            //else if (profile.getHttpStatus() == 403) User has no role
        else if (profile.getHttpStatus() == 404)
            throw new UserException("Fehler 404 - updateProfileMap");
        else if (profile.getHttpStatus() == 408)
            throw new TokenTimeoutException("Fehler 408 - updateProfileMap");
    }

    public static void updateUserDTO(SharedPreferences prefs) throws IOException, UserException, TokenTimeoutException {
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/auth/userprofile/" + prefs.getString(KEYS.USER_NAME.s(), "") + "?tokenValue=" + prefs.getString(KEYS.TOKEN.s(), ""));
        String json = new Scanner(url.openStream()).useDelimiter("\\A").next();
        UserDTO userProfile = new Gson().fromJson(json, UserDTO.class);
        if (userProfile.getHttpStatus() == 200)
            prefs.edit().putString(KEYS.USER_DTO.s(), json).commit();
            // else if (userProfile.getHttpStatus() == 403) User has no role
        else if (userProfile.getHttpStatus() == 404)
            throw new UserException("Fehler 404 - updateUserDTO");
        else if (userProfile.getHttpStatus() == 408)
            throw new TokenTimeoutException("Fehler 408 - updateUserDTO");
    }

    public static void updateMatrixDTO(SharedPreferences prefs) throws IOException, TokenTimeoutException, UserException {
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/skill/matrix/" + prefs.getString(KEYS.USER_NAME.s(), "") + "?tokenValue=" + prefs.getString(KEYS.TOKEN.s(), ""));
        String json = new Scanner(url.openStream()).useDelimiter("\\A").next();
        MatrixDTO matrix = new Gson().fromJson(json, MatrixDTO.class);
        if (matrix.getHttpStatus() == 200)
            prefs.edit().putString(KEYS.MATRIX_DTO.s(), json).apply();
        else if (matrix.getHttpStatus() == 404)
            throw new UserException("Fehler 404 - updateMatrixDTO");
        else if (matrix.getHttpStatus() == 408)
            throw new TokenTimeoutException("Fehler 408 - updateMatrixDTO");
    }

    public static MatrixDTO getMatrixDTOWithSkill(SharedPreferences prefs, String skillName) throws IOException, TokenTimeoutException, UserException {
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/skill/matrix/" + prefs.getString(KEYS.USER_NAME.s(), "") + "?filter=" + skillName + "&tokenValue=" + prefs.getString(KEYS.TOKEN.s(), ""));
        String json = new Scanner(url.openStream()).useDelimiter("\\A").next();
        MatrixDTO matrix = new Gson().fromJson(json, MatrixDTO.class);
        if (matrix.getHttpStatus() == 200)
            return matrix;
        else if (matrix.getHttpStatus() == 404)
            throw new UserException("Fehler 404 - updateMatrixDTO");
        else if (matrix.getHttpStatus() == 408)
            throw new TokenTimeoutException("Fehler 408 - updateMatrixDTO");
        return null;
    }

    public static UserDTO getUserDTOFrom(SharedPreferences prefs, String username) throws IOException, UserException, TokenTimeoutException {
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/auth/userprofile/" + username + "?tokenValue=" + prefs.getString(KEYS.TOKEN.s(), ""));
        String json = new Scanner(url.openStream()).useDelimiter("\\A").next();
        UserDTO userProfile = new Gson().fromJson(json, UserDTO.class);
        if (userProfile.getHttpStatus() == 200)
            return userProfile;
        else if (userProfile.getHttpStatus() == 403)
            throw new UserException("User has no role");
        else if (userProfile.getHttpStatus() == 404)
            throw new UserException("Fehler 404 - User or Token not found");
        else if (userProfile.getHttpStatus() == 408)
            throw new TokenTimeoutException("Fehler 408 - updateUserDTO");
        return null;
    }

    public static boolean  addSkill(SharedPreferences prefs, String skillName, int skillValue) throws Exception{
        String url = "http://swe1.cs.hm.edu:28080/skillz/tuinterface/skill/employeeskill?tokenValue=" + prefs.getString(KEYS.TOKEN.s(), "");
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpPost post = new HttpPost(url);
        post.setHeader("CONTENT_TYPE", "application/x-www-form-urlencoded; charset=utf-8");
        StringEntity se = new StringEntity("shortName=" + prefs.getString(KEYS.USER_NAME.s(), "").substring(0,3) + "&skillName=" + skillName + "&skillValue=" + skillValue);
        se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=utf-8"));
        post.setEntity(se);
        HttpResponse response = client.execute(post);
        if (response != null) {
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            } else{
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                String result = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;
                in.close();
                Log.e("BackendLink|addSkill", prefs.getString(KEYS.USER_NAME.s().substring(0,3) , "") + "-" + skillName + "-" + skillValue + "-" + url);
                throw new Exception(result);
            }
        }
        return false;
    }

    public static boolean logout(Context context) throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(KEYS.FILE_NAME.s(), Context.MODE_PRIVATE);
        URL url = new URL("http://swe1.cs.hm.edu:28080/skillz/tuinterface/auth/logout?tokenValue=" + prefs.getString(KEYS.TOKEN.s(),""));
        HttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost();
        request.setURI(url.toURI());
        HttpResponse response = client.execute(request);
        return (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
    }

    public enum KEYS {
        TOKEN("token"),
        USER_NAME("userName"),
        EMPLOYEE_PROFILE_MAP("employeeProfileDTO"),
        USER_DTO("userDTO"),
        LINK("link"),
        MATRIX_DTO("matrixDTO"),
        FILE_NAME("loginValues");
        private final String key;

        private KEYS(String s) {
            key = s;
        }

        public String s() {
            return key;
        }
    }
}
