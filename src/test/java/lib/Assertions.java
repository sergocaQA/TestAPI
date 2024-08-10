package lib;

import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.hasKey;

public class Assertions {

    public static void assertJsonByName(Response Response, String name, int expectedValue){
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals( expectedValue, value,"JSON value is not equal to expected value");

    }
    public static void assertResponseTextEquals(Response Response, String expectedText){
        assertEquals(
                expectedText,
                Response.asString(),
                "Response text is not equals to expected text");
    }
    public static void assertResponseCodeEquals(Response Response, int expectedStatusCode){
        assertEquals(
                expectedStatusCode,
                Response.statusCode(),
                "Response status code is not equals to expected status code");
    }
}
