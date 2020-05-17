package com.football.Domain.Users;

import java.util.Date;

public class MainReferee extends Referee {

    public MainReferee(String name, String userMail, String password, String training, Date birthDate) {
        super(name, userMail, password, training, birthDate);
    }

    public MainReferee(Fan fan)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate());
    }

    @Override
    public String getType() {
        return "MainReferee";
    }

}
