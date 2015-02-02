import org.junit.Test;

import java.util.LinkedList;

import gr05.swe1.hm.edu.ddskillz.gson_models.employee_resource.EmployeeDTO;

import static org.junit.Assert.assertEquals;

/**
 * Created by o4 on 10.12.14.
 */
public class EmployeeDtoTest {
    @Test
    public void testEmployeeDto() throws Exception {
        EmployeeDTO testEmployeeDTO = new EmployeeDTO();
        testEmployeeDTO.setMessage("Test-Message");
        testEmployeeDTO.setHttpStatus(2);
        testEmployeeDTO.setAbreviation("Kurz");
        testEmployeeDTO.setAdditionalProperty("Zusatz", 2);
        testEmployeeDTO.setAppData("Daten");
        testEmployeeDTO.setEmployementDate(815);
        testEmployeeDTO.setName("Hans");
        LinkedList<Object> testList = new LinkedList<Object>();
        testList.add("Eintrag 1");
        testList.add("Eintrag 2");
        testEmployeeDTO.setDepartmentsList(testList);
        assertEquals("Message", "Test-Message", testEmployeeDTO.getMessage());
        assertEquals("Http-Status", 2, testEmployeeDTO.getHttpStatus());
        assertEquals("Abbreviation", "Kurz", testEmployeeDTO.getAbreviation());
        assertEquals("AddpropKey", true, testEmployeeDTO.getAdditionalProperties().containsKey("Zusatz"));
        assertEquals("AddpropValue", true, testEmployeeDTO.getAdditionalProperties().containsValue(2));
        assertEquals("AppDate", "Daten", testEmployeeDTO.getAppData());
        assertEquals("EmployDate", 815, testEmployeeDTO.getEmployementDate());
        assertEquals("Name", "Hans", testEmployeeDTO.getName());
        assertEquals("DeptList1", "Eintrag 1", testEmployeeDTO.getDepartmentsList().get(0));
        assertEquals("DeptList2", "Eintrag 2", testEmployeeDTO.getDepartmentsList().get(1));
    }
}
