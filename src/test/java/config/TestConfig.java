package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

import static config.Configuration.getToken;
import static org.hamcrest.Matchers.lessThan;

public class TestConfig {

    protected static RequestSpecification videoGame_requestSpec;
    protected static RequestSpecification football_requestSpec;

    @BeforeClass
    public static void setup() {

        videoGame_requestSpec = new RequestSpecBuilder().
                setProxy(8080).
                setBaseUri("http://localhost").
                setBasePath("app").
                addHeader("Content-Type", "application/json").
                addHeader("Accept", "application/json").
                build();

        football_requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.football-data.org").
                setBasePath("v1").
                addHeader("X-Auth-Token", getToken()).
                addHeader("X-Response-Control", "minified").
                build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectResponseTime(lessThan(3000L)).
                expectStatusCode(200).
                build();

    }
}
