package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

//for each method we get String id of the member who asks for the action.
@Service
public class OwnerService {

    @Autowired
    private DBController dbController;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;

    public void addTeam(Owner owner, Team team) throws AlreadyExistException, DontHavePermissionException {
        try {
            dbController.addTeam(owner, team);
            eventLogService.addEventLog(owner.getUserMail(), "addTeam");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        }
    }

    public void addTeam(String id, Team team) throws AlreadyExistException, DontHavePermissionException {
        try {
            if (dbController.existOwner(id)) {
                Owner currOwner = dbController.getOwner(id);
                dbController.addTeam(currOwner, team);
                eventLogService.addEventLog(id, "addTeam");
            } else {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    public void removeTheTeamFromMyList(String id, String name) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (dbController.existOwner(id)) {
                Owner currOwner = dbController.getOwner(id);
                currOwner.getTeams().remove(name);
                dbController.updateOwner(currOwner, currOwner);
                Team team = dbController.getTeam(name);
                team.getOwners().remove(currOwner);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "removeTheTeamFromMyList");
            } else {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("Object Not Exist");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }


    /******************************Add Asset- Manager, Coach, Player, Field************************************/

    /**
     * add a new Manager team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addManager(String id, String teamName, String mailId) throws AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = currOwner.getTeams().get(teamName);
            Account account = team.getAccount();
            //set amount
            account.setAmountOfTeam(account.getAmountOfTeam() - 50);
            HashMap<String, Role> roles = dbController.getRoles();
            Role role = roles.get(mailId);
            Manager manager = null;
            if (role instanceof SystemManager) {
                SystemManager systemManager = (SystemManager) role;
                manager = new Manager(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), systemManager.getBirthDate());
            } else if (role instanceof Player) {
                Player player = (Player) role;
                manager = new Manager(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate());
            } else if (role instanceof Coach) {
                Coach coach = (Coach) role;
                manager = new Manager(coach.getName(), coach.getUserMail(), coach.getPassword(), coach.getBirthDate());
            } else if (role instanceof Fan) {
                Fan fan = (Fan) role;
                manager = new Manager(fan.getName(), fan.getUserMail(), fan.getPassword(), fan.getBirthDate());
            } else if (role instanceof AssociationDelegate) {
                AssociationDelegate associationDelegate = (AssociationDelegate) role;
                manager = new Manager(associationDelegate.getName(), associationDelegate.getUserMail(), associationDelegate.getPassword(), associationDelegate.getBirthDate());
            } else if (role instanceof Referee) {
                Referee referee = (Referee) role;
                manager = new Manager(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate());
            }
            if (manager != null) {
                manager.addTeam(team);
                dbController.deleteRole(currOwner, mailId);
                dbController.addManager(currOwner, manager);
                team.addManager(manager);
//            HashMap<String, Team> teams = dbController.getTeams();
//            teams.replace(teamName, team);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "addManager");
            }

        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * add a new coach team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addCoach(String id, String teamName, String mailId) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);

            Team team = currOwner.getTeams().get(teamName);

            Account account = team.getAccount();
            //set amount
            account.setAmountOfTeam(account.getAmountOfTeam() - 50);

            HashMap<String, Role> roles = dbController.getRoles();
            Role role = roles.get(mailId);
            Coach coach = null;
            if (role instanceof SystemManager) {
                SystemManager systemManager = (SystemManager) role;
                coach = new Coach(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), systemManager.getBirthDate());
            } else if (role instanceof Manager) {
                Manager manager = (Manager) role;
                coach = new Coach(manager.getName(), manager.getUserMail(), manager.getPassword(), manager.getBirthDate());
            } else if (role instanceof Player) {
                Player player = (Player) role;
                coach = new Coach(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate());
            } else if (role instanceof Fan) {
                Fan fan = (Fan) role;
                coach = new Coach(fan.getName(), fan.getUserMail(), fan.getPassword(), fan.getBirthDate());
            } else if (role instanceof AssociationDelegate) {
                AssociationDelegate associationDelegate = (AssociationDelegate) role;
                coach = new Coach(associationDelegate.getName(), associationDelegate.getUserMail(), associationDelegate.getPassword(), associationDelegate.getBirthDate());
            } else if (role instanceof Referee) {
                Referee referee = (Referee) role;
                coach = new Coach(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate());
            }
            if (coach != null) {
                coach.addTeam(team);
                dbController.deleteRole(currOwner, mailId);
                dbController.addCoach(currOwner, coach);
                team.addCoach(coach);
//            HashMap<String, Team> teams = dbController.getTeams();
//            teams.replace(teamName, team);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "addCoach");
            }
            //todo : return !
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * add a new player team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addPlayer(String id, String teamName, String mailId, int year, int month, int day, String roleInPlayers) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = currOwner.getTeams().get(teamName);
            Account account = team.getAccount();
            //set amount
            account.setAmountOfTeam(account.getAmountOfTeam() - 50);
            HashMap<String, Role> roles = dbController.getRoles();
            Role role = roles.get(mailId);
            Player player = null;
            Date date = new Date(year, month, day);
            if (role instanceof SystemManager) {
                SystemManager systemManager = (SystemManager) role;
                player = new Player(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), date, roleInPlayers);
            } else if (role instanceof Manager) {
                Manager manager = (Manager) role;
                player = new Player(manager.getName(), manager.getUserMail(), manager.getPassword(), date, roleInPlayers);
            } else if (role instanceof Coach) {
                Coach coach = (Coach) role;
                player = new Player(coach.getName(), coach.getUserMail(), coach.getPassword(), date, roleInPlayers);
            } else if (role instanceof Fan) {
                Fan fan = (Fan) role;
                player = new Player(fan.getName(), fan.getUserMail(), fan.getPassword(), date, roleInPlayers);
            } else if (role instanceof AssociationDelegate) {
                AssociationDelegate associationDelegate = (AssociationDelegate) role;
                player = new Player(associationDelegate.getName(), associationDelegate.getUserMail(), associationDelegate.getPassword(), date, roleInPlayers);
            } else if (role instanceof Referee) {
                Referee referee = (Referee) role;
                player = new Player(referee.getName(), referee.getUserMail(), referee.getPassword(), date, roleInPlayers);
            }
            if (player != null) {
                player.addTeam(team);
                dbController.deleteRole(currOwner, mailId);
                dbController.addPlayer(currOwner, player);
                team.addPlayer(player);
//            HashMap<String, Team> teams = dbController.getTeams();
//            teams.replace(teamName, team);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "addPlayer");
            }
            //todo: return!
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }

    }

    /**
     * add a field to team asset
     *
     * @param teamName
     */
    public void addField(String id, String teamName, String fieldName) throws DontHavePermissionException, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = currOwner.getTeams().get(teamName);

            //Field field = new Field(fieldName);
            HashSet<Field> fieldExist = team.getTrainingFields();
            boolean exist = false;
            for (Field field : fieldExist) {
                if (field.getName().equals(fieldName)) {
                    exist = true;
                }
            }
            if (!exist) { //not exist
                Account account = team.getAccount();
                //set amount
                account.setAmountOfTeam(account.getAmountOfTeam() - 50);
                Field field = new Field(fieldName);
                team.addField(field);
                field.setTeam(team);
                team.getTrainingFields().add(field);
//            HashMap<String, Team> teams = dbController.getTeams(); //what is the meaning?
//            teams.replace(teamName, team);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "addField");
            }
            //todo: return !
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /******************************Remove Asset- Manage, Coach, Player, Field************************************/

    /**
     * remove manager from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removeManager(String id, String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            //gets the team
            Team team = currOwner.getTeams().get(teamName);
            //gets the list of managers
            HashSet<Manager> managers = team.getManagers();
            for (Manager manager : managers) {
                //found the manager to remove
                if (manager.getUserMail().equals(mailInput)) {
                    Fan fan = new Fan(manager.getName(), manager.getUserMail(), manager.getPassword(), manager.getBirthDate());
                    team.removeManager(manager);
                    dbController.deleteRole(currOwner, mailInput);
//                HashSet<Manager> managersAll= team.getManagers(); we did it
//                managersAll.remove(manager);
                    HashMap<String, Team> teams = dbController.getTeams();
                    //teams.replace(teamName, team);
                    dbController.addFan(currOwner, fan);
                    dbController.updateTeam(currOwner, team);
                    eventLogService.addEventLog(id, "removeManager");
                    break;
                }
            }
            //todo : return!
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * remove coach from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removeCoach(String id, String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            //gets the team
            Team team = dbController.getTeams().get(teamName);
            //gets the list of managers
            HashSet<Coach> coaches = team.getCoaches();
            for (Coach coach : coaches) {
                //found the manager to remove
                if (coach.getUserMail().equals(mailInput)) {
                    Fan fan = new Fan(coach.getName(), coach.getUserMail(), coach.getPassword(), coach.getBirthDate());
                    team.removeCoach(coach);
                    dbController.deleteRole(currOwner, mailInput);
                    dbController.addFan(currOwner, fan);
//                HashMap<String, Team> teams = dbController.getTeams(); why?
//                teams.replace(teamName, team);
                    dbController.updateTeam(currOwner, team);
                    eventLogService.addEventLog(id, "removeCoach");
                    break;
                }
            }
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * remove player from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removePlayer(String id, String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            //gets the team
            Team team = dbController.getTeams().get(teamName);
            //gets the list of managers
            HashSet<Player> players = team.getPlayers();
            for (Player player : players) {
                //found the manager to remove
                if (player.getUserMail().equals(mailInput)) {
                    Fan fan = new Fan(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate());
                    team.removePlayer(player);
                    dbController.getPlayers().remove(player);
                    dbController.deleteRole(currOwner, mailInput);
                    dbController.addFan(currOwner, fan);
//                HashMap<String, Team> teams = dbController.getTeams();
//                teams.replace(teamName, team);
                    dbController.updateTeam(currOwner, team);
                    eventLogService.addEventLog(id, "removePlayer");
                    break;
                }
            }
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * remove field from team
     *
     * @param teamName
     * @param fieldName
     */
    public void removeField(String id, String teamName, String fieldName) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);
            HashSet<Field> allFields = team.getTrainingFields();
            for (Field field : allFields) {
                if (field.getName().equals(fieldName)) {
                    allFields.remove(field);
                    if (teamName.equals(team.getHomeField().getNameOfField())) {
                        team.setHomeField(null);
                    }
                    team.setTrainingFields(allFields);
//                HashMap<String, Team> teams = dbController.getTeams();
//                teams.replace(teamName, team);
//                allFields.remove(field);
//                teams.replace(teamName, team);
                    dbController.updateTeam(currOwner, team);
                    eventLogService.addEventLog(id, "removeField");
                }
            }
            //todo : return !
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }


