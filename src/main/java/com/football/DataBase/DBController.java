package com.football.DataBase;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.League.*;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.*;

//todo
@Repository
public class DBController implements DAO {

    private DB db;
    private DAOTEMP dao;
    private DAOTEMP TeamDao;

    private static final DBController instance = new DBController();

    public static DBController getInstance(){
        return instance;
    }

    private DBController() {
        this.db = new DB();
    }
    /*************************************** Presentation.Guest function ******************************************/

    /*************************************** Getters ******************************************/

    public HashMap<String, Team> getTeams()  {
        //dao=TeamDao.getInstance();
        List<Team> list=dao.getAll();
        HashMap<String , Team> ans=new HashMap<>();
        for (int i=0; i<list.size(); i++)
        {
            ans.put(list.get(i).getName() , list.get(i));
        }
        return ans;
    }

    public HashMap<String, League> getLeagues() {
        this.dao = LeagueDao.getInstance();
        List<String> allLeagues = dao.getAll();
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
        List<Member> list=dao.getAll();
        HashMap<String , Referee> ans=new HashMap<>();
        for (int i=0; i<list.size(); i++)
        {
            if(list.get(i) instanceof  Referee)
                ans.put(list.get(i).getName() , (Referee)list.get(i));
        }
        return ans;
    }

    public List<Team> getTeamsByOwner(String id) {
        return null;
    }

    public HashMap<String, Role> getRoles()  {
        return db.getRoles(); }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies()   {
        return db.getSchedulingPolicies();
    }

    public HashMap<String, Fan> getFans()   {
        return db.getFans();
    }

    public HashMap<String, Player> getPlayers()   {
        return db.getPlayers();
    }

    public HashMap<String, Owner> getOwners()   {
        return db.getOwners();
    }

    public HashMap<String, Manager> getManagers()   {
        return db.getManagers();
    }

    public HashMap<String, Coach> getCoaches()  {
        return db.getCoaches();
    }

    public HashMap<String, Member> getMembers()  {
        return db.getMembers();
    }

    public HashMap<String, Season> getSeasons()   {
        this.dao = SeasonDao.getInstance();
        List<String> allSeasons = dao.getAll();
        HashMap<String,Season> seasons = new HashMap<>();
        for(String seasonString : allSeasons){
            String[] seasonDetails =seasonString.split(":");
            Season season = parseSeason(seasonDetails);
            seasons.put(season.getYear(),season);
        }
        return seasons;
    }

    public HashMap<String, SystemManager> getSystemManagers()   {
        this.dao = SystemManagerDao.getInstance();
        List<String> sMList = dao.getAll();
        HashMap<String,SystemManager> systemManagerHashMap= new HashMap<>();
        for(String sMString : sMList ){
            String[] splited = sMString.split(":");
            SystemManager systemManager = new SystemManager(splited);
            systemManagerHashMap.put(splited[0],systemManager);
        }
        return systemManagerHashMap;
    }

    public HashMap<String, AssociationDelegate> getAssociationDelegate()   {

        this.dao = AssociationDeligateDao.getInstance();
        List<String> aDList = dao.getAll();
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
        this.dao = SystemManagerDao.getInstance();
        if(dao.exist(id)){
            String splited = dao.get(id);
            SystemManager systemManager = new SystemManager(splited.split(":"));
            return systemManager;
        }
        throw new MemberNotExist();
    }

    public AssociationDelegate getAssociationDelegate( String id) throws MemberNotExist {
        this.dao = SystemManagerDao.getInstance();
        if(dao.exist(id)){
            String splited = dao.get(id);
            AssociationDelegate systemManager = new AssociationDelegate(splited.split(":"));
            return systemManager;
        }
        throw new MemberNotExist();
    }

    public Role getMember( String id) throws MemberNotExist {
        if (db.existMember(id)) {
            return db.getMember(id);
        } else if (db.existSystemManager(id)) {
            return db.getSystemManager(id);
        } else {
            throw new MemberNotExist();
        }
    }

    public Team getTeam(String teamName) throws ObjectNotExist {
        if (dao.exist(teamName)) {
            //dao=TeamDao.getInstance();
            return null;// (Team) dao.get(teamName);
        } else {
            throw new ObjectNotExist("the team id is not exist");
        }
    }

    public League getLeague(String leagueId) throws ObjectNotExist {
        dao=LeagueDao.getInstance();
        if(dao.exist(leagueId)){
            String leagueString = dao.get(leagueId);
            String[] splited = leagueString.split(":");
            League league = parseLeague(splited);
            return league;
        } else {
            throw new ObjectNotExist("the league id is not exist");
        }
    }

    public Season getSeason(String seasonId) throws ObjectNotExist {
        dao=SeasonDao.getInstance();
        if(dao.exist(seasonId)){
            String SeasonString = dao.get(seasonId);
            String[] splited = SeasonString.split(":");
            Season season = parseSeason(splited);
            return season;
        } else {
            throw new ObjectNotExist("the season id is not exist");
        }
    }

    public Fan getFan(String id) throws MemberNotExist {
        return db.getFan(id);
    }

