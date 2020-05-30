package com.football.unitTesting;

import com.football.DataBase.DBController;
import com.football.Domain.League.*;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


public class AssociationDelegateTesting {

    DBController dbController = new DBController();;
    SystemManagerService systemManagerService = new SystemManagerService();
    AssociationDelegateService associationDelegateService = new AssociationDelegateService();

    @Before
    public void init() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist, PasswordDontMatchException {
       // dbController.addSystemManager("");
        systemManagerService.addAssociationDelegate("hillapet@post.bgu.ac.il","associationDelegate@gmail.com");
        //controller.logIn("admin@gmail.com","123");
        //controller.addAssociationDelegate("associationDelegate@gmail.com");
       // controller.logOut();
    }


//    @Test
//    public void insertSchedulingPolicyTest() throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        String league = "women league";
//        String season = "2020";
//        String sPolicy = "All teams play each other once";
//        //check if it is used
//    }
//
//    @Test
//    public void changeScorePolicyTest() throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist, PasswordDontMatchException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
//        //String league = "womenLeagueG";
//        //controller.setLeague(league);
//        String league = controller.getLeaguesIds().get(0);
//        String season = "2020";
//        controller.setLeagueByYear("associationDelegate@gmail.com",league, season);
//        String sWinning = "10";
//        String sDraw = "5";
//        String sLosing = "2";
//        controller.changeScorePolicy("associationDelegate@gmail.com",league, season, sWinning, sDraw, sLosing);
//        ScorePolicy scorePolicy = dbController.getLeagueInSeason(league, season).getScorePolicy();
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
////    @Test
////    public void addRefereeToLeagueInSeason() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, ObjectNotExist, MemberNotExist, PasswordDontMatchException, MemberAlreadyExistException {
////        controller.register("referee","referee@gmail.com","123");
////        controller.logIn("hillapet@post.bgu.ac.il","123456");
////        dbController.addReferee(new MainReferee(),"referee@gmail.com");
////        controller.logOut();
////        controller.logIn("associationDelegate@gmail.com", "abc");
////        String league = "womenLeagueA";
////        controller.setLeague(league);
////        String season = "2020";
////        controller.setLeagueByYear(league, season);
////        String refereeName = "referee@gmail.com";
////        controller.addRefereeToLeagueInSeason(league, season, refereeName);
////        assertTrue(controller.getRefereesInLeagueInSeason(league,season).containsKey(refereeName));
////
////    }
////
////    @Test(expected = ObjectNotExist.class)
////    public void addRefereeToLeagueInSeasonTrowException() throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, ObjectNotExist, MemberNotExist, PasswordDontMatchException {
////        controller.logIn("associationDelegate@gmail.com", "abc");
////        String league = "womenLeagueE";
////        controller.setLeague(league);
////        String season = "2020";
////        controller.setLeagueByYear(league, season);
////        String refereeName = "Noa";
////        controller.addRefereeToLeagueInSeason("men league", season, refereeName);
////    }
//
//    @Test
//    public void setSchedulingPolicyToLeagueInSeasonTest() throws ObjectNotExist, IncorrectInputException, DontHavePermissionException, MemberNotExist, PasswordDontMatchException, AlreadyExistException {
//        controller.logIn("associationDelegate@gmail.com", "abc");
////        String league = "womenLeagueA";
////        controller.setLeague(league);
//        String league = controller.getLeaguesIds().get(0);
//        String season = "2021";
//        controller.setLeagueByYear("associationDelegate@gmail.com",league, season);
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

