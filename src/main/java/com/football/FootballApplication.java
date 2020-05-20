package com.football;

import com.football.DataBase.SecondaryRefereeDao;
import com.football.DataBase.SystemManagerDao;
import com.football.Domain.Users.Referee;
import com.football.Domain.Users.SecondaryReferee;
import com.football.Domain.Users.SystemManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class FootballApplication {

    public static void main(String[] args) {
      //  SpringApplication.run(FootballApplication.class, args);

        SecondaryReferee systemManager=new SecondaryReferee("shacahr", "123567", "meretz@gmail.com", "", new Date(1, 1, 1995));
        SecondaryRefereeDao systemManagerDao=new SecondaryRefereeDao();
        systemManagerDao.save(systemManager);
    }

}