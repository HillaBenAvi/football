package com.football.Domain.Users;

import com.football.DataBase.DBController;
import com.football.Domain.Game.Team;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    @Autowired
    private DBController dbController;

    public void addTeam(Owner owner, Team team) throws AlreadyExistException, DontHavePermissionException {
        dbController.addTeam(owner,team);
    }
}
