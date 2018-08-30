package config;

public interface EndPoint {

    static String VIDEOGAME = "videogames";
    static String SINGLE_VIDEOGAME = VIDEOGAME + "/{videoGameId}";
    static String SINGLE_TEAM_NAME = "competitions/426/teams";

    static String COMPETITIONS = "competitions/426/teams";
}
