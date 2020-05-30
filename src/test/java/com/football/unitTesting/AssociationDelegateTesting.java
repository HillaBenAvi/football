//package com.football.unitTesting;
//
//import com.football.Exception.DontHavePermissionException;
//import com.football.Exception.IncorrectInputException;
//import com.football.Manager;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Date;
//
//import static org.hamcrest.core.IsInstanceOf.instanceOf;
//import static org.junit.Assert.assertThat;
//
//public class AssociationDelegateTesting {
//
//    Manager controller ;//= new SystemController("controller Test");
//    Date birthDate = new Date(1995, 3, 6);
//
//    @Before
//    public void init() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist, PasswordDontMatchException {
//        controller = new SystemController("");
//        controller.deleteDBcontroller();
//        controller.signIn("dani","associationDelegate@gmail.com","abc", birthDate);
//        controller.logIn("admin@gmail.com","123");
//        controller.addAssociationDelegate("associationDelegate@gmail.com");
//        controller.logOut();
//    }
//
//
////    @Test
////    public void insertSchedulingPolicyTest() throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
////        controller.logIn("associationDelegate@gmail.com", "abc");
////        String league = "women league";
////        String season = "2020";
////        String sPolicy = "All teams play each other once";
////        //check if it is used
////    }
//
//    @Test
//    public void changeScorePolicyTest() throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist, PasswordDontMatchException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "womenLeagueG";
//        controller.setLeague(league);
//        String season = "2020";
//        controller.setLeagueByYear(league, season);
//        String sWinning = "10";
//        String sDraw = "5";
//        String sLosing = "2";
//        controller.changeScorePolicy(league, season, sWinning, sDraw, sLosing);
//        ScorePolicy scorePolicy = (ScorePolicy) controller.getScorePolicy(league, season);
//        double win = Double.parseDouble(sWinning);
//        double draw = Double.parseDouble(sDraw);
//        double lose = Double.parseDouble(sLosing);
//        double delta = 0.0001;
//        assertEquals(scorePolicy.getScoreToWinningTeam(),win, delta);
//        assertEquals(scorePolicy.getScoreToDrawGame(),draw, delta);
//        assertEquals(scorePolicy.getScoreToLosingTeam(),lose, delta);
//    }
//
//
//    @Test
//    public void addRefereeToLeagueInSeason() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, ObjectNotExist, MemberNotExist, PasswordDontMatchException, MemberAlreadyExistException {
//        controller.signIn("referee","referee@gmail.com","123", birthDate);
//        controller.logIn("admin@gmail.com","123");
//        controller.addReferee("referee@gmail.com",false);
//        controller.logOut();
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "womenLeagueA";
//        controller.setLeague(league);
//        String season = "2020";
//        controller.setLeagueByYear(league, season);
//        String refereeName = "referee@gmail.com";
//        controller.addRefereeToLeagueInSeason(league, season, refereeName);
//        assertTrue(controller.getRefereesInLeagueInSeason(league,season).containsKey(refereeName));
//
//    }
//
//    @Test(expected = ObjectNotExist.class)
//    public void addRefereeToLeagueInSeasonTrowException() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, ObjectNotExist, MemberNotExist, PasswordDontMatchException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "womenLeagueE";
//        controller.setLeague(league);
//        String season = "2020";
//        controller.setLeagueByYear(league, season);
//        String refereeName = "Noa";
//        controller.addRefereeToLeagueInSeason("men league", season, refereeName);
//    }
//
//    @Test
//    public void setSchedulingPolicyToLeagueInSeasonTest() throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, MemberNotExist, PasswordDontMatchException, AlreadyExistException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "womenLeagueA";
//        controller.setLeague(league);
//        String season = "2020";
//        controller.setLeagueByYear(league, season);
//        String policyName = "All teams play each other twice";
//        controller.setSchedulingPolicyToLeagueInSeason(league, season, policyName);
//        assertThat(controller.getSchedulingPolicyInLeagueInSeason(league,season),instanceOf(SchedulingPolicyAllTeamsPlayTwice.class));
//    }
//
//    @Test(expected = IncorrectInputException.class)
//    public void setSchedulingPolicyToLeagueInSeasonTestThrownException() throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, MemberNotExist, PasswordDontMatchException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "womenLeagueC";
//        String season = "2020";
//        String policyName = "All teams play each other every day";
//        controller.setSchedulingPolicyToLeagueInSeason(league, season, policyName);
//    }
//
//}
//
