package com.football.Domain.Game;

import com.football.Domain.Asset.Field;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.Users.Referee;

import java.text.SimpleDateFormat;
import java.util.*;

public class Game extends Observable {
    private String id;
    private Calendar dateAndTime; //
    private Team hostTeam;
    private Team visitorTeam;
    private Field field;
    private String result;
    private EventLog eventLog;
    private LeagueInSeason leagueInSeason;
    private HashSet<Referee> referees;

    public Game(String id , Calendar dateAndTime,Team hostTeam, Team visitorTeam, Field field, Referee mainReferee, Referee secondaryReferee, LeagueInSeason leagueInSeason) {
        this.id=id;
        this.dateAndTime = dateAndTime;
        this.hostTeam = hostTeam;
        this.visitorTeam = visitorTeam;
        this.field = field;
        this.result = "";
        this.eventLog = new EventLog(this);
        this.leagueInSeason=leagueInSeason;
        referees = new HashSet<>();
        referees.add(mainReferee);
        referees.add(secondaryReferee);
    }
    public Game(String gameDetails){
        String[] splitGameDetails = gameDetails.split(":");
        this.id = splitGameDetails[0];
        if(gameDetails.length()>1){
            String[] da = splitGameDetails[1].split(" ");
            this.dateAndTime = new GregorianCalendar(Integer.parseInt(da[0]),Integer.parseInt(da[1]),Integer.parseInt(da[2])
                                    ,Integer.parseInt(da[3]),Integer.parseInt(da[4]));
        }
    }
    public String getId(){
        return id;
    }
    public void removeReferee(Referee referee) {
        referees.remove(referee);
        //put new referee after the delete
    }

    public boolean isRefereeInTheGame(Referee referee){
        return referees.contains(referee);
    }

    public void addEvent (Event event){
        if(this.eventLog == null){
            this.eventLog = new EventLog(this);
        }
        this.eventLog.addEvent(event);
        notifyFollowers("new event in the game:" + event.toString());
    }

    public String getDateString() {
        String ans = "" + dateAndTime.get(Calendar.YEAR) + "-" +  dateAndTime.get(Calendar.MONTH) + "-" + dateAndTime.get(Calendar.DAY_OF_MONTH);
        return ans;
    }
    public Calendar getDateCalendar() {
        return dateAndTime;
    }

    public Team getHostTeam() {
        return hostTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }


    public String getDateAndTimeString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        String ans = sdf.format(dateAndTime.getTime());
        return ans;
    }

    public Field getField() {
        return field;
    }
    public HashSet<Referee> getReferees(){ return this.referees;}

    public void addNewFollower(Observer follower){
        addObserver(follower);
    }

    public void notifyFollowers (String message){
        setChanged();
        notifyObservers(message);
    }

    public int getFollowersNumber(){
        return countObservers();
    }

    public void setResult(String result) {
        this.result = result;
    }
    public void setLeagueInSeason(LeagueInSeason leagueInSeason){
        this.leagueInSeason = leagueInSeason;
    }
    public void addEvents(String events) {
        String[] eventsToAdd = events.split(",");
        this.eventLog = new EventLog(this);
        for( String eventString : eventsToAdd){
            if(eventString.equals("")){
                continue;
            }
            else{
                String[] eventToAdd = eventString.split("!");
                Event event = new Event(eventToAdd);
                this.eventLog.addEvent(event);
            }
        }
    }

    @Override
    public String toString(){

        String details =
                "\'" + id + "\'," +
                        "\'" +  leagueInSeason.getSeason().getYear()+" 0 1 20 30 0\'," +
                        "\'" + hostTeam.getName() + "\'," +
                        "\'" + visitorTeam.getName() + "\'," +
                        "\'" + field.getName() + "\'," +
                        "\'" + result + "\'," +
                        "\'" + eventLog.toString() + "\'," +
                        "\'" + leagueInSeason.getLeague().getName() + "\'," +
                        "\'" + leagueInSeason.getSeason().getYear() + "\'," ;
        details += "\'";
        for ( Referee referee : referees){
            details = details + referee.getUserMail() +";";
        }
        details += "\'";
        return details;
    }

    public String getReport(){
        return id +
                "\n" +  leagueInSeason.getSeason().getYear()+
                "\n" + hostTeam.getName() +
                "\n" + visitorTeam.getName() +
                "\n" + field.getName() +
                "\n" + result +
                "\n" + eventLog.toString() +
                "\n" + leagueInSeason.getLeague().getName() +
                "\n" + leagueInSeason.getSeason().getYear()  ;
    }
}