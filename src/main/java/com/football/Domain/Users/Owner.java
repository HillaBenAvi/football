package com.football.Domain.Users;

import com.football.Domain.Game.Team;
import com.football.Exception.DontHavePermissionException;

import java.util.Date;
import java.util.HashMap;

public class Owner extends Member {
    private HashMap<String, Team> teams;

    public Owner(String name, String userMail, String password, Date birthDate)  {
        super(name, userMail, password, birthDate);
        this.teams = new HashMap<>();
    }

    public Owner(String name, String userMail, String password, Date birthDate, HashMap<String, Team> teams) throws DontHavePermissionException {
        super(name, userMail, password, birthDate);
        this.teams = teams;
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    @Override
    public String getType() {
        return "Owner";
    }
}