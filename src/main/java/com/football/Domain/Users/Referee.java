package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;

import java.util.Date;
import java.util.HashSet;


public abstract class Referee extends Member{
    protected String training;
    protected HashSet<Game> games;

    public Referee(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password , birthDate);
        this.training = training;
        this.games = new HashSet<Game>();

    }


}
