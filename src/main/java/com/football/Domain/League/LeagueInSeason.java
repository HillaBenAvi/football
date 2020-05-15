package com.football.Domain.League;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.Users.MainReferee;
import com.football.Domain.Users.Referee;
import com.football.Domain.Users.SecondaryReferee;
import com.football.Exception.AlreadyExistException;

import java.util.*;

public class LeagueInSeason {
    private HashSet<Game> games;
    private League league;
    private Season season;
    private ASchedulingPolicy schedulingPolicy;
    private IScorePolicy scorePolicy;
    private HashMap<String, Referee> referees;
    private LinkedList<Team> teams;

    public LeagueInSeason(League league,Season season) {
        this.league = league;
        this.season = season;
//        this.schedulingPolicies = schedulingPolicies;
//        this.scorePolicies = scorePolicies;
//        , ASchedulingPolicy schedulingPolicies,IScorePolicy scorePolicies
        games=new HashSet<>();
        referees=new HashMap<>();
        teams=new LinkedList<Team>();
    }

    public ASchedulingPolicy getPolicy()
    {
        return schedulingPolicy;
    }
    /*
    public void setReferee(Referee newReferee){
        if(newReferee != null){
            referee = newReferee;
        }
    }

     */

    public LinkedList<Team> getTeams()
    {
        return teams;
    }
    public Season getSeason(){
        return season;
    }
    public League getLeague(){
        return league;
    }

    public HashMap<String, Referee> getReferees() {
        return referees;
    }

    public void addReferee(String refereeName, Referee referee) {
        if(!referees.containsKey(refereeName)){
            referees.put(refereeName, referee);
        }
    }

    public void setScorePolicy(IScorePolicy policy) {
        scorePolicy = policy;
    }

    public void setSchedulingPolicy(ASchedulingPolicy policy) {
        schedulingPolicy = policy;
    }

    public LinkedList<Team> getTeamsForScheduling() {
        LinkedList<Team> teamToReturn=new LinkedList<>();
        for (int i=0; i<teams.size(); i++)
        {
            if(isFullTeam(teams.get(i)))
            {
                teamToReturn.add(teams.get(i));
            }
        }
        return teamToReturn;
    }

    private boolean isFullTeam(Team team) {
        HashSet<Player> players=team.getPlayers();
        HashSet<Coach> coaches=team.getCoaches();
        HashSet<Manager> managers=team.getManagers();
        if(players.size()<11)
        {
            return false;
        }
        if(coaches.size()<1)
        {
            return false;
        }
        if(managers.size()<1)
        {
            return false;
        }

        return true;
    }

    public void addGames(Set<Game> games) {
        for (Game game:games) {
            this.games.add(game);
            for(Referee r: game.getReferees()){
                r.addGame(game);
            }
        }
    }
    public List< Referee> getMainReferee() {
        List<Referee> toReturn=new LinkedList<>();
        for (String role:referees.keySet()
        ) {
            if(referees.get(role) instanceof MainReferee)
                toReturn.add((MainReferee)referees.get(role));
        }
        return toReturn;
    }

    public List<Referee> getSecondaryReferee() {
        List<Referee> toReturn=new LinkedList<>();
        for (String role:referees.keySet()
        ) {
            if(referees.get(role) instanceof SecondaryReferee)
                toReturn.add( (SecondaryReferee)referees.get(role));
        }
        return toReturn;
    }

    public ScorePolicy getScorePolicy() {
        return (ScorePolicy)this.scorePolicy;
    }

    public HashSet<Game>getGames(){
        return this.games;
    }

    public void addTeam(Team team) throws AlreadyExistException {
        if (this.teams == null ){
            this.teams = new LinkedList<Team>();
        }
        else if(this.teams.contains(team)){
            throw new AlreadyExistException();
        }
        else{
            this.teams.add(team);
        }
    }

    public ASchedulingPolicy getSchedulePolicy() {
        return this.schedulingPolicy;
    }
}
