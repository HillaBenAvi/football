package com.football.DataBase;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.EventLog;
import com.football.Domain.League.*;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Service.ErrorLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

@Repository
public class DBController {

   // private DAOTEMP associationDelegateDao = AssociationDelegateDao.getInstance();
   @Autowired
   public AssociationDelegateDao associationDelegateDao;
    @Autowired
   public CoachDao coachDao;
    @Autowired
   public FanDao fanDao;
    @Autowired
    public FieldDao fieldDao;// = FieldDao.getInstance() ;
    @Autowired
    public GameDao gameDao;// = GameDao.getInstance();
    @Autowired
    public LeagueDao leagueDao;// = LeagueDao.getInstance();
    @Autowired
    public LeagueInSeasonDao leagueInSesonDao ;//= LeagueInSeasonDao.getInstance();
    @Autowired
    public MainRefereeDao mainRefereeDao;//= MainRefereeDao.getInstance();
    @Autowired
    public ManagerDao managerDao;//= ManagerDao.getInstance();
    @Autowired
    public OwnerDao ownerDao;//= OwnerDao.getInstance();
    @Autowired
    public PlayerDao playerDao;//= PlayerDao.getInstance();
    @Autowired
    public SeasonDao seasonDao;//= SeasonDao.getInstance();
    @Autowired
    public SecondaryRefereeDao seconaryRefereeDao;//= SecondaryRefereeDao.getInstance();
    @Autowired
    public SystemManagerDao systemManagerDao;//= SystemManagerDao.getInstance();
    @Autowired
    public TeamDao teamDao;//= TeamDao.getInstance();

// private AssociationDelegateDao associationDelegateDao;// = AssociationDelegateDao.getInstance();
    //private CoachDao coachDao;// = CoachDao.getInstance();
    //private FanDao fanDao;// = FanDao.getInstance() ;
   // private static final DBController instance = new DBController();

    //public static DBController getInstance(){
   //     return instance;
  //  }
//
    public DBController() { }
    /*************************************** Presentation.Guest function ******************************************/

    /*************************************** Getters ******************************************/

    public HashMap<String, Team> getTeams()  {
     //  List<String> teamsList = teamDao.getAll();
       //todo
        return null;

//        List<Team> list=dao.getAll();
//        HashMap<String , Team> ans=new HashMap<>();
//        for (int i=0; i<list.size(); i++)
//        {
//            ans.put(list.get(i).getName() , list.get(i));
//        }
//           return ans;
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
       // dao=UserDao.getInstance();
        //todo

//        List<Member> list=dao.getAll();
//        HashMap<String , Referee> ans=new HashMap<>();
//        for (int i=0; i<list.size(); i++)
//        {
//            if(list.get(i) instanceof  Referee)
//            ans.put(list.get(i).getName() , (Referee)list.get(i));
//        }
        return null;
//        return ans;
    }

    public HashMap<String, Role> getRoles()  {
       //todo
        return null;
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies()   {
        //return db.getSchedulingPolicies();
        //todo
        return null;
    }

    public HashMap<String, Fan> getFans()   {
           // return db.getFans();
        //todo
        return null;
    }

    public HashMap<String, Player> getPlayers()   {
            //return db.getPlayers();
        //todo
        return null;
    }

    public HashMap<String, Owner> getOwners()   {
        //return db.getOwners();
        //todo
        return null;
    }

    public HashMap<String, Manager> getManagers()   {
        //return db.getManagers();
        //todo
        return null;
    }

    public HashMap<String, Coach> getCoaches()  {
      //  return db.getCoaches();
        //todo
        return null;
    }

    public HashMap<String, Member> getMembers()  {
        //return db.getMembers();
        //todo
        return null;
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
            seconaryRefereeDao.get(id);
        } else if (mainRefereeDao.exist(id)) {
            mainRefereeDao.get(id);
        } else if (fanDao.exist(id)) {
            return new Fan(fanDao.get(id).split(":"));
        } else if (ownerDao.exist(id)) {
            ownerDao.get(id);
           // return new Owner(ownerDao.get(id).split(":"),this);
        } else if (systemManagerDao.exist(id)) {
            String details = systemManagerDao.get(id);
            return new SystemManager(details.split(":"));
        } else if (associationDelegateDao.exist(id)) {
            return new AssociationDelegate(associationDelegateDao.get(id).split(":"));
        }else if (playerDao.exist(id)) {
            //return new Player(playerDao.get(id).split(":"),this);
        } else if (managerDao.exist(id)) {
           // return new Manager(managerDao.get(id).split(":"),this);
        }else if (coachDao.exist(id)) {
           // return new Coach(coachDao.get(id).split(":"),this);
        }else
            throw new MemberNotExist();
        //todo
        return null;
    }

