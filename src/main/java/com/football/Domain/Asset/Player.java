package com.football.Domain.Asset;

import com.football.Domain.Game.Event;
import com.football.Domain.Game.Team;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Player extends TeamMember{
    private HashSet<Event> events;
    private String role;
    //  private Team team;//maybe just one team



    public Player(String name, String userMail, String password, Date birthDate, String role) {
        super(name, userMail, password,birthDate);

        this.role = role;
        events=new HashSet<>();
    }

    public Player(String name, String userMail, Date birthDate, String role) {
        super(name, userMail , birthDate);

        this.role = role;
        events= new HashSet<>();
        setPassword(null);
    }

    public Player(String[] playerDetails , HashMap<String, Team> teams) {
        super(playerDetails,teams);
        this.role = playerDetails[4] ;
        this.addEvents(playerDetails[6]);
        //todo add teams?
    }

    public Player(String[] playerDetails) {
        super(playerDetails);
    }

    public void deleteAllTheData()
    {
        for (String strTeam:team.keySet()
        ) {
            team.get(strTeam).removePlayer(this);
        }
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void addEvents(String events) {
        String[] eventsString = events.split(";");
        Event event = new Event(eventsString);
        this.events = new HashSet<>();
        this.events.add(event);
    }
    @Override
    public String toString()
    {
        String str="";
        str="\'"+this.getUserMail()+"\'," +
                "\'" +this.getPassword()+"\'," +
                "\'" +this.getName()+"\'," +
                "\'" +this.getBirthDateString()+"\'," +
                "\'" +this.getRole() + "\'," +
                "\'" +this.getTeamString()+"\'," +
                "\'" +this.getEventString()+"\'";
        return str;
    }

    private String getEventString() {
        String str="";
        for (Event event:events
        ) {
            str+=event.toString()+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }
}
