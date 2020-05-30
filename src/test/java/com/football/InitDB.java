package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.SchedulingPolicyAllTeamsPlayTwice;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import com.football.Service.SecurityMachine;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;

public class InitDB {
    DBController dbc= new DBController();
    Date birthDate = new Date(1995, 3, 6);
    @Autowired
    private SecurityMachine securityMachine = new SecurityMachine();
    SystemManager systemManager = new SystemManager("admin", "hillapet@post.bgu.ac.il" , securityMachine.encrypt("123456"), new Date(1, 1, 1995));


    public InitDB(){
        Fan fan = new Fan("Hillol","peterhilla@gmail.com",securityMachine.encrypt("hhh"),birthDate);
        try {
            dbc.addFan(systemManager,fan);
            dbc.addSystemManager(systemManager,systemManager);
        } catch (AlreadyExistException e) {
        } catch (DontHavePermissionException e) {
        }

    }

    public LeagueInSeason initLeagueInSeason() throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, ObjectAlreadyExist, MemberNotExist, NoEnoughMoney, IncorrectInputException, PasswordDontMatchException {
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
    private Team crateTeamWithAll(int i) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist, ObjectNotExist {
        /********************************************************/
        /*                      create Assets                   */
        /********************************************************/
        String encryptPass =securityMachine.encrypt("1");

        Player p0 = new Player("palyer0"+i,"p0"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p1 = new Player("palyer1"+i,"p1"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p2 = new Player("palyer2"+i,"p2"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p3 = new Player("palyer3"+i,"p3"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p4 = new Player("palyer4"+i,"p4"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p5 = new Player("palyer5"+i,"p5"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p6 = new Player("palyer6"+i,"p6"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p7 = new Player("palyer7"+i,"p7"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p8 = new Player("palyer8"+i,"p8"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p9 = new Player("palyer9"+i,"p9"+i+"@gmail.com",encryptPass,birthDate,"role");
        Player p10 = new Player("palyer10"+i,"p10"+i+"@gmail.com",encryptPass,birthDate,"role");
        Coach coach = new Coach("coach"+i,"coach"+i+"@gmail.com",encryptPass,"training",birthDate);
        com.football.Domain.Asset.Manager manager = new Manager("manager"+i,"manager"+i+"@gmail.com",encryptPass,birthDate);

        /********************************************************/
        /*                     create team                      */
        /********************************************************/

        String mailOwner = "";
        String ownerName = "";
        if(i==0){
            mailOwner = "noa.shabtay22@gmail.com";
            ownerName = "Noa1";
        }if(i==1){
            mailOwner = "noashab@post.bgu.ac.il";
            ownerName = "Shabtay";
        }if(i==2){
            mailOwner ="meretz@post.bgu.ac.il" ;
            ownerName = "Hilla1";
        }if(i==3){
            mailOwner = "Yuval.Hilla@gmail.com ";
            ownerName = "Peter";
        }

        Owner owner = new Owner(ownerName,mailOwner,encryptPass,birthDate);
        if(dbc.existOwner(owner.getUserMail())){
            dbc.removeRole(systemManager,owner.getUserMail());
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
        owner.addTeam(team);
        dbc.updateOwner(systemManager,owner);


        HashSet<Player> players = team.getPlayers();
        for(Player p : players){
            p.addTeam(team);
            dbc.updatePlayer(systemManager,p);
        }
        HashSet<Coach> coaches = team.getCoaches();
        for(Coach c : coaches){
            c.addTeam(team);
            dbc.updateCoach(systemManager,c);
        }
        HashSet<Manager> managers = team.getManagers();
        for(Manager m : managers){
            m.addTeam(team);
            dbc.updateManager(systemManager,m);
        }
        return team;



    }
    private void enterRefereeToLeagueInSeason(LeagueInSeason leagueInSeason, int numOfReferees) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException, MemberNotExist {
        String encryptPass =securityMachine.encrypt("123");
        String mailReferee = "";
        String refereeName = "";
        for(int i=0; i<numOfReferees/2; i++) {
            if(i==0){
                mailReferee = "gorsia@post.bgu.ac.il";
                refereeName = "Dani";
            }if(i==1){
                mailReferee = "shacharmeretz1@gmail.com";
                refereeName = "Shachar";
            }
            Referee referee1 = new SecondaryReferee(refereeName, mailReferee, encryptPass, "r",birthDate);
            if(dbc.existMember(referee1.getUserMail())){
                dbc.deleteReferee(systemManager , referee1.getUserMail());
            }
            dbc.addReferee(systemManager,referee1);
            leagueInSeason.addReferee(referee1.getUserMail(),referee1);
        }
        for(int j=numOfReferees/2 ; j<numOfReferees; j++) {
            if(j==2){
                mailReferee = "osnatshabtay98@gmail.com";
                refereeName = "Osi";
            }if(j==3){
                mailReferee = "daniel.gorsia5@gmail.com";
                refereeName = "Daniel";
            }
            Referee referee2 = new MainReferee(refereeName, mailReferee, encryptPass,"trainin",birthDate);
            if(dbc.existMember(referee2.getUserMail())){
                dbc.deleteReferee(systemManager , referee2.getUserMail());
            }
            dbc.addReferee(systemManager,referee2);
            leagueInSeason.addReferee(referee2.getUserMail(),referee2);
        }

    }
}
