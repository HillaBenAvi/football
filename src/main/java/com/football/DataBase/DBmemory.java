package com.football.DataBase;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;

@Repository
public class DBmemory implements DAO {

    private HashMap<String, League> leagues; //key-name of league, value-league
    private HashMap<String, Season> seasons; //key-year, value-season
    private HashMap<String, SystemManager> systemManagers;
    private HashMap<String, Role> roles; // hash map <mail,role> - maybe users instead roles??
    private HashMap<String, Team> teams;
    private HashMap<String, ASchedulingPolicy> schedulingPolicies;

    //  private HashMap<Member,String> passwordValidation;


    public DBmemory() {
        this.leagues = new HashMap<>();
        this.seasons = new HashMap<>();
        this.systemManagers = new HashMap<>();
        this.roles = new HashMap<>();
        this.teams = new HashMap<>();
        schedulingPolicies = new HashMap<>();
    }

    /***************************************Getters******************************************/

    public HashMap<String, League> getLeagues() { return leagues; }

    public HashMap<String, Role> getRoles() { return roles; }

    public HashMap<String, Team> getTeams() { return teams; }

    public League getLeague(String leagueId) {
        return leagues.get(leagueId);
    }

    public Season getSeason(String seasonId) {
        return seasons.get(seasonId);
    }

    public Team getTeam(String teamName) { return teams.get(teamName); }

    public Referee getReferee(String id) { return (Referee) roles.get(id); }

    @Override
    public void deleteRole(Role role, String id) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void deleteReferee(Role role, String id) throws DontHavePermissionException, MemberNotExist {

    }

    @Override
    public void deleteFan(Role role, String id) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void deleteMember(Role role, String id) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void removeTeam(Role role, String id) throws ObjectNotExist, DontHavePermissionException {

    }

    @Override
    public void deleteOwner(Role role, String id) throws DontHavePermissionException, MemberNotExist {

    }

    @Override
    public void deleteAssociationDelegate(Role role, String id) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void deleteSystemManager(Role role, String id) throws MemberNotExist, DontHavePermissionException {

    }

    @Override
    public void addAssociationDelegate(Role role, AssociationDelegate associationDelegate) throws DontHavePermissionException, AlreadyExistException {

    }

    @Override
    public void addSystemManager(Role role, SystemManager systemManager) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addOwner(Role role, Owner owner) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addFan(Role role, Fan fan) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addReferee(Role role, Referee referee) throws DontHavePermissionException, AlreadyExistException {

    }

    @Override
    public void addManager(Role role, Manager manager) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addPlayer(Role role, Player player) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addCoach(Role role, Coach coach) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addTeam(Role role, Team team) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addSeason(Role role, Season season) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public void addLeague(Role role, League league) throws AlreadyExistException, DontHavePermissionException {

    }

    @Override
    public boolean existReferee(String refereeId) {
        return false;
    }

    public Owner getOwner(String id) { return (Owner) roles.get(id); }

    public Player getPlayer(String id) { return (Player) roles.get(id); }

    public Manager getManager(String id) { return (Manager) roles.get(id); }

    public Coach getCoach(String id) { return (Coach) roles.get(id); }

    public Fan getFan(String id){ return (Fan)roles.get(id); }

    public SystemManager getSystemManager(String id){ return systemManagers.get(id); }

    public HashMap<String, Referee> getReferees() {
        HashMap<String, Referee> refereeHashMap = new HashMap<>();
        for (String str : roles.keySet()
        ) {
            if (roles.get(str) instanceof Referee) {
                refereeHashMap.put(str, (Referee) roles.get(str));
            }
        }
        return refereeHashMap;
    }

