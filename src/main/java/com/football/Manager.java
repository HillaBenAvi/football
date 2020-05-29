package com.football;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class Manager {

    @Autowired
    private DBController dbController;
    @Autowired
    private GuestService guestController;
    @Autowired
    private SystemManagerService systemManagerService;
    @Autowired
    private AssociationDelegateService associationDelegateService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private MainRefereeService mainRefereeService;
    @Autowired
    private SecondaryRefereeService secondaryRefereeService;


    public Member register(String userName, String userMail, String password) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        Member newMember = guestController.signIn(userMail, userName,  password ,new Date());
        return newMember;
    }

    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException, AlreadyExistException {
        Member member =guestController.logIn(userMail, userPassword);
        return member;
    }

    public String stringLogIn(String userMail, String userPassword) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        Member member = logIn(userMail, userPassword);
        return member.getType();
    }

    public void logOut(String id, String password) {
       //todo!
    }

    /****************add assets****************/

    public void addManagerToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addManager(id,teamName,mailId);
            }
        }
    }

    public void addCoachToTeam(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addCoach(id,teamName,mailId);
            }
        }
    }

    public void addPlayerToTeam(String id, String teamName, String mailId, int year, int month, int day, String roleInPlayers) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addPlayer(id,teamName,mailId,year,month,day,roleInPlayers);
            }
        }
    }

    public void addFieldToTeam(String id, String teamName, String fieldName) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.addField(id,teamName,fieldName);
            }
        }
    }

    /****************remove assets****************/

    public void removeTeamManager(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.removeManager(id,teamName, mailId);
            }
        }
    }

    public void removeTeamCoach(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.removeCoach(id,teamName, mailId);
            }
        }
    }

    public void removeTeamPlayer(String id, String teamName, String mailId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
             ownerService.removePlayer(id,teamName, mailId);
            }
        }
    }

    public void removeTeamField(String id, String teamName, String fieldId) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                ownerService.removeField(id,teamName, fieldId);
            }
        }
    }

    public void addNewTeam(String id, String teamName, String ownerId) throws DontHavePermissionException, IncorrectInputException, ObjectNotExist, ObjectAlreadyExist, AlreadyExistException, MemberNotExist {
        if(dbController.existMember(id)) {
            Role member = dbController.getMember(id);
            if (member instanceof SystemManager) {
                systemManagerService.addNewTeam(id, teamName, ownerId);
            }
        }
    }

    public void schedulingGames(String id, String seasonId, String leagueId) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException, MemberNotExist, IncorrectInputException {
        if(dbController.existMember(id)) {
            Role member = dbController.getMember(id);
            if (member instanceof SystemManager) {
                systemManagerService.schedulingGames(id, seasonId, leagueId);
            }
        }
    }

    public void setLeagueByYear(String id, String year, String leagueId) throws ObjectNotExist, AlreadyExistException, MemberNotExist, DontHavePermissionException {
        if(dbController.existMember(id)) {
            Role member = dbController.getMember(id);
            if (member instanceof AssociationDelegate) {
                associationDelegateService.setLeagueByYear(id, leagueId, year);
            }
        }
    }

    public void closeTeam(String id, String teamName) throws DontHavePermissionException, AlreadyExistException, MemberNotExist, ObjectNotExist, IncorrectInputException {
        if(dbController.existMember(id)) {
            Role member = dbController.getMember(id);
            if (member instanceof SystemManager) {
                systemManagerService.closeTeam(id, teamName);
            }
        }
    }

    public void changeScorePolicy(String id, String league, String season, String sWinning, String sDraw, String sLosing) throws MemberNotExist, IncorrectInputException, ObjectNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)) {
            Role member = dbController.getMember(id);
            if (member instanceof AssociationDelegate) {
                associationDelegateService.changeScorePolicy(id, league, season, sWinning, sDraw, sLosing);
            }
        }
    }

    public void insertSchedulingPolicy(String id, String league, String season, String sPolicy) throws MemberNotExist, DontHavePermissionException, ObjectNotExist, AlreadyExistException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof AssociationDelegate){
                associationDelegateService.insertSchedulingPolicy(id, league, season, sPolicy);
            }
        }
    }

    public ArrayList<String> getTeamsOfOwner(String id) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getTeamsById(id);
            }
        }
        return null;
    }

    public ArrayList<String> getFieldsOfOwner(String id,String teamName) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getFieldsOfOwner(id,teamName);
            }
        }
        return null;
    }

    public ArrayList<String> getRolesToAddManager(String id) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getRolesToAddManager(id);
            }
        }
        return null;
    }

    public ArrayList<String> getAllRoles(String id) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getAllRoles(id);
            }
        }
        return null;
    }

    public ArrayList<String> getManagersOfTeam(String id, String teamName) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getManagersOfTeam(id,teamName);
            }
        }
        return null;
    }

    public ArrayList<String> getPlayersOfTeam(String id, String teamName) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getPlayersOfTeam(id,teamName);
            }
        }
        return null;
    }

    public ArrayList<String> getCoachesOfTeam(String id, String teamName) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(dbController.existMember(id)){
            Role member = dbController.getMember(id);
            if (member instanceof Owner){
                return ownerService.getCoachesOfTeam(id,teamName);
            }
        }
        return null;
    }

    public ArrayList<String> getTeams(){
        ArrayList<String> teams=new ArrayList<>();
        for (String teamName: systemManagerService.getTeams().keySet()) {
            teams.add(teamName);
        }
        return teams;
    }

    public ArrayList<String> getRefereesDoesntExistInTheLeagueAndSeason(String leagueId, String seasonId) throws DontHavePermissionException, ObjectNotExist, AlreadyExistException {
        ArrayList<String> referees=new ArrayList<>();
        HashMap<String,Referee> gerReferees= associationDelegateService.getRefereesDoesntExistInTheLeagueAndSeason(leagueId,seasonId);
        for (String name:gerReferees.keySet()) {
            referees.add(name);
        }
        return referees;
    }

    public ArrayList<String> getSeasons() {
        ArrayList<String> seasons=new ArrayList<>();
        HashMap<String, Season> getSeasons= dbController.getSeasons();
        for (String name:getSeasons.keySet()) {
            seasons.add(name);
        }
        return seasons;
    }

    public ArrayList<String> getLeagues() {
        ArrayList<String> leagues=new ArrayList<>();
        HashMap<String, League> getLeagues= dbController.getLeagues();
        for (String name:getLeagues.keySet()) {
            leagues.add(name);
        }
        return leagues;
    }

    public ArrayList<String> getSchedulingPolicies() {
        ArrayList<String> scheduling=new ArrayList<>();
        HashMap<String, ASchedulingPolicy> getSchedulingPolicies= dbController.getSchedulingPolicies();
        for (String name:getSchedulingPolicies.keySet()) {
            scheduling.add(name);
        }
        return scheduling;
    }

    public String addGameEvents(String id, String refereeId, String year,String mounth, String day, String description, String gameMinute, String eventInGame, String playersId) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        mainRefereeService.updateGameEvent(id,refereeId, year, mounth,  day, description, gameMinute, eventInGame, playersId);
        String playersInvolved = "";
        for(String player : playersId.split(";")){
            playersInvolved+= player +"\n";
        }
        return refereeId + " entered Event to game \'" + id +"\':\n"+
                    day+"/"+mounth+"/"+year+" -- " + description + " at "+ gameMinute + " minute.\n" +
                    "players involved: \n" + playersInvolved + "game event category = "+ eventInGame ;
    }

    public HashSet<String> getRefereeGames(String refereeId) throws MemberNotExist {
        Referee referee = dbController.getReferee(refereeId);
        HashSet<Game> games =  referee.getGameSchedule();
        HashSet<String> gamesId = new HashSet<>();
        for(Game game : games){
            gamesId.add(game.getId());
        }
       return gamesId;
    }

    HashMap<String,String> getTeamPlayers(String teamId) throws ObjectNotExist {
        HashMap<String, String> teamPlayers = new HashMap<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Player> players = team.getPlayers();
        for(Player player : players){
            teamPlayers.put(player.getUserMail(),player.getName());
        }
        return teamPlayers;
    }

    public HashMap<String, String> getTeamOwners(String teamId) throws ObjectNotExist {
        HashMap<String, String> teamOwneres = new HashMap<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Owner> owners = team.getOwners();
        for(Owner owner : owners){
            teamOwneres.put(owner.getUserMail(),owner.getName());
        }
        return teamOwneres;
    }

    public HashMap<String, String> getTeamCoaches(String teamId) throws ObjectNotExist {
        HashMap<String, String> teamCoaches = new HashMap<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Coach> coaches = team.getCoaches();
        for(Coach coach : coaches){
            teamCoaches.put(coach.getUserMail(),coach.getName());
        }
        return teamCoaches;
    }

    public HashMap<String, String> getTeamFields(String teamId) throws ObjectNotExist {
        HashMap<String, String> teamFields = new HashMap<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Field> fields = team.getTrainingFields();
        for(Field field : fields){
            teamFields.put(field.getNameOfField(),field.getNameOfField());
        }
        return teamFields;
    }

    public HashMap<String, String> getTeamManagers(String teamId) throws ObjectNotExist {
        HashMap<String, String> teamManagers = new HashMap<>();
        Team team = dbController.getTeam(teamId);
        HashSet<com.football.Domain.Asset.Manager> managers = team.getManagers();
        for(com.football.Domain.Asset.Manager manager : managers){
            teamManagers.put(manager.getUserMail(),manager.getName());
        }
        return teamManagers;
    }
    public HashMap<String, String> getGamePlayers(String gameId) {
        HashMap<String,String> playersInGame = new HashMap<>();
        Game game = dbController.getGame(gameId);
        Team teamHost = game.getHostTeam();
        Team teamGest = game.getVisitorTeam();

        for(Player player : teamGest.getPlayers()){
            playersInGame.put(player.getUserMail(), player.getName());
        }
        for(Player player : teamHost.getPlayers()){
            playersInGame.put(player.getUserMail(), player.getName());
        }
        return playersInGame;
    }


    public void addNotifyFollowEventGame(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyFollowEventGame(userMail);
    }

    public void addNotifyGameFinalReport(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyGameFinalReport(userMail);
    }

    public void addNotifyCreateNewGame(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyCreateNewGame(userMail);
    }

    public void addNotifyScheduleToGame(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyScheduleToGame(userMail);
    }

    public void addNotifyAddAssetToTeam(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyAddAssetToTeam(userMail);
    }
}