    public Team getTeam(String teamName) throws ObjectNotExist {
        //todo
        return null;
//            if (teamDao.exist(teamName)) {
//                //dao=TeamDao.getInstance();
//               return null;// (Team) dao.get(teamName);
//            } else {
//                throw new ObjectNotExist("the team id is not exist");
//            }
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

    public Fan getFan(String id) throws MemberNotExist {
        //return fanDao.get(id);
        //todo
        return null;
    }

    public Referee getReferee(String s) throws MemberNotExist {
//            if (db.existRefree(s)) {
//                return db.getReferee(s);
//            }
//            else {
//                throw new MemberNotExist();
//            }
        //todo
        return null;
    }

    public LinkedList<Member> getMembers(LinkedList<String> idMember) throws MemberNotExist {
//            LinkedList<Member> list = new LinkedList<>();
//            for (int i = 0; i < idMember.size(); i++) {
//                if (!db.existMember(idMember.get(i))) {
//                    throw new MemberNotExist();
//                }
//                Member member = db.getMember(idMember.get(i));
//                list.add(member);
//            }
//            return list;
        //todo
        return null;
    }

    public HashMap<String, Role> getOwnersAndFans() {
//            return db.getOwnersAndFans();
        //todo
        return null;
    }

    public Owner getOwner(String idOwner) throws MemberNotExist {
//            if (db.existOwner(idOwner)) {
//                Owner owner = db.getOwnerOrFan(idOwner);
//                return owner;
//            } else {
//                throw new MemberNotExist();
//            }
        //todo
        return null;
    }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        List<String> gamesStrings = gameDao.getAll();
        HashSet<Game> games = new HashSet<>();
        for(String game : gamesStrings){
            String[] splited = game.split(":");
            if(splited[8].equals(league) && splited[9].equals(season)) {
                League league1 = getLeague(splited[8]);
                LeagueInSeason leagueInSeason = league1.getLeagueInSeason(getSeason(splited[9]));
                Game newGame = parseGame(splited , leagueInSeason);
                games.add(newGame);
            }
        }
        return games;
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
        //todo
//        if (role instanceof SystemManager || role instanceof Owner) {
//            if (!teamDao.exist(name))
//                throw new ObjectNotExist("");
//            teamDao.delete(name);
//        } else {
//            throw new DontHavePermissionException();
//        }
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

//    public void deleteAll() {
//        db.deleteAll();
//    }

    /***************************************add function******************************************/
    public void addErrorLog(ErrorLog errorLog){
        //todo
    }
    public void addEventLog(EventLog eventLog){
        //todo
    }
    public void addAssociationDelegate(Role role, AssociationDelegate associationDelegate) throws
            DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager) {
            if (associationDelegateDao.exist(associationDelegate.getUserMail()))
                throw new AlreadyExistException();

            associationDelegateDao.save(associationDelegate);

            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addFan(Role role, Fan fan) throws AlreadyExistException, DontHavePermissionException {
        if (fanDao.exist(fan.getUserMail()))
            throw new AlreadyExistException();
        else{
            fanDao.save(fan);
        }
    }

    public void addSeason(Role role, Season season) throws AlreadyExistException, DontHavePermissionException {
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

    public void addLeague(Role role, League league) throws AlreadyExistException, DontHavePermissionException {
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
            if (managerDao.exist(manager.getUserMail()))
                throw new AlreadyExistException();
            managerDao.save(manager);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addPlayer(Role role, Player player) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {

            if (playerDao.exist(player.getUserMail()))
                throw new AlreadyExistException();
            playerDao.save(player);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addCoach(Role role, Coach coach) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (coachDao.exist(coach.getUserMail()))
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
        if (ownerDao.exist(owner.getUserMail()))
            throw new AlreadyExistException();
        ownerDao.save(owner);
    }

    public void addSystemManager(Role role, SystemManager systemManager) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager) {
            if (systemManagerDao.exist(systemManager.getUserMail()))
                throw new AlreadyExistException();
                systemManagerDao.save(systemManager);
            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addTeam(Role role, Team team) throws AlreadyExistException, DontHavePermissionException {
       //todo
//        if (role instanceof SystemManager || role instanceof Owner) {
//
//            if (db.existTeam(team.getName()))
//                throw new AlreadyExistException();
//            db.addTeam(team);
//        } else {
//            throw new DontHavePermissionException();
//        }
    }

    public void addReferee(Role role, Referee referee) throws DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager || role instanceof MainReferee || role instanceof SecondaryReferee) {
            if (!seconaryRefereeDao.exist(referee.getUserMail()) && !mainRefereeDao.exist(referee.getUserMail())) {
               if(referee.getType().equals("MainReferee"))
                    mainRefereeDao.save((MainReferee)referee);
               if(referee.getType().equals("SecondaryReferee"))
                   seconaryRefereeDao.save((SecondaryReferee)referee);
            } else {
                throw new AlreadyExistException();
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

    /********************************************exist function***********************************/
    public boolean existReferee( String refereeId) {
        return mainRefereeDao.exist(refereeId) || seconaryRefereeDao.exist(refereeId);
    }

    public boolean existFan(String fanId) {
        return fanDao.exist(fanId);
    }

    public boolean existTeam( String teamName) {
        //todo
        return false;
//        return db.existTeam(teamName);
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

    /******************************************** update function ***********************************/
    public void updateTeam(Role role,Team team) {
        //todo
    }
    public void updateOwner(Role role,Owner owner){
        //todo
    }
    public void updateManager(Role role,Manager manager) throws MemberNotExist, DontHavePermissionException {
        if( role instanceof Owner || role instanceof SystemManager){
            managerDao.update(manager.getUserMail(),manager);
        }
        else{
            throw new DontHavePermissionException();
        }
    }
    public void updateReferee(Role role,Referee referee) throws MemberNotExist, DontHavePermissionException{
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
    public void updateGame(Role role,Game game) throws MemberNotExist, DontHavePermissionException{
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
    public void updateCoach(Role role,Coach coach) throws MemberNotExist, DontHavePermissionException{
        //todo
    }
    public void updateFan(Role role,Fan fan) throws MemberNotExist, DontHavePermissionException{
        //todo
    }

    public void updatePlayer(Role role, Player player) throws MemberNotExist, DontHavePermissionException {

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

    /********************************************private function***********************************/

    private LeagueInSeason parseLeagueInSeason(String leagueInSeasonString, League league ,Season season) {
        String[] splited = leagueInSeasonString.split(":");
        LeagueInSeason leagueInSeason = new LeagueInSeason(league,season);

        /*add teams*/
        String[] teamsString = splited[2].split("---");
        for(String teamS : teamsString){
            try {
                leagueInSeason.addTeam(getTeam(teamS));
            } catch (ObjectNotExist objectNotExist) {
            } catch (AlreadyExistException e) {
            }
        }

        /*add referees*/
        String[] refereeString = splited[3].split("---");
        for(String refereeS : refereeString){
            try {
                leagueInSeason.addReferee(refereeS , getReferee(refereeS));
            } catch (MemberNotExist memberNotExist) { }
        }

        /*add games*/
        String[] gamesString = splited[4].split("---");
        HashSet<Game> games = new HashSet<>();
        for(String gameS : gamesString){
            Game game = parseGame(gameS.split("-"),leagueInSeason);
            games.add(game);
        }
        leagueInSeason.addGames(games);

        /*add ScorePolicy*/
        String[] scoreP = splited[5].split("---");
        double winning = Double.parseDouble(scoreP[0]);
        double draw = Double.parseDouble(scoreP[1]);
        double losing = Double.parseDouble(scoreP[2]);
        ScorePolicy scorePolicy = new ScorePolicy(winning, draw, losing);
        leagueInSeason.setScorePolicy(scorePolicy);

        /*add SchedulingPolicy*/
        ASchedulingPolicy schedulingPolicy = null;
        if (splited[6].equals("All teams play each other twice")) {
            schedulingPolicy = new SchedulingPolicyAllTeamsPlayTwice();
        } else if (splited[6].equals("All teams play each other once")) {
            schedulingPolicy = new SchedulingPolicyAllTeamsPlayOnce();
        }
        leagueInSeason.setSchedulingPolicy(schedulingPolicy);

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
            mainReferee= (MainReferee) getReferee(splited[5]);
            secondReferee = (SecondaryReferee)getReferee(splited[6]);
            Game newGame = new Game(splited[0],dateAndTime,hostTeam,visitorTeam,field,mainReferee,secondReferee,leagueInSeason);
            newGame.addEvents(splited[6]);
            newGame.setResult(splited[5]);
            return newGame;
        } catch (ObjectNotExist objectNotExist) {
        }catch (MemberNotExist memberNotExist) { }
        return null;
    }
    private Calendar getCalander(String calander){
        String[] dateTime = calander.split(",");
        int year = Integer.parseInt(dateTime[0]);
        int mounth = Integer.parseInt(dateTime[1]);
        int dayOfMonth = Integer.parseInt(dateTime[2]);
        int hourOfDay = Integer.parseInt(dateTime[3]);
        int minute = Integer.parseInt(dateTime[4]);
        int second = Integer.parseInt(dateTime[5]);
        return new GregorianCalendar(year, mounth, dayOfMonth, hourOfDay, minute, second);
    }



}
