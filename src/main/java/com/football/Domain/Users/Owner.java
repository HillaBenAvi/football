package com.football.Domain.Users;

import com.football.Domain.Game.Team;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;

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

    public Owner(String[] ownerdetails,HashMap<String,Team> teams){
        super(ownerdetails);
        this.teams = new HashMap<>(teams);
    }

    public Owner(String[] ownerdetails) {
        super(ownerdetails);
    }

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public boolean notHaveTeams() throws MemberNotExist {
        return teams.size()==0;
    }

    @Override
    public String getType() {
        return "0Owner";
    }

    @Override
    public String toString() {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\',\'"+this.getTeamString()+"\'";
        return str;
    }

    private String getTeamString() {
        String str="";
        for (String team:teams.keySet()
        ) {
            str+=team+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    public void addTeam(Team team) {
        if (this.teams == null) {
            this.teams = new HashMap<>();
        }
        if (!this.teams.containsKey(team.getName())) {
            this.teams.put(team.getName(), team);
        }
    }

    public void removeTeam(String teamName) {
        this.teams.remove(teamName);
    }
}