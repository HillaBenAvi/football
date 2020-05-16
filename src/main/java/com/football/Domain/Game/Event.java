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
    public Event(String[] eventDetails){
        Date date = new Date(Integer.parseInt(eventDetails[0]),Integer.parseInt(eventDetails[1]),Integer.parseInt(eventDetails[2]));
        this.time = date;
        this.description = eventDetails[3];
        this.eventInGame = EventInGame.valueOf(eventDetails[3]);
        this.players = new ArrayList<>();
        for(int i=5 ; i < eventDetails.length ;i++){
            String[] playerToAdd = eventDetails[i].split("--");
            Player player = new Player(playerToAdd);
            this.players.add(player);
        }
    }
    @Override
    public String toString(){
        String details = time.getYear() + ";" + time.getMonth() + ";" + time.getDay() ;
        details += ";" + description + ";" + eventInGame + ";" + gameMinute + ";" ;
        for (int i=0 ; i<players.size()-1 ; i++){
            details += players.get(i).getUserMail() + ";" ;
        }
        details+=players.get(players.size()-1);

        return details;
    }
}
