package dal.db;

import bll.helper.HashingHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HashedPasswordTest {

    private final String password = "TestPassword";
    private String expectedPassword ="c3f1e2122a4747da0d02f93252748f443e0fc32d18c843647f9cc9848e920830";

    @DisplayName("Testing hashed password")
    @Test
    void hashingTest(){
        HashingHelper hashingHelper = new HashingHelper();

        Assertions.assertEquals(expectedPassword,hashingHelper.hashPassword(password));
    }
}
