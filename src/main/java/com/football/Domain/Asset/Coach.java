package com.football.Domain.Asset;

import com.football.Domain.Game.Team;

import java.util.Date;
import java.util.HashMap;

public class Coach extends TeamMember{
    private String training;

    public Coach(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password , birthDate);
        this.training = training;
    }

    public Coach(String[] coachDetails , HashMap<String, Team> teams) {
        super(coachDetails,teams);
        //this.training = coachDetails[4] ;
        //todo - continue
    }

    public Coach(String name, String userMail, String training , Date birthDate) {
        super(name, userMail , birthDate);
        this.training = training;
    }

    public Coach(String[] coachDetails) {
        super(coachDetails);
    }

    public void deleteAllTheData()
    {
        for (String strTeam:team.keySet()
        ) {
            team.get(strTeam).removeCoach(this);
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
