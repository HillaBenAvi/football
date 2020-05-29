package com.football.Service;

import com.football.DataBase.DBController;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class EventLogService {


    public static int id ;
    @Autowired
    private DBController dbController;

    public void addEventLog(String userId, String actionName) throws AlreadyExistException {
        id = dbController.getMaxEventLog() +1 ;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        EventLog event = new EventLog(id, strDate, userId, actionName);
        dbController.addEventLog(event);
        id++;
    }
}