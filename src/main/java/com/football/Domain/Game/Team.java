package com.football.Domain.Game;

import com.football.Domain.Asset.*;
import com.football.Domain.Users.Fan;
import com.football.Domain.Users.Owner;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Team {
    private String name;
    private Account account;
    private HashSet<Coach> coaches;
    private HashSet<Player> players;
    private HashSet<Manager> managers;
    private HashSet<Owner> owners;
    private Field homeField;
    private HashSet<Game> games;
    private HashSet<Field> trainingFields; // list of all fields that the group training at
    private Boolean status; //true=> team is open , false=>team is closed
    private PersonalPage personalPage;

    public Team(String name, Account account, Field homeField) {
        this.name = name;
        this.account = account;
        this.homeField = homeField;
        coaches = new HashSet<>();
        players = new HashSet<>();
        managers = new HashSet<>();
        owners = new HashSet<>();
        trainingFields=new HashSet<>();
        games=new HashSet<>();
        personalPage = new PersonalPage();
        this.status=true;
    }

    public Team(String name, Account account,Owner owner) {
        this.name = name;
        this.account = account;
        this.owners = new HashSet<>();
        owners.add(owner);

        coaches = new HashSet<>();
        players = new HashSet<>();
        managers = new HashSet<>();
        trainingFields=new HashSet<>();
        games=new HashSet<>();
        personalPage = new PersonalPage();
        this.status=true;

    }

    public Team(Account account, LinkedList<Player> players, LinkedList<Coach> coaches, LinkedList<Manager> managers, LinkedList<Owner> owners, String teamName) throws AlreadyExistException, DontHavePermissionException {
       this.status=true;
        this.name = teamName;
        this.account = account;
        this.coaches = new HashSet<>();
        this.players = new HashSet<>();
        this.managers = new HashSet<>();
        this.owners = new HashSet<>();
        games=new HashSet<>();
        this.coaches.addAll(coaches);
        this.players.addAll(players);
        this.owners.addAll(owners);
        this.managers.addAll(managers);

    }

    private void updateTheTeamListCoach(LinkedList<Coach> list) {
        for (int i = 0; i < list.size(); i++) {
            TeamMember member = list.get(i);
            member.addTeam(this);
        }

    }

    private void updateTheTeamListManager(LinkedList<Manager> list) {
        for (int i = 0; i < list.size(); i++) {
            TeamMember member = list.get(i);
            member.addTeam(this);
        }
    }

    private void updateTheTeamListPlayer(LinkedList<Player> list) {
        for (int i = 0; i < list.size(); i++) {
            TeamMember member = list.get(i);
            member.addTeam(this);
        }
    }


    public String getName() {
        return name;
    }

//    public HashSet<Owner> deleteTheData(){
//
//        HashSet<Owner> newHash=owners;
//        for (Player player : players
//        ) {
//            player.removeTheTeamFromMyList(this.name);
//        }
//        for (Owner owner : owners
//        ) {
//            owner.removeTheTeamFromMyList(this.name);
//        }
//        for (Coach coach : coaches
//        ) {
//            coach.removeTheTeamFromMyList(this.name);
//        }
//        for (Manager manager : managers
//        ) {
//            manager.removeTheTeamFromMyList(this.name);
//        }
//return newHash;
//    }


    /***Getters***/
    public HashSet<Coach> getCoaches() {
        return coaches;
    }

    public HashSet<Manager> getManagers() {
        return managers;
    }

    public HashSet<Owner> getOwners() {
        return owners;
    }

    public HashSet<Game> getGames () {return games;}

    public HashSet<Player> getPlayers() {
        return players;
    }
    public Player getPlayer(String id){
        for (Player player : this.players) {
            if (player.getUserMail().equals(id))
                return player;
        }
        return null;
    }
    public Coach getCoach(String id){
        for (Coach coach : this.coaches) {
            if (coach.getUserMail().equals(id))
                return coach;
        }
        return null;
    }
    public Field getField(String name){
        for (Field field : this.trainingFields) {
            if (field.getNameOfField().equals(name))
                return field;
        }
        return null;
    }
    public Account getAccount() {
        return account;
    }

    public HashSet<Field> getTrainingFields() { return trainingFields; }

    public Field getFieldFromTrainingFields(String fieldName){
        for(Field field:trainingFields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }
        return null;
    }

    public Field getHomeField() {
        return homeField;
    }

    /***Setters***/

    public void setManagers(HashSet<Manager> managers) {
        this.managers = managers;
    }

    public void setTrainingFields(HashSet<Field> trainingFields) {
        this.trainingFields = trainingFields;
    }

    public void setHomeField(Field homeField) {
        this.homeField = homeField;
    }

    public void setPlayers(HashSet<Player> players) {
        this.players = players;
    }

    public void setOwners(HashSet<Owner> owners) {
        this.owners = owners;
    }

    public boolean isManager(Manager someone) {
        return this.managers.contains(someone);
    }



    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isOwner(Owner owner) {
        if (this.owners != null && this.owners.contains(owner))
            return true;
        return false;
    }

    /**
     * add a new transaction to the account
     * @param description
     * @param amount
     */
    public void addTransaction(String description, double amount) {
       ArrayList<Transaction> transactions= account.getTransactions();
       Transaction transaction= new Transaction(description,amount);
       transactions.add(transaction);
    }

    /***********************Add methods*************************/

    public void addManager(Manager someone) {
        if (someone != null && !this.managers.contains(someone))
            this.managers.add(someone);
        personalPage.notifyFollowers("The team " + name + " has new manager");
    }

    public void addOwner(Owner someone) {
        if (someone != null && !this.owners.contains(someone))
            this.owners.add(someone);
        personalPage.notifyFollowers("The team " + name + " has new owner");
    }

    public void addCoach(Coach someone) {
        if (someone != null && !this.coaches.contains(someone))
            this.coaches.add(someone);
        personalPage.notifyFollowers("The team " + name + " has new coach");
    }

    public void addPlayer(Player someone) {
        if (someone != null && !this.players.contains(someone)){
            this.players.add(someone);
        }
        personalPage.notifyFollowers("The team " + name + " has new player");
    }

    public void addField(Field field) {
        if (field != null && !this.trainingFields.contains(field)){
            this.trainingFields.add(field);
        }
        if(homeField==null){
            homeField=field;
        }
        personalPage.notifyFollowers("The team " + name + " has new field");
    }


    /***********************Remove methods*************************/

    public void removeManager(Manager someone) {
        if (someone != null && !this.managers.contains(someone))
            this.managers.remove(someone);
    }

    public void removeCoach(Coach someone) {
        if (someone != null && !this.coaches.contains(someone))
            this.coaches.remove(someone);
    }

    public void removePlayer(Player someone) {
        if (someone != null && this.players.contains(someone)){
            this.players.remove(someone);
        }
    }

    public void addGame(Game game) {
        games.add(game);
        personalPage.notifyFollowers("The team " + name + " has new game");
    }

    public int getGamesSize() {
        return games.size();
    }

    public Manager getManager(String id) {
        for (Manager manager : this.managers) {
            if (manager.getUserMail().equals(id))
                return manager;
        }
        return null;
    }

    public Owner getOwner(String id){
        for (Owner owner : this.owners) {
            if (owner.getUserMail().equals(id))
                return owner;
        }
        return null;
    }

    public void addNewFollower (Fan newFollower){
        personalPage.addFollower(newFollower);
    }

    public int getFollowersNumber(){
        return personalPage.countObservers();
    }

    @Override
    public String toString() {
        String str="";
        str="\'"+this.getName()+"\',\'"+account.toString()+"\',\'"+getCoachListString()+"\',\'"+getPlayersListString()+"\',\'"+this.getManagersListString()+"\',\'"
                +this.getOwnersListString()+"\',\'";
        if(homeField!=null)
       str+=homeField.toString();
         str+="\',\'"+this.getGamesListString()+"\',\'";
       str+=ToStringtrainingFields();
       str+="\',\'"+this.status+"\'";
        return str;
    }

    private String ToStringtrainingFields() {
        String s="";
        for (Field field: trainingFields
             ) {
            if(field!=null)
            s+=field.toString()+":";
        }
        if(s.length()>0)
            s=s.substring(0,s.length()-1);
        return s;
    }

    private String getPlayersListString() {
        String str="";
        for (Player player:players
        ) {
            str+=player.toString()+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    private String getCoachListString() {
        String str="";
        for (Coach coach:coaches
        ) {
            str+=coach.toString()+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    private String getManagersListString() {
        String str="";
        for (Manager manager:managers
        ) {
            str+=manager.toString()+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    private String getGamesListString() {
        String str="";
        for (Game game:games
        ) {
            str+=game.toString()+";";
        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    private String getOwnersListString() {
        String str="";
        for (Owner owner:owners
        ) {
            str+="("+owner.toString()+") ";

        }
        if(str.length()>0)
        {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }
}

