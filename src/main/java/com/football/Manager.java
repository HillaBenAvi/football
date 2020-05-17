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

    private GuestService guestController;


    public Member register(String userName, String userMail, String password) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Member newMember = guestController.signIn(userName, userMail, password ,new Date());
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


    /****************add assets****************/

    public void addManagerToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).addManager(teamName, mailId);
            }
        }
    }

    public void addCoachToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).addCoach(teamName, mailId);
            }
        }
    }

    public void addPlayerToTeam(String id, String teamName, String mailId, int year, int month, int day, String roleInPlayers) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).addPlayer(teamName, mailId, year, month, day, roleInPlayers);
            }
        }
    }

    public void addFieldToTeam(String id, String teamName, String fieldName) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).addField(teamName, fieldName);
            }
        }
    }

    /****************remove assets****************/

    public void removeTeamManager(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).removeManager(teamName, mailId);
            }
        }
    }

    public void removeTeamCoach(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).removeCoach(teamName, mailId);
            }
        }
    }

    public void removeTeamPlayer(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).removePlayer(teamName, mailId);
            }
        }
    }

    public void removeTeamField(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ((Owner) member).removeField(teamName, mailId);
            }
        }
    }


}
