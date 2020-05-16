package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Team;
import com.football.Domain.League.*;
import com.football.Exception.*;

import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AssociationDelegate extends Member {

    public AssociationDelegate(String name, String userMail, String password, Date birthDate, DBController dbController) {
        super(name, userMail, password, birthDate);
    }

    @Override
    public String getType() {
        return "AssociationDelegate";
    }
}


