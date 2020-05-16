package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

@Repository
public class FanController {

    @Autowired
    private DBController dbController;

    public void followTeam(Team team, Fan fan){
        team.addNewFollower(fan);
    }

    public void followGame(Game game, Fan fan){
        game.addNewFollower(fan);
    }

    public void updatePersonalDetails(String newName,String newMail) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (newName == null || newMail == null){
            throw new IncorrectInputException();
        }
        //dbController.deleteFan(this, super.getUserMail());
        if(newName != ""){
         //   super.setName(newName);
        }
        if(newMail != ""){
         //   super.setMail(newMail);
        }
        //dbController.addFan(this, this);
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
            System.out.print("the path is not legal");
        }
    }

   // public ArrayList<String> getUpdates(){
   //     return updates;
   // }


}
