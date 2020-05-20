package com.football;

import com.football.Domain.Users.Fan;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    @Autowired
    private Manager manager;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "userName") String userName,
                           @RequestParam(value = "userMail") String userMail,
                           @RequestParam(value = "password") String password) {
        try {
            manager.register(userName, userMail, password);
            return "0";
        } catch (IncorrectInputException incorrectInputException) {
            return "1Incorrect Input Exception";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }


    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password") String password){
        try {
            return manager.stringLogIn(id, password);
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(@RequestParam(value = "id") String id,
                         @RequestParam(value = "password") String password) {
        manager.logOut(id, password);
        return "0";
    }

    /*****************add assets to team ********************/

    @RequestMapping(value = "/addManagerToTeam", method = RequestMethod.POST)
    public String addManagerToTeam(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "teamName") String teamName,
                                   @RequestParam(value = "mailId") String mailId) {
        try {
            manager.addManagerToTeam(id, teamName, mailId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @RequestMapping(value = "/addCoachToTeam", method = RequestMethod.POST)
    public String addCoachToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName") String teamName,
                                 @RequestParam(value = "mailId") String mailId) {
        try {
            manager.addCoachToTeam(id, teamName, mailId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @RequestMapping(value = "/addPlayerToTeam", method = RequestMethod.POST)
    public String addPlayerToTeam(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName") String teamName,
                                  @RequestParam(value = "mailId") String mailId,
                                  @RequestParam(value = "year") int year,
                                  @RequestParam(value = "month") int month,
                                  @RequestParam(value = "day") int day,
                                  @RequestParam(value = "roleInPlayers") String roleInPlayers) {
        try {
            manager.addPlayerToTeam(id, teamName, mailId, year, month, day, roleInPlayers);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @RequestMapping(value = "/addFieldToTeam", method = RequestMethod.POST)
    public String addFieldToTeam(@RequestParam(value = "id") String id,
                                 @RequestParam(value = "teamName") String teamName,
                                 @RequestParam(value = "fieldName") String fieldName) {
        try {
            manager.addFieldToTeam(id, teamName, fieldName);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    /*****************remove assets to team ********************/

    @DeleteMapping(value = "/removeTeamManager")
    public String removeTeamManager(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "teamName") String teamName,
                                    @RequestParam(value = "mailId") String mailId) {
        try {
            manager.removeTeamManager(id, teamName, mailId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @DeleteMapping(value = "/removeTeamCoach")
    public String removeTeamCoach(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName") String teamName,
                                  @RequestParam(value = "mailId") String mailId) {
        try {
            manager.removeTeamCoach(id, teamName, mailId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @DeleteMapping(value = "/removeTeamPlayer")
    public String removeTeamPlayer(@RequestParam(value = "id") String id,
                                   @RequestParam(value = "teamName") String teamName,
                                   @RequestParam(value = "mailId") String mailId) {
        try {
            manager.removeTeamPlayer(id, teamName, mailId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @DeleteMapping(value = "/removeTeamField")
    public String removeTeamField(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "teamName") String teamName,
                                  @RequestParam(value = "fieldId") String fieldId) {
        try {
            manager.removeTeamField(id, teamName, fieldId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (PasswordDontMatchException passwordDontMatchException) {
            return "1Password Dont Match Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @RequestMapping(value = "/addNewTeam", method = RequestMethod.POST)
    public String addNewTeam(@RequestParam(value = "id") String id,
                             @RequestParam(value = "teamName") String teamName,
                             @RequestParam(value = "ownerId") String ownerId) {
        try {
            manager.addNewTeam(id, teamName, ownerId);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        } catch (IncorrectInputException incorrectInputException) {
            return "1Incorrect Input Exception";
        } catch (ObjectAlreadyExist objectAlreadyExist) {
            return "1Object Already Exist";
        }
    }

    @RequestMapping(value = "/schedulingGames", method = RequestMethod.POST)
    public String schedulingGames(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "seasonId") String seasonId,
                                  @RequestParam(value = "leagueId") String leagueId) {
        try {
            manager.schedulingGames(id, seasonId, leagueId);
            return "10";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        } catch (IncorrectInputException incorrectInputException) {
            return "1Incorrect Input Exception";
        }
    }

    @RequestMapping(value = "/setLeagueByYear", method = RequestMethod.POST)
    public String setLeagueByYear(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "yearId") String yearId,
                                  @RequestParam(value = "leagueId") String leagueId) {
        try {
            manager.setLeagueByYear(id, yearId, leagueId);
            return "10";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        }
    }

    //todo: after the client
//    @RequestMapping(value="/updateGameEvents",method = RequestMethod.POST)
//    public void updateGameEvents(@RequestParam(value = "id") String id,
//                                 @RequestParam(value = "leagueId") String leagueId)
//            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
//        manager.setLeagueByYear(id, seasonId, leagueId);
//    }

    @RequestMapping(value = "/closeTeam", method = RequestMethod.DELETE)
    public String closeTeam(@RequestParam(value = "id") String id,
                            @RequestParam(value = "teamName") String teamName) {
        try {
            manager.closeTeam(id, teamName);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        } catch (IncorrectInputException incorrectInputException) {
            return "1Incorrect Input Exception";
        }
    }

    //todo: after the client
//    @RequestMapping(value="/initSystem",method = RequestMethod.DELETE)
//    public void initSystem(@RequestParam(value = "id") String id,
//                          @RequestParam(value = "teamName") String teamName)
//            throws DontHavePermissionException, PasswordDontMatchException, MemberNotExist, AlreadyExistException, ObjectNotExist, IncorrectInputException, ObjectAlreadyExist {
//        manager.closeTeam(id, teamName);
//    }


    @RequestMapping(value = "/changeScorePolicy", method = RequestMethod.POST)
    public String changeScorePolicy(@RequestParam(value = "id") String id,
                                    @RequestParam(value = "league") String league,
                                    @RequestParam(value = "season") String season,
                                    @RequestParam(value = "sWinning") String sWinning,
                                    @RequestParam(value = "sDraw") String sDraw,
                                    @RequestParam(value = "sLosing") String sLosing) {
        try {
            manager.changeScorePolicy(id, league, season, sWinning, sDraw, sLosing);
            return "0";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        } catch (IncorrectInputException incorrectInputException) {
            return "1Incorrect Input Exception";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }

    @RequestMapping(value = "/insertSchedulingPolicy", method = RequestMethod.POST)
    public String insertSchedulingPolicy(@RequestParam(value = "id") String id,
                                         @RequestParam(value = "league") String league,
                                         @RequestParam(value = "season") String season,
                                         @RequestParam(value = "sPolicy") String sPolicy) {
        try {
            manager.insertSchedulingPolicy(id, league, season, sPolicy);
            return "0";
        } catch (DontHavePermissionException dontHavePermissionException) {
            return "1Dont Have Permission Exception";
        } catch (MemberNotExist memberNotExist) {
            return "1Member Not Exist";
        } catch (ObjectNotExist objectNotExist) {
            return "1Object Not Exist";
        } catch (AlreadyExistException alreadyExistException) {
            return "1Already Exist Exception";
        }
    }
}

