package Test;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseTestCase {

    String cookie;
    String header;
    int userIdOnAuth;

    @BeforeEach
    public void loginUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        this.cookie = getCookie(responseGetAuth, "auth_sid");
        this.header = getHeader(responseGetAuth, "x-csrf-token");
        this.userIdOnAuth = getIntFromJson(responseGetAuth, "user_id");

    }

    @Test
    public void testUserAuth() {
        Response responseCheckAuth = RestAssured
                .given()
                .cookie("auth_sid", this.cookie)
                .header("x-csrf-token", this.header)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.assertJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);

    }

    @ParameterizedTest
    @ValueSource(strings = {"cookies", "headers"})

    public void testNegativeUserAuth(String condition) {
        RequestSpecification spec = RestAssured
                .given()
                .baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookies")) {
            spec.cookies("auth_sid", this.cookie);
        } else if (condition.equals("headers")) {
            spec.header("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Condition value is know: " + condition);
        }
        Response responseForCheck = spec.get().andReturn();
        Assertions.assertJsonByName(responseForCheck, "user_id", 0);
    }
}
