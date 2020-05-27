package com.football;

import com.football.Domain.Users.Fan;
import com.football.Exception.*;
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
            return "you dont have the permission to sign in";
        }
        return "you register successfully";
    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password) {
        try{
            return  manager.stringLogIn(id, password);
        }
        catch (PasswordDontMatchException e){
            return "incorrect Password";
        }
        catch (MemberNotExist e){
            return "the member does not exist";
        }
        catch (DontHavePermissionException e){
            return "you dont have the permission to sign in";
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
                           @RequestParam(value = "seasonId")String seasonId,
                           @RequestParam(value = "leagueId")String leagueId) {
        try {
            manager.schedulingGames(id, seasonId, leagueId);
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
                                @RequestParam(value = "leagueId")String leagueId){
        try {
            manager.setLeagueByYear(id, seasonId, leagueId);
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        } catch (AlreadyExistException e) {
            return "the object already exist";
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        }
        return "Season set successfully to League";
    }

    //todo: merge this with master
    @RequestMapping(value="/updateGameEvents",method = RequestMethod.POST)
    public String updateGameEvents(@RequestParam(value = "gameId") String id,
                                 @RequestParam(value = "refereeId") String refereeId,
                                 @RequestParam(value = "year") String year,
                                 @RequestParam(value = "mounth") String mounth,
                                 @RequestParam(value = "day") String day,
                                 @RequestParam(value = "description") String description,
                                 @RequestParam(value = "gameMinute") String gameMinute,
                                 @RequestParam(value = "eventEnum") String eventInGame,
                                 @RequestParam(value = "playersId") String playersId) {
        try {
            return manager.addGameEvents(id, refereeId,  year, mounth,  day ,description,gameMinute,eventInGame,playersId);
        } catch (MemberNotExist memberNotExist) {
            return "the member does not exist";
        } catch (DontHavePermissionException e) {
            return "you dont have the permission";
        } catch (ObjectNotExist objectNotExist) {
            return "the object does not exist";
        }

    }

    @RequestMapping(value="/closeTeam",method = RequestMethod.DELETE)
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
    public HashMap<String,String> getTeamManagers(@RequestParam(value = "id") String teamId){

        try {
            return manager.getTeamManagers(teamId);
        } catch (ObjectNotExist objectNotExist) {
            return null;
        }
    }

    @RequestMapping(value="/getTeamPlayers",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamPlayers(@RequestParam(value = "id") String teamId) {
        try {
            return manager.getTeamPlayers(teamId);
        } catch (ObjectNotExist objectNotExist) {
            return null;
        }
    }

    @RequestMapping(value="/getTeamOwners",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamOwners(@RequestParam(value = "id") String teamId) {

        try {
            return manager.getTeamOwners(teamId);
        } catch (ObjectNotExist objectNotExist) {
            return null;
        }
    }

    @RequestMapping(value="/getTeamCoaches",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamCoaches(@RequestParam(value = "id") String teamId){
        try {
            return manager.getTeamCoaches(teamId);
        } catch (ObjectNotExist objectNotExist) {
            return null;
        }
    }

    @RequestMapping(value="/getRefereeGames",method = RequestMethod.GET)
    @ResponseBody
    public HashSet<String> getRefereeGames(@RequestParam(value = "id") String refereeId){

        try {
            return manager.getRefereeGames(refereeId);
        } catch (MemberNotExist memberNotExist) {
            return null;
        }
    }
    @RequestMapping(value="/getGamePlayers",method = RequestMethod.GET)

    @ResponseBody
    public HashMap<String,String> getGamePlayers(@RequestParam(value = "id") String gameId){
        return manager.getGamePlayers(gameId);
    }

    @RequestMapping(value="/getTeamFields",method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String,String> getTeamFields(@RequestParam(value = "id") String teamId) {

        try {
            return manager.getTeamFields(teamId);
        } catch (ObjectNotExist objectNotExist) {
            return null;
        }
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

        //return manager.get PotentialCoaches(id, teamName);
        return new HashMap<>();
    }


}