    @Test(expected = MemberNotExist.class)
    public void setLeagueByYearDoesntExistIdUser() throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        //when(dbControllerMock.existAssociationDelegate("noAssociationDelegate@gmail.com")).thenReturn(false);
        associationDelegateService.setLeagueByYear("noAssociationDelegate@gmail.com", "league", "2020");
    }

    @Test(expected = ObjectNotExist.class)
    public void setLeagueByYearDoesntExistLeague() throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        associationDelegateService.setLeagueByYear("associationDelegate@gmail.com", "noLeague", "2021");
    }

    @Test(expected = AlreadyExistException.class)
    public void setLeagueByYearExistSeason() throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        associationDelegateService.setLeagueByYear("associationDelegate@gmail.com", "league88Test", "2020");
    }

    @Test
    public void setLeagueByYearSucceed() throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        associationDelegateService.setLeagueByYear("associationDelegate@gmail.com", "league88Test", "2021");
        League league = dbController.getLeague("league88Test");
        Season season = new Season("2021");
        LeagueInSeason leagueInSeason = league.getLeagueInSeason(season);
        assertNotNull(leagueInSeason);
    }

    @Test(expected=MemberNotExist.class)
    public void insertSchedulingPolicyDoesntExistIdUser() throws ObjectNotExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        associationDelegateService.insertSchedulingPolicy("noAssociationDelegate@gmail.com", "league", "2020","");
    }

    @Test(expected=ObjectNotExist.class)
    public void insertSchedulingPolicyDoesntExistLeague() throws ObjectNotExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        associationDelegateService.insertSchedulingPolicy("associationDelegate@gmail.com", "league", "2020","All teams play each other twice");
    }

    @Test(expected=ObjectNotExist.class)
    public void insertSchedulingPolicyDoesntExistSeason() throws ObjectNotExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        associationDelegateService.insertSchedulingPolicy("associationDelegate@gmail.com", "league88Test", "2024","All teams play each other twice");
    }

    @Test
    public void insertSchedulingPolicySucceed() throws ObjectNotExist, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        associationDelegateService.insertSchedulingPolicy("associationDelegate@gmail.com", "league88Test", "2020","All teams play each other twice");
        Season season = new Season("2020");
        assertEquals(dbController.getLeague("league88Test").getLeagueInSeason(season).getSchedulePolicy().getNameOfPolicy(), "All teams play each other twice");
    }

    @Test(expected = ObjectNotExist.class)
    public void changeScorePolicyDoesntExistIdUser() throws ObjectNotExist, AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        associationDelegateService.changeScorePolicy("noAssociationDelegate@gmail.com", "league", "2020", "10", "5", "2");
    }

    @Test(expected = ObjectNotExist.class)
    public void changeScorePolicyDoesntExistLeague() throws ObjectNotExist, AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        associationDelegateService.changeScorePolicy("associationDelegate@gmail.com", "league", "2020", "10", "5", "2");
    }

    @Test(expected = ObjectNotExist.class)
    public void changeScorePolicyDoesntExistSeason() throws ObjectNotExist, AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        associationDelegateService.changeScorePolicy("associationDelegate@gmail.com", "league88Test", "2024", "10", "5", "2");
    }


    @Test
    public void changeScorePolicySucceed() throws ObjectNotExist, AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        associationDelegateService.changeScorePolicy("noAssociationDelegate@gmail.com", "league", "2020", "10", "5", "2");
        Season season = new Season("2020");
        LeagueInSeason leagueInSeason = dbController.getLeague("league88Test").getLeagueInSeason(season);
        double[] scores = {leagueInSeason.getScorePolicy().getScoreToWinningTeam(), leagueInSeason.getScorePolicy().getScoreToDrawGame(), leagueInSeason.getScorePolicy().getScoreToLosingTeam()};
        double [] scores1 = {10, 5, 2};
        assertEquals(scores, scores1);
    }

    @Test(expected=ObjectNotExist.class)
    public void getRefereesDoesntExistInTheLeagueAndSeasonDoesntExistLeague() throws ObjectNotExist, DontHavePermissionException, AlreadyExistException {
        associationDelegateService.getRefereesDoesntExistInTheLeagueAndSeason("league", "2020");
    }

    @Test(expected=ObjectNotExist.class)
    public void getRefereesDoesntExistInTheLeagueAndSeasonDoesntExistSeason() throws ObjectNotExist, DontHavePermissionException, AlreadyExistException {
        associationDelegateService.getRefereesDoesntExistInTheLeagueAndSeason("league88Test", "2024");
    }

    @Test
    public void getRefereesDoesntExistInTheLeagueAndSeasonSucceed() throws ObjectNotExist, DontHavePermissionException, AlreadyExistException {
        HashMap<String, Referee> referees = associationDelegateService.getRefereesDoesntExistInTheLeagueAndSeason("league88Test", "2020");
        assertNotEquals(referees.size(), 0);
    }
}


