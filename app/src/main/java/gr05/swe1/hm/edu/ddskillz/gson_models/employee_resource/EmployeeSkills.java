
package gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.SkillLevelDTO;

public class EmployeeSkills {

    @Expose
    private EmployeeDTO employeeDTO;
    @Expose
    private List<SkillLevelDTO> skills = new ArrayList<SkillLevelDTO>();
    @Expose
    private String message;
    @Expose
    private int httpStatus;

    /**
     * 
     * @return
     *     The employeeDTO
     */
    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    /**
     * 
     * @param employeeDTO
     *     The employeeDTO
     */
    public void setEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public EmployeeSkills withEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
        return this;
    }

    /**
     * 
     * @return
     *     The skills
     */
    public List<SkillLevelDTO> getSkills() {
        return skills;
    }

    /**
     * 
     * @param skills
     *     The skills
     */
    public void setSkills(List<SkillLevelDTO> skills) {
        this.skills = skills;
    }

    public EmployeeSkills withSkills(List<SkillLevelDTO> skills) {
        this.skills = skills;
        return this;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public EmployeeSkills withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 
     * @return
     *     The httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * 
     * @param httpStatus
     *     The httpStatus
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public EmployeeSkills withHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
