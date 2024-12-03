import exception.ScoreboardException;
import model.Scoreboard;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private Scoreboard scoreboard;
    private final Team TEAM_MEXICO = new Team("Mexico");
    private final Team TEAM_CANADA = new Team("Canada");
    private final Team TEAM_SPAIN = new Team("Spain");
    private final Team TEAM_BRAZIL = new Team("Brazil");
    private final Team TEAM_GERMANY = new Team("Germany");
    private final Team TEAM_FRANCE = new Team("France");

    @BeforeEach
    void setUp() throws ScoreboardException {
        scoreboard = new Scoreboard();
        setUpTeams();
    }

    @Test
    void shouldAddGame() {
        //then
        assertFalse(scoreboard.getGames().isEmpty());
        assertEquals(1, scoreboard.getGames().size());
        assertEquals("Mexico 0 - Canada 0", scoreboard.getGames().get(0).toString());
    }

    @Test
    void shouldRemoveGame() {
        //when
        scoreboard.endGame(TEAM_MEXICO, TEAM_CANADA);

        //then
        assertTrue(scoreboard.getGames().isEmpty());
    }

    @Test
    void shouldUpdateGame() {
        //given
        Team homeTeam = TEAM_MEXICO;
        Team awayTeam = TEAM_CANADA;

        //when
        scoreboard.updateScore(homeTeam, awayTeam, 1, 0);

        //then
        assertEquals("Mexico 1 - Canada 0", scoreboard.getGames().get(0).toString());
    }

    @Test
    void shouldNotAddNegativeScore() {
        //given
        Team homeTeam = TEAM_MEXICO;
        Team awayTeam = TEAM_CANADA;

        //then
        assertEquals("Score cannot be negative", assertThrows(ScoreboardException.class, () -> scoreboard.updateScore(homeTeam, awayTeam, -1, 0)).getMessage());
    }

    @Test
    void shouldNotAddGameWhenTeamIsAreadyPlaying() {
        //given
        Team homeTeam = TEAM_MEXICO;
        Team awayTeam = TEAM_SPAIN;

        //then
        assertEquals("One of the teams is already playing another game", assertThrows(ScoreboardException.class, () -> scoreboard.startGame(homeTeam, awayTeam)).getMessage());
    }

    @Test
    void shouldGiveSpecificGames() throws ScoreboardException {
        //given
        scoreboard.startGame(TEAM_SPAIN, TEAM_BRAZIL);
        scoreboard.startGame(TEAM_GERMANY, TEAM_FRANCE);

        //when
        scoreboard.updateScore(TEAM_MEXICO, TEAM_CANADA, 1, 0);
        scoreboard.updateScore(TEAM_SPAIN, TEAM_BRAZIL, 0, 1);
        scoreboard.updateScore(TEAM_GERMANY, TEAM_FRANCE, 3, 2);

        //then
        assertEquals("Mexico 1 - Canada 0", scoreboard.getGames().get(0).toString());
        assertEquals("Spain 0 - Brazil 1", scoreboard.getGames().get(1).toString());
        assertEquals("Germany 3 - France 2", scoreboard.getGames().get(2).toString());

    }

    @Test
    void shouldGiveTotalSummary() throws InterruptedException, ScoreboardException {
        //given
        scoreboard.startGame(TEAM_GERMANY, TEAM_FRANCE);
        Thread.sleep(1000);
        scoreboard.startGame(TEAM_SPAIN, TEAM_BRAZIL);
        String expectedSummary = "[Spain 3 - Brazil 2\n" + ", Germany 3 - France 2\n" + ", Mexico 1 - Canada 0\n]";

        //when        scoreboard.updateScore(TEAM_MEXICO, TEAM_CANADA, 1, 0);
        scoreboard.updateScore(TEAM_SPAIN, TEAM_BRAZIL, 3, 2);
        scoreboard.updateScore(TEAM_GERMANY, TEAM_FRANCE, 3, 2);

        //then
        assertEquals(expectedSummary, scoreboard.getSummary().toString());
    }

    private void setUpTeams() throws ScoreboardException {
        scoreboard.startGame(TEAM_MEXICO, TEAM_CANADA);
    }
}
