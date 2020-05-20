package com.football;

import com.football.DataBase.DBController;
import com.football.DataBase.SecondaryRefereeDao;
import com.football.DataBase.SystemManagerDao;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class FootballApplication {

    public static void main(String[] args) throws DontHavePermissionException, AlreadyExistException {
    //    SpringApplication.run(FootballApplication.class, args);

        DBController dbc=new DBController();
        SystemManager systemManager=new SystemManager("shacahr", "meretz@gmail.com", "123456",  new Date(1, 1, 1995));
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

}