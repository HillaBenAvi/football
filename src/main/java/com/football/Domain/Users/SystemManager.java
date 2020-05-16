package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Coach;
import com.football.Domain.Asset.Manager;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.Account;
import com.football.Domain.Game.Game;
import com.football.Domain.Game.Team;
import com.football.Domain.League.ASchedulingPolicy;
import com.football.Domain.League.League;
import com.football.Domain.League.LeagueInSeason;
import com.football.Domain.League.Season;
import com.football.Exception.*;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;


public class SystemManager extends Member {


    public SystemManager(String name, String userMail, String password, DBController dbController, Date birthDate) {
        super(name, userMail, password, birthDate);
    }

    public SystemManager(String[] systemManagerDetails) {
        super(systemManagerDetails[1],systemManagerDetails[0],systemManagerDetails[2],
                new Date(Integer.parseInt(systemManagerDetails[3]),Integer.parseInt(systemManagerDetails[4]),Integer.parseInt(systemManagerDetails[5]))
        );

    }

    /**
     * this function get id of a association deligate and make it fan
     * @param id
     * @throws DontHavePermissionException - if the memver is not system manager
     * @throws MemberNotExist - if the association deligate with this id does not exist
     * @throws AlreadyExistException - if a fan with this specific id already exist
     * @throws IncorrectInputException - if the id is not a mail
     */

    @Override
    public String getType() {
        return "Manager";
    }
}