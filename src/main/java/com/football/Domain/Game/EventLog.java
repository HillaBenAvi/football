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
           if(events.get(i).equals("")){
               continue;
           }
            details += events.get(i).toString() + ";" ;
        }
        if( events!=null && events.size() != 0 )
            details += events.get(events.size()-1);
        return details;
    }
}