    /******************************All Use-Cases functions************************************/
    /**
     * add a new owner to team belong to owner
     *
     * @param teamName
     * @param mailId
     */
    public void addNewOwner(String id, String teamName, String mailId) throws AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);

            Account account = team.getAccount();
            //set amount
            account.setAmountOfTeam(account.getAmountOfTeam() - 50);

            HashMap<String, Role> roles = dbController.getRoles();
            Role role = roles.get(mailId);
            Owner owner = null;
            if (role instanceof SystemManager) {
                SystemManager systemManager = (SystemManager) role;
                owner = new Owner(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), systemManager.getBirthDate());
            } else if (role instanceof Manager) {
                Manager manager = (Manager) role;
                owner = new Owner(manager.getName(), manager.getUserMail(), manager.getPassword(), manager.getBirthDate());
            } else if (role instanceof Player) {
                Player player = (Player) role;
                owner = new Owner(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate());
            } else if (role instanceof Coach) {
                Coach coach = (Coach) role;
                owner = new Owner(coach.getName(), coach.getUserMail(), coach.getPassword(), coach.getBirthDate());
            } else if (role instanceof Fan) {
                Fan fan = (Fan) role;
                owner = new Owner(fan.getName(), fan.getUserMail(), fan.getPassword(), fan.getBirthDate());
            } else if (role instanceof AssociationDelegate) {
                AssociationDelegate associationDelegate = (AssociationDelegate) role;
                owner = new Owner(associationDelegate.getName(), associationDelegate.getUserMail(), associationDelegate.getPassword(), associationDelegate.getBirthDate());
            } else if (role instanceof Referee) {
                Referee referee = (Referee) role;
                owner = new Owner(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate());
            }
            if (owner != null) {
                owner.getTeams().put(team.getName(), team);
                dbController.deleteRole(currOwner, mailId);
                dbController.addOwner(currOwner, owner);
                team.addOwner(owner);
//            HashMap<String, Team> teams = dbController.getTeams();
//            teams.replace(teamName, team);
                dbController.updateTeam(currOwner, team);
                eventLogService.addEventLog(id, "addNewOwner");
            }
            //todo : return !
        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        } catch (AlreadyExistException alreadyExistException) {
            errorLogService.addErrorLog("Already Exist Exception");
        }
    }

    /**
     * Owner temporary close team
     *
     * @param teamName
     */
    public void temporaryTeamClosing(String id, String teamName) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);
            team.setStatus(false);
            //update team
