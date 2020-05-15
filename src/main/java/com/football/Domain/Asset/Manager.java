package com.football.Domain.Asset;

import java.util.Date;

public class Manager extends TeamMember {

    public Manager(String name, String userMail, String password , Date birthDate) {
        super(name, userMail, password , birthDate);
    }

    public Manager(String name, String userMail , Date birthDate) {
        super(name, userMail , birthDate);
        this.setPassword(null);
    }

    public void deleteAllTheData()
    {
        for (String strTeam:team.keySet()
        ) {
            team.get(strTeam).removeManager(this);
        }
    }

}
