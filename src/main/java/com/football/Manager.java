package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Manager {

    @Autowired
    private DBController dbController;
    @Autowired
    private GuestService guestController;
    @Autowired
    private SystemManagerService systemManagerService;
    @Autowired
    private AssociationDelegateService associationDelegateService;
    @Autowired
    private OwnerService ownerService;


    public Member register(String userName, String userMail, String password) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Member newMember = guestController.signIn(userMail, userName,  password ,new Date());
        return newMember;
    }

    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException {
        Member member =guestController.logIn(userMail, userPassword);
        return member;
    }

    public String stringLogIn(String userMail, String userPassword) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
        Member member = logIn(userMail, userPassword);
        return member.getType();
    }

    public void logOut(String id, String password) {
       //todo!
    }

    /****************add assets****************/

    public void addManagerToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addManager(id,teamName,mailId);
            }
        }
    }

    public void addCoachToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addCoach(id,teamName,mailId);
            }
        }
    }

    public void addPlayerToTeam(String id, String teamName, String mailId, int year, int month, int day, String roleInPlayers) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addPlayer(id,teamName,mailId,year,month,day,roleInPlayers);
            }
        }
    }

    public void addFieldToTeam(String id, String teamName, String fieldName) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addField(id,teamName,fieldName);
            }
        }
    }

    /****************remove assets****************/

    public void removeTeamManager(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.removeManager(id,teamName, mailId);
            }
        }
    }

    public void removeTeamCoach(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.removeCoach(id,teamName, mailId);
            }
        }
    }

    public void removeTeamPlayer(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
             ownerService.removePlayer(id,teamName, mailId);
            }
        }
    }

    public void removeTeamField(String id, String teamName, String fieldId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
            ownerService.removeField(id,teamName, fieldId);
            }
        }
    }

    public void addNewTeam(String id, String teamName, String ownerId) throws DontHavePermissionException, IncorrectInputException, ObjectNotExist, ObjectAlreadyExist, AlreadyExistException, MemberNotExist {
        systemManagerService.addNewTeam(id, teamName, ownerId);
    }

    public void schedulingGames(String id, String seasonId, String leagueId) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException, MemberNotExist, IncorrectInputException {
        systemManagerService.schedulingGames(id,seasonId,leagueId);
    }

    public void setLeagueByYear(String id, String year, String leagueId) throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        associationDelegateService.setLeagueByYear(id,leagueId,year);
    }

    public void closeTeam(String id, String teamName) throws DontHavePermissionException, AlreadyExistException, MemberNotExist, ObjectNotExist, IncorrectInputException {
        systemManagerService.closeTeam(id,teamName);
    }


}
