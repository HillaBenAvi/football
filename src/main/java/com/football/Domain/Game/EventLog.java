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

    @Override
    public String toString(){
        String details = "";
        for(int i=0 ; i<events.size()-1 ; i++){
            details += events.get(i).toString() + ";" ;
        }
        details += events.get(events.size()-1);
        return details;
    }
}
