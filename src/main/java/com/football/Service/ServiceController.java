package com.football.Service;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Team;
import com.football.Domain.Users.*;
import com.football.Exception.*;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * singleton
 */
public class ServiceController {
    private static final ServiceController instance = new ServiceController();
    SystemController systemController ;
    public static ServiceController getInstance() {
        return instance;
    }
    private ServiceController(){
        this.systemController = new SystemController("DomainController");
    }

    public String login(String id, String pass) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
        String type = systemController.login(id,pass);
        return type;
    }
    public void signIn(String userName, String userMail, String password , Date birthDate) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException {
        systemController.signIn(userName, userMail,  password , birthDate);
    }
    public void logOut(){
        systemController.logOut();
    }
    public LinkedList<String> removeSystemManagerComboBox() throws DontHavePermissionException {
        HashMap<String, SystemManager> systemmanagers = systemController.getSystemManager();
        LinkedList<String> linkedList = new LinkedList<>(systemmanagers.keySet());
        return linkedList;
    }

    public LinkedList<String> addSystemManagerComboBox() throws DontHavePermissionException {
        HashMap<String, Fan> fans = systemController.getFans();
        LinkedList<String> linkedList = new LinkedList<>(fans.keySet());
        return linkedList;
    }

    public LinkedList<String> addAssociationDeligateComboBox() throws DontHavePermissionException {
        HashMap<String, Fan> fans = systemController.getFans();
        LinkedList<String> linkedList = new LinkedList<>(fans.keySet());
        return linkedList;
    }

    public LinkedList<String> removeAssociationDeligateComboBox() throws DontHavePermissionException {
        HashMap<String, AssociationDelegate> associationDelegate = systemController.getAssociationDelegates();
        LinkedList<String> linkedList = new LinkedList<>(associationDelegate.keySet());
        return linkedList;
    }

    public LinkedList<String> addRefereeComboBox() throws DontHavePermissionException {
        HashMap<String, Fan> fans = systemController.getFans();
        LinkedList<String> linkedList = new LinkedList<>(fans.keySet());
        return linkedList;
    }

    public LinkedList<String> removeRefereeComboBox() {
        HashMap<String, Referee> referees = systemController.getReferees();
        LinkedList<String> linkedList = new LinkedList<>(referees.keySet());
        return linkedList;
    }

    public LinkedList<String> removeMemberComboBox() throws DontHavePermissionException {
        HashMap<String, Member> members = systemController.getMembers();
        LinkedList<String> linkedList = new LinkedList<>(members.keySet());
        return linkedList;
    }


    public LinkedList<String> removeTeamComboBox() {
        HashMap<String, Team> team = systemController.getTeams();
        LinkedList<String> linkedList = new LinkedList<>(team.keySet());
        return linkedList;
    }

    public LinkedList<String> addTeamComboBox() throws DontHavePermissionException {
        HashMap<String, Role> owners = systemController.getOwnersAndFans();
        LinkedList<String> linkedList = new LinkedList<>(owners.keySet());
        return linkedList;
    }

    public boolean matchPass(char[] pass1, char[] pass2) {
        if(pass1.length == pass2.length){
            for(int i=0; i< pass1.length ; i++){
                if(pass1[i] != pass2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    public String getUserName() {
        return this.systemController.getConnectedUser().getName();
    }
}