    public Referee getReferee(String s) throws MemberNotExist {
        if (db.existRefree(s)) {
            return db.getReferee(s);
        }
        else {
            throw new MemberNotExist();
        }
    }

    public LinkedList<Member> getMembers(LinkedList<String> idMember) throws MemberNotExist {
        LinkedList<Member> list = new LinkedList<>();
        for (int i = 0; i < idMember.size(); i++) {
            if (!db.existMember(idMember.get(i))) {
                throw new MemberNotExist();
            }
            Member member = db.getMember(idMember.get(i));
            list.add(member);
        }
        return list;
    }

    public HashMap<String, Role> getOwnersAndFans() {
        return db.getOwnersAndFans();
    }

    public Owner getOwner(String idOwner) throws MemberNotExist {
        if (db.existOwner(idOwner)) {
            Owner owner = db.getOwnerOrFan(idOwner);
            return owner;
        } else {
            throw new MemberNotExist();
        }
    }

    @Override
    public Player getPlayer(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public Manager getManager(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public Coach getCoach(String id) throws MemberNotExist {
        return null;
    }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        dao = GameDao.getInstance();
        List<String> gamesStrings = dao.getAll();
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
            if (!db.existMember(id))
                throw new MemberNotExist();
            db.removeRole(id);
            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteReferee(Role role, String id) throws DontHavePermissionException, MemberNotExist {
        if (role instanceof SystemManager || role instanceof MainReferee || role instanceof SecondaryReferee) {
            if (db.existRefree(id)) {
                db.removeRole(id);
            } else {
                throw new MemberNotExist();

            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteFan(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner || role instanceof Fan) {
            if (db.existFan(id)) {
                db.removeRole(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteMember(Role role, String id) throws MemberNotExist, DontHavePermissionException {

        if (role instanceof SystemManager || role instanceof Owner) {
            if (db.existMember(id)) {
                db.removeRole(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void removeLeague(Role role, String leagueName) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (!db.existLeague(leagueName))
                throw new ObjectNotExist("");
            db.removeLeague(leagueName);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void removeSeason(Role role, String year) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (!db.existSeason(year))
                throw new ObjectNotExist("");
            db.removeSeason(year);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void removeTeam(Role role, String name) throws ObjectNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {

            if (!db.existTeam(name))
                throw new ObjectNotExist("");
            db.removeTeam(name);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteOwner(Role role, String ownerId) throws DontHavePermissionException, MemberNotExist {
        if (role instanceof SystemManager || role instanceof Owner) {
            if (db.existOwner(ownerId)) {
                db.removeRole(ownerId);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteAssociationDelegate(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            dao = AssociationDeligateDao.getInstance();
            if (dao.exist(id)) {
                dao.delete(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteSystemManager(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager) {
            dao = SystemManagerDao.getInstance();
            if (dao.exist(id)) {
                dao.delete(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteAll() {
        db.deleteAll();
    }

    /***************************************add function******************************************/
    public void addAssociationDelegate(Role role, AssociationDelegate associationDelegate) throws
            DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager) {
            dao = AssociationDeligateDao.getInstance();
            if (dao.exist(associationDelegate.getUserMail()))
                throw new AlreadyExistException();
            try {
                dao.save(associationDelegate);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addFan(Role role, Fan fan) throws AlreadyExistException, DontHavePermissionException {
        // if (!(role instanceof Presentation.Guest || role instanceof Fan) {
        //      throw new DontHavePermissionException();
        //  }
        if (db.existMember(fan.getUserMail()))
            throw new AlreadyExistException();
        db.addFan(fan);
    }

    public void addSeason(Role role, Season season) throws AlreadyExistException, DontHavePermissionException {
        this.dao = SeasonDao.getInstance();
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(dao.exist(season.getYear()))
                throw new AlreadyExistException();
            try {
                dao.save(season);
                HashMap<League, LeagueInSeason> lsList = season.getLeagues();
                if(season.getLeagues().size()>0){
                    for(League league : lsList.keySet()){
                        this.dao = LeagueDao.getInstance();
                        if(dao.exist(league.getName())) {
                            dao.update(league.getName(), league);
                        }
                        else{
                            dao.save(league);
                        }
                        this.dao = LeagueInSeasonDao.getInstance();
                        if(dao.exist(league.getName()+":"+season.getYear())){
                            dao.update(league.getName()+":"+season.getYear(),lsList.get(league));
                        }
                        else{
                            dao.save(lsList.get(league));
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addLeague(Role role, League league) throws AlreadyExistException, DontHavePermissionException {
        this.dao = LeagueDao.getInstance();
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(dao.exist(league.getName()))
                throw new AlreadyExistException();
            try {

                dao.save(league);
                HashMap<Season, LeagueInSeason> lsList = league.getSeasons();
                if(league.getSeasons().size()>0){
                    for(Season season : lsList.keySet()){
                        this.dao = SeasonDao.getInstance();
                        if(dao.exist(season.getYear())) {
                            dao.update(season.getYear(), season);
                        }
                        else {
                            dao.save(season);
                        }
                        this.dao = LeagueInSeasonDao.getInstance();
                        if(dao.exist(league.getName()+":"+season.getYear())){
                            dao.update(league.getName()+":"+season.getYear(),lsList.get(season));
                        }
                        else{
                            dao.save(lsList.get(season));
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addLeagueInSeason(Role role , LeagueInSeason leagueInSeason) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if(!LeagueInSeasonDao.getInstance().exist(leagueInSeason.getLeague().getName()+":"+leagueInSeason.getSeason().getYear()))
                LeagueInSeasonDao.getInstance().save(leagueInSeason);
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

            if (db.existMember(manager.getUserMail()))
                throw new AlreadyExistException();
            db.addManager(manager);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addPlayer(Role role, Player player) throws AlreadyExistException, DontHavePermissionException {

        if (role instanceof SystemManager || role instanceof Owner) {

            if (db.existMember(player.getUserMail()))
                throw new AlreadyExistException();
            db.addPlayer(player);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addCoach(Role role, Coach coach) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {

            if (db.existMember(coach.getUserMail()))
                throw new AlreadyExistException();
            db.addCoach(coach);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addOwner(Role role, Owner owner) throws AlreadyExistException, DontHavePermissionException {

        if (!(role instanceof SystemManager || role instanceof Owner)) {
            throw new DontHavePermissionException();
        }
        if (db.existMember(owner.getUserMail()))
            throw new AlreadyExistException();
        db.addOwner(owner);
    }

    public void addSystemManager(Role role, SystemManager systemManager) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager) {
            dao = SystemManagerDao.getInstance();
            if (dao.exist(systemManager.getUserMail()))
                throw new AlreadyExistException();
            try {
                dao.save(systemManager);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return;
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addTeam(Role role, Team team) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof Owner) {

            if (db.existTeam(team.getName()))
                throw new AlreadyExistException();
            db.addTeam(team);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addReferee(Role role, Referee referee) throws DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager || role instanceof MainReferee || role instanceof SecondaryReferee) {
            if (!db.existRefree(referee.getUserMail())) {
                db.addReferee(referee);
            } else {
                throw new AlreadyExistException();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addSchedulingPolicies(Role role, ASchedulingPolicy policy) throws DontHavePermissionException {
        if (role instanceof AssociationDelegate || role instanceof Owner || role instanceof SystemManager) {
            db.addSchedulingPolicies(policy);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addGames(Role role, Set<Game> games) throws DontHavePermissionException, AlreadyExistException {
        if (role instanceof SystemManager ) {
            dao = GameDao.getInstance();
            for( Game game : games){
                if (!dao.exist(game.getId())) {
                    try {
                        dao.save(game);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
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
        return db.existRefree(refereeId);
    }

    public boolean existFan(String fanId) {
        return db.existFan(fanId);
    }

    public boolean existTeam( String teamName) {
        return db.existTeam(teamName);
    }

    public boolean existMember(String id)  {
        return db.existMember(id);
    }

    public boolean existAssociationDelegate(String id)  {
        return AssociationDeligateDao.getInstance().exist(id);
    }

    public boolean existSystemManager( String id)  {
        return AssociationDeligateDao.getInstance().exist(id);
    }

    public boolean existOwner( String ownerId)  {
        //todo
        return SeasonDao.getInstance().exist(ownerId);
    }

    public boolean existSeason(String id) {
        return SeasonDao.getInstance().exist(id);
    }

    @Override
    public void updateTeam(Role role, Team team) throws DontHavePermissionException {

    }

    @Override
    public void updateOwner(Role role, Owner owner) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updateManager(Role role, Manager manager) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updateReferee(Role role, Referee referee) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updateGame(Role role, Game game) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updateCoach(Role role, Coach coach) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updateFan(Role role, Fan fan) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void updatePlayer(Role role, Player player) throws MemberNotExist, DontHavePermissionException {

    }

    /******************************************** update function ***********************************/
    public void updateTeam(String name) {
        //todo
    }

    public void updateLeague(Role role,League league) throws DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            LeagueDao.getInstance().update(league.getName(), league);
        }
        else{
            throw new DontHavePermissionException();
        }
    }

    public void updateSeason(Role role, Season season) throws DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            SeasonDao.getInstance().update(season.getYear(), season);
        }
        else{
            throw new DontHavePermissionException();
        }
    }


    public void updateLeagueInSeason(Role role, LeagueInSeason leagueInSeason) throws DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            String id = leagueInSeason.getLeague().getName() + ":" + leagueInSeason.getSeason().getYear();
            if (LeagueInSeasonDao.getInstance().exist(id))
                LeagueInSeasonDao.getInstance().update(id,leagueInSeason);
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
            String leagueInSeasonString = LeagueInSeasonDao.getInstance().get(splitedSeasonString[i]+":"+splitedSeasonString[0]);
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
            String leagueInSeasonString = LeagueInSeasonDao.getInstance().get(splitedLeagueString[0]+":"+splitedLeagueString[i]);
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