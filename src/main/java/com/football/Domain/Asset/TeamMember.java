package com.football.Domain.Asset;

import com.football.Domain.Game.Team;
import com.football.Domain.Users.Member;

import java.util.Date;
import java.util.HashMap;


public class TeamMember extends Member {
    protected HashMap<String, Team> team;

    public TeamMember(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
        team=new HashMap<>();
    }

    public TeamMember(String name, String userMail, Date birthDate) {
        super(name, userMail,  birthDate);
        team=new HashMap<>();
    }

    @Override
    public String getType() {
        return "TeamMember";
    }

    public void addTeam(Team teamToEnter) {
        if (team == null) {
            team = new HashMap<>();
        }
        if (!team.containsKey(teamToEnter.getName())) {
            team.put(teamToEnter.getName(), teamToEnter);
        }
    }

    public void removeTheTeamFromMyList(String name) {
        team.remove(name);
    }

    public HashMap<String, Team> getTeam() {
        return team;
    }

    public void setTeam(HashMap<String, Team> team) {
        this.team = team;
    }

    public boolean existInTeam(String mail) {
        if (team.containsKey(mail)) {
            return true;
        }
        return false;
    }

    protected String getTeamString() {
        String str="";
        for (String team:team.keySet()) {
            str+=team+";";
        }
        if(str.length()>0)
            str = str.substring(0,str.length()-1);
        return str;
    }


}
