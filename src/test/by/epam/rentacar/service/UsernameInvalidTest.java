package by.epam.rentacar.service;

import by.epam.rentacar.service.validation.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class UsernameInvalidTest {

    private String username;

    public UsernameInvalidTest(String username) {
        this.username = username;
    }

    @Parameterized.Parameters
    public static List<String> usernamesForTest() {
        return Arrays.asList(
                "user name",
                "u",
                "user&name"
        );
    }

    @Test
    public void testUsernames() {
        Assert.assertFalse(Validator.isUsernameValid(username));
    }


}