//        HashMap<String, Team> teams = dbController.getTeams();
//        teams.replace(teamName, team);
            dbController.updateTeam(currOwner, team);
            eventLogService.addEventLog(id, "temporaryTeamClosing");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }


    /**
     * Owner reopen team
     *
     * @param teamName
     */
    public void reopenClosedTeam(String id, String teamName) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);
            team.setStatus(true);

//        HashMap<String, Team> teams = dbController.getTeams();
//        teams.replace(teamName, team);
            dbController.updateTeam(currOwner, team);
            eventLogService.addEventLog(id, "reopenClosedTeam");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /**
     * owner update the outcome of group
     *
     * @param teamName
     * @param description
     * @param amount
     */
    public void addOutCome(String id, String teamName, String description, double amount) throws DontHavePermissionException, AlreadyExistException {
        try {

            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);

            Team team = dbController.getTeams().get(teamName);
            team.addTransaction(description, amount);
            team.getAccount().setAmountOfTeam(team.getAccount().getAmountOfTeam() - amount);
//        dbController.getTeams().replace(teamName, team);
            dbController.updateTeam(currOwner, team);
            eventLogService.addEventLog(id, "addOutCome");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /**
     * owner update the income of group
     *
     * @param teamName
     * @param description
     * @param amount
     */
    public void addInCome(String id, String teamName, String description, double amount) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);

            Team team = dbController.getTeams().get(teamName);
            team.addTransaction(description, amount);
            team.getAccount().setAmountOfTeam(team.getAccount().getAmountOfTeam() + amount);
