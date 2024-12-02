import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private Scoreboard scoreboard;
    Team TEAM_MEXICO = new Team("Mexico");
    Team TEAM_CANADA = new Team("Canada");
    Team TEAM_SPAIN = new Team("Spain");
    Team TEAM_BRAZIL = new Team("Brazil");
    Team TEAM_GERMANY = new Team("Germany");
    Team TEAM_FRANCE = new Team("France");

    @BeforeEach
    void setUp() {
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
        assertThrows(ScoreboardException.class, () -> scoreboard.updateScore(homeTeam, awayTeam, -1, 0));
    }

    @Test
    void shouldGiveSpecificGames() {
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
    void shouldGiveTotalSummary() throws InterruptedException {
        //given
        scoreboard.startGame(TEAM_GERMANY, TEAM_FRANCE);
        Thread.sleep(1000);
        scoreboard.startGame(TEAM_SPAIN, TEAM_BRAZIL);

        //when
        scoreboard.updateScore(TEAM_MEXICO, TEAM_CANADA, 1, 0);
        scoreboard.updateScore(TEAM_SPAIN, TEAM_BRAZIL, 3, 2);
        scoreboard.updateScore(TEAM_GERMANY, TEAM_FRANCE, 3, 2);

        //then
        assertEquals(scoreboard.getSummary(), "Spain 3 - Brazil 2\n" +
                "Germany 3 - France 2\n" +
                "Mexico 1 - Canada 0");
    }

    private void setUpTeams() {
        scoreboard.startGame(TEAM_MEXICO, TEAM_CANADA);
    }
}
