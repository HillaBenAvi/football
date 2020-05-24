package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Team;
import com.football.Domain.League.*;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.regex.Pattern;

//for each method we get String id of the member who asks for the action.
@Service
public class AssociationDelegateService {

    @Autowired
    private DBController dbController;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;

    public AssociationDelegateService(){ }

    public void setLeague(String id, String leagueName) throws AlreadyExistException, IncorrectInputException, DontHavePermissionException, MemberNotExist, ObjectNotExist {
           if (!dbController.existAssociationDelegate(id)) {
               errorLogService.addErrorLog("member not exist");
               throw new MemberNotExist();
           }
           AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
           HashMap<String, League> leagues = dbController.getLeagues();
           League league;
           if (Pattern.matches("[a-zA-Z]+", leagueName)) { //checks that the name contains only letters(true=just letters)
               if (!leagues.containsKey(leagueName)) {
                   league = new League(leagueName);
                   dbController.addLeague(currAssociationDelegate, league);
                   eventLogService.addEventLog(id, "set league");
               } else {
                   errorLogService.addErrorLog(" already exist exception");
                   throw new AlreadyExistException();
               }
           } else {
               errorLogService.addErrorLog(" incorrect input exception");
               throw new IncorrectInputException(leagueName);
           }
    }

    public void setLeagueByYear(String id, String specificLeague, String year) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException, MemberNotExist {
        if (!dbController.existAssociationDelegate(id)) {
            errorLogService.addErrorLog("member not exist");
            throw new MemberNotExist();
        }
        AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
        HashMap<String, League> leagues = dbController.getLeagues();
        League league = leagues.get(specificLeague);
        if (league == null) {
            errorLogService.addErrorLog("object not exist");
            throw new ObjectNotExist("There is no league called " + specificLeague);
        }
        Season season = new Season(year);
        if (league.getLeagueInSeason(season) != null) { //check if the season is already exist in this league
            errorLogService.addErrorLog("already exist exception");
            throw new AlreadyExistException();
        }
        LeagueInSeason leagueInSeason = new LeagueInSeason(league, season);
        league.addLeagueInSeason(leagueInSeason);
        season.addLeagueInSeason(leagueInSeason);
//        dbController.removeLeague(this ,league.getName());
//        dbController.addLeague(this ,league);
//      dbController.removeSeason(this ,season.getYear());
        dbController.addSeason(currAssociationDelegate, season);
        dbController.updateLeague(currAssociationDelegate, league);
        dbController.addLeagueInSeason(currAssociationDelegate, leagueInSeason);
        eventLogService.addEventLog(id, "set league by year");
    }

