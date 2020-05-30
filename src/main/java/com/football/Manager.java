
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

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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
        sPolicy = removegarbij(sPolicy);
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

    public ArrayList<String> getLeaguesIds() {
        ArrayList<String> getLeagues= dbController.getLeaguesIds();

        return getLeagues;
    }

    public ArrayList<String> getSchedulingPolicies() {
        ArrayList<String> scheduling=new ArrayList<>();
        scheduling.add("All teams play each other once");
        scheduling.add("All teams play each other twice");
        return scheduling;
    }

    public String addGameEvents(String id, String refereeId, String year,String mounth, String day, String description, String gameMinute, String eventInGame, String playersId) throws MemberNotExist, DontHavePermissionException, ObjectNotExist, AlreadyExistException {
        id = removegarbij(id);
        description = removegarbij(description);

        mainRefereeService.updateGameEvent(id,refereeId, year, mounth,  day, description, gameMinute, eventInGame, playersId);
        String playersInvolved = "";
        for(String player : playersId.split(";")){
            playersInvolved+= player +"\n";
        }
        return refereeId + " entered Event to game \'" + id +"\':\n"+
                day+"/"+mounth+"/"+year+" -- " + description + " at "+ gameMinute + " minute.\n" +
                "players involved: \n" + playersInvolved + "game event category = "+ eventInGame ;
    }

    private String removegarbij(String str) {
        String res = "";
        for(int i = 0; i<str.length(); i++){
            if(str.charAt(i) == '%' && str.charAt(i+1) == '2' && str.charAt(i+2) == '0'){
                res += ' ';
                i+=2;
            }
            else{
                res += str.charAt(i);
            }
        }
        return res;
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

    ArrayList<String> getTeamPlayers(String teamId) throws ObjectNotExist {
        ArrayList<String> teamPlayers = new ArrayList<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Player> players = team.getPlayers();
        for(Player player : players){
            teamPlayers.add(player.getUserMail());
        }
        return teamPlayers;
    }

    public ArrayList<String> getTeamOwners(String teamId) throws ObjectNotExist {
        ArrayList<String> teamOwners = new ArrayList<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Owner> owners = team.getOwners();
        for(Owner owner : owners){
            teamOwners.add(owner.getUserMail());
        }
        return teamOwners;
    }

    public ArrayList<String> getTeamCoaches(String teamId) throws ObjectNotExist {
        ArrayList<String> teamCoaches = new ArrayList<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Coach> coaches = team.getCoaches();
        for(Coach coach : coaches){
            teamCoaches.add(coach.getUserMail());
        }
        return teamCoaches;
    }

    public ArrayList<String> getTeamFields(String teamId) throws ObjectNotExist {
        ArrayList<String> teamFields = new ArrayList<>();
        Team team = dbController.getTeam(teamId);
        HashSet<Field> fields = team.getTrainingFields();
        for(Field field : fields){
            teamFields.add(field.getNameOfField());
        }
        return teamFields;
    }

    public ArrayList<String> getTeamManagers(String teamId) throws ObjectNotExist {
        ArrayList<String> teamManagers = new ArrayList<>();
        Team team = dbController.getTeam(teamId);
        HashSet<com.football.Domain.Asset.Manager> managers = team.getManagers();
        for(com.football.Domain.Asset.Manager manager : managers){
            teamManagers.add(manager.getUserMail());
        }
        return teamManagers;
    }
    public ArrayList<String> getGamePlayers(String gameId) {
        String gameId2 = "";
        for(int i = 0 ; i<gameId.length() ; i++){
            if(gameId.charAt(i)!='-'){
                gameId2+=gameId.charAt(i);
            }
            else{
                gameId2+=' ';
            }
        }
        ArrayList<String> playersInGame = new ArrayList<>();
        Game game = dbController.getGame(gameId2);
        Team teamHost = game.getHostTeam();
        Team teamGest = game.getVisitorTeam();

        for(Player player : teamGest.getPlayers()){
            playersInGame.add(player.getUserMail());
        }
        for(Player player : teamHost.getPlayers()){
            playersInGame.add(player.getUserMail());
        }
        return playersInGame;
    }

    //Daniel 26.5
    public ArrayList<String> getTeamsByOwner(String ownerId) throws MemberNotExist {
        Owner owner = dbController.getOwner(ownerId);
        HashMap<String, Team> teams = owner.getTeams();
        return new ArrayList<>(teams.keySet());
    }

    //Daniel 26.5
    public ArrayList<String> getAllTeams(){
        HashMap<String, Team> teams = dbController.getTeams();
        ArrayList<String> strTeams = new ArrayList<>();
        strTeams.addAll(teams.keySet());
        return strTeams;

    }

    //get all fans - key=mail, value=name
    public  ArrayList<String>  getPotentialManagers(String id, String teamName) {
        return getAllFans();
    }

    //get all fans - key=mail, value=name
    public ArrayList<String> getPotentialPlayers(String id, String teamName) {
        return getAllFans();
    }

    //get all fans - key=mail, value=name
    public ArrayList<String> getPotentialCoaches(String id, String teamName) {
        return getAllFans();
    }

    //get all fans - key=mail, value=name
    private ArrayList<String> getAllFans(){
        HashMap<String, Fan> fans = dbController.getFans();
        ArrayList<String> strFans = new ArrayList<>();
        for(String fan : fans.keySet()){
            strFans.add(fan);
        }
        return strFans;
    }


    public ArrayList<String> getLeagueInSeasonsIds() {
        return dbController.getLeagueInSeasonsIds();
    }

    public ArrayList<String> getPotentialOwners() {
        ArrayList<String> ownersAndFans = new ArrayList<>();
        ownersAndFans.addAll(dbController.getOwnersAndFans().keySet());
        return ownersAndFans;
    }

    public void addNotifyFollowEventGame(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyFollowEventGame(userMail);
    }

    public void addNotifyGameFinalReport(String userMail, String gameId) throws AlreadyExistException, DontHavePermissionException, MemberNotExist {
        if (dbController.existMember(userMail)) {
            Role member = dbController.getMember(userMail);
            if (member instanceof MainReferee) {
                gameId = removegarbij(gameId);
                mainRefereeService.createGameReport(gameId);
            }
        }
    }

    public void addNotifyAddAssetToTeam(String userMail) throws AlreadyExistException, DontHavePermissionException {
        dbController.addNotifyAddAssetToTeam(userMail);
    }


}
