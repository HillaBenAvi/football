//package com.football.unitTesting;
//
//import com.football.DataBase.DBController;
//import com.football.Domain.Users.Fan;
//import com.football.Domain.Users.Owner;
//import com.football.Domain.Users.OwnerService;
//import com.football.Exception.*;
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
//public class OwnerTesting {
//    OwnerService controller = new OwnerService();
//    DBController dbController = new DBController();
//    Date date = new Date(1993, 8, 11);
//
//    @Before
//    public void init() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException {
//        controller.register("player0", "p0@gmail.com", "1", date);
//        controller.signIn("manager", "manager@gmail.com", "1", date);
//        controller.signIn("owner", "owner@gmail.com", "1", date);
//    }
//
//    @Rule
//    public final ExpectedException thrown = ExpectedException.none();
//
//    @Test
//    public void addManagerNotExistInSystem() throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException, IncorrectInputException, AlreadyExistException, ObjectNotExist, NoEnoughMoney, ObjectAlreadyExist {
//        thrown.expect(MemberNotExist.class);
//
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        //try to add manager not exist
//        controller.addManager("team", "newManager@gmail.com");
//
//        controller.logOut();
//        controller.signIn("M", "newManager@gmail.com", "123", date);
//        controller.logIn("owner@gmail.com", "1");
//        controller.addManager("teamNotExist", "newManager@gmail.com");
//    }
//
//    @Test
//    public void addManagerToTeamNotExistInSystem() throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException, IncorrectInputException, AlreadyExistException, ObjectNotExist, NoEnoughMoney, ObjectAlreadyExist {
//        thrown.expect(ObjectNotExist.class);
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.signIn("M", "newManager@gmail.com", "123", date);
//        controller.logIn("owner@gmail.com", "1");
//        controller.addManager("teamNotExist", "newManager@gmail.com");
//    }
//
//    @Test
//    public void addPlayerAlreadyExistInSystem() throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException, IncorrectInputException, AlreadyExistException, ObjectNotExist, NoEnoughMoney, ObjectAlreadyExist {
//        thrown.expect(ObjectNotExist.class);
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        //try to add player already exist
//        controller.addPlayer("player0", "p0@gmail.com", 1993, 8, 11, "hilla");
//
//    }
//
//    @Test
//    public void addFieldToTeamNotExist() throws DontHavePermissionException, ObjectNotExist, MemberNotExist, NoEnoughMoney, AlreadyExistException, PasswordDontMatchException, IncorrectInputException, ObjectAlreadyExist {
//        thrown.expect(ObjectNotExist.class);
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        controller.addField("teamNotExist", "f");
//    }
//
//    @Test
//    public void removePlayerMakeFan() throws DontHavePermissionException, ObjectNotExist, MemberNotExist, AlreadyExistException, NoEnoughMoney, IncorrectInputException, PasswordDontMatchException, ObjectAlreadyExist {
//
//        /* init - create team  */
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//        controller.addPlayer("p0@gmail.com", "team", 1993, 8, 11, "hilla");
//
//        controller.removePlayer("team", "p0@gmail.com");
//        assertThat(controller.getRoles().get("p0@gmail.com"), instanceOf(Fan.class));
//    }
//
//    @Test
//    public void addOwnerDoNotExist() throws DontHavePermissionException, ObjectNotExist, MemberNotExist, NoEnoughMoney, AlreadyExistException, PasswordDontMatchException, IncorrectInputException, ObjectAlreadyExist {
//        thrown.expect(MemberNotExist.class);
//
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        controller.addNewOwner("team", "ownerNotExist@gmail.com");
//    }
//
//    @Test
//    public void temporaryTeamClosingTest() throws DontHavePermissionException, MemberNotExist, PasswordDontMatchException, ObjectNotExist, ObjectAlreadyExist, AlreadyExistException, IncorrectInputException {
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        //status should be false
//        controller.temporaryTeamClosing("team");
//        assertTrue(controller.getTeams().containsKey("team"));
//        assertFalse(controller.getTeams().get("team").getStatus());
//    }
//
//    @Test
//    public void reopenClosedTeamTest() throws DontHavePermissionException, MemberNotExist, PasswordDontMatchException, ObjectNotExist, ObjectAlreadyExist, AlreadyExistException, IncorrectInputException {
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//
//        //status should be true
//        controller.reopenClosedTeam("team");
//        assertTrue(controller.getTeams().containsKey("team"));
//        assertTrue(controller.getTeams().get("team").getStatus());
//    }
//
//    @Test
//    public void addIncomeToTeam() throws DontHavePermissionException, ObjectAlreadyExist, MemberNotExist, AlreadyExistException, ObjectNotExist, PasswordDontMatchException, IncorrectInputException, NoEnoughMoney, AccountNotExist {
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//        controller.setMoneyToAccount("team", 500);
//
//        controller.addInCome("team", "TestShouldWork", 100);
//        int left = (int) controller.getAccountBalance("team");
//        assertEquals(600, left);
//    }
//
//    @Test
//    public void addOutComeToTeam() throws DontHavePermissionException, ObjectAlreadyExist, MemberNotExist, AlreadyExistException, ObjectNotExist, PasswordDontMatchException, IncorrectInputException, NoEnoughMoney, AccountNotExist {
//
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//        controller.setMoneyToAccount("team", 100);
//
//
//        controller.addOutCome("team", "TestShouldWork", 50);
//        int left = (int) controller.getAccountBalance("team");
//        assertEquals(50, left);
//    }
//
//    @Test
//    public void addIncomeToTeamNoEnoughBalance() throws DontHavePermissionException, ObjectAlreadyExist, MemberNotExist, AlreadyExistException, ObjectNotExist, PasswordDontMatchException, IncorrectInputException, NoEnoughMoney, AccountNotExist {
//        thrown.expect(NoEnoughMoney.class);
//
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//        controller.logIn("owner@gmail.com", "1");
//        controller.setMoneyToAccount("team", 500);
//
//        controller.addInCome("team", "TestShouldWork", 1000);
//    }
//
//    @Test
//    public void addPlayerNoEnoughMoney() throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException, IncorrectInputException, AlreadyExistException, ObjectNotExist, NoEnoughMoney, ObjectAlreadyExist {
//        thrown.expect(NoEnoughMoney.class);
//
//        controller.logIn("admin@gmail.com", "123");
//        controller.addTeam("team", "owner@gmail.com");
//        controller.logOut();
//
//        controller.logIn("owner@gmail.com", "1");
//        controller.setMoneyToAccount("team", 30);
//
//        //try to add player already exist
//        controller.addPlayer("player0", "team", 1993, 8, 11, "hilla");
//
//    }
//
//
//}