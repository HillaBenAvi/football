package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Exception.*;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Owner extends Member {
    private HashMap<String, Team> teams;
    private DBController dbController;


    public Owner(String name, String userMail, String password, Date birthDate, DBController dbController) throws DontHavePermissionException {
        super(name, userMail, password, birthDate);
        this.dbController = dbController;
        teams = new HashMap<>();
    }

    public Owner(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
        dbController = null;
        teams = new HashMap<>();
    }

    @Override
    public String getType() {
        return "Owner";
    }

    public void setDb(DBController dbController) {
        if (dbController == null) {
            this.dbController = dbController;
        }
    }

    public void addTeam(Team team) {
        teams.put(team.getName(), team);
    }

    public void removeTheTeamFromMyList(String name) {
        teams.remove(name);
    }

    /******************************Add Asset- Manage, Coach, Player, Field************************************/

    /**
     * add a new Manager team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addManager(String teamName, String mailId) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        Team team = teams.get(teamName);

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
            dbController.deleteRole(this, mailId);
            dbController.addManager(this, manager);
            team.addManager(manager);
            HashMap<String, Team> teams = dbController.getTeams();
            teams.replace(teamName, team);
        }

    }

    /**
     * add a new coach team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addCoach(String teamName, String mailId) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        Team team = teams.get(teamName);

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
            dbController.deleteRole(this, mailId);
            dbController.addCoach(this, coach);
            team.addCoach(coach);
            HashMap<String, Team> teams = dbController.getTeams();
            teams.replace(teamName, team);
        }
    }

    /**
     * add a new player team to team belong to owner
     *
     * @param teamName,mailId
     */
    public void addPlayer(String teamName, String mailId, int year, int month, int day, String roleInPlayers) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        Team team = teams.get(teamName);

        Account account = team.getAccount();
        //set amount
        account.setAmountOfTeam(account.getAmountOfTeam() - 50);

        HashMap<String, Role> roles = dbController.getRoles();
        Role role = roles.get(mailId);
        Player player=null;
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
        if(player!=null){
            player.addTeam(team);
            dbController.deleteRole(this, mailId);
            dbController.addPlayer(this, player);
            team.addPlayer(player);
            HashMap<String, Team> teams = dbController.getTeams();
            teams.replace(teamName, team);
        }

    }

    /**
     * add a field to team asset
     *
     * @param teamName
     */
    public void addField(String teamName, String fieldName) throws DontHavePermissionException {
        Team team = teams.get(teamName);

        //Field field = new Field(fieldName);
        HashSet<Field> fieldExist=team.getTrainingFields();
        boolean exist=false;
        for(Field field:fieldExist){
            if(field.getName().equals(fieldName)){
                exist=true;
            }
        }
        if(!exist){
            //not exist
            Account account = team.getAccount();
            //set amount
            account.setAmountOfTeam(account.getAmountOfTeam() - 50);

            Field field=new Field(fieldName);

            team.addField(field);
            field.setTeam(team);
            HashSet<Field> trainingFields = dbController.getTeams().get(teamName).getTrainingFields();
            trainingFields.add(field);
            HashMap<String, Team> teams = dbController.getTeams();
            teams.replace(teamName, team);

        }



    }

    /******************************Remove Asset- Manage, Coach, Player, Field************************************/

    /**
     * remove manager from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removeManager(String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        //gets the team
        Team team = teams.get(teamName);
        //gets the list of managers
        HashSet<Manager> managers = team.getManagers();
        for (Manager manager : managers) {
            //found the manager to remove
            if (manager.getUserMail().equals(mailInput)) {
                Fan fan = new Fan(manager.getName(), manager.getUserMail(), manager.getPassword(), manager.getBirthDate(), dbController);
                team.removeManager(manager);
                dbController.deleteRole(this, mailInput);

                HashSet<Manager> managersAll= team.getManagers();
                managersAll.remove(manager);

                HashMap<String, Team> teams = dbController.getTeams();
                teams.replace(teamName, team);

                dbController.addFan(this, fan);
                break;
            }
        }
    }

    /**
     * remove coach from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removeCoach(String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        //gets the team
        Team team = dbController.getTeams().get(teamName);
        //gets the list of managers
        HashSet<Coach> coaches = team.getCoaches();
        for (Coach coach : coaches) {
            //found the manager to remove
            if (coach.getUserMail().equals(mailInput)) {
                Fan fan = new Fan(coach.getName(), coach.getUserMail(), coach.getPassword(), coach.getBirthDate(), dbController);
                team.removeCoach(coach);
                dbController.deleteRole(this, mailInput);
                dbController.addFan(this, fan);
                HashMap<String, Team> teams = dbController.getTeams();
                teams.replace(teamName, team);
                break;
            }
        }
    }

    /**
     * remove player from team and make him fan
     *
     * @param teamName
     * @param mailInput
     */
    public void removePlayer(String teamName, String mailInput) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        //gets the team
        Team team = dbController.getTeams().get(teamName);
        //gets the list of managers
        HashSet<Player> players = team.getPlayers();
        for (Player player : players) {
            //found the manager to remove
            if (player.getUserMail().equals(mailInput)) {
                Fan fan = new Fan(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate(), dbController);
                team.removePlayer(player);
                dbController.getPlayers().remove(player);
                dbController.deleteRole(this, mailInput);
                dbController.addFan(this, fan);
                HashMap<String, Team> teams = dbController.getTeams();
                teams.replace(teamName, team);
                break;
            }
        }
    }

    /**
     * remove field from team
     *
     * @param teamName
     * @param fieldName
     */
    public void removeField(String teamName, String fieldName) throws DontHavePermissionException {

       Team team = dbController.getTeams().get(teamName);
       HashSet<Field> allFields=dbController.getTeams().get(teamName).getTrainingFields();
        for(Field field:allFields){
            if(field.getName().equals(fieldName)){
                allFields.remove(field);
                if(teamName.equals(team.getHomeField().getNameOfField())){
                    team.setHomeField(null);
                }
                team.setTrainingFields(allFields);
                HashMap<String, Team> teams = dbController.getTeams();
                teams.replace(teamName, team);
                allFields.remove(field);
                teams.replace(teamName, team);
            }
        }


    }


    /******************************All Use-Cases functions************************************/
    /**
     * add a new owner to team belong to owner
     *
     * @param teamName
     * @param mailId
     */
    public void addNewOwner(String teamName, String mailId) throws MemberNotExist, AlreadyExistException, DontHavePermissionException {
        Team team = teams.get(teamName);

        Account account = team.getAccount();
        //set amount
        account.setAmountOfTeam(account.getAmountOfTeam() - 50);

        HashMap<String, Role> roles = dbController.getRoles();
        Role role = roles.get(mailId);
        Owner owner=null;
        if (role instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) role;
            owner = new Owner(systemManager.getName(), systemManager.getUserMail(), systemManager.getPassword(), systemManager.getBirthDate(), dbController);
        } else if (role instanceof Manager) {
            Manager manager = (Manager) role;
            owner = new Owner(manager.getName(), manager.getUserMail(), manager.getPassword(), manager.getBirthDate(), dbController);
        } else if (role instanceof Player) {
            Player player = (Player) role;
            owner = new Owner(player.getName(), player.getUserMail(), player.getPassword(), player.getBirthDate(), dbController);
        } else if (role instanceof Coach) {
            Coach coach = (Coach) role;
            owner = new Owner(coach.getName(), coach.getUserMail(), coach.getPassword(), coach.getBirthDate(), dbController);
        } else if (role instanceof Fan) {
            Fan fan = (Fan) role;
            owner = new Owner(fan.getName(), fan.getUserMail(), fan.getPassword(), fan.getBirthDate(), dbController);
        } else if (role instanceof AssociationDelegate) {
            AssociationDelegate associationDelegate = (AssociationDelegate) role;
            owner = new Owner(associationDelegate.getName(), associationDelegate.getUserMail(), associationDelegate.getPassword(), associationDelegate.getBirthDate(), dbController);
        } else if (role instanceof Referee) {
            Referee referee = (Referee) role;
            owner = new Owner(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate(), dbController);
        }
        if(owner!=null){
            owner.addTeam(team);
            dbController.deleteRole(this, mailId);
            dbController.addOwner(this, owner);
            team.addOwner(owner);
            HashMap<String, Team> teams = dbController.getTeams();
            teams.replace(teamName, team);
        }

    }

    /**
     * Owner temporary close team
     *
     * @param teamName
     */
    public void temporaryTeamClosing(String teamName) throws DontHavePermissionException {
        Team team = dbController.getTeams().get(teamName);
        team.setStatus(false);
        //update team
        HashMap<String, Team> teams = dbController.getTeams();
        teams.replace(teamName, team);
    }

    /**
     * Owner reopen team
     *
     * @param teamName
     */
    public void reopenClosedTeam(String teamName) throws DontHavePermissionException {

        Team team = dbController.getTeams().get(teamName);
        team.setStatus(true);

        HashMap<String, Team> teams = dbController.getTeams();
        teams.replace(teamName, team);
    }

    /**
     * owner update the outcome of group
     *
     * @param teamName
     * @param description
     * @param amount
     */
    public void addOutCome(String teamName, String description, double amount) throws NoEnoughMoney, DontHavePermissionException {
        Team team = dbController.getTeams().get(teamName);
        team.addTransaction(description, amount);
        team.getAccount().setAmountOfTeam(team.getAccount().getAmountOfTeam() - amount);
        dbController.getTeams().replace(teamName, team);
    }

    /**
     * owner update the income of group
     *
     * @param teamName
     * @param description
     * @param amount
     */
    public void addInCome(String teamName, String description, double amount) throws DontHavePermissionException {
        Team team = dbController.getTeams().get(teamName);
        team.addTransaction(description, amount);
        team.getAccount().setAmountOfTeam(team.getAccount().getAmountOfTeam() + amount);
        dbController.getTeams().replace(teamName, team);
    }


    /**
     * owner update the role of player
     * @param teamName
     * @param mailId
     * @param role
     * @throws DontHavePermissionException
     */
    public void updatePlayerRole(String teamName, String mailId,String role) throws DontHavePermissionException {
        Team team = dbController.getTeams().get(teamName);
        HashSet<Player> players = team.getPlayers();
        for (Player player : players) {
            //found the manager to remove
            if (player.getUserMail().equals(mailId)) {
                player.setRole(role);

                HashMap<String, Team> teams = dbController.getTeams();
                teams.replace(teamName, team);
                break;
            }
        }

    }

    /**
     * owner update home field
     * @param teamName
     * @param makeHomeField
     * @throws DontHavePermissionException
     */
    public void updateHomeField(String teamName,String makeHomeField) throws DontHavePermissionException {
        Team team = dbController.getTeams().get(teamName);
        HashSet<Field> fields=team.getTrainingFields();

        Field field=new Field(makeHomeField);
        if(fields.contains(field)){
            team.setHomeField(field);
        }else{
            fields.add(field);
        }
        team.setTrainingFields(fields);

        HashMap<String, Team> teams = dbController.getTeams();
        teams.replace(teamName, team);
    }

    /****************************************** setters *********************************************/
    public void setMoneyToAccount(String teamName, double amount) throws ObjectNotExist {
        if (!this.teams.containsKey(teamName)) {
            throw new ObjectNotExist("team tot exist in owner list");
        }
        this.teams.get(teamName).getAccount().setAmountOfTeam(amount);
    }

    /***************************Getters************************************************************/
    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public HashMap<String, Role> getRoles() throws DontHavePermissionException {
        return dbController.getRoles();
    }

    public double getAccountBalance(String teamName) throws ObjectNotExist {
        if (!this.teams.containsKey(teamName)) {
            throw new ObjectNotExist("team tot exist in owner list");
        }
        return this.teams.get(teamName).getAccount().getAmountOfTeam();
    }


    public boolean notHaveTeams() {
        return teams.size()==0;
    }
}
