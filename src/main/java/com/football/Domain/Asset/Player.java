package com.football.Domain.Asset;

import com.football.Domain.Game.Event;

import java.util.Date;
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
        setPassword(null);
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
}
