package com.football.Service;

import com.football.DataBase.DBController;
import com.football.Exception.AlreadyExistException;
import com.football.Exception.DontHavePermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class ErrorLogService {

    @Autowired
    private DBController dbController;
    public static int id = 0;
    //Logger logger = LoggerFactory.getLogger(ErrorLogService.class);

    @RequestMapping("/")
    public void addErrorLog(String topic){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        ErrorLog error = new ErrorLog(id, topic, strDate);

        //dbController.addErrorLog(error);
        id++;
    }
}
