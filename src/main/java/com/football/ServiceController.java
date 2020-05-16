package com.football;

import com.football.Domain.Users.Fan;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*****************add assets to team ********************/

    @RequestMapping(value="/addManagerToTeam",method = RequestMethod.POST)
    public void addManagerToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addManagerToTeam(id, teamName, mailId);
    }

    @RequestMapping(value="/addCoachToTeam",method = RequestMethod.POST)
    public void addCoachToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.addCoachToTeam(id, teamName, mailId);
    }

    @RequestMapping(value="/addCoachToTeam",method = RequestMethod.POST)
    public void addCoachToTeam(@RequestParam(value = "id") String id,
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
                                 @RequestParam(value = "teamName")String teamName,
                                 @RequestParam(value = "fieldName")String fieldName)
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
                                  @RequestParam(value = "mailId")String mailId)
            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException {
        manager.removeTeamField(id, teamName, mailId);
    }




}
