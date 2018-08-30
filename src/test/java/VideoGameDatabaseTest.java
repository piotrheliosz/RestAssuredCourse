import config.TestConfig;
import org.junit.Test;

import static config.EndPoint.SINGLE_VIDEOGAME;
import static config.EndPoint.VIDEOGAME;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameDatabaseTest extends TestConfig {

    @Test
    public void getAllGames() {
        given().
                spec(videoGame_requestSpec).
        when().
                get(VIDEOGAME).
        then();
    }

    @Test
    public void createNewGameByJSON() {
        String gameBodyJSON =
                "{\n" +
                "  \"id\": 999,\n" +
                "  \"name\": \"dupa\",\n" +
                "  \"releaseDate\": \"2018-07-18T10:09:23.293Z\",\n" +
                "  \"reviewScore\": 0,\n" +
                "  \"category\": \"dupiata\",\n" +
                "  \"rating\": \"zdupiony\"\n" +
                "}";

        given().
                spec(videoGame_requestSpec).
                body(gameBodyJSON).
        when().
                post(VIDEOGAME).
        then();
    }

    @Test
    public void createNewGameByXML() {
        String gameBodyXml =
                        "<videoGame category=\"Shooter\" rating=\"Universal\">\n" +
                        "    <id>12</id>\n" +
                        "    <name>Resident Evil 9</name>\n" +
                        "    <releaseDate>2005-10-01T00:00:00+02:00</releaseDate>\n" +
                        "    <reviewScore>101</reviewScore>\n" +
                        "  </videoGame>";

        given().
                spec(videoGame_requestSpec).
                body(gameBodyXml).
        when().
                post(VIDEOGAME).
        then();
    }

    @Test
    public void updateGame() {
        String gameBodyJSON = "{\n" +
                "  \"id\": 999,\n" +
                "  \"name\": \"dupa\",\n" +
                "  \"releaseDate\": \"2018-07-18T10:09:23.293Z\",\n" +
                "  \"reviewScore\": 0,\n" +
                "  \"category\": \"dupiata\",\n" +
                "  \"rating\": \"bardzoZdupiony666\"\n" +
                "}";

        given().
                spec(videoGame_requestSpec).
                body(gameBodyJSON).
        when().
                put(VIDEOGAME + "/999").
        then();
    }

    @Test
    public void deleteGame() {
        given().
                spec(videoGame_requestSpec).
        when().
                delete(VIDEOGAME + "/999").
        then();
    }

    @Test
    public void getSingleGame() {
        given().
                spec(videoGame_requestSpec).
                pathParam("videoGameId", 5).
        when().
                get(SINGLE_VIDEOGAME);
    }

    @Test //to repeat
    public void testVideoGameSerialisationByJSON(){
        VideoGame videoGame = new VideoGame("18", "shooter", "2014-06-06", "Halo 5","Mature", "99");

        given().
                spec(videoGame_requestSpec).
                body(videoGame).
                log().
                all().
        when().
                post(VIDEOGAME).
        then();
    }

    @Test
    public void testVideoGameSchemaXML(){
        given().
                spec(videoGame_requestSpec).
                pathParam("videoGameId", 5).
                log().
                all().
        when().
                get(SINGLE_VIDEOGAME).
        then().
                body(matchesXsdInClasspath("VideoGameXmlSchema.xsd")).
                log().
                all();

    }

    @Test
    public void testVideoGameSchemaJSON(){
        given().
                spec(videoGame_requestSpec).
                pathParam("videoGameId", 1).
                log().
                all().
        when().
                get(SINGLE_VIDEOGAME).
        then().
                body(matchesJsonSchemaInClasspath("VideoGameJsonSchema.json")).
                log().
                all();

    }
}