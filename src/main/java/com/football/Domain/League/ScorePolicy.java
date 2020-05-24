package com.football.Domain.League;

public class ScorePolicy implements IScorePolicy{

    double scoreToWinningTeam;
    double scoreToDrawGame;
    double scoreToLosingTeam;

    /**
     * Default policy
     */
    public ScorePolicy (){
        scoreToWinningTeam = 5;
        scoreToDrawGame = 3;
        scoreToLosingTeam = 1;
    }

    public ScorePolicy (double winning, double draw, double losing){
        scoreToWinningTeam = winning;
        scoreToDrawGame = draw;
        scoreToLosingTeam = losing;
    }

    /**
     * This function changes the points of each situation - winning, draw and losing
     * @param winning
     * @param draw
     * @param losing
     */
    public void setNewPolicy(double winning, double draw, double losing){
        scoreToWinningTeam = winning;
        scoreToDrawGame = draw;
        scoreToLosingTeam = losing;
    }

    /**
     * This function calculates the final score of team in the end of the season to know which is the winning team
     * @param points - points of the team
     * @return final score
     */
    public double finalScore (double points){
        return points;
    }

    public double getScoreToDrawGame() {
        return scoreToDrawGame;
    }
    public double getScoreToWinningTeam(){
        return scoreToWinningTeam;
    }
    public double getScoreToLosingTeam(){
        return scoreToLosingTeam;
    }

    @Override
    public String toString() {
        return ""+this.scoreToWinningTeam+";"+this.scoreToDrawGame+";"+this.scoreToLosingTeam;
    }

    @Override
    public void setPolicy(String details) {
        /*add ScorePolicy*/
        String[] scoreP = details.split("--");
        this.scoreToWinningTeam = Double.parseDouble(scoreP[0]);
        this.scoreToDrawGame = Double.parseDouble(scoreP[1]);
        this.scoreToLosingTeam = Double.parseDouble(scoreP[2]);
    }
}
