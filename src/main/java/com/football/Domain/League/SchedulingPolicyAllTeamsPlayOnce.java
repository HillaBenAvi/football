package com.football.Domain.League;

import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;

import java.util.List;
import java.util.Set;


public class SchedulingPolicyAllTeamsPlayOnce extends ASchedulingPolicy {
    private String name;

    public SchedulingPolicyAllTeamsPlayOnce()
    {
        name = "All teams play each other once";
    }


    public Set<Game> setGamesOfTeams(List <Team> teams, LeagueInSeason leagueInSeason) {
        return super.setGamesOfTeams(teams, leagueInSeason, false);
    }

    @Override
    public String getNameOfPolicy() {
        return name;
    }
}
