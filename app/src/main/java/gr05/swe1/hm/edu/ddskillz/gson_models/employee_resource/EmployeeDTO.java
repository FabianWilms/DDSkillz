
package gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDTO {

    @Expose
    private String name;
    @Expose
    private String abreviation;
    @Expose
    private long employementDate;
    @Expose
    private String appData;
    @Expose
    private List<Object> departmentsList = new ArrayList<Object>();
    @Expose
    private String message;
    @Expose
    private int httpStatus;
    @Expose
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The abreviation
     */
    public String getAbreviation() {
        return abreviation;
    }

    /**
     * @param abreviation The abreviation
     */
    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    /**
     * @return The employementDate
     */
    public long getEmployementDate() {
        return employementDate;
    }

    /**
     * @param employementDate The employementDate
     */
    public void setEmployementDate(int employementDate) {
        this.employementDate = employementDate;
    }

    /**
     * @return The appData
     */
    public String getAppData() {
        return appData;
    }

    /**
     * @param appData The appData
     */
    public void setAppData(String appData) {
        this.appData = appData;
    }

    /**
     * @return The departmentsList
     */
    public List<Object> getDepartmentsList() {
        return departmentsList;
    }

    /**
     * @param departmentsList The departmentsList
     */
    public void setDepartmentsList(List<Object> departmentsList) {
        this.departmentsList = departmentsList;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus The httpStatus
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
