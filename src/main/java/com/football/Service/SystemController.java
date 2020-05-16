package com.football.Service;

import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.IScorePolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.Season;
import com.football.Domain.Users.*;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.Game.EventInGame;
import com.football.Exception.*;
import javafx.util.Pair;
import com.football.DataBase.DBController;

import java.util.*;

public class SystemController {
    private String name;
    private Role connectedUser;
    private DBController dbController =  DBController.getInstance();
    //  private HashMap<Member,String> passwordValidation;


    /**
     * constructor
     *
     * @param name
     */
    public SystemController(String name) {
        this.name = name;
        //this.initSystem("", "", ""); // change it
        try {
            initSystem();
        }catch (IncorrectInputException e) {

        } catch (DontHavePermissionException e) {
        }
//        password verifications
//        passwordValidation=new HashMap<>();
//        for(Role r:roles){
//            if(r instanceof Member){
//                
//            }
//        }
        //member = user; (argument in the constructor-Member user- Fan, Owner, AD, Referee...)
    }

    public void initSystem() throws IncorrectInputException, DontHavePermissionException {
        //check if the user name and the password are connect

      //  dbController =  DBController.getInstance();
        try{
            Fan f = (Fan)signIn("admin", "admin@gmail.com" , "123",new Date(1995,2,15));
            SystemManager systemManager = new SystemManager("admin", "admin@gmail.com", f.getPassword(),new Date(1995,2,15) );
            Role role=connectedUser;
            connectedUser=systemManager;
            this.addSystemManager(f.getUserMail());
            connectedUser=role;

        }catch (AlreadyExistException e){

        } catch (MemberNotExist memberNotExist) {
            memberNotExist.printStackTrace();
        }
        connectedUser = new Guest(dbController ,null);
        System.out.println("Init System:");
        System.out.println("Connect to Security System");
        //todo
    }

