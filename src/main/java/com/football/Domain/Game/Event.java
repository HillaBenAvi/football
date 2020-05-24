package com.football.Domain.Game;

import com.football.Domain.Asset.Player;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private Date time;
    private String description;
    private EventInGame eventInGame;
    private int gameMinute;
    private ArrayList<String> playersNames;

    public Event(Date time, String description, EventInGame eventInGame, int gameMinute, ArrayList<String> players) {
        this.time = time;
        this.description = description;
        this.eventInGame = eventInGame;
        this.gameMinute = gameMinute;
        this.playersNames = players;
    }
    public Event(String[] eventDetails){
        Date date = new Date(Integer.parseInt(eventDetails[0]),Integer.parseInt(eventDetails[1]),Integer.parseInt(eventDetails[2]));
        this.time = date;
        this.description = eventDetails[3];
        this.gameMinute = Integer.parseInt(eventDetails[4]);
        this.eventInGame = EventInGame.valueOf(eventDetails[5]);
        this.playersNames = new ArrayList<>();
        for(int i=6 ; i < eventDetails.length ;i++){
            this.playersNames.add(eventDetails[i]);
        }
    }
    @Override
    public String toString(){
        String details = time.getYear() + ";" + time.getMonth() + ";" + time.getDay() ;
        details += ";" + description + ";" + eventInGame + ";" + gameMinute + ";" ;
        for (int i=0 ; i<playersNames.size()-1 ; i++){
            details += playersNames.get(i)+ ";" ;
        }
        details+=playersNames.get(playersNames.size()-1);

        return details;
    }
}
