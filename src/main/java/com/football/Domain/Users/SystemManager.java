package com.football.Domain.Users;

import java.util.Date;

public class SystemManager extends Member{

    public SystemManager(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
    }


    public SystemManager(String[] systemManagerDetails) {
        super(systemManagerDetails[1],systemManagerDetails[0],systemManagerDetails[2],
                new Date(Integer.parseInt(systemManagerDetails[3]),Integer.parseInt(systemManagerDetails[4]),Integer.parseInt(systemManagerDetails[5]))
        );
    }


    @Override
    public String getType() {
        return "SystemManager";
    }
}
