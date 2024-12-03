package model;

import exception.ScoreboardException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Scoreboard {
    private List<Game> games;

    public Scoreboard() {
        games = new ArrayList();
    }

    public ArrayList getGames() {
        return new ArrayList(games);
    }

    public void startGame(Team homeTeam, Team awayTeam) throws ScoreboardException {
        for (Game game : games) {
            if (game.getHomeTeam().equals(homeTeam) || game.getAwayTeam().equals(homeTeam) ||
                    game.getHomeTeam().equals(awayTeam) || game.getAwayTeam().equals(awayTeam)) {
                throw new ScoreboardException("One of the teams is already playing another game");
            }
        }
        Game newGame = new Game(homeTeam, awayTeam);
        games.add(newGame);
    }

    public void endGame(Team homeTeam, Team awayTeam) {
        games.removeIf(game -> game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam));
    }

    public void updateScore(Team homeTeam, Team awayTeam, int newHomeScore, int newAwayScore) {
        checkScoreValidity(newHomeScore);
        checkScoreValidity(newAwayScore);
        findGame(homeTeam, awayTeam).ifPresent(game -> {
            game.setHomeScore(newHomeScore);
            game.setAwayScore(newAwayScore);
        });
    }

    public List<Game> getSummary() {
        List<Game> sortedGames = new ArrayList(games);
        sortedGames.sort(
                Comparator.comparing(Game::getTotalScore)
                        .thenComparing(Game::getTimeStarted)
                        .reversed()
        );
        StringBuilder sb = new StringBuilder();
        for (Game game : sortedGames) {
            sb.append(game).append("\n");
        }
        return sortedGames;
    }

    private Optional<Game> findGame(Team homeTeam, Team awayTeam) {
        return games.stream()
                .filter(game -> game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam))
                .findFirst();
    }

    private void checkScoreValidity(int score) {
        if (score < 0) try {
            throw new ScoreboardException("Score cannot be negative");
        } catch (ScoreboardException e) {
            throw new RuntimeException(e);
        }
    }
}