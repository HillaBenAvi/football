package com.football.Domain.Users;

import com.football.Domain.Game.Game;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;

import java.util.Date;
import java.util.HashSet;

public class SecondaryReferee extends Referee{

    public SecondaryReferee(String name, String userMail, String password, String training , Date birthdate) {
        super(name, userMail, password, training , birthdate);
    }
    public SecondaryReferee(Fan fan)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate());
    }
    public SecondaryReferee(String[] secondaryRefereeDetails , HashSet<Game> games) {
        super(secondaryRefereeDetails , games);
    }
    @Override
    public String getType() {
        return ("0Secondary Referee");
    }

    @Override
    public void addGame(Game game) {
        super.addGame(game);
    }
}
