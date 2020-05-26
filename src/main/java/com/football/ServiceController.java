package com.football;

import com.football.Domain.Users.Fan;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

@RestController
@RequestMapping(value="/service")
public class ServiceController {

    @Autowired
    private Manager manager;

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public void register(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "userMail") String userMail,
                       @RequestParam(value = "password") String password
    ) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException {
        manager.register(userName,userMail,password);

    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password)
            throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
       return manager.stringLogIn(id, password);
    }

    @RequestMapping(value="/logout",method = RequestMethod.POST)
    public void logout(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password)
            throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
    }


    /*****************add assets to team ********************/

    @RequestMapping(value="/addManagerToTeam",method = RequestMethod.POST)
    public void addManagerToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "managerId")String managerId,
                                 @RequestParam(value = "teamName")String teamName)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addManagerToTeam(id, teamName, managerId);
    }

    @RequestMapping(value="/addCoachToTeam",method = RequestMethod.POST)
    public void addCoachToTeam(@RequestParam(value = "id") String id,
                               @RequestParam(value = "mailId")String mailId,
                                 @RequestParam(value = "teamName")String teamName)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addCoachToTeam(id, teamName, mailId);
    }

    @RequestMapping(value="/addPlayerToTeam",method = RequestMethod.POST)
    public void addPlayerToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId,
                               @RequestParam(value = "year")int year,
                               @RequestParam(value = "month")int month,
                               @RequestParam(value = "day")int day,
                               @RequestParam(value = "roleInPlayers")String roleInPlayers)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addPlayerToTeam(id, teamName, mailId, year, month, day, roleInPlayers);
    }

    @RequestMapping(value="/addFieldToTeam",method = RequestMethod.POST)
    public void addFieldToTeam(@RequestParam(value = "id") String id,
                               @RequestParam(value = "fieldName")String fieldName,
                               @RequestParam(value = "teamName")String teamName)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addFieldToTeam(id, teamName, fieldName);
    }

    /*****************remove assets to team ********************/

    @DeleteMapping(value="/removeTeamManager")
    public void removeTeamManager(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.removeTeamManager(id, teamName, mailId);
    }

    @RequestMapping(value="/removeTeamCoach",method = RequestMethod.POST)
    public void removeTeamCoach(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.removeTeamCoach(id, teamName, mailId);
    }

    @RequestMapping(value="/removeTeamPlayer",method = RequestMethod.POST)
    public void removeTeamPlayer(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.removeTeamPlayer(id, teamName, mailId);
    }

    @RequestMapping(value="/removeTeamField",method = RequestMethod.POST)
    public void removeTeamField(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName")String teamName,
                                  @RequestParam(value = "fieldName")String fieldName)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.removeTeamField(id, teamName, fieldName);
    }

    @RequestMapping(value="/addNewTeam",method = RequestMethod.POST)
    public void addNewTeam(@RequestParam(value = "id") String id,
                                @RequestParam(value = "teamName")String teamName,
                                @RequestParam(value = "ownerId")String ownerId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
        manager.addNewTeam(id, teamName, ownerId);
    }

    @RequestMapping(value="/schedulingGames",method = RequestMethod.POST)
    public void schedulingGames(@RequestParam(value = "id") String id,
                           @RequestParam(value = "seasonId")String seasonId,
                           @RequestParam(value = "leagueId")String leagueId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
        manager.schedulingGames(id, seasonId, leagueId);
    }

    @RequestMapping(value="/setLeagueByYear",method = RequestMethod.POST)
    public void setLeagueByYear(@RequestParam(value = "id") String id,
                                @RequestParam(value = "seasonId")String seasonId,
                                @RequestParam(value = "leagueId")String leagueId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
        manager.setLeagueByYear(id, seasonId, leagueId);
    }

    //todo: after the client
    @RequestMapping(value="/updateGameEvents",method = RequestMethod.POST)
    public void updateGameEvents(@RequestParam(value = "gameId") String id,
                                 @RequestParam(value = "refereeId") String refereeId,
                                 @RequestParam(value = "year") String year,
                                 @RequestParam(value = "mounth") String mounth,
                                 @RequestParam(value = "day") String day,
                                 @RequestParam(value = "description") String description,
                                 @RequestParam(value = "gameMinute") String gameMinute,
                                 @RequestParam(value = "eventEnum") String eventInGame,
                                 @RequestParam(value = "playersId") String playersId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
        manager.addGameEvents(id, refereeId,  year, mounth,  day ,description,gameMinute,eventInGame,playersId);
    }

    @RequestMapping(value="/closeTeam",method = RequestMethod.DELETE)
    public void closeTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName") String teamName)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
        manager.closeTeam(id, teamName);
    }

    //todo: after the client
//    @RequestMapping(value="/initSystem",method = RequestMethod.DELETE)
//    public void initSystem(@RequestParam(value = "id") String id,
//                          @RequestParam(value = "teamName") String teamName)
//            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
//        manager.closeTeam(id, teamName);
//    }





    /***********************to add************************/


    //return a list of teams names of an owner
    @RequestMapping(value="/getTeamsOfOwner",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getTeamsOfOwner(@RequestParam(value = "id") String ownerId){

        //return manager.getTeamsByOwner(ownerId);
        return new ArrayList<>();
    }

    //get all the teams in the system
    @RequestMapping(value="/getAllTeams",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getAllTeams(@RequestParam(value = "id") String id) {

        //return manager.getAllTeams(ownerId);
        return new ArrayList<>();
    }


    /*****************team getters*******************/
    //the key of the HashMaps- id, value- name

    //get all the users that can be coaches of a team
    @RequestMapping(value="/getTeamManagers",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamManagers(@RequestParam(value = "id") String id,
                                                 @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamManagers(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getTeamPlayers",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamPlayers(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamPlayers(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getTeamOwners",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamOwners(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamOwners(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getTeamCoaches",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamCoaches(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamCoaches(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getRefereeGames",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getRefereeGames(@RequestParam(value = "id") String id,
                                                 @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamCoaches(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getTeamFields",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamFields(@RequestParam(value = "id") String id,
                                                @RequestParam(value = "teamName") String teamName) {

        //return manager.getTeamFields(id, teamName);
        return new HashMap<>();
    }

    /*************** team potentials - all the users that can be... ******************/

    @RequestMapping(value="/getPotentialManagers",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getPotentialManagers(@RequestParam(value = "id") String id,
                                                  @RequestParam(value = "teamName") String teamName) {

        //return manager.getPotentialManagers(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getPotentialPlayers",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getPotentialPlayers(@RequestParam(value = "id") String id,
                                                       @RequestParam(value = "teamName") String teamName) {

        //return manager.getPotentialPlayers(id, teamName);
        return new HashMap<>();
    }

    @RequestMapping(value="/getPotentialCoaches",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getPotentialCoaches(@RequestParam(value = "id") String id,
                                                      @RequestParam(value = "teamName") String teamName) {

        //return manager.getPotentialCoaches(id, teamName);
        return new HashMap<>();
    }






}