    public HashMap<String, Fan> getFans() {
        HashMap<String,Fan> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Fan)
            {
                toReturn.put(role,(Fan)roles.get(role));
            }
        }
        return toReturn;
    }

    public HashMap<String, Player> getPlayers() {
        HashMap<String,Player> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Player)
            {
                toReturn.put(role,(Player) roles.get(role));
            }
        }
        return toReturn;
    }

    public HashMap<String, Owner> getOwners() {
        HashMap<String,Owner> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Owner)
            {
                toReturn.put(role,(Owner) roles.get(role));
            }
        }
        return toReturn;

    }

    public HashMap<String, Manager> getManagers() {
        HashMap<String,Manager> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Manager)
            {
                toReturn.put(role,(Manager) roles.get(role));
            }
        }
        return toReturn;
    }

    public HashMap<String, Coach> getCoaches() {
        HashMap<String,Coach> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Coach)
            {
                toReturn.put(role,(Coach)roles.get(role));
            }
        }
        return toReturn;
    }

    public HashMap<String , Member> getMembers() {
        HashMap<String,Member> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Member)
            {
                toReturn.put(role,(Member)roles.get(role));
            }
        }
        return toReturn;
    }

    public HashMap<String, Role> getOwnersAndFans() {
        HashMap<String,Role> toReturn=new HashMap<>();
        for(String role:roles.keySet())
        {
            if(roles.get(role) instanceof  Fan || roles.get(role) instanceof Owner)
            {
                toReturn.put(role,roles.get(role));
            }
        }
        return toReturn;
    }

    public Owner getOwnerOrFan(String idOwner) throws MemberNotExist {
        Role role=roles.get(idOwner);
        if(role instanceof Owner)
        {
            return (Owner)role;
        }
        else if(role instanceof Fan)
        {
            Owner owner=new Owner(role.getName() ,((Fan) role).getUserMail() ,((Fan) role).getPassword() ,role.getBirthDate());
            return owner;
        }
        else {
            throw new MemberNotExist();
        }
    }

    public Member getMember(String id){
        if(roles.containsKey(id))
            return (Member)this.roles.get(id);
        return (Member)this.systemManagers.get(id);
    }

    public HashMap<String,Season> getSeasons() {
        return seasons;
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies(){
        return schedulingPolicies;
    }

    @Override
    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        return null;
    }

    public HashMap<String, AssociationDelegate> getAssociationDelegate() {
        HashMap<String , AssociationDelegate> associationDelegateHashMap=new HashMap<>();
        for (String str: roles.keySet()
        ) {
            if(roles.get(str) instanceof  AssociationDelegate)
            {
                associationDelegateHashMap.put(str , (AssociationDelegate)roles.get(str));
            }
        }
        return associationDelegateHashMap;
    }

    public AssociationDelegate getAssociationDelegate(String id) {
        return (AssociationDelegate)roles.get(id);
    }

    public HashMap<String, SystemManager> getSystemManagers() {
        HashMap<String , SystemManager> systemManagerHashMap=new HashMap<>();
        return systemManagers;
        /*
        for (String str: roles.keySet()
        ) {
            if(roles.get(str) instanceof  SystemManager)
            {
                systemManagerHashMap.put(str , (SystemManager) roles.get(str));
            }
        }
        return systemManagerHashMap;
    */}

    public SystemManager getSystemManagers(String id) {
        return systemManagers.get(id);
    }

    /***************************************delete function******************************************/
    public void removeLeague(String leagueName) {
        leagues.remove(leagueName);
    }

    public void removeRole(String id) { roles.remove(id); }

    public void removeTeam(String name) { teams.remove(name); }

    public void removeAssociationDelegate(String id) {
        roles.remove(id);
    }

    public void removeSeason(String year) {
        seasons.remove(year);
    }

    public void removeSystemManager(String id) {
        systemManagers.remove(id);
    }

    public void deleteAll() {
        leagues.clear();
        seasons.clear();
        SystemManager systemManager=getSystemManager("admin@gmail.com");
        systemManagers.clear();
        systemManagers.put("admin@gmail.com" ,systemManager);// new SystemManager("admin ",  "admin@gmail.com" ,"123" , DBController.getInstance() , new Date(1995,1,1)));
        roles.clear();
        teams.clear();
        schedulingPolicies.clear();
    }


    /***************************************add function******************************************/

    public void addLeague(League league) {
        leagues.put(league.getName(), league);
    }

    /***
     * this function add player to the roles list
     * @param player
     */
    public void addPlayer(Player player) { roles.put(player.getUserMail(), player); }

    /***
     * this function add coach to the roles list
     * @param coach
     */
    public void addCoach(Coach coach) { roles.put(coach.getUserMail(), coach); }

    /**
     * this function add manager to the roles list
     *
     * @param manager
     */
    public void addManager(Manager manager) { roles.put(manager.getUserMail(), manager); }

    /**
     * this function add owner to the roles list
     *
     * @param owner
     */
    public void addOwner(Owner owner) { roles.put(owner.getUserMail(), owner); }

    /**
     * this function add team to the teams list
     *
     * @param team
     */
    public void addTeam(Team team) {
        teams.put(team.getName(), team);
    }

    public void addSchedulingPolicies(ASchedulingPolicy policy) {
        schedulingPolicies.put(policy.getNameOfPolicy(), policy);
    }

    /**
     * this function add system manager to the system manager list
     *
     * @param systemManager
     */
    public void addSystemManager(SystemManager systemManager) { systemManagers.put(systemManager.getUserMail(), systemManager); }

    public void addSeason(Season season) {
        seasons.put(season.getYear(), season);
        System.out.println("Daniel - if the league is exists??? " + leagues.size());
    }

    /**
     * this function add AssociationDelegate to roles list
     * @param associationDelegate
     */
    public void addAssociationDelegate(AssociationDelegate associationDelegate) {
        if(!this.roles.containsKey(associationDelegate.getUserMail())){
            this.roles.put(associationDelegate.getUserMail(),associationDelegate);
        }
    }

    public void addFan(Fan fan) {
        roles.put(fan.getUserMail(), fan);
    }

    public void addReferee(Referee referee) {
        roles.put(referee.getUserMail(),referee);
    }
    /*****************************************exist function****************************************/
    public boolean existSeason(String year){
        return this.seasons.containsKey(year);
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

    @Override
    public void updateLeague(Role role, League league) throws ObjectNotExist, DontHavePermissionException {

    }

    @Override
    public void updateSeason(Role role, Season season) throws ObjectNotExist, DontHavePermissionException {

    }

    @Override
    public void updateLeagueInSeason(Role role, LeagueInSeason leagueInSeason) throws ObjectNotExist, DontHavePermissionException {

    }

    public boolean existLeague(String name){
        return this.leagues.containsKey(name);
    }

    public boolean existTeam(String name){
        return this.teams.containsKey(name);
    }

    /**
     * this function check if this member exist
     * @param id
     * @return
     */
    public boolean existMember(String id) {
        return (this.roles.containsKey(id) || this.systemManagers.containsKey(id));
    }

    public boolean existRefree(String refreeId) {
        if(roles.containsKey(refreeId)==true && roles.get(refreeId) instanceof Referee)
        {
            return true;
        }
        return false;
    }

    public boolean existFan(String fanId) {
        if(roles.containsKey(fanId)==true && roles.get(fanId) instanceof  Fan)
        {
            return true;
        }
        return false;
    }



    public boolean existSystemManager(String id) {
        if(this.systemManagers.containsKey(id))
            return true;
        return false;
    }


    public boolean existOwner(String ownerId) {
        if( roles.containsKey(ownerId) && roles.get(ownerId) instanceof Owner)
            return true;

        return false;
    }

    public boolean existAssociationDelegate(String id) {
        if( roles.containsKey(id) && roles.get(id) instanceof AssociationDelegate)
            return true;

        return false;
    }




//    public void addGames(Set<Game> game) {
//        for (Game gameToAdd:game
//             ) {
//            games.put(gameToAdd.getId() , gameToAdd);
//        }
//    }

    /*
    public HashMap<String, Referee> getMainReferee() {
        HashMap<String , Referee> toReturn=new HashMap<>();
        for (String role:roles.keySet()
             ) {
            if(roles.get(role) instanceof MainReferee)
                toReturn.put(((MainReferee) roles.get(role)).getUserMail() , (MainReferee)roles.get(role));
        }
        return toReturn;
    }

    public HashMap<String, Referee> getSecondaryReferee() {
        HashMap<String , Referee> toReturn=new HashMap<>();
        for (String role:roles.keySet()
        ) {
            if(roles.get(role) instanceof SecondaryReferee)
                toReturn.put(((SecondaryReferee) roles.get(role)).getUserMail() , (SecondaryReferee)roles.get(role));
        }
        return toReturn;
    }
    */
}
