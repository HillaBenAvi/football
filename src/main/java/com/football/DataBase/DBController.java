package com.football.DataBase;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.MemberNotExist;
import com.football.Exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@Service
public class DBController implements DAO{

    @Autowired
    private DB db;

//    private static final DBController instance = new DBController();
//
//    public static DBController getInstance(){
//        return instance;
//    }

    private DBController() {
        this.db = new DB();
    }
    /***************************************Presentation.Guest function******************************************/


    /***************************************Getters******************************************/
    public HashMap<String, Role> getRoles()  {
            return db.getRoles(); }

    public HashMap<String, Team> getTeams()  {
            return db.getTeams();
    }

    public HashMap<String, League> getLeagues()  {
            return db.getLeagues();
    }

    public HashMap<String, Referee> getReferees() {
            return db.getReferees();
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

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies()   {
            return db.getSchedulingPolicies();
    }

    public HashMap<String, Season> getSeasons()   {
            return db.getSeasons();
    }

    public HashMap<String, SystemManager> getSystemManagers()   {
            return db.getSystemManagers();
    }

    public SystemManager getSystemManagers(String id)   {
            return db.getSystemManagers(id);
    }

    public HashMap<String, AssociationDelegate> getAssociationDelegate()   {
            return db.getAssociationDelegate();
    }

    public AssociationDelegate getAssociationDelegate( String id)   {
            return db.getAssociationDelegate(id);
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
            if (db.existTeam(teamName)) {
                return db.getTeam(teamName);
            } else {
                throw new ObjectNotExist("the team id is not exist");
            }
        }

    public League getLeague(String leagueId) throws ObjectNotExist {
            if (db.existLeague(leagueId)) {
                return db.getLeague(leagueId);
            } else {
                throw new ObjectNotExist("the league id is not exist");
            }
        }

    public Season getSeason(String seasonId) throws ObjectNotExist {
            if (db.existSeason(seasonId)) {
                return db.getSeason(seasonId);
            } else {
                throw new ObjectNotExist("the league id is not exist");
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
                owner.setDb(this);
                return owner;
            } else {
                throw new MemberNotExist();
            }
        }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        return db.getLeague(league).getLeagueInSeason(db.getSeason(season)).getGames();
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
            if (db.existAssociationDelegate(id)) {
                db.removeAssociationDelegate(id);
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void deleteSystemManager(Role role, String id) throws MemberNotExist, DontHavePermissionException {
        if (role instanceof SystemManager) {
            if (db.existSystemManager(id)) {
                db.removeSystemManager(id);
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
            if (db.existMember(associationDelegate.getUserMail()))
                throw new AlreadyExistException();
            db.addAssociationDelegate(associationDelegate);
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
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            // if (id in AssTable || id in Manager)
            if (db.existSeason(season.getYear()))
                throw new AlreadyExistException();
            db.addSeason(season);

        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addLeague(Role role, League league) throws AlreadyExistException, DontHavePermissionException {
        if (role instanceof SystemManager || role instanceof AssociationDelegate) {
            if (db.existLeague(league.getName()))
                throw new AlreadyExistException();
            db.addLeague(league);
        } else {
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

            if (db.existMember(systemManager.getUserMail()))
                throw new AlreadyExistException();
            db.addSystemManager(systemManager);
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

            return db.existAssociationDelegate(id);
        }

    public boolean existSystemManager( String id)  {
        return db.existSystemManager(id);
    }

    public boolean existOwner( String ownerId)  {

            return db.existOwner(ownerId);
        }

    public boolean existSeason(String id) {
        return db.existSeason(id);
    }



}