    /*************************************** function for guest******************************************/
    /**
     * this function makes a Presentation.Guest into a member
     * if the member's mail doesnt exist -
     * we will remove the Presentation.Guest from the roles map and add create a Fan member by default and return true
     * if the member's mail exist in the system - prints a error message and return false.
     *
     * @return true = success or false = failed to sign
     */
    public Member signIn(String userName, String userMail, String password , Date birthDate) throws IncorrectInputException, AlreadyExistException, DontHavePermissionException {
        if(connectedUser==null)
        {
            Guest guest=new Guest(dbController,new Date(1,1,1));
            return guest.signIn(userMail, userName, password , birthDate);
        }
        return ((Guest) connectedUser).signIn(userMail, userName, password , birthDate);

    }
    public Role logOut(){
        this.connectedUser = new Guest(this.dbController , null);
        return this.connectedUser;
    }
    /**
     * this function makes a guest into an existing member.
     * if the member doesnt exist - return null
     * if the member exist - return the member
     *
     * @return
     */
    public Member logIn(String userMail, String userPassword) throws MemberNotExist, PasswordDontMatchException, DontHavePermissionException {
        if(connectedUser==null)
        {
            Guest guest=new Guest(dbController,new Date(1995,2,1));
            this.connectedUser=guest.logIn(userMail,userPassword);
            return (Member) this.connectedUser;
        }
        this.connectedUser =((Guest)this.connectedUser).logIn(userMail, userPassword);
        return (Member) this.connectedUser;
    }
    public String login(String userMail, String userPassword) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
        logIn(userMail, userPassword);
        return ((Member) this.connectedUser).getType();
    }
    /*************************************** function for system manager******************************************/

    public void removeAssociationDelegate(String id) throws DontHavePermissionException, IncorrectInputException, MemberNotExist, AlreadyExistException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            systemManager.removeAssociationDelegate(id);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public boolean removeOwner(String ownerId) throws DontHavePermissionException, IncorrectInputException, NotReadyToDelete, MemberNotExist {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.removeOwner(ownerId);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public boolean removeSystemManager(String id) throws DontHavePermissionException, MemberNotExist, IncorrectInputException, NotReadyToDelete, AlreadyExistException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.removeSystemManager(id);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public Referee getReferee(String s) throws DontHavePermissionException, ObjectNotExist, MemberNotExist {
//        if (connectedUser instanceof SystemManager) {
//            SystemManager systemManager = (SystemManager) connectedUser;
           return dbController.getReferee(s);
//        } else {
//            throw new DontHavePermissionException();
//        }
    }

    public void addSystemManager(String id) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            systemManager.addSystemManager(id);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /***
     * this function get id of the refree to remove and the id of the system manager that take care of this function
     * if the referee didnt exist - return false
     * if the referee exist - delete it and return true
     */
    public boolean removeReferee(String refereeId) throws DontHavePermissionException, IncorrectInputException, MemberNotExist, AlreadyExistException {
            if (connectedUser instanceof SystemManager) {
                SystemManager systemManager = (SystemManager) connectedUser;
                return systemManager.removeReferee(refereeId);
            } else {
                throw new DontHavePermissionException();
            }
        }
    /***
     * this function get id of the member to make referee and the id of the system manager that take care of this function
     * if the referee already exist - return false
     * if the referee not exist and success of adding it - add it and return true
     */
    public boolean addReferee(String refereeId, boolean ifMainReferee) throws DontHavePermissionException, MemberAlreadyExistException, AlreadyExistException, MemberNotExist, IncorrectInputException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.addReferee(refereeId, ifMainReferee);
        } else {
            throw new DontHavePermissionException();
        }
    }
    /**
     * this function get the team name and the id of the system manager that take care of this function
     * if the team name already exist - close the team and return true
     * if the team dont exist return false
     *
     * @param teamName
     * @return
     * @throws DontHavePermissionException
     */
    public boolean closeTeam(String teamName) throws DontHavePermissionException, ObjectNotExist, MemberNotExist, AlreadyExistException, IncorrectInputException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.closeTeam(teamName);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * this function get the id of the member we want to delete and the id of the system manager that take care of this function
     *
     * @param id
     * @return
     * @throws DontHavePermissionException
     */
    public boolean removeMember(String id) throws DontHavePermissionException, MemberNotExist, IncorrectInputException, AlreadyExistException, NotReadyToDelete {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.removeMember(id);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * this function get the path to the complaint file and the id of the system manager that take care of this function
     *
     * @param path
     * @throws DontHavePermissionException
     */
    public void watchComplaint(String path) throws DontHavePermissionException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            LinkedList<String> complaint = systemManager.watchComplaint(path);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * this function get the path to the complaint file , the response for the complaint and the id of the system manager that take care of this function
     *
     * @param path
     * @param responseForComplaint
     * @return
     * @throws DontHavePermissionException
     */
    public boolean responseComplaint(String path, LinkedList<Pair<String, String>> responseForComplaint) throws DontHavePermissionException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.ResponseComplaint(path, responseForComplaint);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void schedulingGames(String seasonId, String leagueId) throws DontHavePermissionException, ObjectNotExist, IncorrectInputException, AlreadyExistException {

           if (connectedUser instanceof SystemManager) {
               SystemManager systemManager = (SystemManager) connectedUser;
               systemManager.schedulingGames(seasonId, leagueId);
           } else {
               throw new DontHavePermissionException();
           }


    }

    public void viewSystemInformation(String path) throws DontHavePermissionException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            systemManager.viewSystemInformation(path);
        } else {
            throw new DontHavePermissionException();
        }
    }

