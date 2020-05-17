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


    @Override
    public HashMap<String, Role> getRoles() {
        return null;
    }

    @Override
    public HashMap<String, Member> getMembers() {
        return null;
    }

    @Override
    public HashMap<String, Fan> getFans() {
        return null;
    }

    @Override
    public HashMap<String, Referee> getReferees() {
        return null;
    }

    @Override
    public HashMap<String, Owner> getOwners() {
        return null;
    }

    @Override
    public HashMap<String, Player> getPlayers() {
        return null;
    }

    @Override
    public HashMap<String, Manager> getManagers() {
        return null;
    }

    @Override
    public HashMap<String, Coach> getCoaches() {
        return null;
    }

    @Override
    public HashMap<String, SystemManager> getSystemManagers() {
        return null;
    }

    @Override
    public HashMap<String, AssociationDelegate> getAssociationDelegate() {
        return null;
    }

    @Override
    public HashMap<String, Team> getTeams() {
        return null;
    }

    @Override
    public HashMap<String, League> getLeagues() {
        return null;
    }

    @Override
    public HashMap<String, Season> getSeasons() {
        return null;
    }

    @Override
    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies() {
        return null;
    }

    @Override
    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        return null;
    }

    @Override
    public SystemManager getSystemManagers(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public AssociationDelegate getAssociationDelegate(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public Role getMember(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public Team getTeam(String id) throws ObjectNotExist {
        return null;
    }

    @Override
    public League getLeague(String id) throws ObjectNotExist {
        return null;
    }

    @Override
    public Season getSeason(String id) throws ObjectNotExist {
        return null;
    }

    @Override
    public Fan getFan(String id) throws MemberNotExist {
        return null;
    }

    @Override
    public Referee getReferee(String id) throws MemberNotExist {
        return null;
    }

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
        roles.put(fan.getUserMail(), fan);
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

    @Override
    public boolean existFan(String fanId) {
        return false;
    }

    @Override
    public boolean existTeam(String teamName) {
        return false;
    }

    @Override
    public boolean existMember(String id) {
        return false;
    }

    @Override
    public boolean existAssociationDelegate(String id) {
        return false;
    }

    @Override
    public boolean existSystemManager(String id) {
        return false;
    }

    @Override
    public boolean existOwner(String ownerId) {
        return false;
    }

    @Override
    public boolean existSeason(String id) {
        return false;
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

    @Override
    public Owner getOwner(String id) throws MemberNotExist {
        return null;
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
}
