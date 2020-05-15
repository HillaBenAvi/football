package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Fan extends Member implements Observer {

    private DBController dbController;
    private ArrayList<String> updates;

    public Fan(String name, String mail, String password, Date birthDate, DBController dbcontroller) {
        super(name, mail, password, birthDate);
        dbController = dbcontroller;
        updates = new ArrayList<>();
    }


    public void followTeam(Team team){
        team.addNewFollower(this);
    }

    public void followGame(Game game){
        game.addNewFollower(this);
    }

    public void updatePersonalDetails(String newName,String newMail) throws IncorrectInputException, MemberNotExist, DontHavePermissionException, AlreadyExistException {
        if (newName == null || newMail == null){
            throw new IncorrectInputException();
        }
        dbController.deleteFan(this, super.getUserMail());
        if(newName != ""){
            super.setName(newName);
        }
        if(newMail != ""){
            super.setMail(newMail);
        }
        dbController.addFan(this, this);
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

    public ArrayList<String> getUpdates(){
        return updates;
    }

    @Override
    // the arg is the message recieved from the observable
    public void update(Observable o, Object message) {
        updates.add("new update:" + message.toString());
    }

    @Override
    public String getType() {
        return "Fan";
    }
}
