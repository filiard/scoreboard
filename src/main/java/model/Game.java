package model;

import java.time.LocalDateTime;

public class Game {
    Team homeTeam, awayTeam;
    int homeScore, awayScore;
    private final LocalDateTime timeStarted;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        homeScore = 0;
        awayScore = 0;
        timeStarted = LocalDateTime.now();
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public LocalDateTime getTimeStarted() {
        return timeStarted;
    }


    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getTotalScore(){
        return homeScore+awayScore;
    }

    @Override
    public String toString() {
        return homeTeam.teamname() + " " + homeScore + " - " + awayTeam.teamname() + " " + awayScore+"\n";
    }
}
