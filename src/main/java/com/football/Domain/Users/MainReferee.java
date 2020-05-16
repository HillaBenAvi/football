package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Event;
import com.football.Domain.Game.EventInGame;
import com.football.Domain.Game.Game;
import com.football.Exception.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MainReferee extends Referee {

    public MainReferee(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password, training , birthDate);
    }
    public MainReferee(Fan fan, DBController dbcontroller)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate());
    }

    @Override
    public String getType() {
        return "MainReferee";
    }

}
