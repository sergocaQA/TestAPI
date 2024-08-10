package Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomeWorkTest extends BaseTestCase {

    String cookie;

    @Test
    public void testHomeWorkEx11() {
        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        this.cookie = getCookie(response, "HomeWork");

        Assertions.assertEquals("hw_value", cookie, "Response cookie value is not equal to expected value");


    }
}
