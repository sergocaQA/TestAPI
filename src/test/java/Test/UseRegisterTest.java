package Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UseRegisterTest  extends BaseTestCase {
    @Test
    public void testCreateUserWithExistingEmail(){
        String email = "vinkotov@example.com";

        Map<String, String > userData = new HashMap<>();
        userData.put("email",email);
        userData.put("username","defaultUsername");
        userData.put("firstName","defaultFirstName");
        userData.put("lastName","defaultLastName");
        userData.put("password","defaultPassword");

        Response responseCreateUser = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateUser, "Users with email '" + email + "' already exists");
        Assertions.assertResponseCodeEquals(responseCreateUser, 400);
    }
}
