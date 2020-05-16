package com.football.Domain.Users;

import com.football.Domain.Game.Team;
import com.football.Domain.League.*;
import com.football.DataBase.DBController;
import com.football.Exception.*;
import com.football.Domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AssociationDelegate extends Member {

    @Autowired
    private DBController dbController;

    public AssociationDelegate(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
        this.dbController = dbController;
    }

    public AssociationDelegate(String[] associationDelegateDetails) {
        super(associationDelegateDetails[1],associationDelegateDetails[0],associationDelegateDetails[2],
                new Date(Integer.parseInt(associationDelegateDetails[3]),
                        Integer.parseInt(associationDelegateDetails[4]),
                        Integer.parseInt(associationDelegateDetails[5])));
        this.dbController = dbController;
    }

    public void setLeague(String leagueName) throws AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        HashMap<String, League> leagues = dbController.getLeagues();
        League league;
        if (Pattern.matches("[a-zA-Z]+", leagueName)) { //checks that the name contains only letters(true=just letters)
            if (!leagues.containsKey(leagueName)) {
                league = new League(leagueName);
                dbController.addLeague(this, league);
            } else {
                throw new AlreadyExistException();
            }
        } else {
            throw new IncorrectInputException(leagueName);
        }
        //todo
    }

    public void setLeagueByYear(String specificLeague, String year) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException {
        HashMap<String, League> leagues = dbController.getLeagues();
        League league = leagues.get(specificLeague);
        if (league == null) {
            throw new ObjectNotExist("There is no league called " + specificLeague);
        }

        Season season = new Season(year);
        if (league.getLeagueInSeason(season) != null) { //check if the season is already exist in this league
            throw new AlreadyExistException();
        }
        LeagueInSeason leagueInSeason = new LeagueInSeason(league, season);
        league.addLeagueInSeason(leagueInSeason);
        season.addLeagueInSeason(leagueInSeason);
//        dbController.removeLeague(this ,league.getName());
//        dbController.addLeague(this ,league);
//      dbController.removeSeason(this ,season.getYear());
        dbController.addSeason(this, season);
        dbController.updateLeague(this, league);
        dbController.addLeagueInSeason(this, leagueInSeason);

    }

    public void insertSchedulingPolicy(String league, String season, String sPolicy) throws ObjectNotExist, DontHavePermissionException {
        SchedulingPolicyAllTeamsPlayTwice policy = new SchedulingPolicyAllTeamsPlayTwice();
        League leagueObj = dbController.getLeague( league);
        Season seasonObj = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        leagueInSeason.setSchedulingPolicy(policy);

        //todo
    }


    public void changeScorePolicy(String league, String season, String sWinning, String sDraw, String sLosing) throws IncorrectInputException, ObjectNotExist {
        try {
            double winning = Double.parseDouble(sWinning);
            double draw = Double.parseDouble(sDraw);
            double losing = Double.parseDouble(sLosing);
            ScorePolicy policy = new ScorePolicy(winning, draw, losing);
            League leagueObj = dbController.getLeague( league);
            Season seasonObj = dbController.getSeason( season);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            leagueInSeason.setScorePolicy(policy);
            dbController.updateLeagueInSeason(this,leagueInSeason);
        } catch (ObjectNotExist objectNotExist) {
            throw new ObjectNotExist("");
        } catch (
                Exception e) {
            throw new IncorrectInputException();
        }

    }

    /**
     * this function returns the policy in league in season
     *
     * @param league
     * @param season
     * @return
     * @throws DontHavePermissionException
     * @throws ObjectNotExist
     */
    public ScorePolicy getScorePolicy(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        League leagueObj = dbController.getLeague( league);
        Season seasonObj = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        return leagueInSeason.getScorePolicy();
    }

    /**
     * this function return a Hash map of referees in league in season
     *
     * @param league
     * @param season
     * @return
     * @throws DontHavePermissionException
     * @throws ObjectNotExist
     */
    public HashMap<String, Referee> getRefereesInLeagueInSeason(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        League leagueObj = dbController.getLeague( league);
        Season seasonObj = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        HashMap<String, Referee> refereesInLeagueInSeason = leagueInSeason.getReferees();
        return refereesInLeagueInSeason;
    }

    /**
     * This function returns all the referees that each referee doesn't exist in the league in season that
     * we get in the parameters
     *
     * @param league - name of league
     * @param season - name of season
     * @return list of referees
     * @throws DontHavePermissionException
     */
    public HashMap<String, Referee> getRefereesDoesntExistInTheLeagueAndSeason(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        HashMap<String, Referee> referees = new HashMap<>();
        try {
            HashMap<String, Referee> allRefereesInTheSystem = dbController.getReferees();
            League leagueObj = dbController.getLeague( league);
            Season seasonObj = dbController.getSeason( season);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            HashMap<String, Referee> refereesInLeagueInSeason = leagueInSeason.getReferees();
            for (String nameOfReferee : allRefereesInTheSystem.keySet()) {

                if (!refereesInLeagueInSeason.containsKey(nameOfReferee)) {
                    referees.put(nameOfReferee, allRefereesInTheSystem.get(nameOfReferee));
                }
            }
        }catch(ObjectNotExist objectNotExist){
            throw new ObjectNotExist("");
        } catch (Exception e) {
            throw new DontHavePermissionException();
        }
        return referees;
    }

    /**
     * @param league      - name of league to add the referee
     * @param season      - name of season to add the referee
     * @param refereeName - name of the referee we would like to add
     */
    public void addRefereeToLeagueInSeason(String league, String season, String refereeName) throws ObjectNotExist, ObjectAlreadyExist, DontHavePermissionException {

        HashMap<String, Referee> referees = dbController.getReferees();
        Referee referee = referees.get(refereeName);
        League leagueObj = dbController.getLeague( league);
        Season seasonObj = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        if (!leagueInSeason.getReferees().containsKey(refereeName)){
            leagueInSeason.addReferee(refereeName, referee);
            dbController.updateLeagueInSeason(this, leagueInSeason);
        }
        else
            throw new ObjectAlreadyExist();
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies() throws DontHavePermissionException {
        return dbController.getSchedulingPolicies();
    }

    //we dont let the user to creat a new scheduling policy
    public void addSchedulingPolicy(String policyName) throws IncorrectInputException, DontHavePermissionException {
    }


    public void setSchedulingPolicyToLeagueInSeason(String specificLeague, String year, String policyName) throws ObjectNotExist, IncorrectInputException, DontHavePermissionException {
        ASchedulingPolicy policy;
        if (policyName.equals("All teams play each other twice")) {
            policy = new SchedulingPolicyAllTeamsPlayTwice();
        } else if (policyName.equals("All teams play each other once")) {
            policy = new SchedulingPolicyAllTeamsPlayOnce();
        } else {
            throw new IncorrectInputException();
        }
        League leagueObj = dbController.getLeague( specificLeague);
        Season seasonObj = dbController.getSeason( year);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        leagueInSeason.setSchedulingPolicy(policy);
        dbController.updateLeagueInSeason(this,leagueInSeason);
    }

    /**
     * adding the team to leagueInSeason list - if the team is not valid - throw exception
     *
     * @param league
     * @param season
     * @param teamName
     */
    public void addTeamToLeagueInSeason(String league, String season, String teamName) throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, IncorrectInputException {
        Team team = dbController.getTeam( teamName);
        if (team.getPlayers().size() < 11 || team.getStatus() == false || team.getHomeField() == null) {
            throw new IncorrectInputException();
        }

        Season season1 = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = dbController.getLeague( league).getLeagueInSeason(season1);
        leagueInSeason.addTeam(team);
        dbController.updateLeagueInSeason(this,leagueInSeason);
    }

    /**
     * this function return the schedule policy in league in season
     *
     * @param league
     * @param season
     * @return
     * @throws DontHavePermissionException
     * @throws ObjectNotExist
     */
    public ASchedulingPolicy getSchedulingPolicyInLeagueInSeason(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        Season season1 = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = dbController.getLeague( league).getLeagueInSeason(season1);
        return leagueInSeason.getSchedulePolicy();
    }
    @Override
    public String getType() {
        return "AssociationDelegate";
    }
}


