package com.football.Domain.Users;

import com.football.Domain.Game.Team;
import com.football.Exception.DontHavePermissionException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OwnerDTO extends Member{
    private HashMap<String, Team> teams;


    public OwnerDTO(String name, String userMail, String password, Date birthDate) throws DontHavePermissionException {
        super(name, userMail, password, birthDate);
        teams = new HashMap<>();
    }

    public OwnerDTO(String name, String userMail, String password, Date birthDate, HashMap<String,Team> teams) throws DontHavePermissionException {
        super(name, userMail, password, birthDate);
        this.teams = teams;
    }

    @Override
    public String getType() {
        return null;
    }
}
