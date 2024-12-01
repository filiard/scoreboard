import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreboardTest {
    private Scoreboard scoreboard;
    Team TEAM_MEXICO = new Team("Mexico");
    Team TEAM_CANADA = new Team("Canada");

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
        Team homeTeam = TEAM_MEXICO;
        Team awayTeam = TEAM_CANADA;
        scoreboard.startGame(homeTeam, awayTeam);
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
}
