package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;
import com.football.Service.ErrorLogService;
import com.football.Service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

@Service
public class FanService {

//    @Autowired
//    private DBController dbController;

    @Autowired
    private DBController dbController;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private EventLogService eventLogService;

    public void followTeam(String id, Team team) throws MemberNotExist {
       try{
           if(dbController.existFan(id)){
               Fan fan = dbController.getFan(id);
               team.addNewFollower(fan);
           }
       }catch(MemberNotExist e){
           errorLogService.addErrorLog("Member Not Exist");
           throw new MemberNotExist();
       }

    }

    public void followGame(String id, Game game) throws MemberNotExist {
       try{
           if(dbController.existFan(id)){
               Fan fan = dbController.getFan(id);
               game.addNewFollower(fan);
           }
       }catch(MemberNotExist e){
           errorLogService.addErrorLog("Member Not Exist");
           throw new MemberNotExist();
       }

    }

    public void updatePersonalDetails(String id, String newName,String newMail) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (dbController.existFan(id)) {
            Fan fan = dbController.getFan(id);
            if (newName == null || newMail == null) {
                errorLogService.addErrorLog("Incorrect Input Exception");
                throw new IncorrectInputException();
            }
            dbController.deleteFan(fan, id);
            if (newName != "") {
                fan.setName(newName);
            }
            if (newMail != "") {
                fan.setMail(newMail);
            }
            dbController.addFan(fan,fan);
            eventLogService.addEventLog(id,"update personal details");
        }
    }

    public void sendComplaint(String path, String complaint){
        LinkedList<String> complaintsList = new LinkedList<>();
        try {
            File newText = new File(path);
            Scanner scanner = new Scanner(newText);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                complaintsList.add(line);
            }
            complaintsList.add(complaint);
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i=0; i<complaintsList.size(); i++){
                bw.write(i + "." + complaintsList.get(i));
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            errorLogService.addErrorLog("the path is not legal");
            System.out.print("the path is not legal");
        }
    }


}
