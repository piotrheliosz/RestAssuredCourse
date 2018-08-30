import config.TestConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static config.EndPoint.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

public class GPathJsonTests extends TestConfig {

    @Test //IMPORTANT
    public void extractMapOfElementsWithFind() {
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get(COMPETITIONS);

        Map<String, ?> allTeamDataForSingleTeam = response.path
                ("teams.find { it.shortName == 'Foxes' }");

        System.out.print(allTeamDataForSingleTeam);

        response.then().log().all();
    }

    @Test
    public void extractSingleValueWithFind(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("teams/66/players");

        String certainPlayer =
                response.path("players.find { it.jerseyNumber == 20 }.name");

        System.out.print(certainPlayer);
    }

    @Test
    public void extractListOfValuesWithFind(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("teams/66/players");

        List<String> playersNames =
                response.path("players.findAll { it.nationality == 'England' }.name");

        for(String playerName : playersNames){
            System.out.println(playerName);
        }
    }

    @Test
    public void extractSingleValueWithTheHighestNumber(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("teams/66/players");

        String playerName = response.path
                ("players.max { it.jerseyNumber }.name");

        int playerNumber = response.path
                ("players.find { it.name == '%s' }.jerseyNumber", playerName);

        System.out.println(playerName + " \"" + playerNumber + "\"" );

    }

    @Test
    public void extractMultipleValuesAnsSumThem(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("teams/66/players");

        int sumOfJerseys = response.path("players.collect { it.jerseyNumber }.sum()");

        System.out.println(sumOfJerseys);
    }

    @Test
    public void extractMapOfObjectsWithFindsAndFindAll(){
        Response response =
                given().
                        spec(football_requestSpec).
                when().
                        get("teams/66/players");

        String position = "Centre-Back";
        String nationality = "England";

        Map<String, ?> playersOfCertainPosition = response.path(
                "players.findAll { it.position == '%s' }" +
                        ".find { it.nationality == '%s'}", position, nationality);

        System.out.println(playersOfCertainPosition);
    }

    @Test
    public void extractMultiplePlayers(){
        Response response =
                given().
                        spec(football_requestSpec).
                        when().
                        get("teams/68/players");

        String position = "Centre-Back";
        String nationality = "England";

        ArrayList<Map<String, ?>> allPlayersCertainNation = response.path(
                "players.findAll { it.position == '%s' }" +
                        ".findAll { it.nationality == '%s'}",  position, nationality);

        System.out.println(allPlayersCertainNation);
    }
}