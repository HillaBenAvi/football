package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.EventInGame;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Event;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Period;
import java.util.*;

public class MainReferee extends Referee {

    @Autowired
    private DBController dbController;

    public MainReferee(String name, String userMail, String password, String training , Date birthDate) {
        super(name, userMail, password, training , birthDate);
    }
    public MainReferee(Fan fan, DBController dbcontroller)
    {
        super(fan.getName(),fan.getUserMail(), fan.getPassword() , "" , fan.getBirthDate());
    }

    /**
     * in this function a main referee can update an event in a game
     * @param game
     * @param timeInGame
     * @param event
     * @param time
     * @param description
     * @param players
     */

   public void updateGameEvent(Game game, int timeInGame, EventInGame event, Date time, String description, ArrayList<Player> players){
       if (getEditableGames().contains(game)){
           game.addEvent(new Event(time, description, event, timeInGame, players));
       }
   }


    /**
     * the function allows the referee to update his own details.
      * @param newName
     * @param newMail
     * @param newTraining
     * @throws IncorrectInputException - in case of illegal input
     * @throws MemberNotExist
     * @throws DontHavePermissionException
     * @throws AlreadyExistException
     */

    public void updateDetails(String newName, String newMail,String newTraining) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (newName == null || newMail ==null ||newTraining == null){
            throw new IncorrectInputException("");
        }
        super.dbController.deleteReferee(this, super.getUserMail());
        if (newName != ""){
            super.setName(newName);
        }
        if (newMail != ""){
            super.setUserMail(newMail);
        }
        if (newTraining != ""){
            super.training = newTraining;
        }
        super.dbController.addReferee(this, this);
    }


    /**
     * a function that return all the games the referee can still edit
     * @return all the games that happened less than 5 hours ago
     */

    public LinkedList<Game> getEditableGames (){
        LinkedList<Game> editableGames = new LinkedList();
        for (Game game : super.games){
            Long now = System.currentTimeMillis();
            long difference = (now - game.getDateCalendar().getTimeInMillis())/1000;
            float differenceInHours= difference/(3600);
            if(differenceInHours < 5){
                editableGames.add(game);
            }
        }
        return editableGames;
    }
    @Override
    public String getType() {
        return "MainReferee";
    }

}
