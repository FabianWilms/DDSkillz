package gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource;

/**
 * Created by Peter on 26.11.2014.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeDTO;

public class Matrix {

    @Expose
    private EmployeeDTO employeeDTO;
    @Expose
    private List<Object> skillLevels = new ArrayList<Object>();

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
     * @return The skillLevels
     */
    public List<Object> getSkillLevels() {
        return skillLevels;
    }

    /**
     * @param skillLevels The skillLevels
     */
    public void setSkillLevels(List<Object> skillLevels) {
        this.skillLevels = skillLevels;
    }

}