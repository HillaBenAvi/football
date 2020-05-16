package com.football;

import com.football.Domain.Users.Fan;
import com.football.Exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;

@RestController
@RequestMapping(value="/service")
public class ServiceController {
    //private static final ServiceController instance = new ServiceController();
    //public static ServiceController getInstance() {return instance;}
    @Autowired
    Manager manager;

    @RequestMapping(value="/signin",method = RequestMethod.POST)
    public void signIn(@RequestParam(value = "userName") String userName,
                       @RequestParam(value = "userMail") String userMail,
                       @RequestParam(value = "password") String password
    ) throws IncorrectInputException, DontHavePermissionException, AlreadyExistException {
       // systemController.signIn(userName, userMail,  password , birthDate);
        manager.signIn(userName,userMail,password);

    }

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password")String password) throws PasswordDontMatchException, MemberNotExist, DontHavePermissionException {
      //  String type = systemController.login(id,pass);

        return id + password;
    }

    @RequestMapping(value="/fans",method = RequestMethod.GET)
    public LinkedList<String> getFans() throws DontHavePermissionException {
        HashMap<String, Fan> fans = new HashMap<>();//systemController.getFans();
        Fan f = new Fan("guy","mail","pass",null);
        Fan g = new Fan("daniel","mdail","dpass",null);

        fans.put("guy",f);
        fans.put("daniel",g);
        LinkedList<String> linkedList = new LinkedList<>(fans.keySet());
        return linkedList;
    }
}
