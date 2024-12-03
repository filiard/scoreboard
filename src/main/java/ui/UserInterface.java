package ui;

import exception.ScoreboardException;
import model.Game;
import model.Scoreboard;
import model.Team;

import java.util.Scanner;

public class UserInterface {
    private final Scoreboard scoreboardInstance = new Scoreboard();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        new UserInterface().run();
    }

    private void run() throws Exception {
        String menu =
                "** MENU **\n"
                        + "   1. Add new game\n"
                        + "   2. Remove game\n"
                        + "   3. Update a score\n"
                        + "   4. Get games summary\n"
                        + "   5. Exit\n"
                        + "Selection: ";



        while (true) {
            System.out.println(menu);
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline
            switch (choice) {
                case 1 -> addGame();
                case 2 -> endGame();
                case 3 -> updateScore();
                case 4 -> printSummary();
                case 5 -> {
                    System.out.println("Exiting");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addGame() {
        System.out.print("Enter home team name: ");
        Team homeTeam = new Team(scanner.nextLine());
        System.out.print("Enter away team name: ");
        Team awayTeam = new Team(scanner.nextLine());
        try {
            scoreboardInstance.startGame(homeTeam, awayTeam);
        } catch (ScoreboardException e) {
            throw new RuntimeException(e);
        }
        System.out.println("model.Game added.");
    }

    private void endGame() {
        System.out.print("Enter home team name: ");
        Team homeTeam = new Team(scanner.nextLine());
        System.out.print("Enter away team name: ");
        Team awayTeam = new Team(scanner.nextLine());
        scoreboardInstance.endGame(homeTeam, awayTeam);
        System.out.println("model.Game ended.");
    }

    private void updateScore() {
        System.out.print("Enter home team name: ");
        Team homeTeam = new Team(scanner.nextLine());
        System.out.print("Enter away team name: ");
        Team awayTeam = new Team(scanner.nextLine());
        System.out.print("Enter new home team score: ");
        int homeScore = scanner.nextInt();
        System.out.print("Enter new away team score: ");
        int awayScore = scanner.nextInt();
        scanner.nextLine();
        scoreboardInstance.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        System.out.println("Score updated.");
    }

    private void printSummary() {
        System.out.println("Games Summary:");
        if(scoreboardInstance.getGames().isEmpty()){
            System.out.println("No active games");
        } else {
            for (Game game : scoreboardInstance.getSummary()) {
                System.out.print(game.toString());
            }
        }
    }
}
