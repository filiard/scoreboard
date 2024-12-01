import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private List<Game> games;

    public Scoreboard() {
        games = new ArrayList();
    }

    public ArrayList getGames() {
        return new ArrayList(games);
    }

    public void startGame(Team homeTeam, Team awayTeam) {
        Game newGame = new Game(homeTeam, awayTeam);
        games.add(newGame);
    }

    public void endGame(Team homeTeam, Team awayTeam) {
        games.removeIf(game -> game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam));
    }

    public void updateScore(Team homeTeam, Team awayTeam, int newHomeScore, int newAwayScore) {
        for (Game game : games) {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam)) {
                game.setHomeScore(newHomeScore);
                game.setAwayScore(newAwayScore);
            }
        }
    }
}