    public void insertSchedulingPolicy(String id, String league, String season, String sPolicy) throws ObjectNotExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        if (!dbController.existAssociationDelegate(id)) {
            errorLogService.addErrorLog("member not exist");
            throw new MemberNotExist();
        }
        AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
        SchedulingPolicyAllTeamsPlayTwice policy = new SchedulingPolicyAllTeamsPlayTwice();
        League leagueObj = dbController.getLeague( league);
        Season seasonObj = dbController.getSeason( season);
        LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
        leagueInSeason.setSchedulingPolicy(policy);
        dbController.updateLeagueInSeason(currAssociationDelegate, leagueInSeason);
        eventLogService.addEventLog(id, "insert scheduling policy");
    }


    public void changeScorePolicy(String id, String league, String season, String sWinning, String sDraw, String sLosing) throws IncorrectInputException, ObjectNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existAssociationDelegate(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
            double winning = Double.parseDouble(sWinning);
            double draw = Double.parseDouble(sDraw);
            double losing = Double.parseDouble(sLosing);
            ScorePolicy policy = new ScorePolicy(winning, draw, losing);
            League leagueObj = dbController.getLeague( league);
            Season seasonObj = dbController.getSeason( season);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            leagueInSeason.setScorePolicy(policy);
            dbController.updateLeagueInSeason(currAssociationDelegate,leagueInSeason);
            eventLogService.addEventLog(id, "change score policy");
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("");
        } catch (
                Exception e) {
            errorLogService.addErrorLog("Incorrect Input Exception");
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
    public ScorePolicy getScorePolicy(String id, String league, String season) throws ObjectNotExist{
       try{
           League leagueObj = dbController.getLeague(league);
           Season seasonObj = dbController.getSeason(season);
           LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
           return leagueInSeason.getScorePolicy();
       }catch(ObjectNotExist e){
           errorLogService.addErrorLog("Object Not Exist");
           throw new ObjectNotExist("");
       }
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
    public HashMap<String, Referee> getRefereesInLeagueInSeason(String league, String season) throws ObjectNotExist {
        try{
            League leagueObj = dbController.getLeague(league);
            Season seasonObj = dbController.getSeason(season);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            HashMap<String, Referee> refereesInLeagueInSeason = leagueInSeason.getReferees();
            return refereesInLeagueInSeason;
        }catch(ObjectNotExist e){
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("");
        }
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
    public HashMap<String, Referee> getRefereesDoesntExistInTheLeagueAndSeason(String league, String season) throws ObjectNotExist, DontHavePermissionException {
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
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("");
        } catch (Exception e) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
            throw new DontHavePermissionException();
        }
        return referees;
    }

    /**
     * @param league      - name of league to add the referee
     * @param season      - name of season to add the referee
     * @param refereeName - name of the referee we would like to add
     */
    public void addRefereeToLeagueInSeason(String id, String league, String season, String refereeName) throws ObjectNotExist, ObjectAlreadyExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try{
            if (!dbController.existAssociationDelegate(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
            HashMap<String, Referee> referees = dbController.getReferees();
            Referee referee = referees.get(refereeName);
            League leagueObj = dbController.getLeague(league);
            Season seasonObj = dbController.getSeason(season);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            if (!leagueInSeason.getReferees().containsKey(refereeName)){
                leagueInSeason.addReferee(refereeName, referee);
                eventLogService.addEventLog(id, "add referee to league in season");
                dbController.updateLeagueInSeason(currAssociationDelegate, leagueInSeason);
            }
            else{
                errorLogService.addErrorLog("Object Already Exist");
                throw new ObjectAlreadyExist();
            }
        }catch(DontHavePermissionException e){
            errorLogService.addErrorLog("Dont Have Permission Exception");
            throw new DontHavePermissionException();
        }catch(ObjectAlreadyExist e){
            errorLogService.addErrorLog("Object Already Exist");
            throw new ObjectNotExist("");
        }
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies(){
        return dbController.getSchedulingPolicies();
    }

    public void setSchedulingPolicyToLeagueInSeason(String id, String specificLeague, String year, String policyName) throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, MemberNotExist {
        try {
            if (!dbController.existAssociationDelegate(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
            ASchedulingPolicy policy;
            if (policyName.equals("All teams play each other twice")) {
                policy = new SchedulingPolicyAllTeamsPlayTwice();
            } else if (policyName.equals("All teams play each other once")) {
                policy = new SchedulingPolicyAllTeamsPlayOnce();
            } else {
                errorLogService.addErrorLog("Incorrect Input Exception");
                throw new IncorrectInputException();
            }
            League leagueObj = dbController.getLeague(specificLeague);
            Season seasonObj = dbController.getSeason(year);
            LeagueInSeason leagueInSeason = leagueObj.getLeagueInSeason(seasonObj);
            leagueInSeason.setSchedulingPolicy(policy);
            dbController.updateLeagueInSeason(currAssociationDelegate, leagueInSeason);
            eventLogService.addEventLog(id, "set scheduling policy to league in season");
        }
        catch(ObjectNotExist e){
            errorLogService.addErrorLog("Object Not Exist");
        }
    }

    /**
     * adding the team to leagueInSeason list - if the team is not valid - throw exception
     *
     * @param league
     * @param season
     * @param teamName
     */
    public void addTeamToLeagueInSeason(String id, String league, String season, String teamName) throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, IncorrectInputException, MemberNotExist {
        try{
            if (!dbController.existAssociationDelegate(id)) {
                throw new MemberNotExist();
            }
            AssociationDelegate currAssociationDelegate = dbController.getAssociationDelegate(id);
            Team team = dbController.getTeam(teamName);
            if (team.getPlayers().size() < 11 || team.getStatus() == false || team.getHomeField() == null) {
                throw new IncorrectInputException();
            }

            Season season1 = dbController.getSeason(season);
            LeagueInSeason leagueInSeason = dbController.getLeague(league).getLeagueInSeason(season1);
            leagueInSeason.addTeam(team);
            dbController.updateLeagueInSeason(currAssociationDelegate, leagueInSeason);
            eventLogService.addEventLog(id, "add team to league in season");
        }catch(ObjectNotExist e){
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("");
        }
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
        try{
            Season season1 = dbController.getSeason(season);
            LeagueInSeason leagueInSeason = dbController.getLeague(league).getLeagueInSeason(season1);
            return leagueInSeason.getSchedulePolicy();
        }catch(ObjectNotExist e){
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("");
        }
    }

}
