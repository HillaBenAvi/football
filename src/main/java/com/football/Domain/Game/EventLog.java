package com.football.Domain.Game;
import java.util.ArrayList;

public class EventLog {
    private ArrayList<Event> events;
    private Game game;

    public EventLog(Game game) {
        events=new ArrayList<>();
        this.game=game;
    }

    public void addEvent (Event event){
        events.add(event);
    }

}
