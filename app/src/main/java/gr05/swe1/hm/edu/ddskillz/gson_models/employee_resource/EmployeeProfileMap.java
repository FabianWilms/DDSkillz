
package gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.SkillLevelDTO;

public class EmployeeProfileMap {

    @Expose
    private EmployeeDTO employeeDTO;
    @Expose
    private List<SkillLevelDTO> skills = new ArrayList<SkillLevelDTO>();
    @Expose
    private String message;
    @Expose
    private int httpStatus;
    @Expose
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The employeeDTO
     */
    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    /**
     * @param employeeDTO The employeeDTO
     */
    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    /**
     * @return The skills
     */
    public List<SkillLevelDTO> getSkills() {
        return skills;
    }

    /**
     * @param skills The skills
     */
    public void setSkills(List<SkillLevelDTO> skills) {
        this.skills = skills;
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
