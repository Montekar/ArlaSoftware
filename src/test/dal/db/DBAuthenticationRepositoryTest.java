package dal.db;

import be.users.Admin;
import be.users.Department;
import be.users.User;
import org.junit.jupiter.api.*;

class DBAuthenticationRepositoryTest {
    private DBAuthenticationRepository dbAuthenticationRepository = new DBAuthenticationRepository();
    private final static String adminUsername = "AdminTest";
    private final static String adminPassword = "admin123";
    private static int adminID;

    private final static String departmentUsername = "DepartmentTest";
    private final static String departmentPassword = "department123";
    private final static int departmentRefresh = 140;
    private final static boolean departmentIsResizable = true;
    private static int departmentID;

    private static DBAdminRepository dbAdminRepository = new DBAdminRepository();
    private static DBDepartmentRepository dbDepartmentRepository = new DBDepartmentRepository();

    // Sets up the test data for admin and department
    @BeforeAll
    public static void setUp() {
        adminID = dbAdminRepository.createAdmin(adminUsername,adminPassword);
        departmentID = dbDepartmentRepository.createDepartment(departmentUsername,departmentPassword,departmentRefresh,departmentIsResizable);
    }

    // Cleans up the test data
    @AfterAll
    public static void tearDown() {
        dbAdminRepository.deleteAdmin(adminID);
        dbDepartmentRepository.deleteDepartment(departmentID);
    }

    // Test checks if the admin can be authenticated
    @DisplayName("Admin Authentication")
    @Test
    void authenticateAdmin() {
        User user = dbAuthenticationRepository.authenticateAdmin(adminUsername,adminPassword);
        Assertions.assertTrue(user instanceof Admin);
    }

    // Test checks if the department can be authenticated
    @DisplayName("Department Authentication")
    @Test
    void authenticateDepartment() {
        User user = dbAuthenticationRepository.authenticateDepartment(departmentUsername,departmentPassword);
        Assertions.assertTrue(user instanceof Department);
    }
}