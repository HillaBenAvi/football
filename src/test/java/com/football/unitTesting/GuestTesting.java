//package com.football.unitTesting;
//
//import DataBase.DBController;
//import Domain.Users.Fan;
//import Domain.Users.Guest;
//import Domain.Users.Member;
//import Exception.*;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import java.util.Date;
//
//import static org.hamcrest.core.IsInstanceOf.instanceOf;
//import static org.junit.Assert.*;
//
//public class GuestTesting {
//    Date birthdate=new Date(1993,10,12);
//    // SystemController controller = new SystemController("test controller");
//    DBController dbController = DBController.getInstance();
//    Guest guest = new Guest(dbController,birthdate);
//
//    @Before
//    public void init() {
//        dbController.deleteAll();
//    }
//    @Rule
//    public final ExpectedException thrown= ExpectedException.none();
//
//    @Test
//    public void signIn() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException, MemberNotExist, PasswordDontMatchException {
//
//        /*try to sign in with correct inputs  - result should be correct*/
//        Member newMember = guest.signIn("noa@gmail.com","noa","123", birthdate);
//        assertNotNull(newMember);
//        assertThat(newMember, instanceOf(Fan.class));
//        assertNotEquals(newMember.getPassword() , "123");
//    }
//    @Test
//    public void signInAlreadyExist() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(AlreadyExistException.class);
//        guest.signIn("noa@gmail.com","noa","123", birthdate);
//
//        /*try to sign in but he is a member now - result should be negative*/
//        Member newMember = guest.signIn("noa@gmail.com","noa","123", birthdate);
//        assertNull(newMember);
//    }
//    @Test
//    public void signInIncorrectMail() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(IncorrectInputException.class);
//
//        /*try to sign in with incorrect inputs  - result should be negative*/
//        Member newMember = guest.signIn( "noa.com","noa", "123", birthdate);
//        assertNull(newMember);
//    }
//    @Test
//    public void signInIncorrectMail2() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(IncorrectInputException.class);
//
//        /*try to sign in with incorrect inputs  - result should be negative*/
//        Member newMember = guest.signIn( "","noa", "123", birthdate);
//        assertNull(newMember);
//    }
//    @Test
//    public void signInIncorrectMail3() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(IncorrectInputException.class);
//
//        /*try to sign in with incorrect inputs  - result should be negative*/
//        Member newMember = guest.signIn( "@.", "noa","123", birthdate);
//        assertNull(newMember);
//    }
//    @Test
//    public void signInIncorrectPass() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(IncorrectInputException.class);
//
//        /*try to sign in with incorrect inputs  - result should be negative*/
//        Member newMember = guest.signIn( "noa@gmail.com", "noa","91@*", birthdate);
//    }
//    @Test
//    public void signInIncorrectPass2() throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(IncorrectInputException.class);
//
//        /*try to sign in with incorrect inputs  - result should be negative*/
//        Member newMember = guest.signIn( "noa@gmail.com", "noa","", birthdate);
//        assertNull(newMember);
//    }
//
//    @Test
//    public void logIn() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        Fan fan = (Fan)guest.signIn("noa@gmail.com","noa","123", birthdate);
//
//        /*try to log in with correct details - result should be positive*/
//        Member member = guest.logIn("noa@gmail.com","123");
//        assertNotNull(member);
//        assertEquals("noa",member.getName());
//        assertThat(fan, instanceOf(Fan.class));
//    }
//    @Test
//    public void logInWithException() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(MemberNotExist.class);
//
//        Fan fan = (Fan)guest.signIn("noa@gmail.com","noa","123", birthdate);
//
//        /*try to log in with not exist member - result should be negative*/
//        Member member = guest.logIn("notExist@gmail.com","123");
//        assertNull(member);
//    }
//    @Test
//    public void logInIncorrectPassword() throws MemberNotExist, PasswordDontMatchException, IncorrectInputException, AlreadyExistException, DontHavePermissionException {
//        thrown.expect(PasswordDontMatchException.class);
//        Fan fan = (Fan)guest.signIn("noa@gmail.com","noa","123", birthdate);
//
//        /*try to log in with different password - result should be negative*/
//        guest.logIn("noa@gmail.com","1223");
//    }
//}