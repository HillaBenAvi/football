package com.football.Domain.Asset;

import com.football.Domain.Game.Team;

import java.util.Date;
import java.util.HashMap;

public class Manager extends TeamMember {

    public Manager(String name, String userMail, String password , Date birthDate) {
        super(name, userMail, password , birthDate);
    }

    public Manager(String name, String userMail , Date birthDate) {
        super(name, userMail , birthDate);
        this.setPassword(null);
    }

    public Manager(String[] managerDetails, HashMap<String, Team> teams) {
        super(managerDetails,teams);
    }

    public Manager(String[] managerDetails) {
        super(managerDetails);
    }

    public void deleteAllTheData()
    {
        for (String strTeam:team.keySet()
        ) {
            team.get(strTeam).removeManager(this);
        }
    }
    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\',\'"+this.getTeamString()+"\'";
        return str;
    }

}
