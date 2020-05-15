package com.football.Domain.League;

import java.util.HashMap;

public class Season {
    private String year;
    private HashMap<League,LeagueInSeason> leagueInSeasons;

    public Season(String name) {
        this.year = name;
        this.leagueInSeasons = new HashMap<>();
    }

    public void addLeagueInSeason(LeagueInSeason leagueInSeason){
        if(leagueInSeason != null && !leagueInSeasons.containsKey(leagueInSeason.getLeague())){
            leagueInSeasons.put(leagueInSeason.getLeague(), leagueInSeason);
        }
      //todo
    }

    public String getYear() {
        return year;
    }

    public HashMap<League,LeagueInSeason> getLeagues (){
        return leagueInSeasons;
    }
}
