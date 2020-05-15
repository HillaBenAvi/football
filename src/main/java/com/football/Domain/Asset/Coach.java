package com.football.Domain.Asset;

import java.util.Date;

public class Coach extends TeamMember{
    private String training;

    public Coach(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password , birthDate);
        this.training = training;
    }



    public Coach(String name, String userMail, String training , Date birthDate) {
        super(name, userMail , birthDate);
        this.training = training;
    }

    public void deleteAllTheData()
    {
        for (String strTeam:team.keySet()
        ) {
            team.get(strTeam).removeCoach(this);
        }
    }

}
