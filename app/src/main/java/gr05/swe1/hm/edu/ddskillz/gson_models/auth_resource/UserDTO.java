package gr05.swe1.hm.edu.ddskillz.gson_models.auth_resource;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class UserDTO {

    @Expose
    private String username;
    @Expose
    private String email;
    @Expose
    private List<String> roles = new ArrayList<String>();
    @Expose
    private String message;
    @Expose
    private int httpStatus;

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     * The roles
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     *
     * @param httpStatus
     * The httpStatus
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

}
