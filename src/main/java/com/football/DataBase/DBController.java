package com.football.DataBase;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Service.EventLog;
import com.football.Domain.League.*;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Service.ErrorLog;
import com.football.Service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
public class DBController {

   // private DAO associationDelegateDao = AssociationDelegateDao.getInstance();
   @Autowired
   public AssociationDelegateDao associationDelegateDao=new AssociationDelegateDao();
    @Autowired
   public CoachDao coachDao=new CoachDao();
    @Autowired
   public FanDao fanDao=new FanDao();
    @Autowired
    public FieldDao fieldDao=new FieldDao();// = FieldDao.getInstance() ;
    @Autowired
    public GameDao gameDao=new GameDao();// = GameDao.getInstance();
    @Autowired
    public LeagueDao leagueDao=new LeagueDao();// = LeagueDao.getInstance();
    @Autowired
    public LeagueInSeasonDao leagueInSesonDao =new LeagueInSeasonDao();//= LeagueInSeasonDao.getInstance();
    @Autowired
    public MainRefereeDao mainRefereeDao=new MainRefereeDao();//= MainRefereeDao.getInstance();
    @Autowired
    public ManagerDao managerDao=new ManagerDao();//= ManagerDao.getInstance();
    @Autowired
    public OwnerDao ownerDao=new OwnerDao();//= OwnerDao.getInstance();
    @Autowired
    public PlayerDao playerDao=new PlayerDao();;//= PlayerDao.getInstance();
    @Autowired
    public SeasonDao seasonDao=new SeasonDao();;//= SeasonDao.getInstance();
    @Autowired
    public SecondaryRefereeDao seconaryRefereeDao=new SecondaryRefereeDao();;//= SecondaryRefereeDao.getInstance();
    @Autowired
    public SystemManagerDao systemManagerDao=new SystemManagerDao();//= SystemManagerDao.getInstance();
    @Autowired
    public TeamDao teamDao=new TeamDao();;//= TeamDao.getInstance();
    @Autowired
    public ErrorLogDao errorLogDao=new ErrorLogDao();;//= TeamDao.getInstance()
    @Autowired
    public EventLogDao eventLogDao=new EventLogDao();;//= TeamDao.getInstance();
    @Autowired
    public NotificationDao notificationDao=new NotificationDao();;//= TeamDao.getInstance();


    public DBController() { }

