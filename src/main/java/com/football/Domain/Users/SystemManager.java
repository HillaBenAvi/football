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
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class SystemManager extends Member {

    private DBController dbController;

    public SystemManager(String name, String userMail, String password, DBController dbController, Date birthDate) {
        super(name, userMail, password, birthDate);
        this.dbController = dbController;
    }

    /**
     * this function get id of a association deligate and make it fan
     * @param id
     * @throws DontHavePermissionException - if the memver is not system manager
     * @throws MemberNotExist - if the association deligate with this id does not exist
     * @throws AlreadyExistException - if a fan with this specific id already exist
     * @throws IncorrectInputException - if the id is not a mail
     */
    public void removeAssociationDelegate(String id) throws DontHavePermissionException, MemberNotExist, AlreadyExistException, IncorrectInputException {
        if (dbController.existAssociationDelegate( id)) {
            if (inputAreLegal(id)) {
                AssociationDelegate temp = dbController.getAssociationDelegate( id);
                Fan newFan = new Fan(temp.getName(), temp.getUserMail(), temp.getPassword(), temp.getBirthDate());
                dbController.deleteAssociationDelegate(this, id);
                dbController.addFan(temp, newFan);
            } else {
                throw new IncorrectInputException();
            }
        } else {
            throw new MemberNotExist();
        }
    }

    /**
     * this function get id of a owner and make it fan
     * @param ownerId
     * @return true if it become to fan and else false
     * @throws IncorrectInputException- if the id is not a mail
     * @throws NotReadyToDelete - if the owner still have team in his list you cant delete it
     * @throws MemberNotExist - if the owner with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     */
    public boolean removeOwner(String ownerId) throws IncorrectInputException, NotReadyToDelete, MemberNotExist, DontHavePermissionException {
        if (dbController.existOwner( ownerId)) {
            if (inputAreLegal(ownerId)) {
                Owner owner = (Owner) dbController.getMember( ownerId);
                if (owner.notHaveTeams() == true) {
                    dbController.deleteOwner(this, ownerId);
                    return true;
                } else {
                    throw new NotReadyToDelete("this owner ows teams. you must close the team before");
                }
            } else {
                throw new IncorrectInputException();
            }
        } else {
            throw new MemberNotExist();
        }
    }

    /**
     * this function get id of a system manager and make it fan
     * @param id
     * @return true if it become to fan and else false
     * @throws MemberNotExist- if the system manager with this id does not exist
     * @throws IncorrectInputException- if the id is not a mail
     * @throws NotReadyToDelete - if this is the last system manager
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException- if a fan with this specific id already exist
     */
    public boolean removeSystemManager(String id) throws MemberNotExist, IncorrectInputException, NotReadyToDelete, DontHavePermissionException, AlreadyExistException {
        if (dbController.existSystemManager( id)) {
            if (inputAreLegal(id)) {
                if (dbController.getSystemManagers().size() > 1 && !(this.getUserMail().equals(id))) {
                    SystemManager systemManager=dbController.getSystemManagers( id);
                    Fan fan=new Fan(systemManager.getName(),systemManager.getUserMail(),systemManager.getPassword(),systemManager.getBirthDate());
                    dbController.deleteSystemManager(this, id);
                    dbController.addFan(this,fan);
                    return true;
                } else {
                    throw new NotReadyToDelete("this is the only system manager in the system. you can't delete him");
                }
            } else {
                throw new IncorrectInputException();
            }
        } else {
            throw new MemberNotExist();
        }
    }

    /**
     ** this function get id of a referee and make it fan
     * @param id
     * @return true if it become to fan and else false
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist- if the referee with this id does not exist
     * @throws AlreadyExistException- if a fan with this specific id already exist
     * @throws IncorrectInputException- if the id is not a mail
     */
    public boolean removeReferee(String id) throws DontHavePermissionException, MemberNotExist, AlreadyExistException, IncorrectInputException {
        if (dbController.existReferee( id)) {
            if (inputAreLegal(id)) {
                Referee referee = (Referee) dbController.getMember( id);
                if(referee.hadGames())
                {
                    throw new IncorrectInputException("this referee has games to work in , you cant delete it");
                }
                else {
                    dbController.deleteReferee(this, id);
                    Fan newFan = new Fan(referee.getName(), referee.getUserMail(), referee.getPassword(), referee.getBirthDate());
                    dbController.addFan(this, newFan);
                    return true;
                }
            } else {
                throw new IncorrectInputException();
            }
        } else {
            throw new MemberNotExist();
        }
    }

    /**
     ** this function get id of a member and remove it from the system
     * @param id
     * @return true if it delete from the db and else false
     * @throws IncorrectInputException- if the id is not a mail
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist- if the member with this id does not exist
     * @throws AlreadyExistException- if a fan with this specific id already exist (it make the member a fan and than delete it)
     * @throws NotReadyToDelete-if one of the member is have object that cant be delete with
     */
    public boolean removeMember(String id) throws IncorrectInputException, DontHavePermissionException, MemberNotExist, AlreadyExistException, NotReadyToDelete {
        if (inputAreLegal(id)) {
            if (dbController.existMember( id)) {
                Role role=dbController.getMember(id);
                if(role instanceof Player)
                {
                    ((Player)role).deleteAllTheData();
                }
                else if(role instanceof Coach)
                {
                    ((Coach)role).deleteAllTheData();
                }
                else if(role instanceof Manager)
                {
                    ((Manager)role).deleteAllTheData();
                }
                else if(role instanceof Owner)
                {
                    removeOwner(((Owner) role).getUserMail());
                    //     removeFan(((Owner) role).getUserMail());
                }
                else if(role instanceof Referee)
                {
                    removeReferee(((Referee) role).getUserMail());
                    //  removeFan(((Owner) role).getUserMail());
                }
                else if(role instanceof AssociationDelegate)
                {
                    removeAssociationDelegate(((AssociationDelegate) role).getUserMail());
                    // removeFan(((AssociationDelegate) role).getUserMail());
                }
                else if(role instanceof SystemManager)
                {
                    removeSystemManager(((Owner) role).getUserMail());
                    // removeFan(((Manager) role).getUserMail());
                }
                else if(role instanceof Fan)
                {
                    // removeFan(((Fan) role).getUserMail());
                }
                dbController.deleteMember(this, id);
                return true;
            } else {
                throw new MemberNotExist();
            }
        } else {
            throw new IncorrectInputException("input are illegal");
        }
    }

    /**
     ** this function get id of a member and make it referee
     * @param id
     * @param ifMainReferee- if the referee will be main or secondary
     * @return true if it add to the db as referee and else false
     * @throws IncorrectInputException- if the id is not a mail
     * @throws MemberAlreadyExistException- if a referee with this specific id already exist
     * @throws MemberNotExist- if the referee with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException- if a referee with this specific id already exist
     */
    public boolean addReferee(String id, boolean ifMainReferee) throws IncorrectInputException, MemberAlreadyExistException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (inputAreLegal(id)) {
            if (!dbController.existReferee( id)) {
                if (dbController.existFan( id)) {
                    Fan fan = (Fan) dbController.getMember( id);
                    Referee referee = null;
                    if (ifMainReferee) {
                        referee = new MainReferee(fan, dbController);
                    } else {
                        referee = new SecondaryReferee(fan, dbController);
                    }
                    dbController.deleteFan(this, id);
                    dbController.addReferee(this, referee);
                    return true;
                } else {
                    throw new MemberNotExist();
                }
            } else {
                throw new MemberAlreadyExistException();
            }
        } else {
            throw new IncorrectInputException();
        }
    }

    /**
     ** this function get id of a member and make it fassociation deligate
     * @param id
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist- if the association deligate with this id does not exist
     * @throws AlreadyExistException - if a association deligate with this specific id already exist
     */
    public void addAssociationDelegate(String id) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        if (this.dbController.getFans().containsKey(id)) {
            Member member = (Member) this.dbController.getMember( id);
            AssociationDelegate newA_D = new AssociationDelegate(member.getName(), member.getUserMail(), member.getPassword(), member.getBirthDate(), dbController);
            this.dbController.deleteRole(this, id);
            this.dbController.addAssociationDelegate(this, newA_D);
        }
    }

    /**
     ** this function get id of a member and make it owner
     * @param id
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws MemberNotExist- if the owner with this id does not exist
     * @throws AlreadyExistException- if a owner with this specific id already exist
     */
    public void addOwner(String id) throws DontHavePermissionException, MemberNotExist, AlreadyExistException {
        if (this.dbController.getFans().containsKey(id)) {
            Member member = (Member) this.dbController.getMember( id);
            Owner newOwner = new Owner(member.getName(), member.getUserMail(), member.getPassword(), member.getBirthDate(), this.dbController);
            this.dbController.deleteRole(this, id);
            this.dbController.addOwner(this, newOwner);
        }
    }

    /**
     * * this function get id of a member and make it a system manager
     * @param id
     * @throws MemberNotExist- if the system manager with this id does not exist
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException- if a system manager with this specific id already exist
     */
    public void addSystemManager(String id) throws MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (this.dbController.getFans().containsKey(id)) {
            Member member = (Member) this.dbController.getMember( id);
            SystemManager newSystemManager = new SystemManager(member.getName(), member.getUserMail(), member.getPassword(), this.dbController, member.getBirthDate());
            this.dbController.deleteRole(this, id);
            this.dbController.addSystemManager(this, newSystemManager);
        }
    }

    /**
     * this function get path and print all the data in the file - this data will be the system infromation
     * @param path
     */
    public void viewSystemInformation(String path) {
        print(readLineByLine(path));
    }

    /**
     * this function get season and league id and scheduling all the games in this specific league in season
     * @param seasonId
     * @param leagueId
     * @throws ObjectNotExist - if the league id or the season id is not exists
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws IncorrectInputException- if the league id or the season id input enter in a wrong way
     */
    public void schedulingGames(String seasonId, String leagueId) throws ObjectNotExist, DontHavePermissionException, IncorrectInputException {
        League league = dbController.getLeague( leagueId);
        Season season = dbController.getSeason( seasonId);
        LeagueInSeason leagueInSeason = league.getLeagueInSeason(season);
        LinkedList<Team> teams = leagueInSeason.getTeamsForScheduling();
        if(teams.size()%2!=0)
        {
            throw new IncorrectInputException("need to be even teams to scheduling");
        }
        else if(notEnoughReferee(leagueInSeason , teams.size())==false)
        {
            throw new IncorrectInputException("not enough refree for scheduling games");
        }
        else {
            ASchedulingPolicy schedulingPolicy = leagueInSeason.getPolicy();
            Set <Game> games=schedulingPolicy.setGamesOfTeams(teams, leagueInSeason);
            leagueInSeason.addGames(games);
            //dbController.addGames(this,games);
        }
    }

    /**
     * this function get team name and close it
     * @param teamName
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws ObjectNotExist - if the team name is not connected to real team and not exist in the db
     * @throws MemberNotExist - after the closing the owner become fan if its his only team - if the owner not exist in the db
     * @throws AlreadyExistException -after the closing the owner become fan if its his only team - if the fan with this id already exist in the db
     * @throws IncorrectInputException - if the team name enter in a wrong way
     */
    public boolean closeTeam(String teamName) throws DontHavePermissionException, ObjectNotExist, MemberNotExist, AlreadyExistException, IncorrectInputException {
        if (dbController.existTeam( teamName)) {
            Team team = dbController.getTeam( teamName);
            if(team.getGamesSize()==0) {
                HashSet<Owner> allTheOwnerOfTheGroup = team.deleteTheData();
                changeTheOwnerToFan(allTheOwnerOfTheGroup);
                dbController.removeTeam(this, teamName);
                return true;
            }
            else
            {
                throw new IncorrectInputException("this team have games , you cant close it");
            }
        } else {
            throw new ObjectNotExist("this team name is not exist");
        }
    }

    /**
     * this function get team name and idowner and open new team that the owner is the owner of this team
     * @param teamName
     * @param idOwner
     * @return
     * @throws ObjectAlreadyExist-exist in the db team with this team name
     * @throws ObjectNotExist - id owner is not exist in the db
     * @throws MemberNotExist - id owner is not exist in the db
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException - id owner is already exist in the db not as fan
     * @throws IncorrectInputException - the team name enter in a wrong way
     */
    public boolean addNewTeam(String teamName, String idOwner) throws ObjectAlreadyExist, ObjectNotExist, MemberNotExist, DontHavePermissionException, AlreadyExistException, IncorrectInputException {
        if (alreadyIncludeThisTeamName(teamName) == true) {
            throw new ObjectAlreadyExist();
        }
        else if(legalInputTeamName(teamName)==false)
        {
            throw new IncorrectInputException();
        }
        else if (dbController.existOwner( idOwner) == false && dbController.existFan( idOwner) == false) {
            throw new ObjectNotExist("the is you enter is not exist as owner of a team");
        } else {
            Owner owner = null;
            Role role = dbController.getMember( idOwner);
            if (role instanceof Fan) {//if its the first team for this owner
                owner = new Owner(role.getName(), ((Fan) role).getUserMail(), ((Fan) role).getPassword(), role.getBirthDate(), dbController);
                dbController.deleteFan(this, ((Fan) role).getUserMail());
                dbController.addOwner(this, owner);
            } else if (role instanceof Owner) {
                owner = (Owner) role;
            }
            if (owner != null) {
                Account account = new Account();
                Team newTeam = new Team(teamName, account, owner);
                owner.addTeam(newTeam);
                dbController.addTeam(this, newTeam);
            }
        }
        return false;
    }

    /**************all the complaint function*****************/

    /**
     * this function get path and show all the complaint in the file
     * @param path
     * @return
     */
    public LinkedList<String> watchComplaint(String path) {
        LinkedList<String> complaintList = readLineByLine(path);
        return complaintList;
    }

    /**
     *
     * @param path
     * @param response this hashMap represent - the number of the complaint and the response for the complain
     * @return
     */
    public boolean ResponseComplaint(String path, LinkedList<Pair<String, String>> response) {
        //this function get the linkes list after the manager added his response for the complaint
        writeToFile(path, response);
        return true;
    }

    /**
     * this function get path and the response for the complaint , and write it to the file
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

    /**
     * this function get path to file and return the data from the file
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


    /*******************private function********************/

    /**
     * return true if the string is in a mail version and else false
     * @param str
     * @return
     */
    private boolean inputAreLegal(String str) {
        if (str.contains("@") && str.contains(".")) {
            return true;
        }
        return false;
    }

    /**
     * this function get linkelist and print it to the system
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
     * @param leagueInSeason
     * @param numOfTeams
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     */
    private boolean notEnoughReferee( LeagueInSeason leagueInSeason , int numOfTeams) throws DontHavePermissionException {
        HashMap<String,Referee> refereeHashMap=leagueInSeason.getReferees();
        // if(refereeHashMap.size()<) {
        //     return false;
        //  }
        int counterMain=0;
        int counterSecondary=0;

        for (String referee:refereeHashMap.keySet()
        ) {
            if(refereeHashMap.get(referee) instanceof MainReferee)
            {
                counterMain++;
            }
            else if(refereeHashMap.get(referee) instanceof SecondaryReferee)
            {
                counterSecondary++;
            }
        }
        if (counterSecondary<numOfTeams/2)
        {
            return false;
        }
        if (counterMain<numOfTeams/2)
        {
            return false;
        }
        return true;
    }

    /**
     * this function get a hashset with owner and change them to be fans
     * @param allTheOwnerOfTheGroup
     * @throws MemberNotExist-if the id in the hashset is not exist in the db as owner
     * @throws DontHavePermissionException- if the member is not system manager
     * @throws AlreadyExistException-if there is already a fan with this specific id
     */
    private void changeTheOwnerToFan(HashSet<Owner> allTheOwnerOfTheGroup) throws MemberNotExist, DontHavePermissionException, AlreadyExistException {
        for (Owner owner : allTheOwnerOfTheGroup
        ) {
            if (owner.getTeams().size() == 0) {
                Fan newFan = new Fan(owner.getName(), owner.getUserMail(), owner.getPassword(), owner.getBirthDate());
                dbController.deleteOwner(this, owner.getUserMail());
                dbController.addFan(this, newFan);
            }
        }
    }

    /**
     * this function return true if the team added and false if there were problem with the data
     */
    private boolean legalInputTeamName(String teamName) {
        int counter=0;
        for(int i=0; i<teamName.length(); i++)
        {
            if(teamName.charAt(i)>='0' && teamName.charAt(i)<='9')
                counter++;
        }
        if(counter==teamName.length())
            return false;

        return true;
    }

    /**
     * this function check if there is already a team with the specific team name
     * @param teamName
     * @return
     * @throws DontHavePermissionException- if the member is not system manager
     */
    private boolean alreadyIncludeThisTeamName(String teamName) throws DontHavePermissionException {

        return dbController.existTeam( teamName);
    }


    /************* help function for testing *************/
    public HashMap<String, Role> getRoles() throws DontHavePermissionException {
        return this.dbController.getRoles();
    }

    public HashMap<String, Team> getTeams() throws DontHavePermissionException {
        return this.dbController.getTeams();
    }

    public HashSet<Game> getGames(String league, String season) throws ObjectNotExist {
        return this.dbController.getGames(league,season);
    }
    @Override
    public String getType() {
        return "Manager";
    }
}