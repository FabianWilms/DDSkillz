import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeDTO;
import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeSkills;
import gr05.swe1.hm.edu.ddskillz.gson_models.skill_resource.SkillLevelDTO;

/**
 * Created by o4 on 10.12.14.
 */
public class EmployeeSkillsTest {
    @Test
    public void testEmployeeSkill() throws Exception {

        EmployeeSkills testEmployeeSkills = new EmployeeSkills();
        testEmployeeSkills.setMessage("Msg");
        testEmployeeSkills.setHttpStatus(4);
        LinkedList<SkillLevelDTO> testList = new LinkedList<SkillLevelDTO>();
        testList.add(new SkillLevelDTO());
        testEmployeeSkills.setSkills(testList);
        EmployeeDTO testEmpl = new EmployeeDTO();
        testEmployeeSkills.setEmployeeDTO(testEmpl);
        assertEquals("Message", "Msg", testEmployeeSkills.getMessage());
        assertEquals("Status", 4, testEmployeeSkills.getHttpStatus());
        assertEquals("Skills", testList, testEmployeeSkills.getSkills());
        assertEquals("Employee", testEmpl, testEmployeeSkills.getEmployeeDTO());
    }
}
