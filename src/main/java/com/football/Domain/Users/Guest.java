package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
//import com.football.Service.SecurityMachine;

import java.util.Date;
import java.util.regex.Pattern;

public class Guest extends Role{

    @Autowired
    private DBController dbController;
//    private SecurityMachine securityMachine;
    public Guest() {
        super("guest");
//        this.securityMachine = new SecurityMachine();
    }

}