    /***************************************add function******************************************/
      public void addAssociationDelegate(Role role, AssociationDelegate associationDelegate) throws
            DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager) {
            if (associationDelegateDao.exist(associationDelegate.getUserMail())||existMember(associationDelegate.getUserMail()))
                throw new AlreadyExistException();

            associationDelegateDao.save(associationDelegate);

            return;
        } else {
            throw new DontHavePermissionException();
        }
    }



    public void addFan(Role role, Fan fan) throws AlreadyExistException, DontHavePermissionException {
        if (fanDao.exist(fan.getUserMail()) || existMember(fan.getUserMail()))
            throw new AlreadyExistException();
        else{
            fanDao.save(fan);
        }
    }

    public void addSeason(Role role, Season season) throws AlreadyExistException, DontHavePermissionException, ObjectNotExist {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(seasonDao.exist(season.getYear()))
                throw new AlreadyExistException();

            seasonDao.save(season);
            HashMap<League, LeagueInSeason> lsList = season.getLeagues();
            if(season.getLeagues().size()>0){
                for(League league : lsList.keySet()){
                    if(leagueDao.exist(league.getName())) {
                        leagueDao.update(league.getName(), league);
                    }
                    else{
                        leagueDao.save(league);
                    }

                    if(leagueInSesonDao.exist(league.getName()+":"+season.getYear())){
                        leagueInSesonDao.update(league.getName()+":"+season.getYear(),lsList.get(league));
                    }
                    else{
                        leagueInSesonDao.save(lsList.get(league));
                    }
                }
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addLeague(Role role, League league) throws AlreadyExistException, DontHavePermissionException, ObjectNotExist {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(leagueDao.exist(league.getName()))
                throw new AlreadyExistException();

            leagueDao.save(league);
            HashMap<Season, LeagueInSeason> lsList = league.getSeasons();
            if(league.getSeasons().size()>0){
                for(Season season : lsList.keySet()){
                    if(seasonDao.exist(season.getYear())) {
                        seasonDao.update(season.getYear(), season);
                    }
                    else {
                        seasonDao.save(season);
                    }
                    if(leagueInSesonDao.exist(league.getName()+":"+season.getYear())){
                        leagueInSesonDao.update(league.getName()+":"+season.getYear(),lsList.get(season));
                    }
                    else{
                        leagueInSesonDao.save(lsList.get(season));
                    }
                }
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addLeagueInSeason(Role role , LeagueInSeason leagueInSeason) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(!leagueInSesonDao.exist(leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear()))
                leagueInSesonDao.save(leagueInSeason);
            else{
                throw new AlreadyExistException();
            }
        }
        else{
            throw new DontHavePermissionException();
        }
    }

    public void addManager(Role role, Manager manager) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (managerDao.exist(manager.getUserMail()) || existMember(manager.getUserMail()))
                throw new AlreadyExistException();
            managerDao.save(manager);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addPlayer(Role role, Player player) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {

            if (playerDao.exist(player.getUserMail()) || existMember(player.getUserMail()))
                throw new AlreadyExistException();
            playerDao.save(player);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addCoach(Role role, Coach coach) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (coachDao.exist(coach.getUserMail()) || existMember(coach.getUserMail()))
                throw new AlreadyExistException();
            coachDao.save(coach);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addOwner(Role role, Owner owner) throws AlreadyExistException, DontHavePermissionException {
        if (!(role instanceof SystemManager || role instanceof Owner)) {
            throw new DontHavePermissionException();
        }
        if (ownerDao.exist(owner.getUserMail()) || existMember(owner.getUserMail()))
            throw new AlreadyExistException();
        ownerDao.save(owner);
    }

    public void addSystemManager(Role role, SystemManager systemManager) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager) {
            if (systemManagerDao.exist(systemManager.getUserMail()) || existMember(systemManager.getUserMail()))
                throw new AlreadyExistException();
            systemManagerDao.save(systemManager);
            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addReferee(Role role, Referee referee) throws DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager || role instanceof MainReferee || role instanceof SecondaryReferee) {
            if (!seconaryRefereeDao.exist(referee.getUserMail()) && !mainRefereeDao.exist(referee.getUserMail())||existMember(referee.getUserMail())) {
                if(referee.getType().equals("0MainReferee"))
                    mainRefereeDao.save((MainReferee)referee);
                if(referee.getType().equals("0Secondary Referee"))
                    seconaryRefereeDao.save((SecondaryReferee)referee);
            } else {
                throw new AlreadyExistException();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addTeam(Role role, Team team) throws AlreadyExistException, DontHavePermissionException {

        if (role instanceof SystemManager || role instanceof Owner) {

            if (teamDao.exist(team.getName()))
                throw new AlreadyExistException();
            teamDao.save(team);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addGames(Role role, Set<Game> games) throws DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager ) {
            for( Game game : games){
                if (!gameDao.exist(game.getId())) {

                    gameDao.save(game);

                } else {
                    throw new AlreadyExistException();
                }
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addSchedulingPolicies(Role role, ASchedulingPolicy policy) throws DontHavePermissionException {
//        if (role instanceof AssociationDelegate || role instanceof Owner || role instanceof SystemManager) {
//            db.addSchedulingPolicies(policy);
//        } else {
//            throw new DontHavePermissionException();
//        }
        //todo
    }

    public void addErrorLog(ErrorLog errorLog) throws AlreadyExistException, DontHavePermissionException {
            if (errorLogDao.exist(errorLog.getId())) {
                throw new AlreadyExistException();
            }
        errorLogDao.save(errorLog);

    }

    public void addEventLog(EventLog eventLog) throws AlreadyExistException {
        if (eventLogDao.exist(eventLog.getId())) {
            throw new AlreadyExistException();
        }
        eventLogDao.save(eventLog);

    }
    public void addNotification(Notification notification) throws AlreadyExistException {
        if (notificationDao.exist(notification.getId())) {
            throw new AlreadyExistException();
        }
        notificationDao.save(notification);

    }



    /*************************************** Getters ******************************************/

    public HashMap<String, Team> getTeams()  {
        HashMap<String, Team> teamHashMap = new HashMap<>();
        List<String> teamsString = teamDao.getAll();
        for (String teamS : teamsString){
            try {
                Team team = getTeam(teamS.split(":")[0]);
                teamHashMap.put(team.getName(),team);
            } catch (ObjectNotExist objectNotExist) {
                objectNotExist.printStackTrace();
            }
        }
        return teamHashMap;
    }

    public HashMap<String, League> getLeagues() {
        List<String> allLeagues = leagueDao.getAll();
        HashMap<String,League> leagues = new HashMap<>();
        for(String leagueString : allLeagues){
            String[] leagueDetails =leagueString.split(":");
            League league = parseLeague(leagueDetails);
            leagues.put(league.getName(),league);
        }
        return leagues;
    }

    public HashMap<String, Referee> getReferees() {
        HashMap<String , Referee> result = new HashMap<>();
        List<String> mainsRe = mainRefereeDao.getAll();
        for(String mainRe : mainsRe){
            String[] splitMain = mainRe.split(":");
            String[] gamesSplited = splitMain[5].split(";");
            HashSet<Game> games = new HashSet<>();
            for(int i=0; i<gamesSplited.length;i++){
                Game game = new Game(gameDao.get(gamesSplited[i]));
                games.add(game);
            }
            MainReferee mainReferee = new MainReferee(splitMain,games);
            result.put(mainReferee.getUserMail(),mainReferee);
        }
        List<String> secondRe = seconaryRefereeDao.getAll();
        for(String second : secondRe){
            String[] splitSecond = second.split(":");
            String[] gamesSplited = splitSecond[5].split(";");
            HashSet<Game> games = new HashSet<>();
            for(int i=0; i<gamesSplited.length;i++){
                Game game = new Game(gameDao.get(gamesSplited[i]));
                games.add(game);
            }
            SecondaryReferee secondaryReferee = new SecondaryReferee(splitSecond,games);
            result.put(secondaryReferee.getUserMail(),secondaryReferee);
        }
        return result;
    }

    public HashMap<String, Role> getRoles()  {
        HashMap<String, Role> roleHashMap = new HashMap<>();
        roleHashMap.putAll(getMembers());
        return roleHashMap;
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies()   {
        //return db.getSchedulingPolicies();
        //todo
        return null;
    }

    public HashMap<String, Fan> getFans()   {
        HashMap<String,Fan> result = new HashMap<>();
        List<String> fansString = fanDao.getAll();
        for (String fanString : fansString){
            String[] splitfan = fanString.split(":");
            Fan fan =  new Fan(splitfan);
            result.put(fan.getUserMail(),fan);
        }
        return result;
    }

    public HashMap<String, Player> getPlayers()   {
        HashMap<String,Player> result = new HashMap<>();
        List<String> playersString = playerDao.getAll();
        for (String playerString : playersString){
            String[] splitPlayer = playerString.split(":");
            String[] teamsSplited = splitPlayer[4].split(";");
            HashMap<String,Team> teams = new HashMap<>();
            for(int i=0; i<teamsSplited.length;i++){
                try {
                    teams.put(teamsSplited[i],getTeam(teamsSplited[i]));
                } catch (ObjectNotExist objectNotExist) {
                }
            }
            result.put(splitPlayer[0],new Player(splitPlayer,teams));
        }
        return result;
    }

    public HashMap<String, Owner> getOwners()   {
        HashMap<String,Owner> result = new HashMap<>();
        List<String> ownerStrings = ownerDao.getAll();
        for (String ownerString : ownerStrings){
            String[] splitOwner = ownerString.split(":");
            String[] teamsSplited = splitOwner[4].split(";");
            HashMap<String,Team> teams = new HashMap<>();
            for(int i=0; i<teamsSplited.length;i++){
                try {
                    teams.put(teamsSplited[i],getTeam(teamsSplited[i]));
                } catch (ObjectNotExist objectNotExist) {
                }
            }
            result.put(splitOwner[0],new Owner(splitOwner,teams));
        }
        return result;
    }

    public HashMap<String, Manager> getManagers()   {
        HashMap<String,Manager> result = new HashMap<>();
        List<String> managersString = managerDao.getAll();
        for (String managerString : managersString){
            String[] splitManager = managerString.split(":");
            String[] teamsSplited = splitManager[4].split(";");
            HashMap<String,Team> teams = new HashMap<>();
            for(int i=0; i<teamsSplited.length;i++){
                try {
                    teams.put(teamsSplited[i],getTeam(teamsSplited[i]));
                } catch (ObjectNotExist objectNotExist) {
                }
            }
            result.put(splitManager[0],new Manager(splitManager,teams));
        }
        return result;
    }

    public HashMap<String, Coach> getCoaches()  {
        HashMap<String,Coach> result = new HashMap<>();
        List<String> coachesString = coachDao.getAll();
        for (String coachString : coachesString){
            String[] splitCoach = coachString.split(":");
            String[] teamsSplited = splitCoach[4].split(";");
            HashMap<String,Team> teams = new HashMap<>();
            for(int i=0; i<teamsSplited.length;i++){
                try {
                    teams.put(teamsSplited[i],getTeam(teamsSplited[i]));
                } catch (ObjectNotExist objectNotExist) {
                }
            }
            result.put(splitCoach[0],new Coach(splitCoach,teams));
        }
        return result;
    }

    public HashMap<String, Member> getMembers()  {
        HashMap<String,Member> memberHashMap = new HashMap<>();
        memberHashMap.putAll(getFans());
        memberHashMap.putAll(getReferees());
        memberHashMap.putAll(getPlayers());
        memberHashMap.putAll(getCoaches());
        memberHashMap.putAll(getManagers());
        memberHashMap.putAll(getOwners());
        memberHashMap.putAll(getSystemManagers());
        memberHashMap.putAll(getAssociationDelegate());
        return memberHashMap;
    }

    public HashMap<String, Season> getSeasons()   {
        List<String> allSeasons = seasonDao.getAll();
        HashMap<String,Season> seasons = new HashMap<>();
        for(String seasonString : allSeasons){
            String[] seasonDetails =seasonString.split(":");
            Season season = parseSeason(seasonDetails);
            seasons.put(season.getYear(),season);
        }
        return seasons;
    }

    public HashMap<String, SystemManager> getSystemManagers()   {
        List<String> sMList = systemManagerDao.getAll();
        HashMap<String,SystemManager> systemManagerHashMap= new HashMap<>();
        for(String sMString : sMList ){
            String[] splited = sMString.split(":");
            SystemManager systemManager = new SystemManager(splited);
            systemManagerHashMap.put(splited[0],systemManager);
        }
        return systemManagerHashMap;
    }

    public HashMap<String, AssociationDelegate> getAssociationDelegate()   {
        List<String> aDList = associationDelegateDao.getAll();
        HashMap<String,AssociationDelegate> associationDelegateHashMap= new HashMap<>();
        for(String aDString : aDList ){
            String[] splited = aDString.split(":");
            AssociationDelegate associationDelegate = new AssociationDelegate(splited);
            associationDelegateHashMap.put(splited[0],associationDelegate);
        }
        return associationDelegateHashMap;
    }

    /****************************get with id*****************************************/

    public SystemManager getSystemManagers(String id) throws MemberNotExist {
        if(systemManagerDao.exist(id)){
            String splited = systemManagerDao.get(id);
            SystemManager systemManager = new SystemManager(splited.split(":"));
            return systemManager;
        }
        throw new MemberNotExist();
    }

    public AssociationDelegate getAssociationDelegate( String id) throws MemberNotExist {
        if(associationDelegateDao.exist(id)){
            String splited = associationDelegateDao.get(id);
            AssociationDelegate systemManager = new AssociationDelegate(splited.split(":"));
            return systemManager;
        }
        throw new MemberNotExist();
    }

    public Role getMember( String id) throws MemberNotExist {
        if (seconaryRefereeDao.exist(id)) {
            HashSet<Game> games = new HashSet<>();
            String[] splitSecond = seconaryRefereeDao.get(id).split(":");
            if(splitSecond.length>=6) {
                String[] gamesSplited = splitSecond[5].split(";");
                for (int i = 0; i < gamesSplited.length; i++) {
                    Game game = new Game(gameDao.get(gamesSplited[i]));
                    games.add(game);
                }
            }
            return new SecondaryReferee(splitSecond,games);
        }
        else if (mainRefereeDao.exist(id)) {
            HashSet<Game> games = new HashSet<>();
            String[] splitMain = mainRefereeDao.get(id).split(":");
            if(splitMain.length>=6) {
                String[] gamesSplited = splitMain[5].split(";");
                for (int i = 0; i < gamesSplited.length; i++) {
                    Game game = new Game(gameDao.get(gamesSplited[i]));
                    games.add(game);
                }
            }
            return new MainReferee(mainRefereeDao.get(id).split(":"),games);
        }
        else if (fanDao.exist(id)) {
            return new Fan(fanDao.get(id).split(":"));
        }
        else if (ownerDao.exist(id)) {
            HashMap<String, Team> teams = new HashMap<>();
            String[] splitOwner = ownerDao.get(id).split(":");
            if(splitOwner.length>=5) {
                String[] teamsSplited = splitOwner[4].split(";");
                for (int i = 0; i < teamsSplited.length; i++) {
                    try {
                        Team team = getTeam(teamsSplited[i]);
                        teams.put(teamsSplited[i], team);
                    } catch (ObjectNotExist objectNotExist) {
                    }
                }
            }
            Owner owner =  new Owner(splitOwner,teams);
            return owner;
        }
        else if (systemManagerDao.exist(id)) {
            String details = systemManagerDao.get(id);
            return new SystemManager(details.split(":"));
        }
        else if (associationDelegateDao.exist(id)) {
            return new AssociationDelegate(associationDelegateDao.get(id).split(":"));
        }
        else if (playerDao.exist(id)) {
            String[] splitPlayer = playerDao.get(id).split(":");
            HashMap<String, Team> teams = new HashMap<>();
            if(splitPlayer.length>=6) {
                String[] teamsSplited = splitPlayer[5].split(";");
                for (int i = 0; i < teamsSplited.length; i++) {
                    try {
                        teams.put(teamsSplited[i], getTeam(teamsSplited[i]));
                    } catch (ObjectNotExist objectNotExist) {
                    }
                }
            }
            return new Player(splitPlayer,teams);
        }
        else if (managerDao.exist(id)) {
            String[] splitManager = managerDao.get(id).split(":");
            HashMap<String, Team> teams = new HashMap<>();
            if(splitManager.length>=5) {
                String[] teamsSplited = splitManager[4].split(";");
                for (int i = 0; i < teamsSplited.length; i++) {
                    try {
                        teams.put(teamsSplited[i], getTeam(teamsSplited[i]));
                    } catch (ObjectNotExist objectNotExist) {
                    }
                }
            }
            return new Manager(managerDao.get(id).split(":"),teams);
        }
        else if (coachDao.exist(id)) {
            String[] splitCoach = coachDao.get(id).split(":");
            HashMap<String,Team> teams = new HashMap<>();
            if(splitCoach.length==5)
            {
            String[] teamsSplited = splitCoach[4].split(";");
            for(int i=0; i<teamsSplited.length;i++){
                try {
                    teams.put(teamsSplited[i],getTeam(teamsSplited[i]));
                } catch (ObjectNotExist objectNotExist) {
                }
            }}
            return new Coach(coachDao.get(id).split(":"),teams);
        }
        else
            throw new MemberNotExist();
    }

    public Team getTeam(String teamName) throws ObjectNotExist {

        if(teamDao.exist(teamName)){
            String teamString = teamDao.get(teamName);
            String [] splitTeamDetails = teamString.split(":");


            /*add coaches*/
            LinkedList<Coach> coachLinkedList = new LinkedList<>();
            if(!splitTeamDetails[2].equals("")){
                String[] coaches = splitTeamDetails[2].split(";");
                for(String coach : coaches){
                    String[] splitCoach = coachDao.get(coach).split(":");
                    Coach coach1 = new Coach(splitCoach);
                    coachLinkedList.add(coach1);
                }
            }

            /*add players*/
            LinkedList<Player> players1 = new LinkedList<>();
            if(!splitTeamDetails[3].equals("")){
                String[] players = splitTeamDetails[3].split(";");
                for(String player : players){
                    String[] splitPlayer = playerDao.get(player).split(":");
                    Player player1 = new Player(splitPlayer);
                    players1.add(player1);
                }
            }

            /*add managers*/
            LinkedList<Manager> managers1 = new LinkedList<>();
            if(!splitTeamDetails[4].equals("")){
                String[] managers = splitTeamDetails[4].split(";");
                for(String manager : managers){
                    String[] splitManager = managerDao.get(manager).split(":");
                    Manager manager1 = new Manager(splitManager);
                    managers1.add(manager1);
                }
            }

            /*add owners*/
            LinkedList<Owner> owners1 = new LinkedList<>();
            if(!splitTeamDetails[5].equals("")){
                String[] owners = splitTeamDetails[5].split(";");
                for(String owner : owners){
                    String[] splitOwner = ownerDao.get(owner).split(":");
                    Owner owner1 = new Owner(splitOwner);
                    owners1.add(owner1);
                }
            }

            /*add fileds*/
            LinkedList<Field> fields1= new LinkedList<>();
            if(!splitTeamDetails[8].equals("")){
                String[] fields = splitTeamDetails[8].split(";");
                for(String field : fields){
                    String[] splitField = fieldDao.get(field).split(":");
                    Field field1 = new Field(splitField[0]);
                    fields1.add(field1);
                }
            }


            //todo with games also - ar delete it
            /*add games*/
            if(!splitTeamDetails[7].equals("")){
 //            String[] games = splitTeamDetails[7].split(";");
//            LinkedList<Game> games1= new LinkedList<>();
//            for(String game : games){
//                Game game1 = new Game(gameDao.get(game));
//                games1.add(game1);
//            }
            }

            /*add field*/
            Field field =null;
            if(!splitTeamDetails[6].equals("")){
                field = new Field(fieldDao.get(splitTeamDetails[6]).split(":")[0]);
            }

            Boolean status = splitTeamDetails[9].equals("true") ? true : false;

            /*create Team*/
            Account account = new Account(splitTeamDetails[1]);
            Team team = new Team(account,players1,coachLinkedList,managers1,owners1,field,fields1,status,splitTeamDetails[0]);

            /*add Team to assets*/
            for (Coach coach : team.getCoaches()){
                coach.addTeam(team);
            }
            for (Player player : team.getPlayers()){
                player.addTeam(team);
            }
            for (Manager manager : team.getManagers()){
                manager.addTeam(team);
            }
            for (Owner owner : team.getOwners()){
                owner.addTeam(team);
            }
            if(team.getHomeField()!=null){
                team.getHomeField().setTeam(team);
            }

            return team;
        }
        throw new ObjectNotExist("the team id is not exist");
    }

    public League getLeague(String leagueId) throws ObjectNotExist {
        if(leagueDao.exist(leagueId)){
            String leagueString = leagueDao.get(leagueId);
            String[] splited = leagueString.split(":");
            League league = parseLeague(splited);
            return league;
        } else {
            throw new ObjectNotExist("the league id is not exist");
        }
    }

    public Season getSeason(String seasonId) throws ObjectNotExist {
        if(seasonDao.exist(seasonId)){
            String SeasonString = seasonDao.get(seasonId);
            String[] splited = SeasonString.split(":");
            Season season = parseSeason(splited);
            return season;
        } else {
            throw new ObjectNotExist("the season id is not exist");
        }
    }

    public LeagueInSeason getLeagueInSeason(String league , String season) throws ObjectNotExist {
        if(leagueInSesonDao.exist(league+":"+season)){
            String leagueInSeasonString = leagueInSesonDao.get(league+":"+season);
          //  String[] splited = leagueInSeasonString.split(":");
            LeagueInSeason leagueInSeason1 = parseLeagueInSeason(leagueInSeasonString,getLeague(league),getSeason(season));
            return leagueInSeason1;
        } else {
            throw new ObjectNotExist("the season id is not exist");
        }
    }

    public Fan getFan(String id) throws MemberNotExist {
        if(fanDao.exist(id))
            return new Fan(fanDao.get(id).split(":"));
        else throw new MemberNotExist();
    }

    public Referee getReferee(String s) throws MemberNotExist {
        if (seconaryRefereeDao.exist(s)) {
            HashSet<Game> games = new HashSet<>();
            String[] splitSecond = seconaryRefereeDao.get(s).split(":");
            if(splitSecond.length>=6)
            {
            String[] gamesSplited = splitSecond[5].split(";");
            for(int i=0; i<gamesSplited.length;i++) {
                Game game = new Game(gameDao.get(gamesSplited[i]));
                games.add(game);
            }
            }
            return new SecondaryReferee(splitSecond,games);
        } else if (mainRefereeDao.exist(s)) {
            HashSet<Game> games = new HashSet<>();
            String[] splitMain = mainRefereeDao.get(s).split(":");
            if(splitMain.length>=6) {
                String[] gamesSplited = splitMain[5].split(";");
                for (int i = 0; i < gamesSplited.length; i++) {
                    Game game = new Game(gameDao.get(gamesSplited[i]));
                    games.add(game);
                }
            }
            return new MainReferee(mainRefereeDao.get(s).split(":"),games);
        }
        else throw new MemberNotExist();
    }

    public HashMap<String, Role> getOwnersAndFans() {
        HashMap<String, Role> roleHashMap = new HashMap<>();
        roleHashMap.putAll(getFans());
        roleHashMap.putAll(getOwners());
        return roleHashMap;
    }

    public Owner getOwner(String idOwner) throws MemberNotExist {
        if (ownerDao.exist(idOwner)) {
            HashMap<String, Team> teams = new HashMap<>();
            String[] splitOwner = ownerDao.get(idOwner).split(":");
            if(splitOwner.length>=5) {
                String[] teamsSplited = splitOwner[4].split(";");
                for (int i = 0; i < teamsSplited.length; i++) {
                    try {
                        teams.put(teamsSplited[i], getTeam(teamsSplited[i]));
                    } catch (ObjectNotExist objectNotExist) {
                    }
                }
            }
            Owner owner =  new Owner(splitOwner,teams);
            return owner;
        }
        else throw new MemberNotExist();
    }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        List<String> gamesStrings = gameDao.getAll();
        HashSet<Game> games = new HashSet<>();
        League league1 = getLeague(league);
        LeagueInSeason leagueInSeason = league1.getLeagueInSeason(getSeason(season));
        for(String game : gamesStrings){
            String[] splited = game.split(":");
            if(splited[8].equals(season) && splited[7].equals(league)) {
                Game newGame = parseGame(splited , leagueInSeason);
                newGame.setLeagueInSeason(leagueInSeason);
                games.add(newGame);
            }
        }
        return games;
    }

    public EventLog getEventLog(String id) throws ObjectNotExist {
        if(eventLogDao.exist(id)){
            String eventLog = eventLogDao.get(id);
            String[] splited = eventLog.split(":");
            EventLog eventLogObject = parseEventLog(splited);
            return eventLogObject;
        } else {
            throw new ObjectNotExist("the event log id is not exist");
        }
    }


    public Notification getNotification(String id) throws ObjectNotExist {
        if(notificationDao.exist(id)){
            String notification = notificationDao.get(id);
            String[] splited = notification.split(":");
            Notification notificationObject = parseNotification(splited);
            return notificationObject;
        } else {
            throw new ObjectNotExist("the notification id is not exist");
        }
    }

    public ErrorLog getErrorLog(String id) throws ObjectNotExist {
        if(errorLogDao.exist(id)){
            String errorLog = errorLogDao.get(id);
            String[] splited = errorLog.split(":");
            ErrorLog errorLogObject = parseErrorLog(splited);
            return errorLogObject;
        } else {
            throw new ObjectNotExist("the error log id is not exist");
        }
    }


    /***************************************delete function function******************************************/

    public void deleteRole(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner || role instanceof AssociationDelegate) {
            if (seconaryRefereeDao.exist(id)) {
                seconaryRefereeDao.delete(id);
            } else if (mainRefereeDao.exist(id)) {
                mainRefereeDao.delete(id);
            } else if (fanDao.exist(id)) {
                fanDao.delete(id);
            } else if (ownerDao.exist(id)) {
                ownerDao.delete(id);
            } else if (systemManagerDao.exist(id)) {
                systemManagerDao.delete(id);
            } else if (associationDelegateDao.exist(id)) {
                associationDelegateDao.delete(id);
            }else if (playerDao.exist(id)) {
                playerDao.delete(id);
            } else if (managerDao.exist(id)) {
                managerDao.delete(id);
            }else if (coachDao.exist(id)) {
                coachDao.delete(id);
            }else
                throw new MemberNotExist();
        } else {
            throw new DontHavePermissionException();
        }
    }
    public void removeLeagueInSeason(Role role, String name) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (!leagueInSesonDao.exist(name))
                throw new ObjectNotExist("");
            leagueInSesonDao.delete(name);
        } else {
            throw new DontHavePermissionException();
        }
    }
    public void deleteReferee(Role role, String id) throws DontHavePermissionException, MemberNotExist {
        if (role instanceof SystemManager || role instanceof MainReferee || role instanceof SecondaryReferee) {
            if (seconaryRefereeDao.exist(id)) {
                seconaryRefereeDao.delete(id);
            }
            else if (mainRefereeDao.exist(id)) {
                mainRefereeDao.delete(id);
            }
            else {
                throw new MemberNotExist();

            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteFan(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner || role instanceof Fan) {
            if (fanDao.exist(id)) {
                fanDao.delete(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteMember(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (existMember(id)) {
                deleteRole(role,id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void removeLeague(Role role, String leagueName) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (!leagueDao.exist(leagueName))
                throw new ObjectNotExist("");
            leagueDao.delete(leagueName);
        } else {
            throw new DontHavePermissionException();
        }
        //todo
    }

    public void removeSeason(Role role, String year) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (!seasonDao.exist(year))
                throw new ObjectNotExist("");
            seasonDao.delete(year);
        } else {
            throw new DontHavePermissionException();
        }
        //todo
    }

    public void removeTeam(Role role, String name) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (!teamDao.exist(name))
                throw new ObjectNotExist("");
            teamDao.delete(name);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteOwner(Role role, String ownerId) throws DontHavePermissionException, MemberNotExist {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (ownerDao.exist(ownerId)) {
                ownerDao.delete(ownerId);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteAssociationDelegate(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (associationDelegateDao.exist(id)) {
                associationDelegateDao.delete(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteSystemManager(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager) {
            if (systemManagerDao.exist(id)) {
                systemManagerDao.delete(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void removeNotification(String id) throws ObjectNotExist {
            if (notificationDao.exist(id)) {
                notificationDao.delete(id);
            } else {
                throw new ObjectNotExist("not exist");
            }
        }


    public void removeErrorLog(String id) throws ObjectNotExist {
        if (errorLogDao.exist(id)) {
            errorLogDao.delete(id);
        } else {
            throw new ObjectNotExist ("npt exist");
        }
    }

    public void removeEventLog(String id) throws ObjectNotExist {
        if (eventLogDao.exist(id)) {
            eventLogDao.delete(id);
        } else {
            throw new ObjectNotExist("not exist");
        }
    }

    /********************************************exist function***********************************/


    public boolean existReferee( String refereeId) {
        return mainRefereeDao.exist(refereeId) || seconaryRefereeDao.exist(refereeId);
    }

    public boolean existFan(String fanId) {
        return fanDao.exist(fanId);
    }

    public boolean existTeam( String teamName) {
        return teamDao.exist(teamName);
    }

    public boolean existMember(String id)  {
        if(fanDao.exist(id) || seconaryRefereeDao.exist(id) || associationDelegateDao.exist(id) || ownerDao.exist(id) ||
            systemManagerDao.exist(id) || mainRefereeDao.exist(id) || coachDao.exist(id) || playerDao.exist(id) || managerDao.exist(id))
                return true;
        return false;
    }

    public boolean existAssociationDelegate(String id)  {
        return associationDelegateDao.exist(id);
    }

    public boolean existSystemManager( String id)  {
        return systemManagerDao.exist(id);
    }

    public boolean existOwner( String ownerId)  {
        return ownerDao.exist(ownerId);
    }

    public boolean existSeason(String id) {
        return seasonDao.exist(id);
    }

    public boolean existLeague(String name) {
        return leagueDao.exist(name);
    }
    public boolean existLeagueInSeason(String name) {
        return leagueInSesonDao.exist(name);
    }

    public boolean existNotification(String id) {
        return notificationDao.exist(id);

    }

    public boolean existEventLog(String id) {
        return eventLogDao.exist(id);
    }

    public boolean existErrorLog(String id) {
        return errorLogDao.exist(id);

    }


    /******************************************** update function ***********************************/
    public void updateOwner(Role role,Owner owner) throws DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager){
            ownerDao.update(owner.getUserMail(),owner);
        }
        else
        {
            throw new DontHavePermissionException();
        }
    }

    public void updateManager(Role role,Manager manager) throws DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager){
            managerDao.update(manager.getUserMail(),manager);
        }
        else{
            throw new DontHavePermissionException();
        }
    }

    public void updateReferee(Role role,Referee referee) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if(role instanceof SystemManager || role instanceof AssociationDelegate){
            if(mainRefereeDao.exist(referee.getUserMail())){
                mainRefereeDao.update(referee.getUserMail(),(MainReferee)referee);
            }
            else if(seconaryRefereeDao.exist(referee.getUserMail())){
                seconaryRefereeDao.update(referee.getUserMail(),(SecondaryReferee)referee);
            }
            else{
                throw new MemberNotExist();
            }
        }
        else{
            throw new DontHavePermissionException();
        }
    }

    public void updateGame(Role role,Game game) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(gameDao.exist(game.getId())){
                gameDao.update(game.getId(), game);
            }
            else{
                throw new MemberNotExist();
            }
        }
        else{
            throw new DontHavePermissionException();
        }
    }

    public void updateCoach(Role role,Coach coach) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager){
            coachDao.update(coach.getUserMail(),coach);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateFan(Role role,Fan fan) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager || role instanceof AssociationDelegate){
            fanDao.update(fan.getUserMail(),fan);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updatePlayer(Role role, Player player) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager){
            playerDao.update(player.getUserMail(),player);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateLeague(Role role,League league) throws ObjectNotExist, DontHavePermissionException{
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            leagueDao.update(league.getName(), league);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateSeason(Role role, Season season) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            seasonDao.update(season.getYear(), season);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateLeagueInSeason(Role role, LeagueInSeason leagueInSeason) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            String id = leagueInSeason.getLeague().getName() + ":" + leagueInSeason.getSeason().getYear();
            if (leagueInSesonDao.exist(id))
                leagueInSesonDao.update(id,leagueInSeason);
        } else {
            throw new DontHavePermissionException();
        }
    }
    public void updateTeam(Role role,Team team) throws DontHavePermissionException, ObjectNotExist {
        if( role instanceof Owner || role instanceof SystemManager || role instanceof AssociationDelegate){
            teamDao.update(team.getName(),team);
        }
        else
        {
            throw new DontHavePermissionException();
        }
    }

    public void updateSystemManager(Role role, SystemManager systemManager) throws DontHavePermissionException, ObjectNotExist {
        if(  role instanceof SystemManager){
            systemManagerDao.update(systemManager.getUserMail(),systemManager);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateAssociationDelegate(Role role, AssociationDelegate associationDelegate) throws DontHavePermissionException, ObjectNotExist {
        if(  role instanceof SystemManager){
            associationDelegateDao.update(associationDelegate.getUserMail(),associationDelegate);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    /********************************************private function***********************************/

    private LeagueInSeason parseLeagueInSeason(String leagueInSeasonString, League league ,Season season) {
        String[] splited = leagueInSeasonString.split(":");
        LeagueInSeason leagueInSeason = new LeagueInSeason(league,season);

        /*add teams*/
        if(splited.length>=3) {
            String[] teamsString = splited[2].split(";");
            for (String teamS : teamsString) {
                try {
                    Team t = getTeam(teamS);
                    leagueInSeason.addTeam(t);
                } catch (ObjectNotExist objectNotExist) {
                } catch (AlreadyExistException e) {
                }
            }
        }

        /*add referees*/
        if(splited.length>=4) {
            String[] refereeString = splited[3].split(";");
            for (String refereeS : refereeString) {
                try {
                    leagueInSeason.addReferee(refereeS, getReferee(refereeS));
                } catch (MemberNotExist memberNotExist) {
                }
            }
        }
        /*add games*/
        if(splited.length>=5) {
            String[] gamesString = splited[4].split(";");
            HashSet<Game> games = new HashSet<>();
            for (String gameS : gamesString) {
                String gameDetails = gameDao.get(gameS);
                Game game = parseGame(gameDetails.split(":"), leagueInSeason);
                games.add(game);
            }
            leagueInSeason.addGames(games);

        }
        /*add SchedulingPolicy*/
        ASchedulingPolicy schedulingPolicy = null;
        if(splited.length>=6) {

            if (splited[5].equals("All teams play each other twice")) {
                schedulingPolicy = new SchedulingPolicyAllTeamsPlayTwice();
            } else if (splited[5].equals("All teams play each other once")) {
                schedulingPolicy = new SchedulingPolicyAllTeamsPlayOnce();
            }
            leagueInSeason.setSchedulingPolicy(schedulingPolicy);
        }

        /*add ScorePolicy*/
        if(splited.length>=7) {

            String[] scoreP = splited[6].split(";");
            double winning = Double.parseDouble(scoreP[0]);
            double draw = Double.parseDouble(scoreP[1]);
            double losing = Double.parseDouble(scoreP[2]);
            ScorePolicy scorePolicy = new ScorePolicy(winning, draw, losing);
            leagueInSeason.setScorePolicy(scorePolicy);
        }
        return leagueInSeason;
    }
    private Season parseSeason(String[] splitedSeasonString) {
        Season season = new Season(splitedSeasonString[0]);
        for(int i=1 ; i< splitedSeasonString.length ; i++){
            League league = new League(splitedSeasonString[i]);
            String leagueInSeasonString = leagueInSesonDao.get(splitedSeasonString[i]+":"+splitedSeasonString[0]);
            LeagueInSeason leagueInSeason = parseLeagueInSeason(leagueInSeasonString,league,season);
            league.addLeagueInSeason(leagueInSeason);
            season.addLeagueInSeason(leagueInSeason);
        }
        return season;
    }
    private League parseLeague(String[] splitedLeagueString) {
        League league = new League(splitedLeagueString[0]);
        for(int i=1 ; i< splitedLeagueString.length ; i++){
            Season season = new Season(splitedLeagueString[i]);
            String leagueInSeasonString = leagueInSesonDao.get(splitedLeagueString[0]+":"+splitedLeagueString[i]);
            LeagueInSeason leagueInSeason = parseLeagueInSeason(leagueInSeasonString,league,season);
            league.addLeagueInSeason(leagueInSeason);
            season.addLeagueInSeason(leagueInSeason);
        }
        return league;

    }
    private Game parseGame(String[] splited, LeagueInSeason leagueInSeason){
        Calendar dateAndTime = getCalander(splited[1]);
        try {
            Team hostTeam = getTeam(splited[2]);
            Team visitorTeam = getTeam(splited[3]);
            Field field = new Field(splited[4]);
            MainReferee mainReferee;
            SecondaryReferee secondReferee;
            String id = splited[0];
            String result = splited[5];
            String events = splited[6];
            String league = splited[7];
            String season = splited[8];
            mainReferee= (MainReferee) getReferee(splited[9].split(";")[0]);
            secondReferee = (SecondaryReferee)getReferee(splited[9].split(";")[1]);

            Game newGame = new Game(id,dateAndTime,hostTeam,visitorTeam,field,
                                mainReferee,secondReferee,leagueInSeason);

            newGame.addEvents(events);
            newGame.setResult(result);

            return newGame;

        } catch (ObjectNotExist objectNotExist) {
        }catch (MemberNotExist memberNotExist) { }
        return null;
    }
    private Calendar getCalander(String calander){
        String[] dateTime = calander.split(" ");
        int year = Integer.parseInt(dateTime[0]);
        int mounth = Integer.parseInt(dateTime[1]);
        int dayOfMonth = Integer.parseInt(dateTime[2]);
        int hourOfDay = Integer.parseInt(dateTime[3]);
        int minute = Integer.parseInt(dateTime[4]);
        int second = Integer.parseInt(dateTime[5]);
        return new GregorianCalendar(year, mounth, dayOfMonth, hourOfDay, minute, second);
    }



    private ErrorLog parseErrorLog(String[] splited) {
        ErrorLog errorLog=new ErrorLog(Integer.parseInt(splited[0]),splited[1] , splited[2]);
        return errorLog;
    }


    private EventLog parseEventLog(String[] splited) {
        EventLog eventLog=new EventLog(Integer.parseInt(splited[0]),splited[1] , splited[2] , splited[3]);
        return eventLog;
    }


    private Notification parseNotification(String[] splited) {
        Notification notification=new Notification(splited[0],splited[1] , splited[2]);
        return notification;
    }

}
