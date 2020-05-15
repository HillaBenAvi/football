package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;

import java.util.Date;
import java.util.HashSet;


public abstract class Referee extends Member{
    protected String training;
    protected HashSet<Game> games;

    protected DBController dbController;

    public Referee(String name, String userMail, String password, String training , Date birthDate, DBController dbController) {
        super(name, userMail, password , birthDate);
        this.training = training;
        this.games = new HashSet<Game>();
        this.dbController = dbController;
    }


    /**
     *
     * @return all the games of the referee
     */
    public HashSet<Game> getGameSchedule(){
        return games;
    }


    /**
     * adding a game to the referee's schedule
     * @param game
     */
    public void addGame(Game game){
        games.add(game);
    }


    /**
     *
     * @return true- if the referee has games, else false
     */
    public boolean hadGames()
    {
        return games.size()>0;
    }
}
