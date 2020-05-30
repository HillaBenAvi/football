//package com.football.unitTesting;
//
//import DataBase.DBController;
//import Domain.Asset.Field;
//import Domain.Game.Account;
//import Domain.Game.Game;
//import Domain.Game.Team;
//import Domain.Game.Transaction;
//import Domain.Users.MainReferee;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//
//public class RefereeUnitTesting {
//
//    DBController dbController = DBController.getInstance();
//
//    @Before
//    public  void init()
//    {
//        dbController.deleteAll();
//    }
//    //test for add games and get schedule
//
//    @Test
//
//    public void addGamesGetScheduleTest(){
//
//        ArrayList<Transaction> transM = new ArrayList<>();
//        ArrayList<Transaction> transH = new ArrayList<>();
//        Field field = new Field("Blumfield");
//        Team maccabi = new Team("Maccabi tel aviv", new Account(transM, 100), field);
//        Team hapoel = new Team("Hapoel tel aviv", new Account(transH, 100), field);
//        Calendar date = Calendar.getInstance();
//        date.set(Calendar.YEAR, 1999);
//        date.set(Calendar.MONTH, 7);
//        date.set(Calendar.DAY_OF_MONTH, 26);
//        Game derbi = new Game("derbi" , date ,maccabi, hapoel, field, null, null, null);
//        MainReferee mr = new MainReferee("newReferee", "ref@gmail.com", "1234", "training" , new Date(), dbController);
//        assertEquals(mr.hadGames(),false);
//        mr.addGame(derbi);
//        assertEquals(mr.hadGames(),true);
//        assertEquals(mr.getGameSchedule().size(),1);
//    }
//
//
//
//    /** MainReferee **/
//
//    @Test
//
//    public void editableGamesTest(){
//
//        // Game number 1- Tel Aviv Derbi
//        ArrayList<Transaction> transMTA = new ArrayList<>();
//        ArrayList<Transaction> transHTA = new ArrayList<>();
//        Field field = new Field("Blumfield");
//        Team maccabiTA = new Team("Maccabi tel aviv", new Account(transMTA, 100), field);
//        Team hapoelTA = new Team("Hapoel tel aviv", new Account(transHTA, 100), field);
//        Calendar date = Calendar.getInstance();
//        date.set(Calendar.YEAR, 1999);
//        date.set(Calendar.MONTH, 7);
//        date.set(Calendar.DAY_OF_MONTH, 26);
//        Game derbi = new Game("derbiTelAviv" , date ,maccabiTA, hapoelTA, field, null, null, null);
//
//        // game number 2- Haifa Derbi
//        ArrayList<Transaction> transMH = new ArrayList<>();
//        ArrayList<Transaction> transHH = new ArrayList<>();
//        Field field2 = new Field("Sami Offer");
//        Team maccabiH = new Team("Maccabi Haifa", new Account(transMH, 100), field);
//        Team hapoelH = new Team("Hapoel Haifa", new Account(transHH, 100), field);
//        Calendar date2 = Calendar.getInstance();
//        date2.set(Calendar.YEAR, 2020);
//        date2.set(Calendar.MONTH, 4);
//        date2.set(Calendar.DAY_OF_MONTH, 25);
//        date2.set(Calendar.HOUR_OF_DAY, 21);
//        Game derbi2 = new Game("derbiHaifa" , date2 ,maccabiH, hapoelH, field2, null, null, null);
//
//        MainReferee mr = new MainReferee("newReferee", "ref@gmail.com", "1234", "training" , new Date(), dbController);
//
//        mr.addGame(derbi);
//        mr.addGame(derbi2);
//        assertEquals(mr.getGameSchedule().size(), 2);
//        assertEquals(mr.getEditableGames().size(),1);
//    }
//
//
//    /** SecondaryReferee **/
//
//}
