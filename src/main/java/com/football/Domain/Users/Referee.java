package com.football.Domain.Users;

import com.football.Domain.Game.Game;
import com.football.DataBase.DBController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;


public abstract class Referee extends Member{
    protected String training;
    protected HashSet<Game> games;

    @Autowired
    protected DBController dbController;

    public Referee(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password , birthDate);
        this.training = training;
        this.games = new HashSet<Game>();
        this.dbController = dbController;
    }
    public Referee(String[] refereeDetails,HashSet<Game> games){
        super(refereeDetails);
        if(refereeDetails.length>=5) {
            this.training = refereeDetails[4];
        }else
            this.training="";
        this.games = new HashSet<>(games);
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

    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\',\'"+this.training+"\',\'"+this.getGamesString()+"\'";
        return str;
    }

    private String getGamesString() {
        String str="";
        for (Game game:games) {
            if(game!=null)
                str+=game.getId()+";";
        }
        if(str!=null && str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }
}
