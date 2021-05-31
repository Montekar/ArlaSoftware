package dal.db;

import be.users.Department;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBDepartmentRepositoryTest {
    private final String departmentName = "DepartmentTest";
    private final int refreshTime = 5;
    private final boolean isAutoResizable = true;
    static int departmentID;

    private static List<Department> departmentList;

    private static DBDepartmentRepository dbDepartmentRepository = new DBDepartmentRepository();

    // Sets up the testing and retrieves list of all departments
    @BeforeAll
    static void setUp() {
        departmentList = dbDepartmentRepository.getAllDepartments();
    }

    // Testing if the all departments get retrieved
    @DisplayName("Get all departments")
    @Test
    @Order(1)
    void getAllDepartments() {
        Assertions.assertEquals(departmentList.size(), dbDepartmentRepository.getAllDepartments().size());
    }

    // Testing the possibility to create a department
    @DisplayName("Create a department")
    @Test
    @Order(2)
    void createDepartment() {
        departmentID = dbDepartmentRepository.createDepartment(departmentName,"password",refreshTime,isAutoResizable);
        Assertions.assertTrue(departmentID!=-1);
    }

    // Testing if the department can be retrieved
    @DisplayName("Get department")
    @Test
    @Order(3)
    void getDepartment() {
        Department expectedDepartment = dbDepartmentRepository.getDepartment(departmentID);

        Assertions.assertEquals(departmentName,expectedDepartment.getUsername());
        Assertions.assertEquals(departmentID,expectedDepartment.getId());

    }

    // Test checking the possibility to edit the department
    @DisplayName("Edit the department")
    @Test
    @Order(4)
    void editDepartment() {
        Assertions.assertTrue(dbDepartmentRepository.editDepartment(departmentID,"New Username 1400 Test",refreshTime,false));
    }

    // Test that checks if the refresh time is the same as the one inserted to database
    @DisplayName("Retrieve refresh time")
    @Test
    @Order(5)
    void getRefreshTime() {
        Assertions.assertEquals(refreshTime, dbDepartmentRepository.getRefreshTime(departmentID));
    }

    // Test that checks if the department can be deleted
    @DisplayName("Deletion of department")
    @Test
    @Order(6)
    void deleteDepartment() {
        Assertions.assertTrue(dbDepartmentRepository.deleteDepartment(departmentID));
    }
}