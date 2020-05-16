package com.football.Domain.Users;

import com.football.DataBase.DBController;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

public class Fan extends Member implements Observer {

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
