package com.football;

import com.football.DataBase.DBController;
import com.football.DataBase.SystemManagerDao;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.*;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;

public class DataBaseTest {

    DBController dbc= new DBController();
    Date birthDate = new Date(1995, 3, 6);
    SystemManager systemManager;


    @Before
    public void init(){
        systemManager = new SystemManager("shacahr", "meretz@gmail.com", "123456", new Date(1, 1, 1995));
    }
    //game
    @Test
    public void gameDB() throws AlreadyExistException, IncorrectInputException, MemberNotExist, PasswordDontMatchException, MemberAlreadyExistException, DontHavePermissionException, ObjectAlreadyExist, NoEnoughMoney, ObjectNotExist {
        schedulingGames();
    }

    @Test
    public void fanDB(){
        try {
            Fan fan = new Fan("fan", "testfan@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existFan(fan.getUserMail())==true) {
                dbc.deleteFan(systemManager, fan.getUserMail());
            }
            assertFalse(dbc.existFan(fan.getUserMail()));
            dbc.addFan(systemManager, fan);
            assertTrue(dbc.existFan(fan.getUserMail()));
            assertEquals(dbc.getFan(fan.getUserMail()).toString() , fan.toString());
            Fan fan2 = new Fan("fanafterUpdate", "testfan@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateFan(systemManager, fan2);
            assertEquals(dbc.getFan(fan2.getUserMail()).toString() , fan2.toString());
            assertNotEquals(fan.getName() ,dbc.getMember(fan2.getUserMail()).getName() );

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void systemManagerDB(){
        try {
            SystemManager systemManager1 = new SystemManager("systemManager", "testSystem@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existSystemManager(systemManager1.getUserMail())==true) {
                dbc.deleteSystemManager(systemManager, systemManager1.getUserMail());
            }
            assertFalse(dbc.existSystemManager(systemManager1.getUserMail()));
            dbc.addSystemManager(systemManager, systemManager1);
            assertTrue(dbc.existSystemManager(systemManager1.getUserMail()));
            assertEquals(dbc.getSystemManagers(systemManager1.getUserMail()).toString() , systemManager1.toString());

            SystemManager systemManager2 = new SystemManager("systemManagerAfterUpdate", "testSystem@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateSystemManager(systemManager, systemManager2);
            assertEquals(dbc.getSystemManagers(systemManager2.getUserMail()).toString() , systemManager2.toString());
            assertNotEquals(systemManager1.getName() ,dbc.getMember(systemManager2.getUserMail()).getName() );

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void associationDelegateDB(){
        try {
            AssociationDelegate associationDelegate = new AssociationDelegate("association", "testAssociation@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existAssociationDelegate(associationDelegate.getUserMail())==true) {
                dbc.deleteAssociationDelegate(systemManager, associationDelegate.getUserMail());
            }
            assertFalse(dbc.existAssociationDelegate(associationDelegate.getUserMail()));
            dbc.addAssociationDelegate(systemManager, associationDelegate);
            assertTrue(dbc.existAssociationDelegate(associationDelegate.getUserMail()));
            assertEquals(dbc.getAssociationDelegate(associationDelegate.getUserMail()).toString() , associationDelegate.toString());

            AssociationDelegate associationDelegate2 = new AssociationDelegate("associationAfterUpdate", "testAssociation@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateAssociationDelegate(systemManager, associationDelegate2);

            assertEquals(dbc.getAssociationDelegate(associationDelegate2.getUserMail()).toString() , associationDelegate2.toString());
            assertNotEquals(associationDelegate.getName() ,dbc.getMember(associationDelegate2.getUserMail()).getName() );

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void playerDB(){
        try {
            Player player = new Player("player", "playerTest@gmail.com", "111", new Date(1995, 6, 7),"");
            if(dbc.existMember(player.getUserMail())==true) {
                dbc.deleteMember(systemManager, player.getUserMail());
            }
            assertFalse(dbc.existMember(player.getUserMail()));
            dbc.addPlayer(systemManager, player);
            assertTrue(dbc.existMember(player.getUserMail()));
            assertEquals(dbc.getMember(player.getUserMail()).toString() , player.toString());

            Player player2 = new Player("playerAfterUpdate", "playerTest@gmail.com", "111", new Date(1995, 6, 7),"");
            dbc.updatePlayer(systemManager, player2);
            assertEquals(dbc.getMember(player2.getUserMail()).toString() , player2.toString());
            assertNotEquals(player.getName() ,dbc.getMember(player2.getUserMail()).getName() );

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void coachDB(){
        try {
            Coach coach = new Coach("coach", "testCoach@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existMember(coach.getUserMail())==true) {
                dbc.deleteMember(systemManager, coach.getUserMail());
            }
            assertFalse(dbc.existMember(coach.getUserMail()));

            dbc.addCoach(systemManager, coach);
            assertTrue(dbc.existMember(coach.getUserMail()));
            assertEquals(dbc.getMember(coach.getUserMail()).toString() , coach.toString());

            Coach coach2 = new Coach("coachUpdate", "testCoach@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateCoach(systemManager, coach2);
            assertEquals(dbc.getMember(coach2.getUserMail()).toString() , coach2.toString());
            assertNotEquals(coach.getName() ,dbc.getMember(coach2.getUserMail()).getName() );

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void managerDB(){
        try {
            com.football.Domain.Asset.Manager manager = new  com.football.Domain.Asset.Manager("manager", "managerTest@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existMember(manager.getUserMail())==true) {
                dbc.deleteMember(systemManager, manager.getUserMail());
            }
            assertFalse(dbc.existMember(manager.getUserMail()));

            dbc.addManager(systemManager, manager);
            assertTrue(dbc.existMember(manager.getUserMail()));
            assertEquals(dbc.getMember(manager.getUserMail()).toString() , manager.toString());

            com.football.Domain.Asset.Manager manager2 = new  com.football.Domain.Asset.Manager("managerUpdate", "managerTest@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateManager(systemManager, manager2);
            assertEquals(dbc.getMember(manager2.getUserMail()).toString() , manager2.toString());
            assertNotEquals(manager.getName() ,dbc.getMember(manager2.getUserMail()).getName());

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void owenrDB(){
        try {
            Owner owner= new  Owner("owenr", "ownerTest@gmail.com", "111", new Date(1995, 6, 7));
            if(dbc.existMember(owner.getUserMail())==true) {
                dbc.deleteMember(systemManager, owner.getUserMail());
            }
            assertFalse(dbc.existMember(owner.getUserMail()));

            dbc.addOwner(systemManager, owner);
            assertTrue(dbc.existMember(owner.getUserMail()));
            assertEquals(dbc.getMember(owner.getUserMail()).toString() , owner.toString());

            Owner owner2= new  Owner("ownerAfterUpdate", "ownerTest@gmail.com", "111", new Date(1995, 6, 7));
            dbc.updateOwner(systemManager, owner2);
            assertEquals(dbc.getMember(owner2.getUserMail()).toString() , owner2.toString());
            assertNotEquals(owner.getName() ,dbc.getMember(owner2.getUserMail()).getName());

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void mainRefereeDB(){
        try {
            MainReferee mainReferee = new MainReferee("mainReferee", "MainrefereeTest@gmail.com", "111", "", new Date(1995, 6, 7));
            if(dbc.existReferee(mainReferee.getUserMail())==true) {
                dbc.deleteReferee(systemManager, mainReferee.getUserMail());
            }
            assertFalse(dbc.existReferee(mainReferee.getUserMail()));

            dbc.addReferee(systemManager, mainReferee);
            assertTrue(dbc.existReferee(mainReferee.getUserMail()));
            assertEquals(dbc.getReferee(mainReferee.getUserMail()).toString() , mainReferee.toString());

            MainReferee mainReferee2 = new MainReferee("mainRefereeAfterUpdate", "MainrefereeTest@gmail.com", "111", "", new Date(1995, 6, 7));
            dbc.updateReferee(systemManager, mainReferee2);
            assertEquals(dbc.getReferee(mainReferee2.getUserMail()).toString() , mainReferee2.toString());
            assertNotEquals(mainReferee.getName() ,dbc.getReferee(mainReferee2.getUserMail()).getName());

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void secondaryReferee(){
        try {
            SecondaryReferee secondaryReferee = new SecondaryReferee("secondaryReferee", "secondaryRefereeTest@gmail.com", "111", "", new Date(1995, 6, 7));
            if(dbc.existReferee(secondaryReferee.getUserMail())==true) {
                dbc.deleteReferee(systemManager, secondaryReferee.getUserMail());
            }
            assertFalse(dbc.existReferee(secondaryReferee.getUserMail()));

            dbc.addReferee(systemManager, secondaryReferee);
            assertTrue(dbc.existReferee(secondaryReferee.getUserMail()));
            assertEquals(dbc.getReferee(secondaryReferee.getUserMail()).toString() , secondaryReferee.toString());

            SecondaryReferee secondaryReferee2 = new SecondaryReferee("secondaryRefereeAfterUpdate", "secondaryRefereeTest@gmail.com", "111", "", new Date(1995, 6, 7));
            dbc.updateReferee(systemManager, secondaryReferee2);
            assertEquals(dbc.getReferee(secondaryReferee2.getUserMail()).toString() , secondaryReferee2.toString());
            assertNotEquals(secondaryReferee.getName() ,dbc.getReferee(secondaryReferee2.getUserMail()).getName());

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }
    @Test
    public void seasonDB(){
        try {
            Season season= new Season("1999");
            if(dbc.existSeason(season.getYear())==true) {
                dbc.removeSeason(systemManager, season.getYear());
            }
            assertFalse(dbc.existSeason(season.getYear()));

            dbc.addSeason(systemManager, season);
            assertTrue(dbc.existSeason(season.getYear()));
            assertEquals(dbc.getSeason(season.getYear()).toString() , season.toString());

            Season season2= new Season("1999");
            dbc.updateSeason(systemManager, season2);
            assertEquals(dbc.getSeason(season2.getYear()).toString() , season2.toString());
            //  assertNotEquals(season.getYear() ,dbc.getSeason(season2.getYear()).getYear());

        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }

    @Test
    public void leagueDB(){
        try {
            League league= new League("leagueTest");
            if(dbc.existLeague(league.getName())==true) {
                dbc.removeLeague(systemManager, league.getName());
            }
            assertFalse(dbc.existLeague(league.getName()));

            dbc.addLeague(systemManager, league);
            assertTrue(dbc.existLeague(league.getName()));
            assertEquals(dbc.getLeague(league.getName()).toString() , league.toString());

            League league2= new League("leagueTest");
            dbc.updateLeague(systemManager, league2);
            assertEquals(dbc.getLeague(league2.getName()).toString() , league2.toString());
            assertNotEquals(league.getName() ,dbc.getLeague(league2.getName()));



        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }


    @Test
    public void leagueInSeasonDB(){
        try {
            League league= new League("leagueForTest");
            if(dbc.existLeague(league.getName()))
            {
                dbc.removeLeague(systemManager,league.getName());
            }
            dbc.addLeague(systemManager, league);
            Season season= new Season("0000");
            if(dbc.existSeason(season.getYear()))
            {
                dbc.removeSeason(systemManager,season.getYear());
            }
            dbc.addSeason(systemManager, season);
            LeagueInSeason leagueInSeason= new LeagueInSeason(dbc.getLeague(league.getName()) , dbc.getSeason(season.getYear()));
            if(dbc.existLeagueInSeason(leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear())==true) {
                dbc.removeLeagueInSeason(systemManager, leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear());
            }
            assertFalse(dbc.existLeagueInSeason( leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear()));

            league.addLeagueInSeason(leagueInSeason);
            dbc.updateLeague(systemManager,league);

            season.addLeagueInSeason(leagueInSeason);
            dbc.updateSeason(systemManager,season);

            dbc.addLeagueInSeason(systemManager, leagueInSeason);

            assertTrue(dbc.existLeagueInSeason( leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear()));

            String leagueName = leagueInSeason.getLeague().getName();
            String seasonYear = leagueInSeason.getSeason().getYear();
            LeagueInSeason leagueInSeason1 = dbc.getLeagueInSeason(leagueName, seasonYear);
            assertEquals(leagueInSeason1.toString() , leagueInSeason.toString());
            LeagueInSeason leagueInSeason2= new LeagueInSeason(dbc.getLeague(league.getName()) ,dbc.getSeason(season.getYear()));
            dbc.updateLeagueInSeason(systemManager, leagueInSeason2);
            assertEquals(dbc.getLeagueInSeason(leagueInSeason.getLeague().getName(), leagueInSeason.getSeason().getYear()).toString(), leagueInSeason2.toString());


        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }
    }

    @Test
    public void teamDB(){
        try {
            Team team= new Team("macabi2" ,new Account(), dbc.getOwner("ownerTest@gmail.com"));
            if(dbc.existTeam(team.getName())==true) {
                dbc.removeTeam(systemManager, team.getName());
            }
            assertFalse(dbc.existTeam(team.getName()));

            dbc.addTeam(systemManager, team);
            assertTrue(dbc.existTeam(team.getName()));
            assertEquals(dbc.getTeam(team.getName()).toString() , team.toString());

            Team team2= new Team("macabi2" ,new Account(), dbc.getOwner("owner@gmail.com"));
            dbc.updateTeam(systemManager, team2);
            assertEquals(dbc.getTeam(team2.getName()).toString() , team2.toString());
            assertNotEquals(team.getName() ,dbc.getTeam(team2.getName()));


        } catch (DontHavePermissionException e) {
            e.printStackTrace();
        } catch (AlreadyExistException e) {
            e.printStackTrace();
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
    }

    private void schedulingGames() throws PasswordDontMatchException, DontHavePermissionException, IncorrectInputException, ObjectNotExist, ObjectAlreadyExist, NoEnoughMoney, AlreadyExistException, MemberNotExist, MemberAlreadyExistException {
        /* init - enter league , season , schedulePolicy league in seson */
        /********************************************************/
        /*                         Init                         */
        /********************************************************/
        LeagueInSeason leagueInSeason = initLeagueInSeson();
        Set<Game> games = new HashSet<>();
        /* try to scheduling game with correct input - teams with 11 players - result should be positive */
        if(leagueInSeason.getGames().size() == 0){
            ASchedulingPolicy schedulingPolicy = leagueInSeason.getPolicy();
            games = schedulingPolicy.setGamesOfTeams(leagueInSeason.getTeams(), leagueInSeason);
            leagueInSeason.addGames(games);
            dbc.addGames(systemManager, games);
            dbc.updateLeagueInSeason(systemManager, leagueInSeason);
        }

        HashSet<Game> gamesResult = dbc.getGames("league88Test","2020");
        int amountOfGames = gamesResult.size(); // 20 Choose 2 (???)
        Assert.assertTrue(leagueInSeason.getGames().size() == amountOfGames );

        //check if all teams status==true
        // && check if each team play in 38 games - 19*2
        // && check if team.games.contains(game)
//        for (Game game: games) {
//            Assert.assertTrue(game.getHostTeam().getStatus());
//            assertEquals(game.getHostTeam().getGamesSize(), 90);
//            Assert.assertTrue(game.getHostTeam().getGames().contains(game));
//
//            Assert.assertTrue(game.getVisitorTeam().getStatus());
//            assertEquals(game.getVisitorTeam().getGamesSize(),90);
//            Assert.assertTrue(game.getVisitorTeam().getGames().contains(game));
//
//            Assert.assertTrue(game.getReferees().size() == 2);
//            assertNotNull(game.getField());
//        }
    }
    private LeagueInSeason initLeagueInSeson() throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, ObjectAlreadyExist, MemberNotExist, NoEnoughMoney, IncorrectInputException, PasswordDontMatchException {
        if(dbc.existLeagueInSeason("league88Test:2020")){
             return dbc.getLeagueInSeason("league88Test","2020");
        }

        AssociationDelegate asd = new AssociationDelegate("asd","asd@gmail.com","123",birthDate);
        League league= new League("league88Test");
        if(dbc.existLeague("league88Test")){
            dbc.removeLeague(systemManager,"league88Test");
        }
        Season season = new Season("2020");
        if(dbc.existSeason("2020")){
            dbc.removeSeason(systemManager,"2020");
        }
        dbc.addLeague(asd,league);
        dbc.addSeason(asd,season);
        LeagueInSeason leagueInSeason = new LeagueInSeason(league,season);
        if(dbc.existLeagueInSeason("league88Test:2020")){
            dbc.removeLeagueInSeason(systemManager,"league88Test:2020");
        }
        leagueInSeason.setSchedulingPolicy(new SchedulingPolicyAllTeamsPlayTwice());

        addTeamsCorrectly(leagueInSeason,4);
        /*init - add 20 Teams ,referees to league in season */
        enterRefereeToLeagueInSeason(leagueInSeason,4);
        dbc.addLeagueInSeason(asd,leagueInSeason);
        league.addLeagueInSeason(leagueInSeason);
        season.addLeagueInSeason(leagueInSeason);

        dbc.updateLeague(systemManager,league);
        dbc.updateSeason(systemManager,season);

        return leagueInSeason;
    }
    private void addTeamsCorrectly(LeagueInSeason leagueInSeason,int mumOfTeams) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, ObjectNotExist, ObjectAlreadyExist, MemberNotExist, NoEnoughMoney, PasswordDontMatchException {
        SystemManagerService systemManagerService = new SystemManagerService();

        for(int i=0 ; i< mumOfTeams ; i++){
            if(dbc.existTeam("team"+i)){
                dbc.removeTeam(systemManager, "team"+i);
            }
            Team t = crateTeamWithAll(i);
            leagueInSeason.addTeam(t);
        }
    }
    private Team crateTeamWithAll(int i) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist {
        /********************************************************/
        /*                      create Assets                   */
        /********************************************************/


        Player p0 = new Player("palyer0"+i,"p0"+i+"@gmail.com","1",birthDate,"role");
        Player p1 = new Player("palyer1"+i,"p1"+i+"@gmail.com","1",birthDate,"role");
        Player p2 = new Player("palyer2"+i,"p2"+i+"@gmail.com","1",birthDate,"role");
        Player p3 = new Player("palyer3"+i,"p3"+i+"@gmail.com","1",birthDate,"role");
        Player p4 = new Player("palyer4"+i,"p4"+i+"@gmail.com","1",birthDate,"role");
        Player p5 = new Player("palyer5"+i,"p5"+i+"@gmail.com","1",birthDate,"role");
        Player p6 = new Player("palyer6"+i,"p6"+i+"@gmail.com","1",birthDate,"role");
        Player p7 = new Player("palyer7"+i,"p7"+i+"@gmail.com","1",birthDate,"role");
        Player p8 = new Player("palyer8"+i,"p8"+i+"@gmail.com","1",birthDate,"role");
        Player p9 = new Player("palyer9"+i,"p9"+i+"@gmail.com","1",birthDate,"role");
        Player p10 = new Player("palyer10"+i,"p10"+i+"@gmail.com","1",birthDate,"role");
        Coach coach = new Coach("coach"+i,"coach"+i+"@gmail.com","1","training",birthDate);
        com.football.Domain.Asset.Manager manager = new Manager("manager"+i,"manager"+i+"@gmail.com","1",birthDate);

        /********************************************************/
        /*                     create team                      */
        /********************************************************/

        Owner owner = new Owner("owner"+i,"owner"+i+"@gmail.com","1",birthDate);
        if(dbc.existOwner(owner.getUserMail())){
            dbc.deleteRole(systemManager,owner.getUserMail());
        }
        dbc.addOwner(systemManager,owner);
        Account account = new Account();
        account.setAmountOfTeam(10000);
        Team team = new Team("team"+i,account,owner);

        /********************************************************/
        /*                      add Assets                      */
        /********************************************************/

        team.addPlayer(p0);
        team.addPlayer(p1);
        team.addPlayer(p2);
        team.addPlayer(p3);
        team.addPlayer(p4);
        team.addPlayer(p5);
        team.addPlayer(p6);
        team.addPlayer(p7);
        team.addPlayer(p8);
        team.addPlayer(p9);
        team.addPlayer(p10);
        team.addCoach(coach);
        team.addManager(manager);
        team.addField(new Field("field"+i));

        if( dbc.existMember(p0.getUserMail()))
            dbc.deleteMember(systemManager,p0.getUserMail());
        dbc.addPlayer(owner,p0);
        if( dbc.existMember(p1.getUserMail()))
            dbc.deleteMember(systemManager,p1.getUserMail());
        dbc.addPlayer(owner,p1);
        if( dbc.existMember(p2.getUserMail()))
            dbc.deleteMember(systemManager,p2.getUserMail());
        dbc.addPlayer(owner,p2);
        if( dbc.existMember(p3.getUserMail()))
            dbc.deleteMember(systemManager,p3.getUserMail());
        dbc.addPlayer(owner,p3);
        if( dbc.existMember(p4.getUserMail()))
            dbc.deleteMember(systemManager,p4.getUserMail());
        dbc.addPlayer(owner,p4);
        if( dbc.existMember(p5.getUserMail()))
            dbc.deleteMember(systemManager,p5.getUserMail());
        dbc.addPlayer(owner,p5);
        if( dbc.existMember(p6.getUserMail()))
            dbc.deleteMember(systemManager,p6.getUserMail());
        dbc.addPlayer(owner,p6);
        if( dbc.existMember(p7.getUserMail()))
            dbc.deleteMember(systemManager,p7.getUserMail());
        dbc.addPlayer(owner,p7);
        if( dbc.existMember(p8.getUserMail()))
            dbc.deleteMember(systemManager,p8.getUserMail());
        dbc.addPlayer(owner,p8);
        if( dbc.existMember(p9.getUserMail()))
            dbc.deleteMember(systemManager,p9.getUserMail());
        dbc.addPlayer(owner,p9);
        if( dbc.existMember(p10.getUserMail()))
            dbc.deleteMember(systemManager,p10.getUserMail());
        dbc.addPlayer(owner,p10);
        if( dbc.existMember(coach.getUserMail()))
            dbc.deleteMember(systemManager,coach.getUserMail());
        dbc.addCoach(owner,coach);
        if( dbc.existMember(manager.getUserMail()))
            dbc.deleteMember(systemManager,manager.getUserMail());
        dbc.addManager(owner,manager);



        dbc.addTeam(systemManager,team);

        return team;



    }
    private void enterRefereeToLeagueInSeason(LeagueInSeason leagueInSeason, int numOfReferees) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist {
        for(int i=0; i<numOfReferees/2; i++) {
            Referee referee1 = new SecondaryReferee("referee"+i, "referee"+i+"@gmail.com", "123", "r",birthDate);
            if(dbc.existMember(referee1.getUserMail())){
                dbc.deleteReferee(systemManager , referee1.getUserMail());
            }
            dbc.addReferee(systemManager,referee1);
            leagueInSeason.addReferee(referee1.getUserMail(),referee1);
        }
        for(int j=numOfReferees/2 ; j<numOfReferees; j++) {
            Referee referee2 = new MainReferee("referee"+j, "referee"+j+"@gmail.com", "123","trainin",birthDate);
            if(dbc.existMember(referee2.getUserMail())){
                dbc.deleteReferee(systemManager , referee2.getUserMail());
            }
            dbc.addReferee(systemManager,referee2);
            leagueInSeason.addReferee(referee2.getUserMail(),referee2);
        }

    }
}

