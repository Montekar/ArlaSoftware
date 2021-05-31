package dal.db;

import be.View;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBContentRepositoryTest {
    private final static String departmentUsername = "DepartmentTest";
    private final static String departmentPassword = "department123";
    private final static int departmentRefresh = 140;
    private final static boolean departmentIsResizable = true;
    private static int departmentID;
    private static int contentID;
    private static View contentView;

    private static DBDepartmentRepository dbDepartmentRepository = new DBDepartmentRepository();
    private static DBContentRepository dbContentRepository = new DBContentRepository();

    // Sets up the test
    @BeforeAll
    static void setUp() {
        departmentID = dbDepartmentRepository.createDepartment(departmentUsername, departmentPassword, departmentRefresh, departmentIsResizable);
    }

    @AfterAll
    static void cleanUp() {
        dbDepartmentRepository.deleteDepartment(departmentID);
    }

    @DisplayName("Creating new content")
    @Test
    @Order(1)
    void createContent() {
        contentID = dbContentRepository.createContent(new View(departmentID, 1, 1, 1, 1, "Path", "Title"));
        Assertions.assertTrue(contentID != -1);
        contentView = new View(departmentID, 1, 1, 1, 1, "Path", "Title");
    }

    @DisplayName("Retrieving the content")
    @Test
    @Order(2)
    void getContent() {
        Assertions.assertEquals(departmentID, dbContentRepository.getContent(departmentID).get(0).getId());
    }

    @DisplayName("Editing content")
    @Test
    @Order(3)
    void editContent() {
        Assertions.assertTrue(dbContentRepository.editContent(contentView, new View(departmentID, 2, 2, 2, 2, "Path", "Title")));
        contentView = new View(departmentID, 2, 2, 2, 2, "Path", "Title");
    }

    @DisplayName("Deleting content")
    @Test
    @Order(4)
    void deleteContent() {
        Assertions.assertTrue(dbContentRepository.deleteContent(contentView));
    }
}