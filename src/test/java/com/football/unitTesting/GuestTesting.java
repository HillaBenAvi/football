package com.football.unitTesting;

import com.football.DataBase.DBController;
import com.football.Domain.Users.Fan;
import com.football.Domain.Users.GuestService;
import com.football.Domain.Users.Member;
import com.football.Exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class GuestTesting {
    Date date =new Date(1993,10,12);
    DBController dbController = new DBController();
    GuestService guestService = new GuestService();

//    @Rule
//    public final ExpectedException thrown= ExpectedException.none();

    @Test
    public void signIn() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException, MemberNotExist, PasswordDontMatchException {

        /*try to sign in with correct inputs  - result should be correct*/
        Member newMember = guestService.signIn("noa@gmail.com","noa","123", date);
        assertNotNull(newMember);
        assertThat(newMember, instanceOf(Fan.class));
        assertNotEquals(newMember.getPassword() , "123");
    }

    @Test(expected=AlreadyExistException.class)
    public void signInAlreadyExist() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        guestService.signIn("noa@gmail.com","noa","123", date);
        /*try to sign in but he is a member now - result should be negative*/
        Member newMember = guestService.signIn("noa@gmail.com","noa","123", date);
        assertNull(newMember);
    }

    @Test(expected=IncorrectInputException.class)
    public void signInIncorrectMail() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        /*try to sign in with incorrect inputs  - result should be negative*/
        Member newMember = guestService.signIn( "noa.com","noa", "123", date);
        assertNull(newMember);
    }
    @Test(expected=IncorrectInputException.class)
    public void signInIncorrectMail2() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        /*try to sign in with incorrect inputs  - result should be negative*/
        Member newMember = guestService.signIn( "","noa", "123", date);
        assertNull(newMember);
    }
    @Test(expected=IncorrectInputException.class)
    public void signInIncorrectMail3() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {

        /*try to sign in with incorrect inputs  - result should be negative*/
        Member newMember = guestService.signIn( "@.", "noa","123", date);
        assertNull(newMember);
    }
    @Test(expected=IncorrectInputException.class)
    public void signInIncorrectPass() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        /*try to sign in with incorrect inputs  - result should be negative*/
        Member newMember = guestService.signIn( "noa@gmail.com", "noa","91@*", date);
    }
    @Test(expected=IncorrectInputException.class)
    public void signInIncorrectPass2() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        /*try to sign in with incorrect inputs  - result should be negative*/
        Member newMember = guestService.signIn( "noa@gmail.com", "noa","", date);
        assertNull(newMember);
    }

    @Test
    public void logIn() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Fan fan = (Fan)guestService.signIn("noa@gmail.com","noa","123", date);

        /*try to log in with correct details - result should be positive*/
        Member member = guestService.logIn("noa@gmail.com","123");
        assertNotNull(member);
        assertEquals("noa",member.getName());
        assertThat(fan, instanceOf(Fan.class));
    }
    @Test(expected=MemberNotExist.class)
    public void logInWithException() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Fan fan = (Fan)guestService.signIn("noa@gmail.com","noa","123", date);

        /*try to log in with not exist member - result should be negative*/
        Member member = guestService.logIn("notExist@gmail.com","123");
        assertNull(member);
    }

    @Test(expected=PasswordDontMatchException.class)
    public void logInIncorrectPassword() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Fan fan = (Fan)guestService.signIn("noa@gmail.com","noa","123", date);
        /*try to log in with different password - result should be negative*/
        guestService.logIn("noa@gmail.com","1223");
    }
}