//    /**
//     * this function get the team name and all the team member and the id of the system manager that take care of this function
//     * if the team name not exist - open the team and return true
//     * if the team exist return false
//     *
//     * @param players
//     * @param coachs
//     * @param managers
//     * @param owners
//     * @param teamName
//     * @return
//     * @throws DontHavePermissionException
//     */
    /*
    public boolean addTeam(LinkedList<String> players, LinkedList<String> coachs, LinkedList<String> managers, LinkedList<String> owners, String teamName) throws DontHavePermissionException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.addNewTeam(players, coachs, managers, owners, teamName);
        } else {
            throw new DontHavePermissionException();
        }
    }
*/
    public boolean addTeam(String teamName , String ownerId) throws DontHavePermissionException, ObjectNotExist, MemberNotExist, ObjectAlreadyExist, AlreadyExistException, IncorrectInputException {
        if (connectedUser instanceof SystemManager) {
            SystemManager systemManager = (SystemManager) connectedUser;
            return systemManager.addNewTeam(teamName , ownerId);
        } else {
            throw new DontHavePermissionException();
        }
    }

    public void addAssociationDelegate(String id) throws DontHavePermissionException, AlreadyExistException, MemberNotExist {
        if(connectedUser instanceof SystemManager){
            ((SystemManager)connectedUser).addAssociationDelegate(id);
            return;
        }
        throw new DontHavePermissionException();
    }

    public void addOwner(String id) throws DontHavePermissionException, AlreadyExistException, MemberNotExist {
        if(connectedUser instanceof SystemManager){
            ((SystemManager)connectedUser).addOwner(id);
            return;
        }
        throw new DontHavePermissionException();
    }

    /*************************************** function for owner******************************************/

    /**
     * owner:
     * add a coach
     *
     * @param teamName
     * @param mailId
     * @throws NoEnoughMoney
     */

    public void addCoach(String teamName, String mailId) throws ObjectNotExist, NoEnoughMoney, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-50 < 0)
            throw new NoEnoughMoney();
        if (!dbController.getRoles().containsKey(mailId))
            throw new MemberNotExist();
        if (dbController.getRoles().containsKey(mailId))
            if(dbController.getCoaches().containsKey(mailId))
               throw new AlreadyExistException();


            if(connectedUser instanceof  Owner)
        ((Owner) connectedUser).addCoach(teamName, mailId);
            else
                throw new DontHavePermissionException();
    }

    /**
     * owner:
     * add a player
     *
     * @param mailId
     * @param teamName
     * @param year
     * @param month
     * @param day
     * @param rolePlayer
     */
    public void addPlayer(String mailId, String teamName, int year, int month, int day, String rolePlayer) throws ObjectNotExist, IncorrectInputException, MemberNotExist, AlreadyExistException, NoEnoughMoney, DontHavePermissionException {
        if(!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-50 < 0)
            throw new NoEnoughMoney();
        if (!dbController.getRoles().containsKey(mailId))
            throw new MemberNotExist();
        if (dbController.getRoles().containsKey(mailId))
            if(dbController.getPlayers().containsKey(mailId))
                 throw new AlreadyExistException();
        if(dbController.getTeams().get(teamName).getPlayers().contains(mailId))
            throw new AlreadyExistException();

        if (year < 0 || year > 2020 || month > 12 || month < 1 || day < 1 || day > 32 || rolePlayer == null || rolePlayer=="")
            throw new IncorrectInputException();

        if(connectedUser instanceof Owner)
        ((Owner) connectedUser).addPlayer(teamName, mailId, year, month, day, rolePlayer);
        else
            throw new DontHavePermissionException();
    }

    /**
     * owner:
     * add a field to list of training field of his team
     *
     * @param teamName
     * @param fieldName
     */
    public void addField(String teamName, String fieldName) throws DontHavePermissionException, IncorrectInputException, AlreadyExistException, ObjectAlreadyExist, NoEnoughMoney, ObjectNotExist {
        if(!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-50 < 0)
            throw new NoEnoughMoney();
        if (dbController.getTeams().get(teamName).getFieldFromTrainingFields(fieldName)!=null)
            throw new ObjectAlreadyExist();
        if(connectedUser instanceof Owner) {
            ((Owner) connectedUser).addField(teamName, fieldName);
        }
        else
            throw new DontHavePermissionException();
    }

    /**
     * owner:
     * add new manager to one of his groups
     *
     * @param teamName
     * @param mailId
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     */
    public void addManager(String teamName, String mailId) throws NoEnoughMoney, ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if (!dbController.getRoles().containsKey(mailId))
            throw new MemberNotExist();
        if (dbController.getRoles().containsKey(mailId))
            if(dbController.getManagers().containsKey(mailId))
                     throw new AlreadyExistException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-50 < 0)
            throw new NoEnoughMoney();

        if(connectedUser instanceof Owner)
        ((Owner) connectedUser).addManager(teamName, mailId);
        else
            throw new DontHavePermissionException();
    }

    /**
     * owner:
     * removes a manager
     *
     * @param teamName
     * @param mailToRemove
     */
    public void removeManager(String teamName, String mailToRemove) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist ");
        if (!dbController.getRoles().containsKey(mailToRemove))
            throw new MemberNotExist();
        if (!dbController.getRoles().containsKey(mailToRemove))
            throw new ObjectNotExist("member not exist");

        ((Owner) connectedUser).removeManager(teamName, mailToRemove);

    }

    /**
     * owner:
     * remove coach
     *
     * @param teamName
     * @param mailToRemove
     * @throws MemberNotExist
     */
    public void removeCoach(String teamName, String mailToRemove) throws ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (dbController.getRoles().containsKey(connectedUser.getName()))
            if(dbController.getCoaches().containsKey(mailToRemove))
                 throw new DontHavePermissionException();
        if (!dbController.getRoles().containsKey(mailToRemove))
            throw new MemberNotExist();

        ((Owner) connectedUser).removeCoach(teamName, mailToRemove);
    }

    /**
     * owner:
     * remove player from team
     *
     * @param teamName
     * @param mailToRemove
     */
    public void removePlayer(String teamName, String mailToRemove) throws DontHavePermissionException, ObjectNotExist, MemberNotExist, AlreadyExistException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if ((dbController.getRoles().containsKey(mailToRemove)))
            if(!dbController.getPlayers().containsKey(mailToRemove))
                  throw new MemberNotExist();
        if (!dbController.getRoles().containsKey(mailToRemove))
            throw new MemberNotExist();

        ((Owner) connectedUser).removePlayer(teamName, mailToRemove);
    }

    /**
     * owner:
     * removes a field
     *
     * @param teamName
     * @param fieldName
     */
    public void removeField(String teamName, String fieldName) throws ObjectNotExist, DontHavePermissionException, MemberNotExist, IncorrectInputException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        if(dbController.getTeams().get(teamName).getField(fieldName)==null)
           throw new ObjectNotExist("field not exist");
        ((Owner) connectedUser).removeField(teamName, fieldName);
    }

    /**
     * owner:
     * add new owner to one of his groups
     *
     * @param teamName
     * @param mailId
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     * @throws MemberNotExist
     */
    public void addNewOwner(String teamName, String mailId) throws NoEnoughMoney, ObjectNotExist, MemberNotExist, AlreadyExistException, DontHavePermissionException {
        if (!dbController.getRoles().containsKey(mailId))
            throw new MemberNotExist();
        if (dbController.getRoles().containsKey(mailId))
            if (dbController.getOwners().containsKey(mailId))
                 throw new AlreadyExistException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-50 < 0)
            throw new NoEnoughMoney();

        if(connectedUser instanceof Owner)
        ((Owner) connectedUser).addNewOwner(teamName, mailId);
        else
            throw new DontHavePermissionException();
    }

    /**
     * owner:
     * close team temporary
     *
     * @param teamName
     * @throws ObjectNotExist
     */
    public void temporaryTeamClosing(String teamName) throws ObjectNotExist, DontHavePermissionException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        if (!((Owner) connectedUser).getTeams().containsKey(teamName))
            throw new ObjectNotExist("team not exist");
        ((Owner) connectedUser).temporaryTeamClosing(teamName);
    }

    /**
     * owner:
     * reopen team
     *
     * @param teamName
     * @throws ObjectNotExist
     */
    public void reopenClosedTeam(String teamName) throws ObjectNotExist, DontHavePermissionException {
        if(! (this.connectedUser instanceof Owner))
            throw new DontHavePermissionException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        if (!((Owner) connectedUser).getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        ((Owner) connectedUser).reopenClosedTeam(teamName);
    }

    /**
     * owner:
     * add outcome of team
     *
     * @param teamName
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     */
    public void addOutCome(String teamName, String description, double amount) throws NoEnoughMoney, ObjectNotExist, AccountNotExist, IncorrectInputException, DontHavePermissionException {
       if(!dbController.getTeams().containsKey(teamName))
           throw new ObjectNotExist("team not exist");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam() - amount < 0)
            throw new NoEnoughMoney();
        if (description == null || description.equals("") || amount < 0)
            throw new IncorrectInputException();
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam() < 0)
            throw new NoEnoughMoney();
        if (dbController.getTeams().get(teamName).getAccount() == null)
            throw new AccountNotExist();

        if(connectedUser instanceof Owner)
        ((Owner) connectedUser).addOutCome(teamName, description, amount);
        else
            throw new DontHavePermissionException();
    }

    /**
     * owner:
     * add outcome of team
     *
     * @param teamName
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     */
    public void addInCome(String teamName, String description, double amount) throws NoEnoughMoney, ObjectNotExist, AccountNotExist, IncorrectInputException, DontHavePermissionException {
        if (description == null || description.equals("") || amount < 0)
            throw new IncorrectInputException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        if (dbController.getTeams().get(teamName).getAccount().getAmountOfTeam()-amount < 0)
            throw new NoEnoughMoney();
        if (dbController.getTeams().get(teamName).getAccount() == null)
            throw new AccountNotExist();

        if(connectedUser instanceof Owner)
        ((Owner) connectedUser).addInCome(teamName, description, amount);
        else
            throw new DontHavePermissionException();
    }


    /**
     * owner:
     * update role details
     * @param teamName
     * @param mailId
     * @param role
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     * @throws AccountNotExist
     * @throws IncorrectInputException
     * @throws DontHavePermissionException
     * @throws MemberNotExist
     * @throws AlreadyExistException
     */
    public void updatePlayerRole(String teamName, String mailId,String role) throws NoEnoughMoney, ObjectNotExist, AccountNotExist, IncorrectInputException, DontHavePermissionException, MemberNotExist, AlreadyExistException {
       if(role==null || role.equals(""))
           throw new IncorrectInputException();
        if (dbController.getRoles().containsKey(mailId))
            if (dbController.getPlayers().containsKey(mailId))
                if(dbController.getPlayers().get(mailId).getRole().equals(role))
                  throw new AlreadyExistException();
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");

        ((Owner) connectedUser).updatePlayerRole(teamName, mailId,role);
    }

    /**
     * owner:
     * update home field
     * @param teamName
     * @param makeHomeField
     * @throws NoEnoughMoney
     * @throws ObjectNotExist
     * @throws AccountNotExist
     * @throws IncorrectInputException
     * @throws DontHavePermissionException
     * @throws MemberNotExist
     * @throws AlreadyExistException
     */
    public void updateHomeField(String teamName,String makeHomeField) throws ObjectNotExist, DontHavePermissionException, AlreadyExistException {
        if (!dbController.getTeams().containsKey(teamName))
            throw new ObjectNotExist("");
        if(dbController.getTeams().get(teamName).getTrainingFields().contains(makeHomeField))
            throw new AlreadyExistException();
        if(dbController.getTeams().get(teamName).getHomeField().getNameOfField().equals(makeHomeField))
            throw new AlreadyExistException();
        ((Owner) connectedUser).updateHomeField(teamName, makeHomeField);
    }



    /********Getters for Owner & System manager********/
    public HashMap<String, Role> getRoles() throws DontHavePermissionException {
        if (connectedUser instanceof Owner) {
            return ((Owner) connectedUser).getRoles();
        }
        if (connectedUser instanceof SystemManager) {
            return ((SystemManager) connectedUser).getRoles();
        }
        throw new DontHavePermissionException();
    }

    public HashMap<String, Team> getTeams() {
        try {
            if (connectedUser instanceof Owner) {
                return ((Owner) connectedUser).getTeams();
            }
            if (connectedUser instanceof SystemManager) {
                return ((SystemManager) connectedUser).getTeams();
            }
            throw new DontHavePermissionException();
        }
        catch (Exception e)
        {

        }
        return new HashMap<>();
    }

    public void setMoneyToAccount(String teamName , double amount) throws ObjectNotExist, DontHavePermissionException {
        if(! (connectedUser instanceof Owner) )
            throw new DontHavePermissionException();
        ((Owner)connectedUser).setMoneyToAccount(teamName,amount);
    }

    public double getAccountBalance(String teamName) throws DontHavePermissionException, ObjectNotExist {
        if(! (connectedUser instanceof Owner) )
            throw new DontHavePermissionException();
        return ((Owner)connectedUser).getAccountBalance(teamName);
    }
    /*************************************** function for associationDelegate******************************************/

    /**
     * this
     *
     * @param
     * @param leagueName
     * @throws AlreadyExistException
     */
    public void setLeague(String leagueName) throws AlreadyExistException, IncorrectInputException, DontHavePermissionException {
        try {
            ((AssociationDelegate) connectedUser).setLeague(leagueName);
        } catch (IncorrectInputException incorrectInput) {
            throw new IncorrectInputException(leagueName);
        } catch (AlreadyExistException alreadyExist) {
            throw new AlreadyExistException();
        } catch (Exception e) {
            throw new DontHavePermissionException();
        }
    }

    public void setLeagueByYear(String specificLeague, String year) throws ObjectNotExist, AlreadyExistException, DontHavePermissionException {
        try {
            ((AssociationDelegate) connectedUser).setLeagueByYear(specificLeague, year);
        } catch (ObjectNotExist incorrectInput) {
            throw new ObjectNotExist(incorrectInput.getMessage());
        } catch (AlreadyExistException alreadyExist) {
            throw new AlreadyExistException();
        }
        catch (Exception e){
            throw new DontHavePermissionException();
        }
    }

    public HashMap<String, League> getLeagues() throws DontHavePermissionException {

        try{
            HashMap<String, League> leagues = dbController.getLeagues();
            return leagues;
        }
        catch(Exception e){
            throw new DontHavePermissionException();

        }
    }

    public League getLeague(String league) throws DontHavePermissionException, ObjectNotExist {
        return dbController.getLeague(league);
    }

    public HashMap<String, Referee> getRefereesDoesntExistInTheLeagueAndSeason(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        HashMap<String, Referee> referees = new HashMap<>();
        try {
            referees = ((AssociationDelegate) connectedUser).getRefereesDoesntExistInTheLeagueAndSeason(league, season);

        }catch(ObjectNotExist objectNotExist){
            throw new ObjectNotExist("");
        } catch (Exception e) {
            throw new DontHavePermissionException();
        }
        return referees;
    }

    public void addRefereeToLeagueInSeason(String league, String season, String refereeToAdd) throws DontHavePermissionException, ObjectNotExist {
        try{
            ((AssociationDelegate)connectedUser).addRefereeToLeagueInSeason(league, season, refereeToAdd);
        }
        catch(ObjectNotExist objectNotExist){
            throw new ObjectNotExist("");
        }
        catch(Exception e){
            throw new DontHavePermissionException();
        }

    }
    public HashMap<String, Referee> getRefereesInLeagueInSeason(String league , String season) throws DontHavePermissionException, ObjectNotExist {
        if( this.connectedUser instanceof AssociationDelegate){
            return ((AssociationDelegate)connectedUser).getRefereesInLeagueInSeason(league,season);
        }
        throw new DontHavePermissionException();
    }

    public HashMap<String, Season> getSeasons() {
        HashMap<String, Season> seasons=new HashMap<String, Season>();
        try{
            seasons = dbController.getSeasons();
            throw new DontHavePermissionException();
        }catch(Exception e){

        }
        return seasons;
    }

    public void changeScorePolicy(String league, String season, String sWinning, String sDraw, String sLosing) throws ObjectNotExist, IncorrectInputException, DontHavePermissionException {
       try {
           ((AssociationDelegate) connectedUser).changeScorePolicy(league, season, sWinning, sDraw, sLosing);
       }

       catch(ObjectNotExist objectNotExist){
           throw new ObjectNotExist("");
       }
       catch(IncorrectInputException incorrectInput){
           throw new IncorrectInputException();
       }
       catch(Exception e){
           throw new DontHavePermissionException();
       }
    }

    public IScorePolicy getScorePolicy(String league, String season) throws DontHavePermissionException, ObjectNotExist {
        if(this.connectedUser instanceof AssociationDelegate)
            return ((AssociationDelegate)connectedUser).getScorePolicy(league, season);
        else
            throw new DontHavePermissionException();
    }

    public HashMap<String, ASchedulingPolicy> getSchedulingPolicies() throws DontHavePermissionException{
       return ((AssociationDelegate)connectedUser).getSchedulingPolicies();
    }

    public void addSchedulingPolicy(String policyName) throws IncorrectInputException, DontHavePermissionException {
        ((AssociationDelegate)connectedUser).addSchedulingPolicy(policyName);
    }
    public HashSet<Game> getGames(String league , String season) throws ObjectNotExist, DontHavePermissionException {
        if (connectedUser instanceof SystemManager) {
            return ((SystemManager) this.connectedUser).getGames(league, season);
        }
        else if( connectedUser instanceof Fan){
            //return ((Fan)this.connectedUser).getGames(league,season);
            return null;
        }
        else {
            throw new DontHavePermissionException();
        }
    }
    /*************************************** function for Referee ******************************************/

    /** secondary and main referee **/

    /**
     * the function allows the referee to update his own details.
     * @param newName
     * @param newMail
     * @param newTraining
     * @throws IncorrectInputException
     * @throws DontHavePermissionException
     * @throws MemberNotExist
     * @throws AlreadyExistException
     */
    public void updateDetails(String newName, String newMail,String newTraining) throws IncorrectInputException, DontHavePermissionException, MemberNotExist, AlreadyExistException {
        if (connectedUser instanceof MainReferee) {
            MainReferee referee = (MainReferee) connectedUser;
            referee.updateDetails(newName, newMail,newTraining);
        }
        else if (connectedUser instanceof SecondaryReferee) {
            SecondaryReferee referee = (SecondaryReferee) connectedUser;
            referee.updateDetails(newName, newMail,newTraining);
        }
        else {
            throw new DontHavePermissionException();
        }
    }


    /**
     * the function allows the referee watch his own schedule
     * @return hashSet of games on the referee's schedule
     * @throws DontHavePermissionException
     */

    public HashSet<Game> getGameSchedule() throws DontHavePermissionException {
        if (connectedUser instanceof Referee) {
            Referee referee = (Referee) connectedUser;
            return referee.getGameSchedule();
        } else {
            throw new DontHavePermissionException();
        }
    }

    /** main referee only **/

    /**
     * the function allows the referee to watch all the games he can still edit
     * @return all the referee's editable games.
     * @throws DontHavePermissionException
     */
    public LinkedList<Game> getEditableGames () throws DontHavePermissionException {
        if (connectedUser instanceof MainReferee) {
            MainReferee mainReferee = (MainReferee) connectedUser;
            return mainReferee.getEditableGames ();
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * the function allows the referee to update a game event
     * @return all the referee's editable games.
     * @throws DontHavePermissionException
     */
    public void updateGameEvent(Game game, int timeInGame, EventInGame event, Date date, String description, ArrayList<Player> players){
        if (connectedUser instanceof MainReferee) {
            MainReferee mainReferee = (MainReferee) connectedUser;
            mainReferee.updateGameEvent(game, timeInGame, event, date, description, players);
        } else {

        }
    }


    /*************************************** function for Fan ******************************************/

    /**
     * the function allows the referee to update his own details.
     * @param newName
     * @param newMail
     * @throws DontHavePermissionException
     * @throws IncorrectInputException
     * @throws MemberNotExist
     * @throws AlreadyExistException
     */

    public void updatePersonalDetails(String newName,String newMail) throws DontHavePermissionException, IncorrectInputException, MemberNotExist, AlreadyExistException {
        if (connectedUser instanceof Fan) {
            Fan fan = (Fan) connectedUser;
            fan.updatePersonalDetails(newName, newMail);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * the function allows the fan send a complaint to the system manager
     * @param path
     * @param complaint
     * @throws DontHavePermissionException
     */

    public void sendComplaint (String path, String complaint) throws DontHavePermissionException {
        if (connectedUser instanceof Fan) {
            Fan fan = (Fan) connectedUser;
            fan.sendComplaint(path, complaint);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * the function sign the fan as a follower of te given team
     * @param team
     * @throws DontHavePermissionException
     */

    public void addFollowerToTeam(Team team) throws DontHavePermissionException {
        if (connectedUser instanceof Fan) {
            Fan fan = (Fan) connectedUser;
            fan.followTeam(team);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /**
     * the function sign the fan as a follower of te given game
     * @param game
     * @throws DontHavePermissionException
     */
    public void addFollowerToGame(Game game) throws DontHavePermissionException {
        if (connectedUser instanceof Fan) {
            Fan fan = (Fan) connectedUser;
            fan.followGame(game);
        } else {
            throw new DontHavePermissionException();
        }
    }

    /*************************************************************************************************************/

    public HashMap<String, Fan> getFans(Role role) throws DontHavePermissionException {
        return dbController.getFans();
    }

    public HashMap<String, Fan> getFans() throws DontHavePermissionException {
        return dbController.getFans();
    }

    public HashMap<String, Referee> getReferees() {
        return dbController.getReferees();
    }

    public HashMap<String, Player> getPlayers(Role role) throws DontHavePermissionException {
        return dbController.getPlayers();
    }

    public HashMap<String, Owner> getOwners(Role role) throws DontHavePermissionException {
        return dbController.getOwners();
    }

    public HashMap<String, Manager> getManagers(Role role) throws DontHavePermissionException {
        return dbController.getManagers();
    }

    public HashMap<String, Coach> getCoach(Role role) throws DontHavePermissionException {
        return dbController.getCoaches();
    }

    public HashMap<String,Member> getMembers() throws DontHavePermissionException {
        return dbController.getMembers();
    }

    public void setSchedulingPolicyToLeagueInSeason(String specificLeague, String year, String policy) throws IncorrectInputException, ObjectNotExist, DontHavePermissionException {
        try{
            ((AssociationDelegate)connectedUser).setSchedulingPolicyToLeagueInSeason(specificLeague, year, policy);
        }
        catch(IncorrectInputException incorrectInput){
            throw new IncorrectInputException();
        }
        catch(ObjectNotExist objectNotExist){
            throw new ObjectNotExist("");
        }
        catch(Exception e){
            throw new DontHavePermissionException();
        }

    }
    public ASchedulingPolicy getSchedulingPolicyInLeagueInSeason(String league , String seson) throws DontHavePermissionException, ObjectNotExist {
        if(this.connectedUser instanceof AssociationDelegate){
            return ((AssociationDelegate)connectedUser).getSchedulingPolicyInLeagueInSeason(league,seson);
        }
        else {
            throw new DontHavePermissionException();
        }
    }

    public HashMap<String, SystemManager> getSystemManager() throws DontHavePermissionException {
        return dbController.getSystemManagers();
    }

    public HashMap<String, AssociationDelegate> getAssociationDelegates() throws DontHavePermissionException {
        return dbController.getAssociationDelegate();
    }


    public HashMap<String, Role> getOwnersAndFans() throws DontHavePermissionException {
        return dbController.getOwnersAndFans();
    }

    public Team getTeamByName (String teamName) throws DontHavePermissionException, ObjectNotExist {
        Team team = dbController.getTeam( teamName);
        return team;
    }

    public Role getConnectedUser (){
        return connectedUser;
    }

    /**
     * Associate Dellegite
     * this function add team to league in season
     * @param league
     * @param season
     * @param teamName
     * @throws DontHavePermissionException
     * @throws ObjectNotExist
     * @throws AlreadyExistException
     */
    public void addTeamToLeagueInSeason(String league,String season,String teamName) throws DontHavePermissionException, ObjectNotExist, AlreadyExistException, IncorrectInputException {
        if(this.connectedUser instanceof AssociationDelegate){
            ((AssociationDelegate)connectedUser).addTeamToLeagueInSeason(league,season,teamName);
        }
        else {
            throw new DontHavePermissionException();
        }
    }


    public void deleteDBcontroller() {
        dbController.deleteAll();
    }
}
