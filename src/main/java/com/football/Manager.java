package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Users.Fan;
import com.football.Domain.Users.Guest;
import com.football.Domain.Users.Member;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class Manager {

    @Autowired
    private DBController dbController;


    public Member signIn(String userName, String userMail, String password) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Guest guest = new Guest();//new Date(1, 1, 1));

        String encryptPassword = password;//TODO securityMachine.encrypt(password);
        Fan newMember = new Fan(userName, userMail, encryptPassword, null);
        dbController.addFan(guest,newMember);
        return newMember;//guest.signIn(userMail, userName, password);
    }

/*
    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException {
        if(connectedUser==null)
        {
            Guest guest=new Guest(dbController,new Date(1995,2,1));
            this.connectedUser=guest.logIn(userMail,userPassword);
            return (Member) this.connectedUser;
        }
        this.connectedUser =((Guest)this.connectedUser).logIn(userMail, userPassword);
        return (Member) this.connectedUser;
    }
    public String login(String userMail, String userPassword) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
        logIn(userMail, userPassword);
        return ((Member) this.connectedUser).getType();
    }
    */
}
