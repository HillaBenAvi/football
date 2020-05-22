package com.football;

import com.football.DataBase.DBController;
import com.football.DataBase.SecondaryRefereeDao;
import com.football.DataBase.SystemManagerDao;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@SpringBootApplication
public class FootballApplication {
    public static DBController dbc=new DBController();

    public static void main(String[] args) throws DontHavePermissionException, AlreadyExistException {
       SpringApplication.run(FootballApplication.class, args);

//        checkGetters();

        /*(SystemManager systemManager=new SystemManager("shacahr", "meretz@gmail.com", "123456",  new Date(1, 1, 1995));
        AssociationDelegate associationDelegate=new AssociationDelegate("ass","ass2@gmail.com" , "ass" , new Date(1995,2,2));
        Fan fan=new Fan("fan","fan5@gmail.com","111",new Date(1995,6,7));
      //  Manager manager=new Manager("fan","fan5@gmail.com","111",new Date(1995,6,7));
        Coach coach=new Coach("fan","coach2@gmail.com","111","111",new Date(1995,6,7));
        Player player=new Player("fan","player@gmail.com","111",new Date(1995,6,7),"");
        Owner owner=new Owner("fan","owner@gmail.com","111",new Date(1995,6,7));
        SystemManager systemManager2=new SystemManager("shacahr", "system@gmail.com", "123456",  new Date(1, 1, 1995));
        Account account=new Account();
        SecondaryReferee referee=new SecondaryReferee("fan","owner@gmail.com","111","SecondaryReferee",new Date(1995,6,7));
        Team team=new Team("shachar", account, owner);
        //Season season=new Season("1998" );
       // League league=new League("league");
      //  LeagueInSeason leagueInSeason=new LeagueInSeason(league,season);
      //  dbc.addLeagueInSeason(systemManager,leagueInSeason);
      //  dbc.addTeam(systemManager,team);
        dbc.addReferee(systemManager,referee);
      //  dbc.addGames(systemManager,coach);
    //    dbc.addOwner(systemManager,owner);
     //   dbc.addSystemManager(systemManager,systemManager2);

        /*
       SecondaryRefereeDao systemManagerDao=new SecondaryRefereeDao();
        systemManagerDao.delete("123567");
        systemManagerDao.save(systemManager);
        System.out.println(systemManagerDao.exist("meretz@gmail.com"));
        System.out.println(systemManagerDao.get("meretz@gmail.com"));
        systemManagerDao.delete("meretz@gmail.com");
*/
    }

