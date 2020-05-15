package com.football.Domain.League;

import com.football.Exception.ObjectNotExist;

import java.util.HashMap;

public class League {
    private String name;
    private HashMap<Season,LeagueInSeason> leagueInSeasons;

    public League(String name) {
        this.name = name;
        leagueInSeasons=new HashMap<>();
    }

    public LeagueInSeason getLeagueInSeason(Season season) throws ObjectNotExist {

        if(leagueInSeasons.containsKey(season))
             return leagueInSeasons.get(season);
        else
           return null;
    }

    public String getName() {
        return this.name;
    }

    public void addLeagueInSeason(LeagueInSeason leagueInSeason){
        if(leagueInSeason != null && !leagueInSeasons.containsKey(leagueInSeason.getSeason())){
            leagueInSeasons.put(leagueInSeason.getSeason(), leagueInSeason);
        }
    }

    public HashMap<Season,LeagueInSeason> getSeasons(){
        return leagueInSeasons;
    }
}
