package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import com.football.Exception.IncorrectInputException;
import com.football.Exception.MemberNotExist;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Fan extends Member implements Observer {
    @Autowired
    private DBController dbController;
    private ArrayList<String> updates;

    public Fan(String name, String mail, String password, Date birthDate) {
        super(name, mail, password, birthDate);
        updates = new ArrayList<>();
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
