package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Team;
import com.football.Exception.*;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Owner extends Member {
    private HashMap<String, Team> teams;


    public Owner(String name, String userMail, String password, Date birthDate, DBController dbController) throws DontHavePermissionException {
        super(name, userMail, password, birthDate);
        teams = new HashMap<>();
    }

    public Owner(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
        teams = new HashMap<>();
    }

    @Override
    public String getType() {
        return "Owner";
    }


}
