package com.football.Domain.Users;

import com.football.Domain.Game.Game;

import java.util.Date;
import java.util.HashSet;

public class MainReferee extends Referee {

    public MainReferee(String name, String userMail, String password, String training, Date birthDate) {
        super(name, userMail, password, training, birthDate);
    }

    public MainReferee(Fan fan)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate());
    }

    public MainReferee(String[] secondaryRefereeDetails, HashSet<Game> games) {
        super(secondaryRefereeDetails,games);
    }
    @Override
    public String getType() {
        return "0MainReferee";
    }
    @Override
    public void addGame(Game game) {
        super.addGame(game);
    }
}
