package com.football.Domain.Game;

import com.football.Domain.Asset.Player;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private Date time;
    private String description;
    private EventInGame eventInGame;
    private int gameMinute;
    private ArrayList<Player> players;

    public Event(Date time, String description, EventInGame eventInGame, int gameMinute, ArrayList<Player> players) {
        this.time = time;
        this.description = description;
        this.eventInGame = eventInGame;
        this.gameMinute = gameMinute;
        this.players = players;
    }
}
