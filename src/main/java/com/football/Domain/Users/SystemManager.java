package com.football.Domain.Users;

import java.util.Date;

public class SystemManager extends Member{

    public SystemManager(String name, String userMail, String password, Date birthDate) {
        super(name, userMail, password, birthDate);
    }


    public SystemManager(String[] systemManagerDetails) {
        super(systemManagerDetails[2],systemManagerDetails[0],systemManagerDetails[1],
                new Date(Integer.parseInt(systemManagerDetails[3].split("-")[0]),
                        Integer.parseInt(systemManagerDetails[3].split("-")[1]),
                        Integer.parseInt(systemManagerDetails[3].split("-")[2]))
        );
    }


    @Override
    public String getType() {
        return "0SystemManager";
    }

    @Override
    public String toString() {
        String str="";
        str="\'"+this.getUserMail()+"\',\'"+this.getPassword()+"\',\'"+this.getName()+"\',\'"+this.getBirthDateString()+"\'";
        return str;
    }
}
