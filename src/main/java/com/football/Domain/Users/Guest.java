package com.football.Domain.Users;

import com.football.DataBase.DBController;
import org.springframework.beans.factory.annotation.Autowired;
public class Guest extends Role{

//    private SecurityMachine securityMachine;
    public Guest() {
        super("guest");
//        this.securityMachine = new SecurityMachine();
    }

}
