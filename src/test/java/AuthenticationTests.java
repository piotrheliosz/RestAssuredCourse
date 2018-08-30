import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class AuthenticationTests {

    @Before
    public void setup(){
        RestAssured.proxy("localhost", 8888);
    }

    @Test
    public void basicPreemptiveAuthTest(){
        given().
                auth().
                preemptive().
                basic("user", "pass").
        when().
                get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void basicChallengedAuthTest(){
        given().
                auth().
                basic("user", "pass");
        when().
                get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void oauth1Test(){
        given().
                auth().
                oauth(  "consumerKey",
                        "consumerSecret",
                        "consumerAccessToken",
                        "secretToken").
        when().
                get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void oauth2Test(){
        given().
                auth().oauth2("accessToken").
        when().
                get("http://localhost:8080/someEndpoint");
    }

    @Test
    public void relaxedHTTPsTest(){
        given().
                relaxedHTTPSValidation().
        when().
                get("http://localhost:8080/someEndpoint");

    }

    @Test
    public void keyStoreTest(){
        given().
                keyStore("path", "pass").
        when().
                get("http://localhost:8080/someEndpoint");
    }
}
