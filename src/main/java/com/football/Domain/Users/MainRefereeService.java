package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Event;
import com.football.Domain.Game.EventInGame;
import com.football.Domain.Game.Game;
import com.football.Exception.*;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import com.football.Service.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

@Service
public class MainRefereeService {

//    @Autowired
//    private DBController dbController;

    @Autowired
    private DBController dbController;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;

    private Notification notification;

    /**
     * in this function a main referee can update an event in a game
     * @param gameid
     * @param refereeId
     * @param year
     * @param mounth
     * @param day
     * @param description
     * @param gameMinute
     * @param eventInGame
     * @param description
     * @param playersId
     */

    public void updateGameEvent(String gameid, String refereeId, String year,String mounth, String day, String description, String gameMinute, String eventInGame, String playersId) throws MemberNotExist, DontHavePermissionException, ObjectNotExist {
        if(dbController.existReferee(refereeId)) {
            Referee referee = dbController.getReferee(refereeId);
            if(referee instanceof MainReferee){
                HashSet<Game> games = referee.games;
                Game game = dbController.getGame(gameid);
                for(Game gameR : games){
                    if(gameid.equals(gameR.getId())){
                        Date date = new Date( Integer.parseInt(year), Integer.parseInt(mounth),  Integer.parseInt(day));
                        String[] playersIdS = playersId.split(";");
                        ArrayList<String> players = new ArrayList<>();
                        for(String playerid : playersIdS){
                            players.add(playerid);
                        }
                        Event event1 = new Event(date,description,EventInGame.valueOf(eventInGame),Integer.parseInt(gameMinute),players);
                        game.addEvent(event1);
                        dbController.updateGame(referee,game);
                        return;
                    }
                }
            }
            else {
                throw new DontHavePermissionException();
            }
        }
    }

    /**
     * a function that return all the games the referee can still edit
     * @return all the games that happened less than 5 hours ago
     */

    public LinkedList<Game> getEditableGames (Referee referee) throws MemberNotExist, DontHavePermissionException {

            LinkedList<Game> editableGames = new LinkedList();
            for (Game game : referee.games){
                Long now = System.currentTimeMillis();
                long difference = (now - game.getDateCalendar().getTimeInMillis())/1000;
                float differenceInHours= difference/(3600);
                if(differenceInHours < 5){
                    editableGames.add(game);
                }
            }
            return editableGames;
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

    public void updateDetails(String id, String newName, String newMail,String newTraining) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException, MemberAlreadyExistException {

        if (newName == null || newMail ==null ||newTraining == null){
            throw new IncorrectInputException("");
        }
        if (dbController.existReferee(id)){
            Referee referee = dbController.getReferee(id);
            dbController.deleteReferee(referee, id);
            if (newName != ""){
                referee.setName(newName);
            }
            if (newMail != ""){
                if (!dbController.existMember(newMail)){
                    referee.setUserMail(newMail);
                }
                else{
                    throw new MemberAlreadyExistException();
                }
            }
            if (newTraining != ""){
                referee.training = newTraining;
            }
            dbController.addReferee(referee, referee);
            eventLogService.addEventLog(id, "update details of main referee");
        }
    }

}
