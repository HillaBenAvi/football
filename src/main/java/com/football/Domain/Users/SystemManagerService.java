package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

@Service
public class SystemManagerService {

    @Autowired
    private DBController dbController;


    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;
    /********************remove members*********************/


    /**
     * this function get id of a association deligate and make it fan
     *
     * @param id
     * @throws DontHavePermissionException - if the memver is not system manager
     * @throws MemberNotExist              - if the association deligate with this id does not exist
     * @throws AlreadyExistException       - if a fan with this specific id already exist
     * @throws IncorrectInputException     - if the id is not a mail
     */
    public void removeAssociationDelegate(String id, String idToRemove) throws DontHavePermissionException, AlreadyExistException, IncorrectInputException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existAssociationDelegate(idToRemove)) {
                    if (isLegalid(idToRemove)) {
                        AssociationDelegate temp = dbController.getAssociationDelegate(idToRemove);
                        Fan newFan = new Fan(temp.getName(), temp.getUserMail(), temp.getPassword(), temp.getBirthDate());
                        dbController.deleteAssociationDelegate(manager, idToRemove);
                        dbController.addFan(manager, newFan);
                        eventLogService.addEventLog(id,"removeAssociationDelegate");
                    } else {
                        errorLogService.addErrorLog("Incorrect Input Exception");
                        throw new IncorrectInputException();
                    }
                } else {
                    errorLogService.addErrorLog("Member Not Exist");
                    throw new MemberNotExist();
                }
            } else {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        }
    }

    /**
     * this function get id of a owner and make it fan
     *
     * @param ownerId
     * @return true if it become to fan and else false
     * @throws IncorrectInputException-     if the id is not a mail
     * @throws NotReadyToDelete             - if the owner still have team in his list you cant delete it
     * @throws MemberNotExist               - if the owner with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     */

    public boolean removeOwner(String id, String ownerId) throws IncorrectInputException, NotReadyToDelete, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existOwner(ownerId)) {
                    if (isLegalid(ownerId)) {
                        Owner owner = dbController.getOwner(ownerId);
                        if (owner.notHaveTeams()) {
                            dbController.deleteOwner(manager, ownerId);
                            eventLogService.addEventLog(id,"removeOwner");
                            return true;
                        } else {
                            errorLogService.addErrorLog("Not Ready To Delete");
                            throw new NotReadyToDelete("this owner ows teams. you must close the teams before");
                        }
                    } else {
                        errorLogService.addErrorLog("Incorrect Input Exception");
                        throw new IncorrectInputException();
                    }
                }
            } else {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }

        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (NotReadyToDelete notReadyToDelete) {
            errorLogService.addErrorLog("Not Ready To Delete");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        }
        return false;
    }


    /**
     * this function get id of a system manager and make it fan
     *
     * @param id
     * @return true if it become to fan and else false
     * @throws MemberNotExist-              if the system manager with this id does not exist
     * @throws IncorrectInputException-     if the id is not a mail
     * @throws NotReadyToDelete             - if this is the last system manager
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException-       if a fan with this specific id already exist
     */
    public boolean removeSystemManager(String id, String idToRemove) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existSystemManager(idToRemove)) {
                    if (isLegalid(id)) {
                        if (dbController.getSystemManagers().size() > 1 && !(id.equals(idToRemove))) {
                            SystemManager systemManager = dbController.getSystemManagers(idToRemove);
                            Fan fan = new Fan(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), systemManager.getBirthDate());
                            dbController.deleteSystemManager(manager, idToRemove);
                            dbController.addFan(manager, fan);
                            eventLogService.addEventLog(id,"removeSystemManager");
                            return true;
                        } else {
                            errorLogService.addErrorLog("Not Ready To Delete");
                            throw new NotReadyToDelete("this is the only system manager in the system. you can't delete him");
                        }
                    } else {
                        errorLogService.addErrorLog("Incorrect Input Exception");
                        throw new IncorrectInputException();
                    }
                } else {
                    errorLogService.addErrorLog("Member Not Exist");
                    throw new MemberNotExist();
                }
            }
        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (NotReadyToDelete notReadyToDelete) {
            errorLogService.addErrorLog("Not Ready To Delete");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
        return false;
    }

    /**
     * * this function get id of a referee and make it fan
     *
     * @param id
     * @return true if it become to fan and else false
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist-              if the referee with this id does not exist
     * @throws AlreadyExistException-       if a fan with this specific id already exist
     * @throws IncorrectInputException-     if the id is not a mail
     */
    public boolean removeReferee(String id, String refereeId) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existReferee(refereeId)) {
                    if (isLegalid(refereeId)) {
                        Referee referee = dbController.getReferee(refereeId);
                        if (referee.hadGames()) {
                            errorLogService.addErrorLog("Incorrect Input Exception");
                            throw new IncorrectInputException("this referee has games to work in , you cant delete it");
                        } else {
                            dbController.deleteReferee(manager, refereeId);
                            Fan newFan = new Fan(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate());
                            dbController.addFan(manager, newFan);
                            eventLogService.addEventLog(id,"removeReferee");
                            return true;
                        }
                    } else {
                        errorLogService.addErrorLog("Incorrect Input Exception");
                        throw new IncorrectInputException();
                    }
                } else {
                    errorLogService.addErrorLog("Member Not Exist");
                    throw new MemberNotExist();
                }
            }

        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
        return false;
    }


    /**
     * * this function get id of a member and remove it from the system
     *
     * @param id
     * @return true if it delete from the db and else false
     * @throws IncorrectInputException-     if the id is not a mail
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist-              if the member with this id does not exist
     * @throws AlreadyExistException-       if a fan with this specific id already exist (it make the member a fan and than delete it)
     * @throws NotReadyToDelete-if          one of the member is have object that cant be delete with
     */
    public boolean removeMember(String id, String memberId) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (isLegalid(id)) {
                    if (dbController.existMember(memberId)) {
                        Role role = dbController.getMember(memberId);
                        if (role instanceof Player) {
                            ((Player) role).deleteAllTheData();
                        } else if (role instanceof Coach) {
                            ((Coach) role).deleteAllTheData();
                        } else if (role instanceof Manager) {
                            ((Manager) role).deleteAllTheData();
                        } else if (role instanceof Owner) {
                            removeOwner(id, memberId);
                        } else if (role instanceof Referee) {
                            removeReferee(id, memberId);
                        } else if (role instanceof AssociationDelegate) {
                            removeAssociationDelegate(id, memberId);
                        } else if (role instanceof SystemManager) {
                            removeSystemManager(id, memberId);
                        } else if (role instanceof Fan) {
                            // removeFan(((Fan) role).getUserMail());
                        }
                        dbController.deleteMember(manager, memberId);
                        eventLogService.addEventLog(id,"removeMember");
                        return true;
                    } else {
                        throw new MemberNotExist();
                    }
                } else {
                    throw new IncorrectInputException("input are illegal");
                }
            }
        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        } catch (NotReadyToDelete notReadyToDelete) {
            errorLogService.addErrorLog("Not Ready To Delete");
        }
        return false;
    }


    /********************add members*********************/


    /**
     * * this function get id of a member and make it referee
     *
     * @param id
     * @param ifMainReferee- if the referee will be main or secondary
     * @return true if it add to the db as referee and else false
     * @throws IncorrectInputException-     if the id is not a mail
     * @throws MemberAlreadyExistException- if a referee with this specific id already exist
     * @throws MemberNotExist-              if the referee with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException-       if a referee with this specific id already exist
     */
    public boolean addReferee(String id, String refereeId, boolean ifMainReferee) throws MemberAlreadyExistException, DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (isLegalid(refereeId)) {
                    if (!dbController.existReferee(refereeId)) {
                        if (dbController.existFan(refereeId)) {
                            Fan fan = dbController.getFan(refereeId);
                            Referee referee = new SecondaryReferee(fan);
                            if (ifMainReferee) {
                                referee = new MainReferee(fan);
                            }
                            dbController.deleteFan(manager, refereeId);
                            dbController.addReferee(manager, referee);
                            eventLogService.addEventLog(id,"addReferee");
                            return true;
                        } else {
                            errorLogService.addErrorLog("Member Not Exist");
                            throw new MemberNotExist();
                        }
                    } else {
                        errorLogService.addErrorLog("Member Already Exist Exception");
                        throw new MemberAlreadyExistException();
                    }
                } else {
                    errorLogService.addErrorLog("Incorrect Input Exception");
                    throw new IncorrectInputException();
                }
            }
        } catch (IncorrectInputException incorrectInputException) {
            errorLogService.addErrorLog("Incorrect Input Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
        return false;
    }

    /**
     * * this function get id of a member and make it fassociation deligate
     *
     * @param id
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist-              if the association deligate with this id does not exist
     * @throws AlreadyExistException        - if a association deligate with this specific id already exist
     */
    public void addAssociationDelegate(String id, String associationDelegateId) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existFan(associationDelegateId)) {
                    Member member = (Member) this.dbController.getMember(associationDelegateId);
                    AssociationDelegate newA_D = new AssociationDelegate(member.getName(), member.getUserMail(), member.getPassword(), member.getBirthDate());
                    this.dbController.deleteRole(manager, associationDelegateId);
                    this.dbController.addAssociationDelegate(manager, newA_D);
                    eventLogService.addEventLog(id,"addAssociationDelegate");
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * * this function get id of a member and make it owner
     *
     * @param id
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist-              if the owner with this id does not exist
     * @throws AlreadyExistException-       if a owner with this specific id already exist
     */
    public void addOwner(String id, String ownerId) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (this.dbController.getFans().containsKey(ownerId)) {
                    Member member = (Member) this.dbController.getMember(ownerId);
                    Owner newOwner = new Owner(member.getName(), member.getUserMail(), member.getPassword(), member.getBirthDate());
                    this.dbController.deleteRole(manager, ownerId);
                    this.dbController.addOwner(manager, newOwner);
                    eventLogService.addEventLog(id,"addOwner");
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }

    }

    /**
     * * this function get id of a member and make it a system manager
     *
     * @param id
     * @throws MemberNotExist-              if the system manager with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException-       if a system manager with this specific id already exist
     */
    public void addSystemManager(String id, String systemManagerId) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (this.dbController.getFans().containsKey(systemManagerId)) {
                    Member member = (Member) this.dbController.getMember(systemManagerId);
                    SystemManager newSystemManager = new SystemManager(member.getName(), member.getUserMail(), member.getPassword(), member.getBirthDate());
                    this.dbController.deleteRole(manager, systemManagerId);
                    this.dbController.addSystemManager(manager, newSystemManager);
                    eventLogService.addEventLog(id,"addSystemManager");
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }


    /*********************another functionality***********************/

    /**
     * this function get path and print all the data in the file - this data will be the system infromation
     *
     * @param path
     */
    public void viewSystemInformation(String id, String path) {
        if (dbController.existSystemManager(id)) {
            print(readLineByLine(path));
        }
    }


    /**
     * this function get path to file and return the data from the file
     *
     * @param newPath
     * @return
     */
    public LinkedList<String> readLineByLine(String newPath) {
        LinkedList<String> list = new LinkedList<>();
        try {
            File newText = new File(newPath);
            String allText = new String();
            Scanner scanner = new Scanner(newText);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                list.add(line);

            }
            return list;
        } catch (Exception e) {
            System.out.print("the path is not legal");
        }
        return list;
    }

    /**
     * this function get season and league id and scheduling all the games in this specific league in season
     *
     * @param seasonId
     * @param leagueId
     * @throws ObjectNotExist               - if the league id or the season id is not exists
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws IncorrectInputException-     if the league id or the season id input enter in a wrong way
     */
    public void schedulingGames(String id, String seasonId, String leagueId) throws ObjectNotExist, DontHavePermissionException, IncorrectInputException, AlreadyExistException, MemberNotExist {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                League league = dbController.getLeague(leagueId);
                Season season = dbController.getSeason(seasonId);
                LeagueInSeason leagueInSeason = league.getLeagueInSeason(season);
                LinkedList<Team> teams = leagueInSeason.getTeamsForScheduling();
                if (teams.size() % 2 != 0) {
                    errorLogService.addErrorLog("Incorrect Input Exception");
                    throw new IncorrectInputException("need to be even teams to scheduling");
                } else if (notEnoughReferee(leagueInSeason, teams.size()) == false) {
                    errorLogService.addErrorLog("Incorrect Input Exception");
                    throw new IncorrectInputException("not enough refree for scheduling games");
                } else {
                    ASchedulingPolicy schedulingPolicy = leagueInSeason.getPolicy();
                    Set<Game> games = schedulingPolicy.setGamesOfTeams(teams, leagueInSeason);
                    leagueInSeason.addGames(games);
                    dbController.addGames(manager, games);
                    dbController.updateLeagueInSeason(manager, leagueInSeason);
                    eventLogService.addEventLog(id,"schedulingGames");
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * this function get team name and close it
     *
     * @param teamName
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws ObjectNotExist               - if the team name is not connected to real team and not exist in the db
     * @throws MemberNotExist               - after the closing the owner become fan if its his only team - if the owner not exist in the db
     * @throws AlreadyExistException        -after the closing the owner become fan if its his only team - if the fan with this id already exist in the db
     * @throws IncorrectInputException      - if the team name enter in a wrong way
     */
    public boolean closeTeam(String id, String teamName) throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, IncorrectInputException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (dbController.existTeam(teamName)) {
                    Team team = dbController.getTeam(teamName);
                    if (team.getGamesSize() == 0) {
                        HashSet<Owner> allTheOwnerOfTheGroup = team.getOwners();
                        changeTheOwnerToFan(manager, allTheOwnerOfTheGroup);
                        dbController.removeTeam(manager, teamName);
                        eventLogService.addEventLog(id,"closeTeam");
                        return true;
                    } else {
                        errorLogService.addErrorLog("Incorrect Input Exception");
                        throw new IncorrectInputException("this team have games , you cant close it");
                    }
                } else {
                    errorLogService.addErrorLog("Object Not Exist");
                    throw new ObjectNotExist("this team name is not exist");
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
        return false;
    }

    /**
     * this function get team name and idowner and open new team that the owner is the owner of this team
     *
     * @param teamName
     * @param idOwner
     * @return
     * @throws ObjectAlreadyExist-exist     in the db team with this team name
     * @throws ObjectNotExist               - id owner is not exist in the db
     * @throws MemberNotExist               - id owner is not exist in the db
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException        - id owner is already exist in the db not as fan
     * @throws IncorrectInputException      - the team name enter in a wrong way
     */
    public boolean addNewTeam(String id, String teamName, String idOwner) throws ObjectAlreadyExist, ObjectNotExist, DontHavePermissionException, AlreadyExistException, IncorrectInputException {
        try {
            if (dbController.existSystemManager(id)) {
                SystemManager manager = dbController.getSystemManagers(id);
                if (alreadyIncludeThisTeamName(teamName) == true) {
                    errorLogService.addErrorLog("Object Already Exist");
                    throw new ObjectAlreadyExist();
                } else if (legalInputTeamName(teamName) == false) {
                    errorLogService.addErrorLog("Incorrect Input Exception");
                    throw new IncorrectInputException();
                } else if (dbController.existOwner(idOwner) == false && dbController.existFan(idOwner) == false) {
                    errorLogService.addErrorLog("Object Not Exist");
                    throw new ObjectNotExist("the is you enter is not exist as owner of a team");
                } else {
                    Owner owner = null;
                    Role role = dbController.getMember(idOwner);
                    if (role instanceof Fan) {//if its the first team for this owner
                        owner = new Owner(role.getName(), ((Fan) role).getUserMail(), ((Fan) role).getPassword(), role.getBirthDate());
                        dbController.deleteFan(manager, ((Fan) role).getUserMail());
                        dbController.addOwner(manager, owner);

                    } else if (role instanceof Owner) {
                        owner = (Owner) role;
                    }
                    if (owner != null) {
                        Account account = new Account();
                        Team newTeam = new Team(teamName, account, owner);
                        owner.getTeams().put(teamName, newTeam);
                        dbController.updateOwner(manager, owner);
                        dbController.addTeam(manager, newTeam);
                        eventLogService.addEventLog(id,"addNewTeam");
                    }
                }
            }
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
        return false;
    }


    /**************all the complaint function*****************/

    /**
     * this function get path and show all the complaint in the file
     *
     * @param path
     * @return
     */
    public LinkedList<String> watchComplaint(String id, String path) throws DontHavePermissionException, AlreadyExistException {
        if (dbController.existSystemManager(id)) {
            try {
                SystemManager manager = dbController.getSystemManagers(id);
                LinkedList<String> complaintList = readLineByLine(path);
                return complaintList;
            } catch (MemberNotExist memberNotExist) {
                errorLogService.addErrorLog("Member Not Exist");
            }
        } else {
            errorLogService.addErrorLog("Dont Have Permission Exception");
            throw new DontHavePermissionException();
        }
        return null;
    }


    /**
     * @param path
     * @param response this hashMap represent - the number of the complaint and the response for the complain
     * @return
     */
    public boolean ResponseComplaint(String id, String path, LinkedList<Pair<String, String>> response) throws MemberNotExist, DontHavePermissionException {
        //this function get the linkes list after the manager added his response for the complaint
        if (dbController.existSystemManager(id)) {
            SystemManager manager = dbController.getSystemManagers(id);
            writeToFile(path, response);
            return true;
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * this function get path and the response for the complaint , and write it to the file
     *
     * @param path
     * @param response
     */
    private void writeToFile(String path, LinkedList<Pair<String, String>> response) {
        try {
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < response.size(); i++) {
                if (!response.get(i).getValue().equals("")) {
                    bw.write(i + "." + response.get(i).getKey() + ". answer: " + response.get(i).getValue());
                } else {
                    bw.write(i + "." + response.get(i).getKey() + ".");
                }
            }
            bw.close();
        } catch (Exception e) {

        }
    }


    /*******************private functions********************/

    /**
     * return true if the string is in a mail version and else false
     *
     * @param str
     * @return
     */
    private boolean isLegalid(String str) {
        if (str.contains("@") && str.contains(".")) {
            return true;
        }
        return false;
    }

    /**
     * this function get linkelist and print it to the system
     *
     * @param readLineByLine
     */
    private void print(LinkedList<String> readLineByLine) {
        for (int i = 0; i < readLineByLine.size(); i++) {
            System.out.println(readLineByLine.get(i));
        }
    }

    /**
     * this function help to the scheduling games. check if there is enough referee in the league
     * in season to scheduling all the teams in this league in season
     *
     * @param leagueInSeason
     * @param numOfTeams
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     */
    private boolean notEnoughReferee(LeagueInSeason leagueInSeason, int numOfTeams) throws DontHavePermissionException {
        HashMap<String, Referee> refereeHashMap = leagueInSeason.getReferees();
        // if(refereeHashMap.size()<) {
        //     return false;
        //  }
        int counterMain = 0;
        int counterSecondary = 0;

        for (String referee : refereeHashMap.keySet()
        ) {
            if (refereeHashMap.get(referee) instanceof MainReferee) {
                counterMain++;
            } else if (refereeHashMap.get(referee) instanceof SecondaryReferee) {
                counterSecondary++;
            }
        }
        if (counterSecondary < numOfTeams / 2) {
            return false;
        }
        if (counterMain < numOfTeams / 2) {
            return false;
        }
        return true;
    }

    /**
     * this function get a hashset with owner and change them to be fans
     *
     * @param allTheOwnerOfTheGroup
     * @throws MemberNotExist-if            the id in the hashset is not exist in the db as owner
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException-if     there is already a fan with this specific id
     */
    private void changeTheOwnerToFan(SystemManager manager, HashSet<Owner> allTheOwnerOfTheGroup) throws MemberNotExist, DontHavePermissionException, AlreadyExistException {
        for (Owner owner : allTheOwnerOfTheGroup
        ) {
            if (owner.getTeams().size() == 0) {
                Fan newFan = new Fan(owner.getName(), owner.getUserMail(), owner.getPassword(), owner.getBirthDate());
                dbController.deleteOwner(manager, owner.getUserMail());
                dbController.addFan(manager, newFan);
            }
        }
    }

    /**
     * this function return true if the team added and false if there were problem with the data
     */
    private boolean legalInputTeamName(String teamName) {
        int counter = 0;
        for (int i = 0; i < teamName.length(); i++) {
            if (teamName.charAt(i) >= '0' && teamName.charAt(i) <= '9')
                counter++;
        }
        if (counter == teamName.length())
            return false;

        return true;
    }

    /**
     * this function check if there is already a team with the specific team name
     *
     * @param teamName
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     */
    private boolean alreadyIncludeThisTeamName(String teamName) throws DontHavePermissionException {

        return dbController.existTeam(teamName);
    }


    /************* help function for testing *************/
    public HashMap<String, Role> getRoles() throws DontHavePermissionException {
        return this.dbController.getRoles();
    }

    public HashMap<String, Team> getTeams() throws DontHavePermissionException {
        return this.dbController.getTeams();
    }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        return this.dbController.getGames(league, season);
    }


}