    private static void checkGetters() {
        checkGetMember();

        System.out.println("/***********************************************************/");
        System.out.println("/*                      getSystemManager                   */");
        System.out.println("/***********************************************************/");

        try {
            SystemManager sm = dbc.getSystemManagers("admin@gmail.com");
            System.out.println(sm.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                      getOwner                           */");
        System.out.println("/***********************************************************/");

        try {
            Owner owner = dbc.getOwner("owner@gmail.com");
            System.out.println(owner.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                 getAssociationDelegate                  */");
        System.out.println("/***********************************************************/");
        try {
            AssociationDelegate asd = dbc.getAssociationDelegate("asd@gmail.com");
            System.out.println(asd.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                   getSecondaryReferee                   */");
        System.out.println("/***********************************************************/");
        try {
            SecondaryReferee second = (SecondaryReferee) dbc.getReferee("second@gmail.com");
            System.out.println(second.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                       getMainReferee                    */");
        System.out.println("/***********************************************************/");
        try {
            MainReferee main = (MainReferee) dbc.getReferee("main@gmail.com");
            System.out.println(main.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                           getFan                       */");
        System.out.println("/***********************************************************/");
        try {
            Fan fan = dbc.getFan("fan@gmail.com");
            System.out.println(fan.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getLeague                       */");
        System.out.println("/***********************************************************/");

        try {
            League league = dbc.getLeague("league2");
            System.out.println(league.toString());
        } catch (ObjectNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getSeason                       */");
        System.out.println("/***********************************************************/");
        try {
            Season season = dbc.getSeason("1111");
            System.out.println(season.toString());
        } catch (ObjectNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                       getTeam                           */");
        System.out.println("/***********************************************************/");
        try {
            Team team = dbc.getTeam("team1");
            System.out.println(team.toString());
            Team team2 = dbc.getTeam("team2");
            System.out.println(team2.toString());
        } catch (ObjectNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getCoaches                     */");
        System.out.println("/***********************************************************/");
        HashMap<String,Coach> coachList = dbc.getCoaches();
        for(String coachname : coachList.keySet())
            System.out.println(coachname + " -- " +
                    coachList.get(coachname).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getPlayers                      */");
        System.out.println("/***********************************************************/");

        HashMap<String,Player> playersList = dbc.getPlayers();
        for(String playername : playersList.keySet())
            System.out.println(playername + " -- " +
                    playersList.get(playername).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                        getManagers                      */");
        System.out.println("/***********************************************************/");
        HashMap<String,com.football.Domain.Asset.Manager> managers = dbc.getManagers();
        for(String managername : managers.keySet())
            System.out.println(managername + " -- " +
                    managers.get(managername).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getFans                        */");
        System.out.println("/***********************************************************/");
        HashMap<String,Fan> fans = dbc.getFans();
        for(String fanName : fans.keySet())
            System.out.println(fanName + " -- " +
                    fans.get(fanName).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getOwners                       */");
        System.out.println("/***********************************************************/");
        HashMap<String,Owner> owners = dbc.getOwners();
        for(String roleName : owners.keySet())
            System.out.println(roleName + " -- " +
                    owners.get(roleName).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                        getReferees                      */");
        System.out.println("/***********************************************************/");
        HashMap<String,Referee> referees = dbc.getReferees();
        for(String roleName : referees.keySet())
            System.out.println(roleName + " -- " +
                    referees.get(roleName).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getRoles                       */");
        System.out.println("/***********************************************************/");
        HashMap<String,Role> roles = dbc.getRoles();
        for(String roleName : roles.keySet())
            System.out.println(roleName + " -- " +
                    roles.get(roleName).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getSeasons                      */");
        System.out.println("/***********************************************************/");
        HashMap<String,Season> seasons = dbc.getSeasons();
        for(String season : seasons.keySet())
            System.out.println(season + " -- " +
                    seasons.get(season).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getLeagues                      */");
        System.out.println("/***********************************************************/");
        HashMap<String,League> leagues = dbc.getLeagues();
        for(String league : leagues.keySet())
            System.out.println(league + " -- " +
                    leagues.get(league).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getTeams                       */");
        System.out.println("/***********************************************************/");
        HashMap<String,Team> teams = dbc.getTeams();
        for(String team : teams.keySet())
            System.out.println(team + " -- " +
                    teams.get(team).toString());
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getGames                       */");
        System.out.println("/***********************************************************/");
        try {
            HashSet<Game> games = dbc.getGames("league0","1995");
            for(Game game : games)
                System.out.println(game.getId() + " -- " +
                        game.toString());
        } catch (ObjectNotExist objectNotExist) {
            objectNotExist.printStackTrace();
        }

    }

    private static void checkGetMember() {

        System.out.println("/***********************************************************/");
        System.out.println("/*                      getSystemManager                   */");
        System.out.println("/***********************************************************/");
        try {
            SystemManager sm = (SystemManager) dbc.getMember("admin@gmail.com");
            System.out.println(sm.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                 getAssociationDelegate                  */");
        System.out.println("/***********************************************************/");
        try {
            AssociationDelegate asd = (AssociationDelegate) dbc.getMember("asd@gmail.com");
            System.out.println(asd.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                   getSecondaryReferee                   */");
        System.out.println("/***********************************************************/");
        try {
            SecondaryReferee second = (SecondaryReferee) dbc.getMember("second@gmail.com");
            System.out.println(second.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                       getMainReferee                    */");
        System.out.println("/***********************************************************/");
        try {
            MainReferee main = (MainReferee) dbc.getMember("main@gmail.com");
            System.out.println(main.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                           getFan                       */");
        System.out.println("/***********************************************************/");
        try {
            Fan fan = (Fan) dbc.getMember("fan@gmail.com");
            System.out.println(fan.toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                          getCoach                       */");
        System.out.println("/***********************************************************/");
        try {
            Coach coach = (Coach) dbc.getMember("coach@gmail.com");
            System.out.println(coach.toString());
            for(String t : coach.getTeam().keySet())
                System.out.println(t + "----" +coach.getTeam().get(t).toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                         getPlayer                       */");
        System.out.println("/***********************************************************/");
        try {
            Player player = (Player) dbc.getMember("player@gmail.com");
            System.out.println(player.toString());
            for(String t : player.getTeam().keySet())
                System.out.println(t + "----" +player.getTeam().get(t).toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                        getManager                       */");
        System.out.println("/***********************************************************/");
        try {
            com.football.Domain.Asset.Manager manager =
                    (com.football.Domain.Asset.Manager) dbc.getMember("manager@gmail.com");
            System.out.println(manager.toString());
            for(String t : manager.getTeam().keySet())
                System.out.println(t + "----" +manager.getTeam().get(t).toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        System.out.println("/***********************************************************/");
        System.out.println("/*                      getOwner                           */");
        System.out.println("/***********************************************************/");
        try {
            Owner owner = (Owner)dbc.getMember("owner@gmail.com");
            System.out.println(owner.toString());
            for(String t : owner.getTeams().keySet())
                System.out.println(t + "----" +owner.getTeams().get(t).toString());
        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }

    }

}