package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

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

    //field
    //game

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

            dbc.addLeagueInSeason(systemManager, leagueInSeason);
            assertTrue(dbc.existLeagueInSeason( leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear()));
            assertEquals(dbc.getLeagueInSeason(leagueInSeason.getLeague().getName(), leagueInSeason.getSeason().getYear()).toString() , leagueInSeason.toString());
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
}