//        dbController.getTeams().replace(teamName, team);
            dbController.updateTeam(currOwner, team);
            eventLogService.addEventLog(id, "addInCome");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }


    /**
     * owner update the role of player
     *
     * @param teamName
     * @param mailId
     * @param role
     * @throws DontHavePermissionException
     */
    public void updatePlayerRole(String id, String teamName, String mailId, String role) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);
            for (Player player : team.getPlayers()) {
                //found the manager to remove
                if (player.getUserMail().equals(mailId)) {
                    team.getPlayer(player.getUserMail()).setRole(role);
//                HashMap<String, Team> teams = dbController.getTeams();
//                teams.replace(teamName, team);
                    dbController.updateTeam(currOwner, team);
                    dbController.updatePlayer(currOwner, team.getPlayer(player.getUserMail()));
                    eventLogService.addEventLog(id, "updatePlayerRole");
                    break;
                }
            }
            //todo : return !

        } catch (DontHavePermissionException dontHavePermissionException) {
            errorLogService.addErrorLog("Dont Have Permission Exception");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /**
     * owner update home field
     *
     * @param teamName
     * @param makeHomeField
     * @throws DontHavePermissionException
     */
    public void updateHomeField(String id, String teamName, String makeHomeField) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            Team team = dbController.getTeams().get(teamName);
            HashSet<Field> fields = team.getTrainingFields();

            Field field = new Field(makeHomeField);
            if (fields.contains(field)) {
                team.setHomeField(field);
            } else {
                fields.add(field);
            }
            team.setTrainingFields(fields);
            dbController.updateTeam(currOwner, team);
            eventLogService.addEventLog(id, "updateHomeField");
//        HashMap<String, Team> teams = dbController.getTeams();
//        teams.replace(teamName, team);
            //todo : return !
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /****************************************** setters *********************************************/
    public void setMoneyToAccount(String id, String teamName, double amount) throws ObjectNotExist, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);
            if (!currOwner.getTeams().containsKey(teamName)) {
                errorLogService.addErrorLog("Object Not Exist");
                throw new ObjectNotExist("team doesn't exist in owner list");
            }
            currOwner.getTeams().get(teamName).getAccount().setAmountOfTeam(amount);
            dbController.updateTeam(currOwner, currOwner.getTeams().get(teamName));
            eventLogService.addEventLog(id, "setMoneyToAccount");
        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
    }

    /***************************Getters************************************************************/


    public HashMap<String, Role> getRoles() {
        return dbController.getRoles();
    }

    public double getAccountBalance(String id, String teamName) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            if (!dbController.existOwner(id)) {
                errorLogService.addErrorLog("Member Not Exist");
                throw new MemberNotExist();
            }
            Owner currOwner = dbController.getOwner(id);

            if (!currOwner.getTeams().containsKey(teamName)) {
                errorLogService.addErrorLog("Object Not Exist");
                throw new ObjectNotExist("team tot exist in owner list");
            }

            return currOwner.getTeams().get(teamName).getAccount().getAmountOfTeam();

        } catch (MemberNotExist memberNotExist) {
            errorLogService.addErrorLog("Member Not Exist");
        }
        return -1;
    }


    public ArrayList<String> getTeamsById(String id) throws ObjectNotExist {

        ArrayList<String> teams = new ArrayList<>();
        HashMap<String, Team> teamDB = dbController.getTeams();
        if (teamDB == null || teamDB.size() == 0) {
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("team not exist");
        }
        for (String nameTeam : teamDB.keySet()) {
            if (teamDB.get(nameTeam).getOwner(id) != null) {
                teams.add(nameTeam);
            }
        }
        return teams;


    }

    public ArrayList<String> getFieldsOfOwner(String id, String teamName) throws ObjectNotExist {
        try {
            ArrayList<String> fields = new ArrayList<>();
            Team team = dbController.getTeam(teamName);

            for (Field field : team.getTrainingFields()) {
                fields.add(field.getNameOfField());
            }
            return fields;
        } catch (ObjectNotExist objectNotExist) {
            errorLogService.addErrorLog("Object Not Exist");
        }

        return null;
    }

    public ArrayList<String> getRolesToAddManager(String id) throws ObjectNotExist {
        ArrayList<String> roles = new ArrayList<>();
        HashMap<String, Role> rolesDB = dbController.getRoles();
        if (rolesDB == null || rolesDB.size() == 0) {
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("team not exist");
        }
        for (String role : rolesDB.keySet()) {
            if (!(rolesDB.get(role) instanceof Manager) && !(rolesDB.get(role) instanceof Owner)) {
                roles.add(role);
            }
        }
        return roles;


    }

    public ArrayList<String> getAllRoles(String id) throws ObjectNotExist {
        ArrayList<String> roles = new ArrayList<>();
        HashMap<String, Role> rolesDB = dbController.getRoles();
        if (rolesDB == null || rolesDB.size() == 0) {
            errorLogService.addErrorLog("Object Not Exist");
            throw new ObjectNotExist("team not exist");
        }
        for (String role : rolesDB.keySet()) {
            roles.add(role);
        }
        return roles;
    }
}
