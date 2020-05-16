package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public class RefereeController {


    @Autowired
    private DBController dbController;


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
