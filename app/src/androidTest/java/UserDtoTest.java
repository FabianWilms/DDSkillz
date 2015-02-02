import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

import gr05.swe1.hm.edu.ddskillz.gson_models.auth_resource.UserDTO;

/**
 * Created by o4 on 02.12.14.
 */

public class UserDtoTest {
    @Test
    public void testUserDTO() throws Exception {
        UserDTO testUserDTO = new UserDTO();
        testUserDTO.setEmail("test@test.org");
        testUserDTO.setHttpStatus(2);
        testUserDTO.setMessage("Test-Message");
        testUserDTO.setUsername("tester");
        LinkedList<String> testlist = new LinkedList<String>();
        testlist.add("String1");
        testlist.add("String2");
        testUserDTO.setRoles(testlist);
        assertEquals("E-Mail", "test@test.org", testUserDTO.getEmail());
        assertEquals("HTTPStatus", 2, testUserDTO.getHttpStatus());
        assertEquals("Message", "Test-Message", testUserDTO.getMessage());
        assertEquals("Username", "tester", testUserDTO.getUsername());
        assertEquals("Roles", testlist, testUserDTO.getRoles());
    }
}

