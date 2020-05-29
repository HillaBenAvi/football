package com.football;

import com.football.Domain.Users.Fan;
import com.football.Domain.Users.Member;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@RestController
@RequestMapping(value="/service")
public class ServiceController {

    @Autowired
    private Manager manager;

    @Autowired
    private ErrorLogService errorLogService;


    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String register(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "userMail") String userMail,
                       @RequestParam(value = "password") String password
    )  {
        try {
            manager.register(userName,userMail,password);
        } catch (IncorrectInputException e) {
            return "incorrect input";
        } catch (AlreadyExistException e) {
            return "the member already exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        }
        return "you register successfully";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password) {
        try{
            return manager.stringLogIn(id, password);
        } catch (PasswordDontMatchException e){
            return "Password Dont Match";
        }
        catch (MemberNotExist e){
            return "the member does not exist";
        }
        catch (DontHavePermissionException e){
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the member already exist";
        }
    }

    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public void logout(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password)
            throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
    }


    /*****************add assets to team ********************/

    @RequestMapping(value="/addManagerToTeam",method = RequestMethod.POST)
    public String addManagerToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "managerId")String managerId,
                                 @RequestParam(value = "teamName")String teamName) {
        try {
            manager.addManagerToTeam(id, teamName, managerId);
        } catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the member already exist";
        }
        return "Manager added successfully";
    }

    @RequestMapping(value="/addCoachToTeam",method = RequestMethod.POST)
    public String addCoachToTeam(@RequestParam(value = "id") String id,
                               @RequestParam(value = "mailId")String mailId,
                                 @RequestParam(value = "teamName")String teamName) {
        try {
            manager.addCoachToTeam(id, teamName, mailId);
        } catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the member already exist";
        }
        return "Coach added successfully";
    }

    @RequestMapping(value="/addPlayerToTeam",method = RequestMethod.POST)
    public String addPlayerToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId,
                               @RequestParam(value = "year")int year,
                               @RequestParam(value = "month")int month,
                               @RequestParam(value = "day")int day,
                               @RequestParam(value = "roleInPlayers")String roleInPlayers) {
        try {
            manager.addPlayerToTeam(id, teamName, mailId, year, month, day, roleInPlayers);
        } catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the member already exist";
        }
        return "Player added successfully";
    }

    @RequestMapping(value="/addFieldToTeam",method = RequestMethod.POST)
    public String addFieldToTeam(@RequestParam(value = "id") String id,
                               @RequestParam(value = "fieldName")String fieldName,
                               @RequestParam(value = "teamName")String teamName) {
        try{
            manager.addFieldToTeam(id, teamName, fieldName);
        }catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        }
        return "Field added successfully";
    }

    /*****************remove assets to team ********************/

    @DeleteMapping(value="/removeTeamManager")
    public String removeTeamManager(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId) {
        try {
            manager.removeTeamManager(id, teamName, mailId);
        }catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        }
        return "Manager removed successfully";
    }

    @RequestMapping(value="/removeTeamCoach",method = RequestMethod.POST)
    public String removeTeamCoach(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "mailId")String mailId) {
        try{
            manager.removeTeamCoach(id, teamName, mailId);
        }catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        }
            return "Coach removed successfully";
    }

    @RequestMapping(value="/removeTeamPlayer",method = RequestMethod.POST)
    public String removeTeamPlayer(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "mailId")String mailId) {
        try{
            manager.removeTeamPlayer(id, teamName, mailId);
        }catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        }
        return "Player removed successfully";
    }

    @RequestMapping(value="/removeTeamField",method = RequestMethod.POST)
    public String removeTeamField(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "fieldName")String fieldName) {
        try{
            manager.removeTeamField(id, teamName, fieldName);
        }catch (PasswordDontMatchException e) {
            return "Password Dont Match";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        }
        return "Field removed successfully";
    }

    @RequestMapping(value="/addNewTeam",method = RequestMethod.POST)
    public String addNewTeam(@RequestParam(value = "id") String id,
                                @RequestParam(value = "teamName")String teamName,
                                @RequestParam(value = "ownerId")String ownerId) {
        try {
            manager.addNewTeam(id, teamName, ownerId);
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (IncorrectInputException e) {
            return "incorrect Password";
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        } catch (ObjectAlreadyExist objectAlreadyExist) {
            return "the object already exist";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        }
        return "Team added successfully";
    }

    @RequestMapping(value="/schedulingGames",method = RequestMethod.POST)
    public String schedulingGames(@RequestParam(value = "id") String id,
                           @RequestParam(value = "leagueId:seasonId")String leagueIdseasonId) {
        try {
            manager.schedulingGames(id, leagueIdseasonId.split(":")[1], leagueIdseasonId.split(":")[0]);
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (IncorrectInputException e) {
            return "Incorrect Input";
        }
        return "Games schedule successfully";
    }

    @RequestMapping(value="/setLeagueByYear",method = RequestMethod.POST)
    public String setLeagueByYear(@RequestParam(value = "id") String id,
                                @RequestParam(value = "seasonId")String seasonId,
                                @RequestParam(value = "leagueId")String leagueId,
                                  @RequestParam(value = "sWinning") String sWinning,
                                  @RequestParam(value = "sDraw") String sDraw,
                                  @RequestParam(value = "sLosing") String sLosing,
                                  @RequestParam(value = "schedulingPolicyName") String schedulingPolicyName){
        try {
            manager.setLeagueByYear(id, seasonId, leagueId);
            manager.changeScorePolicy(id, leagueId, seasonId, sWinning, sDraw, sLosing);
            manager.insertSchedulingPolicy(id, leagueId, seasonId, schedulingPolicyName);
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (IncorrectInputException e) {
            return "incorrect input exception";
        }
        return "Season set successfully to League";
    }

    @RequestMapping(value="/updateGameEvents",method = RequestMethod.POST)
    public String updateGameEvents(@RequestParam(value = "gameId") String id,
                                 @RequestParam(value = "refereeId") String refereeId,
                                 @RequestParam(value = "year") String year,
                                 @RequestParam(value = "month") String month,
                                 @RequestParam(value = "day") String day,
                                 @RequestParam(value = "description") String description,
                                 @RequestParam(value = "gameMinute") String gameMinute,
                                 @RequestParam(value = "eventEnum") String eventInGame,
                                 @RequestParam(value = "playersId") String playersId) {
        try {
            return manager.addGameEvents(id, refereeId,  year, month,  day ,description,gameMinute,eventInGame,playersId);
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        }

    }

    @RequestMapping(value="/closeTeam",method = RequestMethod.POST)
    public String closeTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName") String teamName){
        try {
            manager.closeTeam(id, teamName);
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        } catch (IncorrectInputException e) {
            return "Incorrect Input";
        }
        return "Team closed successfully";
    }

    //todo: after the client
//    @RequestMapping(value="/initSystem",method = RequestMethod.DELETE)
//    public void initSystem(@RequestParam(value = "id") String id,
//                          @RequestParam(value = "teamName") String teamName)
//            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
//        manager.closeTeam(id, teamName);
//    }





    /***********************to add************************/


    //return a list of teams names of an owner - Daniel 26.5
    @RequestMapping(value="/getTeamsOfOwner",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getTeamsOfOwner(@RequestParam(value = "id") String ownerId) throws AlreadyExistException, DontHavePermissionException {
        try{
            return manager.getTeamsByOwner(ownerId);
        }
        catch(MemberNotExist e){
            errorLogService.addErrorLog("member not exist");
            return null;
        }
    }

    //get all the teams in the system - Daniel 26.5
    @RequestMapping(value="/getAllTeams",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getAllTeams(@RequestParam(value = "id") String id) {
            return manager.getAllTeams();
    }




    /*****************team getters*******************/
    //the key of the HashMaps- id, value- name
    //get all the league in season
    @RequestMapping(value="/getLeagueInSeasonsIds",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getLeagueInSeasonsIds(){
        return manager.getLeagueInSeasonsIds();
    }
    //get all the users that can be coaches of a team
    @RequestMapping(value="/getTeamManagers",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getTeamManagers(@RequestParam(value = "id") String teamId) throws AlreadyExistException, DontHavePermissionException {

        try {
            return manager.getTeamManagers(teamId);
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("object not exist");
            return null;
        }
    }

    @RequestMapping(value="/getTeamPlayers",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getTeamPlayers(@RequestParam(value = "id") String teamId) throws AlreadyExistException, DontHavePermissionException {
        try {
            return manager.getTeamPlayers(teamId);
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("object not exist");
            return null;
        }
    }

    @RequestMapping(value="/getTeamOwners",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getTeamOwners(@RequestParam(value = "id") String teamId) throws AlreadyExistException, DontHavePermissionException {

        try {
            return manager.getTeamOwners(teamId);
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("object not exist");
            return null;
        }
    }

    @RequestMapping(value="/getTeamCoaches",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String> getTeamCoaches(@RequestParam(value = "id") String teamId) throws AlreadyExistException, DontHavePermissionException {
        try {
            return manager.getTeamCoaches(teamId);
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("object not exist");
            return null;
        }
    }

    @RequestMapping(value="/getRefereeGames",method = RequestMethod.GET)
    @ResponseBody
    public HashSet<String> getRefereeGames(@RequestParam(value = "id") String refereeId) throws AlreadyExistException, DontHavePermissionException {

        try {
            return manager.getRefereeGames(refereeId);
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("member not exist");
            return null;
        }
    }
    @RequestMapping(value="/getGamePlayers",method = RequestMethod.GET)

    @ResponseBody
    public  ArrayList<String>  getGamePlayers(@RequestParam(value = "id") String gameId){
        return manager.getGamePlayers(gameId);
    }

    @RequestMapping(value="/getTeamFields",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getTeamFields(@RequestParam(value = "id") String teamId) throws AlreadyExistException, DontHavePermissionException {

        try {
            return manager.getTeamFields(teamId);
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("object not exist");
            return null;
        }
    }

    /*************** team potentials - all the users that can be... ******************/
    @RequestMapping(value="/getPotentialOwners",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getPotentialOwners() {
        return manager.getPotentialOwners();
    }


    @RequestMapping(value="/getPotentialManagers",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getPotentialManagers(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "teamName") String teamName) {
        return manager.getPotentialManagers(id, teamName);

    }

    @RequestMapping(value="/getPotentialPlayers",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getPotentialPlayers(@RequestParam(value = "id") String id,
                                                       @RequestParam(value = "teamName") String teamName) {
        return manager.getPotentialPlayers(id, teamName);
    }




    @RequestMapping(value="/getPotentialCoaches",method = RequestMethod.GET)
    @ResponseBody
    public  ArrayList<String>  getPotentialCoaches(@RequestParam(value = "id") String id,
                                                      @RequestParam(value = "teamName") String teamName) {

        return manager.getPotentialCoaches(id, teamName);
    }
    @RequestMapping(value="/getAllLeagues",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getAllLeagues(@RequestParam(value = "id") String id){
        return manager.getLeaguesIds();

    }

    @RequestMapping(value="/getAllSchedulingPolicies",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getAllSchedulingPolicies(@RequestParam(value = "id") String id){
        return manager.getSchedulingPolicies();

    }

    /******Notifiers*********/
    @RequestMapping(value="/addNotifyFollowEventGame",method = RequestMethod.POST)
    public void addNotifyFollowEventGame(@RequestParam(value = "userMail") String userMail
    ) throws AlreadyExistException, DontHavePermissionException {
        manager.addNotifyFollowEventGame(userMail);
    }

    @RequestMapping(value="/addNotifyGameFinalReport",method = RequestMethod.POST)
    public void addNotifyGameFinalReport(@RequestParam(value = "userMail") String userMail
    ) throws AlreadyExistException, DontHavePermissionException {
        manager.addNotifyGameFinalReport(userMail);
    }


    @RequestMapping(value="/addNotifyCreateNewGame",method = RequestMethod.POST)
    public void addNotifyCreateNewGame(@RequestParam(value = "userMail") String userMail
    ) throws AlreadyExistException, DontHavePermissionException {
        manager.addNotifyCreateNewGame(userMail);
    }


    @RequestMapping(value="/addNotifyScheduleToGame",method = RequestMethod.POST)
    public void addNotifyScheduleToGame(@RequestParam(value = "userMail") String userMail
    ) throws AlreadyExistException, DontHavePermissionException {
        manager.addNotifyScheduleToGame(userMail);
    }


    @RequestMapping(value="/addNotifyAddAssetToTeam",method = RequestMethod.POST)
    public void addNotifyAddAssetToTeam(@RequestParam(value = "userMail") String userMail
    ) throws AlreadyExistException, DontHavePermissionException {
        manager.addNotifyAddAssetToTeam(userMail);
    }

}
