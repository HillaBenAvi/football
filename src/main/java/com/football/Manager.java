package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Users.Fan;
import com.football.Domain.Users.Guest;
import com.football.Domain.Users.GuestController;
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
    @Autowired
    private GuestController guestController;


    public Member register(String userName, String userMail, String password) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Member newMember = guestController.signIn(userName, userMail, password);
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


}
