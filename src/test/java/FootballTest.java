import config.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;


import java.awt.*;
import java.util.List;

import static config.EndPoint.SINGLE_TEAM_NAME;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;

public class FootballTest extends TestConfig {

    @Test
    public void getAllCompetitionsOneSeason() {
        given().
                spec(football_requestSpec).
                log().
                all().
                queryParam("season", 2016).
        when().
                get("competitions").
        then().
                log().
                all();
    }

    @Test
    public void getTeamCount_OneComp(){
        given().
                spec(football_requestSpec).
        when().
                get("competitions/426/teams").
        then().
                body("count", equalTo(20)).
                log().
                all();
    }

    @Test
    public void getFirstTeamName(){
        given().
                spec(football_requestSpec).
        when().
                get("competitions/426/teams").
        then().
                body("teams.name[0]", equalTo("Hull City FC")).
                log().
                all();
    }

    @Test
    public void getAllTeamData(){
        String responseBody =
                given().
                        spec(football_requestSpec).
                when().
                        get("competitions/426/teams").
                        asString();

        System.out.println(responseBody);
    }

    @Test
    public void getAllTeamData_DoCheckFirst(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        log().
                        everything().
                        contentType(ContentType.JSON).
                        extract().
                        response();

        System.out.println(response.asString());
    }

    @Test
    public void extractHeaders(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().
                        response();

        Headers headers = response.getHeaders();

        String contentType = response.getHeader("Content-Type");

        assertEquals("application/json;charset=UTF-8", contentType);
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName =
                given().
                        spec(football_requestSpec).
                when().
                        get(SINGLE_TEAM_NAME).
                        jsonPath().
                        getString("teams.name[0]");

        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamName(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().
                        response();

        List<String> teamsNames = response.path("teams.name");

        for (String teamName : teamsNames){
            System.out.println(teamsNames.indexOf(teamName) + 1 + ". " + teamName);
        }
    }

}