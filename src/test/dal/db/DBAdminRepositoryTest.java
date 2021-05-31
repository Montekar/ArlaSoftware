package dal.db;

import be.users.Admin;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

class DBAdminRepositoryTest {
    private static Admin admin;
    private static List<Admin> admins = new ArrayList<>();

    private static DBAdminRepository dbAdminRepository = new DBAdminRepository();

    // Retrieves the specific and list of admins for comparison
    @BeforeAll
    @DisplayName("Setting up")
    static void setUp() {
        admins = dbAdminRepository.getAllAdmins();
        admin = dbAdminRepository.getAdmin(1);
    }

    // Test checks if the retrieved admins are equal
    @DisplayName("Get specific Admin")
    @Test
    void getAdmin() {
        Admin adminActualValue = dbAdminRepository.getAdmin(admin.getId());

        Assertions.assertEquals(admin.getId(), adminActualValue.getId());
    }

    // Test checks if the lists are equals
    @DisplayName("Get All Admins")
    @Test
    void getAllAdmins() {
        List<Admin> adminsActualValue = dbAdminRepository.getAllAdmins();

        Assertions.assertEquals(admins.size(), adminsActualValue.size());
    }
}