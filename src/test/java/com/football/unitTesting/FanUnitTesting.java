package com.football.unitTesting;

import com.football.DataBase.DBController;
import com.football.Domain.Asset.Field;
import com.football.Domain.Asset.Player;
import com.football.Domain.Game.*;
import com.football.Domain.Users.AssociationDelegateService;
import com.football.Domain.Users.Fan;
import com.football.Domain.Users.FanService;
import com.football.Domain.Users.SystemManagerService;
import com.football.Exception.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FanUnitTesting {


    DBController dbController;

    FanService fanService;

//    @Before
//    public void init() throws DontHavePermissionException, AlreadyExistException, MemberNotExist, PasswordDontMatchException {
//        // dbController.addSystemManager("");
//        SystemManagerService systemManagerService = new SystemManagerService();
//        systemManagerService.addAssociationDelegate("hillapet@post.bgu.ac.il","associationDelegate@gmail.com");
//        dbController = new DBController();
//        fanService = new FanService();
//        //controller.logIn("admin@gmail.com","123");
//        //controller.addAssociationDelegate("associationDelegate@gmail.com");
//        // controller.logOut();
//    }
////
//    @Test
//    public void testTeamNotifications() {
//        ArrayList<Transaction> trans = new ArrayList<>();
//        Field field = new Field("Blumfield");
//        Team maccabi = new Team("Maccabi tel aviv", new Account(trans, 100), field);
//        Fan fan = new Fan("Hilla", "hilla@gmail.com", "1234", new Date(), dbController);
//        fan.followTeam(maccabi);
//        Field field2 = new Field("Teddy");
//        maccabi.addField(field2);
//        Player messi = new Player("Leo Messi", "messi@gmail.com", "1234", new Date(), "blabla");
//        maccabi.addPlayer(messi);
//        assertEquals(fan.getUpdates().size(),2);
//    }
//
    @Test
    public void testGameNotifications() throws MemberNotExist, DontHavePermissionException, AlreadyExistException {
        ArrayList<Transaction> transM = new ArrayList<>();
        ArrayList<Transaction> transH = new ArrayList<>();
        Field field = new Field("Blumfield");
        Team maccabi = new Team("Maccabi tel aviv", new Account(transM, 100), field);
        Team hapoel = new Team("Hapoel tel aviv", new Account(transH, 100), field);
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, 1999);
        date.set(Calendar.MONTH, 7);
        date.set(Calendar.DAY_OF_MONTH, 26);
        Game derbi = new Game("derbi" , date ,maccabi, hapoel, field, null, null, null);
        //Fan fan = new Fan("Hilla", "hilla@gmail.com", "1234", new Date(), dbController);
        fanService.followGame("peterhilla@gmail.com",derbi);
        derbi.addEvent(new Event(new Date(), "blabla", EventInGame.FOUL,60, null));
        //assertEquals(fanService.getUpdates().size(),1);
    }


}
